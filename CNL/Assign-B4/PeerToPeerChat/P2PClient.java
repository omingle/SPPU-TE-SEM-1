import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class P2PClient {
	private Socket socket = null;
	private DataInputStream inStream = null;
	private DataOutputStream outStream = null;

	public P2PClient() {
	}

	public void createSocket() {
		try {
			socket = new Socket("localHost", 3339);
			System.out.println("Connected\n");
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
			createReadThread();
			createWriteThread();
		} catch (UnknownHostException u) {
			u.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void createReadThread() {
		Thread readThread = new Thread() {
			public void run() {
				String receivedMessage = "";
				while (!receivedMessage.equalsIgnoreCase("bye")) {
					try {
						receivedMessage = inStream.readUTF();
						System.out.println("Server : " + receivedMessage);
					} catch (SocketException se) {
						System.exit(0);
					} catch (IOException i) {
						i.printStackTrace();
					}
				}
			}
		};
		readThread.setPriority(Thread.MAX_PRIORITY);
		readThread.start();
	}

	public void createWriteThread() {
		Thread writeThread = new Thread() {
			public void run() {
				String yourMessage = "";
				while (!yourMessage.equalsIgnoreCase("bye")) {
					try {
						BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
						sleep(100);
						
						yourMessage = inputReader.readLine();
						if (yourMessage != null && yourMessage.length() > 0) {
							synchronized (socket) {
								outStream.writeUTF(yourMessage);
								outStream.flush();
								sleep(100);
							}
						}
						/*
						 * else { notify(); }
						 */
						;
					} catch (IOException i) {
						i.printStackTrace();
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
		};
		writeThread.setPriority(Thread.MAX_PRIORITY);
		writeThread.start();
	}

	public static void main(String[] args) throws Exception {
		P2PClient myChatClient = new P2PClient();
		myChatClient.createSocket();
		/*
		 * myChatClient.createReadThread(); myChatClient.createWriteThread();
		 */
	}
}