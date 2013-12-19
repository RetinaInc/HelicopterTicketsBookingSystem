package congwang.individual.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HelicopterClient {
	protected String hostIp;
	protected int hostPort;
	private static int count = 1;
	private static int count1 = 1;
	// private static StringBuffer fileLines = new StringBuffer();
	protected BufferedReader socketReader;
	protected PrintWriter socketWriter;

	public HelicopterClient(String aHostIp, int aHostPort) {
		hostIp = aHostIp;
		hostPort = aHostPort;
	}

	public void setUpConnection() {
		try {
			Socket client = new Socket(hostIp, hostPort);
			socketReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());
		} catch (SocketException e) {
			System.out.println("Can't connect to the server,please wait");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
			}
			setUpConnection();
		} catch (IOException e) {
			System.out.println("Error in setting up socket connection: " + e);
		}
	}

	// Book a ticket from a company
	public String bookTicket(String s) {

		StringBuilder fileLines = new StringBuilder();
		try {
			Socket client = new Socket(hostIp, hostPort);
			socketReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());
			socketWriter.println(s);
			socketWriter.flush();

			String line = null;
			while ((line = socketReader.readLine()) != null) {
				fileLines.append(line).append("\n");
			}
			System.out.println("Message from server:" + fileLines);
			socketWriter.close();
			socketReader.close();
		} catch (SocketException e) {

			System.out.println("Error occured,please wait 15 second.   "
					+ count--);
			try {
				Thread.sleep(15000); // thread sleeps for 15 second
			} catch (InterruptedException e1) {
			}
			if (count > 0) {
				this.bookTicket(s);
			}
			System.out.println("Error reading from:" + s);
		} catch (IOException e) {
			System.out.println("Error,plesae book again");
		}
		if (count <= 0) {
			return "Cannot connect to the server" + "\n"
					+ "Please reopen the UI and try later";
		}
		return fileLines.toString();
	}

	// Get the timeTable from helicopter's database
	public String getFlight(String s) {
		setUpConnection();
		StringBuffer fileLines = new StringBuffer();
		try {
			socketWriter.println(s);
			socketWriter.flush();

			String line = null;
			while ((line = socketReader.readLine()) != null) {
				fileLines.append(line + "\n");
			}
			System.out.println("message from client:" + fileLines);
			socketWriter.close();
			socketReader.close();

		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		return fileLines.toString();
	}

	// cancel a ticket
	public String cancelFlight(String s) {
		// setUpConnection();
		StringBuffer fileLines = new StringBuffer();
		try {
			Socket client = new Socket(hostIp, hostPort);
			socketReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());
			socketWriter.println(s);
			socketWriter.flush();

			String line = null;
			while ((line = socketReader.readLine()) != null) {
				fileLines.append(line).append("\n");
			}
			System.out.println("Message from server:" + fileLines);
			socketWriter.close();
			socketReader.close();

		} catch (SocketException e) {
			System.out.println("Error occured, please wait 15 second.   "
					+ count1--);
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e1) {
			}
			if (count > 0) {
				this.bookTicket(s);
			}
		} catch (IOException e) {
		}
		if (count1 <= 0) {
			return "Can't connect to the server at this moment.";
		}
		return fileLines.toString();
	}

	public void tearDownConnection() {
		try {
			socketWriter.close();
			socketReader.close();
		} catch (IOException e) {
			System.out.println("Error");
		}

	}

	public static void main(String[] args) {
		Scanner c = new Scanner(System.in);
		System.out
				.println("Please choose the service location(number only): 1-Camp********2-Town*****Other-Exit");
		String l = c.nextLine();
		if (l.equals("1")) // choose serversi in Camp
		{
			HelicopterClient hc = new HelicopterClient("localhost", 3000); // connect
																			// to
																			// the
																			// helicopter
																			// server
			Scanner s = new Scanner(System.in);
			System.out
					.println("Please choose the service(enter the number only): 1-Booking****2-Cancel****Other-Exit");
			String t = s.nextLine();

			if (t.equals("1")) { // booking ticket from helicopter server
				System.out.println("Please Input your passport number:");
				String passport = s.nextLine();
				System.out
						.println("Please Input flight number(please start with'!' and with '?'):");

				String flight = s.nextLine();
				System.out
						.println("Please Input the date of travelling(Please end with '*'):");
				System.out.println("Date Format: yyyy-mm-dd");
				String date = s.nextLine();
				System.out.println("Your booking information " + flight + date
						+ passport + "has been sent ");

				hc.bookTicket(flight + date + passport);
				// hc.cancelFlight("CA0012012-10-255746?G4163");
				// System.out.println(s);
			}

			if (t.equals("2")) { // cancel ticket from helicopter server
				System.out.println("Please Input your ticket number:");
				String ticketNumber = s.nextLine();
				System.out
						.println("Please Input passport number(please start with'?' ):");
				String passport = s.nextLine();
				hc.cancelFlight(ticketNumber + passport);

				System.out.println("Your cancel request regarding "
						+ ticketNumber + passport + " has been sent ");

			} else { // exit
				System.out.println("Thanks, Bye Bye");
			}
		}

		if (l.equals("2")) // choose server in town
		{
			HelicopterClient hc = new HelicopterClient("localhost", 8000); // connect
																			// to
																			// the
																			// company
																			// server
																			// in
																			// town

			Scanner s = new Scanner(System.in);
			System.out
					.println("Please choose the service(enter the number only): 1-Booking****2-Cancel****Other-Exit");
			String t = s.nextLine();
			if (t.equals("1")) { // book ticket from company server in town
				System.out.println("Please Input your passport number:");
				String passport = s.nextLine();
				System.out
						.println("Please Input flight number(please start with'!' and with '?'):");

				String flight = s.nextLine();
				System.out
						.println("Please Input the date of travelling(Please end with '*'):");
				System.out.println("Date Format: yyyy-mm-dd");
				String date = s.nextLine();
				System.out.println("Your booking information " + flight + date
						+ passport + " has been sent ");

				hc.bookTicket(flight + date + passport);
				// hc.cancelFlight("CA0012012-10-255746?G4163");
				// System.out.println(s);
			}

			if (t.equals("2")) { // cancel ticket from company server in town
				System.out.println("Please Input your ticket number:");
				String ticketNumber = s.nextLine();
				System.out
						.println("Please Input passport number(please start with'?' ):");
				String passport = s.nextLine();
				hc.cancelFlight(ticketNumber + passport);

				System.out.println("Your cancel request regarding "
						+ ticketNumber + passport + "has been sent ");

			} else {
				System.out.println("Thanks, Bye Bye"); // exit
			}
		} else {
			System.out.println("Programme terminated");
		} // terminate the programme
	}
}