package ufu.facom.lia.search;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.been.MessageReceived;
import ufu.facom.lia.configs.Configs;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.interfaces.MessageStatus;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.remote.IMasterRemote;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;

public class SlaveAlphaBetaThread implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(SlaveAlphaBetaThread.class);

	private Sucessor sucessor;

	private Evaluation evaluation;

	private MessageReceived msg;

	private IMasterRemote masterRemote;

	private float rootValue;

	private List<IState> states;

	private ICache cache;
	
	private int masterDepth;

	private static Integer batchesSent = 1;

	private int numNodesEvaluated;
	
	public SlaveAlphaBetaThread(MessageReceived msg, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, float rootValue, List<IState> states, ICache cache) {

		this(msg, masterDepth, evaluation, sucessor, masterRemote, rootValue, states);

		this.cache = cache;

	}

	public SlaveAlphaBetaThread(MessageReceived msg, int masterDepth, Evaluation evaluation, Sucessor sucessor,
			IMasterRemote masterRemote, float rootValue, List<IState> states) {

		this.msg = msg;
		this.masterDepth = masterDepth;
		this.evaluation = evaluation;
		this.sucessor = sucessor;
		this.masterRemote = masterRemote;
		this.rootValue = rootValue;
		this.states = states;
	}

	@Override
	public void run() {

		AlphaBetaFailSoft ab = new AlphaBetaFailSoft(evaluation, sucessor);

		if (cache != null) {
			ab.setCache(cache);
			cache.setMasterDepth(masterDepth);
		}

		// Minimax ab = new Minimax(evaluation, sucessor);

		try {
			
			float min = Float.valueOf(SystemConfigs.getInstance().getConfig("lower_bound"));
			float max = Float.valueOf(SystemConfigs.getInstance().getConfig("upper_bound"));

			min = min - rootValue;
			max = max + rootValue;
			
			//BigDecimal min = rootValue.subtract(Configs.LOWER_BOUND);
			//BigDecimal max = rootValue.add(Configs.UPPER_BOUND);
			
			//System.out.println("min: " + min.toPlainString() + " max: " + max.toPlainString());

			msg.setMessageStatus(MessageStatus.INPROCESS);

			int profundidade = (msg.getStateReceived().getCurrentDepth() - msg.getStateReceived().getMasterDepth());
			
			//BigDecimal bd = ab.alphaBetaTT(msg.getState(), profundidade, min, max, 
				//	 ab.otherPlayer(msg.getStateReceived().getPlayer()), cache);

			//BigDecimal bd = BigDecimal.ZERO;
			
			Move move = new Move();
			
			float bd = ab.alphaBeta(msg.getStateReceived().getNode(), move, profundidade, min, max,
					ab.otherPlayer(msg.getStateReceived().getNode().getPlayer()));

			logger.debug("State: k32" + msg.getStateReceived().getNode().getKey().getKey32() + " k64: " + msg.getStateReceived().getNode().getKey().getKey64() +
					" Profundidade: " + msg.getStateReceived().getCurrentDepth() + " Resultado: " + bd);

			//Long stateSpace = calculateStateSpace(msg.getStateReceived().getNode(), 0l);

			//msg.getStateReceived().setStateSpace(stateSpace);

			//msg.getStateReceived().setStateSpace(stateSpace);
			msg.getStateReceived().setAlpha(ab.getGlobalMin());
			msg.getStateReceived().setBeta(ab.getGlobalMin());
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
			
			msg.getStateReceived().setCurrentDepth(10);

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

			//System.out.println("Message Status: " + msg.getMessageStatus());

			//System.out.println("Batches enviados: " + batchesSent);

			synchronized (states) {
				states.add(msg.getStateReceived());
				states.notify();

				if (states.size() >= Configs.BATCH_SIZE) {

					//System.out.println(":::Enviando um batch:::");
					synchronized (masterRemote) {
						masterRemote.receiveStates(states);

						batchesSent++;

						masterRemote.notify();
					}

					states.clear();

				}
			}

		} catch (RemoteException | SearchException e) {
			e.printStackTrace();
		}finally{
			ab = null;
		}
	}

	/*private Long calculateStateSpace(INode state, Long count) {

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
	
	
}
