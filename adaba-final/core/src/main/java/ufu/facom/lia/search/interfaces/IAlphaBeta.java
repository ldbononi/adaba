package ufu.facom.lia.search.interfaces;

import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;

public interface IAlphaBeta {

	/**
	 * Busca alpha-beta sem tabela de transposição e sem rede neural (como
	 * argumento)
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
	 * 
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 */
	float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player)
			throws SearchException;

	/**
	 * Busca alpha-beta sem tabela de transposição
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
	 * @param net
	 *            - rede neural utilizada pela função de avaliação
	 * 
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 */
	float alphaBeta(INode node, IMove move, Integer depth, float min, float max, Player player, INet net)
			throws SearchException;

	/**
	 * Busca alpha-beta com tabela de transposição
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
	 * @param net
	 *            - rede neural utilizada pela função de avaliação
	 * 
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 */
	float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player, INet net,
			ICache cache) throws SearchException, TranspositionTableException, IterativeDeepeningException;

	/**
	 * Busca alpha-beta com tabela de transposição e sem rede neural (como
	 * argumento)
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
	 * 
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 */
	float alphaBetaTT(INode node, IMove move, Integer depth, float min, float max, Player player, ICache cache)
			throws SearchException, TranspositionTableException, IterativeDeepeningException;

	/**
	 * Busca alpha-beta com tabela de transposição e aprofundamento iterativo
	 * limitado pelo tempo
	 * 
	 * @param node
	 *            - estado corrente a partir do qual a busca será realizada
	 * @param depth
	 *            - produndidade máxima que a busca será realizada caso o limite
	 *            de tempo não seja atingido
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
	 *            - tempo máximo que o movimento pesquisado deve ser retornado
	 * @param net
	 *            - rede neural utilizada pela função de avaliação
	 * @param cache
	 *            - estrutura da tabela de transposição a ser utilizada
	 * @return - Valor da predição do nó passado no root da busca
	 * @throws SearchException
	 * @throws TranspositionTableException
	 */
	float alphaBetaIDTimeLimited(INode node, IMove move, Integer depth, float min, float max, Player player,
			Integer incrementalID, Integer timeMove, INet net, ICache cache)
					throws SearchException, TranspositionTableException;

	Evaluation getEvaluation();

	Sucessor getSucessor();

	void setCache(ICache cache);
	
	boolean finalizeSearch();
	
	Integer getNodesEvaluated();
	
	IMove getTheBestMove();

}
