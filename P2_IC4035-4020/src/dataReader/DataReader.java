package dataReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import customer.Customer;
import queues.SLLQueue;


public class DataReader {

	private String file;    
	private int n; // Time of arrival of the customer
	private int m;   // Time that the customer needs for the service
	private int id;
	private Integer[][][] customer; 
	private String parentDirectory; 
	SLLQueue<Customer> arrivalQueue;

	

	public DataReader() throws FileNotFoundException {
		parentDirectory = "inputFiles"; 
		Scanner dataFiles = new Scanner(new File(parentDirectory, "dataFiles.txt")); 
		// the values of n and m shall be read from file: "inputFiles/input.txt". 
		while(dataFiles.hasNext()) {
		this.file = dataFiles.nextLine();	
		}
		dataFiles.close();
	}
	
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	public SLLQueue<Customer> readDataFiles() throws FileNotFoundException {
		SLLQueue<Customer> arrivalQueue = new SLLQueue<>();
		
		parentDirectory = "inputFiles";
		Scanner inputFile = new Scanner(new File(parentDirectory, "dataFiles.txt")); 
		
		while (inputFile.hasNext()) {
		
//			if(inputFile.next() == null) {
//				throw new FileNotFoundException ("File not found");
//			}			
				
			String fileName = inputFile.next(); 
			Scanner inputFile1 = new Scanner(new File(parentDirectory, fileName)); 
			
			while (inputFile1.hasNext()) {
				String input = inputFile1.nextLine();
				String[] numbers = input.split("\t");
				n = Integer.parseInt(numbers[0]);
	            m = Integer.parseInt(numbers[1].substring(numbers[1].length()-1));
	               
	            System.out.println("n " + n);
	            System.out.println("m " + m);
				
				Customer c = new Customer(n, m);
				arrivalQueue.enqueue(c);
			}
			
			inputFile1.close();	
		}
		inputFile.close();	
		System.out.println("Completed File");
		return arrivalQueue; 
	}

	
	public void printFiles() { 
		System.out.println("Files Di are: " ); 
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++) { 
				System.out.print("File["+i+"]= "); 
				printArray((Integer[]) customer[i][j]); 
			}
	}
	
	private void printArray(Integer[] numbers) {
		for (int i=0; i<numbers.length; i++) 
			System.out.print(numbers[i] + "  "); 
		System.out.println(); 
	}


}
