package ufu.facom.lia.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.map.EFeatures;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.cache.TranspositionTable;
import ufu.facom.lia.configs.FeaturesSearchConfig;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.evaluation.policy.BoardEvaluation;
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
import ufu.facom.lia.interfaces.Constants;
import ufu.facom.lia.interfaces.GameState;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.SearchDefinition;
import ufu.facom.lia.interfaces.cache.ICache;
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
 * Esta classe é responsável pelo treinamento da rede neural por self-play com clonagem
 * @author eu_li
 *
 */
public class Game {
	
	private static final Logger logger = LogManager.getLogger(Game.class);

	private int searchDepth;

	private int gameLimit;

	private int testAfter;
	
	private int pointsToClone;
	
	private int timeMove;

	protected boolean opp1IsBlack;

	// private int inLoop;
	
	private String searchDefinition;
	
	private String clonesPath;

	protected String basePath;

	protected String featureFile;

	protected String netFile;
	
	protected String netOpponent;

	protected String boardFile;

	private Boolean isNewNetwork;

	protected DirectNet dirNetOpp1;

	protected DirectNet dirNetOpp2;

	protected FileManipulate fm;

	protected Utils utils;

	protected BoardUtils bu;

	protected List<EFeatures> features;
	
	protected FeaturesSearchConfig fsc;
	
	private ICache tcache;
	
	protected FilePDN pdn; //Add Matheus -> Arquivo pdn
	
	public Game() {
		fsc = FeaturesSearchConfig.getInstance();
	}

	/**
	 * Método que realiza uma partida da rede contra seu clone, mas não realiza os ajustes dos pesos da rede MLP
	 */
	public void playWithoutTraining() {

		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();
		
		//Add Matheus -> Arq PDN
		pdn = new FilePDN("testeaphid");

		dirNetOpp1 = new DirectNet(new EvalNet());
		dirNetOpp1.setEvalNetImpl(new EvalNetImpl(dirNetOpp1.getEvalNet()));

		dirNetOpp2 = new DirectNet(new EvalNet());
		dirNetOpp2.setEvalNetImpl(new EvalNetImpl(dirNetOpp2.getEvalNet()));
		
		IHashKey zobristKey = new ZobristKey();

		try {

			Net netOpp1 = null;

			if (isNewNetwork) {
				netOpp1 = dirNetOpp1.getEvalNetImpl().initializeWeights(netOpp1);
			} else {
				netOpp1 = fm.readFile(basePath + netFile);
			}

			netOpp1.setName("OPP1");
			netOpp1 = utils.getNetConfiguration(netOpp1);

			dirNetOpp1.setNet(netOpp1);

			Net netOpp2 = netOpp1.clone();
			netOpp2.setName("OPP2");
			
			dirNetOpp2.setNet(netOpp2);

			features = fm.readFeaturesFile(basePath + featureFile);

			if (!validateFeatures(netOpp1)) {
				throw new PlayerException(
						"The number of features's bits doesn't match with entries length of network.");
			}

			dirNetOpp1.setBoardUtils(bu);
			dirNetOpp1.setFeatures(features);
			dirNetOpp1.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp1.getEvalNetImpl().initializeWeights(dirNetOpp1.getNet());
			dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());

			dirNetOpp2.setBoardUtils(bu);
			dirNetOpp2.setFeatures(features);
			dirNetOpp2.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp2.getEvalNetImpl().initializeWeights(dirNetOpp2.getNet());
			dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());

			// Defining that player OPP1 is black and OPP2 is red
			// If last parameter (playerTraining) is null, the training not
			// occurs
			
			Board[] boards = fm.readBoardFile(basePath + boardFile);

			int opp1Won = 0, opp2Won = 0, draw = 0;
			
			for(int h = 0; h < boards.length; h++) {
				
				Node current = new Node();
				current.setBoard(boards[h]);
				current.setDepth(0);
				//current.setName("S1");
				current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
				current.setType(Type.MAX.getValue());
				current.setVisited(false);
				
				for(int i = 0; i < 2; i++){
					
					if(i % 2 == 0){
						opp1IsBlack = true;
					}else{
						opp1IsBlack = false;
					}
					
					
					int stateGame = autoPlay(current, Player.BLACK, Player.RED, null, dirNetOpp1, dirNetOpp2);

					if (stateGame == GameState.BLACK_WON) {
						if (opp1IsBlack) {
							opp1Won++;
						} else {
							opp2Won++;
						}
					} else if (stateGame == GameState.RED_WON) {
						if (opp1IsBlack) {
							opp2Won++;
						} else {
							opp1Won++;
						}
					} else if (stateGame == GameState.DRAW) {
						draw++;
					}

					logger.warn("Parcial BOARD("+h+"): rede opp1: " + opp1Won + " vitórias; opp2: " + opp2Won + "; empates: " + draw);
				}
			}
			
			logger.warn("FINAL: rede opp1: " + opp1Won + " vitórias; opp2: " + opp2Won + "; empates: " + draw);

		} catch (IOException | PlayerException | IllegalArgumentException | InvalidBoardException | SearchException
				| CloneNotSupportedException | IllegalAccessException | InvocationTargetException | MlpException | TranspositionTableException | IterativeDeepeningException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Método utilizado para treinar a rede neural.
	 */
	public void play() {
		
		logger.info("Checkers training process starting!");

		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();
		
		dirNetOpp1 = new DirectNet(new EvalNet());
		dirNetOpp1.setEvalNetImpl(new EvalNetImpl(dirNetOpp1.getEvalNet()));

		dirNetOpp2 = new DirectNet(new EvalNet());
		dirNetOpp2.setEvalNetImpl(new EvalNetImpl(dirNetOpp2.getEvalNet()));
		
		IHashKey zobristKey = new ZobristKey();

		System.out.println("jogador é preto? " + opp1IsBlack);

		try {

			Net netOpp1 = null;
			
			if (isNewNetwork) {
				netOpp1 = dirNetOpp1.getEvalNetImpl().initializeWeights(netOpp1);
			} else {
				netOpp1 = fm.readFile(basePath + netFile);
			}

			netOpp1 = utils.getNetConfiguration(netOpp1);
			netOpp1.setName("OPP1");

			dirNetOpp1.setNet(netOpp1);

			Net netOpp2 = netOpp1.clone();
			netOpp2.setName("OPP2");

			dirNetOpp2.setNet(netOpp2);

			features = fm.readFeaturesFile(basePath + featureFile);

			if (!validateFeatures(netOpp1)) {
				throw new PlayerException(
						"The number of features's bits doesn't match with entries length of network.");
			}

			Board[] boards = fm.readBoardFile(basePath + boardFile);

			dirNetOpp1.setBoardUtils(bu);
			dirNetOpp1.setFeatures(features);
			dirNetOpp1.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp1.getEvalNetImpl().initializeWeights(dirNetOpp1.getNet());
			dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());

			dirNetOpp2.setBoardUtils(bu);
			dirNetOpp2.setFeatures(features);
			dirNetOpp2.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp2.getEvalNetImpl().initializeWeights(dirNetOpp2.getNet());
			dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());

			// Defining that player OPP1 is black and OPP2 is red
			// If last parameter (playerTraining) is null, the training not
			// occurs

			int opp1Won = 0, opp2Won = 0, draw = 0, numClone = 0;
			boolean won;
			
			long timeInitial = System.currentTimeMillis();
			
			int stateGame = -1;
			
			logger.warn("GameLimit: " + gameLimit + " TestAfter: " + testAfter);

			for (int h = 0; h < gameLimit; h++) {
				
				logger.warn("##################### MACH NUMBER:  " + h + " ############################");
				
				//opp1Won = 0; opp2Won = 0; draw = 0;
				
				won = true;
				
				for(int j = 0; j < boards.length; j++) {
					
					logger.warn("##################### BOARD NUMBER: " + j + " ############################");
					
					Node current = new Node();
					current.setBoard(boards[j]);
					current.setDepth(0);
					//current.setName("S1");
					current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
					current.setType(Type.MAX.getValue());
					current.setVisited(false);
					
					for (int i = 0; i < testAfter; i++) {
						
						logger.warn("##################### GAME NUMBER:  " + i + " ############################");
						
						if(i % 2 == 0){
							opp1IsBlack = true;
						}else{
							opp1IsBlack = false;
						}
						
						Node currentTemp = current.clone();
						
						//A rede que está treinando ora está como peça preta, ora como peça vermelha. Por isso há a alternância de jogadores.
						if(opp1IsBlack){
							logger.info("BlackPlayer NET: dirNetOpp1 - RedPlayer: dirNetOpp2");
							stateGame = autoPlay(currentTemp, Player.BLACK, Player.RED, Player.BLACK, dirNetOpp1, dirNetOpp2);
							
						}else{
							logger.info("BlackPlayer NET: dirNetOpp2 - RedPlayer: dirNetOpp1");
							stateGame = autoPlay(currentTemp, Player.BLACK, Player.RED, Player.RED, dirNetOpp2, dirNetOpp1);
						}
						
						/*if (stateGame == GameState.BLACK_WON) {
							if (opp1IsBlack) {
								opp1Won++;
								logger.info("???black won: opp1");
							} else {
								opp2Won++;
								logger.info("???black won: opp2");
							}
						} else if (stateGame == GameState.RED_WON) {
							if (opp1IsBlack) {
								opp2Won++;
								logger.info("???red won: opp2");
							} else {
								opp1Won++;
								logger.info("???red won: opp1");
							}
						} else if (stateGame == GameState.DRAW) {
							draw++;
							logger.info("???draw");
						}*/
						
						
						currentTemp = null;
						
						System.gc();
					}
					
					logger.info("Arrived here for cloning");
					
					opp1Won = opp2Won = draw = 0;
					
					for(int i = 0; i < 2; i++){
						
						stateGame = -1;
						
						Node currentTemp = current.clone();
						
						if(i % 2 == 0){
							stateGame = autoPlay(currentTemp, Player.BLACK, Player.RED, null, dirNetOpp1, dirNetOpp2);
						}else{
							stateGame = autoPlay(currentTemp, Player.BLACK, Player.RED, null, dirNetOpp2, dirNetOpp1);
						}

						if (stateGame == GameState.BLACK_WON) {
							if (opp1IsBlack) {
								opp1Won++;
							} else {
								opp2Won++;
							}
						} else if (stateGame == GameState.RED_WON) {
							if (opp1IsBlack) {
								opp2Won++;
							} else {
								opp1Won++;
							}
						} else if (stateGame == GameState.DRAW) {
							draw++;
						}
						
						currentTemp = null;
						
						System.gc();
					}
					
					if ((opp1Won * Constants.VLR_WINREWARD + draw) < pointsToClone && won) won = false;
					
				}

				logger.warn("FINAL: opp1 network: " + opp1Won + " wons; opp2: " + opp2Won + "; draws: " + draw + "\n");

				if (won) {

					fm.writeNetFile(dirNetOpp1.getNet(), clonesPath, "CLONE" + numClone);

					netOpp2 = null;
					
					netOpp2 = netOpp1.clone();
					
					dirNetOpp2.setNet(netOpp2);

					numClone++;
				}
				
				System.gc();
			}

			if (numClone == 0) {
				// Jogador treinou mas não conseguiu clonar
				logger.warn("Trained, but not was cloned.");
				fm.writeNetFile(dirNetOpp1.getNet(), clonesPath, "ADJUSTED" + numClone);
			}else {
				//torneio dos clones
				long timeTraining = (System.currentTimeMillis() - timeInitial);
				playClones(numClone, boards);
				logger.warn("Total Training Time Before Cloning: " + timeTraining);
				logger.warn("Total Training Time After Cloning Tournament: " + (System.currentTimeMillis() - timeInitial));
			}
			
			System.exit(0);

		} catch (IOException | PlayerException | IllegalArgumentException | InvalidBoardException | SearchException
				| CloneNotSupportedException | IllegalAccessException | InvocationTargetException | MlpException | TranspositionTableException | IterativeDeepeningException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void playClones(){
		
		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();
		
		int numClones = Integer.parseInt(SystemConfigs.getInstance().getConfig("numClones").trim());
		
		try {
			
			dirNetOpp1 = new DirectNet(new EvalNet());
			dirNetOpp1.setEvalNetImpl(new EvalNetImpl(dirNetOpp1.getEvalNet()));

			dirNetOpp2 = new DirectNet(new EvalNet());
			dirNetOpp2.setEvalNetImpl(new EvalNetImpl(dirNetOpp2.getEvalNet()));
			
			features = fm.readFeaturesFile(basePath + featureFile);
			
			dirNetOpp1.setBoardUtils(bu);
			dirNetOpp1.setFeatures(features);
			//dirNetOpp1.getEvalNet().setBias(Configuration.BIAS_VALUE);
			//dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());
			//dirNetOpp1.getEvalNetImpl().initializeWeights(dirNetOpp1.getNet());

			dirNetOpp2.setBoardUtils(bu);
			dirNetOpp2.setFeatures(features);
			//dirNetOpp2.getEvalNet().setBias(Configuration.BIAS_VALUE);
			//dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());
			//dirNetOpp2.getEvalNetImpl().initializeWeights(dirNetOpp2.getNet());
			
			Board[] boards = fm.readBoardFile(basePath + boardFile);
			
			/*for (Board b : boards) {
				bu.mapToXBoard(b.getBoard(), b.getXBoard());
			}
			*/
			playClones(numClones, boards);
			
			
		} catch (IOException | PlayerException e) {
			e.printStackTrace();
		} 
	}
	
	

	private void playClones(int numClone, Board boards[]) {
		
		logger.warn("PROCESSO TORNEIO CLONES");

		//Map<String, Integer> wins = new HashMap<String, Integer>();
		
		IHashKey zobristKey = new ZobristKey();

		Net netOpp1 = null;
		Net netOpp2 = null;
		
		int vBestClone = -1;
		int iBestClone = -1;
		int winner = 0;

		try {

			if (numClone == 1) {
				fm.copyFile(basePath + netFile, clonesPath + "/CLONE" + numClone + Configuration.NET_EXTENSION);
				numClone++;
			}

			// Torneio entre os clones
			for (int i = 0; i < numClone; i++) {
				
				winner = 0;
				
				netOpp1 = fm.readFile(clonesPath + "/CLONE" + i + Configuration.NET_EXTENSION);

				dirNetOpp1.setNet(netOpp1);
				dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());
				
				for(int k = 0; k < boards.length; k++) {
					
					Node current = new Node();
					current.setBoard(boards[k]);
					current.setDepth(0);
					//current.setName("S1");
					current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
					current.setType(Type.MAX.getValue());
					current.setVisited(false);

					for (int j = 0; j < numClone; j++) {

						if (i != j) {

							netOpp2 = fm.readFile(
									clonesPath + "/CLONE" + j + Configuration.NET_EXTENSION);

							dirNetOpp2.setNet(netOpp2);
							dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());
							
							Node currentTemp = current.clone();

							int stateGame = autoPlay(currentTemp, Player.BLACK, Player.RED, null, dirNetOpp1, dirNetOpp2);

							//String key = String.valueOf(i);

							if (stateGame == GameState.BLACK_WON) {
								//wins.put(key, i);
								winner++;
							} else if (stateGame == GameState.DRAW) {
								//wins.put(key, 0);
							} else {
								//wins.put(key, -1);
							}
						}
					}
					
					if(winner>=vBestClone) {
						iBestClone = i;
						vBestClone = winner;
					}
				}

				logger.warn("Total de vitorias do clone " + i + " " + winner);
				
			}
			
			/*Set<String> keys = wins.keySet();
			
			int theWins[] = new int[numClone];
			
			for(String k: keys){
				if(wins.get(k) != -1){
					theWins[wins.get(k)] ++;
				}
			} 
			
			int theWin = -1;
			int aux = -1;
			
			for(int i = 0; i < theWins.length; i++){
				if(theWins[i] > aux){
					aux = theWins[i];
					theWin = i;
				}
			}*/
			
			System.out.println("O CLONE VENCEDOR É O CLONE: " + iBestClone);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int autoPlay(Node current, Player blackPlayer, Player redPlayer, Player playerTraining, DirectNet netBlack, DirectNet netRed)
			throws SearchException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InvalidBoardException, MlpException, TranspositionTableException, IterativeDeepeningException {

		IAlphaBeta ab = null;
		
		Integer moves = 0;
		
		//Add Matheus -> variável rodada de movimentos
		Integer count_round = 1;
		
		Long timeInitial = System.currentTimeMillis();
		
		Evaluation be = fsc.getEvaluation();
		Sucessor bs = fsc.getSucessor();

		//ab = new AlphaBetaFailSoft(be, bs);
		ab = MasterAlphaBeta.getInstance(be, bs);
		//ab = new Minimax(be, bs);
		
		if(ab instanceof MasterAlphaBeta){
			logger.warn("Veio aki para reiniciar a busca??????");
			((MasterAlphaBeta) ab).reStartingMaster();
		}
		
		Integer qtdMoves = 0;
		Integer stateGame = 0;

		// inLoop = 0;

		Move lstMvBlack = null;
		Move lstMvRed = null;

		System.out.println("INICIAL");
		bu.printBoard(current.getBoard());

		while (stateGame == GameState.CONTINUE) {
			
			//logger.info("BlackPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());

			//Add Matheus -> Grava no pdn a rodada dos movimentos (1,2,3,4,5,..)
			if(pdn != null)
				pdn.writeRound(count_round);
			
			lstMvBlack = makeMove(netBlack, lstMvBlack, current, blackPlayer, ab);
			moves++;
			
			//Add Matheus -> Grava Movimento BlackPlayer
			if(pdn != null)
				pdn.writeMove(lstMvBlack.getFrom(), lstMvBlack.getTo());
			
			logger.warn("BlackPlayer: currentBoard: \n");
			bu.printBoard(current.getBoard());
			//System.out.println(bu.getRepresentation(current.getBoard().getBoard()));

			if (playerTraining != null && playerTraining.equals(blackPlayer)) {
				float[] entries = netBlack.mapFeatures(current.getBoard(), netBlack.getNet());

				if (qtdMoves == 0) {
					netBlack.getEvalNetImpl().initTDTrain(entries, netBlack.getNet());

				} else {
					netBlack.getEvalNetImpl().tdTrain(entries, netBlack.getNet());
				}
			}

			if(current.getType() == Type.LOSER.getValue()){
				stateGame = GameState.RED_WON;
			}else{
				stateGame = itsOver(current, qtdMoves, lstMvBlack, lstMvRed);
			}
			
			qtdMoves++;

			if (stateGame == GameState.CONTINUE) {
				
				//logger.info("RedPlayer: BOARD REPRESENTATION: " + current.getBoard().getBoardRepresentation());

				lstMvRed = makeMove(netRed, lstMvRed, current, redPlayer, ab);
				moves++;
			
				//Add Matheus -> Grava Movimento White/RedPlayer
				if(pdn != null)
					pdn.writeMove(lstMvRed.getFrom(), lstMvRed.getTo());
				
				logger.warn("RedPlayer: currentBoard: \n");
				bu.printBoard(current.getBoard());
				//System.out.println(bu.getRepresentation(current.getBoard().getBoard()));

				if (playerTraining != null && playerTraining.equals(redPlayer)) {
					float[] entries = netRed.mapFeatures(current.getBoard(), netRed.getNet());

					if (qtdMoves == 0) {
						netRed.getEvalNetImpl().initTDTrain(entries, netRed.getNet());
					} else {
						netRed.getEvalNetImpl().tdTrain(entries, netRed.getNet());
					}
				}

				if(current.getType() == Type.LOSER.getValue()){
					stateGame = GameState.BLACK_WON;
				}else{
					stateGame = itsOver(current, qtdMoves, lstMvBlack, lstMvRed);
				}

				qtdMoves++;
				count_round++; //ADD Matheus -> contador de rodada
				
				//System.out.println("veio aqui...");
			}
		}
		
		if(playerTraining != null && playerTraining.equals(blackPlayer)){
			if(stateGame == GameState.BLACK_WON){
				netBlack.getEvalNetImpl().tdFinal(Constants.WIN_VALUE, netBlack.getNet());
			}else if(stateGame == GameState.RED_WON){
				netBlack.getEvalNetImpl().tdFinal(Constants.LOSE_VALUE, netBlack.getNet());
			}else{
				netBlack.getEvalNetImpl().tdFinal(Constants.DRAW_VALUE, netBlack.getNet());
			}
		}else if(playerTraining != null && playerTraining.equals(redPlayer)){
			if(stateGame == GameState.RED_WON){
				netRed.getEvalNetImpl().tdFinal(Constants.WIN_VALUE, netRed.getNet());
			}else if(stateGame == GameState.BLACK_WON){
				netRed.getEvalNetImpl().tdFinal(Constants.LOSE_VALUE, netRed.getNet());
			}else{
				netRed.getEvalNetImpl().tdFinal(Constants.DRAW_VALUE, netRed.getNet());
			}
		}
		
		logger.warn("Total movies: " + (ab).getNodesEvaluated());
		logger.warn("Jogo terminou com " + moves + " movimentos em (milissegundos): " + (System.currentTimeMillis() - timeInitial));

		if(ab != null){
			//logger.warn("Veio aqui para finalizar a busca??????");
			ab.finalizeSearch();
			//logger.warn("Finalizou aparentemente.");
		}
		
		return stateGame;
		
	}

	public Move makeMove(DirectNet dirNet, Move lstMv, Node current, Player player, IAlphaBeta ab) throws SearchException, TranspositionTableException, IterativeDeepeningException, InvalidBoardException {
		
		Long initialTime = System.currentTimeMillis();

		((BoardEvaluation) ab.getEvaluation()).setDirNet(dirNet);
		
		//tcache = new TranspositionCache(new ZobristKey(), Configuration.REGION_MASTER);
		tcache = new TranspositionTable();
		
		float result = 0f;
		
		Move move = new Move();
		
		if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST.getValue())){
			
			result = ab.alphaBeta(current, move, searchDepth, (Float.MAX_VALUE * (-1)),
					Float.MAX_VALUE, player, ((BoardEvaluation) ab.getEvaluation()).getDirNet().getNet());
			
		}else if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST_WITH_TT.getValue())){
			
			result = ab.alphaBetaTT(current, move, searchDepth, (Float.MAX_VALUE * (-1)),
					Float.MAX_VALUE, player, ((BoardEvaluation) ab.getEvaluation()).getDirNet().getNet(), tcache);
				
		}else if(searchDefinition.equals(SearchDefinition.ITERATIVE_DEEPENING.getValue())){
			
			result = ab.alphaBetaIDTimeLimited(current, move, searchDepth, (Float.MAX_VALUE * (-1)), 
					Float.MAX_VALUE, player, Configuration.INCREASE_ITERATIVE_DEEPENING, timeMove, 
					((BoardEvaluation) ab.getEvaluation()).getDirNet().getNet(), tcache);
		}
		
		tcache = null;
		
		//System.out.println("[GAME] Result: " + result);
		
		current.setEvaluation(result);

		//System.out.println(player);
		move = (Move) ab.getTheBestMove();

		doMove(current, move);

		// To verify possible loops
		if (lstMv == null) {
			lstMv = move;

		} else {
			if (lstMv.getFrom() == move.getTo() && lstMv.getTo() == move.getFrom()
					&& current.getxBoard().getXBoard()[move.getTo()] == lstMv.getPlayer()) {
				lstMv.setQtdMoves(lstMv.getQtdMoves() + 1);
			} else {
				lstMv.setQtdMoves(0);
				// System.out.println(current.getMove().getTo());
				lstMv.setPlayer(current.getxBoard().getXBoard()[move.getTo()]);
			}

			lstMv.setFrom(move.getFrom());
			lstMv.setTo(move.getTo());
		}
		
		bu.mapToBoard(current.getBoard().getBoard(), current.getxBoard().getXBoard());
		//System.out.println("Valor da feature: " + current.getBoard().getBoard()[Configuration.FEATURE_POSITION]);
		
		move = null;
		
		//System.gc();
		
		//logger.warn("[" + player + "] Time move (milissegundos): " + (System.currentTimeMillis() - initialTime));

		return lstMv;
	}

	private int itsOver(INode state, Integer qtdMoves, Move mvBlack, Move mvRed) {

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

		} else if (qtdMoves > Configuration.MIN_MOVES_TO_AVALIATE_GAME && mvBlack.getQtdMoves() >= 3
				&& mvRed.getQtdMoves() >= 3) {

			// FIXME: NÃO apagar esta lógica... foi comentada a fim de verificar
			// o caso de loop de final de jogo
			/*
			 * if (inLoop < 2) { inLoop++; System.out.println(
			 * "(5 jogadas repetidas: PROVAVEL LOOP)"); mvBlack.setQtdMoves(0);
			 * mvRed.setQtdMoves(0); return GameState.CONTINUE;
			 * 
			 * }else{ System.out.println(
			 * "LOOP RECORRENTE: a partida será finalizada como empate"); return
			 * GameState.DRAW; }
			 */

			System.out.println("LOOP IDENTIFICADO: a partida será finalizada como empate");
			return GameState.DRAW;

		} else if (qtdMoves > Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW) {
			System.out.println("A rede já realizou " + Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW
					+ " jogadas! A partida será finalizada como empate.");
			return GameState.DRAW;
		}

		return GameState.CONTINUE;
	}

	private void doMove(INode state, Move move) {

		try{
			
			bu.makeMove(move, ((Node) state).getxBoard().getXBoard());
			
		}catch(Exception e){
			logger.error("Deu um erro ao efetuar um movimento...");
			
			logger.warn("Representação do erro: " + ((Node)state).getBoard().getBoardRepresentation() + " key32: " + state.getKey().getKey32());
		}

	}

	protected boolean validateFeatures(Net net) {

		int numBitsFeatures = 0;

		for (EFeatures ftr : features) {

			numBitsFeatures += ftr.getNumBits();
		}

		if (numBitsFeatures != net.getNumInputs()) {
			return false;
		}

		return true;

	}

	protected void readConfigurations() {

		if (Integer.parseInt(SystemConfigs.getInstance().getConfig("whoIsOPP1").trim()) == BoardValues.BLACKMAN) {
			opp1IsBlack = true;
		} else {
			opp1IsBlack = false;
		}

		gameLimit = Integer.parseInt(SystemConfigs.getInstance().getConfig("gameLimit").trim());
		testAfter = Integer.parseInt(SystemConfigs.getInstance().getConfig("testAfter").trim());
		searchDepth = Integer.parseInt(SystemConfigs.getInstance().getConfig("searchDepth").trim());
		pointsToClone = Integer.parseInt(SystemConfigs.getInstance().getConfig("pointsToClone"));
		timeMove = Integer.parseInt(SystemConfigs.getInstance().getConfig("timeMove"));

		isNewNetwork = Boolean.parseBoolean(SystemConfigs.getInstance().getConfig("newNetwork"));

		clonesPath = SystemConfigs.getInstance().getConfig("clonesPath");
		basePath = SystemConfigs.getInstance().getConfig("basePath");
		featureFile = SystemConfigs.getInstance().getConfig("featureFile");
		netFile = SystemConfigs.getInstance().getConfig("netFile");
		boardFile = SystemConfigs.getInstance().getConfig("boardFile");
		
		
		searchDefinition = SystemConfigs.getInstance().getConfig("search-definition-game");
		
		logger.info("Busca utilizada no jogador: " + searchDefinition);
		logger.info("GameLimit: " + gameLimit + " TestAfter: " + testAfter + " Total: " + (gameLimit * testAfter) + " games.");
		logger.info("Profundidade da busca: " + searchDepth);
	}

	public static void main(String[] args) {

		Game p = new Game();

		p.play();
		
		//p.playWithoutTraining();
		
		//p.playToTournament();
		
		//p.playClones();
	}

}