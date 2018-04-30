package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import customer.Customer;
import dataReader.DataReader;
import policies.MLMS;
import policies.MLMSBLL;
import policies.MLMSBWT;
import policies.SLMS;
import queues.SLLQueue;

/**
 * 
 * @author JaiTorres13
 * 	Jainel Marie Torres Santos (843-14-8932) (Sec. 030)
 * @author CesarJustiniano 
 *	Cesar Andres Justiniano Santiago (840-15-3720)(Sec. 030)
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		DataReader datar = new DataReader();
//		SLLQueue<Customer> arrivalQueue = datar.readDataFiles();
		String parentDirectory = "inputFiles";

		Scanner inputFile = new Scanner(new File(parentDirectory, "dataFiles.txt")); 
		try {
			
			/*
			 * iterates through all the names in dataFiles.txt
			 */
			while (inputFile.hasNextLine()) {
				SLLQueue<Customer> arrivalQueue = datar.readDataFiles(inputFile.next());
				if(arrivalQueue.isEmpty()) throw new IndexOutOfBoundsException();
				//if(arrivalQueue != null){
				SLMS slms1 = new SLMS(arrivalQueue);
				slms1.Service(1);
				//System.out.println("SLMS 1:\t" + slms1.getTime() + "\t" + slms1.getAverageWaitingTime() +  "\t" + slms1.getAverageM());
				SLMS slms3 = new SLMS(arrivalQueue.clone());
				slms3.Service(3);
				SLMS slms5 = new SLMS(arrivalQueue.clone());
				slms5.Service(5);

				MLMS mlms1 = new MLMS(arrivalQueue.clone());
				mlms1.Service(1);
				MLMS mlms3 = new MLMS(arrivalQueue.clone());
				mlms3.Service(3);
				MLMS mlms5 = new MLMS(arrivalQueue.clone());
				mlms5.Service(5);

				MLMSBLL mlmsbll1 = new MLMSBLL(arrivalQueue.clone());
				mlmsbll1.Service(1);
				MLMSBLL mlmsbll3 = new MLMSBLL(arrivalQueue.clone());
				mlmsbll3.Service(3);
				MLMSBLL mlmsbll5 = new MLMSBLL(arrivalQueue.clone());
				mlmsbll5.Service(5);

				MLMSBWT mlmsbwt1 = new MLMSBWT(arrivalQueue.clone());
				mlmsbwt1.Service(1);
				MLMSBWT mlmsbwt3 = new MLMSBWT(arrivalQueue.clone());
				mlmsbwt3.Service(3);
				MLMSBWT mlmsbwt5 = new MLMSBWT(arrivalQueue.clone());
				mlmsbwt5.Service(5);




					/*
					 * creates the ouput file for good files in dataFiles.txt
					 */
					String fileName = inputFile.next().substring(0, inputFile.next().length() - 4) + "_OUT.txt" ;
					PrintWriter out = new PrintWriter(new File(parentDirectory, fileName)); 
					out.println("Number of Customers: " + arrivalQueue.size());

					out.println("SLMS 1:\t" + slms1.getTime() + "\t" + slms1.getAverageWaitingTime() +  "\t" + slms1.getAverageM());
					out.println("SLMS 3:\t" + slms3.getTime() + "\t" + slms3.getAverageWaitingTime() +  "\t" + slms3.getAverageM());
					out.println("SLMS 5:\t" + slms5.getTime() + "\t" + slms5.getAverageWaitingTime() +  "\t" + slms5.getAverageM());

					out.println("MLMS 1:\t" + mlms1.getTime() + "\t" + mlms1.getAverageWaitingTime() +  "\t" + mlms1.getAverageM());
					out.println("MLMS 3:\t" + mlms3.getTime() + "\t" + mlms3.getAverageWaitingTime() +  "\t" + mlms3.getAverageM());
					out.println("MLMS 5:\t" + mlms5.getTime() + "\t" + mlms5.getAverageWaitingTime() +  "\t" + mlms5.getAverageM());

					out.println("MLMSBLL 1:\t" + mlmsbll1.getTime() + "\t" + mlmsbll1.getAverageWaitingTime() +  "\t" + mlmsbll1.getAverageM());
					out.println("MLMSBLL 3:\t" + mlmsbll3.getTime() + "\t" + mlmsbll3.getAverageWaitingTime() +  "\t" + mlmsbll3.getAverageM());
					out.println("MLMSBLL 5:\t" + mlmsbll5.getTime() + "\t" + mlmsbll5.getAverageWaitingTime() +  "\t" + mlmsbll5.getAverageM());

					out.println("MLMSBWT 1:\t" + mlmsbwt1.getTime() + "\t" + mlmsbwt1.getAverageWaitingTime() +  "\t" + mlmsbwt1.getAverageM());
					out.println("MLMSBWT 3:\t" + mlmsbwt3.getTime() + "\t" + mlmsbwt3.getAverageWaitingTime() +  "\t" + mlmsbwt3.getAverageM());
					out.println("MLMSBWT 5:\t" + mlmsbwt5.getTime() + "\t" + mlmsbwt5.getAverageWaitingTime() +  "\t" + mlmsbwt5.getAverageM());
					out.close();
				
			} 
		} catch (FileNotFoundException e) {
			/*
			 * creates an output file for non existing files in dataFiles.txt
			 */
			String fileName = inputFile.next().substring(0, inputFile.next().length() - 4) + "_OUT.txt" ;
			PrintWriter out = new PrintWriter(new File(parentDirectory, fileName)); 
			out.println("Input file not found.");
		} catch (IndexOutOfBoundsException e) {
			/*
			 * creates an output file for bad  files in dataFiles.txt
			 */
			String fileName = inputFile.next().substring(0, inputFile.next().length() - 4) + "_OUT.txt" ;
			PrintWriter out = new PrintWriter(new File(parentDirectory, fileName)); 
			out.println("Input file does not meet the expected format or it is empty");
		} finally {
			if ( inputFile != null) {
				inputFile.close();
			}
		}
		inputFile.close();	
		
//		Scanner scan = new Scanner(System.in);
//		int m,n;
//		SLLQueue<Customer> arrivalQueue = new SLLQueue<Customer>();
//		
//		for(int i=0;i<6;i++){
//			m = scan.nextInt();
//			n = scan.nextInt();
//			
//			Customer client = new Customer(m, n);
//			arrivalQueue.enqueue(client);
//		}
//		
//		
//		System.out.println("Number of clients is: " +  arrivalQueue.size());
//		SLMS slms1 = new SLMS(arrivalQueue.clone());
//		SLMS slms3 = new SLMS(arrivalQueue.clone());
//		SLMS slms5 = new SLMS(arrivalQueue.clone());
//
//		
//		slms1.Service(1);
//		System.out.println("SLMS 1:\t" + slms1.getTime() + "\t" + slms1.getAverageWaitingTime() +  "\t" + slms1.getAverageM());
//		slms3.Service(3);
//		System.out.println("SLMS 3:\t" + slms3.getTime() + "\t" + slms3.getAverageWaitingTime() +  "\t" + slms3.getAverageM());
//		slms5.Service(5);
//		System.out.println("SLMS 5:\t" + slms5.getTime() + "\t" + slms5.getAverageWaitingTime() +  "\t" + slms5.getAverageM());
//
//		System.out.println();
//		
//		MLMS mlms1 = new MLMS(arrivalQueue.clone());
//		MLMS mlms3 = new MLMS(arrivalQueue.clone());
//		MLMS mlms5 = new MLMS(arrivalQueue.clone());
//		
//		mlms1.Service(1);
//		System.out.println("MLMS 1:\t" + mlms1.getTime() + "\t" + mlms1.getAverageWaitingTime() +  "\t" + mlms1.getAverageM());
//		mlms3.Service(3);
//		System.out.println("MLMS 3:\t" + mlms3.getTime() + "\t" + mlms3.getAverageWaitingTime() +  "\t" + mlms3.getAverageM());
//		mlms5.Service(5);
//		System.out.println("MLMS 5:\t" + mlms5.getTime() + "\t" + mlms5.getAverageWaitingTime() +  "\t" + mlms5.getAverageM());
//
//		System.out.println();
//		
//		MLMSBLL mlmsbll1 = new MLMSBLL(arrivalQueue.clone());
//		MLMSBLL mlmsbll3 = new MLMSBLL(arrivalQueue.clone());
//		MLMSBLL mlmsbll5 = new MLMSBLL(arrivalQueue.clone());
//		
//		mlmsbll1.Service(1);
//		System.out.println("MLMSBLL 1:\t" + mlmsbll1.getTime() + "\t" + mlmsbll1.getAverageWaitingTime() +  "\t" + mlmsbll1.getAverageM());
//		mlmsbll3.Service(3);
//		System.out.println("MLMSBLL 3:\t" + mlmsbll3.getTime() + "\t" + mlmsbll3.getAverageWaitingTime() +  "\t" + mlmsbll3.getAverageM());
//		mlmsbll5.Service(5);
//		System.out.println("MLMSBLL 5:\t" + mlmsbll5.getTime() + "\t" + mlmsbll5.getAverageWaitingTime() +  "\t" + mlmsbll5.getAverageM());
//
//		System.out.println();
//		
//		MLMSBWT mlmsbwt1 = new MLMSBWT(arrivalQueue.clone());
//		MLMSBWT mlmsbwt3 = new MLMSBWT(arrivalQueue.clone());
//		MLMSBWT mlmsbwt5 = new MLMSBWT(arrivalQueue.clone());
//		
//		mlmsbwt1.Service(1);
//		System.out.println("MLMSBWT 1:\t" + mlmsbwt1.getTime() + "\t" + mlmsbwt1.getAverageWaitingTime() +  "\t" + mlmsbwt1.getAverageM());
//		mlmsbwt3.Service(3);
//		System.out.println("MLMSBWT 3:\t" + mlmsbwt3.getTime() + "\t" + mlmsbwt3.getAverageWaitingTime() +  "\t" + mlmsbwt3.getAverageM());
//		mlmsbwt5.Service(5);
//		System.out.println("MLMSBWT 5:\t" + mlmsbwt5.getTime() + "\t" + mlmsbwt5.getAverageWaitingTime() +  "\t" + mlmsbwt5.getAverageM());
//		
//		scan.close();
	}

	public static SLLQueue<Customer> copyList (SLLQueue<Customer> custList) {
		SLLQueue<Customer> copyOfList = new SLLQueue<>();

		int j = 0;
		while(!(j==custList.size())) {

			Customer customer = custList.dequeue();
			Customer cCust = new Customer(customer.getArrTime(), customer.getSerTime());

			cCust.setDepTime(customer.getDepTime());
			custList.enqueue(customer);
			copyOfList.enqueue(cCust);
			j++;

		}

		return copyOfList;
	}

}
