
package policies;

import customer.Customer;
import queues.SLLQueue;
import queues.ArrayQueue;

public class SLMS {	
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue;
	private ArrayQueue<Customer> serviceCompletedQueue;
    //time input
    private int time;
        
    public SLMS(SLLQueue<Customer> arrivalQueue, SLLQueue<Customer> serviceStartsQueue, 
        		ArrayQueue<Customer> serviceCompletedQueue ) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  serviceStartsQueue;
   		this.serviceCompletedQueue  =  serviceCompletedQueue ; 
   		time = 0;
   	}
        
    public void Service(int size) {
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
				if(job1.getArrTime()>=time && serviceStartsQueue.size() != size)
					serviceStartsQueue.enqueue(arrivalQueue.dequeue());
			}
			
			time++;	
		}
	}

	public ArrayQueue<Customer> getServiceCompletedQueue() {
		return serviceCompletedQueue;
	}

	public int getTime() {
		return time;
	}
}
