package ufu.facom.lia.structures;

import java.io.Serializable;

import ufu.facom.lia.interfaces.IInfoNode;

public class InfoNode implements IInfoNode, Cloneable, Serializable{
	
	private static final long serialVersionUID = 1L;

	private float value;
	
	private short scoreType;
	
	private Long time;
	
	public InfoNode(float value, short scoreType, Long time){
		this.value = value;
		this.scoreType = scoreType;
		this.time = time;
	}

	@Override
	public float getValue() {
		return value;
	}

	@Override
	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public short getScoreType() {
		return scoreType;
	}

	@Override
	public void setScoreType(short scoreType) {
		this.scoreType = scoreType;
	}

	@Override
	public Long getTime() {
		return time;
	}

	@Override
	public void setTime(Long time) {
		this.time = time;
	}
	
	@Override
	public IInfoNode clone() throws CloneNotSupportedException {
		return (IInfoNode) super.clone();
	}
}
