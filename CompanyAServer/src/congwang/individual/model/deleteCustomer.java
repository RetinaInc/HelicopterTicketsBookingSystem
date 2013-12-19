package congwang.individual.model;

import java.io.Serializable;

public class deleteCustomer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticketNum;
	private String passport;

	public deleteCustomer() {

	}

	public deleteCustomer(String s1, String s2) {
		ticketNum = s1;
		passport = s2;
	}

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

}