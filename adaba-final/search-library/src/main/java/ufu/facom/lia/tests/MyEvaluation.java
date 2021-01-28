package ufu.facom.lia.tests;

import java.math.BigDecimal;

import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Player;

public class MyEvaluation implements Evaluation, Cloneable {

	@Override
	public Float getEvaluation(INode state, Player player) {
		
		/*if(state.getName().equals("S5")){
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S6")){
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S7")){
			return new BigDecimal("0.2");
			
		}else if(state.getName().equals("S9")){
			return new BigDecimal("0.1");
			
		}else if(state.getName().equals("S10")){
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S11")){
			return new BigDecimal("0.2");
			
		}else if(state.getName().equals("S13")){
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S14")){
			return new BigDecimal("0.1");
			
		}else if(state.getName().equals("S15")){
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S4")){
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S8")){
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S3")){
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S12")){
			return new BigDecimal("0.6");
			
		}else if(state.getName().equals("S2")){
			return new BigDecimal("0.9");
		}*/
		
		
		return null;
	}
	
	@Override
	public Evaluation clone() throws CloneNotSupportedException {
		return (Evaluation) super.clone();
	}

}
