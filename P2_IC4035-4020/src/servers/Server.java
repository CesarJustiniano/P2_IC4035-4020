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
	
	public void add(Customer client){
		lineQueue.enqueue(client);
	}
	
	public Customer peekFirstInLine(){
		return lineQueue.first();
	}
	
	public Customer nextCustomer(){
		return lineQueue.dequeue();
	}
	
	public int lineLength(){
		return lineQueue.size();
	}
	
}
