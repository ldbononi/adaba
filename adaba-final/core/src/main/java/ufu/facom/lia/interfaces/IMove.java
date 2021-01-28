package ufu.facom.lia.interfaces;

public interface IMove extends Cloneable{

	int getFrom();

	void setFrom(int from);

	int getTo();

	void setTo(int to);

	int[] getSeqDirs();

	void setSeqDirs(int[] seqDirs);
	
	IMove clone() throws CloneNotSupportedException;

}
