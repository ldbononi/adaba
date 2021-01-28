package ufu.facom.lia.evaluation.policy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.exceptions.MlpException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.mlp.DirectNet;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Player;

public class BoardEvaluation implements Evaluation, Serializable, Cloneable {
	
	private static final Logger logger = LogManager.getLogger(BoardEvaluation.class);

	private static final long serialVersionUID = 1L;
	
	private DirectNet dirNet;
	
	//private EvalNetImpl evalNetImpl;
	
	//private Net net;
	
	public BoardEvaluation(){
		
	}

	public BoardEvaluation(DirectNet dirNet) {
		
		this.dirNet = dirNet;
		//this.evalNetImpl = this.dirNet.getEvalNetImpl();
	}

	@Override
	public Float getEvaluation(INode state, Player player) {
		
		Node n = (Node) state;
		
		try {
			
			if(n.getBoard() == null){
				throw new MlpException("The board representation is null.");
			}
			
			float[] entries = dirNet.mapFeatures(n.getBoard(), dirNet.getNet());
			
			return dirNet.getEvalNetImpl().evaluateNet(entries, dirNet.getNet());
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InvalidBoardException | MlpException e) {
		
			logger.error(e.getMessage());
			
			String nodeInformation = "key32: ";
			
			nodeInformation = String.valueOf(n.getKey().getKey32());
			
			nodeInformation += "key64: " + String.valueOf(n.getKey().getKey64()); 
			
			if(n.getBoard() != null && n.getBoard().getBoardRepresentation() != null){
				nodeInformation += " node representation: " + n.getBoard().getBoardRepresentation();
			}
			
			nodeInformation += " type: " + n.getType();
			
			logger.error("Info::: " + nodeInformation);
			
			e.printStackTrace();
		}
		
		return null;
	}

	/*public Net getNet() {
		return net;
	}

	public void setNet(Net net) {
		this.net = net;
	}*/

	public DirectNet getDirNet() {
		return dirNet;
	}

	public void setDirNet(DirectNet dirNet) {
		this.dirNet = dirNet;
	}
	
	@Override
	public Evaluation clone() throws CloneNotSupportedException {
		return (Evaluation) super.clone();
	}
	
}
