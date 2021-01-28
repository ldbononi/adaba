package ufu.facom.lia.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.remote.ISlaveRemote;

public class AphidTable implements IAphidTable, Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	//private List<IState> tasks;
	private Map<Key, IState> tasks;
	
	//private List<IState> sentTasks;
	private Map<Key, IState> sentTasks;

	private String host;

	private ISlaveRemote slaveRemote;

	private List<IAphidTable> slaves;
	
	private String hostRightNeighbor;
	
	private boolean neuralNetworkSent;
	
	private boolean masterRemoteRegistered;
	
	private boolean blocked;
	
	private boolean finalized;
	
	private Map<Short, Integer> controlPriorities;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public Map<Key, IState> getTasks() {
		
		if(tasks == null) {
			tasks = new HashMap<>();
		}
		return tasks;
	}

	public void setTasks(Map<Key, IState> tasks) {
		this.tasks = tasks;
	}

	public Map<Key, IState> getSentTasks() {
		if(sentTasks == null) {
			sentTasks = new HashMap<>();
		}
		return sentTasks;
	}

	public void setSentTasks(Map<Key, IState> sentTasks) {
		this.sentTasks = sentTasks;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<IAphidTable> getSlaves() {

		if (slaves == null) {
			slaves = new ArrayList<>();
		}

		return slaves;
	}

	public void setSlaves(List<IAphidTable> slaves) {
		this.slaves = slaves;
	}

	public ISlaveRemote getSlaveRemote(){
		return slaveRemote;
	}

	public void setSlaveRemote(ISlaveRemote slaveRemote){
		this.slaveRemote = slaveRemote;
	}

	public String getHostRightNeighbor() {
		return hostRightNeighbor;
	}

	public void setHostRightNeighbor(String hostRightNeighbor) {
		this.hostRightNeighbor = hostRightNeighbor;
	}

	public boolean isMasterRemoteRegistered() {
		return masterRemoteRegistered;
	}

	public void setMasterRemoteRegistered(boolean masterRemoteRegistered) {
		this.masterRemoteRegistered = masterRemoteRegistered;
	}

	@Override
	public boolean isBlocked() {
		return blocked;
	}

	@Override
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	@Override
	public boolean isFinalized() {
		return this.finalized;
	}

	@Override
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	public Map<Short, Integer> getControlPriorities() {
		if(this.controlPriorities == null){
			controlPriorities = new HashMap<>();
		}
		return controlPriorities;
	}
	
	public void setControlLocation(Map<Short, Integer> controlLocation) {
		this.controlPriorities = controlPriorities;
	}

	@Override
	public IAphidTable getSlave(String host) {

		for(IAphidTable at: slaves){
			if(at.getHost().equals(host)){
				return at;
			}
		}
		return null;
	}

	@Override
	public boolean getNeuralNetworkSent() {
		return neuralNetworkSent;
	}

	@Override
	public void setNeuralNetworkSent(boolean neuralNetworkSent) {
		this.neuralNetworkSent = neuralNetworkSent;
	}
}
