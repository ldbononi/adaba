package ufu.facom.lia.interfaces;

import java.rmi.Remote;

public interface ILayerSimple extends Remote {
	
	float[] getWeights();
	
	void setWeights(float[] weights);

}
