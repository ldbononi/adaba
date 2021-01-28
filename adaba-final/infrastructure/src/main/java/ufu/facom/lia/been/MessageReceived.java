package ufu.facom.lia.been;

import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;

public class MessageReceived implements Comparable<MessageReceived> {

	private IState stateReceived;

	private Integer priority;

	private MessageStatus messageStatus;

	public IState getStateReceived() {
		return stateReceived;
	}

	public void setStateReceived(IState stateReceived) {
		this.stateReceived = stateReceived;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Override
	public int compareTo(MessageReceived message) {

		if (this.priority < message.priority) {
            return 1;
        }
        if (this.priority > message.priority) {
            return -1;
        }
        return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageStatus == null) ? 0 : messageStatus.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((stateReceived == null) ? 0 : stateReceived.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageReceived other = (MessageReceived) obj;
		if (messageStatus != other.messageStatus)
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (stateReceived == null) {
			if (other.stateReceived != null)
				return false;
		} else if (!stateReceived.equals(other.stateReceived))
			return false;
		return true;
	}
	
	

}
