package ufu.facom.lia.tests;

import java.math.BigDecimal;

import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Player;

public class GutierrezEvaluation implements Evaluation, Cloneable{

	@Override
	public Float getEvaluation(INode state, Player player) {
		
		/*if(state.getName().equals("S11")){
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S12")){
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S13")){
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S21")){
			return new BigDecimal("0.1");
			
		}else if(state.getName().equals("S31")){
			return new BigDecimal("0.2");
			
		}else if(state.getName().equals("S32")){
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S33")){
			return new BigDecimal("0.8");
		}
		*/
		return null;
	}
	
	@Override
	public Evaluation clone() throws CloneNotSupportedException {
		return (Evaluation) super.clone();
	}

}
