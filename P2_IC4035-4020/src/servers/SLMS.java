
package servers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import customer.Pair;
import queues.SLLQueue;
import queues.ArrayQueue;

public class SLMS {

	public static void main(String[] args) {

		
		
		SLLQueue<Pair> arrivalQueue = new SLLQueue<Pair>();
		SLLQueue<Pair> serviceStartsQueue = new SLLQueue<Pair>();
		ArrayQueue<Pair> serviceCompletedQueue = new ArrayQueue<Pair>();
		
	
            
        //time input
        int time = 0;
        
		while(!arrivalQueue.isEmpty() || !serviceStartsQueue.isEmpty() ) {
			if(!serviceStartsQueue.isEmpty()) {
				Pair job = serviceStartsQueue.first();
				job.setSerTime(job.getSerTime() - 1);
				
					if(job.getSerTime() == 0) {
						job.setDepTime(time);
						serviceCompletedQueue.enqueue(serviceStartsQueue.dequeue());
					}
					else {
						serviceStartsQueue.enqueue(serviceStartsQueue.dequeue());
					}
			}
			
			if(!arrivalQueue.isEmpty())
			{
				Pair job1 = arrivalQueue.first();
				if(job1.getArrTime()==time)
					serviceStartsQueue.enqueue(arrivalQueue.dequeue());
			}
			time++;
			
		}
		

	}
}
