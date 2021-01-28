package ufu.facom.lia.mlp;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.map.EFeatures;
import ufu.facom.lia.board.map.Features;
import ufu.facom.lia.exceptions.InvalidBoardException;
import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.structures.mlp.Net;

public class DirectNet implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<EFeatures> features;

	private Net net;
	
	private EvalNet evalNet;
	
	private EvalNetImpl evalNetImpl;
	
	private BoardUtils boardUtils;
	
	public DirectNet(EvalNet evalNet){
		
		this.evalNet = evalNet;
	}
	
	/**
	 * This function maps in an array the values of the features functions. Note
	 * that in a position of this array is put the value returned but a function
	 * of the Features.class, however, considering the total of bits that exists
	 * of each feature, the next positions is completed with the calculus of the
	 * function 'calculateNextBit'.
	 * 
	 * @param board
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InvalidBoardException
	 */
	public BigDecimal[] mapFeaturesOriginal(Board board, Net net)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidBoardException {

		Class<Features> clazz = Features.class;

		Features ftr = new Features(boardUtils);

		Method[] methods = clazz.getMethods();

		BigDecimal[] iv = new BigDecimal[net.getNumInputs() + 1];

		int idx = 0;

		for (EFeatures feature : features) {

			for (Method m : methods) {

				if (m.getName().toLowerCase().equals(feature.getName().toLowerCase())) {
					iv[idx++] = new BigDecimal(((Integer) m.invoke(ftr, new Object[] { board })).toString());

					for (int i = 1; i < feature.getNumBits(); i++) {
						iv[idx++] = new BigDecimal(((Integer) ftr.calculateNextBit(board.getBoard())).toString());
					}

					break;
				}
			}
		}
		
		for(int i = 0; i < iv.length; i++){
			System.out.print(iv[i] + " ");
		}
		System.out.println("");
		
		return iv;
	}
	
	public float[] mapFeatures(Board board, INet numInputs)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidBoardException {

		Features ftr = new Features(boardUtils);

		float[] iv = new float[numInputs.getNumInputs() + 1];

		int idx = 0;

		for (EFeatures feature : features) {
			
			switch(feature.getKey()){
			
			case 1:
				iv[idx++] = new Float(ftr.pieceAdvantage(board.getBoard()));
				break;
			case 2:
				iv[idx++] = new Float(ftr.pieceDisadvantage(board.getBoard()));
				break;
			case 3:
				iv[idx++] = new Float(ftr.pieceThreat(board.getBoard()));
				break;
			case 4:
				iv[idx++] = new Float(ftr.pieceTake(board.getBoard()));
				break;
			case 5:
				iv[idx++] = new Float(ftr.advancement(board.getBoard()));
				break;
			case 6:
				iv[idx++] = new Float(ftr.doubleDiagonal(board.getBoard()));
				break;
			case 7:
				iv[idx++] = new Float(ftr.backRowBridge(board.getBoard()));
				break;
			case 8:
				iv[idx++] = new Float(ftr.centreControl(board.getBoard()));
				break;
			case 9:
				iv[idx++] = new Float(ftr.xCentreControl(board.getBoard()));
				break;
			case 10:
				iv[idx++] = new Float(ftr.totalMobility(board.getBoard()));
				break;
			case 11:
				iv[idx++] = new Float(ftr.exposure(board.getBoard()));
				break;
			case 12:
				iv[idx++] = new Float(ftr.kingCentreControl(board.getBoard()));
				break;
			case 13:
				iv[idx++] = new Float(ftr.diagonalMoment(board.getBoard()));
				break;
			case 14:
				iv[idx++] = new Float(ftr.apex(board.getBoard()));
				break;
			case 15:
				iv[idx++] = new Float(ftr.cramp(board.getBoard()));
				break;
			case 16:
				iv[idx++] = new Float(ftr.denialOfOccupancy(board.getBoard()));
				break;
			case 17:
				iv[idx++] = new Float(ftr.dyke(board.getBoard()));
				break;
			case 18:
				iv[idx++] = new Float(ftr.exchange(board.getBoard()));
				break;
			case 19:
				iv[idx++] = new Float(ftr.fork(board.getBoard()));
				break;
			case 20:
				iv[idx++] = new Float(ftr.gap(board.getBoard()));
				break;
			case 21:
				iv[idx++] = new Float(ftr.backRowControl(board.getBoard()));
				break;
			case 22:
				iv[idx++] = new Float(ftr.hole(board.getBoard()));
				break;
			case 23:
				iv[idx++] = new Float(ftr.undeniedMobility(board.getBoard()));
				break;
			case 24:
				iv[idx++] = new Float(ftr.node(board.getBoard()));
				break;
			case 25:
				iv[idx++] = new Float(ftr.triangleOfOreo(board.getBoard()));
				break;
			case 26:
				iv[idx++] = new Float(ftr.pole(board.getBoard()));
				break;
			case 27:
				iv[idx++] = new Float(ftr.threat(board.getBoard()));
				break;
			case 28:
				iv[idx++] = new Float(ftr.taken(board.getBoard()));
				break;
			}
			
			/*case 1:
				iv[idx++] = 0f;
				break;
			case 2:
				iv[idx++] = 0f;
				break;
			case 3:
				iv[idx++] = 0f;
				break;
			case 4:
				iv[idx++] = 0f;
				break;
			case 5:
				iv[idx++] = 0f;
				break;
			case 6:
				iv[idx++] = 0f;
				break;
			case 7:
				iv[idx++] = 0f;
				break;
			case 8:
				iv[idx++] = 0f;
				break;
			case 9:
				iv[idx++] = 0f;
				break;
			case 10:
				iv[idx++] = 0f;
				break;
			case 11:
				iv[idx++] = 0f;
				break;
			case 12:
				iv[idx++] = 0f;
				break;
			case 13:
				iv[idx++] = 0f;
				break;
			case 14:
				iv[idx++] = 0f;
				break;
			case 15:
				iv[idx++] = 0f;
				break;
			case 16:
				iv[idx++] = 0f;
				break;
			case 17:
				iv[idx++] = 0f;
				break;
			case 18:
				iv[idx++] = 0f;
				break;
			case 19:
				iv[idx++] = 0f;
				break;
			case 20:
				iv[idx++] = 0f;
				break;
			case 21:
				iv[idx++] = 0f;
				break;
			case 22:
				iv[idx++] = 0f;
				break;
			case 23:
				iv[idx++] = 0f;
				break;
			case 24:
				iv[idx++] = 0f;
				break;
			case 25:
				iv[idx++] = 0f;
				break;
			case 26:
				iv[idx++] = 0f;
				break;
			case 27:
				iv[idx++] = 0f;
				break;
			case 28:
				iv[idx++] = 0f;
				break;
			}
*/
			for (int i = 1; i < feature.getNumBits(); i++) {
				iv[idx++] = new Float(((Integer) ftr.calculateNextBit(board.getBoard())).toString());
			}
		}
		
		/*for(int i = 0; i < iv.length; i++){
			System.out.print(iv[i] + " ");
		}
		System.out.println("");
		*/
		return iv;
	}

	public List<EFeatures> getFeatures() {
		return features;
	}

	public void setFeatures(List<EFeatures> features) {
		this.features = features;
	}

	public Net getNet() {
		return net;
	}

	public void setNet(Net net) {
		if( this.evalNet != null ){
			this.evalNet.setNumInputs(net.getNumInputs());
			this.evalNet.setNumHidden(net.getNumNodesHidden());
		}
		this.net = net;
	}

	public EvalNet getEvalNet() {
		return evalNet;
	}

	public void setEvalNet(EvalNet evalNet) {
		this.evalNet = evalNet;
	}

	public EvalNetImpl getEvalNetImpl() {
		return evalNetImpl;
	}

	public void setEvalNetImpl(EvalNetImpl evalNetImpl) {
		this.evalNetImpl = evalNetImpl;
	}

	public BoardUtils getBoardUtils() {
		return boardUtils;
	}

	public void setBoardUtils(BoardUtils boardUtils) {
		this.boardUtils = boardUtils;
	}
	
	

}
