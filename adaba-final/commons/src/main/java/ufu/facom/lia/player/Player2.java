package ufu.facom.lia.player;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.InvalidPositionException;
import ufu.facom.lia.interfaces.Constants;
import ufu.facom.lia.interfaces.Jumps;

public class Player2 {
	
	private BoardUtils bu;
	
	private Board b;
	
	//TODO: inicializar estas variáveis no método suggestMoves
	private int nJumps;
	
	//TODO: inicializar estas variáveis no método suggestMoves
	private int nMoves;
	
	public Player2(Board b){
		this.b = b;
		bu = new BoardUtils();
		
		//TODO: retirar
		nJumps = 0;
		nMoves = 0;
	}
	
	public int[] getChildren(int piece, int king, Move[] jumps, Move[] moves) throws InvalidPositionException{
		
		int pos = 0, j = 0, dir = 0, mDir = 0;
		
		int[] seqDirs = new int[Constants.MAXCOMB];
		
		XBoard xb = new XBoard();

		try {
			
			bu.mapToXBoard(b.getBoard(), xb.getXBoard());
			
			for(int i = 1; i <= Constants.BOARD_LENGTH; i++){
				if(b.getBoard()[i-1] == piece || b.getBoard()[i-1] == king){
					//where can i jump to?
					pos = bu.mapToXCoord(i);
					j = bu.jumpsAvail(xb.getXBoard(), pos);
					//jumps?
					if(j != Jumps.NONE){
						//for each jump - record
						for(dir = 1; dir <= 8; dir = dir * 2){
							if((j & dir) == dir){
								//Limpa a seq. de pulos - como em java um array de tipo primitivo inicia com o valor padrão não precisa limpar aki até descobrir oq esse vetor faz
								/*for(int idir = 0; idir < Constants.MAXCOMB; idir++){
									seqDirs[idir] = 0;
								}*/
								//Funcao auxiliar que monta o vetor de JUMPS
								retPosTo(pos, pos, xb.getXBoard(), dir, seqDirs, 0, jumps);
								
							}	
						}
					}else{
						mDir = bu.possibleMoves(xb.getXBoard(), pos);
						if(mDir != Jumps.NONE){
							for(dir = 1; dir <= 8; dir = dir * 2){
								if((mDir & dir) == dir){
									moves[nMoves] = new Move();
									moves[nMoves].setFrom(pos);
									moves[nMoves].setTo(pos + bu.poscalc(dir));
									moves[nMoves].setExchange(0);
									nMoves++;
								}
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//caso existam pulos, somente eles interessam
		//processChildren(jumps, nJumps, moves, nMoves);
		
		return seqDirs;
		
	}
	
	@SuppressWarnings("unused")
	private void processChildren(Move[] jumps, int nJumps, Move[] moves, int nMoves){
		if(nJumps >= 1){
			for(int i = 0; i < nMoves; i++){
				moves[i] = new Move();
			}
			nMoves = 0;
		}
	}
	
	public void retPosTo(int fromIni, int from, int[] xBoard, int dir, int[] pFather, int iLevel, Move[] jumps) throws InvalidBoardException, InvalidPositionException{
		
		int[] xBoardTemp = null;
		int j, newFrom, newDir, iDir;
		boolean finished;
		
		xBoardTemp = bu.copyBoard(xBoard);
		pFather[iLevel] = dir;
		
		finished = bu.jumpPiece(xBoardTemp, from, dir);
		newFrom = from + (bu.poscalc(dir) * 2);
		
		//Verifica se existe mais algum pulo
		if(!finished){
			if((j = bu.jumpsAvail(xBoardTemp, newFrom)) != Jumps.NONE){
				//Verifica para cada jump
				for(newDir = 1; newDir <= 8; newDir = newDir * 2){
					if((j & newDir) == newDir){
						//Função auxiliar que monta o vetor de jumps
						retPosTo(fromIni, newFrom, xBoardTemp, newDir, pFather, (iLevel + 1), jumps);
					}
				}
			}else{
				for(iDir = (iLevel +1); iDir < Constants.MAXCOMB; iDir++){
					pFather[iDir] = 0;
					jumps[nJumps] = new Move();
					jumps[nJumps].setFrom(fromIni);
					jumps[nJumps].setTo(newFrom);
					jumps[nJumps].setExchange(1);
					
					for(iDir = 0; iDir < Constants.MAXCOMB; iDir++){
						jumps[nJumps].getSeqDirs()[iDir] = pFather[iDir];
					}
					nJumps++;
				}
			}
			//Neste caso a peça chegou no topo do tabuleiro e formou dama
		}else{
			for(iDir = (iLevel + 1); iDir < Constants.MAXCOMB; iDir++){
				pFather[iDir] = 0;
				jumps[nJumps] = new Move();
				jumps[nJumps].setFrom(fromIni);
				jumps[nJumps].setTo(newFrom);
				jumps[nJumps].setExchange(1);
				
				for(iDir = 0; iDir < Constants.MAXCOMB; iDir++){
					jumps[nJumps].getSeqDirs()[iDir] = pFather[iDir];
				}
				nJumps++;
			}
		}
		
	}
}
