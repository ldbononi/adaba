package ufu.facom.lia.board;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.InvalidPositionException;
import ufu.facom.lia.exceptions.PlayerException;
import ufu.facom.lia.exceptions.ZobristException;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.Jumps;

public class BoardUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Positions 1-8 remain unchanged, 9-16 are plus 1, 17-24 are plus 2, 25-32
	 * are plus 3
	 * 
	 * @param board
	 * @param xBoard
	 * @throws InvalidBoardException
	 */
	public void mapToXBoard(int[] board, int[] xBoard) throws InvalidBoardException {

		try {

			xBoard[0] = BoardValues.INVALID;

			for (int i = 1; i <= 8; i++) {
				xBoard[i] = board[i - 1];
			}
			xBoard[9] = BoardValues.INVALID;

			for (int i = 9; i <= 16; i++) {
				xBoard[i + 1] = board[i - 1];
			}
			xBoard[18] = BoardValues.INVALID;

			for (int i = 17; i <= 24; i++) {
				xBoard[i + 2] = board[i - 1];
			}
			xBoard[27] = BoardValues.INVALID;

			for (int i = 25; i <= 32; i++) {
				xBoard[i + 3] = board[i - 1];
			}

		} catch (Exception e) {
			throw new InvalidBoardException(e.getMessage());
		}
	}

	/**
	 * Reverse function of mapToXBoard
	 * 
	 * @param board
	 * @param xBoard
	 * @throws InvalidBoardException
	 */
	public void mapToBoard(int[] board, int[] xBoard) throws InvalidBoardException {

		try {

			for (int i = 1; i <= 8; i++) {
				board[i - 1] = xBoard[i];
			}

			for (int i = 9; i <= 16; i++) {
				board[i - 1] = xBoard[i + 1];
			}

			for (int i = 17; i <= 24; i++) {
				board[i - 1] = xBoard[i + 2];
			}

			for (int i = 25; i <= 32; i++) {
				board[i - 1] = xBoard[i + 3];
			}

		} catch (Exception e) {
			throw new InvalidBoardException(e.getMessage());
		}

	}
	
	public void convertBoard(String stateRepresentation, Board board, XBoard xb){
		
		String[] representation = StringUtils.split(stateRepresentation, " ");
		int i = 0;
		
		for(String r: representation){
			board.getBoard()[i++] = Integer.parseInt(r);
		}

		try {
			mapToXBoard(board.getBoard(), xb.getXBoard());
			
		} catch (InvalidBoardException e) {
			System.out.println("ERRO: erro ao converter tabuleiro.");
			e.printStackTrace();
		}
	}

	public void printBoard(Board b) {

		for (int i = 31; i >= 28; i--) {
			System.out.print(" " + printPiece(b.getBoard()[i]));
		}
		System.out.println("");

		for (int i = 27; i >= 24; i--) {
			System.out.print(printPiece(b.getBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 23; i >= 20; i--) {
			System.out.print(" " + printPiece(b.getBoard()[i]));
		}
		System.out.println("");

		for (int i = 19; i >= 16; i--) {
			System.out.print(printPiece(b.getBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 15; i >= 12; i--) {
			System.out.print(" " + printPiece(b.getBoard()[i]));
		}
		System.out.println("");

		for (int i = 11; i >= 8; i--) {
			System.out.print(printPiece(b.getBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 7; i >= 4; i--) {
			System.out.print(" " + printPiece(b.getBoard()[i]));
		}
		System.out.println("");

		for (int i = 3; i >= 0; i--) {
			System.out.print(printPiece(b.getBoard()[i]) + " ");
		}
		System.out.println("\n");

	}

	public void printXBoard(XBoard xb) {

		for (int i = 35; i >= 32; i--) {
			System.out.print(" " + printPiece(xb.getXBoard()[i]));
		}
		System.out.println("");

		for (int i = 31; i >= 28; i--) {
			System.out.print(printPiece(xb.getXBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 26; i >= 23; i--) {
			System.out.print(" " + printPiece(xb.getXBoard()[i]));
		}
		System.out.println("");

		for (int i = 22; i >= 19; i--) {
			System.out.print(printPiece(xb.getXBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 17; i >= 14; i--) {
			System.out.print(" " + printPiece(xb.getXBoard()[i]));
		}
		System.out.println("");

		for (int i = 13; i >= 10; i--) {
			System.out.print(printPiece(xb.getXBoard()[i]) + " ");
		}
		System.out.println("");

		for (int i = 8; i >= 5; i--) {
			System.out.print(" " + printPiece(xb.getXBoard()[i]));
		}
		System.out.println("");

		for (int i = 4; i >= 1; i--) {
			System.out.print(printPiece(xb.getXBoard()[i]) + " ");
		}
		System.out.println("");

	}

	private char printPiece(int p) {

		if (p == BoardValues.BLACKMAN) {
			return 'p';
		} else if (p == BoardValues.BLACKKING) {
			return 'B';
		} else if (p == BoardValues.REDMAN) {
			return 'r';
		} else if (p == BoardValues.REDKING) {
			return 'R';
		} else {
			return '#';
		}
	}

	public synchronized int possibleMoves(int[] xBoard, int from) {

		int ret = Jumps.NONE;
		int foward = 0, backward = 0;

		if (xBoard[from] == BoardValues.BLACKMAN || xBoard[from] == BoardValues.BLACKKING
				|| xBoard[from] == BoardValues.REDKING) {
			foward = 1;
		}

		if (xBoard[from] == BoardValues.REDMAN || xBoard[from] == BoardValues.REDKING
				|| xBoard[from] == BoardValues.BLACKKING) {
			backward = 1;
		}

		//check single corners
		if (from == 4){
			if( xBoard[from + poscalc(Jumps.NE)] == BoardValues.EMPTY && foward == 1) {
				ret = Jumps.NE;
			}
			
		} else if (from == 32 && backward == 1){ 
			if(xBoard[from + poscalc(Jumps.SW)] == BoardValues.EMPTY) {
				ret = Jumps.SW;
			}
			
		// bottom squares
		} else if (from <= 3 && foward == 1) {
			if (xBoard[from + poscalc(Jumps.NE)] == BoardValues.EMPTY) {
				ret = Jumps.NE;
			}
			if (xBoard[from + poscalc(Jumps.NW)] == BoardValues.EMPTY) {
				ret += Jumps.NW;
			}
			// top squares
		} else if (from >= 33 && backward == 1) {
			if (xBoard[from + poscalc(Jumps.SE)] == BoardValues.EMPTY) {
				ret = Jumps.SE;
			}
			if (xBoard[from + poscalc(Jumps.SW)] == BoardValues.EMPTY) {
				ret += Jumps.SW;
			}
			// left squares
		} else if (from == 13 || from == 22 || from == 31) {
			if (xBoard[from + poscalc(Jumps.SE)] == BoardValues.EMPTY && backward == 1) {
				ret = Jumps.SE;
			}
			if (xBoard[from + poscalc(Jumps.NE)] == BoardValues.EMPTY && foward == 1) {
				ret += Jumps.NE;
			}

			// right squares
		} else if (from == 5 || from == 14 || from == 23) {
			if (xBoard[from + poscalc(Jumps.NW)] == BoardValues.EMPTY && foward == 1) {
				ret = Jumps.NW;
			}
			if (xBoard[from + poscalc(Jumps.SW)] == BoardValues.EMPTY && backward == 1) {
				ret += Jumps.SW;
			}

			// otherwise in the middle
		} else {
			
			if (xBoard[from + poscalc(Jumps.SE)] == BoardValues.EMPTY && backward == 1) {
				ret = Jumps.SE;
			}
			if (xBoard[from + poscalc(Jumps.SW)] == BoardValues.EMPTY && backward == 1) {
				ret += Jumps.SW;
			}
			if (xBoard[from + poscalc(Jumps.NE)] == BoardValues.EMPTY && foward == 1) {
				ret += Jumps.NE;
			}
			if (xBoard[from + poscalc(Jumps.NW)] == BoardValues.EMPTY && foward == 1) {
				ret += Jumps.NW;
			}
		}

		return ret;
	}

	/**
	 * Calculates possible jumps from pos given
	 * 
	 * @param xBoard
	 * @param p
	 * @return Combination of SE SW NE NW
	 */
	public int jumpsAvail(int[] xBoard, int p) throws InvalidPositionException {

		int ret = Jumps.NONE;

		if (p > 35 || p < 1 || p == 9 || p == 18 || p == 27) {
			throw new InvalidPositionException("The position [" + p + "] does not exist!");
		}

		// foward jumps
		if (xBoard[p] == BoardValues.BLACKMAN || xBoard[p] == BoardValues.BLACKKING
				|| xBoard[p] == BoardValues.REDKING) {

			 if(inArea(p, 3) && badGuy(xBoard[p], xBoard[p + 4]) && xBoard[p + 8] == BoardValues.EMPTY){
				 ret += Jumps.NE;
			 }
			 if(inArea(p, 4) && badGuy(xBoard[p], xBoard[p+5]) && xBoard[p+10] == BoardValues.EMPTY){
				 ret += Jumps.NW;
			 }
		}
		//backward jumps
		if(xBoard[p] == BoardValues.REDMAN || xBoard[p] == BoardValues.REDKING || xBoard[p] == BoardValues.BLACKKING){
			if(inArea(p, 1) && badGuy(xBoard[p], xBoard[p - 5]) && xBoard[p -10] == BoardValues.EMPTY){
				ret += Jumps.SE;
			}
			if(inArea(p, 2) && badGuy(xBoard[p], xBoard[p - 4]) && xBoard[p -8] == BoardValues.EMPTY){
				ret += Jumps.SW;
			}
		}

		return ret;
	}

	/**
	 * Return the pos to the NE/NW/SE/SW of current position (pos)
	 * 
	 * @param pos
	 * @return
	 */
	public int poscalc(int pos) {

		int x;

		if (pos == Jumps.NE)
			x = 4;
		else if (pos == Jumps.NW)
			x = 5;
		else if (pos == Jumps.SE)
			x = -5;
		else if (pos == Jumps.SW)
			x = -4;
		else
			x = 0;

		return x;
	}

	/**
	 * Mapeamento da coordenada de um Board para um XBoard
	 * 
	 * @param i
	 * @return
	 */
	public int mapToXCoord(int i) {
		
		if (i >= 9 && i <= 16) {
			return i + 1;
		} else if (i >= 17 && i <= 24) {
			return i + 2;
		} else if (i >= 25 && i <= 32) {
			return i + 3;
		}

		return i;
	}

	/**
	 * Mapeamento da coordenada de um XBoard para um Board
	 * 
	 * @param i
	 * @return
	 */
	public int mapToCoord(int i) {

		if (i >= 10 && i <= 17) {
			return i - 1;
		} else if (i >= 19 && i <= 26) {
			return i - 2;
		} else if (i >= 28 && i <= 35) {
			return i - 3;
		}

		return i;
	}

	/**
	 * Checks if its in the 'greater area' specified
	 * 
	 * @param p
	 * @param area
	 * @return
	 */
	public boolean inArea(int p, int area) {

		int r = inQuadrant(p);

		if (r == 5) {
			return true;
		}

		switch (area) {
		case 1:
			if (r == 1 || r == 2 || r == 4) {
				return true;
			}
			break;
		case 2:
			if (r == 2 || r == 3 || r == 6) {
				return true;
			}
			break;
		case 3:
			if (r == 4 || r == 7 || r == 8) {
				return true;
			}
			break;
		case 4:
			if (r == 6 || r == 8 || r == 9) {
				return true;
			}
			break;
		default:
			return false;
		}

		return false;

	}

	/**
	 * Takes the position and returns which quadrant its in - see notes for quad
	 * info
	 * 
	 * @param p
	 *            - position
	 * @return
	 */
	public int inQuadrant(int p) {
		switch (p) {
		case 1:
		case 5:
			return 9;
		case 2:
		case 3:
		case 6:
		case 7:
			return 8;
		case 4:
		case 8:
			return 7;
		case 10:
		case 14:
		case 19:
		case 23:
			return 6;
		case 13:
		case 17:
		case 22:
		case 26:
			return 4;
		case 28:
		case 32:
			return 3;
		case 29:
		case 30:
		case 33:
		case 34:
			return 2;
		case 31:
		case 35:
			return 1;
		default:
			return 5;
		}
	}

	/**
	 * Is he a bad guy?
	 * 
	 * @param me
	 * @param opp
	 * @return
	 */
	public boolean badGuy(int me, int opp) {

		if (((me == BoardValues.BLACKMAN || me == BoardValues.BLACKKING)
				&& (opp == BoardValues.REDKING || opp == BoardValues.REDMAN))
				|| ((me == BoardValues.REDMAN || me == BoardValues.REDKING)
						&& (opp == BoardValues.BLACKMAN || opp == BoardValues.BLACKKING))) {
			return true;
		}

		return false;
	}
	
	/*public boolean badGuy(int me, int opp){
		
		if(me == BoardValues.BLACKMAN || me == BoardValues.BLACKKING){
			if(opp == BoardValues.REDKING || opp == BoardValues.REDMAN){
				return true;
			}else{
				return false;
			}
		}else if(me == BoardValues.REDMAN || me == BoardValues.REDKING){
			if(opp == BoardValues.BLACKMAN || opp == BoardValues.BLACKKING){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}*/
	
	public int[] copyBoard(int[] src) throws InvalidBoardException{

		if(src == null || src.length == 0){
			throw new InvalidBoardException("Array fonte inválido!");
		}
		
		int[] target = new int[src.length];
		
		for(int i = 0; i < src.length; i++){
			target[i] = src[i];
		}
		
		return target;
	}
	
	public boolean jumpPiece(int[] xBoard, int from, int j){
		int x = poscalc(j);
		
		//TODO: tem chamadas à tabela de transposição
		
		xBoard[(from + (x*2))] = xBoard[from];
		xBoard[(from + x)] = BoardValues.EMPTY;
		xBoard[(from)] = BoardValues.EMPTY;
		
		boolean ret = promoCheck(xBoard, BoardType.XBOARD);
		
		return ret;
		
		
	}
	
	public void promoCheck(int[] xb){
		//promoCheck(b.getBoard(), BoardType.NORMAL);
		promoCheck(xb, BoardType.XBOARD);
	}
	
	public boolean promoCheck(int[] board, BoardType bType){
		boolean found = false;
		int i = 0, end = 0;
		
		if(bType.equals(BoardType.NORMAL)){
			i = 31;
		}else{
			i = 35;
		}
		
		end = i - 4;
		
		for(; i  > end; i--){
			
			if(board[i] == BoardValues.BLACKMAN){
				
				//TODO: chamada da tabela de transposição
				
				board[i] = BoardValues.BLACKKING;
				
				//TODO: chamada da tabela de transposição
				
				found = true;
			}
		}
		
		if(bType.equals(BoardType.NORMAL)){
			i = 0;
		}else{
			i = 1;
		}
		
		end = i + 4;
		
		for(; i < end; i++){
			
			if(board[i] == BoardValues.REDMAN){
				
				//TODO: chamada da tabela de transposição
				
				board[i] = BoardValues.REDKING;
				
				//TODO: chamada da tabela de transposição
				
				found = true;
				
			}
		}
		
		return found;
	}
	
	
	public void newXorXBoard(int xCoord, int[] xBoard){
		//TODO: TEM HAVER COM TABELA DE TRANSPOSIÇÃO
	}
	
	public void makeMove(int from, int to, int[] xb){
		
		Move m = new Move(from, to);
		
		makeMove(m, xb);
		
	}
	
	public void makeMove(Move move, int[] xboard){

		try{
			int idx = 0, evolution = move.getFrom();
			
			//Do the moves when exists a sequence of jumps
			while(move.getSeqDirs()[idx] != 0){
				int temp = (poscalc(move.getSeqDirs()[idx]));
				evolution += temp;
				xboard[evolution] = BoardValues.EMPTY;
				evolution += temp;
				idx++;
			}
			
			//Make the move on the xBoard
			xboard[move.getTo()] = xboard[move.getFrom()];
			xboard[move.getFrom()] = BoardValues.EMPTY;
			
			//Updating the board to be equals to xBoard
			//mapToBoard(b.getBoard(), xboard.getXBoard());
			
			//Verify the king and change the piece
			promoCheck(xboard);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void convertYbwcToAphidBoard(int[] board){
		
		for(int i = 0; i < board.length; i++){
			switch (board[i]) {
			case -1:
				board[i] = BoardValues.INVALID;
				break;
			case 5:
				board[i] = BoardValues.BLACKMAN;
				break;
			case 6:
				board[i] = BoardValues.REDMAN;
				break;
			case 9:
				board[i] = BoardValues.BLACKKING;
				break;
			case 10:
				board[i] = BoardValues.REDKING;
				break;
			case 16:
				board[i] = BoardValues.EMPTY;
				break;

			default:
				break;
			}
		}
		
		
	}
	
	public int[] convertAphidToYbwcBoard(int[] board){
		
		int[] newBoard = new int[board.length];
		
		for(int i = 0; i < board.length; i++){
			switch (board[i]) {
			case -1:
				newBoard[i] = BoardValues.INVALID;
				break;
			case BoardValues.BLACKMAN:
				newBoard[i] = 5;
				break;
			case BoardValues.REDMAN:
				newBoard[i] = 6;
				break;
			case BoardValues.BLACKKING:
				newBoard[i] = 9;
				break;
			case BoardValues.REDKING:
				newBoard[i] = 10;
				break;
			case BoardValues.EMPTY:
				newBoard[i] = 16;
				break;

			default:
				break;
			}
		}
		
		return newBoard;
	}
	
	public String getRepresentation(int[] board){
		
		String b = "";
		for(int i = 0; i < board.length - 1; i++){
			b += board[i] + " ";
		}
		
		return b;
	}

	public static void main(String[] args) throws InvalidBoardException, ZobristException {

		Board b = new Board();

		BoardUtils bu = new BoardUtils();
		
		//1 1 1 1 1 1 1 0 1 0 0 1 2 0 0 1 0 0 0 0 2 0 0 0 2 2 2 2 2 2 2 2
		//0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 2
		//1 0 0 0 0 0 0 4 2 2 0 0 0 3 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3
		//1 0 3 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 2 2 0 0 2 2 3 0 0 0 0 3
		//0 0 0 0 0 0 0 0 1 0 4 0 0 0 0 0 0 0 0 0 0 0 1 0 0 3 0 3 0 0 0 3
		//0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 3 0 0 0 0 0 0 0 4 0 1 0 0 0 0 0
		//1 1 0 0 1 1 0 0 1 0 0 0 2 0 1 0 0 0 2 0 2 2 0 0 0 2 1 0 3 0 0 2
		//1 1 0 0 1 1 0 0 1 1 0 0 2 0 2 0 0 0 0 0 2 2 0 2 0 2 1 0 3 0 0 2
		//1 1 0 0 1 1 0 0 1 1 0 0 2 2 0 0 0 0 0 0 2 2 0 2 0 2 1 0 3 0 0 2
		//0 0 0 0 1 2 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 3 0 0 0 0 0
		//1 1 0 1 1 1 1 0 1 1 1 0 2 0 0 2 0 0 0 0 0 0 1 0 0 3 0 2 0 0 0 2
		//1 0 0 1 1 0 0 1 1 1 0 0 0 0 1 2 0 0 0 0 0 0 0 0 0 1 0 1 0 3 0 0
		//0 0 0 0 4 0 1 0 0 0 0 0 0 2 2 0 0 0 2 0 2 2 0 2 0 0 0 0 2 0 0 0
		
		String[] estados = new String[20];
		
		/*estados[0] = "0 4 0 0 0 0 0 0 0 2 0 0 1 0 0 1 0 0 0 0 2 2 3 1 0 0 0 0 0 0 0 0";
		estados[1] = "1 0 1 0 1 0 1 1 0 2 0 0 0 2 2 1 0 1 2 1 0 2 0 0 0 0 2 0 0 0 0 2";
		estados[2] = "1 0 1 0 2 0 0 2 1 1 0 1 1 0 0 2 0 0 0 2 2 2 0 0 0 0 0 2 0 3 2 0";
		estados[3] = "0 0 0 1 1 1 1 1 0 0 0 1 1 0 2 0 1 1 2 2 0 2 0 2 2 2 2 0 0 0 0 2";
		estados[4] = "0 0 0 0 0 0 0 0 0 0 1 1 0 1 1 1 0 0 0 1 1 2 2 2 0 0 2 2 0 2 0 0";
		estados[5] = "1 0 1 1 1 1 1 0 0 1 1 2 2 0 1 2 2 1 0 1 1 2 0 2 2 0 2 2 2 2 2 2";
		estados[6] = "0 0 3 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0 0 2 0 0 2 2 0 0 3 0 0 0 0 0";
		estados[7] = "0 0 1 0 1 0 1 0 0 1 0 0 2 1 1 2 2 0 0 1 2 0 0 0 0 2 2 2 0 0 0 0";
		estados[8] = "1 1 1 1 0 0 0 0 0 1 1 1 1 1 0 1 0 0 2 2 2 2 2 2 2 2 0 0 0 2 2 0";
		estados[9] = "0 4 0 0 1 0 0 1 2 1 1 1 0 1 0 1 0 0 0 1 2 0 2 2 2 0 2 0 0 0 2 2";
		estados[10] = "0 0 0 0 0 0 0 0 0 0 0 1 0 0 4 0 0 0 4 2 1 0 0 0 3 0 0 3 0 2 0 0";
		estados[11] = "1 1 1 1 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 2 2 2 2 0 0 0 0 0 2 0 0 2";
		estados[12] = "1 0 1 0 1 1 0 1 0 0 0 0 2 1 2 0 0 0 2 1 0 0 0 0 2 0 2 0 0 2 0 2";
		estados[13] = "0 0 1 0 1 0 0 0 0 0 0 1 0 2 4 0 0 0 0 2 0 2 0 0 2 1 1 0 0 0 0 0";
		estados[14] = "4 0 1 0 0 0 1 2 0 4 0 2 0 0 0 3 0 0 0 0 0 0 1 0 3 0 0 0 0 0 2 0";
		estados[15] = "1 1 1 1 1 1 1 1 0 0 1 1 0 0 0 0 2 2 2 0 0 0 2 0 0 0 2 2 2 2 2 2";
		estados[16] = "4 1 0 0 1 0 0 0 0 0 0 4 0 0 0 2 0 0 3 0 0 0 0 0 0 0 0 0 2 0 3 0";
		estados[17] = "0 0 0 0 0 1 0 0 1 0 2 1 2 0 0 0 0 2 2 4 0 1 0 0 3 3 0 0 0 0 2 0";
		estados[18] = "0 1 0 1 0 0 1 1 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 2 0 2 1 2 2 2 2";
		estados[19] = "1 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 2 2 2 2 2 2 2 2 2 2 2 2";*/
		
		/*estados[0] = "0 1 1 1 1 0 1 1 0 1 1 1 0 0 0 0 0 0 2 0 2 0 0 0 2 2 2 0 2 2 2 2";
		estados[1] = "1 1 1 0 1 1 1 1 0 0 0 1 2 0 0 0 0 1 2 2 1 0 2 0 2 0 2 2 0 2 2 2";
		estados[2] = "1 1 1 0 1 0 1 0 1 1 1 1 0 1 0 0 0 2 2 0 2 0 2 0 0 0 2 2 2 2 2 2";
		estados[3] = "1 1 1 0 1 1 0 1 1 0 0 1 2 2 2 0 0 1 1 0 0 0 0 0 2 2 2 0 2 2 2 2";
		estados[4] = "0 1 1 0 1 1 1 0 1 1 0 1 0 1 0 1 0 2 2 0 2 2 2 0 0 2 2 2 0 2 2 0";
		estados[5] = "1 1 1 0 0 1 0 1 0 1 1 0 2 1 0 0 0 0 0 1 0 2 2 0 2 0 2 2 0 2 2 2";
		estados[6] = "1 1 1 0 0 1 0 1 0 1 1 0 2 1 0 0 0 0 0 1 0 2 2 0 0 0 2 2 2 2 2 2";
		estados[7] = "1 0 1 0 0 1 1 0 1 0 1 1 0 1 0 1 2 0 0 0 2 0 2 0 0 2 2 2 2 2 0 2";
		estados[8] = "1 0 1 0 1 0 1 0 0 0 1 1 1 2 0 0 0 0 0 0 0 0 0 0 2 2 2 2 0 2 0 2";
		estados[9] = "1 1 0 0 0 1 0 0 0 0 1 1 1 0 0 1 0 0 0 0 2 0 0 0 2 2 0 2 2 0 2 2";
		estados[10] = "0 1 1 0 1 1 1 0 0 1 1 1 2 0 1 0 0 0 2 0 0 2 0 2 0 0 2 2 0 2 2 2";
		estados[11] = "1 1 1 0 0 1 1 0 0 1 1 1 2 1 0 0 0 2 2 1 2 0 2 0 0 0 2 0 2 2 2 2";
		estados[12] = "1 1 1 1 1 1 0 0 0 0 0 1 0 0 1 0 0 1 0 0 2 0 0 2 2 2 2 0 2 2 0 2";
		estados[13] = "1 0 1 1 1 1 1 1 0 0 0 0 0 0 1 0 2 0 1 0 0 2 0 0 2 2 2 2 0 2 2 2";
		estados[14] = "1 1 1 0 0 1 0 0 0 1 0 1 2 1 0 1 0 0 0 0 2 2 2 0 0 2 0 0 0 2 2 2";
		estados[15] = "1 1 1 0 1 0 0 1 0 1 0 1 1 0 0 0 0 2 1 2 2 0 0 0 0 2 2 2 2 2 2 0";
		estados[16] = "1 1 1 0 0 1 0 1 0 0 1 1 2 1 0 0 0 1 0 0 2 0 0 2 2 2 2 0 0 2 2 2";
		estados[17] = "0 1 1 0 1 1 0 0 1 1 1 1 0 0 0 1 0 0 0 2 2 2 2 0 0 2 2 2 0 0 2 2";
		estados[18] = "0 1 0 1 1 0 1 0 1 1 0 1 1 0 1 0 0 0 2 0 2 2 0 2 2 2 0 0 2 0 2 2";
		estados[19] = "1 1 1 1 0 1 1 1 1 0 1 1 1 0 1 0 0 0 0 2 2 2 2 2 2 2 2 0 2 2 2 2";*/
		
		estados[0] = "2 0 0 0 2 0 3 0 0 0 0 0 1 0 0 0 0 0 3 0 0 0 1 0 0 0 1 1 1 1 1 1";
		
		//0 1 1 0 1 1 1 0 1 1 0 1 0 1 0 1 0 2 2 0 2 2 2 0 0 2 2 2 0 2 2 0
		
		try {
			b = new Board(estados[0].split(" "));
		} catch (PlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bu.printBoard(b);
		
		
		/*for(int h = 0; h < b.getBoard().length; h++){
			
			if(estados[h] != null ){
				
				System.out.println("\nEstado " + h);
				
				String[] t = estados[h].split(" ");
				
				int[] newBoard = new int[32];
				
				for(int i = 0; i < 32; i++){
					
					switch(t[i]){
					case "1":
						newBoard[i] = BoardValues.BLACKMAN;
						break;
					case "2":
						newBoard[i] = BoardValues.REDMAN;
						break;
					case "0":
						newBoard[i] = BoardValues.EMPTY;
						break;
					case "3":
						newBoard[i] = BoardValues.BLACKKING;
						break;
					case "4":
						newBoard[i] = BoardValues.REDKING;
						break;
					}
				}
				
				b.setBoard(newBoard);
	
				bu.printBoard(b);
			}
		}*/
		
		for(int h = 0; h < estados.length; h++){
			System.out.println("#" + (h+1));
			System.out.println(estados[h] + "\t\t -0.1 +0.1 4");
		}

		/*int[] teste =  new int[32];
		teste[0] = BoardValues.EMPTY;
		teste[1] = BoardValues.EMPTY;
		teste[2] = BoardValues.EMPTY;
		teste[3] = BoardValues.REDKING;
		teste[4] = BoardValues.EMPTY;
		teste[5] = BoardValues.BLACKMAN;
		teste[6] = BoardValues.EMPTY;
		teste[7] = BoardValues.EMPTY;
		teste[8] = BoardValues.EMPTY;
		teste[9] = BoardValues.EMPTY;
		teste[10] = BoardValues.EMPTY;
		teste[11] = BoardValues.EMPTY;
		teste[12] = BoardValues.EMPTY;
		teste[13] = BoardValues.REDMAN;
		teste[14] = BoardValues.REDMAN;
		teste[15] = BoardValues.EMPTY;
		teste[16] = BoardValues.EMPTY;
		teste[17] = BoardValues.EMPTY;
		teste[18] = BoardValues.REDMAN;
		teste[19] = BoardValues.EMPTY;
		teste[20] = BoardValues.REDMAN;
		teste[21] = BoardValues.REDMAN;
		teste[22] = BoardValues.EMPTY;
		teste[23] = BoardValues.REDMAN;
		teste[24] = BoardValues.EMPTY;
		teste[25] = BoardValues.EMPTY;
		teste[26] = BoardValues.EMPTY;
		teste[27] = BoardValues.EMPTY;
		teste[28] = BoardValues.REDMAN;
		teste[29] = BoardValues.EMPTY;
		teste[30] = BoardValues.EMPTY;
		teste[31] = BoardValues.EMPTY;
		
		

		int[] teste2 = new int[36];

		bu.mapToXBoard(teste, teste2);
		
		XBoard xb = new XBoard();
		xb.setXBoard(teste2);

		b.setBoard(teste);

		bu.printBoard(b);

		//b.setXBoard(teste2);

		System.out.println("::::::::::::::XBOARD::::::::::::::");

		bu.printXBoard(xb);
		
		System.out.println("\n\n");
		for(int i = 0; i < teste.length; i++){
			System.out.print(teste[i] + " ");
		}*/
				
		/*Player2 p = new Player2(b);
		
		//TODO: Verificar de onde vem a quantidade de movimentos estabelecida para formar o vetor de movimentos (moves)
		Move[]jumps = new Move[5];
		Move[] moves = new Move[5];
		
		try {
			
			p.getChildren(BoardValues.REDMAN, BoardValues.REDKING, jumps, moves);
			
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("#################MOVES#############################");
		
		for(Move mv: moves){
			
			if(mv != null){
				System.out.println("From: " + mv.getFrom());
				System.out.println("To: " + mv.getTo() + "\n");
			}
		}
		
		System.out.println("################REALIZOU O MOVIMENTO###################");
		
		//p.makeMove(moves[0], b);
		bu.makeMove(jumps[0], b);
		
		System.out.println(":::::::::::::::BOARD:::::::::::::::");
		
		bu.printBoard(b);
		
		System.out.println("::::::::::::::XBOARD::::::::::::::");
		
		bu.printXBoard(b);
		
		System.out.println("################JUMPS##############################");
		
		for(Move mv: jumps){
			if(mv != null){
				System.out.println("From: " + mv.getFrom());
				System.out.println("To: " + mv.getTo() + "\n");
			}
		}*/
	}
}
