package policies;



import java.util.ArrayList;

import customer.Customer;
import queues.SLLQueue;
import servers.Server;

/**
 * 
 * @author JaiTorres13
 * 	Jainel Marie Torres Santos (843-14-8932) (Sec. 030)
 * @author CesarJustiniano 
 *	Cesar Andres Justiniano Santiago (840-15-3720)(Sec. 030)
 *
 */
public class MLMS {
	private SLLQueue<Customer> arrivalQueue, serviceStartsQueue, serviceCompletedQueue;
	private Server[] lines;
	//time input
    private int time;
    
        
    public MLMS(SLLQueue<Customer> arrivalQueue) {
    	this.arrivalQueue = arrivalQueue ;
    	this.serviceStartsQueue =  new SLLQueue<Customer>();
    	this.serviceCompletedQueue  =  new SLLQueue<Customer>() ; 
    	time = 0;
    }
        
    public void Service(int size) throws CloneNotSupportedException {
    	boolean isFirstClient = true;
    	//int iD = 0;
    	lines = new Server[size];
    	
    	for(int i=0;i<size;i++){
    		lines[i] = new Server();
    	}
    	
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			
			if(!arrivalQueue.isEmpty() || numOfWaitingLines() > 0)
			{	
				//setting time equal to the first person that arrives
				if(isFirstClient){
					time = arrivalQueue.first().getArrTime();
					isFirstClient = false;
				}
				
				for(int i=0;i<arrivalQueue.size();i++){
					if(arrivalQueue.first().getArrTime() <= time){
						assignToLine();
						i--;
					}
					else
						i = arrivalQueue.size();
				}
								
				Customer[] jobs = new Customer[size];
				
				for(int i=0;i<size;i++){
					jobs[i] = lines[i].peekFirstInLine();
					
					if(serviceStartsQueue.size() < size && isIndicatedServerAvailable(i) && jobs[i] != null){
						jobs[i].setRecentlyServed(true);
						jobs[i].setWaitingTime(time - jobs[i].getArrTime());
						jobs[i].setDepTime(time + jobs[i].getSerTime());
						serviceStartsQueue.enqueue(lines[i].nextCustomer());
					}
				}
				
				updatingEachM();
			}
			
			if(!serviceStartsQueue.isEmpty()) {
				//this for loop is to make every service post serve once per time
				for(int i=0;i<serviceStartsQueue.size();i++){
					Customer job = serviceStartsQueue.first();
					job.setRecentlyServed(false);

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
			//time skip
			if(!arrivalQueue.isEmpty()){
				if(arrivalQueue.first().getArrTime() - time > 1){
					time = arrivalQueue.first().getArrTime();
				}
			}
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
    
    private void assignToLine(){
   		int index, shortestLine;
    	index = 0;
    	shortestLine = lines[0].lineLength();
        	
        for(int i=1;i<lines.length;i++){
        	if(lines[i].lineLength() < shortestLine){
        		index = i;
        	}
        }
        	
        lines[index].add(arrivalQueue.dequeue(), index);
    }
    
    private int numOfWaitingLines(){
    	int count = 0;
    	
    	for(int i=0;i<lines.length;i++){
    		if(lines[i].isThereLine()){
    			count++;
    		}
    	}
    	return count;
    }
    
    private void updatingEachM() throws CloneNotSupportedException{
    	SLLQueue<Customer> tempQueue = serviceStartsQueue.clone();
    	ArrayList<Customer> tempArray = new ArrayList<Customer>();
    	
    	while(!tempQueue.isEmpty()){
    		Customer job = tempQueue.dequeue();
    		
    		for(int i=0;i<lines.length;i++){
    			if(lines[i].isThereLine()){ // current line is not empty
    				int j = 0;
    				while(lines[i].isThereLine()){
    					tempArray.add(lines[i].nextCustomer());
    					
    					//checking if the attended client arrived later than a client in line
        				if(job.getArrTime() > tempArray.get(j).getArrTime() && job.isRecentlyServed()){ 
        					tempArray.get(j).incrementM();
        				}
        				j++;
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


