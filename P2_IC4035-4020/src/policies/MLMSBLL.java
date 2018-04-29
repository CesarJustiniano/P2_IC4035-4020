package policies;

import java.util.ArrayList;

import customer.Customer;
import queues.SLLQueue;
import servers.Server;

public class MLMSBLL {

	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
	private Server[] policy;
	//time input
    private long time;
    
        
    public MLMSBLL(SLLQueue<Customer> arrivalQueue) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  new SLLQueue<Customer>();
    	this.serviceCompletedQueue  =  new SLLQueue<Customer>() ; 
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
				
				assignToLine(policy, iD++);
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){ 
					jobs[i] = policy[i].peekFirstInLine();
					
					if(jobs[i].getArrTime()>=time && serviceStartsQueue.size() != numOfWaitingLines(policy) && 
							isIndicatedServerAvailable(i) && jobs[i] != null){
						jobs[i].setRecentlyServed(true);
						serviceStartsQueue.enqueue(policy[i].nextCustomer());
					}
				}
				
				balancingLinesLength(policy);
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
    
    private void balancingLinesLength(Server[] lines){
    	int longestLine, longIndex, shortestLine, shortIndex, longCount, shortCount;
    	
    	do{
    		longIndex = shortIndex = 0;
    		longCount = shortCount = 1;
        	longestLine = shortestLine = lines[0].lineLength();
        	
        	for(int i=1;i<lines.length;i++){
        		//looking for shortest line
        		if(lines[i].lineLength() < shortestLine){
        			shortIndex = i;
        			shortestLine = lines[i].lineLength();
        			shortCount = 1;
        		}
        		else if(lines[i].lineLength() == shortestLine){
        			shortCount++;
        		}
        		
        		//looking for longest line
        		if(lines[i].lineLength() > longestLine){
        			longIndex = i;
        			longestLine = lines[i].lineLength();
        			longCount = 1;
        		}
        		else if(lines[i].lineLength() == longestLine)
        			longCount++;
        	}
        	
        	if(longestLine - shortestLine > 1){ //The lines can be balanced
        		int earlyIndex = longIndex;
        		
        		if(longCount > 1){ //There is more than one line with the longest amount of customers
            		long earliestArrival = lines[longIndex].peekLastInLine().getArrTime();
        			for(int i=0;i<lines.length;i++){
            			//looking for the earliest last customer among the longest lines
            			if(lines[i].peekLastInLine().getArrTime() < earliestArrival && lines[i].lineLength() == longestLine){
            				earliestArrival = lines[i].peekLastInLine().getArrTime();
            				earlyIndex = i;
            			}
            		}
        		}
        		
        		if(shortCount > 1){ //there is more than one line with the shortest amount of customers
        			boolean isFound = false;
        			
        			//Looking for the shortest and closest line from right to left
        			for(int i=earlyIndex;i<lines.length;i++){
        				if(lines[i].lineLength() == shortestLine && !isFound){
        					shortIndex = i;
        					isFound = true;
        				}
        			}
        			
        			if(!isFound){
        				for(int i=0;i<lines.length;i++){
            				if(lines[i].lineLength() == shortestLine && !isFound){
            					shortIndex = i;
            					isFound = true;
            				}
            			}
        			}
        		}
        		
        		lines[shortIndex].addTransfer(lines[earlyIndex].transferCustomer(), shortIndex);
        	}
        	
    	}while(longestLine - shortestLine > 1);
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
	public int getTotalNumOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}
