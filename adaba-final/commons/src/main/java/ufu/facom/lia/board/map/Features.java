package ufu.facom.lia.board.map;

import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.InvalidPositionException;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.Configuration;
import ufu.facom.lia.interfaces.EvalNet;
import ufu.facom.lia.interfaces.Jumps;

public class Features {

	private BoardUtils bu;

	public Features() {
	}

	public Features(BoardUtils bu) {
		this.bu = bu;
	}

	/**
	 * Calculates the next bit of a feature
	 * 
	 * @param b
	 * @return
	 */
	public int calculateNextBit(int[] b) throws InvalidBoardException {

		if (b == null) {
			throw new InvalidBoardException();
		}

		b[Configuration.FEATURE_POSITION] /= 2;

		if (b[Configuration.FEATURE_POSITION] == 1 || (b[Configuration.FEATURE_POSITION] > 0 && b[Configuration.FEATURE_POSITION] % 2 == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * advancement (3 bits) :: pieces in 5/6 rows gets points, 3/4 get minus.
	 * 
	 * @param b
	 * @return
	 */
	public int advancement(int[] b) {

		int i, black = 0;

		for (i = 16; i <= 23; i++) {
			if (b[i] == BoardValues.BLACKMAN) {
				black++;
			}
		}
		for (i = 8; i <= 15; i++) {
			if (b[i] == BoardValues.BLACKMAN) {
				black--;
			}
		}

		// -8 to 0 = represented by 0-7
		// 0 to 8 = represented by 8-15

		b[Configuration.FEATURE_POSITION] = black + 8;
		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] < 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * apex (1 bit) :: The parameter is debited with 1 if there are no kings on
	 * the board, if either square 7 or 26 is occupied by an active man, and if
	 * neither of these squares is occupied by a passive man.
	 * 
	 * @param b
	 * @return
	 */
	public int apex(int[] b) {

		int i;

		// Verify if exists any king over the board
		for (i = 0; i < 32; i++) {
			if (b[i] == BoardValues.BLACKKING || b[i] == BoardValues.REDKING) {
				return EvalNet.ONVALUE;
			}
		}
		// Verify if the square 6 or 25 are occupied by a red piece and if there
		// is not any piece red or black in squares 6 or 25
		if ((b[6] == BoardValues.REDMAN || b[25] == BoardValues.REDMAN)
				&& (b[6] != BoardValues.BLACKMAN && b[25] != BoardValues.BLACKMAN)) {
			return EvalNet.OFFVALUE;
		} else {
			return EvalNet.ONVALUE;
		}
	}

	/**
	 * backRowBridge (1 bit):: The parameter is credited with 1 if there are no
	 * active kings on the board and if the two bridge squares (1 and 3, or 30
	 * and 32) is the back row are occupied by passive pieces.
	 * 
	 * @param b
	 * @return
	 */
	public int backRowBridge(int[] b) {

		for (int i = 0; i < 32; i++) {
			if (b[i] == BoardValues.REDMAN) {
				return EvalNet.OFFVALUE;
			}
		}

		if (b[Configuration.FEATURE_POSITION] == BoardValues.BLACKMAN && b[Configuration.FEATURE_POSITION] == BoardValues.BLACKMAN) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Return number of given pieces in the centre squares
	 * 
	 * @param board
	 * @param boardValues
	 * @return
	 */
	private int checkCentre(int[] board, int boardValue) {

		int ret = 0;

		if (board[9] == boardValue)
			ret++;
		if (board[10] == boardValue)
			ret++;
		if (board[13] == boardValue)
			ret++;
		if (board[14] == boardValue)
			ret++;
		if (board[17] == boardValue)
			ret++;
		if (board[18] == boardValue)
			ret++;
		if (board[21] == boardValue)
			ret++;
		if (board[22] == boardValue)
			ret++;

		return ret - 1;

	}

	/**
	 * Centre Control I (3 bits):: The parameter is credited with 1 for each of
	 * the following squares: 11, 12, 15, 16, 20, 21, 24 and 25 which is
	 * occupied by a passive man.
	 * 
	 * @param b
	 * @return
	 */
	public int centreControl(int[] b) {

		b[Configuration.FEATURE_POSITION] = checkCentre(b, BoardValues.BLACKMAN);

		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Takes xBoard coord numbers! Return direction place could be moved to in
	 * 
	 * @param to
	 * @return
	 */
	private int possibleDirections(int to) {

		int ret;

		if (to == 4) {
			ret = Jumps.SW;
		} else if (to == 32) {
			ret = Jumps.NE;
		} else if (to <= 3) {
			ret = Jumps.SE + Jumps.SW;
		} else if (to >= 33) {
			ret = Jumps.NE + Jumps.NW;
		} else if (to == 13 || to == 22 || to == 31) {
			ret = Jumps.NW + Jumps.SW;
		} else if (to == 5 || to == 14 || to == 23) {
			ret = Jumps.NE + Jumps.SE;
		} else {
			ret = Jumps.NE + Jumps.NW + Jumps.SE + Jumps.SW;
		}

		return ret;
	}

	/**
	 * Return directions in which red can move to a square must make sure it's
	 * empty! USE THE XBOARD
	 * 
	 * @param b
	 * @param to
	 * @return
	 */
	private int redCanMoveTo(int[] xb, int to) {

		int from, ret = 0;

		from = possibleDirections(to);

		if ((from & Jumps.NE) == Jumps.NE) {
			if (xb[to - 4] == BoardValues.REDKING) {
				ret += Jumps.NE;
			}
		}

		if ((from & Jumps.NW) == Jumps.NW) {
			if (xb[to - 5] == BoardValues.REDKING) {
				ret += Jumps.NW;
			}
		}

		if ((from & Jumps.SE) == Jumps.SE) {
			if (xb[to + 5] == BoardValues.REDMAN || xb[to + 5] == BoardValues.REDKING) {
				ret += Jumps.SE;
			}
		}

		if ((from & Jumps.SW) == Jumps.SW) {
			if (xb[to + 4] == BoardValues.REDMAN || xb[to + 4] == BoardValues.REDKING) {
				ret += Jumps.SW;
			}
		}

		return ret;
	}

	/**
	 * Return directions in which black can move to a square - must make sure
	 * it's empty!
	 * 
	 * @param b
	 * @param to
	 * @return
	 */
	private int blackCanMoveTo(int[] xb, int to) {

		int from, ret = 0;

		from = possibleDirections(to);

		if ((from & Jumps.SE) == Jumps.SE) {
			if (xb[to + 5] == BoardValues.BLACKKING) {
				ret += Jumps.SE;
			}
		}

		if ((from & Jumps.SW) == Jumps.SW) {
			if (xb[to + 4] == BoardValues.BLACKKING) {
				ret += Jumps.SW;
			}
		}

		if ((from & Jumps.NE) == Jumps.NE) {
			if (xb[to - 4] == BoardValues.BLACKMAN || xb[to - 4] == BoardValues.BLACKKING) {
				ret += Jumps.NE;
			}
		}

		if ((from & Jumps.NW) == Jumps.NW) {
			if (xb[to - 5] == BoardValues.BLACKMAN || xb[to - 5] == BoardValues.BLACKKING) {
				ret += Jumps.NW;
			}
		}

		return ret;
	}

	/**
	 * Auxiliar function rCentreControlCalc
	 * 
	 * @param b
	 * @return
	 */
	private int rCentreControlCalc(int[] b, int[] xb) {

		int ret = 0;

		if ((b[9] == BoardValues.REDMAN)
				|| (b[9] == BoardValues.EMPTY && redCanMoveTo(xb, 11) > 0))
			ret++;
		if ((b[10] == BoardValues.REDMAN)
				|| (b[10] == BoardValues.EMPTY && redCanMoveTo(xb, 12) > 0))
			ret++;
		if ((b[13] == BoardValues.REDMAN)
				|| (b[13] == BoardValues.EMPTY && redCanMoveTo(xb, 15) > 0))
			ret++;
		if ((b[14] == BoardValues.REDMAN)
				|| (b[14] == BoardValues.EMPTY && redCanMoveTo(xb, 16) > 0))
			ret++;
		if ((b[17] == BoardValues.REDMAN)
				|| (b[17] == BoardValues.EMPTY && redCanMoveTo(xb, 20) > 0))
			ret++;
		if ((b[18] == BoardValues.REDMAN)
				|| (b[18] == BoardValues.EMPTY && redCanMoveTo(xb, 21) > 0))
			ret++;
		if ((b[21] == BoardValues.REDMAN)
				|| (b[21] == BoardValues.EMPTY && redCanMoveTo(xb, 24) > 0))
			ret++;
		if ((b[22] == BoardValues.REDMAN)
				|| (b[22] == BoardValues.EMPTY && redCanMoveTo(xb, 25) > 0))
			ret++;

		return ret - 1;

	}

	/**
	 * Centre control II (3 bits) :: The parameter is credited with 1 for each
	 * of the following squares: 10, 11, 14, 15, 19, 20, 23 and 24 that is
	 * either currently occupied by an active piece or to wich an active piece
	 * can move.
	 * 
	 * @param b
	 * @return
	 */
	public int xCentreControl(int[] b) {

		// Estou assumindo que o tabuleiro já está sincronizado board e xBoard
		// XBOARD xb;
		// maptoxboard(b, &xb);
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}

		b[Configuration.FEATURE_POSITION] = (rCentreControlCalc(b, xb));

		if (b[Configuration.FEATURE_POSITION] == 1 || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Cramp (2 bits) :: The parameter is credited with 2 if the passive side
	 * occupies the cramping square (12 for black, and 19 for red) and at least
	 * one other nearby square (8 or 13 for black, and 18 and 23 for red), while
	 * certain squares (16, 20, 21 and 24 for black, and 7, 10, 11 and 15 for
	 * red) are all occupied by the active side.
	 * 
	 * @param b
	 * @return
	 */
	public int cramp(int[] b) {
		int ret = 0;

		if ((b[12] == BoardValues.BLACKMAN || b[12] == BoardValues.BLACKKING)
				&& (b[8] == BoardValues.BLACKMAN || b[8] == BoardValues.BLACKKING
						|| b[13] == BoardValues.BLACKMAN || b[13] == BoardValues.BLACKKING)) {

			if ((b[16] != BoardValues.REDMAN && b[16] != BoardValues.REDKING)
					|| (b[20] != BoardValues.REDMAN && b[20] != BoardValues.REDKING)
					|| (b[21] != BoardValues.REDMAN && b[21] != BoardValues.REDKING)
					|| (b[24] != BoardValues.REDMAN && b[24] != BoardValues.REDKING)) {

				ret = 0;
			} else {
				ret = 2;
			}

		}

		b[Configuration.FEATURE_POSITION] = ret;

		if (b[Configuration.FEATURE_POSITION] == 2 || (b[Configuration.FEATURE_POSITION] > 0 && b[Configuration.FEATURE_POSITION] % 2 == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * Auxiliar function :: possibleJumpsRed
	 * 
	 * @param b
	 * @param p
	 * @return
	 */
	private int possibileJumpsRed(int[] xb, int p) {

		int ret = Jumps.NONE;

		// Os cantos não permitem jumps para o jogador BLACK
		if (p <= 4 || p >= 32 || p == 5 || p == 14 || p == 23 || p == 13 || p == 22 || p == 31) {
			ret = Jumps.NONE;
		} else {
			if ((xb[p + 4] == BoardValues.REDMAN || xb[p + 4] == BoardValues.REDKING)
					&& xb[p - 4] == BoardValues.EMPTY) {
				ret += Jumps.SW;
			}
			if ((xb[p + 5] == BoardValues.REDMAN || xb[p + 5] == BoardValues.REDKING)
					&& xb[p - 5] == BoardValues.EMPTY) {
				ret += Jumps.SE;
			}
			if (xb[p - 5] == BoardValues.REDKING && xb[p + 5] == BoardValues.EMPTY) {
				ret += Jumps.NW;
			}
			if (xb[p - 4] == BoardValues.REDKING && xb[p + 4] == BoardValues.EMPTY) {
				ret += Jumps.NE;
			}
		}

		return ret;
	}

	/**
	 * Auxiliar function :: tests each jump from pos to see if it would not
	 * result in an exchange
	 * 
	 * @return
	 * @throws InvalidPositionException
	 */
	private boolean testJumpsBlack_noExch(int[] xb, int p_red, int dirct_black) throws InvalidPositionException {
		boolean captured1 = true, captured2 = true, captured3 = true;
		boolean checkking;
		int ret_jumps, p_black;

		if (dirct_black == -1 && (p_red <= 4 || p_red >= 32 || p_red == 5 || p_red == 14 || p_red == 23 || p_red == 13
				|| p_red == 22 || p_red == 31)) {
			return false;
		} else {

			if ((xb[p_red + 4] == BoardValues.BLACKKING && xb[p_red - 4] == BoardValues.EMPTY
					&& dirct_black == -1) || (dirct_black == Jumps.SW)) {

				// verificar necessidade de cópia do tabuleiro, acho que não
				// precisa -->> copyboard(&bxc, b);
				checkking = bu.jumpPiece(xb, p_red + 4, Jumps.SW);

				p_black = p_red - 4;

				if (!checkking && (ret_jumps = bu.jumpsAvail(xb, p_black)) > Jumps.NONE) {

					if ((ret_jumps & Jumps.NW) == Jumps.NW) {
						captured1 = testJumpsBlack_noExch(xb, p_black + 5, Jumps.NW);
					}

					if ((ret_jumps & Jumps.SW) == Jumps.SW && captured1) {
						captured2 = testJumpsBlack_noExch(xb, p_black - 4, Jumps.SW);
					}

					if ((ret_jumps & Jumps.SE) == Jumps.SE && captured1 && captured2) {
						captured3 = testJumpsBlack_noExch(xb, p_black - 5, Jumps.SE);
					}

					// Verifica se não houve capturas
					if (!captured1 || !captured2 || !captured3) {
						if (dirct_black == -1) {
							return true;
						} else {
							return false;
						}

					} else if (dirct_black != -1) {
						return true;
					}
				}
			}

			if ((xb[p_red + 5] == BoardValues.BLACKKING && xb[p_red - 5] == BoardValues.EMPTY
					&& dirct_black == -1) || (dirct_black == Jumps.SE)) {

				// verificar necessidade de copiar tabuleiro -->>
				// copyboard(&bxc, b);

				checkking = bu.jumpPiece(xb, p_red + 5, Jumps.SE);
				p_black = p_red - 5;

				if (!checkking && (ret_jumps = bu.jumpsAvail(xb, p_black)) > Jumps.NONE) {

					if ((ret_jumps & Jumps.NE) == Jumps.NE) {
						captured1 = testJumpsBlack_noExch(xb, p_black + 4, Jumps.NE);
					}

					if ((ret_jumps & Jumps.SW) == Jumps.SW && captured1) {
						captured2 = testJumpsBlack_noExch(xb, p_black - 4, Jumps.SW);
					}

					if ((ret_jumps & Jumps.SE) == Jumps.SE && captured1 && captured2) {
						captured3 = testJumpsBlack_noExch(xb, p_black - 5, Jumps.SE);
					}

					if (!captured1 || !captured2 || !captured3) {
						if (dirct_black == -1) {
							return true;
						} else {
							return false;
						}
					} else if (dirct_black != -1) {
						return true;
					}

				}
			}

			if (((xb[p_red - 5] == BoardValues.BLACKMAN || xb[p_red - 5] == BoardValues.BLACKKING)
					&& xb[p_red + 5] == BoardValues.EMPTY && dirct_black == -1)
					|| (dirct_black == Jumps.NW)) {

				// verificar necessidade de copiar tabuleiro -->>
				// copyboard(&bxc, b);

				checkking = bu.jumpPiece(xb, p_red - 55, Jumps.NW);
				p_black = p_red + 5;

				if (!checkking && (ret_jumps = bu.jumpsAvail(xb, p_black)) > Jumps.NONE) {

					if ((ret_jumps & Jumps.NW) == Jumps.NW) {
						captured1 = testJumpsBlack_noExch(xb, p_black + 5, Jumps.NW);
					}

					if ((ret_jumps & Jumps.NE) == Jumps.SW && captured1) {
						captured2 = testJumpsBlack_noExch(xb, p_black + 4, Jumps.NE);
					}

					if ((ret_jumps & Jumps.SW) == Jumps.SW && captured1 && captured2) {
						captured3 = testJumpsBlack_noExch(xb, p_black - 4, Jumps.SW);
					}

					if (!captured1 || !captured2 || !captured3) {
						return (dirct_black == -1);

					} else if (dirct_black != -1) {
						return true;
					}

				} else {

					// tem q ver se alguem pode comer o preto
					if (possibileJumpsRed(xb, p_black) > Jumps.NONE) {
						if (dirct_black != -1)
							return true;
					} else
						return (dirct_black == -1);

				}
			}

			if (((xb[p_red - 4] == BoardValues.BLACKMAN || xb[p_red - 4] == BoardValues.BLACKKING)
					&& xb[p_red + 4] == BoardValues.EMPTY && dirct_black == -1)
					|| (dirct_black == Jumps.NE)) {

				// verificar necessidade de copiar o tabuleiro -->>
				// copyboard(&bxc, b);

				checkking = bu.jumpPiece(xb, p_red - 4, Jumps.NE);
				p_black = p_red + 4;

				if (!checkking && (ret_jumps = bu.jumpsAvail(xb, p_black)) > Jumps.NONE) {

					if ((ret_jumps & Jumps.NE) == Jumps.NE) {
						captured1 = testJumpsBlack_noExch(xb, p_black + 4, Jumps.NE);
					}

					if ((ret_jumps & Jumps.SE) == Jumps.SE && captured1) {
						captured2 = testJumpsBlack_noExch(xb, p_black - 5, Jumps.SE);
					}

					if ((ret_jumps & Jumps.NW) == Jumps.NW && captured1 && captured2) {
						captured3 = testJumpsBlack_noExch(xb, p_black + 5, Jumps.NW);
					}

					// Verifica se não houve capturas
					if (captured1 || captured2 || captured3) {
						return (dirct_black == -1);
					} else {
						if (possibileJumpsRed(xb, p_black) > Jumps.NONE) {
							if (dirct_black == -1)
								return true;
						} else {
							return (dirct_black == -1);
						}
					}
				}
			}
		}

		// por default o retorno é sempre zero (houve captura da peça preta
		// depois que ela capturou a vermelha)
		return false;
	}

	/**
	 * Denial of Occupancy (4 bits) :: The parameter is credited with 1 for each
	 * square defined in MOB if on the next move a piece occupying this square
	 * could be captured without an exchange. MOB:: is credited with 1 for each
	 * square to which the active side could move one or more pieces in the
	 * normal fashion, disregarding the fact that jump moves may or may not be
	 * available.
	 * 
	 * Observation:: The function MOB (total mobilility) was implemented by Mark
	 * Lynch.
	 * 
	 * @param b
	 * @return
	 * @throws CloneNotSupportedException
	 * @throws InvalidPositionException
	 */
	public int denialOfOccupancy(int[] b) {

		int i, dir;
		boolean retAux = false, ret = false;

		int[] xb, backup;
		xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		try {

			// Verificar a necessidade de copiar o tabuleiro maptoxboard(pboard,
			// &xb);

			for (i = 1; i <= 35; i++) {
				if (i == 9 || i == 18 || i == 27) {
					continue;
				}

				if (xb[i] == BoardValues.EMPTY) {
					retAux = false;
				}

				// Verify the condition MOV
				if ((dir = redCanMoveTo(xb, i)) > Jumps.NONE) {
					if ((dir & Jumps.NE) == Jumps.NE) {

						backup = bu.copyBoard(xb);
						
						bu.makeMove(i - 4, i, backup);

						retAux = testJumpsBlack_noExch(backup, i, -1);
					}
				}

				if ((dir & Jumps.NW) == Jumps.NW && !retAux) {

					backup = xb.clone();

					bu.makeMove(i - 5, i, backup);

					retAux = testJumpsBlack_noExch(backup, i, -1);

				}

				if ((dir & Jumps.SE) == Jumps.SE && !retAux) {

					backup = xb.clone();

					bu.makeMove(i + 5, i, backup);

					retAux = testJumpsBlack_noExch(backup, i, -1);

				}

				if ((dir & Jumps.SW) == Jumps.SW && !retAux) {

					backup = xb.clone();

					bu.makeMove(i + 4, i, backup);

					retAux = testJumpsBlack_noExch(backup, i, -1);
				}

				ret = retAux;

			}

			if (!ret) {
				b[Configuration.FEATURE_POSITION] = 0;
			} else {
				b[Configuration.FEATURE_POSITION] = 1;
			}

			// FIXME: Existe uma condição para verificar se o
			// setFeatureEvaluation >
			// 15, todavia a nossa implementação com booleano não permite esta
			// contagem, se der diferença precisaremos verificar como ajustar
			if (b[Configuration.FEATURE_POSITION] == 1 || (b[Configuration.FEATURE_POSITION] > 0 && b[Configuration.FEATURE_POSITION] % 2 == 1)) {
				return EvalNet.ONVALUE;
			} else {
				return EvalNet.OFFVALUE;
			}

		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}

		return EvalNet.OFFVALUE;
	}

	/**
	 * Double Diagonal (4 bits) :: The parameter is credited with 1 for each
	 * passive piece located in the diagonal files (fila) terminating in the
	 * double-corner squares.
	 * 
	 * @param b
	 * @return
	 */
	public int doubleDiagonal(int[] b) {

		int ret = 0;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 1; i <= 31; i += 5) {
			if (xb[i] == BoardValues.BLACKMAN || xb[i] == BoardValues.BLACKKING) {
				ret++;
			}

			if (xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING) {
				ret++;
			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Diagonal Moment (4 bits) :: The parameter is credited with 1/2 for each
	 * passive piece located oon squares 2 removed (distante) from the
	 * double-corner diagonal files, with 1 for each passive piece located on
	 * squares 1 removed from the double-corner files and with 3/2 for each
	 * passive in the double-corner files (filas).
	 * 
	 * Observação: Provavelmente Lynch multiplicouu +2 para cada crédito defindo
	 * por Samuel.
	 * 
	 * Diagonal moment value +1 for two removed +2 for one removed +3 for on it
	 * 
	 * @param b
	 * @return
	 */
	public int diagonalMoment(int[] b) {
		int ret = 0;

		for (int i = 0; i < 32; i++) {
			if (b[i] == BoardValues.BLACKMAN || b[i] == BoardValues.BLACKKING) {
				if (i == 3 || i == 28) {
					ret += 0;
				} else if (i == 2 || i == 7 || i == 11 || i == 20 || i == 24 || i == 29) {
					ret += 1;
				} else if (i == 1 || i == 6 || i == 10 || i == 15 || i == 19 || i == 12 || i == 16 || i == 21 || i == 25
						|| i == 30) {
					ret += 2;
				} else {
					ret += 3;
				}
			}
		}
		
		b[Configuration.FEATURE_POSITION] = ret;

		if (b[Configuration.FEATURE_POSITION] == 1 || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Dyke (3 bits) :: The parameter is credited with 1 for each string of
	 * passive pieces that occupy three adjacent diagonal squares.
	 * 
	 * @param b
	 * @return
	 */
	public int dyke(int[] b) {

		int limited1, limited2, qtdAdjSquare1, qtdAdjSquare2;
		int ret = 0;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		// Variáveis auxiliares para pegar o limite de um percurso pelas
		// diagonais
		limited1 = 1;
		limited2 = 6;

		for (int i = 1; i <= 4; i++) {
			qtdAdjSquare1 = 0;
			qtdAdjSquare2 = 0;

			for (int j = 1; j <= ((limited1 * 4) + i); j += 4) {

				if (xb[j] == BoardValues.BLACKMAN || xb[j] == BoardValues.BLACKKING) {
					qtdAdjSquare1++;
				} else {
					qtdAdjSquare1 = 0;
				}

				if (qtdAdjSquare1 == 3) {
					ret++;
					qtdAdjSquare1 = 0;
				}
			}

			for (int j = 1; j <= ((limited2 * 5) + i); j += 5) {

				if (xb[j] == BoardValues.BLACKMAN || xb[j] == BoardValues.BLACKKING) {
					qtdAdjSquare2++;
				} else {
					qtdAdjSquare2 = 0;
				}

				if (qtdAdjSquare2 == 3) {
					ret++;
					qtdAdjSquare2 = 0;
				}
			}

			limited1 += 2;
			limited2 -= 2;
		}

		limited1 = 5;
		limited2 = 2;

		for (int i = 33; i <= 35; i++) {
			qtdAdjSquare1 = 0;
			qtdAdjSquare2 = 0;

			for (int j = i; i >= (i - (limited1 * 4)); j -= 4) {

				if (xb[j] == BoardValues.BLACKMAN || xb[j] == BoardValues.BLACKKING) {
					qtdAdjSquare1++;
				} else {
					qtdAdjSquare1 = 0;
				}

				if (qtdAdjSquare1 == 3) {
					ret++;
					qtdAdjSquare1 = 0;
				}
			}

			for (int j = i; i >= (i - (limited2 * 5)); j -= 5) {

				if (xb[j] == BoardValues.BLACKMAN || xb[j] == BoardValues.BLACKKING) {
					qtdAdjSquare2++;
				} else {
					qtdAdjSquare2 = 0;
				}

				if (qtdAdjSquare2 == 3) {
					ret++;
					qtdAdjSquare2 = 0;
				}
			}

			limited1 -= 2;
			limited2 += 2;
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if (b[Configuration.FEATURE_POSITION] > 7 || b[Configuration.FEATURE_POSITION] == 1
				|| (b[Configuration.FEATURE_POSITION] > 0 && b[Configuration.FEATURE_POSITION] % 2 == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	@SuppressWarnings("unused")
	private int redCanMoveTo2(int[] xb, int to, int pRed) {

		int from = 0;
		int ret = 0;

		from = possibleDirections(to);

		if ((from & Jumps.NE) == Jumps.NE) {
			if (to - 4 == pRed && xb[to - 4] == BoardValues.REDKING) {
				ret += Jumps.NE;
			}
		}

		if ((from & Jumps.NW) == Jumps.NW) {
			if (to - 5 == pRed && xb[to - 5] == BoardValues.REDKING) {
				ret += Jumps.NW;
			}
		}

		if ((from & Jumps.SE) == Jumps.SE) {
			if (to + 5 == pRed) {
				ret += Jumps.SE;
			}
		}

		if ((from & Jumps.SW) == Jumps.SW) {
			if (to + 4 == pRed) {
				ret += Jumps.SW;
			}
		}

		return ret;
	}

	@SuppressWarnings("unused")
	private boolean testJumpsBlackWithExch(int[] xb, int pRed, int dirctBlack) throws InvalidPositionException, CloneNotSupportedException {
		int retJumps, pBlack = 0;
		boolean checkking, noCaptured1 = true, noCaptured2 = true, noCaptured3 = true;
		int[] bxc = null;

		if (dirctBlack == -1 && (pRed <= 4 || pRed >= 32 || pRed == 5 || pRed == 14 || pRed == 23 || pRed == 13
				|| pRed == 22 || pRed == 31)) {
			return false;
		} else {
			if ((xb[pRed + 4] == BoardValues.BLACKKING && xb[pRed - 4] == BoardValues.EMPTY
					&& dirctBlack == -1) || (dirctBlack == Jumps.SW)) {

				// verificar necessidade de copiar tabuleiro -->>
				// copyboard(&bxc, b);
				try {
					bxc = bu.copyBoard(xb);
				} catch (InvalidBoardException e) {
					e.printStackTrace();
				}

				checkking = bu.jumpPiece(bxc, pRed + 4, Jumps.SW);

				pBlack = pRed - 4;

				if (!checkking && (retJumps = bu.jumpsAvail(bxc, pBlack)) > Jumps.NONE) {

					if ((retJumps & Jumps.NW) == Jumps.NW) {
						noCaptured1 = testJumpsBlackWithExch(bxc, pBlack + 5, Jumps.NW);
					}

					if ((retJumps & Jumps.SW) == Jumps.SW && noCaptured1) {
						noCaptured2 = testJumpsBlackWithExch(bxc, pBlack - 4, Jumps.SW);
					}

					if ((retJumps & Jumps.SE) == Jumps.SE && noCaptured1 && noCaptured2) {
						noCaptured3 = testJumpsBlackWithExch(bxc, pBlack - 5, Jumps.SE);
					}

					if (!noCaptured1 || !noCaptured2 || !noCaptured3) {
						return (dirctBlack == -1);
					} else if (dirctBlack != -1) {
						return true;
					}

				}

			} else {
				if (possibileJumpsRed(xb, pBlack) > Jumps.NONE) {
					return (dirctBlack == -1);
				} else if (dirctBlack != -1) {
					return true;
				}
			}

			if ((xb[pRed + 5] == BoardValues.BLACKKING && xb[pRed - 5] == BoardValues.EMPTY
					&& dirctBlack == -1) || (dirctBlack == Jumps.SE)) {

				// verificar necessidade de copiar tabuleiro -->>
				// copyboard(&bxc, b);
				try {
					bxc = bu.copyBoard(xb);
				} catch (InvalidBoardException e) {
					e.printStackTrace();
				}

				checkking = bu.jumpPiece(bxc, pRed + 5, Jumps.SE);

				pBlack = pRed - 5;

				if (!checkking && (retJumps = bu.jumpsAvail(bxc, pBlack)) > Jumps.NONE) {

					if ((retJumps & Jumps.NE) == Jumps.NE) {
						noCaptured1 = testJumpsBlackWithExch(bxc, pBlack + 4, Jumps.NE);
					}

					if ((retJumps & Jumps.SW) == Jumps.SW && noCaptured1) {
						noCaptured2 = testJumpsBlackWithExch(bxc, pBlack - 4, Jumps.SW);
					}

					if ((retJumps & Jumps.SE) == Jumps.SE && noCaptured1 && noCaptured2) {
						noCaptured3 = testJumpsBlackWithExch(bxc, pBlack - 5, Jumps.SE);
					}

					if (!noCaptured1 || !noCaptured2 || !noCaptured3) {
						return (dirctBlack == -1);
					} else if (dirctBlack != -1) {
						return true;
					}

				}

			} else {
				if (possibileJumpsRed(xb, pBlack) > Jumps.NONE) {
					return (dirctBlack == -1);
				} else if (dirctBlack != -1) {
					return true;
				}
			}

			if (((xb[pRed - 5] == BoardValues.BLACKMAN || xb[pRed - 5] == BoardValues.BLACKKING)
					&& xb[pRed + 5] == BoardValues.EMPTY && dirctBlack == -1) || (dirctBlack == Jumps.NW)) {

				try {
					bxc = bu.copyBoard(xb);
				} catch (InvalidBoardException e) {
					e.printStackTrace();
				}

				checkking = bu.jumpPiece(bxc, pRed - 5, Jumps.NW);

				pBlack = pRed + 5;

				if (!checkking && (retJumps = bu.jumpsAvail(bxc, pBlack)) > Jumps.NONE) {

					if ((retJumps & Jumps.NW) == Jumps.NW) {
						noCaptured1 = testJumpsBlackWithExch(bxc, pBlack + 5, Jumps.NW);
					}

					if ((retJumps & Jumps.NE) == Jumps.NE && noCaptured1) {
						noCaptured2 = testJumpsBlackWithExch(bxc, pBlack + 4, Jumps.NE);
					}

					if ((retJumps & Jumps.SW) == Jumps.SW && noCaptured1 && noCaptured2) {
						noCaptured3 = testJumpsBlackWithExch(bxc, pBlack - 4, Jumps.SW);
					}

					if (!noCaptured1 || !noCaptured2 || !noCaptured3) {
						return (dirctBlack == -1);
					} else if (dirctBlack != -1) {
						return true;
					}

				}

			} else {
				if (possibileJumpsRed(xb, pBlack) > Jumps.NONE) {
					return (dirctBlack == -1);
				} else if (dirctBlack != -1) {
					return true;
				}
			}

			if (((xb[pRed - 4] == BoardValues.BLACKMAN || xb[pRed - 4] == BoardValues.BLACKKING)
					&& xb[pRed + 4] == BoardValues.EMPTY && dirctBlack == -1) || (dirctBlack == Jumps.NE)) {

				// verificar necessidade de copiar tabuleiro -->>
				// copyboard(&bxc, b);
				
				try {
					bxc = bu.copyBoard(xb);
				} catch (InvalidBoardException e) {
					e.printStackTrace();
				}

				checkking = bu.jumpPiece(bxc, pRed - 4, Jumps.NE);

				pBlack = pRed + 4;

				if (!checkking && (retJumps = bu.jumpsAvail(bxc, pBlack)) > Jumps.NONE) {

					if ((retJumps & Jumps.NE) == Jumps.NE) {
						noCaptured1 = testJumpsBlackWithExch(bxc, pBlack + 4, Jumps.NE);
					}

					if ((retJumps & Jumps.SE) == Jumps.SE && noCaptured1) {
						noCaptured2 = testJumpsBlackWithExch(bxc, pBlack - 5, Jumps.SE);
					}

					if ((retJumps & Jumps.NW) == Jumps.NW && noCaptured1 && noCaptured2) {
						noCaptured3 = testJumpsBlackWithExch(bxc, pBlack + 5, Jumps.NW);
					}

					if (!noCaptured1 || !noCaptured2 || !noCaptured3) {
						return (dirctBlack == -1);
					} else if (dirctBlack != -1) {
						return true;
					}

				}

			} else {
				if (possibileJumpsRed(xb, pBlack) > Jumps.NONE) {
					return (dirctBlack == -1);
				} else if (dirctBlack != -1) {
					return true;
				}

				return false;
			}
		}
		return false;
	}

	/**
	 * Exchange (4 bits) :: The parameter is credited with 1 for each square to
	 * which the active side may advance a piece and, in so doing, force an
	 * exchange.
	 * 
	 * @param b
	 * @return
	 */
	public int exchange(int[] b) {
		return 0;
	}

	/**
	 * Exposure (3 bits) :: The parameter is credited with 1 for each passive
	 * piece that is flanked along one or the other diagonal by two empty
	 * squares.
	 * 
	 * @param b
	 * @return
	 */
	public int exposure(int[] b) {

		int ret = 0;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 6; i <= 30; i++) {
			if (xb[i] == BoardValues.BLACKMAN || xb[i] == BoardValues.BLACKKING) {
				if (i == 13 || i == 14 || i == 22 || i == 23) {
					ret += 0;
				} else {
					if ((xb[i - 4] == BoardValues.EMPTY && xb[i + 4] == BoardValues.EMPTY)
							|| (xb[i - 5] == BoardValues.EMPTY
									&& xb[i + 5] == BoardValues.EMPTY)) {
						ret++;
					}
				}
			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Auxiliar function :: This function verifies the squares around 'i' and 'i
	 * + 1' that is empty; besides this, verifies where the red piece of the
	 * board can be moved.
	 * 
	 * @param b
	 * @param posI
	 * @return
	 */
	@SuppressWarnings("unused")
	private int possibleBlanks(XBoard b, int posI) {
		int ret = Jumps.NONE;

		if (posI == 4) {
			if (b.getXBoard()[posI + 4] == BoardValues.EMPTY) {
				ret += Jumps.NE;
			}
		} else if (posI == 32) {
			if (b.getXBoard()[posI - 4] == BoardValues.EMPTY) {
				ret += Jumps.SW;
			}
		} else if (posI <= 3) {
			if (b.getXBoard()[posI + 4] == BoardValues.EMPTY) {
				ret += Jumps.NE;
			}
			if (b.getXBoard()[posI + 5] == BoardValues.EMPTY) {
				ret += Jumps.NW;
			}
		} else if (posI >= 33) {
			if (b.getXBoard()[posI - 5] == BoardValues.EMPTY) {
				ret += Jumps.SE;
			}
			if (b.getXBoard()[posI - 4] == BoardValues.EMPTY) {
				ret += Jumps.SW;
			}
		} else if (posI == 13 || posI == 22 || posI == 31) {
			if (b.getXBoard()[posI + 4] == BoardValues.EMPTY) {
				ret += Jumps.NE;
			}
			if (b.getXBoard()[posI - 5] == BoardValues.EMPTY) {
				ret += Jumps.SE;
			}
		} else {
			if (b.getXBoard()[posI + 4] == BoardValues.EMPTY) {
				ret += Jumps.NE;
			}
			if (b.getXBoard()[posI - 5] == BoardValues.EMPTY) {
				ret += Jumps.SE;
			}
			if (b.getXBoard()[posI + 5] == BoardValues.EMPTY) {
				ret += Jumps.NW;
			}
			if (b.getXBoard()[posI - 4] == BoardValues.EMPTY) {
				ret += Jumps.SW;
			}
		}

		return ret;
	}

	/**
	 * Auxiliar function:: This function verifies for one red piece in 'fromRed'
	 * if it is 'ameaçada' for a position 'posI1' or 'posI2'. Besides this, this
	 * function verifies if a move of some red piece for the position 'fromRed'
	 * (move executed for 'fork' function) didn't resulted in eliminates 'posI1'
	 * or 'posI2'
	 * 
	 * @param b
	 * @param fromRed
	 * @param posI1
	 * @param posI2
	 * @return
	 */
	@SuppressWarnings("unused")
	private int threatOnePieces(int[] xb, int fromRed, int posI1, int posI2) {

		int ret = 0;

		// square 31 or 35
		if (bu.inQuadrant(fromRed) == 1 || bu.inQuadrant(fromRed) == 2) {
			// direction SE
			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 10] == BoardValues.EMPTY
					&& (fromRed - 5 == posI1 || fromRed - 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
			// squares 29, 30, 33 ou 34
		} else if (bu.inQuadrant(fromRed) == 2 || bu.inQuadrant(fromRed) == 3) {
			// Verifies iif the black pieces continues in the place, after the
			// move of the red piece for the position 'fromRed'
			// Direction SW
			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 8] == BoardValues.EMPTY && (fromRed - 4 == posI1 || fromRed - 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (bu.inQuadrant(fromRed) == 4) {
			if ((xb[fromRed] == BoardValues.REDKING) && xb[fromRed + 8] == BoardValues.EMPTY
					&& (fromRed + 4 == posI1 || fromRed + 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 10] == BoardValues.EMPTY
					&& (fromRed - 5 == posI1 || fromRed - 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (fromRed == 11 || fromRed == 12 || fromRed == 15 || fromRed == 16 || fromRed == 20 || fromRed == 21
				|| fromRed == 24 || fromRed == 25) {
			if ((xb[fromRed] == BoardValues.REDKING) && xb[fromRed + 8] == BoardValues.EMPTY
					&& (fromRed + 4 == posI1 || fromRed + 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 10] == BoardValues.EMPTY
					&& (fromRed - 5 == posI1 || fromRed - 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 10] == BoardValues.EMPTY)
					&& (fromRed + 5 == posI1 || fromRed + 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 8] == BoardValues.EMPTY && (fromRed - 4 == posI1 || fromRed - 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (bu.inQuadrant(fromRed) == 6) {
			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 10] == BoardValues.EMPTY)
					&& (fromRed + 5 == posI1 || fromRed + 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDMAN || xb[fromRed] == BoardValues.REDKING)
					&& xb[fromRed - 8] == BoardValues.EMPTY && (fromRed - 4 == posI1 || fromRed - 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (bu.inQuadrant(fromRed) == 7) {
			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 8] == BoardValues.EMPTY)
					&& (fromRed + 4 == posI1 || fromRed + 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (bu.inQuadrant(fromRed) == 8) {
			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 8] == BoardValues.EMPTY)
					&& (fromRed + 4 == posI1 || fromRed + 4 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}

			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 10] == BoardValues.EMPTY)
					&& (fromRed + 5 == posI1 || fromRed + 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		} else if (bu.inQuadrant(fromRed) == 9) {
			if ((xb[fromRed] == BoardValues.REDKING || xb[fromRed + 10] == BoardValues.EMPTY)
					&& (fromRed + 5 == posI1 || fromRed + 5 == posI2)
					&& (xb[posI1] == BoardValues.BLACKMAN || xb[posI1] == BoardValues.BLACKKING)
					&& (xb[posI2] == BoardValues.BLACKMAN
							|| xb[posI2] == BoardValues.BLACKKING)) {
				return 1;
			}
		}

		return 0;

	}

	/**
	 * Threat of Fork (3 bits) :: The parameter is credited with 1 for each
	 * situation in which passive pieces occupy two adjacent squares in one row
	 * and in which there are three empty squares so disposed that the active
	 * side could, by occupying one of them, threaten a sure capture of one or
	 * the other of the two pieces.
	 * 
	 * @param b
	 * @return
	 */
	public int fork(int[] b) {
		return 0;
	}

	public int gap(int[] b) {

		int ret = 0, i;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (i = 1; i <= 35; i++) {
			// canto inferior :: "or that separates a passive piece from the
			// edge of the board"
			if (xb[i] == BoardValues.EMPTY && i == 4) {
				// NE
				if (xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// canto superior :: "or that separates a passive piece from the
			// edge of the board"
			else if (xb[i] == BoardValues.EMPTY && i == 32) {
				// SW
				if (xb[i - 4] == BoardValues.BLACKMAN || xb[i - 4] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// margem inferior :: "or that separates a passive piece from the
			// edge of the board"
			else if (xb[i] == BoardValues.EMPTY && i <= 3) {
				// NE
				if (xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING) {
					ret++;
				}
				// NW
				else if (xb[i + 5] == BoardValues.BLACKMAN
						|| xb[i + 5] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// margem superior :: "or that separates a passive piece from the
			// edge of the board"
			else if (xb[i] == BoardValues.EMPTY && i >= 33) {
				// SE
				if (xb[i - 5] == BoardValues.BLACKMAN || xb[i - 5] == BoardValues.BLACKKING) {
					ret++;
				}
				// SW
				else if (xb[i - 4] == BoardValues.BLACKMAN
						|| xb[i - 4] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// margem esquerda :: "or that separates a passive piece from the
			// edge of the board"
			else if (xb[i] == BoardValues.EMPTY && (i == 13 || i == 22 || i == 31)) {
				// NE
				if (xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING) {
					ret++;
				}
				// SE
				else if (xb[i - 5] == BoardValues.BLACKMAN
						|| xb[i - 5] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// margem direita :: "or that separates a passive piece from the
			// edge of the board"
			else if (xb[i] == BoardValues.EMPTY && (i == 5 || i == 14 || i == 23)) {
				// NW
				if (xb[i + 5] == BoardValues.BLACKMAN || xb[i + 5] == BoardValues.BLACKKING) {
					ret++;
				}
				// SW
				else if (xb[i - 4] == BoardValues.BLACKMAN
						|| xb[i - 4] == BoardValues.BLACKKING) {
					ret++;
				}
			}
			// centro :: "single empty square that separates two passive pieces
			// along a diagonal"
			else if (xb[i] == BoardValues.EMPTY) {
				// NE + SW
				if ((xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING)
						&& (xb[i - 4] == BoardValues.BLACKMAN
								|| xb[i - 4] == BoardValues.BLACKKING)) {
					ret++;
				}
				// NW + SE
				else if ((xb[i + 5] == BoardValues.BLACKMAN || xb[i + 5] == BoardValues.BLACKKING)
						&& (xb[i - 5] == BoardValues.BLACKMAN
								|| xb[i - 5] == BoardValues.BLACKKING)) {
					ret++;
				}
			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 7 || (b[Configuration.FEATURE_POSITION] == 1)
				|| b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2 == 1))) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * Black Row Control (1 bit) :: The parameter is credited with 1 if there
	 * are no active kings and if either the Bridge or the Triangle of Oreo is
	 * occupied by passive pieces.
	 * 
	 * @param b
	 * @return
	 */
	public int backRowControl(int[] b) {

		for (int i = 0; i < 32; i++) {
			if (b[i] == BoardValues.REDKING) {
				return EvalNet.OFFVALUE;
			}
		}

		// Bridge 1-3
		if ((b[0] == BoardValues.BLACKMAN || b[0] == BoardValues.BLACKKING)
				&& (b[2] == BoardValues.BLACKMAN || b[2] == BoardValues.BLACKKING)) {
			return EvalNet.ONVALUE;
		}
		// Triangle of Oreo
		else if ((b[1] == BoardValues.BLACKMAN || b[1] == BoardValues.BLACKKING)
				&& (b[2] == BoardValues.BLACKMAN || b[2] == BoardValues.BLACKKING)
				&& (b[6] == BoardValues.BLACKMAN || b[6] == BoardValues.BLACKKING)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * HOLE (3 bits) :: The parameter is credited with 1 for each empty square
	 * that is surrounded by three or more passive pieces.
	 * 
	 * @param b
	 * @return
	 */
	public int hole(int[] b) {

		int ret = 0, retaux;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 6; i <= 30; i++) {
			retaux = 0;

			if (xb[i] == BoardValues.EMPTY && i != 9 && i != 13 && i != 14 && i != 18 && i != 22 && i != 23
					&& i != 27) {

				// NE
				if (xb[i + 4] == BoardValues.BLACKMAN || xb[i + 4] == BoardValues.BLACKKING) {
					retaux++;
				}
				// SW
				if (xb[i - 4] == BoardValues.BLACKMAN || xb[i - 4] == BoardValues.BLACKKING) {
					retaux++;
				}
				// NW
				if (xb[i + 5] == BoardValues.BLACKMAN || xb[i + 5] == BoardValues.BLACKKING) {
					retaux++;
				}
				// SE
				if (xb[i - 5] == BoardValues.BLACKMAN || xb[i - 5] == BoardValues.BLACKKING) {
					retaux++;
				}
				// that is surrounded by three or more passive pieces
				if (retaux >= 3) {
					ret++;
				}
			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * King Center Control (3 bits) :: The parameter is credited with 1 for each
	 * of the following squares: 11, 12, 15, 16, 20, 21, 24 and 25 which is
	 * occupied by a passive king.
	 * 
	 * @param b
	 * @return
	 */
	public int kingCentreControl(int[] b) {

		int retblack = checkCentre(b, BoardValues.BLACKKING);

		b[Configuration.FEATURE_POSITION] = (retblack);

		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Total Mobility (4 bits) :: The parameter is credited with 1 for each
	 * square to which the active side could move one or more pieces in the
	 * normal fashion, disregarding the fact that jump moves may or may not be
	 * available.
	 * 
	 * Total Mobility - any square where the red player can move to... uses
	 * extended board
	 * 
	 * @param b
	 * @return
	 */
	public int totalMobility(int[] b) {
		
		int ret = 0;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 1; i <= 35; i++) {
			if (xb[i] == BoardValues.EMPTY) {
				if (redCanMoveTo(xb, i) > Jumps.NONE) {
					ret++;
				}
			}

		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 15) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Undenied Mobility (3 bits) :: The parameter is credited with the
	 * difference between MOB and DENY.
	 * 
	 * @param b
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public int undeniedMobility(int[] b) {
		int ret;

		int[] bMob, bDeny;

		try {
			bMob = bu.copyBoard(b);

			bDeny = bu.copyBoard(b);
			
			totalMobility(bMob);
			
			denialOfOccupancy(bDeny);

			ret = bMob[Configuration.FEATURE_POSITION] - bDeny[Configuration.FEATURE_POSITION];

			b[Configuration.FEATURE_POSITION] = ret;

			if ((b[Configuration.FEATURE_POSITION] > 15) || (b[Configuration.FEATURE_POSITION] == 1)
					|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
				return EvalNet.ONVALUE;
			} else {
				return EvalNet.OFFVALUE;
			}
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}

		return EvalNet.OFFVALUE;

	}

	/**
	 * Node (3 bits) :: The parameter is credited with 1 for each passive piece
	 * that is surrounded by al least three empty squares.
	 * 
	 * @param b
	 * @return
	 */
	public int node(int[] b) {

		int ret = 0, retaux;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 6; i <= 30; i++) {
			retaux = 0;

			if ((xb[i] == BoardValues.BLACKMAN || xb[i] == BoardValues.BLACKKING) && i != 9
					&& i != 13 && i != 14 && i != 18 && i != 22 && i != 23 && i != 27) {

				// NE
				if (xb[i + 4] == BoardValues.EMPTY) {
					retaux++;
				}

				// SW
				if (xb[i - 4] == BoardValues.EMPTY) {
					retaux++;
				}

				// NW
				if (xb[i + 5] == BoardValues.EMPTY) {
					retaux++;
				}

				// SE
				if (xb[i - 5] == BoardValues.EMPTY) {
					retaux++;
				}

				// that is surrounded by at least three empty squares
				if (retaux >= 3) {
					ret++;
				}

			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * Triangle of Oreo (1 bit) :: The parameter is credited 1 if there are no
	 * passive kings and if the Triangle of Oreo (squares 2, 3 and 7 for Black,
	 * and squares 26, 30 and 31 for White) is occupied by passive pieces.
	 * 
	 * @param b
	 * @return
	 */
	public int triangleOfOreo(int[] b) {

		for (int i = 0; i < 32; i++) {
			if (b[i] == BoardValues.REDKING) {
				return EvalNet.OFFVALUE;
			}
		}

		if ((b[1] == BoardValues.BLACKMAN || b[1] == BoardValues.BLACKKING)
				&& (b[2] == BoardValues.BLACKMAN || b[2] == BoardValues.BLACKKING)
				&& (b[6] == BoardValues.BLACKMAN || b[6] == BoardValues.BLACKKING)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}

	}

	/**
	 * Pole (3 bits) :: The parameter is credited with 1 for each passive man
	 * that is completely surrounded by empty squares.
	 * 
	 * @param b
	 * @return
	 */
	public int pole(int[] b) {
		
		int ret = 0;
		
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		for (int i = 6; i <= 30; i++) {

			// for each passive man
			if (xb[i] == BoardValues.BLACKMAN && i != 9 && i != 13 && i != 14 && i != 18 && i != 22
					&& i != 23 && i != 27) {

				// NE + SW + NW + SE :: that is completely surrounded by empty
				// squares
				if (xb[i + 4] == BoardValues.EMPTY && xb[i - 4] == BoardValues.EMPTY
						&& xb[i + 5] == BoardValues.EMPTY && xb[i - 5] == BoardValues.EMPTY) {
					ret++;
				}
			}
		}

		b[Configuration.FEATURE_POSITION] = ret;

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
	}

	/**
	 * Threat (3 bits) :: The parameter is credited with 1 for each square to
	 * which an active piece may be moved and in so doing threaten the capture
	 * of a passive piece on a subsequent move.
	 * 
	 * @param b
	 * @return
	 * @throws CloneNotSupportedException
	 * @throws InvalidPositionException
	 */
	public int threat(int[] b) {

		try {
			
			int[] xb = new int[XBoard.XBOARD_LENGTH];
			
			try {
				bu.mapToXBoard(b, xb);
			} catch (InvalidBoardException e1) {
				e1.printStackTrace();
			}
			
			int ret = 0, retaux, i, j;

			int[] bkp = null;

			for (i = 1; i <= 35; i++) {

				if (i == 9 || i == 18 || i == 27) {
					continue;
				}

				if (xb[i] == BoardValues.EMPTY) {

					// for each square to which an active piece may be moved
					retaux = 0;

					if ((j = redCanMoveTo(xb, i)) > Jumps.NONE) {

						// MOVE TO NE
						if ((j & Jumps.NE) == Jumps.NE) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i - 4, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO NW
						if ((j & Jumps.NW) == Jumps.NW) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i - 5, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO SE
						if ((j & Jumps.SE) == Jumps.SE) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i + 5, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO SW
						if ((j & Jumps.SW) == Jumps.SW) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i + 4, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// and in so doing threaten the capture of a passive
						// piece
						// on a subsequent move
						if (retaux == 1) {
							ret++;
						}
					}
				}
			}

			b[Configuration.FEATURE_POSITION] = ret;

			if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
					|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
				return EvalNet.ONVALUE;
			} else {
				return EvalNet.OFFVALUE;
			}

		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		return EvalNet.OFFVALUE;
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Taken (3 bits) :: The parameter is credited with 1 for each square to
	 * which an passive piece may be moved an in so doing threaten the capture
	 * of a active piece on a subsequent move.
	 * 
	 * @param b
	 * @return
	 * @throws CloneNotSupportedException
	 * @throws InvalidPositionException
	 */
	public int taken(int[] b) {

		try {
			int ret = 0, retaux, i, j;

			int[] bkp = null;
			int[] xb = new int[Configuration.FEATURE_POSITION];
			
			try {
				bu.mapToXBoard(b, xb);
			} catch (InvalidBoardException e1) {
				e1.printStackTrace();
			}

			for (i = 1; i <= 35; i++) {
				if (i == 9 || i == 18 || i == 27) {
					continue;
				}

				if (xb[i] == BoardValues.EMPTY) {

					// for each square to which an passive piece may be moved
					retaux = 0;
					if ((j = blackCanMoveTo(xb, i)) > Jumps.NONE) {

						// MOVE TO NE
						if ((j & Jumps.NE) == Jumps.NE) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i - 4, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO NW
						if ((j & Jumps.NW) == Jumps.NW) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i - 5, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO SE
						if ((j & Jumps.SE) == Jumps.SE) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i + 5, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// MOVE TO SW
						if ((j & Jumps.SW) == Jumps.SW) {
							bkp = bu.copyBoard(xb);
							bu.makeMove(i + 4, i, bkp);
							if (bu.jumpsAvail(bkp, i) > Jumps.NONE) {
								retaux = 1;
							}
						}

						// and in so doing threaten the capture of a active
						// piece on
						// a subsequent move
						if (retaux == 1) {
							ret++;
						}
					}
				}
			}

			b[Configuration.FEATURE_POSITION] = ret;

			if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
					|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
				return EvalNet.ONVALUE;
			} else {
				return EvalNet.OFFVALUE;
			}

		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		
		return EvalNet.OFFVALUE;

	}

	/**
	 * Auxiliar function
	 * 
	 * @param b
	 * @return 2 for man, 3 for king
	 */
	private int pieceCount(int[] b) {

		int count = 0;

		for (int i = 0; i < 32; i++) {
			switch (b[i]) {
			case BoardValues.BLACKMAN:
				count += 2;
				break;
			case BoardValues.REDMAN:
				count -= 2;
				break;
			case BoardValues.BLACKKING:
				count += 3;
				break;
			case BoardValues.REDKING:
				count -= 3;
				break;
			default:
				count += 0;
			}
		}

		return count;
	}

	/**
	 * Piece Advantage (4 bits) :: set to 1 if person has a piece advantage
	 * 
	 * @param b
	 * @return
	 */
	public int pieceAdvantage(int[] b) {

		b[Configuration.FEATURE_POSITION]= pieceCount(b);

		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/

	}

	/**
	 * Piece Disadvantage (4 bits) :: if person has disadvantage
	 * 
	 * @param b
	 * @return
	 */
	public int pieceDisadvantage(int[] b) {

		b[Configuration.FEATURE_POSITION] = pieceCount(b);

		if (b[Configuration.FEATURE_POSITION] >= 0) {
			b[Configuration.FEATURE_POSITION] = 0;
		} else {
			b[Configuration.FEATURE_POSITION] = Math.abs(b[Configuration.FEATURE_POSITION]);
		}

		if ((b[Configuration.FEATURE_POSITION] == 1) || (b[Configuration.FEATURE_POSITION] < 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Auxiliar function
	 * 
	 * @param b
	 * @return
	 * @throws InvalidPositionException
	 */
	private int calcBlackPieceThreat(int[] xb) throws InvalidPositionException {

		int ret = 0, j;

		for (int i = 1; i <= 35; i++) {
			if (xb[i] == BoardValues.REDMAN || xb[i] == BoardValues.REDKING) {
				if ((j = bu.jumpsAvail(xb, i)) != Jumps.NONE) {
					if (j == 3 || j == 5 || j == 6 || j > 8) {
						ret += 2;
					} else {
						ret++;
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Auxiliar function
	 * 
	 * @param b
	 * @return
	 * @throws InvalidPositionException
	 */
	private int calcCredPieceThreat(int[] xb) throws InvalidPositionException {

		int ret = 0, j;

		for (int i = 1; i <= 35; i++) {
			if (xb[i] == BoardValues.BLACKMAN || xb[i] == BoardValues.BLACKKING) {
				if ((j = bu.jumpsAvail(xb, i)) != Jumps.NONE) {
					if (j == 3 || j == 5 || j == 6 || j > 8) {
						ret += 2;
					} else {
						ret++;
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Piece Threat (3 bits) :: could opponent take me
	 * 
	 * @param b
	 * @throws InvalidPositionException
	 */
	public int pieceThreat(int[] b) {
		
		try{
			
		int[] xb = new int[XBoard.XBOARD_LENGTH];

		bu.mapToXBoard(b, xb);

		b[Configuration.FEATURE_POSITION] = calcBlackPieceThreat(xb);

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		}catch(InvalidPositionException e){
			e.printStackTrace();
		} catch (InvalidBoardException e) {
			e.printStackTrace();
		}
		
		return EvalNet.OFFVALUE;
		
		/*if (b[Configuration.FEATURE_POSITION]<0) 
			return EvalNet.OFFVALUE;
		else 
			return b[Configuration.FEATURE_POSITION];*/
	}

	/**
	 * Piece Threat (3 bits) :: if we could move again, could we take a piece? -
	 * he couldn't take any
	 * 
	 * @param b
	 * @return
	 * @throws InvalidPositionException
	 */
	public int pieceTake(int[] b) {
		
		try{
			
		int[] xb = new int[XBoard.XBOARD_LENGTH];
		
		try {
			bu.mapToXBoard(b, xb);
		} catch (InvalidBoardException e1) {
			e1.printStackTrace();
		}

		b[Configuration.FEATURE_POSITION] = calcCredPieceThreat(xb);

		if ((b[Configuration.FEATURE_POSITION] > 7) || (b[Configuration.FEATURE_POSITION] == 1)
				|| (b[Configuration.FEATURE_POSITION] > 0 && (b[Configuration.FEATURE_POSITION] % 2) == 1)) {
			return EvalNet.ONVALUE;
		} else {
			return EvalNet.OFFVALUE;
		}
		
		}catch(InvalidPositionException e){
			e.printStackTrace();
		}
		
		return EvalNet.OFFVALUE;
	}
	

}