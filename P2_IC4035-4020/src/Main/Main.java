package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import customer.Customer;
import queues.ArrayQueue;
import queues.SLLQueue;
import servers.SLMS;

public class Main {

	public static void main(String[] args) {
		
		SLLQueue<Customer> arrivalQueue = new SLLQueue<Customer>();
		SLLQueue<Customer> serviceStartsQueue = new SLLQueue<Customer>();
		ArrayQueue<Customer> serviceCompletedQueue = new ArrayQueue<Customer>();
		
		SLMS[] firstPolicy = {null,null,null};
		
		String directory = "Lab6";
		String fileName = "input.txt"; 
		
		File inputfile = new File(directory, fileName);
		BufferedReader dataReader = null;
        String dataline;
        int arrTime = 0;
        int serTime =0;
        try {

            dataReader = new BufferedReader(new FileReader(inputfile));
            while ((dataline = dataReader.readLine()) != null) {

               String[] data =dataline.split("\t");             
              
               arrTime = Integer.parseInt(data[0]);
               serTime = Integer.parseInt(data[1].substring(data[1].length()-1));
               Customer element = new Customer(0, arrTime, serTime);
              
              arrivalQueue.enqueue(element);  
              
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataReader != null) {
                try {
                    dataReader.close();
                } catch (IOException e) {
                    e.printStackTrace();}
            }
        }
        
        for(int i=0;i<firstPolicy.length;i++){
        	firstPolicy[i] = new SLMS(arrivalQueue, serviceStartsQueue, serviceCompletedQueue);
        	//second policy
        	//third policy
        	//fourth policy
        }        
        
        for(int i=0;i<firstPolicy.length;i++){
        	firstPolicy[i].Service(2*i + 1);
        	//second policy
        	//third policy
        	//fourth policy
        }        
	}
	
	
	public static ArrayQueue<Customer> copyOf (ArrayQueue<Customer> list) {
		ArrayQueue<Customer> copy = new ArrayQueue<>();
		
		int i = 0;
		while(!(i==list.size())) {
			Customer c = list.dequeue();
			
			Customer copyC = new Customer();
			
			copyC.setArrTime(c.getArrTime());
			copyC.setDepTime(c.getDepTime());
			copyC.setiD(c.getiD());
			copyC.setSerTime(c.getSerTime());
			
			list.enqueue(c);
			copy.enqueue(copyC);
			i++;
		}
		
		return copy;
	}
	
	public static float time(ArrayQueue<Customer> serviceCompletedQueue ) {
		   //Calculates time in system
  		float totalTime = 0;
  		float arrVal = 0;
  		float depVal = 0;
  		float count = 0;
  		
  		while(!(serviceCompletedQueue .isEmpty())) {
  			arrVal = serviceCompletedQueue.first().getArrTime();
  			depVal = serviceCompletedQueue.first().getDepTime();
  			totalTime= (depVal-arrVal) + totalTime;
  			count++;
  	
  			serviceCompletedQueue .dequeue();
  		}
  		totalTime= totalTime/count;
  		
//  		System.out.print("Averange Time in System is: ");
//  		System.out.printf("%.2f", totalTime);
  	
  		return totalTime;
	}

	//don't know if to use it
	public Customer dataReader() {
		return null;
	}
}