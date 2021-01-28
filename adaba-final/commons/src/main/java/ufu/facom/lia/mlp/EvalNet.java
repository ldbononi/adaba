package ufu.facom.lia.mlp;

import java.io.Serializable;

public class EvalNet implements Serializable {

	private static final long serialVersionUID = 1L;

	private float bias;

	private float lRate1; // learning rate of the net- in - hid links
								// (1/inputs)

	private float lRate2; // hid - out lrate 1/hidunits

	private float momentum; // momentum value

	private float gamma; // rate of decay in TD training

	private float lambda; // how much the sum of deriv (elig) trickles back

	private float maxErr; // keep track of error in net

	private float avgErr; // keep track of error in net

	private float oldOutput; // previous output - for TD

	private float outputUnit; // output value. ie. evaluation

	private float outputError; // output error

	private Integer numInputs; // total inputs neural network to configure
								// lRate1

	private Integer numHidden; // total hidden units neural network to configure
								// lRate2

	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public float getlRate1() {

		/*if (lRate1.compareTo(BigDecimal.ZERO) == 0) {
			lRate1 = new BigDecimal(String.valueOf(1f / numInputs)).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);
		}*/
		
		if (lRate1 == 0) {
			lRate1 = 1f / numInputs;
		}

		return lRate1;
	}

	public void setlRate1(float lRate1) {
		this.lRate1 = lRate1;
	}

	public float getlRate2() {

		/*if (lRate2.compareTo(BigDecimal.ZERO) == 0) {
			lRate2 = new BigDecimal(String.valueOf(1f / numHidden)).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);
		}*/
		
		if (lRate2 == 0) {
			lRate2 = 1f / numHidden;
		}

		return lRate2;
	}

	public Integer getNumHidden() {
		return numHidden;
	}

	public void setNumHidden(Integer numHidden) {
		this.numHidden = numHidden;
	}

	public void setlRate2(float lRate2) {
		this.lRate2 = lRate2;
	}

	public float getMomentum() {
		return momentum;
	}

	public void setMomentum(float momentum) {
		this.momentum = momentum;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

	public float getLambda() {
		return lambda;
	}

	public void setLambda(float lambda) {
		this.lambda = lambda;
	}

	public float getMaxErr() {
		return maxErr;
	}

	public void setMaxErr(float maxErr) {
		this.maxErr = maxErr;
	}

	public float getAvgErr() {
		return avgErr;
	}

	public void setAvgErr(float avgErr) {
		this.avgErr = avgErr;
	}

	public float getOldOutput() {
		
		/*if(oldOutput == null){
			oldOutput = BigDecimal.ZERO;
		}*/
		
		return oldOutput;
	}

	public void setOldOutput(float oldOutput) {
		this.oldOutput = oldOutput;
	}

	public float getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(float outputUnit) {
		this.outputUnit = outputUnit;
	}

	public float getOutputError() {
		
		return outputError;
	}

	public void setOutputError(float outputError) {
		this.outputError = outputError;
	}

	public Integer getNumInputs() {
		return numInputs;
	}

	public void setNumInputs(Integer numInputs) {
		this.numInputs = numInputs;
	}
}
