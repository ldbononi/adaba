package ufu.facom.lia.infrastructure;

import ufu.facom.lia.bean.Move;
import ufu.facom.lia.interfaces.INode;

public class Utilities {

	public Object[] updateVectorLength(Object[] vector, int increaseUnit) {

		Object[] newVector = new Object[vector.length + increaseUnit];

		for (int i = 0; i < vector.length; i++) {
			newVector[i] = vector[i];
		}

		vector = null;

		return newVector;

	}
	
	public Move[] updateVectorLength(Move[] vector, int increaseUnit) {

		Move[] newVector = new Move[vector.length + increaseUnit];

		for (int i = 0; i < vector.length; i++) {
			newVector[i] = vector[i];
		}

		vector = null;
		
		return newVector;
	}
	
	public INode[] updateVectorLength(INode[] vector, int increaseUnit) {

		INode[] newVector = new INode[vector.length + increaseUnit];

		for (int i = 0; i < vector.length; i++) {
			newVector[i] = vector[i];
		}

		vector = null;

		return newVector;

	}

	public Boolean validateVector(Object[] vector) {

		for (int i = 0; i < vector.length; i++) {
			if (vector[i] == null) {
				return true;
			}
		}

		return false;

	}

}
