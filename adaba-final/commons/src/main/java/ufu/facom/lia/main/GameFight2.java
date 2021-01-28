package ufu.facom.lia.main;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.PlayerException;
import ufu.facom.lia.file.FileManipulate;
import ufu.facom.lia.file.FilePDN;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.Configuration;
import ufu.facom.lia.interfaces.GameState;

/**
 * Esta classe faz uma competição entre dois sistemas em Java, por exemplo, uma versão do APHID e uma versão do ADABA. Portanto, há 2 jars envolvidos.
 * @author eu_li
 *
 */
public class GameFight2 {
	
	enum AgentPlayer{
		ADABA,
		APHID;
	}
	
	private static final Logger logger = LogManager.getLogger(GameFight2.class);
	
	private static int numBoard = 0;

	private Board[] boards;
	
	private String boardFile;
	
	private FileManipulate fm;
	
	private FilePDN pdn;
	
	String aphidcmd;
	
	String adabacmd;
	
	BoardUtils bu;
	
	public GameFight2() {
		fm = new FileManipulate();
		boardFile = SystemConfigs.getInstance().getConfig("basePath") + SystemConfigs.getInstance().getConfig("boardFile");
		pdn = new FilePDN(SystemConfigs.getInstance().getConfig("pdnFileName"));
		bu = new BoardUtils();
	}
	
	private void gameFight() {
		
		String blackcmd = SystemConfigs.getInstance().getConfig("cmdblack");
		String redcmd = SystemConfigs.getInstance().getConfig("cmdred");
		
		boolean b = executeProcess(blackcmd);
		System.out.println("finalizou black: " + b);
		boolean r = executeProcess(redcmd);
		System.out.println("finalizou red: " + r);
	
	}
	
	private boolean executeProcess(String command) {
		
		Long blackModificationBoardFile = fm.lastModification(boardFile);
		
		try {
			Process p = Runtime.getRuntime().exec(command);
			
			//System.out.println(p.pid());
			
			Long currentModificationBoardFile = fm.lastModification(boardFile);
			
			synchronized (this) {
				while(blackModificationBoardFile.equals(currentModificationBoardFile)){
					try {
						this.wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					currentModificationBoardFile = fm.lastModification(boardFile);
				}
			}
				
			p.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
		
	}
	
	
	private int autoPlay(AgentPlayer blackAgent, AgentPlayer redAgent){
		
		Integer moves = 0;
		
		Long timeInitial = System.currentTimeMillis();
		
		//Add Matheus -> variável rodada de movimentos
		Integer count_round = 1;
		
		try{
		
			Integer qtdMoves = 0;
			Integer stateGame = 0;
			
			Integer qtdMvBlack = 0;
			Integer qtdMvRed = 0;
			
			String lstState = "";
			String lstRed = "";
			String lstBlack = "";
			
			Board[] current = null;
			
			if(blackAgent.equals(AgentPlayer.ADABA)){
				adabacmd = SystemConfigs.getInstance().getConfig("cmdblack");
				aphidcmd = SystemConfigs.getInstance().getConfig("cmdred");
			}else {
				adabacmd = SystemConfigs.getInstance().getConfig("cmdred");
				aphidcmd = SystemConfigs.getInstance().getConfig("cmdblack");
			}
			
			pdn.InitGamePDN();
	
			while (stateGame == GameState.CONTINUE) {
				
				pdn.writeRound(count_round++);
				
				if(current != null) {
					lstBlack = current[0].getBoardRepresentation();
				}
				
	
				if(blackAgent.equals(AgentPlayer.ADABA)){
					
					//logger.warn("ADABA JOGANDO COMO PRETO");
					
					if(executeProcess(adabacmd)) {
						current = fm.readBoardFile(boardFile);
					}
					
				}else{
					
					//logger.warn("APHID JOGANDO COMO PRETO");
					
					if(executeProcess(aphidcmd)) {
						current = fm.readBoardFile(boardFile);
					}
					
				}
				
				//bu.printBoard(current[0]);
				//System.out.println(bu.getRepresentation(current[0].getBoard()));
				
				moves++;
				
				if(lstBlack.equals(current[0].getBoardRepresentation())) {
					stateGame = GameState.RED_WON;
				}
				
				stateGame = itsOver(current[0], qtdMoves, qtdMvBlack, qtdMvRed);
	
				if (stateGame == GameState.CONTINUE) {
					
					lstRed = current[0].getBoardRepresentation();
					
					if(redAgent.equals(AgentPlayer.ADABA)){
						
						//logger.warn("ADABA JOGANDO COMO VERMELHO");
						
						if(executeProcess(adabacmd)) {
							current = fm.readBoardFile(boardFile);
						}
						
					}else{
						
						//logger.warn("APHID JOGANDO COMO VERMELHO");
						
						if(executeProcess(aphidcmd)) {
							current = fm.readBoardFile(boardFile);
						}
					}
					
					//bu.printBoard(current[0]);
					//System.out.println(bu.getRepresentation(current[0].getBoard()));
					
					moves++;
					
					if(lstRed.equals(current[0].getBoardRepresentation())) {
						stateGame = GameState.BLACK_WON;
					}
					
					
					if(qtdMoves % 2 == 0) {
						//System.out.println("Last   : " + lstState);
						//System.out.println("current: " + current[0].getBoardRepresentation());
						
						if(lstState.equals(current[0].getBoardRepresentation())){
							qtdMvRed++;
							qtdMvBlack++;
						}else{
							lstState = current[0].getBoardRepresentation();
						}
					}
					
					stateGame = itsOver(current[0], qtdMoves, qtdMvBlack, qtdMvRed);
	
					qtdMoves++;
					
					
				}
			}
			
			if(stateGame == GameState.RED_WON) {
				logger.warn("Jogador VERMELHO ganhou");
			}else if(stateGame == GameState.BLACK_WON) {
				logger.warn("Jogador PRETO ganhou");
			}else if(stateGame == GameState.DRAW) {
				logger.warn("Jogo finalizou como EMPATE");
			}else {
				logger.warn("Jogo finalizou com algo estranho.");
			}
			
			logger.warn("Jogo terminou com " + moves + " movimentos em (milissegundos): " + (System.currentTimeMillis() - timeInitial));
			
			pdn.writeResult(stateGame);
	
			return stateGame;
		
		} catch (IOException | PlayerException e) {
			e.printStackTrace();
		}
		
		return GameState.DRAW;
	}
	
	private int itsOver(Board board, Integer qtdMoves, Integer qtdMvBlack, Integer qtdMvRed) {

		int b = 0, r = 0;

		for (int i = 0; i < board.getBoard().length; i++) {

			switch (board.getBoard()[i]) {
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

		} else if (qtdMoves > Configuration.MIN_MOVES_TO_AVALIATE_GAME && qtdMvBlack >= 5
				&& qtdMvRed >= 5) {

			System.out.println("LOOP IDENTIFICADO: a partida será finalizada como empate");
			return GameState.DRAW;

		} else if (qtdMoves > Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW) {
			System.out.println("A rede já realizou " + Configuration.MOVES_TO_CLASSIFY_LIKE_DRAW
					+ " jogadas! A partida será finalizada como empate.");
			return GameState.DRAW;
		}

		return GameState.CONTINUE;
	}
	
	private void loadBoards() {
		
		if(fm == null) {
			fm = new FileManipulate();
		}
		
		String boardsMatch = SystemConfigs.getInstance().getConfig("matchBoardFiles");
				
		try {

			boards = fm.readBoardFile(boardsMatch);
			
		} catch (IOException | PlayerException e) {
			e.printStackTrace();
		}
		
	}
	
	private void manageBoards() {
		
		try {
			fm.writeBoardStateFile(boards[numBoard].getBoardRepresentation(), boardFile);
			numBoard++;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeTournament(AgentPlayer rPlayer, AgentPlayer bPlayer) {
		
		loadBoards();
		
		if(boards != null) {

			for(int i = 0; i < boards.length; i++) {
				
				logger.warn("############################# BOARD: " + i + " #############################\n");
				
				manageBoards();
				autoPlay(bPlayer, rPlayer);
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		
		String blackPlayer = null;
		String redPlayer = null;
		
		if(args.length == 0){
			blackPlayer = "ADABA";
			redPlayer = "APHID";
		}else{
			blackPlayer = args[0];
			redPlayer = args[1];
		}
		
		AgentPlayer bPlayer, rPlayer;
		
		if(blackPlayer.equals(AgentPlayer.ADABA.toString())){
			bPlayer = AgentPlayer.ADABA;
		}else{
			bPlayer = AgentPlayer.APHID;
		}
		
		if(redPlayer.equals(AgentPlayer.ADABA.toString())){
			rPlayer = AgentPlayer.ADABA;
		}else{
			rPlayer = AgentPlayer.APHID;
		}

		new GameFight2().executeTournament(rPlayer, bPlayer);
		
	}

}