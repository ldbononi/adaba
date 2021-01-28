package ufu.facom.lia.interfaces;

public interface IInfoNode {
	
	float getValue();
	
	void setValue(float value);
	
	short getScoreType();
	
	void setScoreType(short scoreType);
	
	Long getTime();
	
	void setTime(Long time);
	
	IInfoNode clone() throws CloneNotSupportedException;

}
