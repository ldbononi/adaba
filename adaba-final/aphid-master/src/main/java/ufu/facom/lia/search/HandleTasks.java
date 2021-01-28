package ufu.facom.lia.search;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.search.interfaces.SlaveStatus;
import ufu.facom.lia.search.interfaces.Status;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.IAphidTable;

//public class HandleTasks implements Runnable {
public class HandleTasks implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(HandleTasks.class);
	
	private String host;
	
	private List<IState> statesReceived;
	
	private int maxDepth;
	
	private IAphidTable slave;

	//@Override
	public void run() {
		handleMessageReceived();

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	public void setStatesReceived(List<IState> statesReceived) {
		this.statesReceived = statesReceived;
	}
	
	public void setSlave(IAphidTable slave) {
		this.slave = slave;
	}

	public void handleMessageReceived(){
		
		//logger.warn("Veio tratar aqui");
		
		for(IState state: statesReceived){
			
			handleMessageReceived(state);
		}
		
	}

	public void handleMessageReceived(IState stateReceived) {
		
		//System.out.println("Owner: " + state.getOwner());
		
		logger.debug("chegou resposta: k64" + stateReceived.getNode().getKey().getKey32() + " na d: " + stateReceived.getNode().getDepth() + " vindo de: " + stateReceived.getOwner());

		IState toChange = slave.getSentTasks().get(stateReceived.getKey());
		if (toChange!= null && toChange.getNode().equals(stateReceived.getNode())) {
			
			toChange.getNode().setEvaluation(stateReceived.getNode().getEvaluation());
			toChange.getNode().setDepth(stateReceived.getNode().getDepth());
			toChange.setCurrentDepth(stateReceived.getCurrentDepth());
			toChange.setSlaveStatus(SlaveStatus.RECEIVED);
			toChange.setBranchRate(stateReceived.getBranchRate());
			toChange.setStateSpace(stateReceived.getStateSpace());
			toChange.setAlpha(stateReceived.getAlpha());
			toChange.setBeta(stateReceived.getBeta());
			toChange.getNode().setScoreType(stateReceived.getNode().getScoreType());
			toChange.setOwner(stateReceived.getOwner());
			toChange.setTime(stateReceived.getTime());
			toChange.setPreviousDepth(stateReceived.getPreviousDepth());
			toChange.setMove(stateReceived.getMove());
			toChange.getNode().setType(stateReceived.getNode().getType());
			
			if(toChange.getNode().getDepth() >= maxDepth || toChange.getNode().getType() == Type.LEAF.getValue()){
				toChange.setStatus(Status.CERTAIN);
			}
			
		}
	}

}
