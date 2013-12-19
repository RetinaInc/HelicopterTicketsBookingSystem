/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package congwang.individual.servers;

/**
 *
 * @author wangco
 */
import congwang.individual.util.socket;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetDatabaseInfoFromCompany {
	private socket cs = null;

	private String ip = "localhost";// ip

	private int port = 8600;

	private String sendMessage = "Windwos";

	private static Process exec;

	public GetDatabaseInfoFromCompany() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean createConnection() {
		cs = new socket(ip, port);
		try {
			cs.CreateConnection();
			System.out.print("Connected with helicopter!" + "\n");
			return true;
		} catch (Exception e) {
			System.out.print("Fail to connect with server!" + "\n");
			return false;
		}

	}

	private void sendMessage() {
		if (cs == null)
			return;
		try {
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			System.out.print("Fail to send message!" + "\n");
		}
	}

	private void getMessage() {
		if (cs == null)
			return;
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			System.out.print("Error\n");
			return;
		}

		try {
			// save the file locally, will keep the file name unchanged.
			String savePath = "u:\\";
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;

			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new BufferedOutputStream(
							new FileOutputStream(savePath))));
			len = inputStream.readLong();

			System.out.println("File length:" + len + "\n");
			System.out.println("Receiving file!" + "\n");

			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				// display the transmission process
				System.out.println("File has been transmitted"
						+ (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			System.out.println("Done, file has been saved in " + savePath
					+ "\n");

			fileOut.close();
		} catch (Exception e) {
			System.out.println("Error" + "\n");
			return;
		}
	}

	public static void export() {
		String initial = ("cmd /c C:\\mysql -uroot -pw89620c helicopterDatabase< u:\\helicopterDatabase.sql");
		Runtime rt = Runtime.getRuntime();

		try {
			setExec(rt.exec(initial));
		} catch (IOException ex) {
			Logger.getLogger(GetDatabaseInfoFromCompany.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	public static void main(String arg[]) {
		new GetDatabaseInfoFromCompany();

		System.out.print("Start to initiallize the helicopterDatbase" + "\n");
		export();
		System.out.print("Database Done" + "\n");
	}

	public static Process getExec() {
		return exec;
	}

	public static void setExec(Process exec) {
		GetDatabaseInfoFromCompany.exec = exec;
	}
}
