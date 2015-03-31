package Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import Database.Database;

public class Server3 {

	private int socket_no = 3333;
	private final int portNumber = 5555;
	private String message;
	private DatagramPacket request_FromClient;
	private DatagramSocket aSocket = null;

	public static void main(String args[]) {
		new Server3();
	}

	public void setMessage(String message) {
		this.message = message.trim();
	}

	public String getMessage() {
		return message.trim();
	}

	public Server3() {
		try {
			System.out.println("Server_1, Usage: java UDPServer <" + socket_no
					+ ">");
			aSocket = new DatagramSocket(socket_no);

			Database database = new Database(socket_no);
			database.cleanDatabase();
			try {
				database.generateCandidates("candidates.txt");
			} catch (Exception e) {
				System.out.println("generate Candidates failed!!");
			}

			while (true) {
				run();				
			}
		} catch (Exception e) {
			System.out.println("couldnt connect to socket number: " + socket_no);
		}
	}

	public void run() throws Exception {
		while (true) {
			recieveFromClient();
			sendToClient();
		}
	}

	public void sendToClient() {
		try {
			byte[] m = getMessage().getBytes();
			request_FromClient.setData(m);
			aSocket.send(request_FromClient);
		} catch (Exception e) {
			System.out.println("SERVER_1: sendToClient FAILED");
		}
	}

	public String vote(String username, String candidate) {
		Database database = new Database(socket_no);

		boolean voted = database.vote(username, candidate);
		if (voted)
			return "True";
		else
			return "False";
	}

	public String register(String username, String password, String FirstName,String LastName,String Address,
			String age) {
		Database database = new Database(socket_no);

		if (Integer.parseInt(age) < 18)
			return "False";
		boolean registered = database.register(username, password, FirstName,LastName,Address,
				age);
		if (registered)
			return "True";
		else
			return "False";
	}

	public String login(String username, String password) {
		Database database = new Database(socket_no);

		boolean loggedin = database.checkLogin(username, password);
		if (loggedin)
			return "True";
		else
			return "False";

	}

	public void recieveFromClient() {
		try {
			byte[] buffer = new byte[100]; // aSocket.getReceiveBufferSize()
			request_FromClient = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request_FromClient);

			setMessage(new String(request_FromClient.getData()));
			//System.out.println(message);
			String messages[] = message.split(";");

			switch (messages[0]) {
			case "Vote":
				message = vote(messages[1], messages[2]);

				break;
			case "Register":
				message = register(messages[1], messages[2], messages[3],
						messages[4],messages[5],messages[6]);
				break;
			case "Login":
				message = login(messages[1], messages[2]);
				break;
			case "Result":
				Database database = new Database(socket_no);
				int total = database.totalNumberOfVotes(socket_no);
				if (total == 0) message = "false";
				else {
					message = "True"+socket_no;
				}
				sendToServer3();
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("SERVER_1: recieveFromClient FAILED");
		}
	}

	public void sendToServer3() {

		try {
			byte[] m = message.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost"); // localHost
			DatagramPacket request = new DatagramPacket(m, message.length(),
					aHost, portNumber);
			aSocket.send(request);
		} catch (Exception e) {
			System.out.println("Send to server Failed!!");
		}
	}

	
}
