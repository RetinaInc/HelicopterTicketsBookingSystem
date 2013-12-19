/*
 * sending .sql file to helicopter which is about to departure
 * 
 */
package congwang.individual.servers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronizingHelicopterDatabase {
	int port = 8600;
	private ServerSocket serverSocket;

	void start() {
		Socket s = null;
		try {
			while (true) {
				// set the file to be transmitted
				String filePath = "d:\\helicopterDatabase.sql";
				File fi = new File(filePath);

				System.out.println("filelength:" + (int) fi.length());

				// public Socket accept() throws

				serverSocket = new ServerSocket(port);
				s = serverSocket.accept();
				System.out.println("Setting up the socket connection");
				DataInputStream dis = new DataInputStream(
						new BufferedInputStream(s.getInputStream()));
				dis.readByte();

				DataInputStream fis = new DataInputStream(
						new BufferedInputStream(new FileInputStream(filePath)));
				DataOutputStream ps = new DataOutputStream(s.getOutputStream());
				// send the file name and file length to the server
				ps.writeUTF(fi.getName());
				ps.flush();
				ps.writeLong((long) fi.length());
				ps.flush();

				int bufferSize = 8192;
				byte[] buf = new byte[bufferSize];

				while (true) {
					int read = 0;
					if (fis != null) {
						read = fis.read(buf);
					}

					if (read == -1) {
						break;
					}
					ps.write(buf, 0, read);
				}
				ps.flush();
				// close the socket connection
				fis.close();
				s.close();
				System.out.println("Transmission Completed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String arg[]) {
		System.out
				.print("Start to initialise the database of leaving helicopter");
		new SynchronizingHelicopterDatabase().start();
	}
}