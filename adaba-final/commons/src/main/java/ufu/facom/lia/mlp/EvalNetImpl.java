package ufu.facom.lia.mlp;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;

import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.MlpException;
import ufu.facom.lia.file.FileManipulate;
import ufu.facom.lia.interfaces.ILayer;
import ufu.facom.lia.interfaces.ILayerSimple;
import ufu.facom.lia.structures.mlp.Layer;
import ufu.facom.lia.structures.mlp.LayerSimple;
import ufu.facom.lia.structures.mlp.Net;

public class EvalNetImpl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// private Net net;
	private EvalNet evalNet;
	private Utils utils;

	private Random rand;

	public EvalNetImpl(EvalNet evalNet) {

		this.evalNet = evalNet;

		readNetParameters();

		utils = new Utils();
	}

	public void updateWeights(Net net) {

		float temp = 0;

		if (net.isDirectLinks()) {

			//System.out.println("#################################DIRECT LINKS######################################");

			for (int i = 0; i <= net.getNumInputs(); i++) {

				/*temp = evalNet.getlRate1().multiply(evalNet.getOutputError()).multiply(net.getElig3().getWeights()[i])
						.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);
*/
				temp = evalNet.getlRate1() * evalNet.getOutputError() * net.getElig3().getWeights()[i];
				
				/*System.out.println("temp: " + evalNet.getlRate1().toPlainString() + " * "
						+ evalNet.getOutputError().toPlainString() + " * "
						+ net.getElig3().getWeights()[i].toPlainString() + " = " + temp.toPlainString());*/

				//if ((net.getOldLayer3().getWeights()[i].compareTo(BigDecimal.ZERO) != 0
					//	&& temp.compareTo(BigDecimal.ZERO) != 0)) {
				
				if(net.getOldLayer3().getWeights()[i] != 0 && temp != 0){

					/*System.out.print(temp.toPlainString() + " + (" + evalNet.getMomentum().toPlainString() + " * "
							+ net.getOldLayer3().getWeights()[i].toPlainString() + ") = ");*/

				/*net.getOldLayer3().getWeights()[i] = temp
							.add(evalNet.getMomentum().multiply(net.getOldLayer3().getWeights()[i]))
							.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
					
					net.getOldLayer3().getWeights()[i] = temp + (evalNet.getMomentum() * net.getOldLayer3().getWeights()[i]);

					//System.out.print(net.getOldLayer3().getWeights()[i].toPlainString() + "\n");

				} else {

					net.getOldLayer3().getWeights()[i] = temp;
				}

				/*System.out.print(net.getLayer3().getWeights()[i].toPlainString() + " + "
						+ net.getOldLayer3().getWeights()[i].toPlainString() + " = ");*/

				/*net.getLayer3().getWeights()[i] = net.getLayer3().getWeights()[i]
						.add(net.getOldLayer3().getWeights()[i])
						.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				net.getLayer3().getWeights()[i] = net.getLayer3().getWeights()[i] + net.getOldLayer3().getWeights()[i];

				//System.out.print(net.getLayer3().getWeights()[i].toPlainString() + "\n");
			}
		}

		/*System.out
				.println("#################################### HIDDEN WEIGHTS #######################################");*/
		
		// update hid-out weights
		for (int i = 0; i <= net.getNumNodesHidden(); i++) {

			/*temp = evalNet.getlRate2().multiply(evalNet.getOutputError()).multiply(net.getElig2().getWeights()[i])
					.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
			
			temp = evalNet.getlRate2() * evalNet.getOutputError() * net.getElig2().getWeights()[i];

			/*System.out.println(
					"temp: " + evalNet.getlRate2().toPlainString() + " * " + evalNet.getOutputError().toPlainString()
							+ " * " + net.getElig2().getWeights()[i].toPlainString() + " = " + temp);*/

			/*if (net.getOldLayer2().getWeights()[i].compareTo(BigDecimal.ZERO) != 0
					&& temp.compareTo(BigDecimal.ZERO) != 0) {*/
			
			if(net.getOldLayer2().getWeights()[i] != 0 && temp != 0){

				/*System.out.print(evalNet.getMomentum().toPlainString() + " * "
						+ net.getOldLayer2().getWeights()[i].toPlainString() + " = ");*/

				/*net.getOldLayer2().getWeights()[i] = temp
						.add(evalNet.getMomentum().multiply(net.getOldLayer2().getWeights()[i]))
						.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				net.getOldLayer2().getWeights()[i] = temp + (evalNet.getMomentum() * net.getOldLayer2().getWeights()[i]);

				//System.out.print(net.getOldLayer2().getWeights()[i].toPlainString() + "\n");

			} else {

				net.getOldLayer2().getWeights()[i] = temp;
			}

			/*System.out.print(net.getLayer2().getWeights()[i].toPlainString() + " + "
					+ net.getOldLayer2().getWeights()[i].toPlainString());*/

			/*net.getLayer2().getWeights()[i] = net.getLayer2().getWeights()[i].add(net.getOldLayer2().getWeights()[i])
					.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
			
			net.getLayer2().getWeights()[i] = net.getLayer2().getWeights()[i] + net.getOldLayer2().getWeights()[i];

			//System.out.print(net.getLayer2().getWeights()[i].toPlainString() + "\n");
		}

		/*System.out.println("##############################ENTRADA###################################");*/
		
		// update in-hid weights
		for (int i = 0; i < net.getNumNodesHidden(); i++) {

			for (int j = 0; j <= net.getNumInputs(); j++) {

				/*temp = evalNet.getlRate1().multiply(evalNet.getOutputError())
						.multiply(net.getElig1().getWeights()[j][i])
						.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				temp = evalNet.getlRate1() * evalNet.getOutputError() * net.getElig1().getWeights()[j][i];

				/*System.out.println("temp: " + evalNet.getlRate1().toPlainString() + " * "
						+ evalNet.getOutputError().toPlainString() + " * "
						+ net.getElig1().getWeights()[j][i].toPlainString() + " = " + temp.toPlainString());*/

				/*if (net.getOldLayer1().getWeights()[j][i].compareTo(BigDecimal.ZERO) != 0
						&& temp.compareTo(BigDecimal.ZERO) != 0) {*/
				
				if(net.getOldLayer1().getWeights()[j][i] != 0 && temp != 0){

					/*System.out.print(temp.toPlainString() + " + " + evalNet.getMomentum().toPlainString() + " * "
							+ net.getOldLayer1().getWeights()[j][i].toPlainString() + " = ");*/

					/*net.getOldLayer1().getWeights()[j][i] = temp
							.add(evalNet.getMomentum().multiply(net.getOldLayer1().getWeights()[j][i]))
							.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
					
					net.getOldLayer1().getWeights()[j][i] =  temp + (evalNet.getMomentum() * net.getOldLayer1().getWeights()[j][i]);

					//System.out.print(net.getOldLayer1().getWeights()[j][i].toPlainString() + "\n");

				} else {
					net.getOldLayer1().getWeights()[j][i] = temp;
				}

				/*System.out.print(net.getLayer1().getWeights()[j][i].toPlainString() + " + "
						+ net.getOldLayer1().getWeights()[j][i].toPlainString() + " = ");*/

				/*net.getLayer1().getWeights()[j][i] = net.getLayer1().getWeights()[j][i]
						.add(net.getOldLayer1().getWeights()[j][i])
						.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);
*/
				
				net.getLayer1().getWeights()[j][i] = net.getLayer1().getWeights()[j][i] + net.getOldLayer1().getWeights()[j][i];
				
				//System.out.print(net.getLayer1().getWeights()[j][i].toPlainString() + "\n");

			}
		}
	}

	// updates the eligibility trace of the network - basically a running sum of
	// the derivatives
	public void updateElig(float[] iv, Net net) {

		float deriv = utils.tanhDeviv(evalNet.getOutputUnit());

		//System.out.println("Devirada de: " + evalNet.getOutputUnit().toPlainString() + " = " + deriv.toPlainString());

		if (net.isDirectLinks()) {
			//System.out.println("######################DIRECT LINKS#######################");
			for (int i = 0; i <= net.getNumInputs(); i++) {

				/*System.out.print(
						evalNet.getLambda().toPlainString() + " * " + net.getElig3().getWeights()[i].toPlainString()
								+ " + (" + deriv.toPlainString() + " * " + iv[i].toPlainString() + ") = ");*/

				/*net.getElig3().getWeights()[i] = evalNet.getLambda().multiply(net.getElig3().getWeights()[i])
						.add(deriv.multiply(iv[i])).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				net.getElig3().getWeights()[i] = evalNet.getLambda() * net.getElig3().getWeights()[i] + (deriv * iv[i]);

				//System.out.print(net.getElig3().getWeights()[i].toPlainString() + "\n");
			}
		}

		//System.out.println("#######################HIDDEN########################");
		for (int i = 0; i <= net.getNumNodesHidden(); i++) {

			/*System.out
					.print(evalNet.getLambda().toPlainString() + " * " + net.getElig2().getWeights()[i].toPlainString()
							+ "(" + deriv.toPlainString() + " * " + net.getHiddenUnits()[i].toPlainString() + ") = ");*/

			/*net.getElig2().getWeights()[i] = evalNet.getLambda().multiply(net.getElig2().getWeights()[i])
					.add(deriv.multiply(net.getHiddenUnits()[i])).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
			
			net.getElig2().getWeights()[i] = evalNet.getLambda() * net.getElig2().getWeights()[i] + (deriv * net.getHiddenUnits()[i]);

			//System.out.println(net.getElig2().getWeights()[i].toPlainString() + "\n");
		}

		//System.out.println("########################INPUTS#######################");
		for (int i = 0; i < net.getNumNodesHidden(); i++) {
			for (int j = 0; j <= net.getNumInputs(); j++) {

				/*System.out.print(evalNet.getLambda().toPlainString() + " * "
						+ net.getElig1().getWeights()[j][i].toPlainString() + " + (" + deriv.toPlainString() + " * "
						+ net.getLayer1().getWeights()[j][i].toPlainString() + " * "
						+ (utils.tanhDeviv(net.getHiddenUnits()[i])).toPlainString() + " * " + iv[j].toPlainString()
						+ ") = ");*/

				/*net.getElig1().getWeights()[j][i] = evalNet.getLambda().multiply(net.getElig1().getWeights()[j][i])
						.add(deriv.multiply(net.getLayer1().getWeights()[j][i]
								.multiply(utils.tanhDeviv(net.getHiddenUnits()[i])).multiply(iv[j]))).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				net.getElig1().getWeights()[j][i] = evalNet.getLambda() * net.getElig1().getWeights()[j][i] + (deriv * net.getLayer1().getWeights()[j][i] * utils.tanhDeviv(net.getHiddenUnits()[i]) * iv[j]);

				//System.out.print(net.getElig1().getWeights()[j][i].toPlainString() + "\n");
			}
		}
	}

	public synchronized float evaluateNet(float[] inputVector, Net net) throws MlpException {

		inputVector[net.getNumInputs()] = evalNet.getBias();
		net.getHiddenUnits()[net.getNumNodesHidden()] = evalNet.getBias();

		// calculate hidden values
		for (int i = 0; i < net.getNumNodesHidden(); i++) {

			net.getHiddenUnits()[i] = 0;

			for (int j = 0; j <= net.getNumInputs(); j++) {

				 /*System.out.print(net.getHiddenUnits()[i] + " + (");
				
				System.out.print(net.getLayer1().getWeights()[j][i] + " * " +
				 inputVector[j] + ") = "
				 + net.getHiddenUnits()[i] + "\n");*/

				/*net.getHiddenUnits()[i] = net.getHiddenUnits()[i]
						.add(net.getLayer1().getWeights()[j][i].multiply(inputVector[j])).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);*/
				
				net.getHiddenUnits()[i] = net.getHiddenUnits()[i] + net.getLayer1().getWeights()[j][i] * inputVector[j];

				 //System.out.print(net.getLayer1().getWeights()[j][i] + " * " +
				 //inputVector[j] + ") = "
				 //+ net.getHiddenUnits()[i] + "\n");
			}
			// no longer sigmoid - now hyperbolic tangent (tanh) range -1 to +1

			net.getHiddenUnits()[i] = utils.tanh(net.getHiddenUnits()[i]);
		}

		// calculate output value - again not forgetting BIAS
		evalNet.setOutputUnit(0);

		// System.out.println();
		// System.out.println("####################HIDDEN
		// ############################");

		for (int i = 0; i <= net.getNumNodesHidden(); i++) {

			// System.out.print(evalNet.getOutputUnit() + " + (");

			/*evalNet.setOutputUnit(
					evalNet.getOutputUnit().add(net.getLayer2().getWeights()[i].multiply(net.getHiddenUnits()[i])).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE));
*/
			evalNet.setOutputUnit(evalNet.getOutputUnit() + net.getLayer2().getWeights()[i] * net.getHiddenUnits()[i]);
			
			// System.out.println(net.getLayer2().getWeights()[i] + " * " +
			// net.getHiddenUnits()[i] + ") = " + evalNet.getOutputUnit() );
		}

		if (net.isDirectLinks()) {
			// System.out.println();
			// System.out.println("####################DIRECT LINKS
			// ############################");
			for (int i = 0; i <= net.getNumInputs(); i++) {

				// System.out.print(evalNet.getOutputUnit() + " + (");

				/*evalNet.setOutputUnit(
						evalNet.getOutputUnit().add(net.getLayer3().getWeights()[i].multiply(inputVector[i])).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE));
*/
				evalNet.setOutputUnit(evalNet.getOutputUnit() + net.getLayer3().getWeights()[i] * inputVector[i]);
				
				// System.out.println(net.getLayer3().getWeights()[i] + " * " +
				// inputVector[i] + ") = " + evalNet.getOutputUnit() );
			}
		}

		evalNet.setOutputUnit(utils.tanh(evalNet.getOutputUnit()));

/*		// TODO:RETIRAR AS LINHAS ABAIXO:

		System.out.println("Resultado: " + evalNet.getOutputUnit());

		evalNet.setOldOutput(BigDecimal.ZERO);

		initializeEligibility(net);
		initializeMomentum(net);

		long timeInicial = System.currentTimeMillis();
		
		evalNet.setOutputError(evalNet.getGamma().multiply(evalNet.getOutputUnit()).subtract(evalNet.getOldOutput())
				.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE));

		System.out.println("Resultado outputerror: " + evalNet.getOutputError());
	
		updateWeights(net);
			
		System.out.println("\n\nUPDATE ELIG");
			  
		updateElig(inputVector, net);
			  
		System.out.println("\n\nUDPATE WEIGHTS NOVAMENTE ");
			  
		updateWeights(net);
		  
		System.out.println("Tempo: " + (System.currentTimeMillis() - timeInicial)/1000);
		
*/
		/*if(evalNet.getOutputUnit() == null){
			System.out.println("REDE RETORNOU NULL!!!");
		}*/
		return evalNet.getOutputUnit();

	}

	public void tdFinal(float eValue, Net net) {
		
		System.out.println("Old output: " + evalNet.getOldOutput());
		
		System.out.println("Nome da rede: " + net.getName());

		/*evalNet.setOutputError(eValue.subtract(evalNet.getOldOutput()).setScale(Configuration.BIG_DEC_SCALE,
				Configuration.ROUNDING_MODE));*/
		
		evalNet.setOutputError(eValue - evalNet.getOldOutput());

		// keep track of error
		/*if (evalNet.getOutputError().abs().compareTo(evalNet.getMaxErr()) > 0) {
			evalNet.setMaxErr(evalNet.getOutputError().abs());
		}*/
		
		if(Math.abs(evalNet.getOutputError()) > evalNet.getMaxErr()){
			evalNet.setMaxErr(Math.abs(evalNet.getOutputError()));
		}

		/*evalNet.setAvgErr(evalNet.getAvgErr().add(evalNet.getOutputError()).setScale(Configuration.BIG_DEC_SCALE,
				Configuration.ROUNDING_MODE));*/
		
		evalNet.setAvgErr(evalNet.getAvgErr() + evalNet.getOutputError());

		updateWeights(net);

	}

	public void tdTrain(float[] iv, Net net) throws MlpException {

		evaluateNet(iv, net);
		
		/*evalNet.setOutputError(evalNet.getGamma().multiply(evalNet.getOutputUnit()).subtract(evalNet.getOldOutput())
				.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE));*/
		
		evalNet.setOutputError(evalNet.getGamma() * evalNet.getOutputUnit() - evalNet.getOldOutput());

		// keep track of error
		/*if (evalNet.getOutputError().abs().compareTo(evalNet.getMaxErr()) > 0) {
			evalNet.setMaxErr(evalNet.getOutputError().abs());
		}*/
		
		if(Math.abs(evalNet.getOutputError()) > evalNet.getMaxErr()){
			evalNet.setMaxErr(Math.abs(evalNet.getOutputError()));
		}

		/*evalNet.setAvgErr(evalNet.getAvgErr().add(evalNet.getOutputError()).setScale(Configuration.BIG_DEC_SCALE,
				Configuration.ROUNDING_MODE));*/
		
		evalNet.setAvgErr(evalNet.getAvgErr() + evalNet.getOutputError());

		updateWeights(net);

		// need 2nd time for TD errors
		evalNet.setOldOutput(evaluateNet(iv, net));

		updateElig(iv, net);

	}

	public void initTDTrain(float[] iv, Net net) throws MlpException {

		initializeEligibility(net);
		initializeMomentum(net);

		// Initializing the errors
		evalNet.setMaxErr(0);
		evalNet.setAvgErr(0);

		// evalNet.setLambda(lambda);
		// evalNet.setGamma(gamma);

		evalNet.setOldOutput(evaluateNet(iv, net));
		updateElig(iv, net);

	}

	public Net initializeWeights(Net net) {

		if (net == null) {
			net = utils.getNetConfiguration(net);
		}

		if (rand == null) {
			rand = new Random(net.getRandomSeed());
		}

		// Input layer to hidden layer
		Layer l1 = (Layer) net.getLayer1();

		// Merely initializates the first layer if the weights didn't load of
		// the
		// file
		if (l1 == null || l1.getWeights().length == 0) {
			l1 = new Layer(net.getNumInputs() + 1, net.getNumNodesHidden());

			for (int i = 0; i <= net.getNumInputs(); i++) {

				for (int j = 0; j < net.getNumNodesHidden(); j++) {

					//BigDecimal rNumber = net.getHighRange().subtract(net.getLowRange());
					float rNumber = net.getHighRange() - net.getLowRange();
					//rNumber = rNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					Double d = rand.nextDouble();

					float newNumber = d.floatValue();
					//newNumber = newNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					//l1.getWeights()[i][j] = net.getLowRange().add(rNumber.multiply(newNumber));
					l1.getWeights()[i][j] = net.getLowRange() + rNumber * newNumber;
				}
			}

			net.setLayer1((ILayer) l1);

		}

		// Hidden layer to output layer
		LayerSimple l2 = (LayerSimple) net.getLayer2();

		if (l2 == null || l2.getWeights().length == 0) {

			l2 = new LayerSimple(net.getNumNodesHidden() + 1);

			for (int j = 0; j <= net.getNumNodesHidden(); j++) {

				//BigDecimal rNumber = net.getHighRange().subtract(net.getLowRange());
				float rNumber = net.getHighRange()- net.getLowRange();
				//rNumber = rNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

				Double d = rand.nextDouble();

				float newNumber = d.floatValue();
				//newNumber = newNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

				l2.getWeights()[j] = net.getLowRange() + rNumber * newNumber;
			}

			net.setLayer2( (ILayerSimple) l2);
		}

		// Initialize weights of in - output links
		if (net.isDirectLinks()) {

			LayerSimple l3 = (LayerSimple) net.getLayer3();

			if (l3 == null || l3.getWeights().length == 0) {

				l3 = new LayerSimple(net.getNumInputs() + 1);

				for (int j = 0; j <= net.getNumInputs(); j++) {

					//BigDecimal rNumber = net.getHighRange().subtract(net.getLowRange());
					float rNumber = net.getHighRange() - net.getLowRange();
					//rNumber = rNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					Double d = rand.nextDouble();

					float newNumber = d.floatValue();
					//newNumber = newNumber.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					l3.getWeights()[j] = net.getLowRange() + rNumber * newNumber;
				}

				net.setLayer3((ILayerSimple) l3);
			}

		}

		return net;
	}

	public void initializeHiddenUnits(Net net) {

		/*
		 * if (net.getLayer2() == null) { net.setLayer2(new
		 * LayerSimple(net.getNumInputs() + 1)); }
		 * 
		 * for (int i = 0; i < net.getLayer2().getWeights().length; i++) {
		 * net.getLayer2().getWeights()[i] = BigDecimal.ZERO; }
		 */

		if (net.getHiddenUnits() == null) {
			net.setHiddenUnits(new float[net.getNumNodesHidden() + 1]);

			/*
			 * for(int i = 0; i < net.getHiddenUnits().length; i++){
			 * net.getHiddenUnits()[i] = BigDecimal.ZERO; }
			 */
		}

	}

	// Initialize net's momentum arrays
	private void initializeMomentum(Net net) throws MlpException {

		if (net == null) {
			throw new MlpException("Network not initialized.");
		}

		if (rand == null) {
			rand = new Random(net.getRandomSeed());
		}

		// Input layer to hidden layer
		Layer l1 = new Layer(net.getNumInputs() + 1, net.getNumNodesHidden());

		for (int i = 0; i <= net.getNumInputs(); i++) {

			for (int j = 0; j < net.getNumNodesHidden(); j++) {

				l1.getWeights()[i][j] = 0;
			}
		}

		net.setOldLayer1((ILayer) l1);

		// Hidden layer to output layer
		LayerSimple l2 = new LayerSimple(net.getNumNodesHidden() + 1);

		for (int j = 0; j <= net.getNumNodesHidden(); j++) {

			l2.getWeights()[j] = 0;
		}

		net.setOldLayer2((ILayerSimple) l2);

		// Initialize weights of in - output links
		if (net.isDirectLinks()) {

			LayerSimple l3 = new LayerSimple(net.getNumInputs() + 1);

			for (int j = 0; j <= net.getNumInputs(); j++) {

				l3.getWeights()[j] = 0;
			}

			net.setOldLayer3( (ILayerSimple) l3);
		}

	}

	// Initialize the eligibility trace
	private void initializeEligibility(Net net) throws MlpException {

		if (net == null) {
			throw new MlpException("Network not initialized.");
		}

		if (rand == null) {
			rand = new Random(net.getRandomSeed());
		}

		// Input layer to hidden layer
		Layer l1 = new Layer(net.getNumInputs() + 1, net.getNumNodesHidden());

		for (int i = 0; i <= net.getNumInputs(); i++) {

			for (int j = 0; j < net.getNumNodesHidden(); j++) {

				l1.getWeights()[i][j] = 0;
			}
		}

		net.setElig1( (ILayer) l1);

		// Hidden layer to output layer
		LayerSimple l2 = new LayerSimple(net.getNumNodesHidden() + 1);

		for (int j = 0; j <= net.getNumNodesHidden(); j++) {

			l2.getWeights()[j] = 0;
		}

		net.setElig2( (ILayerSimple) l2);

		// Initialize weights of in - output links
		if (net.isDirectLinks()) {

			LayerSimple l3 = new LayerSimple(net.getNumInputs() + 1);

			for (int j = 0; j <= net.getNumInputs(); j++) {

				l3.getWeights()[j] = 0;
			}

			net.setElig3( (ILayerSimple) l3);
		}

	}

	private void readNetParameters() {

		evalNet.setGamma(Float.valueOf(SystemConfigs.getInstance().getConfig("gamma")));
		evalNet.setLambda(Float.valueOf(SystemConfigs.getInstance().getConfig("lambda")));
		evalNet.setMomentum(Float.valueOf(SystemConfigs.getInstance().getConfig("momentum")));
		evalNet.setlRate1(Float.valueOf(SystemConfigs.getInstance().getConfig("lRate1")));
		evalNet.setlRate2(Float.valueOf(SystemConfigs.getInstance().getConfig("lRate2")));
		evalNet.setBias(Float.valueOf(SystemConfigs.getInstance().getConfig("bias")));

	}

	/*
	 * public Net getNet() { return net; }
	 * 
	 * public void setNet(Net net) { this.net = net; }
	 */

	public static void main(String[] args) {

		/*BigDecimal a = new BigDecimal("2");
		BigDecimal b = BigDecimal.ZERO;

		BigDecimal c = new BigDecimal("0.1111111");

		System.out.println(c.toString());

		System.out.println(c.multiply(a));
		System.out.println((b.multiply(c)).toPlainString());*/
		
		EvalNetImpl eval = new EvalNetImpl(new EvalNet());
		
		Net teste = null;
		teste = new Utils().getNetConfiguration(teste);
		eval.initializeWeights(teste);
		
		FileManipulate fm = new FileManipulate();
		try {
			fm.writeNetFile(teste, "/home/lia1/", "redeteste");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
