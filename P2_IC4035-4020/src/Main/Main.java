package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import customer.Pair;
import queues.ArrayQueue;
import queues.SLLQueue;

public class Main {

	public static void main(String[] args) {
		SLLQueue<Pair> arrivalQueue = new SLLQueue<Pair>();
		SLLQueue<Pair> serviceStartsQueue = new SLLQueue<Pair>();
		ArrayQueue<Pair> serviceCompletedQueue = new ArrayQueue<Pair>();
		
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
               Pair element = new Pair(arrTime,serTime,0);
              
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
	}

}
