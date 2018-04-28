package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import customer.Customer;
import policies.SLMS;
import queues.ArrayQueue;
import queues.SLLQueue;

public class Main {

	public static void main(String[] args) {
		
		SLLQueue<Customer> arrivalQueue = new SLLQueue<Customer>();
		SLLQueue<Customer> serviceStartsQueue = new SLLQueue<Customer>();
		SLLQueue<Customer> serviceCompletedQueue = new SLLQueue<Customer>();
		
	
        SLLQueue<SLMS> serv = new SLLQueue<SLMS>();
        SLMS serv1 = new SLMS(arrivalQueue, serviceStartsQueue, serviceCompletedQueue);
        serv1.Service(3);
		System.out.print("Averange Time in System is: ");
		System.out.printf("%.2f");
        
    
	}

	public static ArrayQueue<Customer> copyList (ArrayQueue<Customer> custList) {
		ArrayQueue<Customer> copyOfList = new ArrayQueue<>();
		
		int i = 0;
		while(!(i==custList.size())) {
			
			Customer customer = custList.dequeue();
			Customer cCust = new Customer(customer.getiD(), customer.getArrTime(), customer.getSerTime());
			
			cCust.setDepTime(customer.getDepTime());
			custList.enqueue(customer);
			copyOfList.enqueue(cCust);
			i++;
			
		}
		
		return copyOfList;
	}
	
	

	
}
