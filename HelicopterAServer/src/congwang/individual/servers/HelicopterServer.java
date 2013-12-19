package congwang.individual.servers;

import java.io.*;
import java.net.*;

public class HelicopterServer {
	protected int listenPort;

	public HelicopterServer(int port) {
		listenPort = port;
	}

	// Thread
	public void acceptConnections() {
		try {
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = extracted().accept();
				handleConnection(incomingConnection);
				System.out
						.println("Helicopter Server handling requests from clients");
			}
		} catch (BindException e) {
			System.out.println("Unable to bind port" + listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ServerSocket extracted() throws IOException {
		return new ServerSocket(listenPort, 100);
	}

	public void handleConnection(Socket connectionToHandle) {
		new Thread(new ConnectionHandler(connectionToHandle)).start();
	}

	public static void main(String[] args) {
		HelicopterServer server = new HelicopterServer(3000);
		server.acceptConnections();

	}
}