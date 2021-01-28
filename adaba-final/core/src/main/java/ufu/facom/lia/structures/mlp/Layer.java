package ufu.facom.lia.structures.mlp;

import java.io.Serializable;

import ufu.facom.lia.interfaces.ILayer;

public class Layer implements ILayer, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private float[][] weights;

	public Layer(int i, int j) {
		weights = new float[i][j];
	}
	
	public void setWeights(float[][] weights){
		this.weights = weights;
	}

	public float[][] getWeights() {
		return weights;
	}
	
	@Override
	protected Layer clone() throws CloneNotSupportedException {
		
		Layer layer = (Layer) super.clone();
		
		if(layer.getWeights() != null){
			float[][] weights = new float[layer.getWeights().length][];
			
			for(int i = 0; i < layer.getWeights().length; i++){
				weights[i] = new float[layer.getWeights()[i].length];
				
				for(int j = 0; j < layer.getWeights()[i].length; j++){
					weights[i][j] = layer.getWeights()[i][j];
				}
				
			}
			
			layer.setWeights(weights);
		}
		
		return layer;
	}

}
