package ufu.facom.lia.interfaces;

import java.rmi.Remote;

public interface INet extends Remote{
	
	String getName();
	
	void setName(String name);
	
	int getNumInputs();
	
	void setNumInputs(int numInputs);
	
	int getNumNodesHidden();
	
	void setNumNodesHidden(int numNodesHidden);
	
	boolean isDirectLinks();
	
	void setDirectLinks(boolean directLinks);
	
	float getLowRange();
	
	void setLowRange(float lowRange);
	
	float getHighRange();
	
	void setHighRange(float highRange);
	
	Long getRandomSeed();
	
	void setRandomSeed(Long randomSeed);
	
	/*ILayer getLayer1();
	
	void setLayer1(ILayer layer1);
	
	ILayerSimple getLayer2();
	
	void setLayer2(ILayerSimple layer2);
	
	ILayerSimple getLayer3();
	
	void setLayer3(ILayerSimple layer3);
	
	ILayer getOldLayer1();
	
	void setOldLayer1(ILayer oldLayer1);
	
	ILayerSimple getOldLayer2();
	
	void setOldLayer2(ILayerSimple oldLayer2);
	
	ILayerSimple getOldLayer3();
	
	void setOldLayer3(ILayerSimple oldLayer3);
	
	ILayer getElig1();
	
	void setElig1(ILayer elig1);
	
	ILayerSimple getElig2();
	
	void setElig2(ILayerSimple elig2);
	
	ILayerSimple getElig3();
	
	void setElig3(ILayerSimple elig3);*/

	float[] getInput();
	
	void setInput(float[] input);
	
	float[] getHiddenUnits();
	
	void setHiddenUnits(float[] hiddenUnits);

}
