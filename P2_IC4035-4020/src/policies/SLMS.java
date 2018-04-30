
package policies;


import customer.Customer;
import queues.SLLQueue;

public class SLMS {	
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
    //time input
    private int time;
        
    public SLMS(SLLQueue<Customer> arrivalQueue) {
    	this.arrivalQueue = arrivalQueue ;
    	serviceStartsQueue =  new SLLQueue<Customer>();
   		serviceCompletedQueue  =  new SLLQueue<Customer>(); 
   		time = 0;
   	}
        
    public void Service(int size){
    	boolean isFirstClient = true;
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty())
			{
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = arrivalQueue.first().getArrTime();
					isFirstClient = false;
				}
				
				for(int i=0;i<size;i++){
					if(!arrivalQueue.isEmpty()){
						Customer job1 = arrivalQueue.first();
						
						if(job1.getArrTime()<=time && serviceStartsQueue.size() < size){
							job1.setWaitingTime(time - job1.getArrTime());
							job1.setDepTime(time + job1.getSerTime());
							serviceStartsQueue.enqueue(arrivalQueue.dequeue());
						}
					}
				}
			}
			
			if(!serviceStartsQueue.isEmpty()) {
				//this for loop is to make every service post serve once per time
				for(int i=0;i<serviceStartsQueue.size();i++){
					Customer job = serviceStartsQueue.first();
					
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
    
    //Use only when all customers received complete service
    public float getAverageM() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	float m = 0;
    	
    	while(!tempQueue.isEmpty()){
    		m += tempQueue.dequeue().getM();
    	}
    	
    	return m / serviceCompletedQueue.size(); //m
    }
    
    //Use only when all customers received complete service
    public float getAverageWaitingTime() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceCompletedQueue.clone();
    	float sum = 0;
    	
    	while(!tempQueue.isEmpty()){
    		sum += tempQueue.dequeue().getWaitingTime();
    	}
    	
    	return sum / serviceCompletedQueue.size(); //t2
    }

    //Use only when all customers received complete service
	public int getTime() {
		return time; //t1
	}
	
    //Use only when all customers received complete service
	public int getTotalNumOfCustomer(){
		return serviceCompletedQueue.size(); //n
	}
}
