package ufu.facom.lia.hash;

import ufu.facom.lia.evaluation.policy.Node;
import ufu.facom.lia.exceptions.ZobristException;
import ufu.facom.lia.interfaces.BoardValues;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.IHashKey;

public class ZobristKey implements IHashKey {
	
	private static long[] key64 = new long[128];
	private static int[] key32 = new int[128];
	
	static{
		
		key64[0] = 1478754046664586863L;
		key64[1] = 2120251484556677534L;
		key64[2] = 584882445155849028L;
		key64[3] = 3760951787791404667L;
		key64[4] = 1790361570420992041L;
		key64[5] = 5781218707178284009L;
		key64[6] = 7894141919871615785L;
		key64[7] = 3578131985066232389L;
		key64[8] = 1817657397089932766L;
		key64[9] = 953739615516480151L;
		key64[10] = 5808583100557493539L;
		key64[11] = 3651659200175719294L;
		key64[12] = 1125032371284561709L;
		key64[13] = 1559254254694982281L;
		key64[14] = 1620413813026009937L;
		key64[15] = 958532140380769526L;
		key64[16] = 1591554202652719505L;
		key64[17] = 1624867970977323614L;
		key64[18] = 6685379756495787903L;
		key64[19] = 6977407078633077238L;
		key64[20] = 1729081295984380347L;
		key64[21] = 6892212846999406827L;
		key64[22] = 632708781781195948L;
		key64[23] = 8082145037705841596L;
		key64[24] = 1174081101029859999L;
		key64[25] = 348921443543585631L;
		key64[26] = 1457974994007758230L;
		key64[27] = 6486449913624012919L;
		key64[28] = 3466492341137833191L;
		key64[29] = 471079928059731524L;
		key64[30] = 1265803793010643531L;
		key64[31] = 1196331064168240729L;
		key64[32] = 7959653230876841906L;
		key64[33] = 1394710282219996232L;
		key64[34] = 8642761543770578771L;
		key64[35] = 1591924587300972629L;
		key64[36] = 4820270499748795572L;
		key64[37] = 3225083136839776522L;
		key64[38] = 1814686871109549413L;
		key64[39] = 966026321784100878L;
		key64[40] = 1536101965795270550L;
		key64[41] = 2665997128910576346L;
		key64[42] = 4116146610084751327L;
		key64[43] = 1605765185908830833L;
		key64[44] = 1503562655001840321L;
		key64[45] = 812445482574380181L;
		key64[46] = 1733093197806477350L;
		key64[47] = 1714706357858314431L;
		key64[48] = 801355974318604282L;
		key64[49] = 7341931806310238008L;
		key64[50] = 6510958723394863875L;
		key64[51] = 1244321574552903559L;
		key64[52] = 1870961009483288548L;
		key64[53] = 1355890617462912462L;
		key64[54] = 5101600632499193585L;
		key64[55] = 1797860915757630329L;
		key64[56] = 1667855917042838661L;
		key64[57] = 1812024871927449277L;
		key64[58] = 6885142883649161710L;
		key64[59] = 7674655846754091273L;
		key64[60] = 7142836392781394548L;
		key64[61] = 934893943121608558L;
		key64[62] = 1137038065157768603L;
		key64[63] = 5103557615976167553L;
		key64[64] = 1821368303374267150L;
		key64[65] = 5688052552305314374L;
		key64[66] = 4184803622034389672L;
		key64[67] = 1421501277622921964L;
		key64[68] = 154808700423236090L;
		key64[69] = 1062096942058000496L;
		key64[70] = 811615768727924425L;
		key64[71] = 8961368139741125032L;
		key64[72] = 1377473475110409086L;
		key64[73] = 1780939509007623561L;
		key64[74] = 1769474449616173014L;
		key64[75] = 1258824260720777251L;
		key64[76] = 1184653815078949669L;
		key64[77] = 1023962441211841638L;
		key64[78] = 1501202449546262242L;
		key64[79] = 432321212931276379L;
		key64[80] = 1171196056361380757L;
		key64[81] = 1579519584277815850L;
		key64[82] = 12012512926255931L;
		key64[83] = 1196922711287061799L;
		key64[84] = 9204715365712158256L;
		key64[85] = 1122515725441343746L;
		key64[86] = 1275373227474541249L;
		key64[87] = 1457835577042588156L;
		key64[88] = 3905938155971329489L;
		key64[89] = 3931887130350033154L;
		key64[90] = 1744827655684463950L;
		key64[91] = 5425415767280876664L;
		key64[92] = 4758384373672461188L;
		key64[93] = 1142209372058245165L;
		key64[94] = 1123961318980707956L;
		key64[95] = 1383193871499007890L;
		key64[96] = 8978665553187022367L;
		key64[97] = 6792129980026176469L;
		key64[98] = 1110600308486405788L;
		key64[99] = 5684749757081299935L;
		key64[100] = 3967728617316940461L;
		key64[101] = 1623203266974481401L;
		key64[102] = 1354678032186242680L;
		key64[103] = 3009792841844867034L;
		key64[104] = 1342259092375336061L;
		key64[105] = 1022176388732921119L;
		key64[106] = 5616157223557226974L;
		key64[107] = 2865046354894257591L;
		key64[108] = 1464259463112989593L;
		key64[109] = 8381146724961928037L;
		key64[110] = 3023307655632321181L;
		key64[111] = 8375086150794650026L;
		key64[112] = 1181004167988126008L;
		key64[113] = 1213308520865758682L;
		key64[114] = 973471555951372857L;
		key64[115] = 1218493748803272056L;
		key64[116] = 4993510297519374450L;
		key64[117] = 1212413787004164618L;
		key64[118] = 2664161134633443445L;
		key64[119] = 327774891080306970L;
		key64[120] = 1488896853717660521L;
		key64[121] = 6271745259985944523L;
		key64[122] = 1450725767204505073L;
		key64[123] = 8740695389947450601L;
		key64[124] = 948781099114194022L;
		key64[125] = 1463952744736776292L;
		key64[126] = 8795549574004575914L;
		key64[127] = 1803060461769597446L;
			
		key32[0] = 624480609;
		key32[1] = 943091740;
		key32[2] = 483127371;
		key32[3] = 361614344;
		key32[4] = 101415361;
		key32[5] = 406976948;
		key32[6] = 17787634;
		key32[7] = 927147620;
		key32[8] = 88555997;
		key32[9] = 624431208;
		key32[10] = 515432313;
		key32[11] = 20421936;
		key32[12] = 243995382;
		key32[13] = 892168904;
		key32[14] = 767060695;
		key32[15] = 487632255;
		key32[16] = 541852373;
		key32[17] = 943874415;
		key32[18] = 523645679;
		key32[19] = 181371974;
		key32[20] = 993945440;
		key32[21] = 409513806;
		key32[22] = 485087324;
		key32[23] = 411721511;
		key32[24] = 715605215;
		key32[25] = 296472132;
		key32[26] = 389899783;
		key32[27] = 966004945;
		key32[28] = 767469490;
		key32[29] = 706098374;
		key32[30] = 341843816;
		key32[31] = 318488086;
		key32[32] = 209341568;
		key32[33] = 537896406;
		key32[34] = 459370360;
		key32[35] = 39634299;
		key32[36] = 896762471;
		key32[37] = 277644702;
		key32[38] = 509830971;
		key32[39] = 682612718;
		key32[40] = 793789203;
		key32[41] = 818949100;
		key32[42] = 90355357;
		key32[43] = 268873186;
		key32[44] = 254029271;
		key32[45] = 572395045;
		key32[46] = 390111534;
		key32[47] = 688164087;
		key32[48] = 73441363;
		key32[49] = 822407971;
		key32[50] = 845580314;
		key32[51] = 732898268;
		key32[52] = 749189849;
		key32[53] = 950120437;
		key32[54] = 268324916;
		key32[55] = 554910572;
		key32[56] = 369245918;
		key32[57] = 91050355;
		key32[58] = 922083552;
		key32[59] = 263599383;
		key32[60] = 885674548;
		key32[61] = 894632731;
		key32[62] = 381490855;
		key32[63] = 380688238;
		key32[64] = 591620314;
		key32[65] = 402164392;
		key32[66] = 2990736;
		key32[67] = 127320286;
		key32[68] = 432680423;
		key32[69] = 795892184;
		key32[70] = 735650193;
		key32[71] = 82327475;
		key32[72] = 120325779;
		key32[73] = 444503102;
		key32[74] = 169268647;
		key32[75] = 70590263;
		key32[76] = 829286632;
		key32[77] = 131652415;
		key32[78] = 234923154;
		key32[79] = 658135140;
		key32[80] = 238571593;
		key32[81] = 575008026;
		key32[82] = 981834431;
		key32[83] = 28794070;
		key32[84] = 223908107;
		key32[85] = 647591073;
		key32[86] = 507846703;
		key32[87] = 990488661;
		key32[88] = 220154105;
		key32[89] = 99206299;
		key32[90] = 273557187;
		key32[91] = 660026144;
		key32[92] = 414798615;
		key32[93] = 100156799;
		key32[94] = 71794471;
		key32[95] = 251460504;
		key32[96] = 586640075;
		key32[97] = 36699020;
		key32[98] = 79943194;
		key32[99] = 98562606;
		key32[100] = 737239948;
		key32[101] = 223552171;
		key32[102] = 526954350;
		key32[103] = 800217672;
		key32[104] = 930636801;
		key32[105] = 858509046;
		key32[106] = 548467360;
		key32[107] = 916059784;
		key32[108] = 972795347;
		key32[109] = 906638337;
		key32[110] = 245555473;
		key32[111] = 911006865;
		key32[112] = 225991940;
		key32[113] = 323115973;
		key32[114] = 218417083;
		key32[115] = 883298175;
		key32[116] = 565889083;
		key32[117] = 179819929;
		key32[118] = 143883426;
		key32[119] = 589539056;
		key32[120] = 525048764;
		key32[121] = 334549206;
		key32[122] = 157821544;
		key32[123] = 943070040;
		key32[124] = 204803064;
		key32[125] = 542397761;
		key32[126] = 271066857;
		key32[127] = 188732445;
	}
	
	/**
	 * Returns the key (64 bits) of a specific board position, the positions are between 0 and 31
	 * @param pos
	 * @param boardValue
	 * @return
	 * @throws ZobristException
	 */
	public static long getKey64(int pos, int boardValue) throws ZobristException{
		
		return key64[getPos(pos, boardValue)];
	}
	
	/**
	 * Returns the key (32 bits) of a specific board position, the positions are between 0 and 31
	 * @param pos
	 * @param boardValue
	 * @return
	 * @throws ZobristException
	 */
	public static int getKey32(int pos, int boardValue) throws ZobristException{
		
		return key32[getPos(pos, boardValue)];
	}
	
	public static int getPos(int pos, int boardValue) throws ZobristException{
		
		int i = ++pos * 4;
		
		switch (boardValue) {
		case BoardValues.BLACKMAN:
			i -= 4;
			break;
		case BoardValues.REDMAN:
			i -= 3;
			break;
		case BoardValues.BLACKKING:
			i -= 2;
			break;
		case BoardValues.REDKING:
			i -= 1;
			break;
		default:
			break;
		}
		
		if(i < 0 || i >= 128){
			throw new ZobristException("Posição inexistente");
		}
		
		return i;
		
	}
	
	/*public static long getKey64(Board b){
		
		long key = 0;
		
		for(int i = 0; i < b.getBoard().length; i++){
			
			if(b.getBoard()[i] != BoardValues.EMPTY){
				
				try {
					key = key ^ getKey64(i, b.getBoard()[i]);
					
				} catch (ZobristException e) {
					
					e.printStackTrace();
				}
				
			}
			
		}
		
		System.out.println("key: " + key);
		
		return key;
		
	}*/
	
	/*public static int getKey32(Board b){
		
		int key = 0;
		
		for(int i = 0; i < b.getBoard().length; i++){
			
			if(b.getBoard()[i] != BoardValues.EMPTY){
				
				try {
					key = key ^ getKey32(i, b.getBoard()[i]);
					
				} catch (ZobristException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("key: " + key);
		
		return key;
		
	}
	*/
	//TODO: testar xor... fazer cache com JCS e LinkedHashMap; Criar estrutura like TTable do Gutierrez
	public static void main(String[] args){
		 /*long l1 = 1171106056361138075L;
		 long l2 = 9204715365712158256L;
		 long l3 = 6271745259985844523L;
		 
		 long chave = l1 ^ l2 ^ l3;
		 
		 System.out.println("valor da chave: " + chave);
		 
		 long chave2 = chave ^ l1;
		 
		 System.out.println("valor da chave2: " + chave2);
		 
		 long chave3 = chave2 ^ l2;
		 
		 System.out.println("valor da chave 3: " + chave3);
		 
		 long chave4 = chave3 ^ l3;
		 
		 System.out.println("valor da chave 4: " + chave4);*/
		
		/*try {
			System.out.println("Chave: " + getKey64(31, BoardValues.BLACKMAN));
		} catch (ZobristException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	@Override
	public long getKey64(INode state) {
		
		long key = 0;
		
		Node node = (Node) state;
		
		for(int i = 0; i < node.getBoard().getBoard().length; i++){
			
			if(node.getBoard().getBoard()[i] != BoardValues.EMPTY){
				
				try {
					key = key ^ getKey64(i, node.getBoard().getBoard()[i]);
					
				} catch (ZobristException e) {
					e.printStackTrace();
				}
			}
		}
		
		//System.out.println("key: " + key);
		
		return key;
	}

	@Override
	public int getKey32(INode state) {
		
		int key = 0;
		
		Node node = (Node) state;
		
		for(int i = 0; i < node.getBoard().getBoard().length; i++){
			
			if(node.getBoard().getBoard()[i] != BoardValues.EMPTY){
				
				try {
					key = key ^ getKey32(i, node.getBoard().getBoard()[i]);
					
				} catch (ZobristException e) {
					e.printStackTrace();
				}
			}
		}
		
		//System.out.println("key: " + key);
		
		return key;
	}

}
