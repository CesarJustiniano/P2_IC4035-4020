package customer;

/**
 * 
 * @author JaiTorres13
 * 	Jainel Marie Torres Santos (843-14-8932) (Sec. 030)
 * @author CesarJustiniano 
 *	Cesar Andres Justiniano Santiago (840-15-3720)(Sec. 030)
 *
 */
public class Customer {
	private int iD, line, m;
	private boolean isRecentlyServed;  
	private int arrivalTime; //The first int of data
	private int serviceTime; //The second int of data
	private long departureTime; 
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
	public long getDepTime() {
		return departureTime;
	}
	
	public int getNumLine(){
		return line;
	}
	
	public int getWaitingTime(){
		return waitingTime;
	}
	
	public int getWaitingTime(int time){
		int result = time - serviceTime;
		if(result < 0)
			return result * -1;
		return result;
	}

	//setters
	public void incrementM() {
		m++; //this variable will function as a counter
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public void setArrTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setSerTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setDepTime(long l) {
		this.departureTime = l;
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
