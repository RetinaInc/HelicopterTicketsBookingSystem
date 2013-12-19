package congwang.individual.model;

import java.io.Serializable;

public class TimeTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flightNum;
	private String leftTime;
	private String arriveTime;
	private String destination;
	private String company;
	private String leftDate;
	private int leftSeat;

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(String leftTime) {
		this.leftTime = leftTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLeftDate() {
		return leftDate;
	}

	public void setLeftDate(String leftDate) {
		this.leftDate = leftDate;
	}

	public int getLeftSeat() {
		return leftSeat;
	}

	public void setLeftSeat(int leftSeat) {
		this.leftSeat = leftSeat;
	}

	@Override
	public String toString() {
		return flightNum + "      " + destination + "      " + leftDate
				+ "       " + leftTime;
	}

}