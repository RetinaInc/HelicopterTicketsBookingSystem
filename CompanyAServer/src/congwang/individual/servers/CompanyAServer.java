/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package congwang.individual.servers;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author congwang
 */

public class CompanyAServer {
	protected int listenPort;

	public CompanyAServer(int port) {
		listenPort = port;
	}

	// Thread
	@SuppressWarnings("resource")
	public void acceptConnections() {
		try {
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = new ServerSocket(listenPort, 100).accept();
				handleConnection(incomingConnection);
				System.out
						.println("Company Server handling requests from clients");
			}
		} catch (BindException e) {
			System.out.println("Unable to bind port" + listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleConnection(Socket connectionToHandle) {
		new Thread(new ConnectionHandler(connectionToHandle)).start();
	}

	public static void main(String[] args) {
		CompanyAServer server = new CompanyAServer(8000);
		server.acceptConnections();

	}
}