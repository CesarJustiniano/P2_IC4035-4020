package policies;



import java.util.ArrayList;

import customer.Customer;
import queues.SLLQueue;
import servers.Server;

public class MLMS {
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
	private ArrayList<Server> arrayServer;
	//time input
    private int time;
    
        
    public MLMS(SLLQueue<Customer> arrivalQueue) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  new SLLQueue<Customer>();
    	this.serviceCompletedQueue  =  new SLLQueue<Customer>() ; 
    	this.arrayServer = new ArrayList<Server>();
    	time = 0;
    }
        
    public void Service(int size) throws CloneNotSupportedException {
    	boolean isFirstClient = true;
    	int iD = 0;
    	
    	//initialing lines
    	for(int i=0;i<size;i++){
    		arrayServer.add(new Server());
    	}
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty() || numOfWaitingLines(arrayServer) > 0)
			{	
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = arrivalQueue.first().getArrTime();
					isFirstClient = false;
				}
				
				assignToLine(arrayServer, iD++, size);
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){
					if(arrayServer.get(i).peekFirstInLine() != null){
						jobs[i] = arrayServer.get(i).peekFirstInLine();
						
						if(jobs[i].getArrTime()<=time && serviceStartsQueue.size() != numOfWaitingLines(arrayServer) && 
								isIndicatedServerAvailable(i)){
							jobs[i].setRecentlyServed(true);
							jobs[i].setWaitingTime(time - jobs[i].getArrTime());
							serviceStartsQueue.enqueue(arrayServer.get(i).nextCustomer());
						}
					}
				}
				
				updatingEachM(arrayServer);
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
    
    private void assignToLine(ArrayList<Server> lines, int iD, int size){
    	if(!arrivalQueue.isEmpty()){
    		int index, shortestLine;
    		index = 0;
    		shortestLine = lines.get(0).lineLength();
        	
        	for(int i=1;i<size;i++){
        		if(lines.get(i).lineLength() < shortestLine){
        			index = i;
        		}
        	}
        	
        	lines.get(index).add(arrivalQueue.dequeue(), index, iD);
    	}
    }
    
    private int numOfWaitingLines(ArrayList<Server> lines){
    	int count = 0;
    	
    	for(int i=0;i<lines.size();i++){
    		if(lines.get(i).isThereLine()){
    			count++;
    		}
    	}
    	
    	return count;
    }
    
    private void updatingEachM(ArrayList<Server> lines) throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	ArrayList<Customer> tempArray = new ArrayList<Customer>();
    	
    	while(!tempQueue.isEmpty()){
    		Customer job = tempQueue.dequeue();
    		
    		for(int i=0;i<lines.size();i++){
    			if(lines.get(i).lineLength() != 0){ // current line is not empty
    				for(int j=0;j<lines.get(i).lineLength();j++){
    					tempArray.add(lines.get(i).nextCustomer());
        				
        				//checking if the attended client arrived later than a client in line
        				if(job.getiD() > tempArray.get(j).getiD() && job.isRecentlyServed()){ 
        					tempArray.get(j).incrementM();
        				}
    				}
    				
    				//returning the clients back to their line in their original order
    				while(!tempArray.isEmpty()){
    					lines.get(i).addTransfer(tempArray.remove(0), i);
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
	public int getTotalOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}


