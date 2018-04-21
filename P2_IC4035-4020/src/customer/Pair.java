package customer;

public class Pair {
	
	private int arrivalTime; //The first int of data
	private int serviceTime; //The second int of data
	private int departureTime; 

	public Pair(int arrivalTime, int serviceTime , int departureTime) {
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.departureTime = departureTime;
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
	public int getArrTime() {
		return arrivalTime;
	}

	public int getSerTime() {
		return serviceTime;
	}
	public int getDepTime() {
		return departureTime;
	}
	public String toString() {
		return arrivalTime + "," + serviceTime; 

	}
}
