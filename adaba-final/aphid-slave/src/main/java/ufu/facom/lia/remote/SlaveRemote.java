package ufu.facom.lia.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.cache.Entry;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.interfaces.cache.ICacheShadow;
import ufu.facom.lia.remote.utils.Utils;
import ufu.facom.lia.search.SlaveAlphaBeta;

public class SlaveRemote extends UnicastRemoteObject implements ISlaveRemote{
	
	private static final Logger logger = LogManager.getLogger(SlaveRemote.class);

	private static final long serialVersionUID = 1L;
	
	private Map<Key, IState> tasks; 
	
	private List<MessageReceived> messagesReceived;
	
	private List<Key> abortedTasks;
	
	private IMasterRemote masterRemote;
	
	private Boolean stopSearch;
	
	private boolean stopJob;
	
	private boolean restartSlave;
	
	private Boolean waitingClearSearch;
	
	private INet net;
	
	private float alpha;
	
	private float beta;
	
	private float rootMinMaxValue;
	
	private ICache localCache;
	
	public SlaveRemote() throws RemoteException {
		waitingClearSearch = false;
	}

	@Override
	public void registerEntry(IState task) throws RemoteException {
		getTasks().put( task.getKey(), task );
		//System.out.println("Task added.");
	}
	
	@Override
	public void registryEntries(Map<Key, IState> tasks) throws RemoteException{
		
		/*if(tasks!= null){
			logger.warn("Tamanho da tarefa rebida: " + tasks.size());
		}*/
		
		getTasks().putAll(tasks);
	
		setStopSearch(false);
	}

/*	@Override
	public void writeMasterRemote(IState state) throws RemoteException {
		
		if(masterRemote != null){
			masterRemote.receiveState(state);
			
		//	System.out.println("Message sent to MasterRemote!!! STATE: " + state.getName());
		}
	}*/

	@Override
	public void registryMasterRemote(IMasterRemote masterRemote) throws RemoteException {
		
		if(masterRemote != null){
			this.masterRemote = masterRemote;
			
		//	System.out.println("MasterRemote registered!");
			
		}/*else{
			System.out.println("MasterRemote unregistered!");
		}*/
	}
	
	@Override
	public void canFinalizeSearch() throws RemoteException {
		//System.out.println("Finalizando a busca!!!");
		stopSearch = true;
		SlaveAlphaBeta.getInstance().setUnblockMsgSent(false);
	}
	
	@Override
	public boolean finalizeJob() throws RemoteException {
		//System.out.println("Encerrando Slave!!!");
		this.stopJob = true;
		
		return true;
	}
	
	@Override
	public void registerNewWindow(float alpha, float beta) throws RemoteException{
		//System.out.println(":::Window Received [" + alpha + ", " + beta + "] :::");
		this.alpha = alpha;
		this.beta = beta;
	}
	
	@Override
	public void registerRootMinimaxValue(float value) throws RemoteException {
		//System.out.println("ROOT VALUE received! " + value);
		this.rootMinMaxValue = value;
	}

	@Override
	public void setTasks(Map<Key, IState> tasks) throws RemoteException{
		this.tasks = tasks;
	}

	@Override
	public Map<Key, IState> getTasks() throws RemoteException{
		if(tasks == null){
			tasks = Collections.synchronizedMap(new HashMap<>());
		}
		return tasks;
	}
	
	@Override
	public void updateLocation(int key32, long key64, short location) throws RemoteException{
		
		System.out.println("ATUALIZANDO LOCALIZAÇÃO key32: "  + key32 + " e key64:  " + key64 + " para: " + location);
		
		//TODO: melhorar esta lógica de encontrar o elemento
		for(MessageReceived msg: messagesReceived){
			if(msg.getStateReceived().getNode().getKey().getKey32() == key32 && msg.getStateReceived().getNode().getKey().getKey64() == key64){
			//if(msg.getStateReceived().getNode().getName().equals(key)){
				msg.getStateReceived().setLocation(location);
				msg.setPriority(Utils.getPriority(location, msg.getPriority()));
				System.out.println(":::atualizado:::");
				break;
			}
		}
	}
	
	public IMasterRemote getMasterRemote() throws RemoteException{
		return this.masterRemote;
	}

	public Boolean getStopSearch() {
		
		if(stopSearch ==  null){
			return false;
		}
		
		return stopSearch;
	}

	public void setStopSearch(Boolean stopSearch) {
		this.stopSearch = stopSearch;
	}
	
	public Boolean getStopJob() {
		return stopJob;
	}
	
	public void setStopJob(Boolean stopJob) {
		this.stopJob = stopJob;
	}
	
	public boolean getRestartSlave() {
		return restartSlave;
	}

	public void setRestartSlave(boolean restartSlave) {
		this.restartSlave = restartSlave;
	}

	@Override
	public void setNet(INet net) throws RemoteException{
		//System.out.println("Incluindo rede neural: " + net.getName());
		this.net = net;
	}
	
	@Override
	public INet getNet(){
		return net;
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public float getBeta() {
		return beta;
	}

	public void setBeta(float beta) {
		this.beta = beta;
	}
	
	public float getRootMinMaxValue(){
		return rootMinMaxValue;
	}

	public List<MessageReceived> getMessagesReceived() {
		if(messagesReceived == null){
			messagesReceived = new ArrayList<MessageReceived>();
			//messagesReceived = Collections.synchronizedList(new ArrayList<MessageReceived>());;
		}
		return messagesReceived;
	}

	public void setMessagesReceived(List<MessageReceived> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	public ICache getLocalCache() {
		return localCache;
	}
	
	public void setLocalCache(ICache localCache) {
		this.localCache = localCache;
	}
	
	public Boolean getWaitingClearSearch() {
		return waitingClearSearch;
	}

	public void setWaitingClearSearch(Boolean waitingClearSearch) {
		this.waitingClearSearch = waitingClearSearch;
	}

	@Override
	public void allowSendNextTT() throws RemoteException {
		((ICacheShadow)localCache).allowSendNextTT();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void registryShadowTable(Map ttable) throws RemoteException {
		
		System.out.println(":::: SlaveRemote SHADOW ::::: Elementos que chegaram!!!\n");
		
		Set<Key> keys = ttable.keySet();
		
		for(Key k : keys){
			System.out.print("k32: " + ((Entry)ttable.get(k)).getState().getKey().getKey32() + " k64:" + ((Entry)ttable.get(k)).getState().getKey().getKey64());
		}
		System.out.println("\n");
		
		((ICacheShadow)localCache).addShadowTable(ttable);
	}

	/*@Override
	public boolean abortMessage( IState state ) throws RemoteException {

		if(messagesReceived != null){
			synchronized (messagesReceived) {
				logger.warn(":::ABORT MESSAGE:::");
				for(MessageReceived msg: messagesReceived){
					if(msg.getStateReceived().getNode().equals(state.getNode()) && !msg.getMessageStatus().equals(MessageStatus.PROCESSED)){
						msg.setMessageStatus(MessageStatus.ABORTED);
						
						logger.warn("Mensagem da tarefa: key32 " + state.getNode().getKey().getKey32() + " e key64: " + state.getNode().getKey().getKey64() + " abortada.");
						
						return true;
					}
				}
				
				messagesReceived.notifyAll();
			}
		}
		
		logger.warn("Veio abortar, mas não achou a tarefa");
		
		return false;
	}*/
	
	public void abortMessage( List<Key> keys ) throws RemoteException {

		this.abortedTasks = keys;
		
	}

	@Override
	public void restartSlave() throws RemoteException {
		//System.out.println("Começando novamente os trabalhos!!!");
		this.restartSlave = true;
	}
	
	@Override
	public boolean unblockSlave() throws RemoteException{
		this.restartSlave = true;
		return true;
	}

	@Override
	public void waitingTasksNotification(String host) throws RemoteException {
		masterRemote.slaveWaitingTask(host);
		
	}

	public List<Key> getAbortedTasks() {
		return abortedTasks;
	}

	public void setAbortedTasks(List<Key> abortedTasks) {
		this.abortedTasks = abortedTasks;
	}
	
	
}
