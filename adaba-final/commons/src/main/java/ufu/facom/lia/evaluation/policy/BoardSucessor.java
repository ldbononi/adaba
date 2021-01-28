package ufu.facom.lia.evaluation.policy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.InvalidPositionException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.hash.ZobristKey;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.Configuration;
import ufu.facom.lia.interfaces.Constants;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.Jumps;
import ufu.facom.lia.interfaces.Location;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;

public class BoardSucessor implements Sucessor, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private BoardUtils bu;

	private Board b;
	
	private XBoard xb;
	
	//private static Long nodeId = 1l;
	
	private ZobristKey zobrist;
	
	//private Utilities utilities;

	// TODO: inicializar estas variáveis no método suggestMoves
	//private int nJumps;

	// TODO: inicializar estas variáveis no método suggestMoves
	//private int nMoves;

	public BoardSucessor() {
		bu = new BoardUtils();
		zobrist = new ZobristKey();
		//utilities = new Utilities();

		// TODO: retirar
		//nJumps = 0;
		//nMoves = 0;
	}

	@Override
	public List<INode> getSucessors(INode currentState, Player player) throws SearchException {
		
		Node node = (Node) currentState;

		List<INode> sucessors = new ArrayList<INode>(Configuration.INITIAL_CAPACITY_LIST);

		//int indexSucessors = 0;
		
		if(node.getBoard() == null){
			
			if(node.getBoard().getBoardRepresentation() == null){
				throw new SearchException("Representação do tabuleiro não informada.");
			}
			
			node.setBoard(new Board());
			
			bu.convertBoard(node.getBoard().getBoardRepresentation(), node.getBoard(), node.getxBoard());
			
		}
		
		try{
			b = node.getBoard();
			xb = node.getxBoard();
			
			if(xb == null){
				xb = new XBoard();
				node.setxBoard(xb);
			}
			
			bu.mapToXBoard(b.getBoard(), xb.getXBoard());
			
		}catch(InvalidBoardException e){
			e.printStackTrace();
		}

		//Move[] jumps = new Move[Configuration.TOTAL_MOVES_ARRAY];
		//Move[] moves = new Move[Configuration.TOTAL_MOVES_ARRAY];
		
		List<Move> jumps = new ArrayList<Move>(Configuration.INITIAL_CAPACITY_LIST);
		List<Move> moves = new ArrayList<Move>(Configuration.INITIAL_CAPACITY_LIST);
		
		/*Actions ac = new Actions();
		ac.setJumps(jumps);
		ac.setMoves(moves);*/

		int whoPlays, piece, king;

		//whoPlays = whoPlay(player);//comentar quando for GameOneMove
		whoPlays = player.getValue();//descomentar quando for GameOneMove
		
		//FIXME: descomentar esse trecho quando for GameOneMove e comentar o abaixo.
		//Caso o jogo tenha iniciado com o jogador RED, é necessário inverter a visão do tabuleiro em relação às peças, pois este método, por padrão, considera que o BLACK sempre inicia
		int whoStarts = Integer.parseInt(SystemConfigs.getInstance().getConfig("whoStarts").trim());

		/*if (whoPlays == BoardValues.BLACKMAN && whoStarts == BoardValues.BLACKMAN) {
			piece = BoardValues.BLACKMAN;
			king = BoardValues.BLACKKING;
		} else {
			piece = BoardValues.REDMAN;
			king = BoardValues.REDKING;
		}
		*/
		
		if (whoPlays == BoardValues.BLACKMAN && whoStarts == BoardValues.BLACKMAN) {
			piece = BoardValues.BLACKMAN;
			king = BoardValues.BLACKKING;
		} else {
			piece = BoardValues.REDMAN;
			king = BoardValues.REDKING;
		}
		
		try {
			getChildren(piece, king, jumps, moves);

			//The priority is of the jumps
			if (!jumps.isEmpty()) {

				for (Move mv : jumps) {

					if (mv != null) {
						//System.out.println("From: " + mv.getFrom());
						//System.out.println("To: " + mv.getTo() + "\n");

						Board temp = node.getBoard().clone();
						XBoard xTemp = node.getxBoard().clone();

						bu.makeMove(mv, xTemp.getXBoard());
						
						bu.mapToBoard(temp.getBoard(), xTemp.getXBoard());
						
						//TODO: retirar
						//System.out.println("Move: " + mv.getFrom() + "-" + mv.getTo());
						//bu.printBoard(temp);

						Node n = new Node();
						
						n.setBoard(temp);
						n.setxBoard(xTemp);
						//n.setParent(node);
						n.setDepth(node.getDepth() + 1);
						n.setVisited(false);
						n.setMove(mv);
						n.setPlayer(player);
						n.setKey(new Key(zobrist.getKey64(n), zobrist.getKey32(n)));
						n.setParentKey32(node.getKey().getKey32());
						n.setLocation(Location.NORMAL.getValue());
						//n.setRepresentation(n.getBoard().getBoardRepresentation());
						
						if(node.getType() == Type.MAX.getValue()){
							n.setType(Type.MIN.getValue());
						}else{
							n.setType(Type.MAX.getValue());
						}
						
						//n.setName("S" + n.getType() + String.valueOf(zobrist.getKey32(n)) + "S" + node.getType() + String.valueOf(zobrist.getKey32(node)));
						
						sucessors.add(n);
						//indexSucessors++;

						//sucessors[indexSucessors++] = n;
						
						/*if(indexSucessors >= sucessors.length){
							sucessors = (State[]) utilities.updateVectorLength(sucessors, Configuration.LENGTH_ARRAY_UPDATE);
						}*/
					}
				}
			}else if (!moves.isEmpty()) {
				for (Move mv : moves) {

					if (mv != null) {
						//System.out.println("From: " + mv.getFrom());
						//System.out.println("To: " + mv.getTo() + "\n");
						
						Board temp = node.getBoard().clone();
						XBoard xTemp = node.getxBoard().clone();

						bu.makeMove(mv, xTemp.getXBoard());
						
						bu.mapToBoard(temp.getBoard(), xTemp.getXBoard());

						//TODO: retirar
						//System.out.println("Move: " + mv.getFrom() + "-" + mv.getTo());
						//bu.printBoard(temp);

						Node n = new Node();

						n.setBoard(temp);
						n.setxBoard(xTemp);
						//n.setParent(node);
						n.setDepth(node.getDepth() + 1);
						n.setVisited(false);
						n.setMove(mv);
						n.setPlayer(player);
						n.setKey(new Key(zobrist.getKey64(n), zobrist.getKey32(n)));
						n.setParentKey32(node.getKey().getKey32());
						n.setLocation(Location.NORMAL.getValue());
						//n.setRepresentation(n.getBoard().getBoardRepresentation());
						
						if(node.getType() == Type.MAX.getValue()){
							n.setType(Type.MIN.getValue());
						}else{
							n.setType(Type.MAX.getValue());
						}

						//n.setName("S" + n.getType() + String.valueOf(zobrist.getKey32(n)) + "S" + node.getType() + String.valueOf(zobrist.getKey32(node)));
						
						sucessors.add(n);
						//sucessors[indexSucessors++] = n;
						
						/*if(indexSucessors >= sucessors.length){
							sucessors = (State[]) utilities.updateVectorLength(sucessors, Configuration.LENGTH_ARRAY_UPDATE);
						}*/
					}
				}
			}
			
			//If there is no jump or possible move, this node is a leaf
			if(jumps.isEmpty() && moves.isEmpty()){
				node.setType(Type.LEAF.getValue());
			}

		} catch (InvalidPositionException | CloneNotSupportedException e) {
			throw new SearchException("Erro ao gerar sucessores!");
		} catch (InvalidBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sucessors;
	}
	
	public int[] getChildren(int piece, int king, List<Move> jumps, List<Move> moves) throws InvalidPositionException {

		int pos = 0, j = 0, dir = 0, mDir = 0;

		int[] seqDirs = new int[Constants.MAXCOMB];

		try {
			for (int i = 1; i <= Constants.BOARD_LENGTH; i++) {
				if (b.getBoard()[i - 1] == piece || b.getBoard()[i - 1] == king) {
					// where can i jump to?
					pos = bu.mapToXCoord(i);
					j = bu.jumpsAvail(xb.getXBoard(), pos);
					// jumps?
					if (j != Jumps.NONE) {
						// for each jump - record
						for (dir = 1; dir <= 8; dir = dir * 2) {
							if ((j & dir) == dir) {
								// Limpa a seq. de pulos - como em java um array
								// de tipo primitivo inicia com o valor padrão
								// não precisa limpar aki até descobrir oq esse
								// vetor faz
								/*
								 * for(int idir = 0; idir < Constants.MAXCOMB;
								 * idir++){ seqDirs[idir] = 0; }
								 */
								// Funcao auxiliar que monta o vetor de JUMPS
								retPosTo(pos, pos, xb.getXBoard(), dir, seqDirs, 0, jumps);

							}
						}
					} else {
						mDir = bu.possibleMoves(xb.getXBoard(), pos);
						if (mDir != Jumps.NONE) {
							for (dir = 1; dir <= 8; dir = dir * 2) {
								if ((mDir & dir) == dir) {
									Move m = new Move();
									m.setFrom(pos);
									m.setTo(pos + bu.poscalc(dir));
									m.setExchange(0);
									moves.add(m);
									
									//nMoves++;
									
									/*if(nMoves >= actions.getMoves().length){
										actions.setMoves(utilities.updateVectorLength(actions.getMoves(), Configuration.LENGTH_ARRAY_UPDATE));
									}*/
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

		// caso existam pulos, somente eles interessam
		// processChildren(jumps, nJumps, moves, nMoves);

		return seqDirs;

	}

	/*public int[] getChildren(int piece, int king, Move[] jumps, Move[] moves) throws InvalidPositionException {

		int pos = 0, j = 0, dir = 0, mDir = 0;

		int[] seqDirs = new int[Constants.MAXCOMB];

		try {
			for (int i = 1; i <= Constants.BOARD_LENGTH; i++) {
				if (b.getBoard()[i - 1] == piece || b.getBoard()[i - 1] == king) {
					// where can i jump to?
					pos = bu.mapToXCoord(i);
					j = bu.jumpsAvail(b.getXBoard(), pos);
					// jumps?
					if (j != Jumps.NONE) {
						// for each jump - record
						for (dir = 1; dir <= 8; dir = dir * 2) {
							if ((j & dir) == dir) {
								// Limpa a seq. de pulos - como em java um array
								// de tipo primitivo inicia com o valor padrão
								// não precisa limpar aki até descobrir oq esse
								// vetor faz
								
								 * for(int idir = 0; idir < Constants.MAXCOMB;
								 * idir++){ seqDirs[idir] = 0; }
								 
								// Funcao auxiliar que monta o vetor de JUMPS
								retPosTo(pos, pos, b.getXBoard(), dir, seqDirs, 0, jumps);

							}
						}
					} else {
						mDir = bu.possibleMoves(b.getXBoard(), pos);
						if (mDir != Jumps.NONE) {
							for (dir = 1; dir <= 8; dir = dir * 2) {
								if ((mDir & dir) == dir) {
									moves[nMoves] = new Move();
									moves[nMoves].setFrom(pos);
									moves[nMoves].setTo(pos + bu.poscalc(dir));
									moves[nMoves].setExchange(0);
									nMoves++;
									
									if(nMoves >= moves.length){
										utilities.updateVectorLength(moves, Configuration.LENGTH_ARRAY_UPDATE);
									}
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

		// caso existam pulos, somente eles interessam
		// processChildren(jumps, nJumps, moves, nMoves);

		return seqDirs;

	}*/

	public void retPosTo(int fromIni, int from, int[] xBoard, int dir, int[] pFather, int iLevel, List<Move> jumps)
			throws InvalidBoardException, InvalidPositionException {

		int[] xBoardTemp = null;
		int j, newFrom, newDir, iDir;
		boolean finished;

		xBoardTemp = bu.copyBoard(xBoard);
		pFather[iLevel] = dir;

		finished = bu.jumpPiece(xBoardTemp, from, dir);
		newFrom = from + (bu.poscalc(dir) * 2);

		// Verifica se existe mais algum pulo
		if (!finished) {
			if ((j = bu.jumpsAvail(xBoardTemp, newFrom)) != Jumps.NONE) {
				// Verifica para cada jump
				for (newDir = 1; newDir <= 8; newDir = newDir * 2) {
					if ((j & newDir) == newDir) {
						// Função auxiliar que monta o vetor de jumps
						retPosTo(fromIni, newFrom, xBoardTemp, newDir, pFather, (iLevel + 1), jumps);
					}
				}
			} else {
				for (iDir = (iLevel + 1); iDir < Constants.MAXCOMB; iDir++) {
					pFather[iDir] = 0;
					Move jump = new Move();
					jump.setFrom(fromIni);
					jump.setTo(newFrom);
					jump.setExchange(1);

					for (iDir = 0; iDir < Constants.MAXCOMB; iDir++) {
						jump.getSeqDirs()[iDir] = pFather[iDir];
					}
					
					jumps.add(jump);
					/*nJumps++;
					
					if(nJumps >= actions.getJumps().length){
						actions.setJumps(utilities.updateVectorLength(actions.getJumps(), Configuration.LENGTH_ARRAY_UPDATE));
					}*/
					
				}
			}
			// Neste caso a peça chegou no topo do tabuleiro e formou dama
		} else {
			for (iDir = (iLevel + 1); iDir < Constants.MAXCOMB; iDir++) {
				pFather[iDir] = 0;
				Move jump = new Move();
				jump.setFrom(fromIni);
				jump.setTo(newFrom);
				jump.setExchange(1);

				for (iDir = 0; iDir < Constants.MAXCOMB; iDir++) {
					jump.getSeqDirs()[iDir] = pFather[iDir];
				}
				
				jumps.add(jump);
				
				/*nJumps++;
				
				if(nJumps >= actions.getJumps().length){
					actions.setJumps(utilities.updateVectorLength(actions.getJumps(), Configuration.LENGTH_ARRAY_UPDATE));
				}*/
			}
		}

	}
	
	@Override
	public String getName(INode n, INode parent){
		
		return "S" + n.getType() + String.valueOf(zobrist.getKey32(n)) + "S" + parent.getType() + String.valueOf(zobrist.getKey32(parent));
	}

	/*// TODO: ver como vamos configurar estes parâmetros
	public int getnJumps() {
		return nJumps;
	}

	public void setnJumps(int nJumps) {
		this.nJumps = nJumps;
	}

	public int getnMoves() {
		return nMoves;
	}

	public void setnMoves(int nMoves) {
		this.nMoves = nMoves;
	}*/

	/*private boolean validateMovesArray(Move[] array) {

		for (Move v : array) {
			if (v != null) {
				return true;
			}
		}
		return false;
	}*/
	
	public int whoPlay(Player player) {

		int whoIsOPP1 = Integer.parseInt(SystemConfigs.getInstance().getConfig("whoIsOPP1").trim());
		int whoStarts = Integer.parseInt(SystemConfigs.getInstance().getConfig("whoStarts").trim());

		if(whoStarts != 0){
			if(whoStarts == BoardValues.BLACKMAN){
				return BoardValues.BLACKMAN;
			}else{
				return BoardValues.REDMAN;
			}
			
		}else{
			if ((whoIsOPP1 == BoardValues.BLACKMAN && player.equals(Player.BLACK))
					|| (whoIsOPP1 == BoardValues.REDMAN && player.equals(Player.RED))) {
				return BoardValues.BLACKMAN;

			} else if ((whoIsOPP1 == BoardValues.REDMAN && player.equals(Player.BLACK))
					|| (whoIsOPP1 == BoardValues.BLACKMAN && player.equals(Player.RED))) {
				return BoardValues.REDMAN;

			} 
		}
		
		return BoardValues.BLACKMAN;
	}
	
	@Override
	public Sucessor clone() throws CloneNotSupportedException {
		return (Sucessor) super.clone();
	}
}
