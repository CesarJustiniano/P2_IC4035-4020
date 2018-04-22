package customer;

public class Customer {
	private int iD;
	private boolean isServed;  
	private long arrivalTime; //The first int of data
	private long serviceTime; //The second int of data
	private long departureTime; 

	public Customer() {
		arrivalTime = serviceTime =departureTime  = 0;
		isServed = false;
	}
	
	public Customer(int iD, long arrivalTime, long serviceTime){
		this.iD = iD;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		isServed = false;
	}

	public boolean isServed() {
		return isServed;
	}
	
	public int getiD() {
		return iD;
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
	public long getArrTime() {
		return arrivalTime;
	}

	public long getSerTime() {
		return serviceTime;
	}
	public long getDepTime() {
		return departureTime;
	}
	
	public void setiD(int iD) {
		this.iD = iD;
	}
	
	public void setServed(boolean isServed) {
		this.isServed = isServed;
	}
	
	public String toString() {
		return arrivalTime + "," + serviceTime; 

	}
	
}
