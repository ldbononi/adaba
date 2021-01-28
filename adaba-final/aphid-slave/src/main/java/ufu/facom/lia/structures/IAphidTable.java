package ufu.facom.lia.structures;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ufu.facom.lia.interfaces.IState;

public interface IAphidTable extends Remote {

	Integer getId() throws RemoteException;

	void setId(Integer id) throws RemoteException;

	List<IState> getTasks() throws RemoteException;

	void setTasks(List<IState> tasks) throws RemoteException;

	String getHost() throws RemoteException;

	void setHost(String host) throws RemoteException;

	List<IAphidTable> getSlaves() throws RemoteException;

	void setSlaves(List<IAphidTable> slaves) throws RemoteException;

}
