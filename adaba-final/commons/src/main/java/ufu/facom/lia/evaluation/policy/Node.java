package ufu.facom.lia.evaluation.policy;

import java.io.Serializable;
import java.util.List;

import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.BoardUtils;
import ufu.facom.lia.board.XBoard;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.interfaces.IMove;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Player;

public class Node implements INode, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	//private int key32;

	//private long key64;
	
	private Key key;

	private int parentKey32;

	private float evaluation;

	private short type;
	
	private short location;

	//private transient List<INode> children;

	private int depth;

	// private String name;

	// private INode parent;

	private Boolean visited;

	private Board board;
	
	private XBoard xBoard;

	private IMove move;

	private Player player;

	private short scoreType;

	@Override
	public float getEvaluation() {
		return evaluation;
	}

	@Override
	public void setEvaluation(float evaluation) {
		this.evaluation = evaluation;

	}

	@Override
	public short getType() {
		return type;
	}

	@Override
	public void setType(short type) {
		this.type = type;

	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/*
	 * @Override public String getName() { return name; }
	 * 
	 * @Override public void setName(String name) { this.name = name;
	 * 
	 * }
	 */

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public XBoard getxBoard() {
		return xBoard;
	}

	public void setxBoard(XBoard xBoard) {
		this.xBoard = xBoard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IMove getMove() {
		return move;
	}

	public void setMove(IMove move) {
		this.move = move;
	}

	public Boolean getVisited() {
		return visited;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;

	}

	@Override
	public short getScoreType() {
		return scoreType;
	}

	@Override
	public void setScoreType(short scoreType) {
		this.scoreType = scoreType;
	}

	/*@Override
	public List<INode> getChildren() {
		return this.children;
	}

	@Override
	public void setChildren(List<INode> children) {
		this.children = children;
	}*/

	@Override
	public Boolean isVisited() {
		return visited;
	}

	@Override
	public void setVisited(Boolean visited) {
		this.visited = visited;

	}


	@Override
	public int getParentKey32() {
		return parentKey32;
	}

	@Override
	public void setParentKey32(int parentKey32) {
		this.parentKey32 = parentKey32;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	/*
	 * @Override public INode getParent() { return parent; }
	 * 
	 * @Override public void setParent(INode state) { this.parent = state; }
	 */
	@Override
	public Node clone() throws CloneNotSupportedException {

		Node n = ( Node) super.clone();

		if (n.getBoard() != null) {
			Board b = n.getBoard().clone();
			n.setBoard(b);
		}

		if (n.getMove() != null) {
			//TODO: ajustar o clone de Move!!!
			IMove m = n.getMove().clone();
			n.setMove(m);
		}

		/*if (n.getChildren() != null) {
			List<INode> childrenTemp = new ArrayList<INode>();

			for (INode c : n.getChildren()) {
				childrenTemp.add(((Node) c).clone());
			}

			n.setChildren(childrenTemp);
		}*/

		return n;
	}

	@Override
	public synchronized int compareTo(INode o) {
		if (this.evaluation < o.getEvaluation()) {
			return 1;//estava o contrário aqui 1
		}
		if (this.evaluation > o.getEvaluation()) {
			return -1; //aqui 1
		}
		return 0;
	}

	@Override
	public short getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(short location) {
		this.location = location;
	}

	@Override
	public void printRepresentation() {
	
		BoardUtils bu = new BoardUtils();
		
		bu.printBoard(board);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (player != other.player)
			return false;
		return true;
	}
	
	
}
