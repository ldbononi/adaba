package ufu.facom.lia.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ufu.facom.lia.interfaces.IState;

public interface IMasterRemote extends Remote {
	
	void receiveState(IState state) throws RemoteException;
	
	void receiveStates(List<IState> states) throws RemoteException;
	
	boolean unblockSlave(String host) throws RemoteException;
	
	void confirmMsgSlaveFinalized(String host) throws RemoteException;
	
	void confirmMsgSlaveRestarted(String host) throws RemoteException;
	
	void slaveWaitingTask(String host) throws RemoteException;

}
