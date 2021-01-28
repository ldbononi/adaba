package ufu.facom.lia.board;

import ufu.facom.lia.bean.Move;

public class Actions {

	Move[] moves;

	Move[] jumps;

	public Move[] getMoves() {
		return moves;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public Move[] getJumps() {
		return jumps;
	}

	public void setJumps(Move[] jumps) {
		this.jumps = jumps;
	}

}
