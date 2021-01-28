package ufu.facom.lia.cache;

import java.io.Serializable;

import ufu.facom.lia.interfaces.INode;

public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;

	private INode state;

	//private Key key;

	//private String host;

	private short scoreType;

	public INode getState() {
		return state;
	}

	public void setState(INode state) {
		this.state = state;
	}

	/*public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
*/
	public short getScoreType() {
		return scoreType;
	}

	public void setScoreType(short scoreType) {
		this.scoreType = scoreType;
	}

	/*public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}*/

}
