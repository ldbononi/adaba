package ufu.facom.lia.tests;

import java.util.Comparator;
import java.util.PriorityQueue;

public class QueueTest {
	
	public static void main(String[] args) {
		
		PriorityQueue<Integer> test =  new PriorityQueue<>();
		
		test.add(5);
		test.add(2);
		test.add(1);
		
		for(int i = 0; i < 3; i++){
			System.out.println(test.poll());
		}
		
		Comparator<Integer> comparator = new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
			
		};
		
		PriorityQueue<Integer> test2 = new PriorityQueue<>(10, comparator);
		
		test2.add(5);
		test2.add(2);
		test2.add(1);
		
		for(int i = 0; i < 3; i++){
			System.out.println(test2.poll());
		}
		
		System.out.println("=============================================================");
		
		PriorityQueue<MeuTest> test3 = new PriorityQueue<>();
		
		MeuTest a = new MeuTest(5);
		MeuTest b = new MeuTest(2);
		MeuTest c = new MeuTest(1);
		
		test3.add(a);
		test3.add(b);
		test3.add(c);
		
		System.out.println(test3.peek().getPriority());
		
		a.setPriority(8);
		
		System.out.println(test3.peek().getPriority());
		
		c.setPriority(10);
		
		System.out.println(test3.peek().getPriority());
		
		MeuTest d = new MeuTest(10);
		
		test3.add(d);
		
		System.out.println(test3.peek().getPriority());
	}

}

class MeuTest implements Comparable<MeuTest>{
	
	MeuTest(Integer p){
		this.priority = p;
	}
	
	private int priority;
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}

	@Override
	public int compareTo(MeuTest o) {
		return Integer.valueOf(o.getPriority()).compareTo(Integer.valueOf(priority));
	}
}
