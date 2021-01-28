package ufu.facom.lia.tests;

import java.util.ArrayList;
import java.util.List;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.Node;

public class GutierrezSucessor implements Sucessor, Cloneable{

	@Override
	public List<INode> getSucessors(INode currentState, Player player) throws SearchException {
		
		List<INode> sucessors = new ArrayList<INode>();
		/*
		if(currentState.getName().equals("S0")){
			
			Node s1 = new Node("S1");
			Node s2 = new Node("S2");
			Node s3 = new Node("S3");
			
			s1.setParent(currentState);
			s2.setParent(currentState);
			s3.setParent(currentState);
			
			s1.setDepth(currentState.getDepth()+1);
			s2.setDepth(currentState.getDepth()+1);
			s3.setDepth(currentState.getDepth()+1);
			
			s1.setType(Type.MIN);
			s2.setType(Type.MIN);
			s3.setType(Type.MIN);
			
			sucessors.add(s1);
			sucessors.add(s2);
			sucessors.add(s3);
			
			if(player.equals(Player.BLACK)){
				s1.setPlayer(Player.RED);
				s2.setPlayer(Player.RED);
				s3.setPlayer(Player.RED);
			}else{
				s1.setPlayer(Player.BLACK);
				s2.setPlayer(Player.BLACK);
				s3.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S1")){
			
			Node s1 = new Node("S11");
			Node s2 = new Node("S12");
			Node s3 = new Node("S13");
			
			s1.setParent(currentState);
			s2.setParent(currentState);
			s3.setParent(currentState);
			
			s1.setDepth(currentState.getDepth()+1);
			s2.setDepth(currentState.getDepth()+1);
			s3.setDepth(currentState.getDepth()+1);
			
			s1.setType(Type.MIN);
			s2.setType(Type.MIN);
			s3.setType(Type.MIN);
			
			sucessors.add(s1);
			sucessors.add(s2);
			sucessors.add(s3);
			
			if(player.equals(Player.BLACK)){
				s1.setPlayer(Player.RED);
				s2.setPlayer(Player.RED);
				s3.setPlayer(Player.RED);
			}else{
				s1.setPlayer(Player.BLACK);
				s2.setPlayer(Player.BLACK);
				s3.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S2")){
			
			Node s1 = new Node("S21");
			
			s1.setParent(currentState);
			
			s1.setDepth(currentState.getDepth()+1);
			
			s1.setType(Type.MIN);
			
			sucessors.add(s1);
			
			if(player.equals(Player.BLACK)){
				s1.setPlayer(Player.RED);
			}else{
				s1.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S3")){
			
			Node s1 = new Node("S31");
			Node s2 = new Node("S32");
			Node s3 = new Node("S33");
			
			s1.setParent(currentState);
			s2.setParent(currentState);
			s3.setParent(currentState);
			
			s1.setDepth(currentState.getDepth()+1);
			s2.setDepth(currentState.getDepth()+1);
			s3.setDepth(currentState.getDepth()+1);
			
			s1.setType(Type.MIN);
			s2.setType(Type.MIN);
			s3.setType(Type.MIN);
			
			sucessors.add(s1);
			sucessors.add(s2);
			sucessors.add(s3);
			
			if(player.equals(Player.BLACK)){
				s1.setPlayer(Player.RED);
				s2.setPlayer(Player.RED);
				s3.setPlayer(Player.RED);
			}else{
				s1.setPlayer(Player.BLACK);
				s2.setPlayer(Player.BLACK);
				s3.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}
		*/
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
