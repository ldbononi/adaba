package ufu.facom.lia.interfaces;

import java.rmi.Remote;

public interface ILayer extends Remote {
	
	void setWeights(float[][] weights);
	
	float[][] getWeights();
}
