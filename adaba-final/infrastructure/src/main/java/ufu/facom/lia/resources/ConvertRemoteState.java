package ufu.facom.lia.resources;

import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;

public class ConvertRemoteState {

	/*public MessageReceived convert(IState remoteState) {
		
		MessageReceived mr = new MessageReceived();

		IState st = new State();

		st.setDepth(remoteState.getDepth());
		st.setEvaluation(st.getEvaluation());
		st.setName(remoteState.getName());
		st.setPlayer(remoteState.getPlayer());
		st.setBranchRate(remoteState.getBranchRate());
		st.setStateSpace(remoteState.getStateSpace());
		st.setStateRepresentation(remoteState.getStateRepresentation());
		st.setScoreType(remoteState.getScoreType());
		st.setLocation(remoteState.getLocation());
		//st.setPriority(remoteState.getPriority());
		st.setMasterVisited(remoteState.getMasterVisited());
		st.setHistoric(remoteState.getHistoric());
		st.setOwner(remoteState.getOwner());
		st.setTime(remoteState.getTime());
		st.setPreviousDepth(remoteState.getPreviousDepth());

		if (remoteState.getType().equals(Configs.MAX)) {
			st.setType(Type.MAX);
		} else if (remoteState.getType().equals(Configs.MIN)) {
			st.setType(Type.MIN);
		} else {
			st.setType(Type.LEAF);
		}
		
		mr.setState(st);
		mr.setStateReceived(remoteState);
		mr.setMessageStatus(MessageStatus.NEW);
		//mr.setPriority(Configs.DEFAULT_PRIORITY);

		return mr;

	}
	*/
	public void updateMessageReceived(IState remoteState, MessageReceived msgRcvd){
		
		msgRcvd.setStateReceived(remoteState);
		msgRcvd.setMessageStatus(MessageStatus.UPDATED);
		
		/*State st = new Node();

		st.setDepth(remoteState.getDepth());
		st.setEvaluation(st.getEvaluation());
		st.setName(remoteState.getName());
		st.setPlayer(remoteState.getPlayer());
		st.setStateSpace(remoteState.getStateSpace());
		st.setBranchRate(remoteState.getBranchRate());
		st.setStateRepresentation(remoteState.getStateRepresentation());
		st.setScoreType(remoteState.getScoreType());
		st.setLocation(remoteState.getLocation());
		//st.setPriority(remoteState.getPriority());
		st.setMasterVisited(remoteState.getMasterVisited());
		st.setHistoric(remoteState.getHistoric());
		st.setOwner(remoteState.getOwner());
		st.setTime(remoteState.getTime());
		st.setPreviousDepth(remoteState.getPreviousDepth());

		if (remoteState.getType().equals(Configs.MAX)) {
			st.setType(Type.MAX);
		} else if (remoteState.getType().equals(Configs.MIN)) {
			st.setType(Type.MIN);
		} else {
			st.setType(Type.LEAF);
		}
		
		msgRcvd.setState(st);*/
		
		//msgRcvd.setPriority(Configs.DEFAULT_PRIORITY);
	}
	
	/*public IState convertState(State remoteState, float min, float max) {

		IState st = new ufu.facom.lia.been.State();

		/*st.setDepth(remoteState.getDepth());
		st.setEvaluation(remoteState.getEvaluation());
		st.setName(remoteState.getName());
		st.setPlayer(remoteState.getPlayer());
		st.setBeta(max);
		st.setAlpha(min);
		st.setBranchRate(remoteState.getBranchRate());
		st.setStateSpace(remoteState.getStateSpace());
		st.setCurrentDepth(remoteState.getDepth() + Configs.ITERATIVE_DEEPENING);
		st.setStateRepresentation(remoteState.getStateRepresentation());
		st.setScoreType(remoteState.getScoreType());
		st.setLocation(remoteState.getLocation());
		//st.setPriority(remoteState.getPriority());
		st.setMasterVisited(remoteState.getMasterVisited() != null ? remoteState.getMasterVisited() : Boolean.FALSE);
		st.setHistoric(remoteState.getHistoric());
		st.setOwner(remoteState.getOwner());
		st.setTime(remoteState.getTime());
		st.setPreviousDepth(remoteState.getPreviousDepth());

		if (remoteState.getType().equals(Type.MAX)) {
			st.setType(Configs.MAX);
		} else if (remoteState.getType().equals(Type.MIN)) {
			st.setType(Configs.MIN);
		} else {
			st.setType(Configs.LEAF);
		}*/

		//return st;

	//}*/
}
