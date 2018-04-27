package customer;

public class Customer {
	private int iD, line, m;
	private boolean isServed;  
	private long arrivalTime; //The first int of data
	private long serviceTime; //The second int of data
	private long departureTime; 
	
	public Customer(int iD, long arrivalTime, long serviceTime){
		this.iD = iD;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.line = 0;
		this.m = 0;
		isServed = false;
	}

	//getters
	public boolean isServed() {
		return isServed;
	}
	
	public int getiD() {
		return iD;
	}
	
	public int getM() {
		return m;
	}
	
	public long getArrTime() {
		return arrivalTime;
	}

	public long getSerTime() {
		return serviceTime;
	}
	public long getDepTime() {
		return departureTime;
	}
	
	public int getNumLine(){
		return line;
	}
	
	public long getWaitingTime(){
		long result = arrivalTime - departureTime;
		
		if(result < 0){
			return result * -1;
		}
		
		return result;
	}

	//setters
	public void incrementM() {
		m++; //this variable will function as a counter
	}

	public void setArrTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setSerTime(long serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setDepTime(long departureTime) {
		this.departureTime = departureTime;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}
	
	public void setServed(boolean isServed) {
		this.isServed = isServed;
	}
	
	public void setLine(int line){
		this.line = line;
	}
	
	public String toString() {
		return arrivalTime + "," + serviceTime; 

	}
	
}
