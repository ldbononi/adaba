package ufu.facom.lia.mlp;

import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.structures.mlp.Net;

public class Utils {

	public Net getNetConfiguration(Net net) {

		if (net == null) {
			net = new Net();
		}

		net.setNumInputs(Integer.parseInt(SystemConfigs.getInstance().getConfig("numInputs").trim()));
		net.setNumNodesHidden(Integer.parseInt(SystemConfigs.getInstance().getConfig("numHidden").trim()));
		net.setName(SystemConfigs.getInstance().getConfig("networkName"));
		net.setDirectLinks(SystemConfigs.getInstance().getConfig("directLinks").trim().equals("1") ? true : false);
		net.setLowRange(new Float(SystemConfigs.getInstance().getConfig("lowRange").trim()));
		net.setHighRange(new Float(SystemConfigs.getInstance().getConfig("highRange").trim()));
		net.setRandomSeed(Long.parseLong(SystemConfigs.getInstance().getConfig("networkSeed")));

		return net;
	}

	public float tanh1(float x) {

		Double t = (2.0 / (1.0 + Math.exp(-2.0*(double)x))) - 1.0;

		/*BigDecimal base = new BigDecimal("2");

		BigDecimal baseNeg = new BigDecimal("-2");

		Double aTemp = Math.exp(baseNeg.multiply(x).doubleValue());

		BigDecimal exp = new BigDecimal(aTemp).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

		return (base.divide(BigDecimal.ONE.add(exp))).subtract(BigDecimal.ONE).setScale(Configuration.BIG_DEC_SCALE,
				Configuration.ROUNDING_MODE);*/
		
		return t.floatValue();

	}

	public float tanh1Deriv(float x) {

		// ((1.0 + (double)x) * (1.0 - (double)x))

		return (float) ((1.0 + x) * (1.0 - x));//(BigDecimal.ONE.add(x)).multiply(BigDecimal.ONE.subtract(x)).setScale(Configuration.BIG_DEC_SCALE,
				//Configuration.ROUNDING_MODE);
	}

	public float tanh(float x) {

		// (float) (tanh((double)x))

		Double a = Math.tanh(x);

		return a.floatValue(); // new BigDecimal(a.toString()).setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);
	}

	public float tanhDeviv(float x) {

		// (1.0 - ((double)x*(double)x))
		
		float b = 1 - (x*x);

		//BigDecimal b = BigDecimal.ONE.subtract(x.multiply(x)).setScale(Configuration.BIG_DEC_SCALE,
			//	Configuration.ROUNDING_MODE);

		return b;
	}
}
