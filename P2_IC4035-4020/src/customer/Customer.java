package customer;

public class Customer {
	private int iD, line, m;
	private boolean isRecentlyServed;  
	private int arrivalTime; //The first int of data
	private int serviceTime; //The second int of data
	private int departureTime; 
	private int waitingTime;
	
	public Customer(int arrivalTime, int serviceTime){
		this.iD = 0;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.line = 0;
		this.m = 0;
		this.waitingTime = 0;
		isRecentlyServed = false;
	}

	//getters
	public boolean isRecentlyServed() {
		return isRecentlyServed;
	}
	
	public int getiD() {
		return iD;
	}
	
	public int getM() {
		return m;
	}
	
	public int getArrTime() {
		return arrivalTime;
	}

	public int getSerTime() {
		return serviceTime;
	}
	public int getDepTime() {
		return departureTime;
	}
	
	public int getNumLine(){
		return line;
	}
	
	public int getWaitingTime(){
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	//setters
	public void incrementM() {
		m++; //this variable will function as a counter
	}

	public void setArrTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setSerTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setDepTime(int departureTime) {
		this.departureTime = departureTime;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}
	
	public void setRecentlyServed(boolean isRecentlyServed) {
		this.isRecentlyServed = isRecentlyServed;
	}
	
	public void setLine(int line){
		this.line = line;
	}
	
	public String toString() {
		return arrivalTime + "," + serviceTime; 

	}
	
}
