package ufu.facom.lia.search;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.map.EFeatures;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.cache.TranspositionTable;
import ufu.facom.lia.configs.Configs;
import ufu.facom.lia.configs.FeaturesSearchConfig;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.evaluation.policy.BoardEvaluation;
import ufu.facom.lia.exceptions.SlaveException;
import ufu.facom.lia.file.FileManipulate;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.main.SlaveManager;
import ufu.facom.lia.mlp.DirectNet;
import ufu.facom.lia.mlp.EvalNet;
import ufu.facom.lia.mlp.EvalNetImpl;
import ufu.facom.lia.remote.ISlaveRemote;
import ufu.facom.lia.remote.SlaveRemote;
import ufu.facom.lia.remote.utils.ConvertRemoteState;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.SlaveStatus;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.structures.mlp.Net;

public class SlaveAlphaBeta {
	
	private static final Logger logger = LogManager.getLogger(SlaveAlphaBeta.class);

	private ISlaveRemote slaveRemote;
	
	private SlaveManager sm;

	private Map<Key, IState> tasks;

	private Evaluation evaluation;

	private Sucessor sucessor;

	private Integer maxThreadPoolSize;

	private List<MessageReceived> receivedMessages;

	private ConvertRemoteState crs;
	
	private INet net;
	
	private ICache cache;
	
	private Boolean useTableShadow;
	
	private String thisHost;
	
	private static Integer thisPort;
	
	private int masterDepth;
	
	private Boolean callGC;
	
	private boolean unblockMsgSent;
	
	private boolean finalized;
	
	private String searchDefinition;
	
	private static SlaveAlphaBeta sab;
	
	private long waitUnits;
	
	private long tWaitingTasks;
	
	private int numNodesEvaluated;
	
	private int numFile;
	
	private int waitIterations;
	
	public static SlaveAlphaBeta getInstance(Integer port){
		
		if(sab == null){
			sab = new SlaveAlphaBeta(port);
		}
		
		return sab;
		
	}
	
	public static SlaveAlphaBeta getInstance(){
		return getInstance(thisPort);
	}
	
	private SlaveAlphaBeta(Integer port) {

		sm = new SlaveManager();
		
		thisPort = port;
		
		useTableShadow = Boolean.parseBoolean(SystemConfigs.getInstance().getConfig("user-table-shadow"));
		
		thisHost = SystemConfigs.getInstance().getConfig("this");
		
		searchDefinition = SystemConfigs.getInstance().getConfig("search-definition-slave");
		
		configTT();
		
		callGC = true;
	}
	
	private void configTT(){
		
		if(useTableShadow){
			
			//cache = new TranspositionTableShadow( sm, thisHost);
			
			loadNeighboor(cache);
			
			//((TranspositionTableShadow)cache).setLocalCache(cache);
			
		}else{
			//cache = new TranspositionCache(new ZobristKey(), Configs.REGION_SLAVE);
			cache = new TranspositionTable();
		}
	}
	
	private void configSearch() {

		FeaturesSearchConfig fsc = FeaturesSearchConfig.getInstance();

		if (evaluation == null) {
			evaluation = fsc.getEvaluation();
		
			if(evaluation instanceof BoardEvaluation){
	
				BoardUtils bu = new BoardUtils();
				DirectNet dirNet = new DirectNet(new EvalNet());
				FileManipulate fm = new FileManipulate();
				
				List<EFeatures> features = null;
				
				try {
					features = fm.readFeaturesFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				dirNet.setEvalNetImpl(new EvalNetImpl(dirNet.getEvalNet()));
				dirNet.setBoardUtils(bu);
				dirNet.setFeatures(features);
				if(net!=null) {
					dirNet.setNet((Net) net);
				}
				
				((BoardEvaluation) evaluation).setDirNet(dirNet);
			}
		}

		if (sucessor == null) {
			sucessor = fsc.getSucessor();
		}

		if (maxThreadPoolSize == null) {
			maxThreadPoolSize = fsc.getMaxThreadPoolLength();
		}
		
		masterDepth = Integer.parseInt(SystemConfigs.getInstance().getConfig("master-depth"));

		crs = new ConvertRemoteState();

		if(receivedMessages == null) {
			receivedMessages = ((SlaveRemote)slaveRemote).getMessagesReceived();
		}
			
		if(useTableShadow){
		//	((TranspositionTableShadow)cache).connectToNeighbor();
		}

		fsc = null;
	}
	
	private void loadNeighboor(ICache cacheShadow){
		
		String[] slaves = (SystemConfigs.getInstance().getConfig("slaves")).split(";");
		
		for(int i = 0; i < slaves.length; i++){
			
			/*if(slaves[i].equals(thisHost)){
				
				//Verifica o primeiro elemento do ciclo de hosts slaves
				if(i == 0){
					((TranspositionTableShadow) cacheShadow).setTheFirst(Boolean.TRUE);
					((TranspositionTableShadow) cacheShadow).setCanSend(Boolean.TRUE);
				}
				
				if(i+1 < slaves.length){
					((TranspositionTableShadow) cacheShadow).setHostNeighbor(slaves[i+1]);
					
				}else{
					((TranspositionTableShadow) cacheShadow).setHostNeighbor(slaves[0]);
				}
				break;
			}*/
		}
	}

	public void loadTasks() throws RemoteException {
		
		if(slaveRemote == null){
			slaveRemote = sm.connectionRemoteSlave(Configs.SERVER_NAME, thisPort);
		}
		
		tasks = ((SlaveRemote)slaveRemote).getTasks();

		synchronizeTasks();
		
		net = slaveRemote.getNet();
		
		if(net == null){
			logger.warn("Neural Network not loaded.");
		}

		logger.debug("Tamanho da tarefa recebida: " + tasks.size());
	}
	
	private void synchronizeTasks(){
		
		System.out.println("Waiting tasks...");

		try {
			synchronized(((SlaveRemote)slaveRemote).getTasks()){
				while (((SlaveRemote)slaveRemote).getTasks().isEmpty()) {
					try {
						if(((SlaveRemote)slaveRemote).getStopJob()){
							break;
						}
						// waiting for master tasks
						((SlaveRemote)slaveRemote).getTasks().wait(100);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			tasks = ((SlaveRemote)slaveRemote).getTasks();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		//System.out.println("Tasks arrived.");
	}

	private synchronized boolean slaveManageMessages() throws ClassNotFoundException, NoSuchMethodException, SecurityException, RemoteException {

		//logger.warn("SlaveManageMessages");
		
		Boolean hasNew = false;
		Boolean wasUpdated = false;
		
		receivedMessages = ((SlaveRemote) slaveRemote).getMessagesReceived();
		tasks = ((SlaveRemote) slaveRemote).getTasks();
		
		if(receivedMessages == null){
			logger.warn("received messages is null");
			return false;
		}
		
		if (((SlaveRemote) slaveRemote).getStopSearch()) {
			for (MessageReceived msg : receivedMessages) {
				msg.setMessageStatus(MessageStatus.PROCESSED);
			}
		} else {
			
			synchronized (tasks) {
				
				Set<Key> keys = tasks.keySet();
				Iterator<Key> it = keys.iterator();
				
				while(it.hasNext()) {
					IState s = tasks.get(it.next());
				//}
				//for (int i = 0; i < tasks.size(); i++) {
					
					//IState s = tasks.get(i);

					Boolean exists = false;

					// Verifica se já existe e se existir atualiza os dados (neste
					// caso algum nó que foi processado anteriormente)
					synchronized (receivedMessages) {
						for (MessageReceived msg : receivedMessages) {
							if (msg.getStateReceived().getNode().equals(s.getNode())) {
								exists = true;

								crs.updateMessageReceived(s, msg);
								wasUpdated = true;
							}
						}

						if (!exists) {
							if(crs == null) {
								configSearch();
							}
								
							receivedMessages.add(crs.convert(s));
							hasNew = true;
						}
					}
				}
				
				tasks.clear();
				
				tasks.notifyAll();
				
				if(hasNew || wasUpdated){
					logger.info("Received tasks ready: " + receivedMessages.size());
					return true;
				}
			}
		}
		
		if(System.currentTimeMillis() - tWaitingTasks >= 120000){
			logger.warn("enviando notificação para master, não tenho tarefas!");
			tWaitingTasks = System.currentTimeMillis();
			((SlaveRemote) slaveRemote).waitingTasksNotification(thisHost + ":" + thisPort);
		}
		
		return false;
	}

	private void manageSlaveSearch() {
		
		//logger.warn("Manage Slave Search");
		
		while(waitUnits < waitIterations) {
			
			tWaitingTasks = System.currentTimeMillis();

			try {
				
				if(((SlaveRemote) slaveRemote).getRestartSlave() && finalized){
					
					logger.warn("Restarting slave.");
					
					waitUnits = 0;
					
					((SlaveRemote) slaveRemote).setRestartSlave(false);
					
					boolean hasTasks = slaveManageMessages();
					
					if(!hasTasks){
						restartSlave();
					}
				}
				
				if(((SlaveRemote) slaveRemote).getStopJob() && !finalized){
					
					logger.warn("Finalizing slave.");
					
					finalizeSlave();
				}
				
				logger.debug("stopSearch value: " + ((SlaveRemote) slaveRemote).getStopSearch() + " unblockMsgSent: " + unblockMsgSent);

				try{
					
					
					while (!((SlaveRemote) slaveRemote).getStopSearch()) {
						
						waitUnits = 0;
						
						unblockMsgSent = false;
						//finalized = false;
						
						if(!callGC){
							callGC = true;
						}
		
						boolean hasTasks = slaveManageMessages();
		
						if(hasTasks){
							tWaitingTasks = System.currentTimeMillis();
							runTasks();
						}
		
						synchronized (this) {
							wait(250);
						}
					}
				} catch (SlaveException e) {
					logger.warn("Total de nós avaliados: " + numNodesEvaluated);
					logger.warn("Aborting the tasks execution. >>> " + e.getMessage());
				}
				
				if(!unblockMsgSent){
				
					logger.warn("Limpando tudo!!!");
					
					if(receivedMessages != null && !receivedMessages.isEmpty()){
						
						for(MessageReceived mr: receivedMessages){
							mr.setStateReceived(null);
						}
						
						((SlaveRemote)slaveRemote).setMessagesReceived(null);
						receivedMessages = ((SlaveRemote)slaveRemote).getMessagesReceived();
					}
					
					
					cache = null;
					configTT();
					
					((SlaveRemote)slaveRemote).setTasks(null);
					tasks = ((SlaveRemote)slaveRemote).getTasks();
					 
					if(callGC){
						System.gc();
						callGC = false;
					}
					
					/*if(((SlaveRemote)slaveRemote).getMasterRemote().unblockSlave(thisHost + ":" + thisPort)){
						logger.warn("Chegou aqui para desbloquear!");
						unblockMsgSent = true;
					}*/
					
					unblockMsgSent = true;
					
					/*synchronized (this) {
						wait(500);
					}*/
				}
				
				synchronized (this) {
					//Thread.sleep(800);
					wait(700);
				}
				
				waitUnits++;
				if(waitUnits < waitIterations){
					logger.warn("The manager: " + waitUnits);
					//manageSlaveSearch();
				}else{
					logger.warn("The slave shutdown!!!");
					try {
						new FileManipulate().writeOutputFile(numFile + " " + thisHost + "\t" + numNodesEvaluated, String.valueOf(thisHost));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}

			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InterruptedException | RemoteException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void finalizeSlave(){
		
		tasks = null;
		maxThreadPoolSize = null;
		receivedMessages = null;
		crs = null;
		cache = null;
		useTableShadow = false;
		callGC = true;
		unblockMsgSent = false;
		
		finalized = true;
		
		try {
			((SlaveRemote) slaveRemote).setTasks(null);
			((SlaveRemote) slaveRemote).setMessagesReceived(null);
			((SlaveRemote) slaveRemote).setStopSearch(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		try {
			logger.warn("HOST:::: " + thisHost + ":" + thisPort);
			((SlaveRemote) slaveRemote).getMasterRemote().confirmMsgSlaveFinalized(thisHost + ":" + thisPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.gc();
		
		logger.warn("FinalizeSlave: Finished!");
		
	}
	
	
	
	public void startSlave(){
		
		logger.info("Starting slave!");
		
		try {
			
			unblockMsgSent = false;
			finalized = false;
			
			sab.loadTasks();
			
			sab.configSearch();

			sab.manageSlaveSearch();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void restartSlave(){
		
		logger.info("Restarting Slave");
		
		try {
			((SlaveRemote) slaveRemote).getMasterRemote().confirmMsgSlaveRestarted(thisHost + ":" + thisPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		((SlaveRemote) slaveRemote).setStopJob(false);
		((SlaveRemote) slaveRemote).setStopSearch(false);
		
		configTT();
		
		finalized = false;
		
		try {
			sab.loadTasks();
		
			sab.configSearch();
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void runTasks() throws SlaveException {

		//logger.warn("Starting runTasks. ReceivedMessages: " + receivedMessages.isEmpty());
		
		if(((SlaveRemote)slaveRemote).getStopSearch()){
			throw new SlaveException("Aborting the search.");
		}
		
		while (receivedMessages.isEmpty()) {
			try {
				this.wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		logger.warn("runTasks: Let's start!");
		
		List<IState> statesToMaster = new ArrayList<IState>();
		
		MessageReceived msg = getHighPriorityTask();
		
		if(msg == null){
			logger.warn("Não tem mensagens");
			
		}else {
			
			try {
				
				SlaveAlphaBetaExecution slbt = new SlaveAlphaBetaExecution(msg, searchDefinition, masterDepth, evaluation, sucessor,
						slaveRemote.getMasterRemote(), slaveRemote, slaveRemote.getRootMinMaxValue(), slaveRemote.getAlpha(), slaveRemote.getBeta(), statesToMaster, cache);
		
				while (msg != null) {
					
					if(((SlaveRemote)slaveRemote).getStopSearch()){
						throw new SlaveException("Aborting the search.");
						
					}
		
					if (!msg.getMessageStatus().equals(MessageStatus.PROCESSED)) {
		
						msg.setMessageStatus(MessageStatus.INPROCESS);
						
						slbt.setMsg(msg);;
						
						slbt.execution();
						
						numNodesEvaluated += slbt.getTotalNodesEvaluated();
						
					}
					
					msg = getHighPriorityTask();
				}
				
				logger.warn("Total de nós avaliados: " + numNodesEvaluated);

			} catch (RemoteException e) {
				logger.error("Occurred an error to pass the MasterRemote to Thread.");

				e.printStackTrace();
			}
			
		}

		//Enviando o restante de mensagens ao mestre (NÃO É DIVISOR PELO NÚMERO DO Configs.BATCH_SIZE)
		synchronized (statesToMaster) {
			
			//logger.warn("Enviando resto para master: " + statesToMaster.size());

			if( statesToMaster.size() > 0 ){
				
				logger.warn("Enviando tarefa para o master: " + statesToMaster.size());
				
				try {
					synchronized (slaveRemote.getMasterRemote()) {
						slaveRemote.getMasterRemote().receiveStates(statesToMaster);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				statesToMaster.clear();
			}
			
			statesToMaster.notifyAll();
		}
		
		if(((SlaveRemote)slaveRemote).getStopSearch()){
			
			throw new SlaveException("Aborting the search.");
		}
		
		logger.info("Finished runTasks");
		
	}

	
	private synchronized MessageReceived getHighPriorityTask() {
		
		MessageReceived highDepth = receivedMessages.get(0);
		MessageReceived highPriority = null;
		boolean updatedDepth = false, updatedPriority = false;
		
		for(int i = 0; i < receivedMessages.size(); i++){
			
			if(getAbortedTasks() != null && getAbortedTasks().contains(receivedMessages.get(i).getStateReceived().getKey())) {
				System.out.println("Tarefa transferida!!!");
				continue;
			}
			
			if(!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.INPROCESS) && 
			!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.PROCESSED) ){
				highPriority = receivedMessages.get(i);
				break;
			}
		}
		
		for(int i = 0; i < receivedMessages.size(); i++){
			
			if(getAbortedTasks() != null && getAbortedTasks().contains(receivedMessages.get(i).getStateReceived().getKey())) {
				System.out.println("Tarefa transferida!!!");
				continue;
			}
			
			if(highDepth.getStateReceived().getCurrentDepth() > receivedMessages.get(i).getStateReceived().getCurrentDepth()){
				highDepth = receivedMessages.get(i);
				updatedDepth = true;
			}
			
			if( highPriority != null && (!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.INPROCESS) && 
					!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.PROCESSED) ) &&
					highPriority.getPriority() < receivedMessages.get(i).getPriority() ){ 
				highPriority = receivedMessages.get(i);
				updatedPriority = true;
			}
		}
		
		if(!highDepth.getMessageStatus().equals(MessageStatus.INPROCESS) && 
				!highDepth.getMessageStatus().equals(MessageStatus.PROCESSED) && !highDepth.getMessageStatus().equals(MessageStatus.ABORTED) && 
				(updatedDepth || (highPriority != null && highDepth.getStateReceived().getCurrentDepth() < highPriority.getStateReceived().getCurrentDepth()))){
			return highDepth;
			
		}else if(updatedPriority){
			return highPriority;
		}
		
		//if(highPriority != null)
			//logger.warn("Prioridade: " + highPriority.getPriority() + " Location: " + highPriority.getStateReceived().getLocation());
		
		return highPriority;
		
	}
	
	
	private synchronized MessageReceived getHighPriorityTask2() {
		
		MessageReceived highDepth = receivedMessages.get(0);
		MessageReceived highPriority = null;
		boolean updatedDepth = false, updatePriority = true, updated = false;
		
		Collections.sort(receivedMessages);
		
		highPriority = receivedMessages.get(0);
		
		if(!highPriority.getMessageStatus().equals(MessageStatus.INPROCESS) && 
				!highPriority.getMessageStatus().equals(MessageStatus.PROCESSED) ){
			
			updatePriority = false;
					
		}
		
		if(updatePriority){
			for(int i = 1; i < receivedMessages.size(); i++) {
				
				if(getAbortedTasks() != null && getAbortedTasks().contains(receivedMessages.get(i).getStateReceived().getKey())) {
					System.out.println("Tarefa transferida!!!");
					continue;
				}
				
				if(!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.INPROCESS) && 
				!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.PROCESSED) ){
					highPriority = receivedMessages.get(i);
					updated = true;
					break;
				}
			}
		}
		
		if(updatePriority && !updated){
			highPriority = null;
		}
		
		/*
		for(int i = 1; i < receivedMessages.size(); i++){
			
			if(!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.INPROCESS) && 
			!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.PROCESSED) && 
			!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.ABORTED) ){
				highPriority = receivedMessages.get(i);
				break;
			}
		}
		
		for(int i = 0; i < receivedMessages.size(); i++){
			
			if
			
			if(highDepth.getStateReceived().getCurrentDepth() > receivedMessages.get(i).getStateReceived().getCurrentDepth()){
				highDepth = receivedMessages.get(i);
				updatedDepth = true;
			}
			
			if( highPriority != null && (!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.INPROCESS) && 
					!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.PROCESSED) && 
					!receivedMessages.get(i).getMessageStatus().equals(MessageStatus.ABORTED)) &&
					highPriority.getPriority() < receivedMessages.get(i).getPriority() ){ 
				highPriority = receivedMessages.get(i);
				updatedPriority = true;
			}
		}
		
		if(!highDepth.getMessageStatus().equals(MessageStatus.INPROCESS) && 
				!highDepth.getMessageStatus().equals(MessageStatus.PROCESSED) && !highDepth.getMessageStatus().equals(MessageStatus.ABORTED) && 
				(updatedDepth || (highPriority != null && highDepth.getStateReceived().getCurrentDepth() < highPriority.getStateReceived().getCurrentDepth()))){
			return highDepth;
			
		}else if(updatedPriority){
			return highPriority;
		}*/
		//if(highPriority != null)
		//	logger.warn("Prioridade: " + highPriority.getPriority() + " " + highPriority.getStateReceived().getKey().getKey32() + " " + highPriority.getStateReceived().getKey().getKey64());
		
		return highPriority;
		
	}
	

	public void setUnblockMsgSent(boolean unblockMsgSent) {
		this.unblockMsgSent = unblockMsgSent;
	}
	
	public void setNumFile(int numFile) {
		this.numFile = numFile;
	}

	public void setWaitIterations(int waitIterations) {
		this.waitIterations = waitIterations;
	}

	public List<Key> getAbortedTasks() {
		return ((SlaveRemote)slaveRemote).getAbortedTasks();
	}

	public static void main(String[] args) throws NoSuchMethodException {

		try {
			
			if(args.length == 0){
				throw new SlaveException("The port number does not informed.");
			}
			
			if(args.length == 1){
				throw new SlaveException("The file number does not informed.");
			}
			
			if(args.length == 2) {
				throw new SlaveException("The number of iterations to finalize does not informed.");
			}
			
			Integer port = Integer.parseInt(args[0].trim());
			Integer numFile = Integer.parseInt(args[1].trim());
			Integer numIterations = Integer.parseInt(args[2].trim());

			SlaveAlphaBeta sab = SlaveAlphaBeta.getInstance(port);
			
			sab.setNumFile(numFile);
			sab.setWaitIterations(numIterations);
			sab.startSlave();

		} catch ( SecurityException | SlaveException e) {
			e.printStackTrace();
		}
	}
}
