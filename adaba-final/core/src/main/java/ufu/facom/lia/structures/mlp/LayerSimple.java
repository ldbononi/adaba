package ufu.facom.lia.structures.mlp;

import java.io.Serializable;

import ufu.facom.lia.interfaces.ILayerSimple;

public class LayerSimple implements ILayerSimple, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private float[] weights;

	public LayerSimple(int i) {
		weights = new float[i];
	}

	public float[] getWeights() {
		return weights;
	}

	public void setWeights(float[] weights) {
		this.weights = weights;
	}
	
	@Override
	protected LayerSimple clone() throws CloneNotSupportedException {
		
		LayerSimple layer = (LayerSimple) super.clone();
		
		if(layer.getWeights() != null){
			float[] weights = new float[layer.getWeights().length];
			
			for(int i = 0; i < layer.getWeights().length; i++){
				weights[i] = layer.getWeights()[i];
			}
			
			layer.setWeights(weights);
		}
		
		return layer;
	}
}
