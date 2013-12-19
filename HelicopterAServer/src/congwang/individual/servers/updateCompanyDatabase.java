/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package congwang.individual.servers;

/**
 *
 * @author wangco
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class updateCompanyDatabase {
	private static String path = "u:\\helicopterDatabase.sql";

	public static void export() {
		String dumpCommand = ("cmd /c C:\\mysqldump -hlocalhost -uroot -pw89620c --opt helicopterDatabase");
		Runtime rt = Runtime.getRuntime();
		File test = new File(path);
		PrintStream ps;
		try {
			Process child = rt.exec(dumpCommand);
			ps = new PrintStream(test);
			InputStream in = child.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				ps.write(ch);
				System.out.write(ch); // to view it by console
			}
			InputStream err = child.getErrorStream();
			while ((ch = err.read()) != -1) {
				System.out.write(ch);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	int port = 11000;

	void start() {
		Socket s = null;
		try {
			while (true) {
				// set the file to be transmitted
				String filePath = "u:\\helicopterDatabase.sql";
				File fi = new File(filePath);

				System.out.println("filelength:" + (int) fi.length());

				// public Socket accept() throws
				// IOException
				s = extracted().accept();
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

	private ServerSocket extracted() throws IOException {
		return new ServerSocket(port);
	}

	public static void main(String arg[]) {
		export();
		System.out.println("BackingUp start");
		new updateCompanyDatabase().start();
		System.exit(-1);

	}
}