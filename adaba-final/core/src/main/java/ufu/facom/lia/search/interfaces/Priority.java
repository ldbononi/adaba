package ufu.facom.lia.search.interfaces;

import java.io.Serializable;

public enum Priority implements Serializable{
	
	DEFAULT(5),
	PV(4),
	CRITICAL_TREE(2),
	UNCERTAIN_NODE(1);
	
	private int value;
	
	private Priority(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}

}
