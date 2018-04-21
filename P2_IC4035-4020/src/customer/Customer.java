package customer;

public class Customer {
	private long arrivalTime, remainingTime, serviceTime;
	private int iD;
	private boolean isServed;
	
	public Customer(int iD){
		this.iD = iD;
		arrivalTime = remainingTime = serviceTime = 0;
		isServed = false;
	}
	
	public Customer(int iD, long arrivalTime){
		this.iD = iD;
		this.arrivalTime = arrivalTime;
		isServed = false;
	}

	//Getters
	
	public boolean isServed() {
		return isServed;
	}

	public long getRemainingTime() {
		return remainingTime;
	}

	public long getServiceTime() {
		return serviceTime;
	}

	public int getiD() {
		return iD;
	}

	public long getWaitingTime() {
		return System.nanoTime() - arrivalTime;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	//Setters

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setRemainingTime(long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public void setServiceTime(long serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}
	
	public void setServed(boolean isServed) {
		this.isServed = isServed;
	}
}
