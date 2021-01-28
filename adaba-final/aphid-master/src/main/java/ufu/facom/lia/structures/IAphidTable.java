package ufu.facom.lia.structures;

import java.util.List;
import java.util.Map;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.remote.ISlaveRemote;

public interface IAphidTable {

	Integer getId();

	void setId(Integer id);
	
	Map<Key, IState> getTasks();

	void setTasks(Map<Key, IState> tasks);
	
	Map<Key, IState> getSentTasks();

	void setSentTasks(Map<Key, IState> sentTasks);

	String getHost();

	void setHost(String host);

	List<IAphidTable> getSlaves();

	void setSlaves(List<IAphidTable> slaves);
	
	ISlaveRemote getSlaveRemote();
	
	void setSlaveRemote(ISlaveRemote slaveRemote);
	
	IAphidTable getSlave(String host);
	
	boolean getNeuralNetworkSent();
	
	void setNeuralNetworkSent(boolean neuralNetworkSent);
	
	boolean isBlocked();
	
	void setBlocked(boolean blocked);
	
	boolean isFinalized();
	
	void setFinalized(boolean finalized);
	
	Map<Short, Integer> getControlPriorities();
	
	void setControlLocation(Map<Short, Integer> controlLocation);
	
}
