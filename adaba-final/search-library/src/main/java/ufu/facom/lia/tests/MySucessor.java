package ufu.facom.lia.tests;

import java.util.ArrayList;
import java.util.List;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.Node;

public class MySucessor implements Sucessor, Cloneable{

	@Override
	public List<INode> getSucessors(INode currentState, Player player) throws SearchException {

		List<INode> sucessors = new ArrayList<INode>();
		
		/*if(currentState.getName().equals("S1")){
			
			Node s2 = new Node("S2");
			Node s15 = new Node("S15");
			
			s2.setParent(currentState);
			s15.setParent(currentState);
			
			s2.setDepth(currentState.getDepth()+1);
			s15.setDepth(currentState.getDepth()+1);
			
			s2.setType(Type.MIN);
			s15.setType(Type.LEAF);
			
			sucessors.add(s2);
			sucessors.add(s15);
			
			if(player.equals(Player.BLACK)){
				s2.setPlayer(Player.RED);
				s15.setPlayer(Player.RED);
			}else{
				s2.setPlayer(Player.BLACK);
				s15.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S2")){
			
			Node s3 = new Node("S3");
			Node s12 = new Node("S12");
			
			s3.setParent(currentState);
			s12.setParent(currentState);
			
			s3.setDepth(currentState.getDepth()+1);
			s12.setDepth(currentState.getDepth()+1);
			
			s3.setType(Type.MAX);
			s12.setType(Type.MAX);
			
			sucessors.add(s3);
			sucessors.add(s12);
			
			if(player.equals(Player.BLACK)){
				s3.setPlayer(Player.RED);
				s12.setPlayer(Player.RED);
			}else{
				s3.setPlayer(Player.BLACK);
				s12.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S3")){
			
			Node s4 = new Node("S4");
			Node s8 = new Node("S8");
			
			s4.setParent(currentState);
			s4.setType(Type.MIN);
			
			s8.setParent(currentState);
			s8.setType(Type.MIN);
			
			s4.setDepth(currentState.getDepth() + 1);
			s8.setDepth(currentState.getDepth() + 1);
			
			sucessors.add(s4);
			sucessors.add(s8);
			
			if(player.equals(Player.BLACK)){
				s4.setPlayer(Player.RED);
				s8.setPlayer(Player.RED);
			}else{
				s4.setPlayer(Player.BLACK);
				s8.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S4")){
			
			Node s5 = new Node("S5");
			Node s6 = new Node("S6");
			Node s7 = new Node("S7");
			
			s5.setParent(currentState);
			s6.setParent(currentState);
			s7.setParent(currentState);
			
			s5.setDepth(currentState.getDepth()+1);
			s6.setDepth(currentState.getDepth()+1);
			s7.setDepth(currentState.getDepth()+1);
			
			s5.setType(Type.LEAF);
			s6.setType(Type.LEAF);
			s7.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s5.setPlayer(Player.RED);
				s6.setPlayer(Player.RED);
				s7.setPlayer(Player.RED);
			}else{
				s5.setPlayer(Player.BLACK);
				s6.setPlayer(Player.BLACK);
				s7.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s5);
			sucessors.add(s6);
			sucessors.add(s7);
			
			currentState.setChildren(sucessors);

		}else if(currentState.getName().equals("S8")){
			
			Node s9 = new Node("S9");
			Node s10 = new Node("S10");
			Node s11 = new Node("S11");
			
			s9.setType(Type.LEAF);
			s10.setType(Type.LEAF);
			s11.setType(Type.LEAF);
			
			s9.setParent(currentState);
			s10.setParent(currentState);
			s11.setParent(currentState);
			
			s9.setDepth(currentState.getDepth()+1);
			s10.setDepth(currentState.getDepth()+1);
			s11.setDepth(currentState.getDepth()+1);
			
			if(player.equals(Player.BLACK)){
				s9.setPlayer(Player.RED);
				s10.setPlayer(Player.RED);
				s11.setPlayer(Player.RED);
			}else{
				s9.setPlayer(Player.BLACK);
				s10.setPlayer(Player.BLACK);
				s11.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s9);
			sucessors.add(s10);
			sucessors.add(s11);
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S12")){
		
			Node s13 =  new Node("S13");
			Node s14 =  new Node("S14");
			
			s13.setEvaluation(0.3f);
			s14.setEvaluation(0.1f);
			
			s13.setDepth(currentState.getDepth()+1);
			s14.setDepth(currentState.getDepth()+1);
			
			if(player.equals(Player.BLACK)){
				s13.setPlayer(Player.RED);
				s14.setPlayer(Player.RED);
			}else{
				s13.setPlayer(Player.BLACK);
				s14.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s13);
			sucessors.add(s14);
			
			currentState.setChildren(sucessors);
			
			s13.setParent(currentState);
			s13.setParent(currentState);
			
			s13.setType(Type.LEAF);
			s14.setType(Type.LEAF);
		
		}*/
		
		return sucessors;
	}

	@Override
	public String getName(INode n, INode parent) {
		return String.valueOf(n.getKey().getKey32());
	}
	
	@Override
	public Sucessor clone() throws CloneNotSupportedException {
		return (Sucessor) super.clone();
	}

}
