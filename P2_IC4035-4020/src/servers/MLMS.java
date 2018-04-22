package servers;

import customer.Customer;
import queues.SLLQueue;
import queues.ArrayQueue;

public class MLMS {
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue;
	private ArrayQueue<Customer> serviceCompletedQueue;
	//time input
    private int time;
        
    public MLMS(SLLQueue<Customer> arrivalQueue, SLLQueue<Customer> serviceStartsQueue, 
        		ArrayQueue<Customer> serviceCompletedQueue ) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  serviceStartsQueue;
    	this.serviceCompletedQueue  =  serviceCompletedQueue ; 
    	time = 0;
    }
        
    public void Service(int size) {
    	int[] line = new int[size];
    	
    	for(int i=0;i<line.length;i++){
    		line[i] = 0;
    	}
    	
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
			
			if(!arrivalQueue.isEmpty())
			{
				Customer job1 = arrivalQueue.first();
				if(job1.getArrTime()>=time && serviceStartsQueue.size() < numOfWaitingLines(line) ||
						serviceStartsQueue.size() == 0){
					serviceStartsQueue.enqueue(arrivalQueue.dequeue());
					line[shortestLine(line)]++;
				}
			}
			time++;
			
		}
    }
    
    public static int shortestLine(int[] line){
    	int index = 0;
    	
    	for(int i=1;i<line.length;i++){
    		if(line[i] < line[i-1] ){
    			index = i;
    		}
    	}
    	
    	return index;
    }
    
    public static int numOfWaitingLines(int[] lines){
    	int count = 0;
    	
    	for(int i=0;i<lines.length;i++){
    		if(lines[i] > 0){
    			count++;
    		}
    	}
    	
    	return count;
    }
    
    public ArrayQueue<Customer> getServiceCompletedQueue() {
		return serviceCompletedQueue;
	}

	public int getTime() {
		return time;
	}
}


