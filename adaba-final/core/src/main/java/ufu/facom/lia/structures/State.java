package ufu.facom.lia.structures;

import java.io.Serializable;
import java.util.Map;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.IInfoNode;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.search.interfaces.SlaveStatus;
import ufu.facom.lia.search.interfaces.Status;

public class State implements IState, Serializable {

	private static final long serialVersionUID = 1L;

	private int maxDepth; // profundidade máxima de busca definida em arquivo de
	// configuração

	private int masterDepth; // profundidade d' do mestre

	private int currentDepth; // profundidade que será realizada a busca na
	// próxima iteração

	private int previousDepth; // profundidade alcançada durante a exploração de
								// um nó

	private float alpha;

	private float beta;

	private String stateRepresentation; // representação do estado do tabuleiro
	// para conversão

	private Status status; // verifica se trata-se de um nó completo (CERTAIN)
							// ou
	// não completo (UNCERTAIN)

	private SlaveStatus slaveStatus; // estado do nó enviado para um escravo

	private int brachRate;

	private long stateSpace;

	private short location; // Verifica se o nó está na Principal Variation
	// (PV nodes, or type-1)

	private Map<Integer, IInfoNode> historic;

	private boolean masterVisited;

	private String owner;

	private long time; // Timestamp que indica o momento em que a tarefa foi
						// enviada ao slave

	private boolean edge;

	private INode node;
	
	private IMove move;
	
	private float diff;
	
	private Key key;

	@Override
	public int getMaxDepth() {
		return this.maxDepth;
	}

	@Override
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public int getMasterDepth() {
		return masterDepth;
	}

	@Override
	public void setMasterDepth(int masterDepth) {
		this.masterDepth = masterDepth;
	}

	@Override
	public int getCurrentDepth() {
		return currentDepth;
	}

	@Override
	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

	@Override
	public Integer getPreviousDepth() {
		return previousDepth;
	}

	@Override
	public void setPreviousDepth(Integer previousDepth) {
		this.previousDepth = previousDepth;
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public float getBeta() {
		return beta;
	}

	@Override
	public void setBeta(float beta) {
		this.beta = beta;
	}

	@Override
	public String getStateRepresentation() {
		return stateRepresentation;
	}

	@Override
	public void setStateRepresentation(String stateRepresentation) {
		this.stateRepresentation = stateRepresentation;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public SlaveStatus getSlaveStatus() {
		return slaveStatus;
	}

	@Override
	public void setSlaveStatus(SlaveStatus slaveStatus) {
		this.slaveStatus = slaveStatus;
	}

	@Override
	public Integer getBranchRate() {
		return brachRate;
	}

	@Override
	public void setBranchRate(Integer branchRate) {
		this.brachRate = branchRate;
	}

	@Override
	public Long getStateSpace() {
		return stateSpace;
	}

	@Override
	public void setStateSpace(Long stateSpace) {
		this.stateSpace = stateSpace;
	}

	@Override
	public short getLocation() {
		return location;
	}

	@Override
	public void setLocation(short location) {
		this.location = location;
	}

	@Override
	public Map<Integer, IInfoNode> getHistoric() {
		return historic;
	}

	@Override
	public void setHistoric(Map<Integer, IInfoNode> historic) {
		this.historic = historic;
	}

	@Override
	public Boolean getMasterVisited() {
		return masterVisited;
	}

	@Override
	public void setMasterVisited(Boolean masterVisited) {
		this.masterVisited = masterVisited;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public Long getTime() {
		return time;
	}

	@Override
	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public boolean isEdge() {
		return edge;
	}

	@Override
	public void setEdge(boolean edge) {
		this.edge = edge;
	}

	@Override
	public INode getNode() {
		return node;
	}

	@Override
	public void setNode(INode node) {
		this.node = node;
	}
	
	@Override
	public IMove getMove() {
		return this.move;
	}

	@Override
	public void setMove(IMove move) {
		this.move = move;
	}

	@Override
	public int compareTo(IState o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getDiff() {
		return diff;
	}

	@Override
	public void setDiff(float diff) {
		this.diff = diff;
		
	}

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void setKey(Key key) {
		this.key = key;
	}
}
