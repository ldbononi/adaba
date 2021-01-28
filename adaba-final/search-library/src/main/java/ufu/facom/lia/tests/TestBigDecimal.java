package ufu.facom.lia.tests;

import java.math.BigDecimal;

public class TestBigDecimal {
	
	private BigDecimal aTeste;
	
	public static void main(String[] args) {
		TestBigDecimal tbd = new TestBigDecimal();
		
		//tbd.testando();
		tbd.hoIsGreater(new BigDecimal("-0.304931"), new BigDecimal("-0.326063"));
	}
	
	@SuppressWarnings("unused")
	private void testando(){
		
		aTeste = new BigDecimal("10");
		
		BigDecimal bTeste = new BigDecimal("2");
		
		BigDecimal b = null;
		
		if(teste(b)){
			b = aTeste;
		}
		
		System.out.println(b.toPlainString());
		
		//aTeste = aTeste.add(new BigDecimal("4"));
		aTeste = bTeste;
		
		System.out.println("ATeste: " + aTeste.toPlainString());
		
		System.out.println(b.toPlainString());
	}
	
	private void hoIsGreater(BigDecimal a, BigDecimal b){
		
		if(a.compareTo(b) > 0){
			System.out.println(a.toPlainString() + " é maior que " + b.toPlainString());
		}else if(a.compareTo(b) < 0){
			System.out.println(a.toPlainString() + " é menor que " + b.toPlainString());
		}
	}
	
	
	
	private Boolean teste(BigDecimal a){
		

		if(a == null){
			return true;
		}
		
		if(aTeste.compareTo(a) > 0){
			return true;
		}
		
		if(aTeste.compareTo(a) < 0){
			return true;
		}
		
		return false;
	}

}
