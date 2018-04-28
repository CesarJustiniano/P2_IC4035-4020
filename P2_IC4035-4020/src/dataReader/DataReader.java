package dataReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class DataReader {

	private int n;    // Time of arrival of the customer
	private int m;   // Time that the customer needs for the service
	private Integer[][][] customer; 
	private String parentDirectory; 
	

	public DataReader() throws FileNotFoundException {
		parentDirectory = "inputFiles"; 
		Scanner parameters = new Scanner(new File(parentDirectory, "parameters.txt")); 
		// the values of n and m shall be read from file: "inputFiles/input.txt". 
		this.n = parameters.nextInt(); 
		this.m = parameters.nextInt();
		parameters.close();
	}
	
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Object[][][] readDataFiles() throws FileNotFoundException {
		customer = new Integer[n][m][];
		parentDirectory = "inputFiles";
		
		for (int i=0; i<n; i++) { 
			for (int j=0; j<m; j++) {
				
				String fileName = "data_" + i + ".txt"; 
				Scanner inputFile = new Scanner(new File(parentDirectory, fileName)); 
				ArrayList<Integer> fileContent = new ArrayList<>(); 
				while (inputFile.hasNext())
					fileContent.add(inputFile.nextInt());
				inputFile.close();
				customer[i][j] = (Integer[]) fileContent.toArray(new Integer[0]);  
			}
		}	
		return customer; 
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
