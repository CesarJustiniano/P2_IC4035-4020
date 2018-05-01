package servers;

import customer.Customer;
import queues.SLLQueue;

/**
 * 
 * @author JaiTorres13
 * 	Jainel Marie Torres Santos (843-14-8932) (Sec. 030)
 * @author CesarJustiniano 
 *	Cesar Andres Justiniano Santiago (840-15-3720)(Sec. 030)
 *
 */
public class Server {
	private SLLQueue<Customer> lineQueue;
	
	public Server(){
		lineQueue = new SLLQueue<Customer>();
	}
	
	public boolean isThereLine(){
		return !lineQueue.isEmpty();
	}
	
	public void add(Customer client, int numLine, int iD){
		Customer c = client;
		c.setLine(numLine);
		c.setiD(iD);
		lineQueue.enqueue(c);
	}
	
	public void addTransfer(Customer client, int numLine){
		Customer c = client;
		c.setLine(numLine);
		lineQueue.enqueue(c);
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
	
	public long getTotalWaitTime() throws CloneNotSupportedException{
		SLLQueue<Customer> tempQueue = lineQueue.clone();
		long sum = 0;
		
		while(!tempQueue.isEmpty()){
			sum += tempQueue.dequeue().getWaitingTime();
		}
		
		return sum;
	}
	
	public long getTotalWaitTime(int time) throws CloneNotSupportedException{
		SLLQueue<Customer> tempQueue = lineQueue.clone();
		long sum = 0;
		
		while(!tempQueue.isEmpty()){
			sum += tempQueue.dequeue().getWaitingTime(time);
		}
		
		return sum;
	}
}
