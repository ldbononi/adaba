package ufu.facom.lia.search;

import java.util.List;

import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Status;
import ufu.facom.lia.search.interfaces.Sucessor;

public class MasterOwnExploration {
	
	private Sucessor sucessor;
	private Evaluation evaluation;
	
	private ICache cache;
	
	public MasterOwnExploration(Sucessor sucessor, Evaluation evaluation, ICache cache){
		this.sucessor = sucessor;
		this.evaluation = evaluation;
		this.cache = cache;
	}
	
	private float search(IState state,  IMove move, int depth, float alpha, float beta, Player player)
			throws SearchException, TranspositionTableException, IterativeDeepeningException{
		
		IAlphaBeta ab = new AlphaBetaFailSoft(evaluation, sucessor);
		
		float result =  ab.alphaBetaTT(state.getNode(), move, depth, alpha, beta, player, cache);
		
		return result;
		
	}
	
	/*public void searchBatch(List<IState> states,  int depth, float alpha, float beta, Player player)
			throws SearchException, TranspositionTableException, IterativeDeepeningException{
		
		System.out.println("[MASTER_OWN_EXPLORATION] veio aki!!!");
		
		for(IState s: states){
			
			s.getNode().setEvaluation(search(s, depth, alpha, beta, player));
			s.setStatus(Status.CERTAIN);
		}
	}*/
}
