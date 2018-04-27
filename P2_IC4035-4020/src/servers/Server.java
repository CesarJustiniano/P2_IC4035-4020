package servers;

import customer.Customer;
import queues.SLLQueue;

public class Server {
	private SLLQueue<Customer> lineQueue;
	
	public Server(){
		lineQueue = new SLLQueue<Customer>();
	}
	
	public boolean isThereLine(){
		return !lineQueue.isEmpty();
	}
	
	public void add(Customer client, int numLine){
		client.setLine(numLine);
		lineQueue.enqueue(client);
	}
	
	public Customer peekFirstInLine(){
		return lineQueue.first();
	}
	
	public Customer peekLastInLine(){
		return lineQueue.last();
	}
	
	public Customer nextCustomer(){
		return lineQueue.dequeue();
	}
	
	public Customer transferCustomer(){
		return lineQueue.dequeueLast();
	}
	
	public int lineLength(){
		return lineQueue.size();
	}
	
	public long getTotalWaitTime(){
		SLLQueue<Customer> tempQueue = lineQueue;
		long sum = 0;
		
		while(!tempQueue.isEmpty()){
			sum += tempQueue.dequeue().getWaitingTime();
		}
		
		return sum;
	}
	
}
