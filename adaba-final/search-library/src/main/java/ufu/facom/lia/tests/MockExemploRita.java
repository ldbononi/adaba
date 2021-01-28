package ufu.facom.lia.tests;

import java.util.List;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.AlphaBetaFailSoft;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Type;
import ufu.facom.lia.structures.Node;

public class MockExemploRita implements IMock {
	
	public List<INode> getSucessors(){
		
		
		return null;
	}
	
	public INode createMockSimple(){
		
		INode root = new Node(1, 1l);
		root.setType(Type.MAX.getValue());
		root.setDepth(0);
		
		return root;
		
	}
	
	public INode createMock(){
		
		/*INode root = new Node("S1");
		
		root.setDepth(0);
		
		Node s2 = new Node("S2");
		Node s15 = new Node("S15");
		
		s2.setParent(root);
		s15.setParent(root);
		
		s2.setType(Type.MIN);
		s15.setType(Type.LEAF);
		
		s15.setEvaluation(0.5f);
		
		List<INode> childrenRoot = new ArrayList<INode>();
		childrenRoot.add(s2);
		childrenRoot.add(s15);
		
		root.setType(Type.MAX);
		root.setChildren(childrenRoot);
		
		Node s3 = new Node("S3");
		Node s12 = new Node("S12");
		
		s3.setParent(s2);
		s12.setParent(s2);
		
		s3.setType(Type.MAX);
		s12.setType(Type.MAX);
		
		List<INode> childrenS2 = new ArrayList<INode>();
		childrenS2.add(s3);
		childrenS2.add(s12);
		
		s2.setChildren(childrenS2);
		
		Node s4 = new Node("S4");
		Node s8 = new Node("S8");
		
		s4.setParent(s3);
		s4.setType(Type.MIN);
		
		s8.setParent(s3);
		s8.setType(Type.MIN);
		
		List<INode> childrenS3 = new ArrayList<INode>();
		childrenS3.add(s4);
		childrenS3.add(s8);
		
		s3.setChildren(childrenS3);
		
		Node s5 = new Node("S5");
		Node s6 = new Node("S6");
		Node s7 = new Node("S7");
		
		s5.setParent(s4);
		s6.setParent(s4);
		s7.setParent(s4);
		
		s5.setType(Type.LEAF);
		s6.setType(Type.LEAF);
		s7.setType(Type.LEAF);
		
		List<INode> childrenS4 = new ArrayList<INode>();
		childrenS4.add(s5);
		childrenS4.add(s6);
		childrenS4.add(s7);
		
		s4.setChildren(childrenS4);
		
		s5.setEvaluation(0.3f);
		s6.setEvaluation(0.7f);
		s7.setEvaluation(0.2f);
		
		Node s9 = new Node("S9");
		Node s10 = new Node("S10");
		Node s11 = new Node("S11");
		
		s9.setType(Type.LEAF);
		s10.setType(Type.LEAF);
		s11.setType(Type.LEAF);
		
		s9.setParent(s8);
		s10.setParent(s8);
		s11.setParent(s8);
		
		List<INode> childrenS8 = new ArrayList<INode>();
		childrenS8.add(s9);
		childrenS8.add(s10);
		childrenS8.add(s11);
		
		s8.setChildren(childrenS8);
		
		s9.setEvaluation(0.1f);
		s10.setEvaluation(0.5f);
		s11.setEvaluation(0.2f);
		
		Node s13 =  new Node("S13");
		Node s14 =  new Node("S14");
		
		s13.setEvaluation(0.3f);
		s14.setEvaluation(0.1f);
		
		List<INode> childrenS12 = new ArrayList<INode>();
		
		childrenS12.add(s13);
		childrenS12.add(s14);
		
		s12.setChildren(childrenS12);
		
		s13.setParent(s12);
		s13.setParent(s12);
		
		s13.setType(Type.LEAF);
		s14.setType(Type.LEAF);*/
		
		
		//return root;
		
		return null;
		
	}
	
	public static void main(String[] args) throws SearchException {
		MockExemploRita mck = new MockExemploRita();
		INode n = mck.createMock();
		
		AlphaBetaFailSoft ab = new AlphaBetaFailSoft(new MyEvaluation(), new MySucessor());
		
		float result = ab.alphaBeta(n,null, 4, (Float.MAX_VALUE * (-1)), Float.MAX_VALUE, Player.BLACK);
		
		System.out.println("Resultado: " + result);
		
	}

}
