package ufu.facom.lia.configs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ufu.facom.lia.interfaces.INet;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.structures.mlp.Net;

public class FeaturesSearchConfig {

	private SystemConfigs sc = SystemConfigs.getInstance();
	
	private static FeaturesSearchConfig fsc;
	
	private FeaturesSearchConfig() {
	}
	
	public static FeaturesSearchConfig getInstance(){
		
		if(fsc == null){
			fsc = new FeaturesSearchConfig();
		}
		
		return fsc;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public Sucessor getSucessor() {

		try {

			String clazzName = sc.getConfig("sucessorClazz");

			Class clazz = Class.forName(clazzName);

			Constructor c = clazz.getConstructor();

			Object o = c.newInstance();

			Sucessor sucessor = (Sucessor) o;

			return sucessor;

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Evaluation getEvaluation(){
		return getEvaluation(null);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Evaluation getEvaluation(INet net) {

		try {

			String clazzName = sc.getConfig("evaluationClazz");

			Class clazz = Class.forName(clazzName);

			Constructor c = clazz.getConstructor();

			Object o = c.newInstance();

			Evaluation evaluation = (Evaluation) o;

			return evaluation;

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public int getMaxThreadPoolLength(){
		
		return Integer.parseInt(sc.getConfig("max-thread-pool-size"));
		
	}
	
	public static void main(String[] args) {
		
		FeaturesSearchConfig fsc = new FeaturesSearchConfig();
		
		fsc.getSucessor();
		
	}
	
	public INet getNetTest(){
		INet n = new Net();
		
		n.setName("Meu teste");
		
		return n;
	}

}
