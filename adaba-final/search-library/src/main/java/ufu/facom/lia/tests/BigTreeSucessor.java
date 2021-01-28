package ufu.facom.lia.tests;

import java.util.ArrayList;
import java.util.List;

import ufu.facom.lia.exceptions.SearchException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.search.interfaces.Player;
import ufu.facom.lia.search.interfaces.Sucessor;

public class BigTreeSucessor implements Sucessor, Cloneable{

	@Override
	public List<INode> getSucessors(INode currentState, Player player) throws SearchException {

		List<INode> sucessors = new ArrayList<INode>();
		
		/*if(currentState.getKey32() == 1){
			
			Node s2 = new Node(2, 2l);
			Node s3 = new Node(3, 3l);
			
			s2.setParentKey64(currentState.getKey64());
			s3.setParentKey64(currentState.getKey64());
			
			s2.setDepth(currentState.getDepth()+1);
			s3.setDepth(currentState.getDepth()+1);
			
			s2.setType(Type.MIN);
			s3.setType(Type.MIN);
			
			sucessors.add(s2);
			sucessors.add(s3);
			
			if(player.equals(Player.BLACK)){
				s2.setPlayer(Player.RED);
				s3.setPlayer(Player.RED);
			}else{
				s2.setPlayer(Player.BLACK);
				s3.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 2){
			
			Node s4 = new Node(4, 4);
			Node s5 = new Node(5, 5);
			
			s4.setParentKey64(currentState.getKey64());
			s5.setParentKey64(currentState.getKey64());
			
			s4.setDepth(currentState.getDepth()+1);
			s5.setDepth(currentState.getDepth()+1);
			
			s4.setType(Type.MAX);
			s5.setType(Type.MAX);
			
			sucessors.add(s4);
			sucessors.add(s5);
			
			if(player.equals(Player.BLACK)){
				s4.setPlayer(Player.RED);
				s5.setPlayer(Player.RED);
			}else{
				s4.setPlayer(Player.BLACK);
				s5.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 3){
			
			Node s6 = new Node(6, 6l);
			Node s7 = new Node(7 , 7l);
			
			s6.setParentKey64(currentState.getKey64());
			s6.setType(Type.MAX);
			
			s7.setParentKey64(currentState.getKey64());
			s7.setType(Type.MAX);
			
			s6.setDepth(currentState.getDepth() + 1);
			s7.setDepth(currentState.getDepth() + 1);
			
			sucessors.add(s6);
			sucessors.add(s7);
			
			if(player.equals(Player.BLACK)){
				s6.setPlayer(Player.RED);
				s7.setPlayer(Player.RED);
			}else{
				s6.setPlayer(Player.BLACK);
				s7.setPlayer(Player.BLACK);
			}
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 4){
			
			Node s8 = new Node(8, 8l);
			Node s9 = new Node(9, 9l);
			
			s8.setParentKey64(currentState.getKey64());
			s9.setParentKey64(currentState.getKey64());
			
			s8.setDepth(currentState.getDepth()+1);
			s9.setDepth(currentState.getDepth()+1);
			
			s8.setType(Type.MIN);
			s9.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s8.setPlayer(Player.RED);
				s9.setPlayer(Player.RED);
			}else{
				s8.setPlayer(Player.BLACK);
				s9.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s8);
			sucessors.add(s9);
			
			currentState.setChildren(sucessors);

		}else if(currentState.getKey32() == 6){
			
			Node s10 = new Node(10, 10l);
			
			s10.setType(Type.MIN);
			
			s10.setParentKey64(currentState.getKey64());
			
			s10.setDepth(currentState.getDepth()+1);
			
			if(player.equals(Player.BLACK)){
				s10.setPlayer(Player.RED);
			}else{
				s10.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s10);
			
			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 7){
		
			Node s11 =  new Node(11, 11l);
			Node s12 =  new Node(12, 12l);
			
			s11.setDepth(currentState.getDepth()+1);
			s12.setDepth(currentState.getDepth()+1);
			
			s11.setParentKey64(currentState.getKey64());
			s12.setParentKey64(currentState.getKey64());
			
			s11.setType(Type.MIN);
			s12.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s11.setPlayer(Player.RED);
				s12.setPlayer(Player.RED);
			}else{
				s11.setPlayer(Player.BLACK);
				s12.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s11);
			sucessors.add(s12);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 8){
		
			Node s13 =  new Node(13, 13l);
			Node s14 =  new Node(14, 14l);
			
			s13.setDepth(currentState.getDepth()+1);
			s14.setDepth(currentState.getDepth()+1);
			
			s13.setParentKey64(currentState.getKey64());
			s14.setParentKey64(currentState.getKey64());
			
			s13.setType(Type.MAX);
			s14.setType(Type.MAX);
			
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
			
		}else if(currentState.getKey32() == 9){
		
			Node s15 =  new Node(15, 15l);
			Node s16 =  new Node(16, 16l);
			
			s15.setDepth(currentState.getDepth()+1);
			s16.setDepth(currentState.getDepth()+1);
			
			s15.setParentKey64(currentState.getKey64());
			s16.setParentKey64(currentState.getKey64());
			
			s15.setType(Type.MAX);
			s16.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s15.setPlayer(Player.RED);
				s16.setPlayer(Player.RED);
			}else{
				s15.setPlayer(Player.BLACK);
				s16.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s15);
			sucessors.add(s16);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 10){
		
			Node s17 =  new Node(17, 17l);
			
			s17.setDepth(currentState.getDepth()+1);
			
			s17.setParentKey64(currentState.getKey64());
			
			s17.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s17.setPlayer(Player.RED);
			}else{
				s17.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s17);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 11){
		
			Node s18 =  new Node(18, 18l);
			
			s18.setDepth(currentState.getDepth()+1);
			
			s18.setParentKey64(currentState.getKey64());
			
			s18.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s18.setPlayer(Player.RED);
			}else{
				s18.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s18);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 12){
		
			Node s19 =  new Node(19, 19l);
			Node s20 =  new Node(20, 20l);
			
			s19.setDepth(currentState.getDepth()+1);
			s20.setDepth(currentState.getDepth()+1);
			
			s19.setParentKey64(currentState.getKey64());
			s20.setParentKey64(currentState.getKey64());
			
			s19.setType(Type.MAX);
			s20.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s19.setPlayer(Player.RED);
				s20.setPlayer(Player.RED);
			}else{
				s19.setPlayer(Player.BLACK);
				s20.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s19);
			sucessors.add(s20);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 13){
		
			Node s21 =  new Node(21, 21l);
			Node s22 =  new Node(22, 22l);
			
			s21.setDepth(currentState.getDepth()+1);
			s22.setDepth(currentState.getDepth()+1);
			
			s21.setParentKey64(currentState.getKey64());
			s22.setParentKey64(currentState.getKey64());
			
			s21.setType(Type.MIN);
			s22.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s21.setPlayer(Player.RED);
				s22.setPlayer(Player.RED);
			}else{
				s21.setPlayer(Player.BLACK);
				s22.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s21);
			sucessors.add(s22);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 14){
		
			Node s23 =  new Node(23, 23l);
			Node s24 =  new Node(24, 24l);
			
			s23.setDepth(currentState.getDepth()+1);
			s24.setDepth(currentState.getDepth()+1);
			
			s23.setParentKey64(currentState.getKey64());
			s24.setParentKey64(currentState.getKey64());
			
			s23.setType(Type.MIN);
			s24.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s23.setPlayer(Player.RED);
				s24.setPlayer(Player.RED);
			}else{
				s23.setPlayer(Player.BLACK);
				s24.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s23);
			sucessors.add(s24);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 15){
		
			Node s25 =  new Node(25, 25l);
			Node s26 =  new Node(25, 26l);
			
			s25.setDepth(currentState.getDepth()+1);
			s26.setDepth(currentState.getDepth()+1);
			
			s25.setParentKey64(currentState.getKey64());
			s26.setParentKey64(currentState.getKey64());
			
			s25.setType(Type.LEAF);
			s26.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s25.setPlayer(Player.RED);
				s26.setPlayer(Player.RED);
			}else{
				s25.setPlayer(Player.BLACK);
				s26.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s25);
			sucessors.add(s26);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 16){
		
			Node s27 =  new Node(27, 27l);
			
			s27.setDepth(currentState.getDepth()+1);
			
			s27.setParentKey64(currentState.getKey64());
			
			s27.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s27.setPlayer(Player.RED);
			}else{
				s27.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s27);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getKey32() == 17){
		
			Node s28 =  new Node(28, 28l);
			Node s29 =  new Node(29, 29l);
			
			s28.setDepth(currentState.getDepth()+1);
			s29.setDepth(currentState.getDepth()+1);
			
			s28.setParentKey64(currentState.getKey64());
			s29.setParentKey64(currentState.getKey64());
			
			s28.setType(Type.MIN);
			s29.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s28.setPlayer(Player.RED);
				s29.setPlayer(Player.RED);
			}else{
				s28.setPlayer(Player.BLACK);
				s29.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s28);
			sucessors.add(s29);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S18")){
		
			Node s30 =  new Node("S30");
			Node s31 =  new Node("S31");
			
			s30.setDepth(currentState.getDepth()+1);
			s31.setDepth(currentState.getDepth()+1);
			
			s30.setParentKey64(currentState.getKey64());
			s31.setParentKey64(currentState.getKey64());
			
			s30.setType(Type.MIN);
			s31.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s30.setPlayer(Player.RED);
				s31.setPlayer(Player.RED);
			}else{
				s30.setPlayer(Player.BLACK);
				s31.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s30);
			sucessors.add(s31);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S19")){
		
			Node s32 =  new Node("S32");
			Node s33 =  new Node("S33");
			
			s32.setDepth(currentState.getDepth()+1);
			s33.setDepth(currentState.getDepth()+1);
			
			s32.setParentKey64(currentState.getKey64());
			s33.setParentKey64(currentState.getKey64());
			
			s32.setType(Type.LEAF);
			s33.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s32.setPlayer(Player.RED);
				s33.setPlayer(Player.RED);
			}else{
				s32.setPlayer(Player.BLACK);
				s33.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s32);
			sucessors.add(s33);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S20")){
		
			Node s34 =  new Node("S34");
			
			s34.setDepth(currentState.getDepth()+1);
			
			s34.setParentKey64(currentState.getKey64());
			
			s34.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s34.setPlayer(Player.RED);
			}else{
				s34.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s34);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S21")){
		
			Node s35 =  new Node("S35");
			Node s36 =  new Node("S36");
			
			s35.setDepth(currentState.getDepth()+1);
			s36.setDepth(currentState.getDepth()+1);
			
			s35.setParentKey64(currentState.getKey64());
			s36.setParentKey64(currentState.getKey64());
			
			s35.setType(Type.MAX);
			s36.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s35.setPlayer(Player.RED);
				s36.setPlayer(Player.RED);
			}else{
				s35.setPlayer(Player.BLACK);
				s36.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s35);
			sucessors.add(s36);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S22")){
		
			Node s37 =  new Node("S37");
			Node s38 =  new Node("S38");
			
			s37.setDepth(currentState.getDepth()+1);
			s38.setDepth(currentState.getDepth()+1);
			
			s37.setParentKey64(currentState.getKey64());
			s38.setParentKey64(currentState.getKey64());
			
			s37.setType(Type.MAX);
			s38.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s37.setPlayer(Player.RED);
				s38.setPlayer(Player.RED);
			}else{
				s37.setPlayer(Player.BLACK);
				s38.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s37);
			sucessors.add(s38);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S23")){
		
			Node s39 =  new Node("S39");
			
			s39.setDepth(currentState.getDepth()+1);
			
			s39.setParentKey64(currentState.getKey64());
			
			s39.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s39.setPlayer(Player.RED);
			}else{
				s39.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s39);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S27")){
		
			Node s40 =  new Node("S40");
			Node s41 =  new Node("S41");
			
			s40.setDepth(currentState.getDepth()+1);
			s41.setDepth(currentState.getDepth()+1);
			
			s40.setParentKey64(currentState.getKey64());
			s41.setParentKey64(currentState.getKey64());
			
			s40.setType(Type.MAX);
			s41.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s40.setPlayer(Player.RED);
				s41.setPlayer(Player.RED);
			}else{
				s40.setPlayer(Player.BLACK);
				s41.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s40);
			sucessors.add(s41);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S28")){
		
			Node s42 =  new Node("S42");
			Node s43 =  new Node("S43");
			
			s42.setDepth(currentState.getDepth()+1);
			s43.setDepth(currentState.getDepth()+1);
			
			s42.setParentKey64(currentState.getKey64());
			s43.setParentKey64(currentState.getKey64());
			
			s42.setType(Type.MAX);
			s43.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s42.setPlayer(Player.RED);
				s43.setPlayer(Player.RED);
			}else{
				s42.setPlayer(Player.BLACK);
				s43.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s42);
			sucessors.add(s43);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S29")){
		
			Node s44 =  new Node("S44");
			
			s44.setDepth(currentState.getDepth()+1);
			
			s44.setParentKey64(currentState.getKey64());
			
			s44.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s44.setPlayer(Player.RED);
			}else{
				s44.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s44);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S30")){
		
			Node s45 =  new Node("S45");
			
			s45.setDepth(currentState.getDepth()+1);
			
			s45.setParentKey64(currentState.getKey64());
			
			s45.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s45.setPlayer(Player.RED);
			}else{
				s45.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s45);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S34")){
		
			Node s46 =  new Node("S46");
			Node s47 =  new Node("S47");
			
			s46.setDepth(currentState.getDepth()+1);
			s47.setDepth(currentState.getDepth()+1);
			
			s46.setParentKey64(currentState.getKey64());
			s47.setParentKey64(currentState.getKey64());
			
			s46.setType(Type.MAX);
			s47.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s46.setPlayer(Player.RED);
				s47.setPlayer(Player.RED);
			}else{
				s46.setPlayer(Player.BLACK);
				s47.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s46);
			sucessors.add(s47);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S35")){
		
			Node s48 =  new Node("S48");
			Node s49 =  new Node("S49");
			
			s48.setDepth(currentState.getDepth()+1);
			s49.setDepth(currentState.getDepth()+1);
			
			s48.setParentKey64(currentState.getKey64());
			s49.setParentKey64(currentState.getKey64());
			
			s48.setType(Type.MIN);
			s49.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s48.setPlayer(Player.RED);
				s49.setPlayer(Player.RED);
			}else{
				s48.setPlayer(Player.BLACK);
				s49.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s48);
			sucessors.add(s49);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S36")){
		
			Node s50 =  new Node("S50");
			Node s51 =  new Node("S51");
			
			s50.setDepth(currentState.getDepth()+1);
			s51.setDepth(currentState.getDepth()+1);
			
			s50.setParentKey64(currentState.getKey64());
			s51.setParentKey64(currentState.getKey64());
			
			s50.setType(Type.MIN);
			s51.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s50.setPlayer(Player.RED);
				s51.setPlayer(Player.RED);
			}else{
				s50.setPlayer(Player.BLACK);
				s51.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s50);
			sucessors.add(s51);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S37")){
		
			Node s52 =  new Node("S52");
			Node s53 =  new Node("S53");
			
			s52.setDepth(currentState.getDepth()+1);
			s53.setDepth(currentState.getDepth()+1);
			
			s52.setParentKey64(currentState.getKey64());
			s53.setParentKey64(currentState.getKey64());
			
			s52.setType(Type.MIN);
			s53.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s52.setPlayer(Player.RED);
				s53.setPlayer(Player.RED);
			}else{
				s52.setPlayer(Player.BLACK);
				s53.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s52);
			sucessors.add(s53);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S39")){
		
			Node s54 =  new Node("S54");
			Node s55 =  new Node("S55");
			
			s54.setDepth(currentState.getDepth()+1);
			s55.setDepth(currentState.getDepth()+1);
			
			s54.setParentKey64(currentState.getKey64());
			s55.setParentKey64(currentState.getKey64());
			
			s54.setType(Type.MIN);
			s55.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s54.setPlayer(Player.RED);
				s55.setPlayer(Player.RED);
			}else{
				s54.setPlayer(Player.BLACK);
				s55.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s54);
			sucessors.add(s55);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S40")){
		
			Node s56 =  new Node("S56");
			Node s57 =  new Node("S57");
			
			s56.setDepth(currentState.getDepth()+1);
			s57.setDepth(currentState.getDepth()+1);
			
			s56.setParentKey64(currentState.getKey64());
			s57.setParentKey64(currentState.getKey64());
			
			s56.setType(Type.MIN);
			s57.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s56.setPlayer(Player.RED);
				s57.setPlayer(Player.RED);
			}else{
				s56.setPlayer(Player.BLACK);
				s57.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s56);
			sucessors.add(s57);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S41")){
		
			Node s58 =  new Node("S58");
			
			s58.setDepth(currentState.getDepth()+1);
			
			s58.setParentKey64(currentState.getKey64());
			
			s58.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s58.setPlayer(Player.RED);
			}else{
				s58.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s58);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S42")){
		
			Node s59 =  new Node("S59");
			
			s59.setDepth(currentState.getDepth()+1);
			
			s59.setParentKey64(currentState.getKey64());
			
			s59.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s59.setPlayer(Player.RED);
			}else{
				s59.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s59);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S43")){
		
			Node s60 =  new Node("S60");
			Node s61 =  new Node("S61");
			
			s60.setDepth(currentState.getDepth()+1);
			s61.setDepth(currentState.getDepth()+1);
			
			s60.setParentKey64(currentState.getKey64());
			s61.setParentKey64(currentState.getKey64());
			
			s60.setType(Type.LEAF);
			s61.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s60.setPlayer(Player.RED);
				s61.setPlayer(Player.RED);
			}else{
				s60.setPlayer(Player.BLACK);
				s61.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s60);
			sucessors.add(s61);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S44")){
		
			Node s62 =  new Node("S62");
			Node s63 =  new Node("S63");
			
			s62.setDepth(currentState.getDepth()+1);
			s63.setDepth(currentState.getDepth()+1);
			
			s62.setParentKey64(currentState.getKey64());
			s63.setParentKey64(currentState.getKey64());
			
			s62.setType(Type.LEAF);
			s63.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s62.setPlayer(Player.RED);
				s63.setPlayer(Player.RED);
			}else{
				s62.setPlayer(Player.BLACK);
				s63.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s62);
			sucessors.add(s63);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S45")){
		
			Node s64 =  new Node("S64");
			Node s65 =  new Node("S65");
			
			s64.setDepth(currentState.getDepth()+1);
			s65.setDepth(currentState.getDepth()+1);
			
			s64.setParentKey64(currentState.getKey64());
			s65.setParentKey64(currentState.getKey64());
			
			s64.setType(Type.MIN);
			s65.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s64.setPlayer(Player.RED);
				s65.setPlayer(Player.RED);
			}else{
				s64.setPlayer(Player.BLACK);
				s65.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s64);
			sucessors.add(s65);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S46")){
		
			Node s66 =  new Node("S66");
			Node s67 =  new Node("S67");
			
			s66.setDepth(currentState.getDepth()+1);
			s67.setDepth(currentState.getDepth()+1);
			
			s66.setParentKey64(currentState.getKey64());
			s67.setParentKey64(currentState.getKey64());
			
			s66.setType(Type.MIN);
			s67.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s66.setPlayer(Player.RED);
				s67.setPlayer(Player.RED);
			}else{
				s66.setPlayer(Player.BLACK);
				s67.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s66);
			sucessors.add(s67);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S47")){
		
			Node s68 =  new Node("S68");
			
			s68.setDepth(currentState.getDepth()+1);
			
			s68.setParentKey64(currentState.getKey64());
			
			s68.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s68.setPlayer(Player.RED);
			}else{
				s68.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s68);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S48")){
		
			Node s69 =  new Node("S69");
			Node s70 =  new Node("S70");
			
			s69.setDepth(currentState.getDepth()+1);
			s70.setDepth(currentState.getDepth()+1);
			
			s69.setParentKey64(currentState.getKey64());
			s70.setParentKey64(currentState.getKey64());
			
			s69.setType(Type.MAX);
			s70.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s69.setPlayer(Player.RED);
				s70.setPlayer(Player.RED);
			}else{
				s69.setPlayer(Player.BLACK);
				s70.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s69);
			sucessors.add(s70);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S49")){
		
			Node s71 =  new Node("S71");
			Node s72 =  new Node("S72");
			
			s71.setDepth(currentState.getDepth()+1);
			s72.setDepth(currentState.getDepth()+1);
			
			s71.setParentKey64(currentState.getKey64());
			s72.setParentKey64(currentState.getKey64());
			
			s71.setType(Type.MAX);
			s72.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s71.setPlayer(Player.RED);
				s72.setPlayer(Player.RED);
			}else{
				s71.setPlayer(Player.BLACK);
				s72.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s71);
			sucessors.add(s72);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S50")){
		
			Node s73 =  new Node("S73");
			
			s73.setDepth(currentState.getDepth()+1);
			
			s73.setParentKey64(currentState.getKey64());
			
			s73.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s73.setPlayer(Player.RED);
			}else{
				s73.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s73);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S51")){
		
			Node s74 =  new Node("S74");
			Node s75 =  new Node("S75");
			
			s74.setDepth(currentState.getDepth()+1);
			s75.setDepth(currentState.getDepth()+1);
			
			s74.setParentKey64(currentState.getKey64());
			s75.setParentKey64(currentState.getKey64());
			
			s74.setType(Type.MAX);
			s75.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s74.setPlayer(Player.RED);
				s75.setPlayer(Player.RED);
			}else{
				s74.setPlayer(Player.BLACK);
				s75.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s74);
			sucessors.add(s75);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S52")){
		
			Node s76 =  new Node("S76");
			Node s77 =  new Node("S77");
			
			s76.setDepth(currentState.getDepth()+1);
			s77.setDepth(currentState.getDepth()+1);
			
			s76.setParentKey64(currentState.getKey64());
			s77.setParentKey64(currentState.getKey64());
			
			s76.setType(Type.MAX);
			s77.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s76.setPlayer(Player.RED);
				s77.setPlayer(Player.RED);
			}else{
				s76.setPlayer(Player.BLACK);
				s77.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s76);
			sucessors.add(s77);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S53")){
		
			Node s78 =  new Node("S78");
			Node s79 =  new Node("S79");
			
			s78.setDepth(currentState.getDepth()+1);
			s79.setDepth(currentState.getDepth()+1);
			
			s78.setParentKey64(currentState.getKey64());
			s79.setParentKey64(currentState.getKey64());
			
			s78.setType(Type.MAX);
			s79.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s78.setPlayer(Player.RED);
				s79.setPlayer(Player.RED);
			}else{
				s78.setPlayer(Player.BLACK);
				s79.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s78);
			sucessors.add(s79);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S54")){
		
			Node s80 =  new Node("S80");
			Node s81 =  new Node("S81");
			
			s80.setDepth(currentState.getDepth()+1);
			s81.setDepth(currentState.getDepth()+1);
			
			s80.setParentKey64(currentState.getKey64());
			s81.setParentKey64(currentState.getKey64());
			
			s80.setType(Type.MAX);
			s81.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s80.setPlayer(Player.RED);
				s81.setPlayer(Player.RED);
			}else{
				s80.setPlayer(Player.BLACK);
				s81.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s80);
			sucessors.add(s81);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S55")){
		
			Node s82 =  new Node("S82");
			Node s83 =  new Node("S83");
			
			s82.setDepth(currentState.getDepth()+1);
			s83.setDepth(currentState.getDepth()+1);
			
			s82.setParentKey64(currentState.getKey64());
			s83.setParentKey64(currentState.getKey64());
			
			s82.setType(Type.MAX);
			s83.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s82.setPlayer(Player.RED);
				s83.setPlayer(Player.RED);
			}else{
				s82.setPlayer(Player.BLACK);
				s83.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s82);
			sucessors.add(s83);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S56")){
		
			Node s84 =  new Node("S84");
			
			s84.setDepth(currentState.getDepth()+1);
			
			s84.setParentKey64(currentState.getKey64());
			
			s84.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s84.setPlayer(Player.RED);
			}else{
				s84.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s84);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S57")){
		
			Node s85 =  new Node("S85");
			Node s86 =  new Node("S86");
			
			s85.setDepth(currentState.getDepth()+1);
			s86.setDepth(currentState.getDepth()+1);
			
			s85.setParentKey64(currentState.getKey64());
			s86.setParentKey64(currentState.getKey64());
			
			s85.setType(Type.LEAF);
			s86.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s85.setPlayer(Player.RED);
				s86.setPlayer(Player.RED);
			}else{
				s85.setPlayer(Player.BLACK);
				s86.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s85);
			sucessors.add(s86);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S58")){
		
			Node s87 =  new Node("S87");
			Node s88 =  new Node("S88");
			
			s87.setDepth(currentState.getDepth()+1);
			s88.setDepth(currentState.getDepth()+1);
			
			s87.setParentKey64(currentState.getKey64());
			s88.setParentKey64(currentState.getKey64());
			
			s87.setType(Type.LEAF);
			s88.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s87.setPlayer(Player.RED);
				s88.setPlayer(Player.RED);
			}else{
				s87.setPlayer(Player.BLACK);
				s88.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s87);
			sucessors.add(s88);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S64")){
		
			Node s89 =  new Node("S89");
			Node s90 =  new Node("S90");
			
			s89.setDepth(currentState.getDepth()+1);
			s90.setDepth(currentState.getDepth()+1);
			
			s89.setParentKey64(currentState.getKey64());
			s90.setParentKey64(currentState.getKey64());
			
			s89.setType(Type.MAX);
			s90.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s89.setPlayer(Player.RED);
				s90.setPlayer(Player.RED);
			}else{
				s89.setPlayer(Player.BLACK);
				s90.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s89);
			sucessors.add(s90);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S65")){
		
			Node s91 =  new Node("S91");
			Node s92 =  new Node("S92");
			
			s91.setDepth(currentState.getDepth()+1);
			s92.setDepth(currentState.getDepth()+1);
			
			s91.setParentKey64(currentState.getKey64());
			s92.setParentKey64(currentState.getKey64());
			
			s91.setType(Type.MAX);
			s92.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s91.setPlayer(Player.RED);
				s92.setPlayer(Player.RED);
			}else{
				s91.setPlayer(Player.BLACK);
				s92.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s91);
			sucessors.add(s92);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S66")){
		
			Node s93 =  new Node("S93");
			
			s93.setDepth(currentState.getDepth()+1);
			
			s93.setParentKey64(currentState.getKey64());
			
			s93.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s93.setPlayer(Player.RED);
			}else{
				s93.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s93);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S67")){
		
			Node s94 =  new Node("S94");
			Node s95 =  new Node("S95");
			
			s94.setDepth(currentState.getDepth()+1);
			s95.setDepth(currentState.getDepth()+1);
			
			s94.setParentKey64(currentState.getKey64());
			s95.setParentKey64(currentState.getKey64());
			
			s94.setType(Type.LEAF);
			s95.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s94.setPlayer(Player.RED);
				s95.setPlayer(Player.RED);
			}else{
				s94.setPlayer(Player.BLACK);
				s95.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s94);
			sucessors.add(s95);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S68")){
		
			Node s96 =  new Node("S96");
			Node s97 =  new Node("S97");
			
			s96.setDepth(currentState.getDepth()+1);
			s97.setDepth(currentState.getDepth()+1);
			
			s96.setParentKey64(currentState.getKey64());
			s97.setParentKey64(currentState.getKey64());
			
			s96.setType(Type.LEAF);
			s97.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s96.setPlayer(Player.RED);
				s97.setPlayer(Player.RED);
			}else{
				s96.setPlayer(Player.BLACK);
				s97.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s96);
			sucessors.add(s97);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S69")){
		
			Node s98 =  new Node("S98");
			Node s99 =  new Node("S99");
			
			s98.setDepth(currentState.getDepth()+1);
			s99.setDepth(currentState.getDepth()+1);
			
			s98.setParentKey64(currentState.getKey64());
			s99.setParentKey64(currentState.getKey64());
			
			s98.setType(Type.MIN);
			s99.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s98.setPlayer(Player.RED);
				s99.setPlayer(Player.RED);
			}else{
				s98.setPlayer(Player.BLACK);
				s99.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s98);
			sucessors.add(s99);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S70")){
		
			Node s100 =  new Node("S100");
			Node s101 =  new Node("S101");
			
			s100.setDepth(currentState.getDepth()+1);
			s101.setDepth(currentState.getDepth()+1);
			
			s100.setParentKey64(currentState.getKey64());
			s101.setParentKey64(currentState.getKey64());
			
			s100.setType(Type.MIN);
			s101.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s100.setPlayer(Player.RED);
				s101.setPlayer(Player.RED);
			}else{
				s100.setPlayer(Player.BLACK);
				s101.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s100);
			sucessors.add(s101);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S71")){
		
			Node s102 =  new Node("S102");
			Node s103 =  new Node("S103");
			
			s102.setDepth(currentState.getDepth()+1);
			s103.setDepth(currentState.getDepth()+1);
			
			s102.setParentKey64(currentState.getKey64());
			s103.setParentKey64(currentState.getKey64());
			
			s102.setType(Type.MIN);
			s103.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s102.setPlayer(Player.RED);
				s103.setPlayer(Player.RED);
			}else{
				s102.setPlayer(Player.BLACK);
				s103.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s102);
			sucessors.add(s103);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S73")){
		
			Node s104 =  new Node("S104");
			Node s105 =  new Node("S105");
			
			s104.setDepth(currentState.getDepth()+1);
			s105.setDepth(currentState.getDepth()+1);
			
			s104.setParentKey64(currentState.getKey64());
			s105.setParentKey64(currentState.getKey64());
			
			s104.setType(Type.MIN);
			s105.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s104.setPlayer(Player.RED);
				s105.setPlayer(Player.RED);
			}else{
				s104.setPlayer(Player.BLACK);
				s105.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s104);
			sucessors.add(s105);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S74")){
		
			Node s106 =  new Node("S106");
			
			s106.setDepth(currentState.getDepth()+1);
			
			s106.setParentKey64(currentState.getKey64());
			
			s106.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s106.setPlayer(Player.RED);
			}else{
				s106.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s106);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S75")){
		
			Node s107 =  new Node("S107");
			Node s108 =  new Node("S108");
			
			s107.setDepth(currentState.getDepth()+1);
			s108.setDepth(currentState.getDepth()+1);
			
			s107.setParentKey64(currentState.getKey64());
			s108.setParentKey64(currentState.getKey64());
			
			s107.setType(Type.MIN);
			s108.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s107.setPlayer(Player.RED);
				s108.setPlayer(Player.RED);
			}else{
				s107.setPlayer(Player.BLACK);
				s108.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s107);
			sucessors.add(s108);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S76")){
		
			Node s109 =  new Node("S109");
			Node s110 =  new Node("S110");
			
			s109.setDepth(currentState.getDepth()+1);
			s110.setDepth(currentState.getDepth()+1);
			
			s109.setParentKey64(currentState.getKey64());
			s110.setParentKey64(currentState.getKey64());
			
			s109.setType(Type.MIN);
			s110.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s109.setPlayer(Player.RED);
				s110.setPlayer(Player.RED);
			}else{
				s109.setPlayer(Player.BLACK);
				s110.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s109);
			sucessors.add(s110);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S77")){
		
			Node s111 =  new Node("S111");
			Node s112 =  new Node("S112");
			
			s111.setDepth(currentState.getDepth()+1);
			s112.setDepth(currentState.getDepth()+1);
			
			s111.setParentKey64(currentState.getKey64());
			s112.setParentKey64(currentState.getKey64());
			
			s111.setType(Type.MIN);
			s112.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s111.setPlayer(Player.RED);
				s112.setPlayer(Player.RED);
			}else{
				s111.setPlayer(Player.BLACK);
				s112.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s111);
			sucessors.add(s112);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S78")){
		
			Node s113 =  new Node("S113");
			Node s114 =  new Node("S114");
			
			s113.setDepth(currentState.getDepth()+1);
			s114.setDepth(currentState.getDepth()+1);
			
			s113.setParentKey64(currentState.getKey64());
			s114.setParentKey64(currentState.getKey64());
			
			s113.setType(Type.MIN);
			s114.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s113.setPlayer(Player.RED);
				s114.setPlayer(Player.RED);
			}else{
				s113.setPlayer(Player.BLACK);
				s114.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s113);
			sucessors.add(s114);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S79")){
		
			Node s115 =  new Node("S115");
			
			s115.setDepth(currentState.getDepth()+1);
			
			s115.setParentKey64(currentState.getKey64());
			
			s115.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s115.setPlayer(Player.RED);
			}else{
				s115.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s115);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S80")){
		
			Node s116 =  new Node("S116");
			Node s117 =  new Node("S117");
			
			s116.setDepth(currentState.getDepth()+1);
			s117.setDepth(currentState.getDepth()+1);
			
			s116.setParentKey64(currentState.getKey64());
			s117.setParentKey64(currentState.getKey64());
			
			s116.setType(Type.MIN);
			s117.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s116.setPlayer(Player.RED);
				s117.setPlayer(Player.RED);
			}else{
				s116.setPlayer(Player.BLACK);
				s117.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s116);
			sucessors.add(s117);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S81")){
		
			Node s118 =  new Node("S118");
			Node s119 =  new Node("S119");
			
			s118.setDepth(currentState.getDepth()+1);
			s119.setDepth(currentState.getDepth()+1);
			
			s118.setParentKey64(currentState.getKey64());
			s119.setParentKey64(currentState.getKey64());
			
			s118.setType(Type.MIN);
			s119.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s118.setPlayer(Player.RED);
				s119.setPlayer(Player.RED);
			}else{
				s118.setPlayer(Player.BLACK);
				s119.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s118);
			sucessors.add(s119);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S82")){
		
			Node s120 =  new Node("S120");
			Node s121 =  new Node("S121");
			
			s120.setDepth(currentState.getDepth()+1);
			s121.setDepth(currentState.getDepth()+1);
			
			s120.setParentKey64(currentState.getKey64());
			s121.setParentKey64(currentState.getKey64());
			
			s120.setType(Type.MIN);
			s121.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s120.setPlayer(Player.RED);
				s121.setPlayer(Player.RED);
			}else{
				s120.setPlayer(Player.BLACK);
				s121.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s120);
			sucessors.add(s121);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S89")){
		
			Node s122 =  new Node("S122");
			Node s123 =  new Node("S123");
			
			s122.setDepth(currentState.getDepth()+1);
			s123.setDepth(currentState.getDepth()+1);
			
			s122.setParentKey64(currentState.getKey64());
			s123.setParentKey64(currentState.getKey64());
			
			s122.setType(Type.MIN);
			s123.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s122.setPlayer(Player.RED);
				s123.setPlayer(Player.RED);
			}else{
				s122.setPlayer(Player.BLACK);
				s123.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s122);
			sucessors.add(s123);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S90")){
		
			Node s124 =  new Node("S124");
			Node s125 =  new Node("S125");
			
			s124.setDepth(currentState.getDepth()+1);
			s125.setDepth(currentState.getDepth()+1);
			
			s124.setParentKey64(currentState.getKey64());
			s125.setParentKey64(currentState.getKey64());
			
			s124.setType(Type.MIN);
			s125.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s124.setPlayer(Player.RED);
				s125.setPlayer(Player.RED);
			}else{
				s124.setPlayer(Player.BLACK);
				s125.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s124);
			sucessors.add(s125);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S91")){
		
			Node s126 =  new Node("S126");
			Node s127 =  new Node("S127");
			
			s126.setDepth(currentState.getDepth()+1);
			s127.setDepth(currentState.getDepth()+1);
			
			s126.setParentKey64(currentState.getKey64());
			s127.setParentKey64(currentState.getKey64());
			
			s126.setType(Type.MIN);
			s127.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s126.setPlayer(Player.RED);
				s127.setPlayer(Player.RED);
			}else{
				s126.setPlayer(Player.BLACK);
				s127.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s126);
			sucessors.add(s127);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S98")){
		
			Node s128 =  new Node("S128");
			Node s129 =  new Node("S129");
			
			s128.setDepth(currentState.getDepth()+1);
			s129.setDepth(currentState.getDepth()+1);
			
			s128.setParentKey64(currentState.getKey64());
			s129.setParentKey64(currentState.getKey64());
			
			s128.setType(Type.MAX);
			s129.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s128.setPlayer(Player.RED);
				s129.setPlayer(Player.RED);
			}else{
				s128.setPlayer(Player.BLACK);
				s129.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s128);
			sucessors.add(s129);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S99")){
		
			Node s130 =  new Node("S130");
			Node s131 =  new Node("S131");
			
			s130.setDepth(currentState.getDepth()+1);
			s131.setDepth(currentState.getDepth()+1);
			
			s130.setParentKey64(currentState.getKey64());
			s131.setParentKey64(currentState.getKey64());
			
			s130.setType(Type.LEAF);
			s131.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s130.setPlayer(Player.RED);
				s131.setPlayer(Player.RED);
			}else{
				s130.setPlayer(Player.BLACK);
				s131.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s130);
			sucessors.add(s131);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S100")){
		
			Node s132 =  new Node("S132");
			
			s132.setDepth(currentState.getDepth()+1);
			
			s132.setParentKey64(currentState.getKey64());
			
			s132.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s132.setPlayer(Player.RED);
			}else{
				s132.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s132);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S101")){
		
			Node s133 =  new Node("S133");
			Node s134 =  new Node("S134");
			
			s133.setDepth(currentState.getDepth()+1);
			s134.setDepth(currentState.getDepth()+1);
			
			s133.setParentKey64(currentState.getKey64());
			s134.setParentKey64(currentState.getKey64());
			
			s133.setType(Type.MAX);
			s134.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s133.setPlayer(Player.RED);
				s134.setPlayer(Player.RED);
			}else{
				s133.setPlayer(Player.BLACK);
				s134.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s133);
			sucessors.add(s134);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S102")){
		
			Node s135 =  new Node("S135");
			Node s136 =  new Node("S136");
			
			s135.setDepth(currentState.getDepth()+1);
			s136.setDepth(currentState.getDepth()+1);
			
			s135.setParentKey64(currentState.getKey64());
			s136.setParentKey64(currentState.getKey64());
			
			s135.setType(Type.MAX);
			s136.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s135.setPlayer(Player.RED);
				s136.setPlayer(Player.RED);
			}else{
				s135.setPlayer(Player.BLACK);
				s136.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s135);
			sucessors.add(s136);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S103")){
		
			Node s137 =  new Node("S137");
			Node s138 =  new Node("S138");
			
			s137.setDepth(currentState.getDepth()+1);
			s138.setDepth(currentState.getDepth()+1);
			
			s137.setParentKey64(currentState.getKey64());
			s138.setParentKey64(currentState.getKey64());
			
			s137.setType(Type.MAX);
			s138.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s137.setPlayer(Player.RED);
				s138.setPlayer(Player.RED);
			}else{
				s137.setPlayer(Player.BLACK);
				s138.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s137);
			sucessors.add(s138);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S104")){
		
			Node s139 =  new Node("S139");
			Node s140 =  new Node("S140");
			
			s139.setDepth(currentState.getDepth()+1);
			s140.setDepth(currentState.getDepth()+1);
			
			s139.setParentKey64(currentState.getKey64());
			s140.setParentKey64(currentState.getKey64());
			
			s139.setType(Type.MAX);
			s140.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s139.setPlayer(Player.RED);
				s140.setPlayer(Player.RED);
			}else{
				s139.setPlayer(Player.BLACK);
				s140.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s139);
			sucessors.add(s140);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S106")){
		
			Node s141 =  new Node("S141");
			Node s142 =  new Node("S142");
			
			s141.setDepth(currentState.getDepth()+1);
			s142.setDepth(currentState.getDepth()+1);
			
			s141.setParentKey64(currentState.getKey64());
			s142.setParentKey64(currentState.getKey64());
			
			s141.setType(Type.MAX);
			s142.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s141.setPlayer(Player.RED);
				s142.setPlayer(Player.RED);
			}else{
				s141.setPlayer(Player.BLACK);
				s142.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s141);
			sucessors.add(s142);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S107")){
		
			Node s143 =  new Node("S143");
			Node s144 =  new Node("S144");
			
			s143.setDepth(currentState.getDepth()+1);
			s144.setDepth(currentState.getDepth()+1);
			
			s143.setParentKey64(currentState.getKey64());
			s144.setParentKey64(currentState.getKey64());
			
			s143.setType(Type.MAX);
			s144.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s143.setPlayer(Player.RED);
				s144.setPlayer(Player.RED);
			}else{
				s143.setPlayer(Player.BLACK);
				s144.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s143);
			sucessors.add(s144);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S108")){
		
			Node s145 =  new Node("S145");
			
			s145.setDepth(currentState.getDepth()+1);
			
			s145.setParentKey64(currentState.getKey64());
			
			s145.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s145.setPlayer(Player.RED);
			}else{
				s145.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s145);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S109")){
		
			Node s146 =  new Node("S146");
			Node s147 =  new Node("S147");
			
			s146.setDepth(currentState.getDepth()+1);
			s147.setDepth(currentState.getDepth()+1);
			
			s146.setParentKey64(currentState.getKey64());
			s147.setParentKey64(currentState.getKey64());
			
			s146.setType(Type.MAX);
			s147.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s146.setPlayer(Player.RED);
				s147.setPlayer(Player.RED);
			}else{
				s146.setPlayer(Player.BLACK);
				s147.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s146);
			sucessors.add(s147);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S110")){
		
			Node s148 =  new Node("S148");
			Node s149 =  new Node("S149");
			
			s148.setDepth(currentState.getDepth()+1);
			s149.setDepth(currentState.getDepth()+1);
			
			s148.setParentKey64(currentState.getKey64());
			s149.setParentKey64(currentState.getKey64());
			
			s148.setType(Type.LEAF);
			s149.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s148.setPlayer(Player.RED);
				s149.setPlayer(Player.RED);
			}else{
				s148.setPlayer(Player.BLACK);
				s149.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s148);
			sucessors.add(s149);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S111")){
		
			Node s150 =  new Node("S150");
			
			s150.setDepth(currentState.getDepth()+1);
			
			s150.setParentKey64(currentState.getKey64());
			
			s150.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s150.setPlayer(Player.RED);
			}else{
				s150.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s150);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S112")){
		
			Node s151 =  new Node("S151");
			Node s152 =  new Node("S152");
			
			s151.setDepth(currentState.getDepth()+1);
			s152.setDepth(currentState.getDepth()+1);
			
			s151.setParentKey64(currentState.getKey64());
			s152.setParentKey64(currentState.getKey64());
			
			s151.setType(Type.MAX);
			s152.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s151.setPlayer(Player.RED);
				s152.setPlayer(Player.RED);
			}else{
				s151.setPlayer(Player.BLACK);
				s152.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s151);
			sucessors.add(s152);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S113")){
		
			Node s153 =  new Node("S153");
			
			s153.setDepth(currentState.getDepth()+1);
			
			s153.setParentKey64(currentState.getKey64());
			
			s153.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s153.setPlayer(Player.RED);
			}else{
				s153.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s153);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S114")){
		
			Node s154 =  new Node("S154");
			Node s155 =  new Node("S155");
			
			s154.setDepth(currentState.getDepth()+1);
			s155.setDepth(currentState.getDepth()+1);
			
			s154.setParentKey64(currentState.getKey64());
			s155.setParentKey64(currentState.getKey64());
			
			s154.setType(Type.MAX);
			s155.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s154.setPlayer(Player.RED);
				s155.setPlayer(Player.RED);
			}else{
				s154.setPlayer(Player.BLACK);
				s155.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s154);
			sucessors.add(s155);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S115")){
		
			Node s156 =  new Node("S156");
			Node s157 =  new Node("S157");
			
			s156.setDepth(currentState.getDepth()+1);
			s157.setDepth(currentState.getDepth()+1);
			
			s156.setParentKey64(currentState.getKey64());
			s157.setParentKey64(currentState.getKey64());
			
			s156.setType(Type.MAX);
			s157.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s156.setPlayer(Player.RED);
				s157.setPlayer(Player.RED);
			}else{
				s156.setPlayer(Player.BLACK);
				s157.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s156);
			sucessors.add(s157);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S116")){
		
			Node s158 =  new Node("S158");
			Node s159 =  new Node("S159");
			
			s158.setDepth(currentState.getDepth()+1);
			s159.setDepth(currentState.getDepth()+1);
			
			s158.setParentKey64(currentState.getKey64());
			s159.setParentKey64(currentState.getKey64());
			
			s158.setType(Type.MAX);
			s159.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s158.setPlayer(Player.RED);
				s159.setPlayer(Player.RED);
			}else{
				s158.setPlayer(Player.BLACK);
				s159.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s158);
			sucessors.add(s159);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S117")){
		
			Node s160 =  new Node("S160");
			Node s161 =  new Node("S161");
			
			s160.setDepth(currentState.getDepth()+1);
			s161.setDepth(currentState.getDepth()+1);
			
			s160.setParentKey64(currentState.getKey64());
			s161.setParentKey64(currentState.getKey64());
			
			s160.setType(Type.LEAF);
			s161.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s160.setPlayer(Player.RED);
				s161.setPlayer(Player.RED);
			}else{
				s160.setPlayer(Player.BLACK);
				s161.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s160);
			sucessors.add(s161);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S118")){
		
			Node s162 =  new Node("S162");
			
			s162.setDepth(currentState.getDepth()+1);
			
			s162.setParentKey64(currentState.getKey64());
			
			s162.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s162.setPlayer(Player.RED);
			}else{
				s162.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s162);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S119")){
		
			Node s163 =  new Node("S163");
			Node s164 =  new Node("S164");
			
			s163.setDepth(currentState.getDepth()+1);
			s164.setDepth(currentState.getDepth()+1);
			
			s163.setParentKey64(currentState.getKey64());
			s164.setParentKey64(currentState.getKey64());
			
			s163.setType(Type.MAX);
			s164.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s163.setPlayer(Player.RED);
				s164.setPlayer(Player.RED);
			}else{
				s163.setPlayer(Player.BLACK);
				s164.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s163);
			sucessors.add(s164);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S120")){
		
			Node s165 =  new Node("S165");
			Node s166 =  new Node("S166");
			
			s165.setDepth(currentState.getDepth()+1);
			s166.setDepth(currentState.getDepth()+1);
			
			s165.setParentKey64(currentState.getKey64());
			s166.setParentKey64(currentState.getKey64());
			
			s165.setType(Type.MAX);
			s166.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s165.setPlayer(Player.RED);
				s166.setPlayer(Player.RED);
			}else{
				s165.setPlayer(Player.BLACK);
				s166.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s165);
			sucessors.add(s166);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S121")){
		
			Node s167 =  new Node("S167");
			Node s168 =  new Node("S168");
			
			s167.setDepth(currentState.getDepth()+1);
			s168.setDepth(currentState.getDepth()+1);
			
			s167.setParentKey64(currentState.getKey64());
			s168.setParentKey64(currentState.getKey64());
			
			s167.setType(Type.MAX);
			s168.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s167.setPlayer(Player.RED);
				s168.setPlayer(Player.RED);
			}else{
				s167.setPlayer(Player.BLACK);
				s168.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s167);
			sucessors.add(s168);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S122")){
		
			Node s226 =  new Node("S226");
			Node s227 =  new Node("S227");
			
			s226.setDepth(currentState.getDepth()+1);
			s227.setDepth(currentState.getDepth()+1);
			
			s226.setParentKey64(currentState.getKey64());
			s227.setParentKey64(currentState.getKey64());
			
			s226.setType(Type.MAX);
			s227.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s226.setPlayer(Player.RED);
				s227.setPlayer(Player.RED);
			}else{
				s226.setPlayer(Player.BLACK);
				s227.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s226);
			sucessors.add(s227);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S123")){
		
			Node s228 =  new Node("S228");
			Node s229 =  new Node("S229");
			
			s228.setDepth(currentState.getDepth()+1);
			s229.setDepth(currentState.getDepth()+1);
			
			s228.setParentKey64(currentState.getKey64());
			s229.setParentKey64(currentState.getKey64());
			
			s228.setType(Type.LEAF);
			s229.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s228.setPlayer(Player.RED);
				s229.setPlayer(Player.RED);
			}else{
				s228.setPlayer(Player.BLACK);
				s229.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s228);
			sucessors.add(s229);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S124")){
		
			Node s230 =  new Node("S230");
			
			s230.setDepth(currentState.getDepth()+1);
			
			s230.setParentKey64(currentState.getKey64());
			
			s230.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s230.setPlayer(Player.RED);
			}else{
				s230.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s230);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S125")){
		
			Node s231 =  new Node("S231");
			Node s232 =  new Node("S232");
			
			s231.setDepth(currentState.getDepth()+1);
			s232.setDepth(currentState.getDepth()+1);
			
			s231.setParentKey64(currentState.getKey64());
			s232.setParentKey64(currentState.getKey64());
			
			s231.setType(Type.MAX);
			s232.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s231.setPlayer(Player.RED);
				s232.setPlayer(Player.RED);
			}else{
				s231.setPlayer(Player.BLACK);
				s232.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s231);
			sucessors.add(s232);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S126")){
		
			Node s233 =  new Node("S233");
			Node s234 =  new Node("S234");
			
			s233.setDepth(currentState.getDepth()+1);
			s234.setDepth(currentState.getDepth()+1);
			
			s233.setParentKey64(currentState.getKey64());
			s234.setParentKey64(currentState.getKey64());
			
			s233.setType(Type.MAX);
			s234.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s233.setPlayer(Player.RED);
				s234.setPlayer(Player.RED);
			}else{
				s233.setPlayer(Player.BLACK);
				s234.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s233);
			sucessors.add(s234);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S127")){
		
			Node s235 =  new Node("S235");
			Node s236 =  new Node("S236");
			
			s235.setDepth(currentState.getDepth()+1);
			s236.setDepth(currentState.getDepth()+1);
			
			s235.setParentKey64(currentState.getKey64());
			s236.setParentKey64(currentState.getKey64());
			
			s235.setType(Type.MAX);
			s236.setType(Type.MAX);
			
			if(player.equals(Player.BLACK)){
				s235.setPlayer(Player.RED);
				s236.setPlayer(Player.RED);
			}else{
				s235.setPlayer(Player.BLACK);
				s236.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s235);
			sucessors.add(s236);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S128")){
		
			Node s169 =  new Node("S169");
			Node s170 =  new Node("S170");
			
			s169.setDepth(currentState.getDepth()+1);
			s170.setDepth(currentState.getDepth()+1);
			
			s169.setParentKey64(currentState.getKey64());
			s170.setParentKey64(currentState.getKey64());
			
			s169.setType(Type.MIN);
			s170.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s169.setPlayer(Player.RED);
				s170.setPlayer(Player.RED);
			}else{
				s169.setPlayer(Player.BLACK);
				s170.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s169);
			sucessors.add(s170);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S129")){
		
			Node s171 =  new Node("S171");
			Node s172 =  new Node("S172");
			
			s171.setDepth(currentState.getDepth()+1);
			s172.setDepth(currentState.getDepth()+1);
			
			s171.setParentKey64(currentState.getKey64());
			s172.setParentKey64(currentState.getKey64());
			
			s171.setType(Type.MIN);
			s172.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s171.setPlayer(Player.RED);
				s172.setPlayer(Player.RED);
			}else{
				s171.setPlayer(Player.BLACK);
				s172.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s171);
			sucessors.add(s172);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S131")){
		
			Node s173 =  new Node("S173");
			
			s173.setDepth(currentState.getDepth()+1);
			
			s173.setParentKey64(currentState.getKey64());
			
			s173.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s173.setPlayer(Player.RED);
			}else{
				s173.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s173);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S132")){
		
			Node s174 =  new Node("S174");
			
			s174.setDepth(currentState.getDepth()+1);
			
			s174.setParentKey64(currentState.getKey64());
			
			s174.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s174.setPlayer(Player.RED);
			}else{
				s174.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s174);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S133")){
		
			Node s175 =  new Node("S175");
			Node s176 =  new Node("S176");
			
			s175.setDepth(currentState.getDepth()+1);
			s176.setDepth(currentState.getDepth()+1);
			
			s175.setParentKey64(currentState.getKey64());
			s176.setParentKey64(currentState.getKey64());
			
			s175.setType(Type.MIN);
			s176.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s175.setPlayer(Player.RED);
				s176.setPlayer(Player.RED);
			}else{
				s175.setPlayer(Player.BLACK);
				s176.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s175);
			sucessors.add(s176);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S134")){
		
			Node s177 =  new Node("S177");
			
			s177.setDepth(currentState.getDepth()+1);
			
			s177.setParentKey64(currentState.getKey64());
			
			s177.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s177.setPlayer(Player.RED);
			}else{
				s177.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s177);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S135")){
		
			Node s178 =  new Node("S178");
			Node s179 =  new Node("S179");
			
			s178.setDepth(currentState.getDepth()+1);
			s179.setDepth(currentState.getDepth()+1);
			
			s178.setParentKey64(currentState.getKey64());
			s179.setParentKey64(currentState.getKey64());
			
			s178.setType(Type.MIN);
			s179.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s178.setPlayer(Player.RED);
				s179.setPlayer(Player.RED);
			}else{
				s178.setPlayer(Player.BLACK);
				s179.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s178);
			sucessors.add(s179);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S136")){
		
			Node s180 =  new Node("S180");
			Node s181 =  new Node("S181");
			
			s180.setDepth(currentState.getDepth()+1);
			s181.setDepth(currentState.getDepth()+1);
			
			s180.setParentKey64(currentState.getKey64());
			s181.setParentKey64(currentState.getKey64());
			
			s180.setType(Type.MIN);
			s181.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s180.setPlayer(Player.RED);
				s181.setPlayer(Player.RED);
			}else{
				s180.setPlayer(Player.BLACK);
				s181.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s180);
			sucessors.add(s181);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S137")){
		
			Node s182 =  new Node("S182");
			Node s183 =  new Node("S183");
			
			s182.setDepth(currentState.getDepth()+1);
			s183.setDepth(currentState.getDepth()+1);
			
			s182.setParentKey64(currentState.getKey64());
			s183.setParentKey64(currentState.getKey64());
			
			s182.setType(Type.MIN);
			s183.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s182.setPlayer(Player.RED);
				s183.setPlayer(Player.RED);
			}else{
				s182.setPlayer(Player.BLACK);
				s183.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s182);
			sucessors.add(s183);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S138")){
		
			Node s184 =  new Node("S184");
			
			s184.setDepth(currentState.getDepth()+1);
			
			s184.setParentKey64(currentState.getKey64());
			
			s184.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s184.setPlayer(Player.RED);
			}else{
				s184.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s184);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S139")){
		
			Node s185 =  new Node("S185");
			Node s186 =  new Node("S186");
			
			s185.setDepth(currentState.getDepth()+1);
			s186.setDepth(currentState.getDepth()+1);
			
			s185.setParentKey64(currentState.getKey64());
			s186.setParentKey64(currentState.getKey64());
			
			s185.setType(Type.MIN);
			s186.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s185.setPlayer(Player.RED);
				s186.setPlayer(Player.RED);
			}else{
				s185.setPlayer(Player.BLACK);
				s186.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s185);
			sucessors.add(s186);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S140")){
		
			Node s187 =  new Node("S187");
			Node s188 =  new Node("S188");
			
			s187.setDepth(currentState.getDepth()+1);
			s188.setDepth(currentState.getDepth()+1);
			
			s187.setParentKey64(currentState.getKey64());
			s188.setParentKey64(currentState.getKey64());
			
			s187.setType(Type.LEAF);
			s188.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s187.setPlayer(Player.RED);
				s188.setPlayer(Player.RED);
			}else{
				s187.setPlayer(Player.BLACK);
				s188.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s187);
			sucessors.add(s188);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S141")){
		
			Node s189 =  new Node("S189");
			Node s190 =  new Node("S190");
			
			s189.setDepth(currentState.getDepth()+1);
			s190.setDepth(currentState.getDepth()+1);
			
			s189.setParentKey64(currentState.getKey64());
			s190.setParentKey64(currentState.getKey64());
			
			s189.setType(Type.MIN);
			s190.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s189.setPlayer(Player.RED);
				s190.setPlayer(Player.RED);
			}else{
				s189.setPlayer(Player.BLACK);
				s190.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s189);
			sucessors.add(s190);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S143")){
		
			Node s191 =  new Node("S191");
			
			s191.setDepth(currentState.getDepth()+1);
			
			s191.setParentKey64(currentState.getKey64());
			
			s191.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s191.setPlayer(Player.RED);
			}else{
				s191.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s191);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S144")){
		
			Node s192 =  new Node("S192");
			Node s193 =  new Node("S193");
			
			s192.setDepth(currentState.getDepth()+1);
			s193.setDepth(currentState.getDepth()+1);
			
			s192.setParentKey64(currentState.getKey64());
			s193.setParentKey64(currentState.getKey64());
			
			s192.setType(Type.MIN);
			s193.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s192.setPlayer(Player.RED);
				s193.setPlayer(Player.RED);
			}else{
				s192.setPlayer(Player.BLACK);
				s193.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s192);
			sucessors.add(s193);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S145")){
		
			Node s194 =  new Node("S194");
			Node s195 =  new Node("S195");
			
			s194.setDepth(currentState.getDepth()+1);
			s195.setDepth(currentState.getDepth()+1);
			
			s194.setParentKey64(currentState.getKey64());
			s195.setParentKey64(currentState.getKey64());
			
			s194.setType(Type.MIN);
			s195.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s194.setPlayer(Player.RED);
				s195.setPlayer(Player.RED);
			}else{
				s194.setPlayer(Player.BLACK);
				s195.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s194);
			sucessors.add(s195);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S146")){
		
			Node s196 =  new Node("S196");
			Node s197 =  new Node("S197");
			
			s196.setDepth(currentState.getDepth()+1);
			s197.setDepth(currentState.getDepth()+1);
			
			s196.setParentKey64(currentState.getKey64());
			s197.setParentKey64(currentState.getKey64());
			
			s196.setType(Type.MIN);
			s197.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s196.setPlayer(Player.RED);
				s197.setPlayer(Player.RED);
			}else{
				s196.setPlayer(Player.BLACK);
				s197.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s196);
			sucessors.add(s197);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S147")){
		
			Node s198 =  new Node("S198");
			Node s199 =  new Node("S199");
			
			s198.setDepth(currentState.getDepth()+1);
			s199.setDepth(currentState.getDepth()+1);
			
			s198.setParentKey64(currentState.getKey64());
			s199.setParentKey64(currentState.getKey64());
			
			s198.setType(Type.MIN);
			s199.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s198.setPlayer(Player.RED);
				s199.setPlayer(Player.RED);
			}else{
				s198.setPlayer(Player.BLACK);
				s199.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s198);
			sucessors.add(s199);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S149")){
		
			Node s200 =  new Node("S200");
			
			s200.setDepth(currentState.getDepth()+1);
			
			s200.setParentKey64(currentState.getKey64());
			
			s200.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s200.setPlayer(Player.RED);
			}else{
				s200.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s200);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S150")){
		
			Node s201 =  new Node("S201");
			
			s201.setDepth(currentState.getDepth()+1);
			
			s201.setParentKey64(currentState.getKey64());
			
			s201.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s201.setPlayer(Player.RED);
			}else{
				s201.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s201);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S151")){
		
			Node s202 =  new Node("S202");
			Node s203 =  new Node("S203");
			
			s202.setDepth(currentState.getDepth()+1);
			s203.setDepth(currentState.getDepth()+1);
			
			s202.setParentKey64(currentState.getKey64());
			s203.setParentKey64(currentState.getKey64());
			
			s202.setType(Type.MIN);
			s203.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s202.setPlayer(Player.RED);
				s203.setPlayer(Player.RED);
			}else{
				s202.setPlayer(Player.BLACK);
				s203.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s202);
			sucessors.add(s203);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S152")){
		
			Node s204 =  new Node("S204");
			
			s204.setDepth(currentState.getDepth()+1);
			
			s204.setParentKey64(currentState.getKey64());
			
			s204.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s204.setPlayer(Player.RED);
			}else{
				s204.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s204);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S153")){
		
			Node s205 =  new Node("S205");
			
			s205.setDepth(currentState.getDepth()+1);
			
			s205.setParentKey64(currentState.getKey64());
			
			s205.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s205.setPlayer(Player.RED);
			}else{
				s205.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s205);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S154")){
		
			Node s206 =  new Node("S206");
			
			s206.setDepth(currentState.getDepth()+1);
		
			s206.setParentKey64(currentState.getKey64());
			
			s206.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s206.setPlayer(Player.RED);
			}else{
				s206.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s206);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S155")){
		
			Node s207 =  new Node("S207");
			
			s207.setDepth(currentState.getDepth()+1);
			
			s207.setParentKey64(currentState.getKey64());
			
			s207.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s207.setPlayer(Player.RED);
			}else{
				s207.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s207);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S156")){
		
			Node s208 =  new Node("S208");
			Node s209 =  new Node("S209");
			
			s208.setDepth(currentState.getDepth()+1);
			s209.setDepth(currentState.getDepth()+1);
			
			s208.setParentKey64(currentState.getKey64());
			s209.setParentKey64(currentState.getKey64());
			
			s208.setType(Type.LEAF);
			s209.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s208.setPlayer(Player.RED);
				s209.setPlayer(Player.RED);
			}else{
				s208.setPlayer(Player.BLACK);
				s209.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s208);
			sucessors.add(s209);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S158")){
		
			Node s210 =  new Node("S210");
			Node s211 =  new Node("S211");
			
			s210.setDepth(currentState.getDepth()+1);
			s211.setDepth(currentState.getDepth()+1);
			
			s210.setParentKey64(currentState.getKey64());
			s211.setParentKey64(currentState.getKey64());
			
			s210.setType(Type.MIN);
			s211.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s210.setPlayer(Player.RED);
				s211.setPlayer(Player.RED);
			}else{
				s210.setPlayer(Player.BLACK);
				s211.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s210);
			sucessors.add(s211);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S159")){
		
			Node s212 =  new Node("S212");
			Node s213 =  new Node("S213");
			
			s212.setDepth(currentState.getDepth()+1);
			s213.setDepth(currentState.getDepth()+1);
			
			s212.setParentKey64(currentState.getKey64());
			s213.setParentKey64(currentState.getKey64());
			
			s212.setType(Type.MIN);
			s213.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s212.setPlayer(Player.RED);
				s213.setPlayer(Player.RED);
			}else{
				s212.setPlayer(Player.BLACK);
				s213.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s212);
			sucessors.add(s213);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S161")){
		
			Node s214 =  new Node("S214");
			
			s214.setDepth(currentState.getDepth()+1);
			
			s214.setParentKey64(currentState.getKey64());
			
			s214.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s214.setPlayer(Player.RED);
			}else{
				s214.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s214);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S162")){
		
			Node s215 =  new Node("S215");
			
			s215.setDepth(currentState.getDepth()+1);
			
			s215.setParentKey64(currentState.getKey64());
			
			s215.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s215.setPlayer(Player.RED);
			}else{
				s215.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s215);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S163")){
		
			Node s216 =  new Node("S216");
			Node s217 =  new Node("S217");
			
			s216.setDepth(currentState.getDepth()+1);
			s217.setDepth(currentState.getDepth()+1);
			
			s216.setParentKey64(currentState.getKey64());
			s217.setParentKey64(currentState.getKey64());
			
			s216.setType(Type.MIN);
			s217.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s216.setPlayer(Player.RED);
				s217.setPlayer(Player.RED);
			}else{
				s216.setPlayer(Player.BLACK);
				s217.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s216);
			sucessors.add(s217);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S164")){
		
			Node s218 =  new Node("S218");
			
			s218.setDepth(currentState.getDepth()+1);
			
			s218.setParentKey64(currentState.getKey64());
			
			s218.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s218.setPlayer(Player.RED);
			}else{
				s218.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s218);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S165")){
		
			Node s219 =  new Node("S219");
			Node s220 =  new Node("S220");
			
			s219.setDepth(currentState.getDepth()+1);
			s220.setDepth(currentState.getDepth()+1);
			
			s219.setParentKey64(currentState.getKey64());
			s220.setParentKey64(currentState.getKey64());
			
			s219.setType(Type.MIN);
			s220.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s219.setPlayer(Player.RED);
				s220.setPlayer(Player.RED);
			}else{
				s219.setPlayer(Player.BLACK);
				s220.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s219);
			sucessors.add(s220);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S166")){
		
			Node s221 =  new Node("S221");
			Node s222 =  new Node("S222");
			
			s221.setDepth(currentState.getDepth()+1);
			s222.setDepth(currentState.getDepth()+1);
			
			s221.setParentKey64(currentState.getKey64());
			s222.setParentKey64(currentState.getKey64());
			
			s221.setType(Type.MIN);
			s222.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s221.setPlayer(Player.RED);
				s222.setPlayer(Player.RED);
			}else{
				s221.setPlayer(Player.BLACK);
				s222.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s221);
			sucessors.add(s222);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S167")){
		
			Node s223 =  new Node("S223");
			Node s224 =  new Node("S224");
			
			s223.setDepth(currentState.getDepth()+1);
			s224.setDepth(currentState.getDepth()+1);
			
			s223.setParentKey64(currentState.getKey64());
			s224.setParentKey64(currentState.getKey64());
			
			s223.setType(Type.MIN);
			s224.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s223.setPlayer(Player.RED);
				s224.setPlayer(Player.RED);
			}else{
				s223.setPlayer(Player.BLACK);
				s224.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s223);
			sucessors.add(s224);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S168")){
		
			Node s225 =  new Node("S225");
			
			s225.setDepth(currentState.getDepth()+1);
			
			s225.setParentKey64(currentState.getKey64());
			
			s225.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s225.setPlayer(Player.RED);
			}else{
				s225.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s225);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S226")){
		
			Node s237 =  new Node("S237");
			Node s238 =  new Node("S238");
			
			s237.setDepth(currentState.getDepth()+1);
			s238.setDepth(currentState.getDepth()+1);
			
			s237.setParentKey64(currentState.getKey64());
			s238.setParentKey64(currentState.getKey64());
			
			s237.setType(Type.MIN);
			s238.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s237.setPlayer(Player.RED);
				s238.setPlayer(Player.RED);
			}else{
				s237.setPlayer(Player.BLACK);
				s238.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s237);
			sucessors.add(s238);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S227")){
		
			Node s239 =  new Node("S239");
			Node s240 =  new Node("S240");
			
			s239.setDepth(currentState.getDepth()+1);
			s240.setDepth(currentState.getDepth()+1);
			
			s239.setParentKey64(currentState.getKey64());
			s240.setParentKey64(currentState.getKey64());
			
			s239.setType(Type.MIN);
			s240.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s239.setPlayer(Player.RED);
				s240.setPlayer(Player.RED);
			}else{
				s239.setPlayer(Player.BLACK);
				s240.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s239);
			sucessors.add(s240);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S229")){
		
			Node s241 =  new Node("S241");
			
			s241.setDepth(currentState.getDepth()+1);
			
			s241.setParentKey64(currentState.getKey64());
			
			s241.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s241.setPlayer(Player.RED);
			}else{
				s241.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s241);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S230")){
		
			Node s242 =  new Node("S242");
			
			s242.setDepth(currentState.getDepth()+1);
			
			s242.setParentKey64(currentState.getKey64());
			
			s242.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s242.setPlayer(Player.RED);
			}else{
				s242.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s242);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S231")){
		
			Node s243 =  new Node("S243");
			Node s244 =  new Node("S244");
			
			s243.setDepth(currentState.getDepth()+1);
			s244.setDepth(currentState.getDepth()+1);
			
			s243.setParentKey64(currentState.getKey64());
			s244.setParentKey64(currentState.getKey64());
			
			s243.setType(Type.MIN);
			s244.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s243.setPlayer(Player.RED);
				s244.setPlayer(Player.RED);
			}else{
				s243.setPlayer(Player.BLACK);
				s244.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s243);
			sucessors.add(s244);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S232")){
		
			Node s245 =  new Node("S245");
			
			s245.setDepth(currentState.getDepth()+1);
			
			s245.setParentKey64(currentState.getKey64());
			
			s245.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s245.setPlayer(Player.RED);
			}else{
				s245.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s245);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S233")){
		
			Node s246 =  new Node("S246");
			Node s247 =  new Node("S247");
			
			s246.setDepth(currentState.getDepth()+1);
			s247.setDepth(currentState.getDepth()+1);
			
			s246.setParentKey64(currentState.getKey64());
			s247.setParentKey64(currentState.getKey64());
			
			s246.setType(Type.MIN);
			s247.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s246.setPlayer(Player.RED);
				s247.setPlayer(Player.RED);
			}else{
				s246.setPlayer(Player.BLACK);
				s247.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s246);
			sucessors.add(s247);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S234")){
		
			Node s248 =  new Node("S248");
			Node s249 =  new Node("S249");
			
			s248.setDepth(currentState.getDepth()+1);
			s249.setDepth(currentState.getDepth()+1);
			
			s248.setParentKey64(currentState.getKey64());
			s249.setParentKey64(currentState.getKey64());
			
			s248.setType(Type.MIN);
			s249.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s248.setPlayer(Player.RED);
				s249.setPlayer(Player.RED);
			}else{
				s248.setPlayer(Player.BLACK);
				s249.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s248);
			sucessors.add(s249);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S235")){
		
			Node s250 =  new Node("S250");
			Node s251 =  new Node("S251");
			
			s250.setDepth(currentState.getDepth()+1);
			s251.setDepth(currentState.getDepth()+1);
			
			s250.setParentKey64(currentState.getKey64());
			s251.setParentKey64(currentState.getKey64());
			
			s250.setType(Type.MIN);
			s251.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s250.setPlayer(Player.RED);
				s251.setPlayer(Player.RED);
			}else{
				s250.setPlayer(Player.BLACK);
				s251.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s250);
			sucessors.add(s251);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S236")){
		
			Node s252 =  new Node("S252");
			
			s252.setDepth(currentState.getDepth()+1);
			
			s252.setParentKey64(currentState.getKey64());
			
			s252.setType(Type.MIN);
			
			if(player.equals(Player.BLACK)){
				s252.setPlayer(Player.RED);
			}else{
				s252.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s252);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S169")){
		
			Node s253 =  new Node("S253");
			Node s254 =  new Node("S254");
			
			s253.setDepth(currentState.getDepth()+1);
			s254.setDepth(currentState.getDepth()+1);
			
			s253.setParentKey64(currentState.getKey64());
			s254.setParentKey64(currentState.getKey64());
			
			s253.setType(Type.LEAF);
			s254.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s253.setPlayer(Player.RED);
				s254.setPlayer(Player.RED);
			}else{
				s253.setPlayer(Player.BLACK);
				s254.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s253);
			sucessors.add(s254);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S170")){
		
			Node s255 =  new Node("S255");
			Node s256 =  new Node("S256");
			
			s255.setDepth(currentState.getDepth()+1);
			s256.setDepth(currentState.getDepth()+1);
			
			s255.setParentKey64(currentState.getKey64());
			s256.setParentKey64(currentState.getKey64());
			
			s255.setType(Type.LEAF);
			s256.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s255.setPlayer(Player.RED);
				s256.setPlayer(Player.RED);
			}else{
				s255.setPlayer(Player.BLACK);
				s256.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s255);
			sucessors.add(s256);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S171")){
		
			Node s257 =  new Node("S257");
			
			s257.setDepth(currentState.getDepth()+1);
			
			s257.setParentKey64(currentState.getKey64());
			
			s257.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s257.setPlayer(Player.RED);
			}else{
				s257.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s257);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S172")){
		
			Node s258 =  new Node("S258");
			Node s259 =  new Node("S259");
			
			s258.setDepth(currentState.getDepth()+1);
			s259.setDepth(currentState.getDepth()+1);
			
			s258.setParentKey64(currentState.getKey64());
			s259.setParentKey64(currentState.getKey64());
			
			s258.setType(Type.LEAF);
			s259.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s258.setPlayer(Player.RED);
				s259.setPlayer(Player.RED);
			}else{
				s258.setPlayer(Player.BLACK);
				s259.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s258);
			sucessors.add(s259);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S174")){
		
			Node s260 =  new Node("S260");
			Node s261 =  new Node("S261");
			
			s260.setDepth(currentState.getDepth()+1);
			s261.setDepth(currentState.getDepth()+1);
			
			s260.setParentKey64(currentState.getKey64());
			s261.setParentKey64(currentState.getKey64());
			
			s260.setType(Type.LEAF);
			s261.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s260.setPlayer(Player.RED);
				s261.setPlayer(Player.RED);
			}else{
				s260.setPlayer(Player.BLACK);
				s261.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s260);
			sucessors.add(s261);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S175")){
		
			Node s262 =  new Node("S262");
			
			s262.setDepth(currentState.getDepth()+1);
			
			s262.setParentKey64(currentState.getKey64());
			
			s262.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s262.setPlayer(Player.RED);
			}else{
				s262.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s262);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S176")){
		
			Node s263 =  new Node("S263");
			Node s264 =  new Node("S264");
			
			s263.setDepth(currentState.getDepth()+1);
			s264.setDepth(currentState.getDepth()+1);
			
			s263.setParentKey64(currentState.getKey64());
			s264.setParentKey64(currentState.getKey64());
			
			s263.setType(Type.LEAF);
			s264.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s263.setPlayer(Player.RED);
				s264.setPlayer(Player.RED);
			}else{
				s263.setPlayer(Player.BLACK);
				s264.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s263);
			sucessors.add(s264);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S177")){
		
			Node s265 =  new Node("S265");
			Node s266 =  new Node("S266");
			
			s265.setDepth(currentState.getDepth()+1);
			s266.setDepth(currentState.getDepth()+1);
			
			s265.setParentKey64(currentState.getKey64());
			s266.setParentKey64(currentState.getKey64());
			
			s265.setType(Type.LEAF);
			s266.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s265.setPlayer(Player.RED);
				s266.setPlayer(Player.RED);
			}else{
				s265.setPlayer(Player.BLACK);
				s266.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s265);
			sucessors.add(s266);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S178")){
		
			Node s267 =  new Node("S267");
			Node s268 =  new Node("S268");
			
			s267.setDepth(currentState.getDepth()+1);
			s268.setDepth(currentState.getDepth()+1);
			
			s267.setParentKey64(currentState.getKey64());
			s268.setParentKey64(currentState.getKey64());
			
			s267.setType(Type.LEAF);
			s268.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s267.setPlayer(Player.RED);
				s268.setPlayer(Player.RED);
			}else{
				s267.setPlayer(Player.BLACK);
				s268.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s267);
			sucessors.add(s268);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S179")){
		
			Node s269 =  new Node("S269");
			Node s270 =  new Node("S270");
			
			s269.setDepth(currentState.getDepth()+1);
			s270.setDepth(currentState.getDepth()+1);
			
			s269.setParentKey64(currentState.getKey64());
			s270.setParentKey64(currentState.getKey64());
			
			s269.setType(Type.LEAF);
			s270.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s269.setPlayer(Player.RED);
				s270.setPlayer(Player.RED);
			}else{
				s269.setPlayer(Player.BLACK);
				s270.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s269);
			sucessors.add(s270);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S180")){
		
			Node s271 =  new Node("S271");
			
			s271.setDepth(currentState.getDepth()+1);
			
			s271.setParentKey64(currentState.getKey64());
			
			s271.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s271.setPlayer(Player.RED);
			}else{
				s271.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s271);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S181")){
		
			Node s272 =  new Node("S272");
			Node s273 =  new Node("S273");
			
			s272.setDepth(currentState.getDepth()+1);
			s273.setDepth(currentState.getDepth()+1);
			
			s272.setParentKey64(currentState.getKey64());
			s273.setParentKey64(currentState.getKey64());
			
			s272.setType(Type.LEAF);
			s273.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s272.setPlayer(Player.RED);
				s273.setPlayer(Player.RED);
			}else{
				s272.setPlayer(Player.BLACK);
				s273.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s272);
			sucessors.add(s273);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S182")){
		
			Node s274 =  new Node("S274");
			Node s275 =  new Node("S275");
			
			s274.setDepth(currentState.getDepth()+1);
			s275.setDepth(currentState.getDepth()+1);
			
			s274.setParentKey64(currentState.getKey64());
			s275.setParentKey64(currentState.getKey64());
			
			s274.setType(Type.LEAF);
			s275.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s274.setPlayer(Player.RED);
				s275.setPlayer(Player.RED);
			}else{
				s274.setPlayer(Player.BLACK);
				s275.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s274);
			sucessors.add(s275);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S183")){
		
			Node s276 =  new Node("S276");
			
			s276.setDepth(currentState.getDepth()+1);
			
			s276.setParentKey64(currentState.getKey64());
			
			s276.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s276.setPlayer(Player.RED);
			}else{
				s276.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s276);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S184")){
		
			Node s277 =  new Node("S277");
			Node s278 =  new Node("S278");
			
			s277.setDepth(currentState.getDepth()+1);
			s278.setDepth(currentState.getDepth()+1);
			
			s277.setParentKey64(currentState.getKey64());
			s278.setParentKey64(currentState.getKey64());
			
			s277.setType(Type.LEAF);
			s278.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s277.setPlayer(Player.RED);
				s278.setPlayer(Player.RED);
			}else{
				s277.setPlayer(Player.BLACK);
				s278.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s277);
			sucessors.add(s278);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S185")){
		
			Node s279 =  new Node("S279");
			Node s280 =  new Node("S280");
			
			s279.setDepth(currentState.getDepth()+1);
			s280.setDepth(currentState.getDepth()+1);
			
			s279.setParentKey64(currentState.getKey64());
			s280.setParentKey64(currentState.getKey64());
			
			s279.setType(Type.LEAF);
			s280.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s279.setPlayer(Player.RED);
				s280.setPlayer(Player.RED);
			}else{
				s279.setPlayer(Player.BLACK);
				s280.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s279);
			sucessors.add(s280);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S186")){
		
			Node s281 =  new Node("S281");
			Node s282 =  new Node("S282");
			
			s281.setDepth(currentState.getDepth()+1);
			s282.setDepth(currentState.getDepth()+1);
			
			s281.setParentKey64(currentState.getKey64());
			s282.setParentKey64(currentState.getKey64());
			
			s281.setType(Type.LEAF);
			s282.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s281.setPlayer(Player.RED);
				s282.setPlayer(Player.RED);
			}else{
				s281.setPlayer(Player.BLACK);
				s282.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s281);
			sucessors.add(s282);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S188")){
		
			Node s283 =  new Node("S283");
			
			s283.setDepth(currentState.getDepth()+1);
			
			s283.setParentKey64(currentState.getKey64());
			
			s283.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s283.setPlayer(Player.RED);
			}else{
				s283.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s283);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S189")){
		
			Node s284 =  new Node("S284");
			Node s285 =  new Node("S285");
			
			s284.setDepth(currentState.getDepth()+1);
			s285.setDepth(currentState.getDepth()+1);
			
			s284.setParentKey64(currentState.getKey64());
			s285.setParentKey64(currentState.getKey64());
			
			s284.setType(Type.LEAF);
			s285.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s284.setPlayer(Player.RED);
				s285.setPlayer(Player.RED);
			}else{
				s284.setPlayer(Player.BLACK);
				s285.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s284);
			sucessors.add(s285);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S190")){
		
			Node s286 =  new Node("S286");
			Node s287 =  new Node("S287");
			
			s286.setDepth(currentState.getDepth()+1);
			s287.setDepth(currentState.getDepth()+1);
			
			s286.setParentKey64(currentState.getKey64());
			s287.setParentKey64(currentState.getKey64());
			
			s286.setType(Type.LEAF);
			s287.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s286.setPlayer(Player.RED);
				s287.setPlayer(Player.RED);
			}else{
				s286.setPlayer(Player.BLACK);
				s287.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s286);
			sucessors.add(s287);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S191")){
		
			Node s288 =  new Node("S288");
			
			s288.setDepth(currentState.getDepth()+1);
			
			s288.setParentKey64(currentState.getKey64());
			
			s288.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s288.setPlayer(Player.RED);
			}else{
				s288.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s288);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S192")){
		
			Node s289 =  new Node("S289");
			
			s289.setDepth(currentState.getDepth()+1);
			
			s289.setParentKey64(currentState.getKey64());
			
			s289.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s289.setPlayer(Player.RED);
			}else{
				s289.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s289);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S193")){
		
			Node s290 =  new Node("S290");
			
			s290.setDepth(currentState.getDepth()+1);
			
			s290.setParentKey64(currentState.getKey64());
			
			s290.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s290.setPlayer(Player.RED);
			}else{
				s290.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s290);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S194")){
		
			Node s291 =  new Node("S291");
			Node s292 =  new Node("S292");
			
			s291.setDepth(currentState.getDepth()+1);
			s292.setDepth(currentState.getDepth()+1);
			
			s291.setParentKey64(currentState.getKey64());
			s292.setParentKey64(currentState.getKey64());
			
			s291.setType(Type.LEAF);
			s292.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s291.setPlayer(Player.RED);
				s292.setPlayer(Player.RED);
			}else{
				s291.setPlayer(Player.BLACK);
				s292.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s291);
			sucessors.add(s292);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S196")){
		
			Node s293 =  new Node("S293");
			Node s294 =  new Node("S294");
			
			s293.setDepth(currentState.getDepth()+1);
			s294.setDepth(currentState.getDepth()+1);
			
			s293.setParentKey64(currentState.getKey64());
			s294.setParentKey64(currentState.getKey64());
			
			s293.setType(Type.LEAF);
			s294.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s293.setPlayer(Player.RED);
				s294.setPlayer(Player.RED);
			}else{
				s293.setPlayer(Player.BLACK);
				s294.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s293);
			sucessors.add(s294);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S197")){
		
			Node s295 =  new Node("S295");
			Node s296 =  new Node("S296");
			
			s295.setDepth(currentState.getDepth()+1);
			s296.setDepth(currentState.getDepth()+1);
			
			s295.setParentKey64(currentState.getKey64());
			s296.setParentKey64(currentState.getKey64());
			
			s295.setType(Type.LEAF);
			s296.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s295.setPlayer(Player.RED);
				s296.setPlayer(Player.RED);
			}else{
				s295.setPlayer(Player.BLACK);
				s296.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s295);
			sucessors.add(s296);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S198")){
		
			Node s297 =  new Node("S297");
			
			s297.setDepth(currentState.getDepth()+1);
			
			s297.setParentKey64(currentState.getKey64());
			
			s297.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s297.setPlayer(Player.RED);
			}else{
				s297.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s297);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S199")){
		
			Node s298 =  new Node("S298");
			Node s299 =  new Node("S299");
			
			s298.setDepth(currentState.getDepth()+1);
			s299.setDepth(currentState.getDepth()+1);
			
			s298.setParentKey64(currentState.getKey64());
			s299.setParentKey64(currentState.getKey64());
			
			s298.setType(Type.LEAF);
			s299.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s298.setPlayer(Player.RED);
				s299.setPlayer(Player.RED);
			}else{
				s298.setPlayer(Player.BLACK);
				s299.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s298);
			sucessors.add(s299);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S201")){
		
			Node s300 =  new Node("S300");
			Node s301 =  new Node("S301");
			
			s300.setDepth(currentState.getDepth()+1);
			s301.setDepth(currentState.getDepth()+1);
			
			s300.setParentKey64(currentState.getKey64());
			s301.setParentKey64(currentState.getKey64());
			
			s300.setType(Type.LEAF);
			s301.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s300.setPlayer(Player.RED);
				s301.setPlayer(Player.RED);
			}else{
				s300.setPlayer(Player.BLACK);
				s301.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s300);
			sucessors.add(s301);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S202")){
		
			Node s302 =  new Node("S302");
			
			s302.setDepth(currentState.getDepth()+1);
			
			s302.setParentKey64(currentState.getKey64());
			
			s302.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s302.setPlayer(Player.RED);
			}else{
				s302.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s302);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S203")){
		
			Node s303 =  new Node("S303");
			Node s304 =  new Node("S304");
			
			s303.setDepth(currentState.getDepth()+1);
			s304.setDepth(currentState.getDepth()+1);
			
			s303.setParentKey64(currentState.getKey64());
			s304.setParentKey64(currentState.getKey64());
			
			s303.setType(Type.LEAF);
			s304.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s303.setPlayer(Player.RED);
				s304.setPlayer(Player.RED);
			}else{
				s303.setPlayer(Player.BLACK);
				s304.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s303);
			sucessors.add(s304);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S204")){
		
			Node s305 =  new Node("S305");
			Node s306 =  new Node("S306");
			
			s305.setDepth(currentState.getDepth()+1);
			s306.setDepth(currentState.getDepth()+1);
			
			s305.setParentKey64(currentState.getKey64());
			s306.setParentKey64(currentState.getKey64());
			
			s305.setType(Type.LEAF);
			s306.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s305.setPlayer(Player.RED);
				s306.setPlayer(Player.RED);
			}else{
				s305.setPlayer(Player.BLACK);
				s306.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s305);
			sucessors.add(s306);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S210")){
		
			Node s307 =  new Node("S307");
			Node s308 =  new Node("S308");
			
			s307.setDepth(currentState.getDepth()+1);
			s308.setDepth(currentState.getDepth()+1);
			
			s307.setParentKey64(currentState.getKey64());
			s308.setParentKey64(currentState.getKey64());
			
			s307.setType(Type.LEAF);
			s308.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s307.setPlayer(Player.RED);
				s308.setPlayer(Player.RED);
			}else{
				s307.setPlayer(Player.BLACK);
				s308.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s307);
			sucessors.add(s308);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S211")){
		
			Node s309 =  new Node("S309");
			Node s310 =  new Node("S310");
			
			s309.setDepth(currentState.getDepth()+1);
			s310.setDepth(currentState.getDepth()+1);
			
			s309.setParentKey64(currentState.getKey64());
			s310.setParentKey64(currentState.getKey64());
			
			s309.setType(Type.LEAF);
			s310.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s309.setPlayer(Player.RED);
				s310.setPlayer(Player.RED);
			}else{
				s309.setPlayer(Player.BLACK);
				s310.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s309);
			sucessors.add(s310);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S212")){
		
			Node s311 =  new Node("S311");
			
			s311.setDepth(currentState.getDepth()+1);
			
			s311.setParentKey64(currentState.getKey64());
			
			s311.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s311.setPlayer(Player.RED);
			}else{
				s311.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s311);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S213")){
		
			Node s312 =  new Node("S312");
			Node s313 =  new Node("S313");
			
			s312.setDepth(currentState.getDepth()+1);
			s313.setDepth(currentState.getDepth()+1);
			
			s312.setParentKey64(currentState.getKey64());
			s313.setParentKey64(currentState.getKey64());
			
			s312.setType(Type.LEAF);
			s313.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s312.setPlayer(Player.RED);
				s313.setPlayer(Player.RED);
			}else{
				s312.setPlayer(Player.BLACK);
				s313.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s312);
			sucessors.add(s313);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S215")){
		
			Node s314 =  new Node("S314");
			Node s315 =  new Node("S315");
			
			s314.setDepth(currentState.getDepth()+1);
			s315.setDepth(currentState.getDepth()+1);
			
			s314.setParentKey64(currentState.getKey64());
			s315.setParentKey64(currentState.getKey64());
			
			s314.setType(Type.LEAF);
			s315.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s314.setPlayer(Player.RED);
				s315.setPlayer(Player.RED);
			}else{
				s314.setPlayer(Player.BLACK);
				s315.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s314);
			sucessors.add(s315);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S216")){
		
			Node s316 =  new Node("S316");
			
			s316.setDepth(currentState.getDepth()+1);
			
			s316.setParentKey64(currentState.getKey64());
			
			s316.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s316.setPlayer(Player.RED);
			}else{
				s316.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s316);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S217")){
		
			Node s317 =  new Node("S317");
			Node s318 =  new Node("S318");
			
			s317.setDepth(currentState.getDepth()+1);
			s318.setDepth(currentState.getDepth()+1);
			
			s317.setParentKey64(currentState.getKey64());
			s318.setParentKey64(currentState.getKey64());
			
			s317.setType(Type.LEAF);
			s318.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s317.setPlayer(Player.RED);
				s318.setPlayer(Player.RED);
			}else{
				s317.setPlayer(Player.BLACK);
				s318.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s317);
			sucessors.add(s318);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S218")){
		
			Node s319 =  new Node("S319");
			Node s320 =  new Node("S320");
			
			s319.setDepth(currentState.getDepth()+1);
			s320.setDepth(currentState.getDepth()+1);
			
			s319.setParentKey64(currentState.getKey64());
			s320.setParentKey64(currentState.getKey64());
			
			s319.setType(Type.LEAF);
			s320.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s319.setPlayer(Player.RED);
				s320.setPlayer(Player.RED);
			}else{
				s319.setPlayer(Player.BLACK);
				s320.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s319);
			sucessors.add(s320);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S219")){
		
			Node s321 =  new Node("S321");
			Node s322 =  new Node("S322");
			
			s321.setDepth(currentState.getDepth()+1);
			s322.setDepth(currentState.getDepth()+1);
			
			s321.setParentKey64(currentState.getKey64());
			s322.setParentKey64(currentState.getKey64());
			
			s321.setType(Type.LEAF);
			s322.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s321.setPlayer(Player.RED);
				s322.setPlayer(Player.RED);
			}else{
				s321.setPlayer(Player.BLACK);
				s322.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s321);
			sucessors.add(s322);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S220")){
		
			Node s323 =  new Node("S323");
			Node s324 =  new Node("S324");
			
			s323.setDepth(currentState.getDepth()+1);
			s324.setDepth(currentState.getDepth()+1);
			
			s323.setParentKey64(currentState.getKey64());
			s324.setParentKey64(currentState.getKey64());
			
			s323.setType(Type.LEAF);
			s324.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s323.setPlayer(Player.RED);
				s324.setPlayer(Player.RED);
			}else{
				s323.setPlayer(Player.BLACK);
				s324.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s323);
			sucessors.add(s324);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S221")){
		
			Node s325 =  new Node("S325");
			
			s325.setDepth(currentState.getDepth()+1);
			
			s325.setParentKey64(currentState.getKey64());
			
			s325.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s325.setPlayer(Player.RED);
			}else{
				s325.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s325);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S222")){
		
			Node s326 =  new Node("S326");
			Node s327 =  new Node("S327");
			
			s326.setDepth(currentState.getDepth()+1);
			s327.setDepth(currentState.getDepth()+1);
			
			s326.setParentKey64(currentState.getKey64());
			s327.setParentKey64(currentState.getKey64());
			
			s326.setType(Type.LEAF);
			s327.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s326.setPlayer(Player.RED);
				s327.setPlayer(Player.RED);
			}else{
				s326.setPlayer(Player.BLACK);
				s327.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s326);
			sucessors.add(s327);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S223")){
		
			Node s328 =  new Node("S328");
			Node s329 =  new Node("S329");
			
			s328.setDepth(currentState.getDepth()+1);
			s329.setDepth(currentState.getDepth()+1);
			
			s328.setParentKey64(currentState.getKey64());
			s329.setParentKey64(currentState.getKey64());
			
			s328.setType(Type.LEAF);
			s329.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s328.setPlayer(Player.RED);
				s329.setPlayer(Player.RED);
			}else{
				s328.setPlayer(Player.BLACK);
				s329.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s328);
			sucessors.add(s329);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S224")){
		
			Node s330 =  new Node("S330");
			
			s330.setDepth(currentState.getDepth()+1);
			
			s330.setParentKey64(currentState.getKey64());
			
			s330.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s330.setPlayer(Player.RED);
			}else{
				s330.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s330);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S225")){
		
			Node s331 =  new Node("S331");
			Node s332 =  new Node("S332");
			
			s331.setDepth(currentState.getDepth()+1);
			s332.setDepth(currentState.getDepth()+1);
			
			s331.setParentKey64(currentState.getKey64());
			s332.setParentKey64(currentState.getKey64());
			
			s331.setType(Type.LEAF);
			s332.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s331.setPlayer(Player.RED);
				s332.setPlayer(Player.RED);
			}else{
				s331.setPlayer(Player.BLACK);
				s332.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s331);
			sucessors.add(s332);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S237")){
		
			Node s333 =  new Node("S333");
			Node s334 =  new Node("S334");
			
			s333.setDepth(currentState.getDepth()+1);
			s334.setDepth(currentState.getDepth()+1);
			
			s333.setParentKey64(currentState.getKey64());
			s334.setParentKey64(currentState.getKey64());
			
			s333.setType(Type.LEAF);
			s334.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s333.setPlayer(Player.RED);
				s334.setPlayer(Player.RED);
			}else{
				s333.setPlayer(Player.BLACK);
				s334.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s333);
			sucessors.add(s334);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S238")){
		
			Node s335 =  new Node("S335");
			Node s336 =  new Node("S336");
			
			s335.setDepth(currentState.getDepth()+1);
			s336.setDepth(currentState.getDepth()+1);
			
			s335.setParentKey64(currentState.getKey64());
			s336.setParentKey64(currentState.getKey64());
			
			s335.setType(Type.LEAF);
			s336.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s335.setPlayer(Player.RED);
				s336.setPlayer(Player.RED);
			}else{
				s335.setPlayer(Player.BLACK);
				s336.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s335);
			sucessors.add(s336);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S239")){
		
			Node s337 =  new Node("S337");
			
			s337.setDepth(currentState.getDepth()+1);
			
			s337.setParentKey64(currentState.getKey64());
			
			s337.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s337.setPlayer(Player.RED);
			}else{
				s337.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s337);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S240")){
		
			Node s338 =  new Node("S338");
			Node s339 =  new Node("S339");
			
			s338.setDepth(currentState.getDepth()+1);
			s339.setDepth(currentState.getDepth()+1);
			
			s338.setParentKey64(currentState.getKey64());
			s339.setParentKey64(currentState.getKey64());
			
			s338.setType(Type.LEAF);
			s339.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s338.setPlayer(Player.RED);
				s339.setPlayer(Player.RED);
			}else{
				s338.setPlayer(Player.BLACK);
				s339.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s338);
			sucessors.add(s339);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S242")){
		
			Node s340 =  new Node("S340");
			Node s341 =  new Node("S341");
			
			s340.setDepth(currentState.getDepth()+1);
			s341.setDepth(currentState.getDepth()+1);
			
			s340.setParentKey64(currentState.getKey64());
			s341.setParentKey64(currentState.getKey64());
			
			s340.setType(Type.LEAF);
			s341.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s340.setPlayer(Player.RED);
				s341.setPlayer(Player.RED);
			}else{
				s340.setPlayer(Player.BLACK);
				s341.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s340);
			sucessors.add(s341);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S243")){
		
			Node s342 =  new Node("S342");
			
			s342.setDepth(currentState.getDepth()+1);
			
			s342.setParentKey64(currentState.getKey64());
			
			s342.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s342.setPlayer(Player.RED);
			}else{
				s342.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s342);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S244")){
		
			Node s343 =  new Node("S343");
			Node s344 =  new Node("S344");
			
			s343.setDepth(currentState.getDepth()+1);
			s344.setDepth(currentState.getDepth()+1);
			
			s343.setParentKey64(currentState.getKey64());
			s344.setParentKey64(currentState.getKey64());
			
			s343.setType(Type.LEAF);
			s344.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s343.setPlayer(Player.RED);
				s344.setPlayer(Player.RED);
			}else{
				s343.setPlayer(Player.BLACK);
				s344.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s343);
			sucessors.add(s344);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S245")){
		
			Node s345 =  new Node("S345");
			Node s346 =  new Node("S346");
			
			s345.setDepth(currentState.getDepth()+1);
			s346.setDepth(currentState.getDepth()+1);
			
			s345.setParentKey64(currentState.getKey64());
			s346.setParentKey64(currentState.getKey64());
			
			s345.setType(Type.LEAF);
			s346.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s345.setPlayer(Player.RED);
				s346.setPlayer(Player.RED);
			}else{
				s345.setPlayer(Player.BLACK);
				s346.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s345);
			sucessors.add(s346);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S246")){
		
			Node s347 =  new Node("S347");
			Node s348 =  new Node("S348");
			
			s347.setDepth(currentState.getDepth()+1);
			s348.setDepth(currentState.getDepth()+1);
			
			s347.setParentKey64(currentState.getKey64());
			s348.setParentKey64(currentState.getKey64());
			
			s347.setType(Type.LEAF);
			s348.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s347.setPlayer(Player.RED);
				s348.setPlayer(Player.RED);
			}else{
				s347.setPlayer(Player.BLACK);
				s348.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s347);
			sucessors.add(s348);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S247")){
		
			Node s349 =  new Node("S349");
			Node s350 =  new Node("S350");
			
			s349.setDepth(currentState.getDepth()+1);
			s350.setDepth(currentState.getDepth()+1);
			
			s349.setParentKey64(currentState.getKey64());
			s350.setParentKey64(currentState.getKey64());
			
			s349.setType(Type.LEAF);
			s350.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s349.setPlayer(Player.RED);
				s350.setPlayer(Player.RED);
			}else{
				s349.setPlayer(Player.BLACK);
				s350.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s349);
			sucessors.add(s350);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S248")){
		
			Node s351 =  new Node("S351");
			
			s351.setDepth(currentState.getDepth()+1);
			
			s351.setParentKey64(currentState.getKey64());
			
			s351.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s351.setPlayer(Player.RED);
			}else{
				s351.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s351);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S249")){
		
			Node s352 =  new Node("S352");
			Node s353 =  new Node("S353");
			
			s352.setDepth(currentState.getDepth()+1);
			s353.setDepth(currentState.getDepth()+1);
			
			s352.setParentKey64(currentState.getKey64());
			s353.setParentKey64(currentState.getKey64());
			
			s352.setType(Type.LEAF);
			s353.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s352.setPlayer(Player.RED);
				s353.setPlayer(Player.RED);
			}else{
				s352.setPlayer(Player.BLACK);
				s353.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s352);
			sucessors.add(s353);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S250")){
		
			Node s354 =  new Node("S354");
			Node s355 =  new Node("S355");
			
			s354.setDepth(currentState.getDepth()+1);
			s355.setDepth(currentState.getDepth()+1);
			
			s354.setParentKey64(currentState.getKey64());
			s355.setParentKey64(currentState.getKey64());
			
			s354.setType(Type.LEAF);
			s355.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s354.setPlayer(Player.RED);
				s355.setPlayer(Player.RED);
			}else{
				s354.setPlayer(Player.BLACK);
				s355.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s354);
			sucessors.add(s355);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S251")){
		
			Node s356 =  new Node("S356");
			
			s356.setDepth(currentState.getDepth()+1);
			
			s356.setParentKey64(currentState.getKey64());
			
			s356.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s356.setPlayer(Player.RED);
			}else{
				s356.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s356);

			currentState.setChildren(sucessors);
			
		}else if(currentState.getName().equals("S252")){
		
			Node s357 =  new Node("S357");
			Node s358 =  new Node("S358");
			
			s357.setDepth(currentState.getDepth()+1);
			s358.setDepth(currentState.getDepth()+1);
			
			s357.setParentKey64(currentState.getKey64());
			s358.setParentKey64(currentState.getKey64());
			
			s357.setType(Type.LEAF);
			s358.setType(Type.LEAF);
			
			if(player.equals(Player.BLACK)){
				s357.setPlayer(Player.RED);
				s358.setPlayer(Player.RED);
			}else{
				s357.setPlayer(Player.BLACK);
				s358.setPlayer(Player.BLACK);
			}
			
			sucessors.add(s357);
			sucessors.add(s358);

			currentState.setChildren(sucessors);
			
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
