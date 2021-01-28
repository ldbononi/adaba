package ufu.facom.lia.search.interfaces;

import ufu.facom.lia.interfaces.INode;

public interface Evaluation {
	
	Float getEvaluation(INode state, Player player);
	
	Evaluation clone() throws CloneNotSupportedException;

}
