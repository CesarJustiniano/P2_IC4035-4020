
package policies;

import customer.Customer;
import queues.SLLQueue;

public class SLMS {	
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
    //time input
    private long time;
        
    public SLMS(SLLQueue<Customer> arrivalQueue, SLLQueue<Customer> serviceStartsQueue, 
    		SLLQueue<Customer> serviceCompletedQueue ) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  serviceStartsQueue;
   		this.serviceCompletedQueue  =  serviceCompletedQueue ; 
   		time = 0;
   	}
        
    public void Service(int size) {
    	boolean isFirstClient = true;
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty())
			{
				Customer job1 = arrivalQueue.first();
				
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = job1.getArrTime();
					isFirstClient = false;
				}
				
				if(job1.getArrTime()>=time && serviceStartsQueue.size() != size){
					serviceStartsQueue.enqueue(arrivalQueue.dequeue());
					
				}
			}
			
			if(!serviceStartsQueue.isEmpty()) {
				//this for loop is to make every service post serve once per time
				for(int i=0;i<serviceStartsQueue.size();i++){
					Customer job = serviceStartsQueue.first();
					job.setSerTime(job.getSerTime() - 1);
					
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
	}
    
    //Use only when all customers received complete service
    public long getAverageM(){
    	return 0; //the result will always be 0 because it will always be one line
    }
    
    //Use only when all customers received complete service
    public long getAverageWaitingTime() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	long sum = 0;
    	
    	while(!tempQueue.isEmpty()){
    		sum += tempQueue.dequeue().getWaitingTime();
    	}
    	
    	return sum / serviceCompletedQueue.size(); //t2
    }

    //Use only when all customers received complete service
	public long getTime() {
		return time; //t1
	}
	
    //Use only when all customers received complete service
	public int getTotalNumOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}
