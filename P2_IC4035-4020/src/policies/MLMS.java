package policies;



import java.util.ArrayList;

import customer.Customer;
import queues.SLLQueue;
import servers.Server;

public class MLMS {
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
	private Server[] policy;
	//time input
    private long time;
    
        
    public MLMS(SLLQueue<Customer> arrivalQueue, SLLQueue<Customer> serviceStartsQueue, 
    		SLLQueue<Customer> serviceCompletedQueue ) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  serviceStartsQueue;
    	this.serviceCompletedQueue  =  serviceCompletedQueue ; 
    	time = 0;
    }
        
    public void Service(int size) throws CloneNotSupportedException {
    	policy = new Server[size];
    	boolean isFirstClient = true;
    	int iD = 0;
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty() || numOfWaitingLines(policy) > 0)
			{	
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = arrivalQueue.first().getArrTime();
					isFirstClient = false;
				}
				
				while(arrivalQueue.first().getArrTime() <= time){
					assignToLine(policy, iD++);
				}
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){
					jobs[i] = policy[i].peekFirstInLine();
					
					if(jobs[i].getArrTime()>=time && serviceStartsQueue.size() != numOfWaitingLines(policy) && 
							isIndicatedServerAvailable(i) && jobs[i] != null){
						jobs[i].setRecentlyServed(true);
						serviceStartsQueue.enqueue(policy[i].nextCustomer());
					}
				}
				
				updatingEachM(policy);
			}
			
			if(!serviceStartsQueue.isEmpty()) {
				//this for loop is to make every service post serve once per time
				for(int i=0;i<serviceStartsQueue.size();i++){
					Customer job = serviceStartsQueue.first();
					job.setSerTime(job.getSerTime() - 1);
					job.setRecentlyServed(false);

				
					if(job.getSerTime() == 0) {
						job.setDepTime(time);
						serviceCompletedQueue.enqueue(serviceStartsQueue.dequeue());
						i--;
					}
					else{
						serviceStartsQueue.enqueue(serviceStartsQueue.dequeue());
					}
				}
			}
			
			time++;
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
    
    private void assignToLine(Server[] line, int iD){
    	if(!arrivalQueue.isEmpty()){
    		int index, shortestLine;
    		index = 0;
    		shortestLine = line[index].lineLength();
        	
        	for(int i=1;i<line.length;i++){
        		if(line[i].lineLength() < shortestLine){
        			index = i;
        		}
        	}
        	
        	line[index].add(arrivalQueue.dequeue(), index, iD);
    	}
    }
    
    private int numOfWaitingLines(Server[] lines){
    	int count = 0;
    	
    	for(int i=0;i<lines.length;i++){
    		if(lines[i].isThereLine()){
    			count++;
    		}
    	}
    	
    	return count;
    }
    
    private void updatingEachM(Server[] lines) throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	ArrayList<Customer> tempArray = new ArrayList<Customer>();
    	
    	while(!tempQueue.isEmpty()){
    		Customer job = tempQueue.dequeue();
    		
    		for(int i=0;i<lines.length;i++){
    			if(lines[i].lineLength() != 0){ // current line is not empty
    				for(int j=0;j<lines[i].lineLength();j++){
    					tempArray.add(lines[i].nextCustomer());
        				
        				//checking if the attended client arrived later than a client in line
        				if(job.getiD() > tempArray.get(j).getiD() && job.isRecentlyServed()){ 
        					tempArray.get(j).incrementM();
        				}
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
    public long getAverageM() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	int m = 0;
    	
    	while(!tempQueue.isEmpty()){
    		m += tempQueue.dequeue().getM();
    	}
    	
    	return m / serviceCompletedQueue.size(); //m
    }
    
    //Use only when all customers received service
    public long getAverageWaitingTime() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	long sum = 0;
    	
    	while(!tempQueue.isEmpty()){
    		sum += tempQueue.dequeue().getWaitingTime();
    	}
    	
    	return sum / serviceCompletedQueue.size(); //t2
    }

    //Use only when all customers received service
	public long getTime() {
		return time; //t1
	}
	
	//Use only when all customers received complete service
	public int getTotalOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}


