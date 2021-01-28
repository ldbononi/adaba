package ufu.facom.lia.board;

import java.io.Serializable;

import ufu.facom.lia.exceptions.InvalidBoardException;

public class XBoard implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int XBOARD_LENGTH = 36;

	private int[] xBoard;

	public XBoard() {
		xBoard = new int[XBOARD_LENGTH]; // tem uma posição a mais, pois ignora a posição 0
								// a fim de dar certo no mapeamento xBoard;
	}


	public int[] getXBoard() {
		return xBoard;
	}

	public void setXBoard(int[] xBoard) throws InvalidBoardException {
		if (xBoard == null || xBoard.length != 36) {
			throw new InvalidBoardException("Tabuleiro nulo ou quantidade de peças inválido");
		}

		this.xBoard = xBoard;
	}


	@Override
	public XBoard clone() throws CloneNotSupportedException {
		
		XBoard b = (XBoard) super.clone();
		
		int[] xBoardNew = new int[b.getXBoard().length];
		
		try {
			
			for(int i = 0; i < b.getXBoard().length; i++){
				xBoardNew[i] = b.getXBoard()[i];
			}
			
			b.setXBoard(xBoardNew);
			
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		return b;
	}
}
