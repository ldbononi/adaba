package ufu.facom.lia.remote.utils;

import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;
import ufu.facom.lia.search.interfaces.Priority;

public class ConvertRemoteState {

	public MessageReceived convert(IState remoteState) {

		MessageReceived mr = new MessageReceived();

		mr.setStateReceived(remoteState);
		mr.setMessageStatus(MessageStatus.NEW);
		mr.setPriority(Utils.getPriority(remoteState.getLocation(), Priority.DEFAULT.getValue()));

		return mr;

	}

	public void updateMessageReceived(IState remoteState, MessageReceived msgRcvd) {

		msgRcvd.setStateReceived(remoteState);

		msgRcvd.setMessageStatus(MessageStatus.UPDATED);
		msgRcvd.setPriority(Utils.getPriority(remoteState.getLocation(), msgRcvd.getPriority()));
	}

}
