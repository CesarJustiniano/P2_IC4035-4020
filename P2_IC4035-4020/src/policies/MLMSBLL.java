package policies;

import customer.Customer;
import queues.ArrayQueue;
import queues.SLLQueue;
import servers.Server;

public class MLMSBLL {

	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue;
	private ArrayQueue<Customer> serviceCompletedQueue;
	private Server[] policy;
	//time input
    private int time;
    
        
    public MLMSBLL(SLLQueue<Customer> arrivalQueue, SLLQueue<Customer> serviceStartsQueue, 
        		ArrayQueue<Customer> serviceCompletedQueue ) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  serviceStartsQueue;
    	this.serviceCompletedQueue  =  serviceCompletedQueue ; 
    	time = 0;
    }
        
    public void Service(int size) {
    	policy = new Server[size];
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!serviceStartsQueue.isEmpty()) {
				Customer job = serviceStartsQueue.first();
				job.setSerTime(job.getSerTime() - 1);
				
					if(job.getSerTime() == 0) {
						job.setDepTime(time);
						serviceCompletedQueue.enqueue(serviceStartsQueue.dequeue());
					}
					else{
						serviceStartsQueue.enqueue(serviceStartsQueue.dequeue());
					}
			}
			
			if(!arrivalQueue.isEmpty() || numOfWaitingLines(policy) > 0)
			{	
				assignToLine(policy);
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){
					jobs[i] = policy[i].peekFirstInLine();
					
					if(jobs[i].getArrTime()>=time && serviceStartsQueue.size() != numOfWaitingLines(policy)){
						serviceStartsQueue.enqueue(policy[i].nextCustomer());
					}
				}
				
				balancingLinesLength(policy);
			}
			time++;
		}
    }
    
    public void assignToLine(Server[] line){
    	if(!arrivalQueue.isEmpty()){
    		int index, shortestLine;
    		index = 0;
    		shortestLine = line[index].lineLength();
    		
        	for(int i=1;i<line.length;i++){
        		if(line[i].lineLength() < shortestLine){
        			index = i;
        		}
        	}
        	
        	line[index].add(arrivalQueue.dequeue());
    	}
    }
    
    public int numOfWaitingLines(Server[] lines){
    	int count = 0;
    	
    	for(int i=0;i<lines.length;i++){
    		if(lines[i].isThereLine()){
    			count++;
    		}
    	}
    	
    	return count;
    }
    
    public void balancingLinesLength(Server[] lines){
    	int longestLine, longIndex, shortestLine, shortIndex, longCount;
    	
    	do{
    		longIndex = shortIndex = 0;
    		longCount = 1;
        	longestLine = shortestLine = lines[0].lineLength();
        	
        	for(int i=1;i<lines.length;i++){
        		//looking for shortest line
        		if(lines[i].lineLength() < shortestLine){
        			shortIndex = i;
        			shortestLine = lines[i].lineLength();
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
        		
        		lines[shortIndex].add(lines[earlyIndex].transferCustomer());
        	}
        	
    	}while(longestLine - shortestLine > 1);
    }
    
    public ArrayQueue<Customer> getServiceCompletedQueue() {
		return serviceCompletedQueue;
	}

	public int getTime() {
		return time;
	}
}
