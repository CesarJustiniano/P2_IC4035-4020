package dataGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * 
 * @author pedroirivera-vega
 *
 */
public class DataGenerator {
    // R1 and R2 are fixed parameters to support data generation. We can play with
	// different values to adjust that generated data satisfies some criteria being
	// pursued. But for this project, I would say that the values given are good 
	// enough. 
	private static final int R1 = 30, R2 = 0;  
	                   
	private  int maxRangeValue;  // the largest integer value to be generated
	private int n;    // Arrival time of customer 
	private int m;    // Time that takes the customer to serve them
	private int totalSize;   // sum of the sizes of all datasets of particular data generators
	private Integer[][] sizes;    // sizes of datasets provided (or generated) by Ci for Ej
	private Integer[][][] customer; // dataSet[i][j] will be the generated data for F_i_j
	private int sizeFactor; 
	
	private Random rnd; 
	
	/**
	 * 
	 * @param n
	 * @param m
	 * @param totalSize
	 */
	public DataGenerator(int n, int m, int totalSize) {
		this.n = n; 
		this.m = m;
		// In computing MaxRangeValue, I just picked the value 10 arbitrarily in 
		// the next expression. Think about better possibilities, perhaps involving
		// n and m too... 
		// maxRangeValue is essentially the number of different values (+1) that 
		// final elements are chosen from...
		this.sizeFactor = totalSize/(n*m);   // to be used while generating sizes
		this.totalSize = totalSize;   // Sizes must satisfy sum_ij(sizes[i][j]) == totalSize
		rnd = new Random();  // to generate random values
	}
	
	/**
	 * 
	 * @return
	 */
	public Object[][][] generateData() {
		IntegerComparator1 c = new IntegerComparator1();
		customer = new Integer[n][m][];
		generateSizes(); 
		for (int i=0; i<n; i++) { 
			for (int j=0; j<m; j++) {
				ArrayList<Integer> list = new ArrayList<>(); 
				while(list.size() != this.sizes[i][j]) {
					list.add(this.rnd.nextInt(maxRangeValue));
				}
				// add a common value to sets in row 0
				if (i==0) 
					list.add(maxRangeValue); 
				list.sort(c);
				customer[i][j] = (Integer[]) list.toArray(new Integer[0]); 
			}
		}	
		return customer; 
	}

	/**
	 * 
	 */
	private void generateSizes() {
		sizes = new Integer[n][m]; 
		sizeFactor = 5;
		int s = 0; 
		for (int i=0; i<n; i++) 
			for (int j=0; j<m; j++) { 
			sizes[i][j] = (rnd.nextInt(R1)-R2)+sizeFactor; 
			if (sizes[i][j] < 0)
				sizes[i][j] = 0; 
			s += sizes[i][j]; 
		}

		// adjust sizes for the total to be totalSize-m
		matchSizes(s, totalSize-m); 
		
		// determine the max size in s
		int maxSize = maxSize(sizes); 
		maxRangeValue = (int) (maxSize); 

	}
	
	private int maxSize(Integer[][] sizes) {
		int mv = 0; 
		for (int i=0; i<sizes.length; i++)
			for (int j=0; j<sizes[i].length; j++) 
				if (sizes[i][j] > mv)
					mv = sizes[i][j]; 
		return mv;
	}

	/**
	 * 
	 * @param s
	 * @param total
	 */
	private void matchSizes(int s, int total) { 
		int step = 1; 
		if (s>total) step = -1; 
		int i = 0, j=0; 
		while (s != total) { 
			if (sizes[i][j] != 0) {
			   sizes[i][j] += step;     // check that some values (e.g. negative) are not valid for sizes
			   s += step; 
			}   
			j++; 
			if (j == m) { 
				j = 0; 
     			i = (i+1) % n;
			}	
		}
	}

	
	// THE FOLLOWING METHODS ARE HERE TO USE IN TESTING....
	public void printSizes() { 
		System.out.println("Sizes:");
		for (int i=0; i<n; i++)
			printArray(sizes[i]);
	}
	
	public void printSets() { 
		System.out.println("Sets are: " ); 
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++) { 
				System.out.print("Set["+i+"]["+j+"] = "); 
				printArray((Integer[]) customer[i][j]); 
			}
	}
	
	private void printArray(Integer[] numbers) {
		for (int i=0; i<numbers.length; i++) 
			System.out.print(numbers[i] + "  "); 
		System.out.println(); 
	}


}
