package ufu.facom.lia.search.interfaces;

import java.util.List;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.INode;

public interface Sucessor {

	List<INode> getSucessors(INode currentState, Player player) throws SearchException;
	
	String getName(INode n, INode parent);
	
	Sucessor clone() throws CloneNotSupportedException;

}
