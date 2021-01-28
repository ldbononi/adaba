package ufu.facom.lia.board;

import java.io.Serializable;

import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.PlayerException;

public class Board implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int BOARD_LENGTH = 32;

	private int[] board;

	//private int[] xBoard;

	//private int featureEvaluation;

	// private static Board b;

	public Board() {
		board = new int[BOARD_LENGTH + 1];
		//xBoard = new int[36]; // tem uma posição a mais, pois ignora a posição 0
								// a fim de dar certo no mapeamento xBoard;
	}

	public Board(String[] positions) throws PlayerException {

		this();

		// The number of positions there are more three informations
		/*if (board.length != positions.length - 3) {
			throw new PlayerException("Impossible convert the board: the number of positions doesn't matches.");
		}*/

		for (int i = 0; i < BOARD_LENGTH; i++) {
			if(positions[i].trim() != ""){
				board[i] = Integer.parseInt(positions[i].trim());
			}
		}
		
		//TODO: retirar isso aqui urgente!!! FOI COLOCADO APENAS PARA TREINAR OS AGENTES DE FINAL DE JOGO
		/*int j = 0;
		for (int i = BOARD_LENGTH-1; i >= 0; i--, j++) {
			if(positions[i].trim() != ""){
				board[j] = Integer.parseInt(positions[i].trim());
			}
		}*/

	}

	/*
	 * public static Board getInstance() {
	 * 
	 * if (b == null) { b = new Board(); }
	 * 
	 * return b; }
	 */

	public int[] getBoard() {
		return board;
	}

	public void setBoard(int[] board) throws InvalidBoardException {
		/*if (board == null || board.length != BOARD_LENGTH + 1) {
			throw new InvalidBoardException("Tabuleiro nulo ou quantidade de peças inválido");
		}*/

		this.board = board;
	}

	/*public int[] getXBoard() {
		
		int[] xb = new int[36];
		
		BoardUtils bu = new BoardUtils();
		try {
			bu.mapToXBoard(getBoard(), xb);
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		return xb;
	}*/

	/*public void setXBoard(int[] xBoard) throws InvalidBoardException {
		if (xBoard == null || xBoard.length != BOARD_LENGTH + 4) {
			throw new InvalidBoardException("Tabuleiro nulo ou quantidade de peças inválido");
		}

		this.xBoard = xBoard;
	}
*/
	/**
	 * Used to record the feature evaluations... is a auxiliar variable
	 * 
	 * @return
	 */
	
	/*public int getFeatureEvaluation() {
		return featureEvaluation;
	}

	public void setFeatureEvaluation(int featureEvaluation) {
		this.featureEvaluation = featureEvaluation;
	}*/
	
	public String getBoardRepresentation(){
		
		String sBoard = "";
		
		if(board != null){
			for(int i = 0; i < board.length - 1; i++){
				sBoard += board[i] + " ";
			}
		}else{
			sBoard = null;
		}
		
		return sBoard;
	}

	private void teste2(int[] a) {

		a[2] = 4;
	}

	public static void main(String[] args) {
		Board b = new Board();

		int[] a = { 1, 1, 1, 1 };

		System.out.println("Antes: ");
		for (int i : a) {
			System.out.println(i + " ");
		}
		b.teste2(a);
		System.out.println("Depois: ");
		for (int i : a) {
			System.out.println(i + " ");
		}

	}

	@Override
	public Board clone() throws CloneNotSupportedException {
		
		Board b = (Board) super.clone();
		
		int[] boardNew = new int[b.getBoard().length];
		//int[] xBoardNew = new int[b.getXBoard().length];
		
		try {
			
			for(int i = 0; i < b.getBoard().length; i++){
				boardNew[i] = b.getBoard()[i];
			}
			
			/*for(int i = 0; i < b.getXBoard().length; i++){
				xBoardNew[i] = b.getXBoard()[i];
			}*/
			
			b.setBoard(boardNew);
			//b.setXBoard(xBoardNew);
			
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		return b;
	}
}
