package ufu.facom.lia.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
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
import ufu.facom.lia.interfaces.GameState;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.SearchDefinition;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.interfaces.cache.IHashKey;
import ufu.facom.lia.mlp.DirectNet;
import ufu.facom.lia.mlp.EvalNet;
import ufu.facom.lia.mlp.EvalNetImpl;
import ufu.facom.lia.mlp.Utils;
import ufu.facom.lia.search.AlphaBetaFailSoft;
import ufu.facom.lia.search.MasterAlphaBeta;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.mlp.Net;

public class GameOneMove {
	
	private static final Logger logger = LogManager.getLogger(GameOneMove.class);

	private int searchDepth;

	private int gameLimit;

	private int testAfter;
	
	private int timeMove;

	private boolean opp1IsBlack;
	
	private Player whoStarts;
	
	private String searchDefinition;
	
	private String clonesPath;

	private String basePath;

	private String featureFile;

	private String netFile;

	private String boardFile;

	private Boolean isNewNetwork;

	private DirectNet dirNetOpp1;

	private DirectNet dirNetOpp2;

	protected FileManipulate fm;

	protected Utils utils;

	protected BoardUtils bu;

	protected List<EFeatures> features;
	
	protected FeaturesSearchConfig fsc;
	
	private ICache tcache;
	
	private int numFile;
	
	protected FilePDN pdn; 

	public GameOneMove() {
		fsc = FeaturesSearchConfig.getInstance();
	}

	public void playWithoutTraining() {

		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();
		
		pdn = new FilePDN(SystemConfigs.getInstance().getConfig("pdnFileName"));

		dirNetOpp1 = new DirectNet(new EvalNet());
		dirNetOpp1.setEvalNetImpl(new EvalNetImpl(dirNetOpp1.getEvalNet()));

		dirNetOpp2 = new DirectNet(new EvalNet());
		dirNetOpp2.setEvalNetImpl(new EvalNetImpl(dirNetOpp2.getEvalNet()));

		System.out.println("black player? " + opp1IsBlack);
		
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

			Board[] boards = fm.readBoardFile(basePath + boardFile);

			/*for (Board b : boards) {
				bu.mapToXBoard(b.getBoard(), b.getXBoard());
			}*/

			Node current = new Node();
			current.setBoard(boards[0]);
			current.setDepth(0);
			//current.setName("S1");
			current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
			current.setType(Type.MAX.getValue());
			current.setVisited(false);

			// dirNet = new DirectNet(bu, features);
			dirNetOpp1.setBoardUtils(bu);
			dirNetOpp1.setFeatures(features);
			//dirNetOpp1.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp1.getEvalNetImpl().initializeWeights(dirNetOpp1.getNet());
			dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());

			dirNetOpp2.setBoardUtils(bu);
			dirNetOpp2.setFeatures(features);
			//dirNetOpp2.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp2.getEvalNetImpl().initializeWeights(dirNetOpp2.getNet());
			dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());

			// Defining that player OPP1 is black and OPP2 is red
			// If last parameter (playerTraining) is null, the training not
			// occurs

			int opp1Won = 0, opp2Won = 0, draw = 0;
			
			for(int i = 0; i < 3; i++){
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

				System.out.println("FINAL: rede opp1: " + opp1Won + " wins; opp2: " + opp2Won + " wins; draws: " + draw);
			}

			

		} catch (IOException | PlayerException | IllegalArgumentException | InvalidBoardException | SearchException
				| CloneNotSupportedException | IllegalAccessException | InvocationTargetException | MlpException | TranspositionTableException | IterativeDeepeningException e) {
			e.printStackTrace();
		}

	}

	public void play() {
		
		logger.info("Checkers training process starting!");

		readConfigurations();

		fm = new FileManipulate();

		utils = new Utils();

		bu = new BoardUtils();
		
		pdn = new FilePDN(SystemConfigs.getInstance().getConfig("pdnFileName"));

		dirNetOpp1 = new DirectNet(new EvalNet());
		dirNetOpp1.setEvalNetImpl(new EvalNetImpl(dirNetOpp1.getEvalNet()));

		dirNetOpp2 = new DirectNet(new EvalNet());
		dirNetOpp2.setEvalNetImpl(new EvalNetImpl(dirNetOpp2.getEvalNet()));
		
		IHashKey zobristKey = new ZobristKey();

		if(whoStarts == Player.BLACK){
			logger.warn("Jogador PRETO atuando.");
		}else{
			logger.warn("Jogador VERMELHO atuando.");
		}

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
			XBoard[] xBoards = new XBoard[boards.length];
			
			for(int i = 0; i < xBoards.length; i++ ){
				xBoards[i] = new XBoard();
			}
			
			dirNetOpp1.setBoardUtils(bu);
			dirNetOpp1.setFeatures(features);
			//dirNetOpp1.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp1.getEvalNetImpl().initializeWeights(dirNetOpp1.getNet());
			dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());

			dirNetOpp2.setBoardUtils(bu);
			dirNetOpp2.setFeatures(features);
			//dirNetOpp2.getEvalNet().setBias(Configuration.BIAS_VALUE);
			dirNetOpp2.getEvalNetImpl().initializeWeights(dirNetOpp2.getNet());
			dirNetOpp2.getEvalNetImpl().initializeHiddenUnits(dirNetOpp2.getNet());

			for(int k = 0; k < boards.length; k++){
				
				logger.warn("###########################:::: BOARD " + k +" ::::############################");
				
			//	bu.mapToXBoard(boards[k].getBoard(), xBoards[k].getXBoard());
				
				Node current = new Node();
				current.setBoard(boards[k]);
				current.setxBoard(xBoards[k]);
				current.setDepth(0);
				//current.setName("S1");
				current.setKey(new Key(zobristKey.getKey64(current), zobristKey.getKey32(current)));
				current.setType(Type.MAX.getValue());
				current.setVisited(false);
				
				long timeInitial = System.currentTimeMillis();

				Node currentTemp = current.clone();
				
				//A rede que está treinando ora está como peça preta, ora como peça vermelha. Por isso há a alternância de jogadores.
				if(opp1IsBlack){
					logger.info("BlackPlayer NET: dirNetOpp1 - RedPlayer: dirNetOpp2");
					autoPlay(currentTemp, Player.BLACK, Player.RED, Player.BLACK, dirNetOpp1, dirNetOpp2);
					
				}else{
					logger.info("BlackPlayer NET: dirNetOpp2 - RedPlayer: dirNetOpp1");
					autoPlay(currentTemp, Player.BLACK, Player.RED, Player.RED, dirNetOpp2, dirNetOpp1);
				}
				
				currentTemp = null;
				
				System.gc();
				
				logger.warn("Total Time: " + (System.currentTimeMillis() - timeInitial));
				
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
			}*/
			
			playClones(numClones, boards[0]);
			
			
		} catch (IOException | PlayerException e) {
			e.printStackTrace();
		} 
	}
	
	

	private void playClones(int numClone, Board b) {

		Map<String, Integer> wins = new HashMap<String, Integer>();
		
		IHashKey zobristKey = new ZobristKey();

		Net netOpp1 = null;
		Net netOpp2 = null;

		try {

			if (numClone == 1) {
				fm.copyFile(basePath + netFile, clonesPath + "/CLONE" + numClone + Configuration.NET_EXTENSION);
				numClone++;
			}

			// Torneio entre os clones
			for (int i = 0; i < numClone; i++) {

				netOpp1 = fm.readFile(clonesPath + "/CLONE" + i + Configuration.NET_EXTENSION);

				dirNetOpp1.setNet(netOpp1);
				dirNetOpp1.getEvalNetImpl().initializeHiddenUnits(dirNetOpp1.getNet());
				
				Node current = new Node();
				current.setBoard(b);
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

						String key = String.valueOf(i) + String.valueOf(j);

						if (stateGame == GameState.BLACK_WON) {
							wins.put(key, i);
						} else if (stateGame == GameState.RED_WON) {
							wins.put(key, j);
						} else {
							wins.put(key, -1);
						}

					}
				}
			}
			
			Set<String> keys = wins.keySet();
			
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
			}
			
			System.out.println("O CLONE VENCEDOR É O CLONE: " + theWin);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int autoPlay(Node current, Player blackPlayer, Player redPlayer, Player playerTraining, DirectNet netBlack, DirectNet netRed)
			throws SearchException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InvalidBoardException, MlpException, TranspositionTableException, IterativeDeepeningException {

		IAlphaBeta ab = null;
		
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

		Move lstMvBlack = null;
		Move lstMvRed = null;

		System.out.println("INICIAL");
		bu.printBoard(current.getBoard());
		
		//TODO: IMPLEMENTAR EM BOARDUTILS método para contar número de peças no tabuleiro
		//ajustar condições abaixo para o caso de haver necessidade de trocar de peça.
		//criar método para carregar uma lista de redes. Vamos pega-las de acordo com o índice
		//incluir método classifica que realiza o cálculo da distância euclidiana para poder pegar o índice do cluster correto

		if(whoStarts.equals(Player.BLACK)){
			makeMove(netBlack, lstMvBlack, current, blackPlayer, ab);
			logger.warn("BlackPlayer: currentBoard: \n");
		}else{
			makeMove(netRed, lstMvRed, current, redPlayer, ab);
			logger.warn("RedPlayer: currentBoard: \n");
		}
		
		bu.printBoard(current.getBoard());

		logger.warn("Total moves: " + (ab.getNodesEvaluated()));
		
		logger.warn("Jogada realizada em (milissegundos): " + (System.currentTimeMillis() - timeInitial));

		if(ab != null){
			ab.finalizeSearch();
		}
		
		return 0;
		
	}

	public Move makeMove(DirectNet dirNet, Move lstMv, Node current, Player player, IAlphaBeta ab) throws SearchException, TranspositionTableException, IterativeDeepeningException, InvalidBoardException {
		
		Long initialTime = System.currentTimeMillis();

		((BoardEvaluation) ab.getEvaluation()).setDirNet(dirNet);
		
		//tcache = new TranspositionCache(new ZobristKey(), Configuration.REGION_MASTER);
		tcache = new TranspositionTable();
		
		float result = 0f;
		
		Move move = new Move();
		
		logger.info("quem joga:" + player);
		
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
		
		current.setEvaluation(result);
		
		move = (Move) ab.getTheBestMove();
		
		if(move != null) {
			logger.warn("Resultado: " + result + " Movimento: " + move.toString());

			doMove(current, move);
		}
		
		bu.mapToBoard(current.getBoard().getBoard(), current.getxBoard().getXBoard());
		
		try {
			
			if(move != null)
				pdn.writeMove(move.getFrom(), move.getTo());
			
			//FIXME: descomentar aqui se a classe estiver sendo usada para uma disputa do tipo FIGHT em que se deseja salvar o estado do movimento executado.
			//fm.writeBoardStateFile(current.getBoard().getBoardRepresentation(), (basePath + boardFile));
			
			int d = searchDepth;
			
			if(ab instanceof MasterAlphaBeta) {
				d = ((MasterAlphaBeta) ab).getDepthReached();
			}
			
			if(move != null) {
				fm.writeOutputFile(numFile + "\t" + player + "\t" + d + "\t" + (System.currentTimeMillis() - initialTime) + "\t" + result + "\t" + ab.getNodesEvaluated() + "\t" + move.toString(), "M");
				
				logger.info("Número de nós avaliados mestre: " + ab.getNodesEvaluated());
				logger.info("Movimento executado: " + move.toString());
				logger.info("[" + player + "] Time move (milissegundos): " + (System.currentTimeMillis() - initialTime));
				logger.info("Resultado da busca: " + result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		move = null;
		
		System.gc();

		return lstMv;
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
		
		if (Integer.parseInt(SystemConfigs.getInstance().getConfig("whoStarts").trim()) == BoardValues.REDMAN) {
			whoStarts = Player.RED;
		} else {
			whoStarts = Player.BLACK;
		}

		gameLimit = Integer.parseInt(SystemConfigs.getInstance().getConfig("gameLimit").trim());
		testAfter = Integer.parseInt(SystemConfigs.getInstance().getConfig("testAfter").trim());
		searchDepth = Integer.parseInt(SystemConfigs.getInstance().getConfig("searchDepth").trim());
		timeMove = Integer.parseInt(SystemConfigs.getInstance().getConfig("timeMove"));

		isNewNetwork = Boolean.parseBoolean(SystemConfigs.getInstance().getConfig("newNetwork"));

		clonesPath = SystemConfigs.getInstance().getConfig("clonesPath");
		basePath = SystemConfigs.getInstance().getConfig("basePath");
		featureFile = SystemConfigs.getInstance().getConfig("featureFile");
		netFile = SystemConfigs.getInstance().getConfig("netFile");
		boardFile = SystemConfigs.getInstance().getConfig("boardFile");
		
		searchDefinition = SystemConfigs.getInstance().getConfig("search-definition-game");
		
		//playerLoserEvaluation = new BigDecimal(SystemConfigs.getInstance().getConfig("player-loser-evaluation").trim());
		
		logger.info("Busca utilizada no jogador: " + searchDefinition);
		logger.info("GameLimit: " + gameLimit + " TestAfter: " + testAfter + " Total: " + (gameLimit * testAfter) + " games.");
		logger.warn("Profundidade da busca: " + searchDepth);
	}
	
	public void setNumFile(int numFile) {
		this.numFile = numFile;
	}

	public static void main(String[] args) throws SearchException {

		GameOneMove p = new GameOneMove();
		
		if(args.length == 0){
			throw new SearchException("The file number does not informed.");
		}
		
		Integer numFile = Integer.parseInt(args[0].trim());

		p.setNumFile(numFile);
		p.play();
		
		//p.playWithoutTraining();
		
		//p.playClones();
	}

}