package ufu.facom.lia.interfaces;

import java.rmi.Remote;
import java.util.Map;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.search.interfaces.SlaveStatus;
import ufu.facom.lia.search.interfaces.Status; 

public interface IState extends Remote, Comparable<IState> {
	
	Key getKey();
	
	void setKey(Key key);

	int getMaxDepth();

	void setMaxDepth(int maxDepth);

	int getMasterDepth();

	void setMasterDepth(int masterDepth);

	int getCurrentDepth();

	void setCurrentDepth(int currentDepth);

	Integer getPreviousDepth();

	void setPreviousDepth(Integer previousDepth);

	float getAlpha();

	void setAlpha(float alpha);

	float getBeta();

	void setBeta(float beta);

	String getStateRepresentation();

	void setStateRepresentation(String stateRepresentation);

	Status getStatus();

	void setStatus(Status status);

	SlaveStatus getSlaveStatus();

	void setSlaveStatus(SlaveStatus slaveStatus);

	Integer getBranchRate();

	void setBranchRate(Integer branchRate);

	Long getStateSpace();

	void setStateSpace(Long stateSpace);

	short getLocation();

	void setLocation(short location);

	Map<Integer, IInfoNode> getHistoric();

	void setHistoric(Map<Integer, IInfoNode> historic);

	Boolean getMasterVisited();

	void setMasterVisited(Boolean masterVisited);

	String getOwner();

	void setOwner(String owner);

	Long getTime();

	void setTime(Long time);
	
	boolean isEdge();
	
	void setEdge(boolean edge);

	INode getNode();

	void setNode(INode node);
	
	IMove getMove();
	
	void setMove(IMove move);
	
	float getDiff();
	
	void setDiff(float diff);
}
