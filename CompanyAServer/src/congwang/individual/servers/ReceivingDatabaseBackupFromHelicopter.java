/*
 * .sql file receiving from helicopters
 * the file will be used for sync purpose
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

public class ReceivingDatabaseBackupFromHelicopter {
	private socket cs = null;

	private String ip = "localhost";// ip

	private int port = 11000;

	private String sendMessage = "Windows";

	private static Process exec;

	public ReceivingDatabaseBackupFromHelicopter() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}

		} catch (Exception ex) {
		}
	}

	private boolean createConnection() {
		cs = new socket(ip, port);
		try {
			cs.CreateConnection();
			System.out.print("Conneted with helicopter!" + "\n");
			return true;
		} catch (Exception e) {
			System.out.print("Fail to conncet with helicopter!" + "\n");
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
			String savePath = "d:\\";
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;

			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new BufferedOutputStream(
							new FileOutputStream(savePath))));
			len = inputStream.readLong();

			System.out.println("filelength:" + len + "\n");
			System.out.println("Receiving the file!" + "\n");

			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				// display the transmission prosess
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
		String initial = ("cmd /c C:\\mysql -uroot -pw89620c helicopterBackUpDatabase< d:\\helicopterDatabase.sql");
		Runtime rt = Runtime.getRuntime();
		try {
			setExec(rt.exec(initial));

		} catch (IOException ex) {
			Logger.getLogger(
					ReceivingDatabaseBackupFromHelicopter.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	public static void main(String arg[]) {
		new ReceivingDatabaseBackupFromHelicopter();
		System.out.println("BackingUp the helicopter database");
		export();
		System.out.println("Database BackUp done");
	}

	public static Process getExec() {
		return exec;
	}

	public static void setExec(Process exec) {
		ReceivingDatabaseBackupFromHelicopter.exec = exec;
	}
}
