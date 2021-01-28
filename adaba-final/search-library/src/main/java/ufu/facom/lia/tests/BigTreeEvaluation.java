package ufu.facom.lia.tests;

import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Evaluation;
import ufu.facom.lia.search.interfaces.Player;

public class BigTreeEvaluation implements Evaluation, Cloneable {

	@Override
	public Float getEvaluation(INode state, Player player) {
		
		/*if(state.getName().equals("S2")){
			
			return new BigDecimal("6.1");
			
		}else if(state.getName().equals("S3")){
			
			return new BigDecimal("6.5");
			
		}else if(state.getName().equals("S4")){
			
			return new BigDecimal("6.2");

		}else if(state.getName().equals("S5")){
			
			return new BigDecimal("4.0");

		}else if(state.getName().equals("S6")){
			
			return new BigDecimal("6.6");
			
		}else if(state.getName().equals("S7")){
		
			return new BigDecimal("6.4");
			
		}else if(state.getName().equals("S8")){
		
			return new BigDecimal("6.5");
			
		}else if(state.getName().equals("S9")){
		
			return new BigDecimal("5.9");

			
		}else if(state.getName().equals("S10")){
		
			return new BigDecimal("6.0");
			
		}else if(state.getName().equals("S11")){
		
			return new BigDecimal("6.3");
			
		}else if(state.getName().equals("S12")){
		
			return new BigDecimal("5.3");
			
		}else if(state.getName().equals("S13")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S14")){
		
			return new BigDecimal("5.3");
			
		}else if(state.getName().equals("S15")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S16")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S17")){
		
			return new BigDecimal("5.0");
			
		}else if(state.getName().equals("S18")){
		
			return new BigDecimal("5.0");
			
		}else if(state.getName().equals("S19")){
		
			return new BigDecimal("5.8");
			
		}else if(state.getName().equals("S20")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S21")){
		
			return new BigDecimal("5.9");
			
		}else if(state.getName().equals("S22")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S23")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S24")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S25")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S26")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S27")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S28")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S29")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S30")){
		
			return new BigDecimal("4.8");
			
		}else if(state.getName().equals("S31")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S32")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S33")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S34")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S35")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S36")){
		
			return new BigDecimal("4.3");
			
		}else if(state.getName().equals("S37")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S38")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S39")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S40")){
		
			return new BigDecimal("4.9");
			
		}else if(state.getName().equals("S41")){
		
			return new BigDecimal("5.1");
			
		}else if(state.getName().equals("S42")){
		
			return new BigDecimal("4.3");
			
		}else if(state.getName().equals("S43")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S44")){
		
			return new BigDecimal("3.3");
			
		}else if(state.getName().equals("S45")){
		
			return new BigDecimal("4.9");
			
		}else if(state.getName().equals("S46")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S47")){
		
			return new BigDecimal("4.4");
			
		}else if(state.getName().equals("S48")){
		
			return new BigDecimal("4.9");
			
		}else if(state.getName().equals("S49")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S50")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S51")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S52")){
		
			return new BigDecimal("5.7");
			
		}else if(state.getName().equals("S53")){
		
			return new BigDecimal("5.8");
			
		}else if(state.getName().equals("S54")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S55")){
		
			return new BigDecimal("4.4");
			
		}else if(state.getName().equals("S56")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S57")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S58")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S59")){
			
			return new BigDecimal("3.4");

		}else if(state.getName().equals("S60")){
			
			return new BigDecimal("3.2");

		}else if(state.getName().equals("S61")){
			
			return new BigDecimal("3.5");

		}else if(state.getName().equals("S62")){
			
			return new BigDecimal("3.6");

		}else if(state.getName().equals("S63")){
			
			return new BigDecimal("3.2");

		}else if(state.getName().equals("S64")){
		
			return new BigDecimal("4.1");
			
		}else if(state.getName().equals("S65")){
		
			return new BigDecimal("5.8");
			
		}else if(state.getName().equals("S66")){
		
			return new BigDecimal("5.9");
			
		}else if(state.getName().equals("S67")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S68")){
		
			return new BigDecimal("3.6");
			
		}else if(state.getName().equals("S69")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S70")){
		
			return new BigDecimal("5.0");
			
		}else if(state.getName().equals("S71")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S72")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S73")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S74")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S75")){
		
			return new BigDecimal("2.6");
			
		}else if(state.getName().equals("S76")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S77")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S78")){
		
			return new BigDecimal("4.8");
			
		}else if(state.getName().equals("S79")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S80")){
			
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S81")){
			
			return new BigDecimal("3.7");
			
		}else if(state.getName().equals("S82")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S83")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S84")){
			
			return new BigDecimal("3.1");

		}else if(state.getName().equals("S85")){
			
			return new BigDecimal("3.2");

		}else if(state.getName().equals("S86")){
			
			return new BigDecimal("3.1");

		}else if(state.getName().equals("S87")){
			
			return new BigDecimal("3.3");

		}else if(state.getName().equals("S88")){
			
			return new BigDecimal("3.0");

		}else if(state.getName().equals("S89")){
		
			return new BigDecimal("5.6");
			
		}else if(state.getName().equals("S90")){
			
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S91")){
		
			return new BigDecimal("3.6");
			
		}else if(state.getName().equals("S92")){
			
			return new BigDecimal("3.0");

		}else if(state.getName().equals("S93")){
			
			return new BigDecimal("3.1");

		}else if(state.getName().equals("S94")){
			
			return new BigDecimal("3.4");

		}else if(state.getName().equals("S95")){
			
			return new BigDecimal("3.1");

		}else if(state.getName().equals("S96")){
			
			return new BigDecimal("3.0");

		}else if(state.getName().equals("S97")){
			
			return new BigDecimal("2.9");

		}else if(state.getName().equals("S98")){
		
			return new BigDecimal("5.1");
			
		}else if(state.getName().equals("S99")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S100")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S101")){
		
			return new BigDecimal("6.3");
			
		}else if(state.getName().equals("S102")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S103")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S104")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S105")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S106")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S107")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S108")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S109")){
		
			return new BigDecimal("4.8");
			
		}else if(state.getName().equals("S110")){
		
			return new BigDecimal("4.1");
			
		}else if(state.getName().equals("S111")){
		
			return new BigDecimal("5.1");
			
		}else if(state.getName().equals("S112")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S113")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S114")){
		
			return new BigDecimal("5.8");
			
		}else if(state.getName().equals("S115")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S116")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S117")){
		
			return new BigDecimal("5.1");
			
		}else if(state.getName().equals("S118")){
		
			return new BigDecimal("5.7");
			
		}else if(state.getName().equals("S119")){
		
			return new BigDecimal("5.5");
			
		}else if(state.getName().equals("S120")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S121")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S122")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S123")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S124")){
		
			return new BigDecimal("5.2");
			
		}else if(state.getName().equals("S125")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S126")){
		
			return new BigDecimal("3.6");
			
		}else if(state.getName().equals("S127")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S128")){
		
			return new BigDecimal("4.4");
			
		}else if(state.getName().equals("S129")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S130")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S131")){
		
			return new BigDecimal("3.3");
			
		}else if(state.getName().equals("S132")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S133")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S134")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S135")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S136")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S137")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S138")){
		
			return new BigDecimal("4.8");
			
		}else if(state.getName().equals("S139")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S140")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S141")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S142")){
			
			return new BigDecimal("2.5");

		}else if(state.getName().equals("S143")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S144")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S145")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S146")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S147")){
		
			return new BigDecimal("5.7");
			
		}else if(state.getName().equals("S148")){
			
			return new BigDecimal("4.5");

		}else if(state.getName().equals("S149")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S150")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S151")){
		
			return new BigDecimal("4.9");
			
		}else if(state.getName().equals("S152")){
		
			return new BigDecimal("4.0");
			
		}else if(state.getName().equals("S153")){
		
			return new BigDecimal("5.4");
			
		}else if(state.getName().equals("S154")){
		
			return new BigDecimal("4.6");
			
		}else if(state.getName().equals("S155")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S156")){
		
			return new BigDecimal("4.1");
			
		}else if(state.getName().equals("S157")){
			
			return new BigDecimal("3.7");

		}else if(state.getName().equals("S158")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S159")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S160")){
			
			return new BigDecimal("3.8");

		}else if(state.getName().equals("S161")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S162")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S163")){
		
			return new BigDecimal("4.2");
			
		}else if(state.getName().equals("S164")){
		
			return new BigDecimal("3.8");
			
		}else if(state.getName().equals("S165")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S166")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S167")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S168")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S226")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S227")){
		
			return new BigDecimal("4.2");
			
		}else if(state.getName().equals("S228")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S229")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S230")){
		
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S231")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S232")){
		
			return new BigDecimal("3.3");
			
		}else if(state.getName().equals("S233")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S234")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S235")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S236")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S169")){
		
			return new BigDecimal("3.0");
			
		}else if(state.getName().equals("S170")){
		
			return new BigDecimal("2.0");
			
		}else if(state.getName().equals("S171")){
		
			return new BigDecimal("4.0");
			
		}else if(state.getName().equals("S172")){
		
			return new BigDecimal("5.0");
			
		}else if(state.getName().equals("S173")){
			
			return new BigDecimal("2.4");

		}else if(state.getName().equals("S174")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S175")){
		
			return new BigDecimal("2.4");
			
		}else if(state.getName().equals("S176")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S177")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S178")){
		
			return new BigDecimal("2.0");
			
		}else if(state.getName().equals("S179")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S180")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S181")){
		
			return new BigDecimal("4.0");
			
		}else if(state.getName().equals("S182")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S183")){
		
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S184")){
		
			return new BigDecimal("4.3");
			
		}else if(state.getName().equals("S185")){
		
			return new BigDecimal("5.1");
			
		}else if(state.getName().equals("S186")){
		
			return new BigDecimal("4.8");
			
		}else if(state.getName().equals("S187")){
			
			return new BigDecimal("2.2");

		}else if(state.getName().equals("S188")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S189")){
		
			return new BigDecimal("4.2");
			
		}else if(state.getName().equals("S190")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S191")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S192")){
		
			return new BigDecimal("2.2");
			
		}else if(state.getName().equals("S193")){
		
			return new BigDecimal("3.2");
			
		}else if(state.getName().equals("S194")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S195")){
			
			return new BigDecimal("1.5");

		}else if(state.getName().equals("S196")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S197")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S198")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S199")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S200")){
			
			return new BigDecimal("0.5");

		}else if(state.getName().equals("S201")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S202")){
		
			return new BigDecimal("3.7");
			
		}else if(state.getName().equals("S203")){
		
			return new BigDecimal("5.0");
			
		}else if(state.getName().equals("S204")){
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S205")){
			
			return new BigDecimal("2.9");

		}else if(state.getName().equals("S206")){
			
			return new BigDecimal("3.9");

		}else if(state.getName().equals("S207")){
			
			return new BigDecimal("1.9");

		}else if(state.getName().equals("S208")){
			
			return new BigDecimal("0.9");

		}else if(state.getName().equals("S209")){
			
			return new BigDecimal("2.1");

		}else if(state.getName().equals("S210")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S211")){
			return new BigDecimal("1.0");
			
		}else if(state.getName().equals("S212")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S213")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S214")){
			
			return new BigDecimal("2.7");

		}else if(state.getName().equals("S215")){
			
			return new BigDecimal("4.7");
			
		}else if(state.getName().equals("S216")){
		
			return new BigDecimal("3.6");
			
		}else if(state.getName().equals("S217")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S218")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S219")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S220")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S221")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S222")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S223")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S224")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S225")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S237")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S238")){
		
			return new BigDecimal("1.1");
			
		}else if(state.getName().equals("S239")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S240")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S241")){
			
			return new BigDecimal("0.7");

		}else if(state.getName().equals("S242")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S243")){
		
			return new BigDecimal("4.1");
			
		}else if(state.getName().equals("S244")){
		
			return new BigDecimal("1.2");
			
		}else if(state.getName().equals("S245")){
			
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S246")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S247")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S248")){
		
			return new BigDecimal("4.5");
			
		}else if(state.getName().equals("S249")){
		
			return new BigDecimal("2.8");
			
		}else if(state.getName().equals("S250")){
		
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S251")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S252")){
		
			return new BigDecimal("2.6");
			
		}else if(state.getName().equals("S253")){
		
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S254")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S255")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S256")){
		
			return new BigDecimal("1.0");
			
		}else if(state.getName().equals("S257")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S258")){
		
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S259")){
		
			return new BigDecimal("1.2");
			
		}else if(state.getName().equals("S260")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S261")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S262")){
		
			return new BigDecimal("1.3");
			
		}else if(state.getName().equals("S263")){
		
			return new BigDecimal("1.0");
			
		}else if(state.getName().equals("S264")){
		
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S265")){
		
			return new BigDecimal("2.4");
			
		}else if(state.getName().equals("S266")){
		
			return new BigDecimal("3.2");
			
		}else if(state.getName().equals("S267")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S268")){
		
			return new BigDecimal("2.6");
			
		}else if(state.getName().equals("S269")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S270")){
		
			return new BigDecimal("4.2");
			
		}else if(state.getName().equals("S271")){
		
			return new BigDecimal("3.0");
			
		}else if(state.getName().equals("S272")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S273")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S274")){
		
			return new BigDecimal("0.2");
			
		}else if(state.getName().equals("S275")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S276")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S277")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S278")){
		
			return new BigDecimal("1.0");
			
		}else if(state.getName().equals("S279")){
		
			return new BigDecimal("1.4");
			
		}else if(state.getName().equals("S280")){
		
			return new BigDecimal("2.7");
			
		}else if(state.getName().equals("S281")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S282")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S283")){
		
			return new BigDecimal("2.6");
			
		}else if(state.getName().equals("S284")){
		
			return new BigDecimal("1.1");
			
		}else if(state.getName().equals("S285")){
		
			return new BigDecimal("2.4");
			
		}else if(state.getName().equals("S286")){
		
			return new BigDecimal("1.4");
			
		}else if(state.getName().equals("S287")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S288")){
		
			return new BigDecimal("3.7");
			
		}else if(state.getName().equals("S289")){
		
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S290")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S291")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S292")){
		
			return new BigDecimal("2.0");
			
		}else if(state.getName().equals("S293")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S294")){
		
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S295")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S296")){
		
			return new BigDecimal("0.6");
			
		}else if(state.getName().equals("S297")){
		
			return new BigDecimal("2.6");
			
		}else if(state.getName().equals("S298")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S299")){
		
			return new BigDecimal("0.1");
			
		}else if(state.getName().equals("S300")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S301")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S302")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S303")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S304")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S305")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S306")){
		
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S307")){
		
			return new BigDecimal("0.1");
			
		}else if(state.getName().equals("S308")){
		
			return new BigDecimal("2.5");
			
		}else if(state.getName().equals("S309")){
		
			return new BigDecimal("3.2");
			
		}else if(state.getName().equals("S310")){
		
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S311")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S312")){
		
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S313")){
		
			return new BigDecimal("0.6");
			
		}else if(state.getName().equals("S314")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S315")){
		
			return new BigDecimal("1.7");
			
		}else if(state.getName().equals("S316")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S317")){
		
			return new BigDecimal("1.4");
			
		}else if(state.getName().equals("S318")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S319")){
		
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S320")){
		
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S321")){
		
			return new BigDecimal("0.3");
			
		}else if(state.getName().equals("S322")){
		
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S323")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S324")){
		
			return new BigDecimal("2.3");
			
		}else if(state.getName().equals("S325")){
		
			return new BigDecimal("3.4");
			
		}else if(state.getName().equals("S326")){
		
			return new BigDecimal("2.9");
			
		}else if(state.getName().equals("S327")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S328")){
		
			return new BigDecimal("3.9");
			
		}else if(state.getName().equals("S329")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S330")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S331")){
		
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S332")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S333")){
		
			return new BigDecimal("1.5");
			
		}else if(state.getName().equals("S334")){
		
			return new BigDecimal("1.3");
			
		}else if(state.getName().equals("S335")){
		
			return new BigDecimal("3.1");
			
		}else if(state.getName().equals("S336")){
		
			return new BigDecimal("3.2");
			
		}else if(state.getName().equals("S337")){
		
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S338")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S339")){
		
			return new BigDecimal("0.9");
			
		}else if(state.getName().equals("S340")){
		
			return new BigDecimal("0.8");
			
		}else if(state.getName().equals("S341")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S342")){
		
			return new BigDecimal("1.8");
			
		}else if(state.getName().equals("S343")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S344")){
		
			return new BigDecimal("0.5");
			
		}else if(state.getName().equals("S345")){
		
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S346")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S347")){
		
			return new BigDecimal("0.2");
			
		}else if(state.getName().equals("S348")){
		
			return new BigDecimal("1.9");
			
		}else if(state.getName().equals("S349")){
		
			return new BigDecimal("0.4");
			
		}else if(state.getName().equals("S350")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S351")){
		
			return new BigDecimal("0.7");
			
		}else if(state.getName().equals("S352")){
		
			return new BigDecimal("0.6");
			
		}else if(state.getName().equals("S353")){
		
			return new BigDecimal("1.6");
			
		}else if(state.getName().equals("S354")){
		
			return new BigDecimal("3.5");
			
		}else if(state.getName().equals("S355")){
		
			return new BigDecimal("2.2");
			
		}else if(state.getName().equals("S356")){
		
			return new BigDecimal("2.1");
			
		}else if(state.getName().equals("S357")){
		
			return new BigDecimal("0.6");
			
		}else if(state.getName().equals("S358")){
		
			return new BigDecimal("3.9");
			
		}		
		*/
		return null;
	}
	
	@Override
	public Evaluation clone() throws CloneNotSupportedException {
		return (Evaluation) super.clone();
	}

}
