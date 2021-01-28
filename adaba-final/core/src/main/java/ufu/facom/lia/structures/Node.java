package ufu.facom.lia.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	private Player player;

	// private String name;

	private float evaluation;

	private int depth;

	// private INode parent;

	private short type;
	
	private short location;
	
	private IMove move;

	//private List<INode> children;

	private Boolean visited;

	private short scoreType;

	public Node() {
	}

	public Node(int key32, long key64) {
		this.key = new Key(key64, key32);
	}

	public Node(float evaluation, Integer depth) {
		this.evaluation = evaluation;
		this.depth = depth;
	}

	/*
	 * public Node(float evaluation, Integer depth, Node parent) {
	 * this.evaluation = evaluation; this.depth = depth; //this.parent = parent;
	 * }
	 */

	/*
	 * @Override public INode getParent() { return parent; }
	 * 
	 * @Override public void setParent(INode parent) { this.parent = parent; }
	 */
	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
	public short getLocation() {
		return location;
	}

	public void setLocation(short location) {
		this.location = location;
	}

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	@Override
	public float getEvaluation() {
		return this.evaluation;
	}

	@Override
	public void setEvaluation(float evaluation) {
		this.evaluation = evaluation;
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
	public int getDepth() {
		return this.depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
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

	@Override
	public int compareTo(INode o) {
		if (this.evaluation < o.getEvaluation()) {
			return 1;
		}
		if (this.evaluation > o.getEvaluation()) {
			return -1;
		}
		return 0;
	}

	@Override
	public INode clone() throws CloneNotSupportedException {

		Node n = (Node) super.clone();

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
	public IMove getMove() {
		return this.move;
	}

	@Override
	public void setMove(IMove move) {
		this.move = move;
	}

	@Override
	public void printRepresentation() {
		
	}
	
	@Override
	public Key getKey() {
		return this.key;
	}

	@Override
	public void setKey(Key key) {
		this.key = key;
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
