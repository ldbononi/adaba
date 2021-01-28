package ufu.facom.lia.bean;

import java.io.Serializable;

import ufu.facom.lia.interfaces.Constants;
import ufu.facom.lia.interfaces.IMove;

public class Move implements Cloneable, Serializable, IMove {

	private static final long serialVersionUID = 1L;

	private int from;

	private int to;

	// The quantity of moves applied for this configuration "from" - "to"
	private int qtdMoves;

	// The player that had executed the move 
	private int player;

	int[] seqDirs;

	int exchange;

	public Move() {
		this.seqDirs = new int[Constants.MAXCOMB];
	}

	public Move(int from, int to) {
		this();
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int[] getSeqDirs() {
		return seqDirs;
	}

	public void setSeqDirs(int[] seqDirs) {
		this.seqDirs = seqDirs;
	}

	public int getExchange() {
		return exchange;
	}

	public void setExchange(int exchange) {
		this.exchange = exchange;
	}

	public int getQtdMoves() {
		return qtdMoves;
	}

	public void setQtdMoves(int qtdMoves) {
		this.qtdMoves = qtdMoves;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	@Override
	public Move clone() throws CloneNotSupportedException {
		
		Move m = (Move) super.clone();
		
		if(m.getSeqDirs() != null){
			int[] seqDirsTemp = new int[m.getSeqDirs().length];
			
			for(int i = 0; i < m.getSeqDirs().length; i++){
				seqDirsTemp[i] = m.getSeqDirs()[i];
			}
			
			m.setSeqDirs(seqDirsTemp);
		}
		
		return m;
	}
	
	@Override
	public String toString() {
		
		return this.getFrom() + "-" + this.getTo();
	}

}
