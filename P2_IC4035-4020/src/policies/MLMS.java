package policies;



import customer.Customer;
import queues.SLLQueue;
import servers.Server;
import queues.ArrayQueue;

public class MLMS {
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue;
	private ArrayQueue<Customer> serviceCompletedQueue;
	private Server[] policy;
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
//    	int[] line = new int[size];
//    	
//    	for(int i=0;i<line.length;i++){
//    		line[i] = 0;
//    	}
    	
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
			}
			time++;
		}
    }
    
    public void assignToLine(Server[] line){
    	if(!arrivalQueue.isEmpty()){
    		int index = 0;
        	
        	for(int i=1;i<line.length;i++){
        		if(line[i].lineLength() < line[i-1].lineLength()){
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
    
    public ArrayQueue<Customer> getServiceCompletedQueue() {
		return serviceCompletedQueue;
	}

	public int getTime() {
		return time;
	}
}


