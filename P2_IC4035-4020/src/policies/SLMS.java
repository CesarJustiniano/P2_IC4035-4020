package policies;
import java.util.Scanner;

import classes.Customer;
import classes.SLLQueue;

public class SLMS {
	
	private SLLQueue<Customer> customerLine = new SLLQueue<Customer>();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SLLQueue<Customer> customerLine = new SLLQueue<Customer>();
		Object[] servers;
		int t1, t2, n, m, numServers;
		long startService, endedService;
		Scanner scan = new Scanner(System.in); //system.in is for testing
		
		n = scan.nextInt();
		servers = new Object[scan.nextInt()];
		
		for(int i=0; i<n; i++){
			customerLine.enqueue(null);
		}		
		
		m = customerLine.size();
		System.out.println("The amount of customers: " + n);
		
		while(!customerLine.isEmpty()){
			System.out.println(customerLine.dequeue());
		}
		
		scan.close();
	}


}
