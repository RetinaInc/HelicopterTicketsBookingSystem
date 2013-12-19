package congwang.individual.servers;

import congwang.individual.model.CustomerInfo;
import congwang.individual.model.TimeTable;
import congwang.individual.model.addCustomer;
import congwang.individual.model.deleteCustomer;
import congwang.individual.service.CustomerInfoService;
import congwang.individual.service.TimeTableService;
import congwang.individual.service.addCustomerService;
import congwang.individual.service.deleteCustomerService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author wangco
 */
public class ConnectionHandler implements Runnable {

	protected Socket socketToHandle;
	private long n;

	public long days(String s) throws ParseException {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new Date(System.currentTimeMillis());
		myFormatter.format(date);
		java.util.Date mydate = myFormatter.parse(s);
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println(day);
		return day;
	}

	public ConnectionHandler(Socket aSocket) {
		socketToHandle = aSocket;
	}

	@Override
	public void run() {
		try {
			OutputStream outputToSocket = socketToHandle.getOutputStream();
			InputStream inputFromSocket = socketToHandle.getInputStream();

			BufferedReader streamReader = new BufferedReader(
					new InputStreamReader(inputFromSocket));
			String line = streamReader.readLine();
			String s1 = line.substring(0, 1); // the request classifier ! %
			// System.out.println(s1);
			int l = line.indexOf("?");
			int k = line.indexOf("*");

			if (s1.equals("!")) {
				String s2 = line.substring(1, l); // flightNum
				String s3 = line.substring(l + 1, k); // date
				String s4 = line.substring(k + 1, line.length()); // passport
																	// number
				try (PrintWriter streamWriter = new PrintWriter(outputToSocket)) {
					setN(days(s3));
					// System.out.println(n);
					if (days(s3) < 0) {
						String s = bookAticket(s2, s3, s4);
						// System.out.println(s);
						streamWriter.println(s);
					} else {
						streamWriter
								.println("Sorry,requst denied,your booking was too late"
										+ "\n" + "Please try other flights.");
					}
				}
				streamReader.close();
			} else {
				String s2 = line.substring(0, l);
				String date = s2.substring(5, 15);
				String s3 = line.substring(l + 1, line.length());
				try (PrintWriter streamWriter = new PrintWriter(outputToSocket)) {
					if (days(date) < 0) {
						String s = cancelAticket(s2, s3);
						streamWriter.println(s);
					} else {
						streamWriter
								.println("Sorry,requst denied,your cancel was too late.");
					}
				}
				streamReader.close();
			}
		} catch (IOException | ParseException e) {
		}
	}

	public String bookAticket(String s2, String s3, String s4) {
		TimeTableService ts = new TimeTableService();
		TimeTable t = ts.loadByFlightNoAndDate(s2, s3);
		if (t == null) {
			return "Wrong input or no flight available on that day.";
		}

		System.out.println("Seat Left of this flight:" + t.getLeftSeat());
		if (t.getLeftSeat() > 0) {
			t.setLeftSeat(t.getLeftSeat() - 1);
			System.out
					.println("Seat Left of this flight after this transaction: "
							+ t.getLeftSeat());
			ts.update(t);
			String s = addCustomer(s2, s3, s4);
			System.out.println("Ticket Number for this Client is: " + s);

			return "Book successfully" + "\n" + "Your ticket number is " + s
					+ "\n" + "Thanks for choosing us.";
		} else {
			return "Sorry, There is no ticket available at this moment.";
		}

	}

	public String cancelAticket(String s2, String s3) {

		String flightNo = s2.substring(0, 5); // get filightNo from ticket no.
		String date = s2.substring(5, 15); // get date from ticket no.
		// System.out.println(flightNo);
		// System.out.println(date);
		TimeTableService ts = new TimeTableService();
		TimeTable t = ts.loadByFlightNoAndDate(flightNo, date);

		if (t == null) {
			return "Wrong input";
		}

		CustomerInfoService cs = new CustomerInfoService();
		CustomerInfo c;

		// c.setTicketNum(s2);
		// c.setPassport(s3);
		c = cs.loadByPassportAndTicketNum(s2, s3);

		if (c == null) {
			return "Wrong input";
		}
		cs.deleteByPassportAndTicketNum(s2, s3);
		// save cancel infomation into deleteCustomer database
		deleteCustomerService dc = new deleteCustomerService();
		deleteCustomer delete = new deleteCustomer();
		delete.setPassport(s3);
		delete.setTicketNum(s2);
		dc.add(delete);

		if (t.getLeftSeat() < 6) {
			t.setLeftSeat(t.getLeftSeat() + 1);
			System.out.println("Seat Left after cancel:" + t.getLeftSeat());
			ts.update(t);
		}
		return "Cancel successfully" + "\n" + "Your canceled ticket number is"
				+ s2;

	}

	public String addCustomer(String b2, String b3, String b4) {
		CustomerInfoService cs = new CustomerInfoService();
		CustomerInfo c = new CustomerInfo();
		c.setPassport(b4);
		int a = (int) (Math.random() * 1000) + (int) (Math.random() * 800); // generate
																			// the
																			// random
																			// number
																			// to
																			// be
																			// used
																			// to
																			// make
																			// the
																			// ticket
																			// number
		String s = b2 + b3 + a; // generate the ticket number by combining the
								// flightNum, Date plust a random number.
		c.setTicketNum(s);
		cs.add(c);

		addCustomerService ac = new addCustomerService();
		addCustomer add = new addCustomer();
		add.setPassport(b4);
		add.setTicketNum(s);
		ac.add(add); // save customer information into addCustomer database
						// which is a backup database and also could be used for
						// synchronizing with server
		return s;
	}

	public long getN() {
		return n;
	}

	public void setN(long n) {
		this.n = n;
	}

}