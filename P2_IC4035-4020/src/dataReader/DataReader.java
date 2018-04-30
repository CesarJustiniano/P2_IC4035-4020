package dataReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import customer.Customer;
import queues.SLLQueue;

/**
 * 	The class reads the file 
 * @author JaiTorres13
 * 	Jainel Marie Torres Santos (843-14-8932) (Sec. 030)
 *@author CesarJustiniano 
 *	Cesar Andres Justiniano Santiago (840-15-3720)(Sec. 030)
 *
 */
public class DataReader {

	private String file;    
	private int n; // Time of arrival of the customer
	private int m;   // Time that the customer needs for the service
	private int id;
	private Integer[][][] customer; 
	private String parentDirectory; 
	SLLQueue<Customer> arrivalQueue;

	public static void main(String[] args)  throws FileNotFoundException{
		
		DataReader dt = new DataReader();
		SLLQueue<Customer> list = dt.readDataFiles("data_5.txt");
		if(list.isEmpty()) System.out.println("is empty");
	}
	

	public DataReader() throws FileNotFoundException {
		parentDirectory = "inputFiles"; 
		Scanner dataFiles = new Scanner(new File(parentDirectory, "dataFiles.txt")); 
		dataFiles.close();
	}
	
	/**
	 *  reads the name of the file of the string 
	 * @param inputFile
	 * @return arrivalQueue
	 * @throws NoSuchElementException 
	 * @throws FileNotFoundException 
	 */
	public SLLQueue<Customer> readDataFiles(String inputFile) throws NoSuchElementException,
	FileNotFoundException, IndexOutOfBoundsException{
		SLLQueue<Customer> arrivalQueue = new SLLQueue<>();
		
		parentDirectory = "inputFiles";
		String fileName = inputFile;
		Scanner inputFile1 = new Scanner(new File(parentDirectory, fileName)); 
			try {
		
				
			
			System.out.println(fileName);
			    
			
				while (inputFile1.hasNext()) {
					String input = inputFile1.nextLine();
					String[] numbers = input.split("\t");
					if(numbers.length < 2)
						throw new NoSuchElementException();
					   n = Integer.parseInt(numbers[0]);
		               m = Integer.parseInt(numbers[1].substring(numbers[1].length()-1));
		               
		               System.out.println("n " + n);
		               System.out.println("m " + m);

					
					Customer c = new Customer(n, m);
					arrivalQueue.enqueue(c);
				}	
			inputFile1.close();
		
		}  catch (NoSuchElementException  e) {
            System.out.println();
            while(!arrivalQueue.isEmpty())
            		arrivalQueue.dequeue();
        } catch (IndexOutOfBoundsException  e) {
            System.out.println();
            while(!arrivalQueue.isEmpty())
            		arrivalQueue.dequeue();
        } finally {
            if ( inputFile1 != null) {
                inputFile1.close();
            }
        }
//		System.out.println("Completed File");
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
