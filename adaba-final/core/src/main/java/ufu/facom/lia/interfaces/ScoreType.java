package ufu.facom.lia.interfaces;

import java.io.Serializable;

public enum ScoreType implements Serializable{
	
	HASH_EXACT((short) 1),
	AT_MOST((short) 2),
	AT_LEAST((short) 3);
	
	private short value;
	
	private ScoreType(short value){
		this.value = value;
	}
	
	public short getValue(){
		return this.value;
	}
}
