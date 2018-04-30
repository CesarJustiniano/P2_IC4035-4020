package policies;

import java.util.ArrayList;

import customer.Customer;
import queues.SLLQueue;
import servers.Server;

public class MLMSBWT {

	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
	private Server[] lines;
	//time input
    private int time;
    
        
    public MLMSBWT(SLLQueue<Customer> arrivalQueue) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  new SLLQueue<Customer>();
    	this.serviceCompletedQueue  =  new SLLQueue<Customer>();
    	time = 0;
    }
        
    public void Service(int size) throws CloneNotSupportedException {
    	boolean isFirstClient = true;
    	int iD = 0;
    	lines = new Server[size];
    	
    	for(int i=0;i<size;i++){
    		lines[i] = new Server();
    	}
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty() || numOfWaitingLines() > 0)
			{	
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = arrivalQueue.first().getArrTime();
					isFirstClient = false;
				}
				
				assignToLinePerWait(iD++);
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){
					jobs[i] = lines[i].peekFirstInLine();
					
					if(serviceStartsQueue.size() != numOfWaitingLines() && isIndicatedServerAvailable(i) && 
							jobs[i] != null){
						jobs[i].setRecentlyServed(true);
						jobs[i].setDepTime(time - jobs[i].getSerTime());
						jobs[i].setWaitingTime(time - jobs[i].getArrTime());
						serviceStartsQueue.enqueue(lines[i].nextCustomer());
					}
					
				}
				
				updatingEachM();
			}
			
			if(!serviceStartsQueue.isEmpty()) {
				//this for loop is to make every service post serve once per time
				for(int i=0;i<serviceStartsQueue.size();i++){
					Customer job = serviceStartsQueue.first();
					job.setRecentlyServed(false);
				
					if(job.getDepTime() <= time) {
						serviceCompletedQueue.enqueue(serviceStartsQueue.dequeue());
						i--;
					}
					else{
						serviceStartsQueue.enqueue(serviceStartsQueue.dequeue());
					}
				}
			}
			
			time++;
			if(!arrivalQueue.isEmpty()){
				if(arrivalQueue.first().getArrTime() - time > 1){
					time = arrivalQueue.first().getArrTime();
				}
			}
		}
		time--;
    }
    
    private boolean isIndicatedServerAvailable(int numLine) throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	
    	while(!tempQueue.isEmpty()){
    		//checking if the server post of the indicated line is still occupy
    		if(numLine == tempQueue.dequeue().getNumLine()){
    			return false;
    		}
    	}
    	return true;
    }
    
    private void assignToLinePerWait(int iD) throws CloneNotSupportedException{
    	int index = 0; 
    	long fastestLine = lines[index].getTotalWaitTime() + serverTimeRemaining(index);
    		
    	for(int i=1;i<lines.length;i++){
    		if(lines[i].getTotalWaitTime(time) + serverTimeRemaining(i) < fastestLine){
               	index = i;
            }
        }
        	
        lines[index].add(arrivalQueue.dequeue(), index, iD);    	
    }
    
    private long serverTimeRemaining(int numServer) throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	
    	while(!tempQueue.isEmpty()){
    		if(numServer == tempQueue.first().getNumLine()){
    			return tempQueue.first().getDepTime() - time;
    		}
    		tempQueue.dequeue();
    	}
    	
    	//the server is currently not serving a customer
    	return 0;
    }
    
    private int numOfWaitingLines(){
    	int count = 0;
    	
    	for(int i=0;i<lines.length;i++){
    		if(lines[i].isThereLine()){
    			count++;
    		}
    	}
    	
    	return count;
    }
    
    private void updatingEachM() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	ArrayList<Customer> tempArray = new ArrayList<Customer>();
    	
    	while(!tempQueue.isEmpty()){
    		Customer job = tempQueue.dequeue();
    		
    		for(int i=0;i<lines.length;i++){
    			if(lines[i].isThereLine()){ // current line is not empty
    				int j = 0;
    				while(lines[i].isThereLine()){
    					tempArray.add(lines[i].nextCustomer());
    					
    					//checking if the attended client arrived later than a client in line
        				if(job.getiD() > tempArray.get(j).getiD() && job.isRecentlyServed()){ 
        					tempArray.get(j).incrementM();
        				}
        				j++;
    				}
    				
    				//returning the clients back to their line in their original order
    				while(!tempArray.isEmpty()){
    					lines[i].addTransfer(tempArray.remove(0), i);
    				}
    			}
    		}
    	}
    }
    
    //Use only when all customers received complete service
    public float getAverageM() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	float m = 0;
    	
    	while(!tempQueue.isEmpty()){
    		m += tempQueue.dequeue().getM();
    	}
    	
    	return m / serviceCompletedQueue.size(); //m
    }

    //Use only when all customers received service
    public float getAverageWaitingTime() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	float sum = 0;
    	
    	while(!tempQueue.isEmpty()){
    		sum += tempQueue.dequeue().getWaitingTime();
    	}
    	
    	return sum / serviceCompletedQueue.size(); //t2
    }

    //Use only when all customers received service
	public int getTime() {
		return time; //t1
	}
	
	//Use only when all customers received complete service
	public int getTotalNumOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}
