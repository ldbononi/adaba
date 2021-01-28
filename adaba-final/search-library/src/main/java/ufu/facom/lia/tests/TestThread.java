package ufu.facom.lia.tests;

import java.util.ArrayList;
import java.util.List;

public class TestThread implements Runnable{
	
	private static final long MAX_TIME = 5L;
	
	private static List<String> list = new ArrayList<String>();
	
	private String name;
	

	@Override
	public void run() {
		
		for(int i = 0; i < 5; i++){
			
			addName("Maria");
			
			System.out.println("Thread: " + Thread.currentThread().getName() + "Tamanho: " + list.size());
			
			for(int j = 0;  j < list.size(); j++){
				System.out.println(list.get(j));
			}
			
		}
	}
	
	public synchronized void addName(String name) {
		list.add(name);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.add("João");
		
		
	}
	
	public void count() throws InterruptedException{
		
		long init = System.currentTimeMillis();
		
		for(int i = 0; i < 1000000; i++){
			
			if(System.currentTimeMillis()- init > MAX_TIME){
				throw new InterruptedException();
			}
			
			System.out.println("Valor de i: " + i);
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
