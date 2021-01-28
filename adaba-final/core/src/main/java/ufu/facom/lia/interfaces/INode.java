package ufu.facom.lia.interfaces;

import java.util.List;

import ufu.facom.lia.cache.Key;
import ufu.facom.lia.search.interfaces.Player;

public interface INode extends Comparable<INode>{
	
	//int getKey32();
	
	//void setKey32(int key32);
	
	//long getKey64();
	
	//void setKey64(long key64);
	Key getKey();
	
	void setKey(Key key);
	
	int getParentKey32();
	
	void setParentKey32(int parentKey32);
	
	IMove getMove();
	
	void setMove(IMove move);
	
	float getEvaluation();
	
	void setEvaluation(float evaluation);
	
	short getType(); // MIN, MAX ou LEAF
	
	void setType(short type);
	
	short getLocation(); //PV //NORMAL //OLD_PV
	
	void setLocation(short location);
	
	/*List<INode> getChildren();
	
	void setChildren(List<INode> children);*/
	
	int getDepth(); // profundidade na qual a busca foi realizada
	
	void setDepth(int depth);
	
	Player getPlayer();
	
	void setPlayer(Player player);
	
	short getScoreType();
	
	void setScoreType(short scoreType);
	
	Boolean isVisited();
	
	void setVisited(Boolean visited);
	
	void printRepresentation();

}
