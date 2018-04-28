package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import customer.Customer;
import dataReader.DataReader;
import policies.MLMS;
import policies.MLMSBLL;
import policies.MLMSBWT;
import policies.SLMS;
import queues.ArrayQueue;
import queues.SLLQueue;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		DataReader datar = new DataReader();
		datar.readDataFiles();
		SLLQueue<Customer> arrivalQueue = datar.readDataFiles();
		SLLQueue<Customer> serviceStartsQueue = new SLLQueue<Customer>();
		SLLQueue<Customer> serviceCompletedQueue = new SLLQueue<Customer>();
		
		 SLLQueue<Customer> arrivalQueue1 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue1 = new SLLQueue<Customer>();
		
		 SLLQueue<Customer> arrivalQueue2 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue2 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue3 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue3 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue4 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue4 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue5 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue5 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue6 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue6 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue7 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue7 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue8 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue8 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue9 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue9 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue10 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue10 = new SLLQueue<Customer>();
		
		SLLQueue<Customer> arrivalQueue11 = copyList(arrivalQueue);
		SLLQueue<Customer> serviceCompletedQueue11 = new SLLQueue<Customer>();
		
			
		
	
        SLMS slms1 = new SLMS(arrivalQueue, serviceStartsQueue, serviceCompletedQueue);
        slms1.Service(1);
        SLMS slms3 = new SLMS(arrivalQueue1, serviceStartsQueue, serviceCompletedQueue1);
        slms3.Service(3);
        SLMS slms5 = new SLMS(arrivalQueue2, serviceStartsQueue, serviceCompletedQueue2);
        slms5.Service(5);
        
        MLMS mlms1 = new MLMS(arrivalQueue3, serviceStartsQueue, serviceCompletedQueue3);
        mlms1.Service(1);
        MLMS mlms3 = new MLMS(arrivalQueue4, serviceStartsQueue, serviceCompletedQueue4);
        mlms3.Service(3);
        MLMS mlms5 = new MLMS(arrivalQueue5, serviceStartsQueue, serviceCompletedQueue5);
        mlms5.Service(5);
        
        MLMSBLL mlmsbll1 = new MLMSBLL(arrivalQueue6, serviceStartsQueue, serviceCompletedQueue6);
        mlmsbll1.Service(1);
        MLMSBLL mlmsbll3 = new MLMSBLL(arrivalQueue7, serviceStartsQueue, serviceCompletedQueue7);
        mlmsbll3.Service(3);
        MLMSBLL mlmsbll5 = new MLMSBLL(arrivalQueue8, serviceStartsQueue, serviceCompletedQueue8);
        mlmsbll5.Service(5);
        
        MLMSBWT mlmsbwt1 = new MLMSBWT(arrivalQueue9, serviceStartsQueue, serviceCompletedQueue9);
        mlmsbwt1.Service(1);
        MLMSBWT mlmsbwt3 = new MLMSBWT(arrivalQueue10, serviceStartsQueue, serviceCompletedQueue10);
        mlmsbwt3.Service(3);
        MLMSBWT mlmsbwt5 = new MLMSBWT(arrivalQueue11, serviceStartsQueue, serviceCompletedQueue11);
        mlmsbwt5.Service(5);
        
	
		String parentDirectory = "inputFiles";   // must exist in current directory

		// create all the files for testing and grading with random integer values as
		// content. Each such file represents a set, since there is no repetition of
		// values. Some might end being empty...
		for (int i=0; i < 10; i++) {
				String fileName = "data_" + i + "_OUT.txt" ;
				PrintWriter out = new PrintWriter(new File(parentDirectory, fileName)); 
				out.println("Number of Customers: " + serviceCompletedQueue.size());
				
				out.println("SLMS 1:\t" + slms1.getTime() + "\t" + slms1.getAverageWaitingTime() +  "\t" + slms1.getAverageOfM());
				out.println("SLMS 3:\t" + slms3.getTime() + "\t" + slms3.getAverageWaitingTime() +  "\t" + slms3.getAverageOfM());
				out.println("SLMS 5:\t" + slms5.getTime() + "\t" + slms5.getAverageWaitingTime() +  "\t" + slms5.getAverageOfM());
				
				out.println("MLMS 1:\t" + mlms1.getTime() + "\t" + mlms1.getAverageWaitingTime() +  "\t" + mlms1.getAverageOfM());
				out.println("MLMS 3:\t" + mlms3.getTime() + "\t" + mlms3.getAverageWaitingTime() +  "\t" + mlms3.getAverageOfM());
				out.println("MLMS 5:\t" + mlms5.getTime() + "\t" + mlms5.getAverageWaitingTime() +  "\t" + mlms5.getAverageOfM());
				
				out.println("MLMSBLL 1:\t" + mlmsbll1.getTime() + "\t" + mlmsbll1.getAverageWaitingTime() +  "\t" + mlmsbll1.getAverageOfM());
				out.println("MLMSBLL 3:\t" + mlmsbll3.getTime() + "\t" + mlmsbll3.getAverageWaitingTime() +  "\t" + mlmsbll3.getAverageOfM());
				out.println("MLMSBLL 5:\t" + mlmsbll5.getTime() + "\t" + mlmsbll5.getAverageWaitingTime() +  "\t" + mlmsbll5.getAverageOfM());
				
				
				out.println("MLMSBWT 1:\t" + mlmsbwt1.getTime() + "\t" + mlmsbwt1.getAverageWaitingTime() +  "\t" + mlmsbwt1.getAverageOfM());
				out.println("MLMSBWT 3:\t" + mlmsbwt3.getTime() + "\t" + mlmsbwt3.getAverageWaitingTime() +  "\t" + mlmsbwt3.getAverageOfM());
				out.println("MLMSBWT 5:\t" + mlmsbwt5.getTime() + "\t" + mlmsbwt5.getAverageWaitingTime() +  "\t" + mlmsbwt5.getAverageOfM());
				out.close();
		}
	}
        
    
	

	public static SLLQueue<Customer> copyList (SLLQueue<Customer> custList) {
		SLLQueue<Customer> copyOfList = new SLLQueue<>();
		
		int j = 0;
		while(!(j==custList.size())) {
			
			Customer customer = custList.dequeue();
			Customer cCust = new Customer(customer.getiD(), customer.getArrTime(), customer.getSerTime());
			
			cCust.setDepTime(customer.getDepTime());
			custList.enqueue(customer);
			copyOfList.enqueue(cCust);
			j++;
			
		}
		
		return copyOfList;
	}
	
}
