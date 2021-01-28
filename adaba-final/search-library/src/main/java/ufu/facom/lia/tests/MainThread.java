package ufu.facom.lia.tests;

public class MainThread {
	
	public static void main(String[] args) {
		
		TestThread tt = new TestThread();
		
		Thread one = new Thread(tt);
		Thread two = new Thread(tt);

		one.setName("ONE");
		two.setName("TWO");
		
		one.start();
		two.start();
	}
}
