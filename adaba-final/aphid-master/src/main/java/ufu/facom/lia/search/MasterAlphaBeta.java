package ufu.facom.lia.search;

import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.cache.TranspositionTableBigTree;
import ufu.facom.lia.configs.Configs;
import ufu.facom.lia.configs.FeaturesSearchConfig;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.Location;
import ufu.facom.lia.interfaces.SearchDefinition;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.main.MasterManager;
import ufu.facom.lia.remote.IMasterRemote;
import ufu.facom.lia.remote.MasterRemote;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.SlaveStatus;
import ufu.facom.lia.search.interfaces.Status;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.AphidTable;
import ufu.facom.lia.structures.IAphidTable;
import ufu.facom.lia.structures.State;
import ufu.facom.lia.tests.MockExemploRita;

public class MasterAlphaBeta implements IAlphaBeta{

	private static final Logger logger = LogManager.getLogger(MasterAlphaBeta.class);
	
	private Integer HOST_ID = 1;

	private SystemConfigs sc; 

	private IAphidTable at;
	
	private Map<Key, INode> states;
	
	private Map<Key, INode> oldStates;
	
	private Map<Key, IState> edge;

	private MasterManager mm;

	private IMasterRemote masterRemote;

	private INode root;

	private Player player;

	private Integer masterDepth;

	private Integer maxDepth;
	
	private Integer currentMasterDepth;
	
	@SuppressWarnings("unused")
	private Integer granularity;
	
	private AlphaBetaFailSoft ab;
	
	private Evaluation evaluation;
	
	private Sucessor sucessor;

	private boolean canContinue;
	
	private INet net;
	
	private ICache cache;
	
	private static MasterAlphaBeta mab;
	
	private int passNumber;
	
	private boolean masterConfigured;
	
	private boolean firstSearch;
	
	private String searchDefinition;
	
	private long lastLoadBalance;
	
	private Integer maxTimeMove;
	
	private int numNodesEvaluted;
	
	private ExecutorService executorTreadPool;
	
	private int timeToSleep;//tempo que a thread principal vai dormir
	
	private int timeToLoadBalance;
	
	private int dk;//profundidade limite da primeira passagem pelo eldestbrother.
	
	private Integer depthReached;
	
	public static MasterAlphaBeta getInstance(Evaluation evaluation, Sucessor sucessor){
		
		if(mab == null){
			try {
				mab = new MasterAlphaBeta(evaluation, sucessor);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return mab;
		
	}

	private MasterAlphaBeta(Evaluation evaluation, Sucessor sucessor) throws RemoteException {

		mm = new MasterManager();

		masterRemote = new MasterRemote();

		sc = SystemConfigs.getInstance();
		
		this.canContinue = true;
		
		this.firstSearch = true;
		
		this.evaluation = evaluation;
		
		this.sucessor = sucessor;
		
		executorTreadPool = Executors.newFixedThreadPool(Integer.parseInt(sc.getConfig("max-thread-pool-size").trim()));
		
		timeToSleep = Integer.parseInt(sc.getConfig("time-to-sleep").trim());
		
		timeToLoadBalance = Integer.parseInt(sc.getConfig("time-to-load-balance"));
		
	}
	
	public void reStartingMaster(){
		
		if(!firstSearch){
			restartingSlaves();
		}
		
		firstSearch = false;
	}

	private void configMaster() throws RemoteException {
		
		boolean configAphidTable = false;
		
		if(at == null){
			at = new AphidTable();
			configAphidTable = true;
		}
		
		
		ab = new AlphaBetaFailSoft(evaluation, sucessor);

		edge = Collections.synchronizedMap(new HashMap<>());
		
		states = Collections.synchronizedMap(new HashMap<>());
		
		oldStates = new HashMap<>();

		setMasterDepth(Integer.parseInt(sc.getConfig("master-depth")));

		maxDepth = Integer.parseInt(sc.getConfig("max-depth"));
		
		depthReached = maxDepth;
		
		currentMasterDepth = getMasterDepth();
		
		searchDefinition = sc.getConfig("search-definition-master");
		
		dk = Integer.parseInt(sc.getConfig("k"));

		if(configAphidTable){
			configAphidTable();
		}
				
		ab.setMasterDepth(getMasterDepth());
		
		at.setTasks(edge);
		at.setId(HOST_ID);
		
		maxTimeMove = Integer.valueOf(SystemConfigs.getInstance().getConfig("maxTimeMove"));
		
	}

	private void configAphidTable() {

		try {
			
			String[] slaves = (sc.getConfig("slaves")).split(";");
				 
			at.setId(HOST_ID++);
			at.setHost(sc.getConfig("master"));	

			for(int i = 0; i < slaves.length; i++){

				AphidTable at = new AphidTable();
				at.setHost(slaves[i]);
				at.setId(HOST_ID++);
				at.setSlaveRemote(mm.doCallSlave(at.getHost()));
				
				if(i+1 < slaves.length){
					at.setHostRightNeighbor(slaves[i+1]);
					
				}else{
					at.setHostRightNeighbor(slaves[0]);
				}

				if (at.getSlaveRemote() != null) {
					logger.info("Recording the masterRemote on slave.");
					
					at.getSlaveRemote().registryMasterRemote(masterRemote);
				}
				
				this.at.getSlaves().add(at);
			}

			
		} catch (RemoteException e) {
			logger.error("Occur an erro on creating the APHID_TABLE_MASTER' slaves.");
		}
	}
	
	

	public float masterSearch(INode root, IMove move, Player player, float min, float max, Integer timeMove) throws RemoteException {
		
		logger.info("MasterSearch starting");
		
		float result = 0f;
		
		float previousResult = 0f;
		
		float previousAlpha = 0, previousBeta = 0, originalMin = 0, originalMax = 0;
		
		this.root = root;
		this.player = player;
		
		Long time = null;
		
		int totalSlavesUnblocked = 0;
		
		while(totalSlavesUnblocked != at.getSlaves().size()){
			totalSlavesUnblocked = 0;
			for(IAphidTable slave: at.getSlaves()){
				if(!slave.isBlocked()){
					totalSlavesUnblocked++;
				}else{
					if(slave.getSlaveRemote().unblockSlave()){
						slave.setBlocked(false);
					}
				}
			}
		}
		
		if(timeMove != null){
			time = System.currentTimeMillis() + timeMove;
		}

		logger.info("Slaves unblocked. Verifying the time of the search and whether the search will be staying in loop.");
		
		lastLoadBalance = System.currentTimeMillis();
		
		INode eldestBrother = null;
		
		Map<Key, INode> boardStates = new HashMap<Key, INode>();
		
		ab.setBoardStates(boardStates);
		
		mainSearch: while(!canFinalize()){
			
			try {
				
				if(timeMove != null && System.currentTimeMillis() > time){
					throw new IterativeDeepeningException("Time exceeded.");
				}
				
				while(!canContinue){
								
					
					if(timeMove != null && System.currentTimeMillis() > time){
						throw new IterativeDeepeningException("Time exceeded.");
					}
					
					try {
						synchronized (this) {
							wait(timeToSleep);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
				canContinue = false;
				
				passNumber++;
				
				//Utilizada para usar o guessedValue
				ab.setCurrentMasterDepth(currentMasterDepth);
				
				
				ab.setNodesEvaluated(0);
				
				states = new HashMap<>();
				edge.clear();
				
				for(IAphidTable slave: at.getSlaves()) {
					edge.putAll(slave.getSentTasks());
				}
				
				Set<Key> ks = edge.keySet();
				Iterator<Key> itks = ks.iterator();
				while(itks.hasNext()) {
					IState sTemp = edge.get(itks.next());
					sTemp.setMasterVisited(false);
					states.put(sTemp.getKey(), sTemp.getNode());
					
				}
				
				ab.setStatesEvaluated(states);
				
				if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST.getValue()) || cache == null){
					result = ab.alphaBeta(getRoot(), move, getMasterDepth(), min, max, player);
					
				}else if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST_WITH_TT.getValue())){
					result = ab.alphaBetaTT(getRoot(), move, getMasterDepth(), min, max, player, cache);
					
				}else{
					logger.error("The master does not executes search in iterative deepening inside of the base loop.");
				}
				
				if(masterDepth == maxDepth){
					System.out.println("###" + masterDepth + " " + maxDepth);
					logger.warn("aki ó");
					break mainSearch;
				}
				
				if(ab.getBoardStates().isEmpty()){
					//logger.warn("The search will be finalized because there is no work to slaves.");
					break mainSearch;
				}
				
				//Retirando o estado de visitação da borda
				Set<Key> keys = states.keySet();
				Iterator<Key> it = keys.iterator();
				while(it.hasNext()) {
					INode nodeExisting = states.get(it.next());
					nodeExisting.setVisited(false);
				}
				
				Map<Key, INode> news = new HashMap<>();
				
				keys = boardStates.keySet();
				it = keys.iterator();
				while(it.hasNext()) {
					INode boardState = boardStates.get(it.next());
					
					INode boardStateExisting = states.get(boardState.getKey());
					if(boardStateExisting == null) {
						news.put(boardState.getKey(), boardState);
					}else {
						boardStateExisting.setVisited(true);
					}
					
					IState stateTemp = edge.get(boardState.getKey());
					if(stateTemp != null) {
						stateTemp.setMasterVisited(true);
					}
				}
					
				if(currentMasterDepth < maxDepth){
					currentMasterDepth += Configs.ITERATIVE_DEEPENING;
				}
				
				float eldestBrotherEvaluation = 0f;
				
				
				//Atualiza localização do nó na borda, pois ele pode ter sido alterado
				if(eldestBrother ==  null || (eldestBrother != null && !eldestBrother.equals(ab.getEldestBrother()))) {
					
					if(eldestBrother != null) {
						eldestBrother.setLocation(Location.OLD_PV.getValue());
					}
					
					eldestBrother = ab.getEldestBrother();
				}	
				
				float diff = 0;
				if(eldestBrother != null){
					
					if(eldestBrother.getDepth() < maxDepth){

						logger.info("Oldest Brother: " + eldestBrother.getKey().getKey32());					
						
						IMove m = new Move();
						
						float minT = Float.valueOf(SystemConfigs.getInstance().getConfig("lower_bound"));
						float maxT = Float.valueOf(SystemConfigs.getInstance().getConfig("upper_bound"));
						
						AlphaBetaFailSoft ab2 = new AlphaBetaFailSoft(evaluation, sucessor);
						
						previousAlpha = minT + result;
						previousBeta = maxT + result;
						
						int profundidade = 0;
						
						if(maxDepth <= dk) {
							profundidade = (maxDepth - masterDepth);
						}else {
							profundidade = dk - masterDepth;
							dk += Configs.ITERATIVE_DEEPENING;
						}
						
						
						ab2.setNodesEvaluated(0);
						eldestBrotherEvaluation = ab2.alphaBeta(eldestBrother, m, profundidade, previousAlpha, previousBeta, ab.otherPlayer(eldestBrother.getPlayer()));
						//eldestBrotherEvaluation = ab.alphaBetaTT(eldestBrother, m, profundidade, previousAlpha, previousBeta, ab.otherPlayer(eldestBrother.getPlayer()), cache);
						numNodesEvaluted += ab2.getNodesEvaluated();
						
						eldestBrother.setMove(m);
						eldestBrother.setDepth(profundidade + masterDepth);
					
					}else {
						eldestBrotherEvaluation = eldestBrother.getEvaluation();
					}
					
					diff = Math.abs(result - eldestBrotherEvaluation);
					
					
					
				}
				
				
				originalMin = -diff + result;
				originalMax = diff + result;
				
				//Ajustando a borda da busca
				Set<Key> keys1 = news.keySet();
				Iterator<Key> it1 = keys1.iterator();
				while(it1.hasNext()) {
					
					INode s = news.get(it1.next());
						
					IState state = new State();

					state.setAlpha(originalMin);//janela do irmão mais velho
					state.setBeta(originalMax);//janela do irmão mais velho
					state.setMaxDepth(maxDepth);
					state.setMasterDepth(masterDepth);
					state.setSlaveStatus(SlaveStatus.SEND);
					state.setNode(s);
					state.setKey(s.getKey());
					state.setDiff(result);
					state.setLocation(s.getLocation());
					
					if(s.getDepth() >= maxDepth) {
						state.setStatus(Status.CERTAIN);
						state.setCurrentDepth(s.getDepth());
					}else {
						state.setStatus(Status.UNCERTAIN);
						state.setCurrentDepth(s.getDepth() + Configs.ITERATIVE_DEEPENING);
					}

					edge.put(state.getKey(), state);
						
				}
				
				tasksPartition();
				
				for (IAphidTable slave : at.getSlaves()) {
	
					if (!slave.getTasks().isEmpty() && slave.getSlaveRemote() != null) {
						
						Long currentTime = System.currentTimeMillis();
						
						keys = slave.getTasks().keySet();
						it = keys.iterator();
						while(it.hasNext()) {
							IState st = slave.getTasks().get(it.next());
							st.setSlaveStatus(SlaveStatus.SENT);
							st.setTime(currentTime);
						}
						
						boolean success = true;
						
						try{
							
							// recording the net
							if(!slave.getNeuralNetworkSent()){
								slave.getSlaveRemote().setNet(getNet());
								slave.setNeuralNetworkSent(true);
							}
							
							// sending the root value
							if(result != previousResult){
								//logger.warn("ROOT VALUE: Result: " + result + " previous: " + previousResult);
								//slave.getSlaveRemote().registerRootMinimaxValue(result);
								slave.getSlaveRemote().registerNewWindow(originalMin, originalMax);
							}
							
							// send task to slave
							slave.getSentTasks().putAll(slave.getTasks());
							slave.getSlaveRemote().registryEntries(slave.getTasks());

							//logger.warn("Tasks sent to " + slave.getHost() + ", size: " + slave.getTasks().size());
							
						}catch(ConnectIOException e){
							
							logger.error("slave: " + slave.getHost() + " apresentou problema..." + e.getMessage());
							
							success = false;
							
							e.printStackTrace();
						}
						
						//clear the tasks already sent by a slave
						//clearTasksAlreadySent(slave, success);
						slave.setTasks(new HashMap<>());

					} else if(slave.getSlaveRemote() == null) {
						// TODO: teste it here!!! Colocar os tratamentos de exceções para o
						// caso do servidor não ter subido
						// TODO: REALOCAR TAREFAS PQ SLAVE ESTÁ FORA!!!
						logger.warn("[:::ATENÇÃO::: ] Service: " + slave.getHost() + " is unavaliable!");
					} else{
						
						logger.info("There is no tasks to send.");
						
						//sending the root value
						if(result != previousResult){
							//logger.warn("ROOT VALUE2: Result: " + result + " previous: " + previousResult);
							//slave.getSlaveRemote().registerRootMinimaxValue(result);
							slave.getSlaveRemote().registerNewWindow(originalMin, originalMax);
						}
					}
				}
				
				previousResult = result;
				
				//Balanceamento de carga feito no final da passagem
				/*if(passNumber > 1 && (System.currentTimeMillis() - lastLoadBalance >= timeToLoadBalance)){
					loadBalance();
					lastLoadBalance = System.currentTimeMillis();
				}*/
				
	
			} catch (SearchException | TranspositionTableException e) {
				e.printStackTrace();
				
			} catch (IterativeDeepeningException e) {
				
				edge.clear();
				
				for(IAphidTable slave: at.getSlaves()) {
					edge.putAll(slave.getSentTasks());
				}
				
				int higherDepth = 0;
				
				Set<Key> ks = edge.keySet();
				Iterator<Key> itks = ks.iterator();
				while(itks.hasNext()) {
					IState sTemp = edge.get(itks.next());
					if(sTemp.getCurrentDepth() > higherDepth) {
						higherDepth = sTemp.getCurrentDepth();
					}
				}
				
				depthReached = higherDepth;
				
				logger.warn("Time reached: the search will be finalized!!! P: " + depthReached);
				
				break;
				
			}
		}

		//Sending message to slaves to finalize the activities
		for (IAphidTable slave : at.getSlaves()) {
			logger.info("Calling slave: " + slave.getHost());
			slave.getSlaveRemote().canFinalizeSearch();
			slave.setBlocked(true);
		}
		
		logger.info("FINALIZED!!!");
		
		try {
			if(getRoot().getType() != Type.LOSER.getValue()){
				if(cache == null) {
					result = ab.alphaBeta(getRoot(), move, getMasterDepth(), min, max, player);
				}else {
					result = ab.alphaBetaTT(getRoot(), move, getMasterDepth(), min, max, player, cache);
				}
				logger.info("Número de nós avaliados: " + ab.getNodesEvaluated());
				this.numNodesEvaluted = ab.getNodesEvaluated();
				logger.info("[MASTER] FINAL RESULT: " + result);
			}
		} catch (SearchException e) {
			e.printStackTrace();
		} catch (TranspositionTableException e) {
			e.printStackTrace();
		} catch (IterativeDeepeningException e) {
			e.printStackTrace();
		}
		
		clearMaster();
		
		return result;

	}
	
	public boolean unblockSlave(String host){
		
		logger.warn("Unblocking slave: " + host);
		
		IAphidTable slave = at.getSlave(host);
		slave.setBlocked(false);
		
		int total = 0;
		for(IAphidTable s: at.getSlaves()){
			if(!s.isBlocked()){
				total++;
			}
		}
		
		if(total == at.getSlaves().size()){
			canContinue = true;
		}
		
		return true;
	}
	
	public void handleMessageReceived(List<IState> states) throws RemoteException{
		
		String host = states.get(0).getOwner();
		
		HandleTasks ht = new HandleTasks();
		ht.setHost(host);
		ht.setSlave(at.getSlave(host));
		ht.setStatesReceived(states);
		ht.setMaxDepth(maxDepth);
		
		executorTreadPool.submit(ht);
	
		canContinue = true;
		
	}
	
	public void loadBalance() throws RemoteException{
		
		List<InfoToBalance> finalized = new ArrayList<>();
		List<InfoToBalance> notFinalized = new ArrayList<>();
		
		for(IAphidTable slave: at.getSlaves()){
			
			int totalCertain = 0;
			
			Set<Key> keys = slave.getSentTasks().keySet();
			Iterator<Key> it = keys.iterator();
			while(it.hasNext()) {
				IState state = slave.getSentTasks().get(it.next());
				if(state.getStatus().equals(Status.CERTAIN)){
					totalCertain++;
				}
			}
			
			if(totalCertain == slave.getSentTasks().size()){
				finalized.add(new InfoToBalance(slave.getHost(), slave.getSentTasks().size(), totalCertain));
			}else{
				notFinalized.add(new InfoToBalance(slave.getHost(), slave.getSentTasks().size(), totalCertain));
			}
		}
		
		Collections.sort(notFinalized);
		
		int idxFinalized = finalized.size();
			
		for(InfoToBalance itb: notFinalized) {
		
			if (idxFinalized > 0) {
			
				int totalToSend = (int) (itb.getTotalNotFinalizedTasks() * 0.1);
				totalToSend = (int) (totalToSend / idxFinalized);
				
				
				IAphidTable slaveFinalized = at.getSlave(finalized.get(idxFinalized-1).getHost());
				IAphidTable slaveNotFinalized = at.getSlave(itb.getHost());
				
				
				Map<Key, IState> toSend = new HashMap<>();
				List<Key> keysToAbort = new ArrayList<>();
				
				int totalToSendTemp = totalToSend;
				
				Set<Key> k = slaveNotFinalized.getSentTasks().keySet();
				Iterator<Key> i = k.iterator();
				while(i.hasNext() && totalToSendTemp > 0) {
					IState s = slaveNotFinalized.getSentTasks().get(i.next());
					if(s.getStatus() != Status.CERTAIN && s.getSlaveStatus() != SlaveStatus.TRANSFERRED) {
						//MUDA O STATUS DO ESTADO QUE PERTENCIA AO ESCRAVO QUE NÃO FINALIZOU AS ATIVIDADES
						s.setSlaveStatus(SlaveStatus.TRANSFERRED);
						s.setOwner(slaveFinalized.getHost());
						toSend.put(s.getKey(), s);
						keysToAbort.add(s.getKey());
						totalToSendTemp--;
					}
				}
				
				slaveFinalized.getSlaveRemote().registryEntries(toSend);
				
				slaveFinalized.getSentTasks().putAll(toSend);
				
				slaveNotFinalized.getSlaveRemote().abortMessage(keysToAbort);
				
				idxFinalized--;
				
			}
		}
	}
	
	public void loadBalance2() throws RemoteException{
		
		List<InfoToBalance> finalized = new ArrayList<>();
		List<InfoToBalance> notFinalized = new ArrayList<>();
		
		for(IAphidTable slave: at.getSlaves()){
			
			int totalCertain = 0;
			
			Set<Key> keys = slave.getSentTasks().keySet();
			Iterator<Key> it = keys.iterator();
			while(it.hasNext()) {
				IState state = slave.getSentTasks().get(it.next());
				if(state.getStatus().equals(Status.CERTAIN)){
					totalCertain++;
				}
			}
			
			if(totalCertain == slave.getSentTasks().size()){
				finalized.add(new InfoToBalance(slave.getHost(), slave.getSentTasks().size(), totalCertain));
			}else{
				notFinalized.add(new InfoToBalance(slave.getHost(), slave.getSentTasks().size(), totalCertain));
			}
		}
		
		Collections.sort(notFinalized);
		
		int indexFinalized = 0;
		int indexNotFinalized = 0;
		
		boolean balancing = false;
				
		while(indexFinalized < finalized.size() && indexNotFinalized < notFinalized.size()){
			
			if(notFinalized.get(indexNotFinalized).getTotalNotFinalizedTasks() > 0){
				
				IAphidTable slaveFinalized = at.getSlave(finalized.get(indexFinalized).getHost());
				IAphidTable slaveNotFinalized = at.getSlave(notFinalized.get(indexNotFinalized).getHost());
				
				IState taskExchanged = slaveNotFinalized.getSentTasks().get(slaveNotFinalized.getSentTasks().size() -1);
				
				int qtdeNotFinalized = 0;
				
				if(taskExchanged != null && taskExchanged.getStatus().equals(Status.CERTAIN)){
					for(int i = slaveNotFinalized.getSentTasks().size(); i > 0; i--){
						//TODO: A LINHA SEGUINTE NÃO FUNCIONA
						IState task = slaveNotFinalized.getSentTasks().get(i - 1);
						if(task.getStatus().equals(Status.UNCERTAIN)){
							
							qtdeNotFinalized++;
							
							if(qtdeNotFinalized == 1){
								taskExchanged = task;
							}else{
								break;
							}
						}
					}
				}
				
				if(qtdeNotFinalized > 1){
					boolean abort = false;
					
					if(abort){
						slaveNotFinalized.getSentTasks().remove(taskExchanged);
						taskExchanged.setOwner(slaveFinalized.getHost());
						slaveFinalized.getTasks().put(taskExchanged.getKey(), taskExchanged);
						
						// send task to slave
						slaveFinalized.getSlaveRemote().registryEntries(slaveFinalized.getTasks());
						logger.warn("[LOADING BALANCING:] Tasks sent to: " + slaveFinalized.getHost());
						clearTasksAlreadySent(slaveFinalized, true);	
					}
					
					logger.warn("Resultado do loadBalance: " + abort);
				}
				
				
				indexFinalized++;
				indexNotFinalized++;
				
				balancing = true;
			}
		}
		
		finalized = null;
		notFinalized = null;
		
		if(balancing){
			canContinue = true;
		}
	}
	
	private void clearMaster(){
		
		logger.info("Veio limpar o master!!!");
		
		oldStates = new HashMap<>();
		oldStates.putAll(states);
		
		passNumber = 0;
		
		states = new HashMap<>();
		ab.setBoardStates(states);
		
		edge.clear();
		
		//Limpando slaves
		for(IAphidTable at : at.getSlaves()){
			at.setTasks(null);
			at.setSentTasks(null);
			at.setNeuralNetworkSent(false);
		}
		
		canContinue = true;
		
	}

	private ArrayList<IState> notFinalized;
	
	private Boolean canFinalize() throws RemoteException{
		
		edge.clear();
		
		notFinalized = new ArrayList<>();
		
		int total = 0;
		
		for(IAphidTable slave: at.getSlaves()) {
			edge.putAll(slave.getSentTasks());
		}
		
		Set<Key> keys = edge.keySet();
		Iterator<Key> it = keys.iterator();
		while(it.hasNext()) {
			IState s = edge.get(it.next());
			if (s.getStatus() != null && s.getStatus().equals(Status.CERTAIN) || (s.getSlaveStatus() != null && s.getSlaveStatus().equals(SlaveStatus.TRANSFERRED))) {
				total++;
			}else if(s.getMasterVisited()){
				notFinalized.add(s);
			}
			
		}
		
		//logger.warn("total: " + total + " tanto de estados: " + edge.size() + " " + (notFinalized.isEmpty()));
		
		if(!edge.isEmpty() && notFinalized.isEmpty() && total > 0) {
			return true;
		}
		
		return false;
		
	}

	/**
	 * Stores the tasks sent into 'sentTasks' list and clears the principal task list.
	 */
	private void clearTasksAlreadySent(IAphidTable slave, boolean success){

		if(success){
			//logger.warn("veio atualizar tarefas dos escravos para sent aki 7");
			// recording in memory of task already sent to slave
			slave.getSentTasks().putAll(slave.getTasks());
		}
		// clear the tasks of the principal list
		slave.setTasks(new HashMap<>());
	}
	
	private void tasksPartition() throws RemoteException, SearchException {
		
		if(at.getSlaves().size() == 0){
			throw new SearchException("Slaves not recorded!");
		}

		int aux = 0;
		
		Set<Key> keys = at.getTasks().keySet();
		Iterator<Key> it = keys.iterator();
		while(it.hasNext()) {
			IState task = at.getTasks().get(it.next());
			
			if(task.getOwner() == null && !task.getStatus().equals(Status.CERTAIN) 
					&& task.getSlaveStatus().equals(SlaveStatus.SEND)){
				//Setando o proprietário da tarefa
				task.setOwner(at.getSlaves().get(aux).getHost());
				at.getSlaves().get(aux).getTasks().put(task.getKey(), task);
			}
			
			aux++;
			
			if(aux == at.getSlaves().size()){
				aux = 0;
			}
			
		}
			
	}
	
	private void tasksPartition2() throws RemoteException, SearchException {
		
		if(at.getSlaves().size() == 0){
			throw new SearchException("Slaves not recorded!");
		}

		int aux = 0;
		
		Set<Key> keys = at.getTasks().keySet();
		Iterator<Key> it = keys.iterator();
		while(it.hasNext()) {
			IState task = at.getTasks().get(it.next());
			
			if(task.getOwner() == null && !task.getStatus().equals(Status.CERTAIN) 
					&& task.getSlaveStatus().equals(SlaveStatus.SEND)){
				
				//Setando o proprietário da tarefa
				task.setOwner(at.getSlaves().get(aux).getHost());
				at.getSlaves().get(aux).getTasks().put(task.getKey(), task);
								
				//criar um mapa com o total de nós em cada localização
				short location = task.getLocation();
				Integer qtd = at.getControlPriorities().get(location);
				
				if(qtd==null){
					qtd = 1;
				}else{
					qtd++;
				}
				
				at.getControlPriorities().put(location, qtd);
			}
			
			aux++;
			
			if(aux == at.getSlaves().size()){
				aux = 0;
			}
			
		}
		
		Set<Short> ks = at.getControlPriorities().keySet();
		Iterator<Short> is = ks.iterator();
		while(is.hasNext()){
			Short p = is.next();
			
			System.out.println("Prioridade " + p + " total de nós: " + at.getControlPriorities().get(p));
		}
		
			
	}
	
	
	public void checkFinalizedSlave(String host){
		
		//logger.warn("Veio aki para atualizar host!!!");
		
		synchronized(at.getSlaves()){
			
			logger.warn(host);
			
			IAphidTable slave = at.getSlave(host);
			if(slave != null)
				slave.setFinalized(true);
			
			if(at.getSlaves() != null)
				at.getSlaves().notifyAll();
		}
		
		logger.warn("Slaves finalizaram");
		
	}
	
	@Override
	public boolean finalizeSearch() {
		
		logger.warn("Enviando mensagem para slaves finalizarem.");
		
		try{
			for(IAphidTable slave: at.getSlaves()){
				slave.getSlaveRemote().finalizeJob();
			}
			
			int totalFinalized = 0;

			
			while(totalFinalized != at.getSlaves().size()){
				totalFinalized = 0;
				for(IAphidTable slave: at.getSlaves()){
					if(slave.isFinalized()){
						totalFinalized++;
					}
				}
				synchronized(at.getSlaves()){
					at.getSlaves().wait(100);
				}
			}

		}catch(RemoteException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void checkRestartSlave(String host){
		
		logger.warn("Veio aki para reiniciar host!!!");
		
		synchronized(at.getSlaves()){
			IAphidTable slave = at.getSlave(host);
			slave.setFinalized(false);
			slave.setBlocked(false);
			
			at.getSlaves().notifyAll();
		}
		
		logger.warn("Slaves Reiniciaram");
		
	}
	
	public void restartingSlaves(){
		
		logger.warn("Enviando mensagem para slaves reiniciarem");
		
		//Sending message to slaves to finalize the activities
		for (IAphidTable slave : at.getSlaves()) {
			try {
				logger.info("chamando slave: " + at.getHost());
				slave.getSlaveRemote().restartSlave();
				
				//slave.setBlocked(false);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		int totalRestarted = 0;

		
		while(totalRestarted != at.getSlaves().size()){
			totalRestarted = 0;
			for(IAphidTable slave: at.getSlaves()){
				if(!slave.isFinalized()){
					totalRestarted++;
				}
			}
			synchronized(at.getSlaves()){
				try {
					at.getSlaves().wait(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void slaveNotification(String host){
		
		logger.warn("Master notified by: " + host);
		
		IAphidTable slave = at.getSlave(host);
		
		Map<Key, IState> notFinalized = new HashMap<>();
		
		Set<Key> keys = slave.getSentTasks().keySet();
		Iterator<Key> it = keys.iterator();
		while(it.hasNext()) {
			IState task = slave.getSentTasks().get(it.next());
			if(!task.getStatus().equals(Status.CERTAIN)){
				notFinalized.put(task.getKey(), task);
			}
		}
		
		try {
			if(!notFinalized.isEmpty()){
				logger.warn("Notifying the slave: " + host + " about tasks not finalized.");
				slave.getSlaveRemote().registryEntries(notFinalized);
			}else{
				logger.warn("Todas as tarefas foram finalizadas.");
				
				synchronized (edge) {
					keys = edge.keySet();
					it = keys.iterator();
					while(it.hasNext()) {
						IState e = edge.get(it.next());
						if(e.getOwner() != null && e.getOwner().equals(host)){
							e.setStatus(Status.CERTAIN);
						}
					}
					/*for(IState e: edge){
						if(e.getOwner() != null && e.getOwner().equals(host)){
							e.setStatus(Status.CERTAIN);
						}
					}*/
				}
				
				//loadBalance();
			}
		} catch (RemoteException e) {
			logger.error("Error sending tasks again to slave: " + host);
			e.printStackTrace();
		}
	}
	
	private float alphaBetaIDTimeLimited(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID, Integer timeMove) throws SearchException, TranspositionTableException {

		float result = 0f;

		Long time = System.currentTimeMillis() + timeMove;
		
		logger.debug("Tempo final total da busca: " + time);

		try {
			
			int i = 0;
			
			configMaster();
			
			((MasterRemote) getMasterRemote()).setMasterAlphaBeta(mab);
			
			while(maxDepth <= depth){
				
				Long time2 = System.currentTimeMillis();
				
				logger.debug("Tempo inicial da busca: " + time2);

				if (time2 > time) {
					throw new SearchException("Tempo ID atingido");
				}
				
				logger.warn("########## ITERAÇÃO " + i + "######### | p=" + maxDepth);
				
				i++;
				
				//Reinicia estrutas utilizadas pela busca
				edge = Collections.synchronizedMap(new HashMap<Key, IState>());
				
				states = Collections.synchronizedMap(new HashMap<Key, INode>());

				ab.setMasterDepth(getMasterDepth());
				ab.setBoardStates(states);
				
				at.setTasks(edge);
				
				for(IAphidTable slave: at.getSlaves()){
					slave.setSentTasks(null);
				}
				
				result = masterSearch(node, move, player, min, max, timeMove);
				
				maxDepth += Configs.ITERATIVE_DEEPENING;
				
				logger.debug("VAMOS COMEÇAR AGAIN!!!");

			}

		} catch (SearchException | RemoteException e) {
			logger.trace(e.getMessage());
		}

		return result;

	}
		
	@Override
	public float alphaBetaIDTimeLimited(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID, Integer timeMove, INet net, ICache cache) throws SearchException, TranspositionTableException {
		
		this.setNet(net);
		this.setCache(cache);
		
		return alphaBetaIDTimeLimited(node, move, depth, min, max, player, incrementalID, timeMove);
	}

	@Override
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player)
			throws SearchException {
		
		//logger.warn("alphabeta");
		
		float result = 0f;
		
		try {
			
			if(!masterConfigured){
				configMaster();
				masterConfigured = true;
			}
			
			if(((MasterRemote) getMasterRemote()).getMasterAlphaBeta() == null){
				((MasterRemote) getMasterRemote()).setMasterAlphaBeta(mab);
			}
			
			for(IAphidTable slave: at.getSlaves()){
				slave.setBlocked(false);
			}
			
			//Reinicia estrutas utilizadas pela busca
			edge = Collections.synchronizedMap(new HashMap<Key, IState>());
			
			states = Collections.synchronizedMap(new HashMap<Key, INode>());
			
			ab.setMasterDepth(getMasterDepth());
			ab.setBoardStates(states);
			
			at.setTasks(edge);
			
			for(IAphidTable slave: at.getSlaves()){
				slave.setSentTasks(null);
			}
			
			canContinue = true;
			
			result = masterSearch(node, move, player, min, max, maxTimeMove);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player, INet net)
			throws SearchException {
		
		this.setNet(net);
		
		return alphaBeta(node, move, depth, min, max, player);
	}
	
	
	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player, INet net, ICache cache)
			throws SearchException {
		
		this.setNet(net);
		this.setCache(cache);
		
		return alphaBeta(node, move, depth, min, max, player);
	}
	
	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player,
			ICache cache) throws SearchException {

		this.setCache(cache);
		
		return alphaBeta(node, move, depth, min, max, player);
	}
	
	public IMasterRemote getMasterRemote() {
		return this.masterRemote;
	}

	public INode getRoot() {
		return root;
	}

	public void setRoot(INode root) {
		this.root = root;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getMasterDepth() {
		return masterDepth;
	}

	public void setMasterDepth(Integer masterDepth) {
		this.masterDepth = masterDepth;
	}

	public INet getNet() {
		return net;
	}

	public void setNet(INet net) {
		this.net = net;
	}

	public ICache getCache() {
		return cache;
	}

	public void setCache(ICache cache) {
		this.cache = cache;
	}

	@Override
	public Evaluation getEvaluation() {
		return this.evaluation;
	}

	@Override
	public Sucessor getSucessor() {
		return this.sucessor;
	}
	
	public Integer getDepthReached() {
		return depthReached;
	}

	public static void main(String[] args) throws SearchException, RemoteException {

		MockExemploRita mck = new MockExemploRita();
		INode n = mck.createMockSimple();
		
		FeaturesSearchConfig fsc = FeaturesSearchConfig.getInstance();
		
		Evaluation e = fsc.getEvaluation();
		
		Sucessor s = fsc.getSucessor();

		MasterAlphaBeta ab = MasterAlphaBeta.getInstance(e, s);
		
		ICache cache = new TranspositionTableBigTree(null);
		
		ab.setCache(cache);
		ab.setNet(fsc.getNetTest());
		ab.configMaster();

		((MasterRemote) ab.getMasterRemote()).setMasterAlphaBeta(ab);
		
		try {
			ab.alphaBetaIDTimeLimited(n, null, 12, (Float.MAX_VALUE * (-1)), Float.MAX_VALUE, Player.BLACK, Configs.INIT_HOST_ID, 60000, fsc.getNetTest(), cache);
		} catch (TranspositionTableException e1) {
			e1.printStackTrace();
		}
		
		cache.clear();
		
	}

	@Override
	public Integer getNodesEvaluated() {
		return numNodesEvaluted;
	}

	@Override
	public IMove getTheBestMove() {
		return ab.getTheBestMove();
	}
}

/**
 * Classe interna para auxiliar no método loadBalancing
 */
class InfoToBalance implements Comparable<InfoToBalance>{
	
	private String host;
	private Integer totalTasks;
	private Integer totalFinalizedTasks;
	private Integer totalNotFinalizedTasks;
	
	InfoToBalance(String host, Integer totalTasks, Integer totalFinalizedTasks){
		
		this.host = host;
		this.totalTasks = totalTasks;
		this.totalFinalizedTasks = totalFinalizedTasks;
		this.totalNotFinalizedTasks = totalTasks - totalFinalizedTasks;
		
	}
	
	public String getHost() {
		return host;
	}
	
	public Integer getTotalTasks() {
		return totalTasks;
	}
	
	public Integer getTotalFinalizedTasks() {
		return totalFinalizedTasks;
	}
	
	public void setTotalFinalizedTasks(Integer totalFinalizedTasks) {
		this.totalFinalizedTasks = totalFinalizedTasks;
	}
	
	public Integer getTotalNotFinalizedTasks() {
		return totalNotFinalizedTasks;
	}
	
	
		
	@Override
	public int compareTo(InfoToBalance o) {
		
		if(this.totalNotFinalizedTasks < o.totalNotFinalizedTasks){
			return 1;
		}
		
		if(this.totalNotFinalizedTasks > o.totalNotFinalizedTasks){
			return -1;
		}
		
		return 0;
	}
}
