package ufu.facom.lia.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.IState;

public interface ISlaveRemote extends Remote{
	
	void registerEntry(IState task) throws RemoteException;
	
	void registryEntries(Map<Key, IState> tasks) throws RemoteException;
	
	//void writeMasterRemote(IState state) throws RemoteException;
	
	void registryMasterRemote(IMasterRemote masterRemote) throws RemoteException;
	
	void setTasks(Map<Key, IState> tasks) throws RemoteException;
	
	void canFinalizeSearch() throws RemoteException;
	
	boolean finalizeJob() throws RemoteException;
	
	Map<Key, IState> getTasks() throws RemoteException;
	
	IMasterRemote getMasterRemote() throws RemoteException;
	
	void setNet(INet net) throws RemoteException;
	
	INet getNet() throws RemoteException;
	
	void registerNewWindow(float alpha, float beta) throws RemoteException;
	
	void registerRootMinimaxValue(float value) throws RemoteException;
	
	float getAlpha() throws RemoteException;
	
	float getBeta() throws RemoteException;
	
	float getRootMinMaxValue() throws RemoteException;
	
	void updateLocation(int key32, long key64, short location) throws RemoteException;
	
	void registryShadowTable(@SuppressWarnings("rawtypes") Map ttable) throws RemoteException;
	
	//boolean abortMessage( IState state ) throws RemoteException;
	
	void abortMessage( List<Key> keys ) throws RemoteException;
	
	void allowSendNextTT() throws RemoteException;
	
	void restartSlave() throws RemoteException;
	
	boolean unblockSlave() throws RemoteException;
	
	void waitingTasksNotification(String host) throws RemoteException;

}
