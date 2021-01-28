package ufu.facom.lia.search;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.configs.Configs;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.IterativeDeepeningException;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.exceptions.SlaveException;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;
import ufu.facom.lia.interfaces.SearchDefinition;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.remote.IMasterRemote;
import ufu.facom.lia.remote.ISlaveRemote;
import ufu.facom.lia.remote.SlaveRemote;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;

public class SlaveAlphaBetaExecution {
	
	private static final Logger logger = LogManager.getLogger(SlaveAlphaBetaExecution.class);

	private Sucessor sucessor;

	private Evaluation evaluation;

	private MessageReceived msg;

	private IMasterRemote masterRemote;
	
	private ISlaveRemote slaveRemote;

	private float rootValue;
	
	private float alpha;
	
	private float beta;

	private List<IState> states;

	private ICache cache;
	
	private int masterDepth;
	
	private String searchDefinition;

	private static Integer batchesSent = 1;
	
	private int totalNodesEvaluated;

	public SlaveAlphaBetaExecution(MessageReceived msg, String searchDefinition, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, ISlaveRemote slaveRemote, float rootValue, List<IState> states, ICache cache) {

		this(msg, searchDefinition, masterDepth, evaluation, sucessor, masterRemote, slaveRemote, rootValue, states);

		this.cache = cache;

	}
	
	public SlaveAlphaBetaExecution(MessageReceived msg, String searchDefinition, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, ISlaveRemote slaveRemote, float rootValue, float alpha, float beta, List<IState> states, ICache cache) {

		this(msg, searchDefinition, masterDepth, evaluation, sucessor, masterRemote, slaveRemote, rootValue, alpha, beta, states);

		this.cache = cache;

	}

	public SlaveAlphaBetaExecution(MessageReceived msg, String searchDefinition, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, ISlaveRemote slaveRemote, float rootValue, List<IState> states) {

		this.msg = msg;
		this.searchDefinition = searchDefinition;
		this.masterDepth = masterDepth;
		this.evaluation = evaluation;
		this.sucessor = sucessor;
		this.masterRemote = masterRemote;
		this.slaveRemote = slaveRemote;
		this.rootValue = rootValue;
		this.states = states;
	}
	
	public SlaveAlphaBetaExecution(MessageReceived msg, String searchDefinition, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, ISlaveRemote slaveRemote, float rootValue, float alpha, float beta, List<IState> states) {

		this.msg = msg;
		this.searchDefinition = searchDefinition;
		this.masterDepth = masterDepth;
		this.evaluation = evaluation;
		this.sucessor = sucessor;
		this.masterRemote = masterRemote;
		this.slaveRemote = slaveRemote;
		this.rootValue = rootValue;
		this.alpha = alpha;
		this.beta = beta;
		this.states = states;
	}

	public void execution() throws SlaveException{

		AlphaBetaFailSoft ab = new AlphaBetaFailSoft(evaluation, sucessor);

		if (cache != null) {
			ab.setCache(cache);
			cache.setMasterDepth(masterDepth);
		}

		// Minimax ab = new Minimax(evaluation, sucessor);

		try {

			//float min = Float.valueOf(SystemConfigs.getInstance().getConfig("lower_bound"));
			//float max = Float.valueOf(SystemConfigs.getInstance().getConfig("upper_bound"));
			
			//min = min + rootValue;
			///max = max + rootValue;
			
			//System.out.println("RootValue: " + rootValue + "Janela [" + min + "-" + max +"]");
			
			//logger.info("RootValue: " + rootValue + "LOWER: " + min.toPlainString() + " upper: " + max.toPlainString());
			
			float min = alpha;
			float max = beta;

			msg.setMessageStatus(MessageStatus.INPROCESS);

			int profundidade = (msg.getStateReceived().getCurrentDepth() - msg.getStateReceived().getMasterDepth());
			
			float bd = 0f;
			IMove move = new Move();
			
			ab.setNodesEvaluated(0);
					
			if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST.getValue())){
				bd = ab.alphaBeta(msg.getStateReceived().getNode(), move, profundidade, min, max,
						ab.otherPlayer(msg.getStateReceived().getNode().getPlayer()));

			}else if(searchDefinition.equals(SearchDefinition.DEPTH_FIRST_WITH_TT.getValue())){
				bd = ab.alphaBetaTT(msg.getStateReceived().getNode(), move, profundidade, min, max, 
						ab.otherPlayer(msg.getStateReceived().getNode().getPlayer()), cache);

			}else{
				logger.error("The slave does not executes a search with iterative deepening inside a search. The control is made by the master.");
			}
			
			//logger.warn("Número de nós avaliados ["+msg.getStateReceived().getNode().getKey32()+"] (d=" + msg.getStateReceived().getCurrentDepth() + ") = " + ab.getNodesEvaluated());
			
			this.totalNodesEvaluated = ab.getNodesEvaluated();

			
			/*if(bd == null){
				logger.info("Busca retornou null!");
				System.out.println("Representação do tabuleiro: " + msg.getState().getRepresentation());
			}*/
			
			/*logger.debug("State: k32" + msg.getStateReceived().getNode().getKey32() + " k64: " + msg.getStateReceived().getNode().getKey64() +
					" Profundidade: " + msg.getStateReceived().getCurrentDepth() + " Resultado: " + bd);*/

			//FIXME: não está sendo utilizado nesta implementação
			//Long stateSpace = calculateStateSpace(msg.getState(), 0l);

			//msg.getState().setStateSpace(stateSpace);

			//msg.getStateReceived().setStateSpace(stateSpace);
			msg.getStateReceived().setAlpha(ab.getGlobalMin());
			msg.getStateReceived().setBeta(ab.getGlobalMax());
			msg.getStateReceived().setBranchRate(msg.getStateReceived().getBranchRate());
			msg.getStateReceived().getNode().setEvaluation(bd);
			msg.getStateReceived().setMove(move);

			msg.getStateReceived().setPreviousDepth(msg.getStateReceived().getNode().getDepth());
			msg.getStateReceived().getNode().setDepth(msg.getStateReceived().getCurrentDepth());
			
			if (msg.getStateReceived().getNode().getDepth() < msg.getStateReceived().getMaxDepth() 
					&& msg.getStateReceived().getNode().getType() != Type.LEAF.getValue()) {
				
				msg.setMessageStatus(MessageStatus.ITERATION_ENDED);
			} else {
				msg.setMessageStatus(MessageStatus.PROCESSED);
			}

			/*
			 * if(msg.getStateReceived().getDepth() >=
			 * msg.getStateReceived().getMaxDepth()){
			 * msg.setMessageStatus(MessageStatus.SPECULATIVE); }else{
			 * msg.setMessageStatus(MessageStatus.ITERATION_ENDED); }
			 */

			// caso a nova profundidade seja maior que a profundidade máxima,
			// deixar a profundidade
			if (msg.getStateReceived().getCurrentDepth() + Configs.ITERATIVE_DEEPENING > msg.getStateReceived()
					.getMaxDepth() && !msg.getMessageStatus().equals(MessageStatus.SPECULATIVE)) {
				msg.getStateReceived().setCurrentDepth(msg.getStateReceived().getMaxDepth());
			} else {
				msg.getStateReceived()
						.setCurrentDepth(msg.getStateReceived().getCurrentDepth() + Configs.ITERATIVE_DEEPENING);
			}
			
			//logger.warn(msg.getStateReceived().getCurrentDepth());

			//System.out.println("Message Status: " + msg.getMessageStatus());

			//System.out.println("Batches enviados: " + batchesSent);
			
			if(((SlaveRemote)slaveRemote).getStopSearch()){
				throw new SlaveException("Aborting the search.");
			}

			synchronized (states) {
				
				if(!states.contains(msg.getStateReceived())){
					states.add(msg.getStateReceived());
				}

				if (states.size() >= Configs.BATCH_SIZE) {

					//System.out.println(":::Enviando um batch:::");
					synchronized (masterRemote) {
						masterRemote.receiveStates(states);
						
						//logger.warn("Enviando tarefas para master: " + states.size());

						batchesSent++;

						masterRemote.notify();
					}

					states.clear();

				}
				
				states.notifyAll();
			}

		} catch (RemoteException | SearchException | TranspositionTableException | IterativeDeepeningException e) {
			e.printStackTrace();
		}finally{
			ab = null;
		}
	}

	/*@SuppressWarnings("unused")
	private Long calculateStateSpace(INode state, Long count) {

		Long amount = count;

		if (state.getChildren() != null) {
			for (INode s : state.getChildren()) {
				amount = calculateStateSpace(s, amount);
			}
			amount += 1;
		} else {
			amount++;
		}

		return amount;
	}*/

	public MessageReceived getMessage() {
		return msg;
	}

	public Integer getBatchesSent() {
		return batchesSent;
	}

	public void setMsg(MessageReceived msg) {
		this.msg = msg;
	}

	public int getTotalNodesEvaluated() {
		return totalNodesEvaluated;
	}
	
}
