package ufu.facom.lia.structures.mlp;

import java.io.Serializable;

import ufu.facom.lia.interfaces.ILayer;
import ufu.facom.lia.interfaces.ILayerSimple;
import ufu.facom.lia.interfaces.INet;

public class Net implements INet, Cloneable, Serializable{

	private static final long serialVersionUID = 1L;

	private String name;

	private int numInputs;

	private int numNodesHidden;

	private boolean directLinks;

	private float lowRange;

	private float highRange;

	private Long randomSeed; // Seed of the random numbers of the weights

	private float[] input; // Array of enter.

	private ILayer layer1; // input layer - hidden weights

	private ILayerSimple layer2; // hidden weights - output weights

	private ILayerSimple layer3; // in layer - output weights

	private ILayer oldLayer1;

	private ILayerSimple oldLayer2;

	private ILayerSimple oldLayer3;

	private ILayer elig1;

	private ILayerSimple elig2;

	private ILayerSimple elig3;

	private float[] hiddenUnits; // hidden units values

	public Net() {
	}

	public Net(String name) {
		this.name = name;
	}

	public Net(String name, int numInputs, int numNodesHidden, boolean directLinks) {
		this.name = name;
		this.numInputs = numInputs;
		this.numNodesHidden = numNodesHidden;
		this.directLinks = directLinks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumInputs() {
		return numInputs;
	}

	public void setNumInputs(int numInputs) {
		this.numInputs = numInputs;
	}

	public int getNumNodesHidden() {
		return numNodesHidden;
	}

	public void setNumNodesHidden(int numNodesHidden) {
		this.numNodesHidden = numNodesHidden;
	}

	public boolean isDirectLinks() {
		return directLinks;
	}

	public void setDirectLinks(boolean directLinks) {
		this.directLinks = directLinks;
	}

	public float getLowRange() {
		return lowRange;
	}

	public void setLowRange(float lowRange) {
		this.lowRange = lowRange;
	}

	public float getHighRange() {
		return highRange;
	}

	public void setHighRange(float highRange) {
		this.highRange = highRange;
	}

	public Long getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(Long randomSeed) {
		this.randomSeed = randomSeed;
	}

	public ILayer getLayer1() {
		return layer1;
	}

	public void setLayer1(ILayer layer1) {
		this.layer1 = layer1;
	}

	public ILayerSimple getLayer2() {
		return layer2;
	}

	public void setLayer2(ILayerSimple layer2) {
		this.layer2 = layer2;
	}

	public ILayerSimple getLayer3() {
		return layer3;
	}

	public void setLayer3(ILayerSimple layer3) {
		this.layer3 = layer3;
	}

	public ILayer getOldLayer1() {
		return oldLayer1;
	}

	public void setOldLayer1(ILayer oldLayer1) {
		this.oldLayer1 = oldLayer1;
	}

	public ILayerSimple getOldLayer2() {
		return oldLayer2;
	}

	public void setOldLayer2(ILayerSimple oldLayer2) {
		this.oldLayer2 = oldLayer2;
	}

	public ILayerSimple getOldLayer3() {
		return oldLayer3;
	}

	public void setOldLayer3(ILayerSimple oldLayer3) {
		this.oldLayer3 = oldLayer3;
	}

	public ILayer getElig1() {
		return elig1;
	}

	public void setElig1(ILayer elig1) {
		this.elig1 = elig1;
	}

	public ILayerSimple getElig2() {
		return elig2;
	}

	public void setElig2(ILayerSimple elig2) {
		this.elig2 = elig2;
	}

	public ILayerSimple getElig3() {
		return elig3;
	}

	public void setElig3(ILayerSimple elig3) {
		this.elig3 = elig3;
	}

	/*public LayerSimple getHiddenUnits() {
		return hiddenUnits;
	}

	public void setHiddenUnits(LayerSimple hiddenUnits) {
		this.hiddenUnits = hiddenUnits;
	}*/

	public float[] getInput() {

		if (input == null && numInputs > 0) {
			input = new float[numInputs];
		}

		return input;
	}

	public void setInput(float[] input) {
		this.input = input;
	}
	
	public float[] getHiddenUnits() {
		return hiddenUnits;
	}

	public void setHiddenUnits(float[] hiddenUnits) {
		this.hiddenUnits = hiddenUnits;
	}

	@Override
	public Net clone() throws CloneNotSupportedException {
		
		Net net = (Net) super.clone();
		
		if(net.getInput() != null){
			float[] inputs = new float[net.getInput().length];
			
			for(int i = 0; i < net.getInput().length; i++){
				inputs[i] = net.getInput()[i];
			}
			
			net.setInput(inputs);
		}
		
		if(net.getHiddenUnits() != null){
			float[] units = new float[net.getHiddenUnits().length];
			
			for(int i = 0; i < net.getHiddenUnits().length; i++){
				units[i] = net.getHiddenUnits()[i];
			}
			
			net.setHiddenUnits(units);
		}
		
		if(net.getLayer1() != null){
			net.setLayer1( (ILayer) ((Layer) net.getLayer1()).clone());
		}
		
		if(net.getLayer2() != null){
			net.setLayer2((ILayerSimple) ((LayerSimple) net.getLayer2()).clone());
		}
		
		if(net.getLayer3() != null){
			net.setLayer3((ILayerSimple) ((LayerSimple) net.getLayer3()).clone());
		}
		if(net.getOldLayer1() != null){
			net.setOldLayer1((ILayer) ((Layer) net.getOldLayer1()).clone());
		}
		
		if(net.getOldLayer2() != null){
			net.setOldLayer2((ILayerSimple) ((LayerSimple) net.getOldLayer2()).clone());
		}
		
		if(net.getOldLayer3() != null){
			net.setOldLayer3( (ILayerSimple) ((LayerSimple) net.getOldLayer3()).clone());
		}
		
		if(net.getElig1() != null){
			net.setElig1( (ILayer) ((Layer) net.getElig1()).clone());
		}
		
		if(net.getElig2() != null){
			net.setElig2( (ILayerSimple) ((LayerSimple) net.getElig2()).clone());
		}
		
		if(net.getElig3() != null){
			net.setElig3( (ILayerSimple) ((LayerSimple) net.getElig3()).clone());
		}
		
		return net;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		Net n1 = new Net();
		
		n1.setDirectLinks(true);
		n1.setHighRange(0.44f);
		n1.setName("Maria");
		n1.setLowRange(0.2f);
		n1.setNumInputs(33);
		n1.setRandomSeed(4l);
		n1.setNumNodesHidden(20);
		
		Net n2 = n1.clone();
		
		//n2.setHighRange(n2.getHighRange().add(BigDecimal.ONE));
		n2.setHighRange(n2.getHighRange() + 1);
		n2.setName("Lazara");
		
		System.out.println("Aki...");
		
	}

	
	
	

}
