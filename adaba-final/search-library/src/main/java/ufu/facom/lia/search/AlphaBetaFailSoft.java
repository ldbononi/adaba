package ufu.facom.lia.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IInfoNode;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.Location;
import ufu.facom.lia.interfaces.ScoreType;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.IAlphaBeta;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;

/**
 * Retorna o verdadeiro valor minimax
 * 
 * @author LIDIA
 *
 */
public class AlphaBetaFailSoft implements IAlphaBeta {
	
	private static final Logger logger = LogManager.getLogger(AlphaBetaFailSoft.class);

	private Evaluation evaluation;

	private Sucessor sucessor;

	private ICache tcache;

	//private List<INode> boardStates;
	private Map<Key, INode> boardStates;
	
	private Map<Key, INode> statesEvaluated;

	private Integer masterDepth;

	private float globalMin;

	private float globalMax;

	private Map<Integer, Float> historicMasterResults;

	private Integer currentMasterDepth;
	
	private int nodesEvaluated;
	
	private INode eldestBrother;
	
	private IMove thebest;

	/**
	 * @param evaluation
	 *            - Classe de avaliação dos movimentos
	 * @param sucessor
	 *            - Classe que apresenta métodos para gerar os sucessores de um
	 *            movimento
	 */
	public AlphaBetaFailSoft(Evaluation evaluation, Sucessor sucessor) {
		this.evaluation = evaluation;
		this.sucessor = sucessor;
	}

	/**
	 * @param evaluation
	 *            - Classe de avaliação dos movimentos
	 * @param sucessor
	 *            - Classe que apresenta métodos para gerar os sucessores de um
	 *            movimento
	 * @param tcache
	 *            - Tabela de Transposição
	 */
	public AlphaBetaFailSoft(Evaluation evaluation, Sucessor sucessor, ICache tcache) {

		this(evaluation, sucessor);
		this.tcache = tcache;
	}
	
	/**
	 * Método de busca por aprofundamento iterativo, neste método o
	 * aprofundamento irá até o limite estabelecido no argumento 'depth', isto
	 * é, não haverá interrupção
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade máxima que a busca será realizada
	 * @param min
	 *            - alpha da janela de busca
	 * @param max
	 *            - beta da janela de busca
	 * @param player
	 *            - jogador responsável pelo nível 0 (root) da busca
	 * @param incrementalID
	 *            - valor a ser incrementado na profundidade de busca a cada
	 *            iteração
	 * @return
	 * @throws SearchException
	 */
	public float alphaBetaID(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID) throws SearchException {

		float result = 0f;
		
		for (int i = incrementalID; i <= depth; i += incrementalID) {

			try {
				result = alphaBetaTT(node, move, i, min, max, player, null, tcache);
				
			} catch (TranspositionTableException | IterativeDeepeningException e) {
				e.printStackTrace();
			}
		}

		return result;

	}
	
	public float alphaBetaID(INode node, IMove move, Integer depth, float min, float max, Player player, ICache cache,
			Integer incrementalID) throws SearchException {

		float result = 0f;

		//TODO: AJUSTAR AKI
		for (int i = 4; i <= depth; i += incrementalID) {
		//for (int i = incrementalID; i <= depth; i += incrementalID) {

			try {
				result = alphaBetaTT(node, move, i, min, max, player, null, cache);
				
			} catch (TranspositionTableException | IterativeDeepeningException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	/**
	 * Método de busca por aprofundamento iterativo com limite de tempo, isto é,
	 * a busca será interrompida se: 1) o limite máximo de profundidade da busca
	 * for atingido; 2) se o tempo máximo para realização do movimento for
	 * atingido - argumento 'timeMove'
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade máxima que a busca será realizada
	 * @param min
	 *            - alpha da janela de busca
	 * @param max
	 *            - beta da janela de busca
	 * @param player
	 *            - jogador responsável pelo nível 0 (root) da busca
	 * @param incrementalID
	 *            - valor a ser incrementado na profundidade de busca a cada
	 *            iteração
	 * @param timeMove
	 *            - limite de tempo máximo para realização da busca
	 * @return
	 * @throws SearchException
	 */
	@Override
	public float alphaBetaIDTimeLimited(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID, Integer timeMove, INet net, ICache cache) throws SearchException, TranspositionTableException {

		float result = 0f;

		Long time = System.currentTimeMillis() + timeMove;
		
		if(this.tcache == null){
			tcache = cache;
		}
		
		int deep = 0;

		try {
			for (int i = incrementalID; i <= depth; i += incrementalID) {

				System.out.println("############## Iteração: " + i);
				
				nodesEvaluated = 0;
				
				if (System.currentTimeMillis() > time) {
					throw new IterativeDeepeningException("Tempo ID atingido");
				}

				//System.out.println("só para saber: max:" + max + " min:" + min);
				
				//node.setChildren(null);
				node.setEvaluation(0);
				
				//System.out.println("Profundidade atual: " + i);
				
				result = alphaBetaTTWithTimeLimit(node, move, i, i, min, max, player, time);
				
				//System.out.println("Nós avaliados: " + nodesEvaluated);
				
				deep = i;

			}

		} catch (SearchException e) {
			System.out.println(e.getMessage());
			
		} catch (IterativeDeepeningException e) {
			
			node.setEvaluation(result);
			logger.warn("Tempo de busca atingido. Profundidade utilizada: " + deep);
			//e.printStackTrace();
		}

		return result;

	}

	/**
	 * Método de busca 'alpha-beta' sem tabela de transposição
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade máxima que a busca será realizada
	 * @param min
	 *            - alpha da janela de busca
	 * @param max
	 *            - beta da janela de busca
	 * @param player
	 *            - jogador responsável pelo nível 0 (root) da busca
	 * 
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 */
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player)
			throws SearchException {

		float besteval;
		// System.out.println("Node: " + node.getName() + " Profundidade: " +
		// depth + " Player: " + player);
//	   System.out.println("[ALPHA-BETA ALG] Janela: [" + min + ", " + max + "]");
	   
			globalMin = min;
		
			globalMax = max;
		
		nodesEvaluated++;

		if (node.getType() == Type.LEAF.getValue() || depth == 0) {

			float bd;

			bd = getEvaluation(node, min, max);

			if (bd == 0f) {
				
				Float e = evaluation.getEvaluation(node, player);
				
				if(e != null){
					bd = e;
				}
			}

			// System.out.println("Result: " + bd.toPlainString());
			node.setEvaluation(bd);
			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			return bd;
		}

		List<INode> states = null;
		
		/*if(node.getChildren() != null && !node.getChildren().isEmpty() && node.getChildren().get(0).getPlayer().equals(player)){
			states = node.getChildren();
		}else{*/
			states = sucessor.getSucessors(node, player);
			/*node.setChildren(states);
		}*/
		
		/*if(node.getChildren().isEmpty()){
			node.setType(Type.LOSER.getValue());
			return Float.parseFloat(SystemConfigs.getInstance().getConfig("player-loser-evaluation"));
		}

		node.setChildren(states);
*/		// System.out.println("Nó: " + node.getName() + " brach: " +
		// states.size());
		//node.setBranchRate(states.size()); // FIXME: added here to record the
											// number of children (branch
											// factor)

		if (node.getType() == Type.MAX.getValue()) {

			besteval = min;

			if (states != null) {
				for (INode child : states) {

					child.setEvaluation(alphaBeta(child, move, depth - 1, besteval, max, otherPlayer(player)));
					child.setVisited(true);
					
					
					
					if (child.getType() == Type.LEAF.getValue()) {

						// FIXME: Olhar aqui, pois avaliação foi modificada por
						// conta do APHID
						float bd = getEvaluation(node, min, max);

						if (bd == 0f) {
							// TODO: olhar aqui por conta do jogador, parece que
							// isso aqui tá chamando a função repetidamente
							
							Float e = evaluation.getEvaluation(child, player);
							
							if(e != null){
								bd = e;
							}
						} 
						
						child.setEvaluation(bd);
					}
					

					if (child.getEvaluation() > besteval) {
						besteval = child.getEvaluation();
						
						move = child.getMove();
						thebest = child.getMove();
					}

					if (besteval >= max) {
						node.setScoreType(ScoreType.AT_LEAST.getValue());
						
						return besteval;
					}
				}
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			thebest = move;
			
			return besteval;
		}

		if (node.getType() == Type.MIN.getValue()) {

			besteval = max;

			if (states != null) {
				for (INode child : states) {

					child.setEvaluation(alphaBeta(child, move, depth - 1, min, besteval, otherPlayer(player)));
					child.setVisited(true);
					
					
					if (child.getType() == Type.LEAF.getValue()) {

						// FIXME: Olhar aqui, pois avaliação foi modificada por
						// conta do APHID
						float bd = getEvaluation(node, min, max);

						if (bd == 0f) {
							// TODO: olhar aqui por conta do jogador, parece que
							// isso aqui tá chamando a função repetidamente
							
							Float e = evaluation.getEvaluation(child, player);
							
							if(e != null){
								bd = e;
							}
						} 
						
						child.setEvaluation(bd);
					}
					
					if (child.getEvaluation() < besteval) {
						besteval = child.getEvaluation();
						
						move = child.getMove();
						thebest = child.getMove();
						
					}

					if (besteval <= min) {
						node.setScoreType(ScoreType.AT_MOST.getValue());
						
						// FIXME: alterado aki para gravar os valores da janela alpha-beta,
						// olha toda a condição, pois houveram diversas modificações para o
						// APHID
						setGlobalMax(max);
						setGlobalMin(min);
						return besteval;
					}
				}
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			thebest = move;

			return besteval;
		}

		return 0f;
	}

	/**
	 * Busca alpha-beta com tabela de transposição e interrupção caso o tempo de
	 * busca tenha sido atingido
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade que a busca será realizada
	 * @param min
	 *            - alpha da janela de busca
	 * @param max
	 *            - beta da janela de busca
	 * @param player
	 *            - jogador responsável pelo nível 0 (root) da busca
	 * @param timeMove
	 *            - duração de um movimento para realização da busca
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 * @throws IterativeDeepeningException 
	 */
	private float alphaBetaTTWithTimeLimit(INode node, IMove move, Integer depth, Integer maxDepth, float min, float max, Player player, Long timeSearch)
			throws SearchException, TranspositionTableException, IterativeDeepeningException {
		
		//logger.info("Tempo atual: " + System.currentTimeMillis() + " tempo da busca: " + timeSearch);
		
		//logger.warn("Profundidade : " + depth);
				
		if(timeSearch != null && System.currentTimeMillis() > timeSearch){
			throw new IterativeDeepeningException("Time exceeded.");
		}

		float besteval;
		
	
		//TODO: realizar teste verificando as referências dos objetos criados... endereço de memória... pois se forem diferentes vai criar muita coisa na memória e isso não é bom!!!
		
		//tcache.get(node, maxDepth);
		/*
		if(n != null) {
			node = null;
			node = n;
		}*/
		
			globalMin = min;
		
			globalMax = max;
	
		
		nodesEvaluated++;

		if (node.getType() == Type.LEAF.getValue() || depth == 0) {

			

			float bd;

			bd = getEvaluation(node, min, max);

			if (bd == 0f){ 
				Float e = evaluation.getEvaluation(node, player);
				
				if(e != null){
					bd = e;
				}
			
			}

			node.setEvaluation(bd);
			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			//synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			//}
			
			if(boardStates != null && depth == 0){
				boardStates.put(node.getKey(), node);
			}
			
			return bd;
		}

		List<INode> states = sucessor.getSucessors(node, player);
		
		if(states!=null && !states.isEmpty()){
			//Collections.sort(states);//ordena os filhos
			states.get(0).setLocation(Location.PV.getValue());
			short l = Location.PV.getValue();
			/*System.out.println("\n");
			for(short k = 0; k < states.size(); k++) {
				System.out.print(states.get(k).getMove().toString() + " ");
			}
			System.out.println("\n");*/
			for(short k = 1; k < states.size(); k++){
				states.get(k).setLocation(++l);
			}
			if(eldestBrother == null) {
				eldestBrother = states.get(0);
			}
		}
		
		if (node.getType() == Type.MAX.getValue()) {

			besteval = min;
			
			int qtd = 0;
			
			for (INode child : states) {
				if(tcache.getToHeuritic(child, node.getType(), maxDepth, besteval, max)){
					qtd++;
				}
			}
			
			if(qtd == states.size()){
				Collections.sort(states);
			}
			
			if (states != null) {
				for (INode child : states) {
					
					//System.out.println("Pesquisa c: " + child.getName() + " d: " + child.getDepth() + " janela: [" + besteval + ", " + max + "] e pseudoValue: " + evaluation.getEvaluation(child, player));
					
					//oolean retrieved = tcache.get(child, node.getType(), maxDepth, besteval, max);
					Boolean retrieved = false;
					
					//System.out.println("REtrived: " + retrieved + " para nó: " + child.getName());
					
					if(!retrieved){
						child.setEvaluation(alphaBetaTTWithTimeLimit(child, move, depth - 1, maxDepth, besteval, max, otherPlayer(player), timeSearch));
						child.setVisited(true);
						
						if (child.getType() == Type.LEAF.getValue()) {

							// FIXME: Olhar aqui, pois avaliação foi modificada por
							// conta do APHID
							float bd = getEvaluation(node, min, max);

							if (bd == 0f) {
								// TODO: olhar aqui por conta do jogador, parece que
								// isso aqui tá chamando a função repetidamente
								
								Float e = evaluation.getEvaluation(child, player);
								
								if(e != null){
									bd = e;
								}
							} 
							
							child.setEvaluation(bd);
						}
					}

					if (child.getEvaluation() > besteval) {
						besteval = child.getEvaluation();
						
						move = child.getMove();
						
						thebest = child.getMove();
						
					}

					if (besteval >= max) {
						child.setScoreType(ScoreType.AT_LEAST.getValue());
						
						//synchronized (tcache) {
							tcache.store(child, ScoreType.AT_LEAST.getValue());
						//}
							// FIXME: alterado aki para gravar os valores da janela alpha-beta,
							// olha toda a condição, pois houveram diversas modificações para o
							// APHID
							setGlobalMax(max);
							setGlobalMin(min);
						return besteval;
					}
				}
				
				
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			//synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			//}
				
			thebest = move;
			move = thebest;

			return besteval;
		}

		if (node.getType() == Type.MIN.getValue()) {

			besteval = max;
			
			int qtd = 0;
			
			for (INode child : states) {
				if(tcache.getToHeuritic(child, node.getType(), maxDepth, besteval, max)){
					qtd++;
				}
			}
			
			if(qtd == states.size()){
				Collections.sort(states);
			}

			if (states != null) {
				for (INode child : states) {
					
					//System.out.println("Pesquisa c: " + child.getName() + " d: " + child.getDepth() + " janela: [" + min + ", " + besteval + "] e pseudoValue: " + evaluation.getEvaluation(child, player));
					
					//Boolean retrieved = tcache.get(child, node.getType(), maxDepth, min, besteval);
					Boolean retrieved = false;
					
					
					//System.out.println("REtrived: " + retrieved + " para nó: " + child.getName());

					
					if(!retrieved){
						child.setEvaluation(alphaBetaTTWithTimeLimit(child, move, depth - 1, maxDepth, min, besteval, otherPlayer(player), timeSearch));
						child.setVisited(true);
						

						if (child.getType() == Type.LEAF.getValue()) {

							// FIXME: Olhar aqui, pois avaliação foi modificada por
							// conta do APHID
							float bd = getEvaluation(node, min, max);

							if (bd == 0f) {
								
								Float e = evaluation.getEvaluation(child, player);
								
								if(e != null){
									bd = e;
								}
							} 
							
							child.setEvaluation(bd);
						}
					}

					if (child.getEvaluation() < besteval) {
						besteval = child.getEvaluation();
						
						move = child.getMove();
						
						thebest = child.getMove();
						
					}

					if (besteval <= min) {
						child.setScoreType(ScoreType.AT_MOST.getValue());
						
						//synchronized (tcache) {
							tcache.store(child, ScoreType.AT_MOST.getValue());
						//}

						return besteval;
					}
				}
				
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			//synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			//}
			
			thebest = move;
			move = thebest;
				

			return besteval;
		}
		
		return 0f;
	}
	
	/**
	 * Busca alpha-beta com tabela de transposição do ramo mais a esquerda da árvore de busca (oldest brother)
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade que a busca será realizada
	 * @param min
	 *            - alpha da janela de busca
	 * @param max
	 *            - beta da janela de busca
	 * @param player
	 *            - jogador responsável pelo nível 0 (root) da busca
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 * @throws IterativeDeepeningException 
	 */
	public float alphaBetaOldestBrother(INode node, IMove move, Integer depth, Integer maxDepth, float min, float max, Player player)
			throws SearchException, TranspositionTableException, IterativeDeepeningException {
		
		float besteval;

		if (node.getType() == Type.LEAF.getValue() || depth == 0) {

			// FIXME: alterado aki para gravar os valores da janela alpha-beta,
			// olha toda a condição, pois houveram diversas modificações para o
			// APHID
			setGlobalMax(max);
			setGlobalMin(min);

			float bd;

			bd = getEvaluation(node, min, max);

			if (bd == 0f){ 
				Float e = evaluation.getEvaluation(node, player);
				
				if(e != null){
					bd = e;
				}
			
			}
			
			node.setEvaluation(bd);
			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			}
			
			if(boardStates != null && depth == 0){
				boardStates.put(node.getKey(), node);
			}
			
			System.out.println("Alpha: " + min + " Beta: " + max);
			
			return bd;
		}

		List<INode> states = null;
		
		/*if(node.getChildren() != null && !node.getChildren().isEmpty() && node.getChildren().get(0).getPlayer().equals(player)){
			states = node.getChildren();
		}else{*/
			states = sucessor.getSucessors(node, player);
			/*node.setChildren(states);
		}
*/
		if (node.getType() == Type.MAX.getValue()) {

			besteval = min;

			if (states != null) {
				for(int i = 0; i < states.size(); i++){
					
					if(i == 0){
						
						INode child = states.get(i);
						
						//System.out.println("Pesquisa c: " + child.getName() + " d: " + child.getDepth() + " janela: [" + besteval + ", " + max + "] e pseudoValue: " + evaluation.getEvaluation(child, player));
						
						Boolean retrieved = false;
						
						synchronized (tcache) {
							retrieved = tcache.get(child, node.getType(), maxDepth, besteval, max);
						}
						
						//System.out.println("REtrived: " + retrieved + " para nó: " + child.getName());
						
						if(!retrieved){
							child.setEvaluation(alphaBetaOldestBrother(child, move, depth - 1, maxDepth, besteval, max, otherPlayer(player)));
							child.setVisited(true);

							if (child.getType() == Type.LEAF.getValue()) {

								// FIXME: Olhar aqui, pois avaliação foi modificada por
								// conta do APHID
								float bd = getEvaluation(node, min, max);

								if (bd == 0f) {
									// TODO: olhar aqui por conta do jogador, parece que
									// isso aqui tá chamando a função repetidamente
									
									Float e = evaluation.getEvaluation(child, player);
									
									if(e != null){
										bd = e;
									}
								} 
								
								child.setEvaluation(bd);
							}
						}

						if (child.getEvaluation() > besteval) {
							besteval = child.getEvaluation();
							
							//if(node.getMove()!=null){
								move.setFrom(child.getMove().getFrom());
								move.setTo(child.getMove().getTo());
								move.setSeqDirs(child.getMove().getSeqDirs());	
							//}
						}

						if (besteval >= max) {
							child.setScoreType(ScoreType.AT_LEAST.getValue());
							
							synchronized (tcache) {
								tcache.store(child, ScoreType.AT_LEAST.getValue());
							}
							return besteval;
						}
					}
				}
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			}
			return besteval;
		}

		if (node.getType() == Type.MIN.getValue()) {

			besteval = max;

			if (states != null) {
				for(int i = 0; i < states.size(); i++){
				
					if(i == 0){
						INode child = states.get(i);
						
						//System.out.println("Pesquisa c: " + child.getName() + " d: " + child.getDepth() + " janela: [" + min + ", " + besteval + "] e pseudoValue: " + evaluation.getEvaluation(child, player));
						
						Boolean retrieved = false;
						
						synchronized (tcache) {
							retrieved = tcache.get(child, node.getType(), maxDepth, min, besteval);
						}
						
						//System.out.println("REtrived: " + retrieved + " para nó: " + child.getName());

						
						if(!retrieved){
							child.setEvaluation(alphaBetaOldestBrother(child, move, depth - 1, maxDepth, min, besteval, otherPlayer(player)));
							child.setVisited(true);

							if (child.getType() == Type.LEAF.getValue()) {

								// FIXME: Olhar aqui, pois avaliação foi modificada por
								// conta do APHID
								float bd = getEvaluation(node, min, max);

								if (bd == 0f) {
									
									Float e = evaluation.getEvaluation(child, player);
									
									if(e != null){
										bd = e;
									}
								} 
								
								child.setEvaluation(bd);
							}
						}else{
							//child.setParent(node);
							//child.setName(sucessor.getName(child, node));
						}

						if (child.getEvaluation() < besteval) {
							besteval = child.getEvaluation();
							
							/*if(node.getMove()!=null){
								move.setFrom(node.getMove().getFrom());
								move.setTo(node.getMove().getTo());
								move.setSeqDirs(node.getMove().getSeqDirs());	
							}*/
							move.setFrom(child.getMove().getFrom());
							move.setTo(child.getMove().getTo());
							move.setSeqDirs(child.getMove().getSeqDirs());	
						}

						if (besteval <= min) {
							child.setScoreType(ScoreType.AT_MOST.getValue());
							
							synchronized (tcache) {
								tcache.store(child, ScoreType.AT_MOST.getValue());
							}
							return besteval;
						}
					}
				}
			}

			node.setScoreType(ScoreType.HASH_EXACT.getValue());
			
			synchronized (tcache) {
				tcache.store(node, ScoreType.HASH_EXACT.getValue());
			}
			
			return besteval;
		}
		
		if(node.getMove()!=null){
			move.setFrom(node.getMove().getFrom());
			move.setTo(node.getMove().getTo());
			move.setSeqDirs(node.getMove().getSeqDirs());	
		}

		return 0f;
	}
	
	@Override
	public float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player, INet net)
			throws SearchException {

		return alphaBeta(node, move, depth, min, max, player);
	}

	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player, INet net, ICache cache)
			throws SearchException, TranspositionTableException, IterativeDeepeningException {
		
		this.tcache = cache;
		return alphaBetaTTWithTimeLimit(node, move, depth, depth, min, max, player, null);
	}
	
	@Override
	public float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player,
			ICache cache) throws SearchException, TranspositionTableException, IterativeDeepeningException {
		
		return alphaBetaTT(node, move, depth, min, max, player, null, cache);
	}


	public Player otherPlayer(Player p) {

		if (p.equals(Player.BLACK)) {
			return Player.RED;
		}

		return Player.BLACK;
	}

	/*public List<INode> getBoardStates() {
		return boardStates;
	}

	public void setBoardStates(List<INode> generalStates) {
		this.boardStates = generalStates;
	}*/

	public Map<Key, INode> getBoardStates() {
		return boardStates;
	}

	public void setBoardStates(Map<Key, INode> boardStates) {
		this.boardStates = boardStates;
	}

	public Integer getMasterDepth() {
		return masterDepth;
	}

	public void setMasterDepth(Integer masterDepth) {
		this.masterDepth = masterDepth;
	}

	public float getGlobalMin() {
		return globalMin;
	}

	public void setGlobalMin(float globalMin) {
		this.globalMin = globalMin;
	}

	public float getGlobalMax() {
		return globalMax;
	}

	public void setGlobalMax(float globalMax) {
		this.globalMax = globalMax;
	}
	
	public Map<Key, INode> getStatesEvaluated() {
		return statesEvaluated;
	}

	public void setStatesEvaluated(Map<Key, INode> statesEvaluated) {
		this.statesEvaluated = statesEvaluated;
	}

	private float getEvaluation(INode node, float alpha, float beta) {

		if (statesEvaluated != null && !statesEvaluated.isEmpty()) {
			
			INode n = statesEvaluated.get(node.getKey());
			
			if(n != null) {
				//System.out.println("RETORNO: " + n.getKey().getKey32() + "#" + n.getKey().getKey64() + " " + n.getDepth() + n.getEvaluation());
				return n.getEvaluation();
			}
			
			/*//TODO: verificar nível, tipo e player... se for o mesmo, blz.
			if(statesEvaluated.containsKey(node.getKey())) {
				//System.out.println("RETORNO: " + statesEvaluated.get(node.getKey()).getKey().getKey32() + "#" + statesEvaluated.get(node.getKey()).getKey().getKey64() + " " + statesEvaluated.get(node.getKey()).getDepth() + statesEvaluated.get(node.getKey()).getEvaluation());
				return statesEvaluated.get(node.getKey()).getEvaluation();
			}*/
		}

		return 0f;
	}

	public float getHypotheticalValue() {
		return boardStates.get(0).getEvaluation();
	}

	public synchronized float guessedValue(IState state, float alpha, float beta) {

		Map<Integer, IInfoNode> historic = state.getHistoric();
		float hipotheticalValue = getHypotheticalValue();

		if (historic == null) {
			return 0f;
		}

		// Nesse caso slave já retornou resposta
		if (historic.get(currentMasterDepth) != null) {
			return historic.get(currentMasterDepth).getValue();
		}

		Set<Integer> depths = historic.keySet();

		TreeSet<Integer> shortedDepths = new TreeSet<>();

		for (Integer d : depths) {
			shortedDepths.add(d);
		}

		Iterator<Integer> iterator = shortedDepths.descendingIterator();

		while (iterator.hasNext()) {

			Integer i = iterator.next();

			if (i <= currentMasterDepth) {

				float slaveValue = historic.get(i).getValue();
				float currentSearch = historicMasterResults.get(i);

				float value = hipotheticalValue - currentSearch;
				value = slaveValue - value;

				if (historic.get(i).getScoreType() == ScoreType.HASH_EXACT.getValue()
						|| (value <= alpha || value >=beta)) {

					return value;
				}
			}
		}

		// Se chegar aqui pega da profundidade mais rasa
		float value = (hipotheticalValue - historicMasterResults.get(masterDepth)) - historic.get(shortedDepths.first()).getValue();

		//System.out.println("GUESSED_VALUE: " + value.toPlainString() + " Janela: [" + alpha.toPlainString() + ", "
			//	+ beta.toPlainString() + "]");

		return value;

	}
	
	public IMove getTheBestMove() {
		return thebest;
	}

	@Override
	public Evaluation getEvaluation() {
		return evaluation;
	}

	@Override
	public Sucessor getSucessor() {
		return sucessor;
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
	public boolean finalizeSearch() {
		return true;
	}
	
	public void setNodesEvaluated(Integer nodesEvaluated){
		this.nodesEvaluated = nodesEvaluated;
	}

	@Override
	public Integer getNodesEvaluated() {
		return nodesEvaluated;
	}
	
	public INode getEldestBrother() {
		return eldestBrother;
	}
	
	public void setEldestBrother(INode eldestBrother) {
		this.eldestBrother = eldestBrother;
	}
	
}
