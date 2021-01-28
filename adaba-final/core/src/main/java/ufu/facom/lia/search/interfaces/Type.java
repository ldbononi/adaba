package ufu.facom.lia.search.interfaces;

public enum Type {
	
	MIN((short) 1),
	MAX((short) 2),
	LEAF((short) 3),
	LOSER((short) 4); //when there is no successor for this node, not is a LEAF node
	
	private short value;
	
	private Type(short value){
		this.value = value;
	}
	
	public short getValue(){
		return value;
	}

}
