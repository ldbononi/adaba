package ufu.facom.lia.search.interfaces;

import java.io.Serializable;

public enum Player implements Serializable{
	
	BLACK(1),
	RED(2);
	
	private int value;
	private Player(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

}
