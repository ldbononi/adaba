package ufu.facom.lia.search;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.tests.BigTreeEvaluation;
import ufu.facom.lia.tests.BigTreeSucessor;
import ufu.facom.lia.tests.IMock;
import ufu.facom.lia.tests.MockExemploRita;

public class Minimax implements IAlphaBeta{
	
	private Evaluation evaluation;
	
	private Sucessor sucessor;
	
	private ICache tcache;
	
	private List<INode> boardStates;
	
	private Integer masterDepth;
	
	private BigDecimal globalMin;

	private BigDecimal globalMax;
	
	private Integer currentMasterDepth;
	
	private Map<Integer, Float> historicMasterResults;
	
	public Minimax(Evaluation evaluation, Sucessor sucessor){
		this.evaluation = evaluation;
		this.sucessor = sucessor;
	}
	
	public float minimax(INode node, Integer depth, Player player) throws SearchException{
		
		float besteval;
		//System.out.println("Node: " + node.getName() + " Profundidade: " + depth + " Player: " + player);
		
		if(node.getType() == Type.LEAF.getValue() || depth == 0){
			
			float bd;

			bd = getEvaluation(node);

			if (bd == 0f) {
				
				Float e = evaluation.getEvaluation(node, player);
				
				if(e != null){
					bd = e.floatValue();
				}
			}

			//System.out.println("Result: " + bd.toPlainString());
			return bd;
		}
		
		List<INode> states = sucessor.getSucessors(node, player);
		

		// FIXME:alterado aki para incluir a fronteira da busca
		addBoardStates(states);

		//node.setChildren(states);
		
		if(node.getType() == Type.MAX.getValue()){
			besteval = ((-1)*Float.MAX_VALUE);
			
			if(states!= null){
				for(INode child: states){
					child.setEvaluation(minimax(child, depth-1, otherPlayer(player)));
					child.setVisited(true);
					
					if(child.getEvaluation() > besteval){
						if(child.getType() == Type.LEAF.getValue()){
							
							//FIXME: Olhar aqui, pois avaliação foi modificada por conta do APHID
							float bd = getEvaluation(node);
							
							if(bd == 0f){
								
								Float e = evaluation.getEvaluation(child, player);
								
								if(e != null){
									bd = e.floatValue();
								}
							}
							
							child.setEvaluation(bd);
							
						}else{
							besteval = child.getEvaluation();
						}
						
					}
				}
			}
			return besteval;
		}
		if(node.getType() == Type.MIN.getValue()){
			besteval = Float.MAX_VALUE;
			if(states !=null){
				for(INode child: states){
					child.setEvaluation(minimax(child, depth-1, otherPlayer(player)));
					child.setVisited(true);
					
					if(child.getEvaluation() < besteval){
						if(child.getType() == Type.LEAF.getValue()){		
							
							//FIXME: Olhar aqui, pois avaliação foi modificada por conta do APHID
							float bd = getEvaluation(node);
							
							if(bd == 0f){
								
								Float e = evaluation.getEvaluation(child, player);
								
								if(e != null){
									bd = e.floatValue();
								}
							}
							
							child.setEvaluation(bd);
							
						}else{
							besteval = child.getEvaluation();
						}
					}
				}
			}
			return besteval;
		}
		
		return 0f;
		
	}
	
	private float getEvaluation(INode node) {

		if (boardStates != null && !boardStates.isEmpty()) {
			for (INode st : boardStates) {
				if (st.equals(node)) {
					return st.getEvaluation();
				}
			}
		}

		return 0f;
	}
	
	public void addBoardStates(List<INode> states) {

		children: for (INode st : states) {
			if (masterDepth != null && (masterDepth.equals(st.getDepth()) || st.getType() == Type.LEAF.getValue())) {
				for(INode s : boardStates){
					if(s.equals(st)){
						break children;
					}
				}
				boardStates.add(st);
			}
		}
	}
	
	public Player otherPlayer(Player p) {

		if (p.equals(Player.BLACK)) {
			return Player.RED;
		}

		return Player.BLACK;
	}

	
	public List<INode> getBoardStates() {
		return boardStates;
	}

	public void setBoardStates(List<INode> generalStates) {
		this.boardStates = generalStates;
	}
	
	public Integer getMasterDepth() {
		return masterDepth;
	}

	public void setMasterDepth(Integer masterDepth) {
		this.masterDepth = masterDepth;
	}

	
	public static void main(String[] args) throws SearchException {
		
		IMock mck = new MockExemploRita();
		
		INode root = mck.createMock();
		
		Minimax ex = new Minimax(new BigTreeEvaluation(), new BigTreeSucessor());
		
		float result = ex.minimax(root, 0, Player.BLACK);
		
		System.out.println("Resultado:" + result);
		
	}
	
	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player, INet net,
			ICache cache) throws SearchException {

		this.tcache = cache;
		return minimax(node, depth, player);
	}

	@Override
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player)
			throws SearchException {
		return minimax(node, depth, player);
	}
	
	@Override
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player, INet net)
			throws SearchException {
		return minimax(node, depth, player);
	}
	
	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player,
			ICache cache) throws SearchException {
		
		this.tcache = cache;
		return minimax(node, depth, player);
	}

	@Override
	public Evaluation getEvaluation() {
		return this.evaluation;
	}

	@Override
	public Sucessor getSucessor() {
		return this.sucessor;
	}

	public BigDecimal getGlobalMin() {
		return globalMin;
	}

	public void setGlobalMin(BigDecimal globalMin) {
		this.globalMin = globalMin;
	}

	public BigDecimal getGlobalMax() {
		return globalMax;
	}

	public void setGlobalMax(BigDecimal globalMax) {
		this.globalMax = globalMax;
	}
	
	public Map<Integer, Float> getHistoricMasterResults() {
		return historicMasterResults;
	}

	public void setHistoricMasterResults(Map<Integer, Float> historicMasterResults) {
		this.historicMasterResults = historicMasterResults;
	}
	
	public Integer getCurrentMasterDepth() {
		return currentMasterDepth;
	}

	public void setCurrentMasterDepth(Integer currentMasterDepth) {
		this.currentMasterDepth = currentMasterDepth;
	}

	@Override
	public void setCache(ICache cache) {
		this.tcache = cache;
		
	}

	@Override
	public float alphaBetaIDTimeLimited(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID, Integer timeMove, INet net, ICache cache)
					throws SearchException, TranspositionTableException {
		// TODO Auto-generated method stub
		return 0f;
	}

	@Override
	public boolean finalizeSearch() {
		return true;
	}

	@Override
	public Integer getNodesEvaluated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMove getTheBestMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
