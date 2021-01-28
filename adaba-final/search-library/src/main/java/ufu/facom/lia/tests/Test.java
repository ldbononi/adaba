package ufu.facom.lia.tests;

import java.util.ArrayList;
import java.util.List;

import ufu.facom.lia.cache.TranspositionTableBigTree;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.search.AlphaBetaFailSoft;
//import ufu.facom.lia.search.AlphaBetaFailSoft;
import ufu.facom.lia.search.Minimax;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.Node;

public class Test {
	
	public static void main(String[] args) {
		try {
			System.out.println("ALPHA-BETA");
			Test.alphaBeta();
		} catch (SearchException | TranspositionTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void minimax() throws SearchException {
		
		List<INode> states = new ArrayList<INode>();
		
		Minimax minimax = new Minimax(new BigTreeEvaluation(), new BigTreeSucessor());
		
		minimax.setBoardStates(states);
		
		INode state = new Node();
		
		//state.setName("S1");
		state.setType(Type.MAX.getValue());
		state.setDepth(0);
		
		//for(int i = 2; i <= 6; i+=2){
		
			float result = minimax.minimax(state, 12, Player.BLACK); 
			
			System.out.println("Resultado: " + result);
			
			//System.out.println("tanto de filhos: " + state.getBranchRate());
			
			System.out.println("Total de nós: ");
			
			//TODO: fazer algoritmo para leitura da árvore
			
			
		//}
	}
	
	public static void alphaBeta() throws SearchException, TranspositionTableException {
		
		List<INode> states = new ArrayList<INode>();
		
		AlphaBetaFailSoft ab = new AlphaBetaFailSoft( new BigTreeEvaluation(), new BigTreeSucessor());
		
		//ab.setBoardStates(states);
		
		INode state = new Node();
		
		//state.setName("S1");
		state.setType(Type.MAX.getValue());
		state.setDepth(0);
		
		ICache cache = new TranspositionTableBigTree(null);
		
		//for(int i = 2; i <= 6; i+=2){
		
			//BigDecimal result = ab.alphaBetaID(state, 12, new BigDecimal(String.valueOf(Float.MAX_VALUE * (-1))),
				//	new BigDecimal(String.valueOf(Float.MAX_VALUE)), Player.OPP1, cache, 2);
		
			float result = ab.alphaBetaIDTimeLimited(state, null, 11, (Float.MAX_VALUE * (-1)), Float.MAX_VALUE, Player.BLACK, 2, 320000, null, cache);
			
			System.out.println("Resultado: " + result);
			
			//System.out.println("tanto de filhos: " + state.getBranchRate());
			
			System.out.println("Total de nós: ");
			
			
			//TODO: fazer algoritmo para leitura da árvore
			
			
		//}
	}
}
