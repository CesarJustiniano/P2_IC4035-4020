
package servers;

import customer.Customer;
import queues.SLLQueue;
import queues.ArrayQueue;

public class SLMS {

		
		SLLQueue<Customer> arrivalQueue = new SLLQueue<Customer>();
		SLLQueue<Customer> serviceStartsQueue = new SLLQueue<Customer>();
		ArrayQueue<Customer> serviceCompletedQueue = new ArrayQueue<Customer>();
        //time input
        int time = 0;
        
        public SLMS(SLLQueue<Customer> arrivalQueue, 	SLLQueue<Customer> serviceStartsQueue, 
        		ArrayQueue<Customer> serviceCompletedQueue ) {
    		 this.arrivalQueue = arrivalQueue ;
    		this.serviceStartsQueue =  serviceStartsQueue;
    		this. serviceCompletedQueue  =  serviceCompletedQueue ; 
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
}
