package ufu.facom.lia.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.configs.FeaturesSearchConfig;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.evaluation.policy.Node;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.MlpException;
import ufu.facom.lia.exceptions.PlayerException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.file.FileManipulate;
import ufu.facom.lia.file.FilePDN;
import ufu.facom.lia.hash.ZobristKey;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.Configuration;
import ufu.facom.lia.interfaces.GameState;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.IHashKey;
import ufu.facom.lia.mlp.DirectNet;
import ufu.facom.lia.mlp.EvalNet;
import ufu.facom.lia.mlp.EvalNetImpl;
import ufu.facom.lia.mlp.Utils;
import ufu.facom.lia.search.MasterAlphaBeta;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.mlp.Net;

/**
 * Esta classe é responsável por realizar um torneio (disputa) entre o presente
 * código Java (ADABA-FINAL) e um sistema em C (D-VisionDraughts) através de uma
 * chamada de sistema por linha de comando
 * 
 * @author eu_li
 *
 */
public class GameFight extends Game {

	enum AgentPlayer {
		YBWC, APHID;
	}

	private static final Logger logger = LogManager.getLogger(GameFight.class);

	private String featureFile;

	private String netFile;

	private String boardFile;

	private DirectNet dirNetAphid;

	private int totalBoards;

	public GameFight() {
		fsc = FeaturesSearchConfig.getInstance();
	}

	private int autoPlay(Node current, Integer indexBoard, Player blackPlayer, Player redPlayer, DirectNet netAphid,
			AgentPlayer blackAgent, AgentPlayer redAgent)
			throws SearchException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InvalidBoardException, MlpException, TranspositionTableException, IterativeDeepeningException {

		IAlphaBeta ab = null;

		Integer moves = 0;

		Long timeInitial = System.currentTimeMillis();

		// Add Matheus -> variável rodada de movimentos
		Integer count_round = 1;

		try {

			Evaluation be = fsc.getEvaluation();
			Sucessor bs = fsc.getSucessor();

			// ab = new AlphaBetaFailSoft(be, bs);
			ab = MasterAlphaBeta.getInstance(be, bs);
			// ab = new Minimax(be, bs);

			if (ab instanceof MasterAlphaBeta) {
				((MasterAlphaBeta) ab).reStartingMaster();
			}

			Integer qtdMoves = 0;
			Integer stateGame = 0;

			Integer qtdMvBlack = 0;
			Integer qtdMvRed = 0;

			Move lstMvBlack = null;
			Move lstMvRed = null;

			String lstStateBlack = "";
			String lstStateRed = "";

			Long startTime = 0l;

			while (stateGame == GameState.CONTINUE) {

				Long aphidMofificationBoardFile = fm.lastModification(boardFile);

				// FIXME: Add Matheus -> Grava no pdn a rodada dos movimentos (1,2,3,4,5,..)
				pdn.writeRound(count_round++);

				if (blackAgent.equals(AgentPlayer.APHID)) {

					logger.warn("APHID JOGANDO");

					startTime = System.currentTimeMillis();

					Board[] boards = fm.readBoardFile(boardFile);
					XBoard[] xBoards = new XBoard[boards.length];

					bu.convertYbwcToAphidBoard(boards[indexBoard].getBoard());
					current.setBoard(boards[indexBoard]);

					bu.mapToXBoard(boards[indexBoard].getBoard(), xBoards[indexBoard].getXBoard());
					current.setxBoard(xBoards[indexBoard]);

					lstMvBlack = makeMove(netAphid, lstMvBlack, current, blackPlayer, ab);

					// FIXME: Add Matheus -> Grava Movimento BlackPlayer
					pdn.writeMove(lstMvBlack.getFrom(), lstMvBlack.getTo());

					logger.info("BlackPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());
					logger.warn("BlackPlayer: currentBoard: \n");
					bu.printBoard(current.getBoard());

					fm.writeBoardStateFile(
							bu.getRepresentation(bu.convertAphidToYbwcBoard(current.getBoard().getBoard())), boardFile);
					aphidMofificationBoardFile = fm.lastModification(boardFile);

					logger.warn("Time move: " + (System.currentTimeMillis() - startTime));

				} else {

					logger.warn("YBWC JOGANDO");

					startTime = System.currentTimeMillis();

					String ybwcCommand = SystemConfigs.getInstance().getConfig("ybwcCommandBlack");

					Runtime.getRuntime().exec(ybwcCommand);

					Long currentModificationBoardFile = fm.lastModification(boardFile);

					synchronized (this) {
						while (aphidMofificationBoardFile.equals(currentModificationBoardFile)) {
							try {
								this.wait(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							currentModificationBoardFile = fm.lastModification(boardFile);
						}
					}

					Board[] boards = fm.readBoardFile(boardFile);
					XBoard[] xBoards = new XBoard[boards.length];

					bu.convertYbwcToAphidBoard(boards[indexBoard].getBoard());
					current.setBoard(boards[indexBoard]);

					bu.mapToXBoard(boards[indexBoard].getBoard(), xBoards[indexBoard].getXBoard());
					current.setxBoard(xBoards[indexBoard]);

					logger.info("BlackPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());
					logger.warn("BlackPlayer: currentBoard: \n");
					bu.printBoard(current.getBoard());

					logger.warn("Time move: " + (System.currentTimeMillis() - startTime));
				}

				moves++;

				if (lstStateBlack.equals(current.getBoard().getBoardRepresentation())) {
					qtdMvBlack++;
				} else {
					lstStateBlack = current.getBoard().getBoardRepresentation();
				}

				if (current.getType() == Type.LOSER.getValue()) {
					stateGame = GameState.RED_WON;
				} else {
					stateGame = itsOver(current, qtdMoves, qtdMvBlack, qtdMvRed);
				}

				if (stateGame == GameState.CONTINUE) {

					if (redAgent.equals(AgentPlayer.APHID)) {

						logger.warn("APHID JOGANDO");

						startTime = System.currentTimeMillis();

						Board[] boards = fm.readBoardFile(boardFile);
						XBoard[] xBoards = new XBoard[boards.length];

						bu.convertYbwcToAphidBoard(boards[indexBoard].getBoard());
						current.setBoard(boards[indexBoard]);

						bu.mapToXBoard(boards[indexBoard].getBoard(), xBoards[indexBoard].getXBoard());
						current.setxBoard(xBoards[indexBoard]);

						lstMvRed = makeMove(netAphid, lstMvRed, current, redPlayer, ab);

						// FIXME: Add Matheus -> Grava Movimento RedPlayer
						pdn.writeMove(lstMvRed.getFrom(), lstMvRed.getTo());

						logger.info("RedPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());
						logger.warn("RedPlayer: currentBoard: \n");
						bu.printBoard(current.getBoard());

						fm.writeBoardStateFile(
								bu.getRepresentation(bu.convertAphidToYbwcBoard(current.getBoard().getBoard())),
								boardFile);
						aphidMofificationBoardFile = fm.lastModification(boardFile);

						logger.warn("Time move: " + (System.currentTimeMillis() - startTime));

					} else {

						logger.warn("YBWC JOGANDO");

						startTime = System.currentTimeMillis();

						String ybwcCommand = SystemConfigs.getInstance().getConfig("ybwcCommandRed");

						Runtime.getRuntime().exec(ybwcCommand);

						Long currentModificationBoardFile = fm.lastModification(boardFile);

						synchronized (this) {
							while (aphidMofificationBoardFile.equals(currentModificationBoardFile)) {
								try {
									this.wait(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								currentModificationBoardFile = fm.lastModification(boardFile);
							}
						}

						Board[] boards = fm.readBoardFile(boardFile);
						XBoard[] xBoards = new XBoard[boards.length];

						bu.convertYbwcToAphidBoard(boards[indexBoard].getBoard());
						current.setBoard(boards[indexBoard]);

						bu.mapToXBoard(boards[indexBoard].getBoard(), xBoards[indexBoard].getXBoard());
						current.setxBoard(xBoards[indexBoard]);

						logger.info("RedPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());
						logger.warn("RedPlayer: currentBoard: \n");
						bu.printBoard(current.getBoard());

						logger.warn("Time move: " + (System.currentTimeMillis() - startTime));
					}

					moves++;

					if (lstStateRed.equals(current.getBoard().getBoardRepresentation())) {
						qtdMvBlack++;
					} else {
						lstStateRed = current.getBoard().getBoardRepresentation();
					}

					if (current.getType() == Type.LOSER.getValue()) {
						stateGame = GameState.BLACK_WON;
					} else {
						stateGame = itsOver(current, qtdMoves, qtdMvBlack, qtdMvRed);
					}

					qtdMoves++;
				}
			}

			logger.warn("Jogo terminou com " + moves + " movimentos em (milissegundos): "
					+ (System.currentTimeMillis() - timeInitial));

			if (ab != null) {
				ab.finalizeSearch();
			}

			return stateGame;

		} catch (IOException | PlayerException e) {
			e.printStackTrace();
		}

		return GameState.DRAW;
	}

	private int itsOver(INode state, Integer qtdMoves, Integer qtdMvBlack, Integer qtdMvRed) {

		int b = 0, r = 0;

		for (int i = 0; i < ((Node) state).getBoard().getBoard().length; i++) {

			switch (((Node) state).getBoard().getBoard()[i]) {
			case BoardValues.BLACKMAN:
				b += 2;
				break;
			case BoardValues.BLACKKING:
				b += 3;
				break;
			case BoardValues.REDMAN:
				r += 2;
				break;
			case BoardValues.REDKING:
				r += 3;
				break;
			}
		}

		if (b == 0) {
			return GameState.RED_WON;

		} else if (r == 0) {
			return GameState.BLACK_WON;

		} else if (qtdMoves > Configuration.MIN_MOVES_TO_AVALIATE_GAME && qtdMvBlack >= 5 && qtdMvRed >= 5) {

			System.out.println("LOOP IDENTIFICADO: a partida será finalizada como empate");
			return GameState.DRAW;

		} else if (qtdMoves > Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW) {
			System.out.println("A rede já realizou " + Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW
					+ " jogadas! A partida será finalizada como empate.");
			return GameState.DRAW;
		}

		return GameState.CONTINUE;
	}

	public void play(AgentPlayer blackAgent, AgentPlayer redAgent) {

		logger.info("Checkers training process starting!");

		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();

		// FIXME:Add Matheus -> Arq PDN
		pdn = new FilePDN("JOGO", true);

		dirNetAphid = new DirectNet(new EvalNet());
		dirNetAphid.setEvalNetImpl(new EvalNetImpl(dirNetAphid.getEvalNet()));

		IHashKey zobristKey = new ZobristKey();

		System.out.println("jogador é preto? " + blackAgent);

		try {
			Net netAphid = fm.readFile(netFile);

			netAphid = utils.getNetConfiguration(netAphid);
			netAphid.setName("APHID-NET");

			dirNetAphid.setNet(netAphid);

			features = fm.readFeaturesFile(featureFile);

			if (!validateFeatures(netAphid)) {
				throw new PlayerException(
						"The number of features's bits doesn't match with entries length of network.");
			}

			Board[] boards = fm.readBoardFile(boardFile);

			/*
			 * for (Board b : boards) { bu.convertYbwcToAphidBoard(b.getBoard());
			 * bu.mapToXBoard(b.getBoard(), b.getXBoard()); }
			 */

			System.out.println("INICIAL");
			bu.printBoard(boards[0]);

			Node current = new Node();
			current.setBoard(boards[0]);
			current.setDepth(0);
			// current.setName("S1");
			current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
			current.setType(Type.MAX.getValue());
			current.setVisited(false);

			dirNetAphid.setBoardUtils(bu);
			dirNetAphid.setFeatures(features);
			dirNetAphid.getEvalNetImpl().initializeWeights(dirNetAphid.getNet());
			dirNetAphid.getEvalNetImpl().initializeHiddenUnits(dirNetAphid.getNet());

			int blackWon = 0, redWon = 0, draw = 0;

			long timeInitial = System.currentTimeMillis();

			int stateGame = -1;

			for (int h = 0; h < totalBoards; h++) {

				logger.info("Current Game: " + h);

				Node currentTemp = current.clone();

				stateGame = autoPlay(currentTemp, h, Player.BLACK, Player.RED, dirNetAphid, blackAgent, redAgent);

				if (stateGame == GameState.BLACK_WON) {
					if (blackAgent.equals(AgentPlayer.APHID)) {
						blackWon++;
						logger.info("???black won: aphid");
					} else {
						redWon++;
						logger.info("???black won: ybwc");
					}
				} else if (stateGame == GameState.RED_WON) {
					if (redAgent.equals(AgentPlayer.APHID)) {
						redWon++;
						logger.info("???red won: aphid");
					} else {
						blackWon++;
						logger.info("???red won: ybwc");
					}
				} else if (stateGame == GameState.DRAW) {
					logger.info("???draw");
				}

				currentTemp = null;

				pdn.writeResult(stateGame);

				System.gc();

				logger.warn("FINAL: black player: " + blackWon + " wons; red player: " + redWon + "; draws: " + draw);

			}

			logger.warn("Total Match Time: " + (System.currentTimeMillis() - timeInitial));

		} catch (IOException | PlayerException | IllegalArgumentException | InvalidBoardException | SearchException
				| CloneNotSupportedException | IllegalAccessException | InvocationTargetException | MlpException
				| TranspositionTableException | IterativeDeepeningException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void readConfigurations() {

		super.readConfigurations();

		netFile = SystemConfigs.getInstance().getConfig("aphidNetFile");
		featureFile = SystemConfigs.getInstance().getConfig("aphidFeaturesFile");
		boardFile = SystemConfigs.getInstance().getConfig("basePath")
				+ SystemConfigs.getInstance().getConfig("boardFile");
		totalBoards = Integer.parseInt(SystemConfigs.getInstance().getConfig("totalBoards"));

	}

	public static void main(String[] args) {

		String blackPlayer = null;
		String redPlayer = null;

		if (args.length == 0) {
			blackPlayer = "APHID";
			redPlayer = "YBWC";
		} else {
			blackPlayer = args[0];
			redPlayer = args[1];
		}

		AgentPlayer bPlayer, rPlayer;

		if (blackPlayer.equals(AgentPlayer.APHID.toString())) {
			bPlayer = AgentPlayer.APHID;
		} else {
			bPlayer = AgentPlayer.YBWC;
		}

		if (redPlayer.equals(AgentPlayer.APHID.toString())) {
			rPlayer = AgentPlayer.APHID;
		} else {
			rPlayer = AgentPlayer.YBWC;
		}

		GameFight p = new GameFight();

		p.play(bPlayer, rPlayer);

	}

}