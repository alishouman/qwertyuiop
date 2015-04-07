package Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Database.Database;

public class Server3 extends Thread{

	private int socket_no = 3333;
	private final int portNumber = 5555;
	private String message;
	private DatagramPacket request_FromClient;
	private DatagramSocket aSocket = null;
	 private Semaphore sem = new Semaphore(10, true);
private String messageQueue [][]=new String[10][7];
private int index=0;
private Semaphore lock=new Semaphore(1,true);
	public static void main(String args[]) {
		Server3 server3 = new Server3();
		server3.run();
	}

	public void setMessage(String message) {
		this.message = message.trim();
	}

	public String getMessage() {
		return message.trim();
	}

	public Server3() {
		try {
			System.out.println("Server_3, Usage: java UDPServer <" + socket_no
					+ ">");
			aSocket = new DatagramSocket(socket_no);

			Database database = new Database(socket_no);
			database.cleanDatabase();
			try {
				database.generateCandidates("input/File_3.txt");
			} catch (Exception e) {
				System.out.println("generate Candidates failed!!");
			}
		} catch (Exception e) {
			System.out.println("couldnt connect to socket number: " + socket_no);
		}
	}

	public void run()  {
		while (true) {
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			recieveFromClient();
			
			sem.release();
		}
	}

	public void sendToClient() {
		try {
			
			byte[] m = getMessage().getBytes();
			
			request_FromClient.setData(m);
		
			aSocket.send(request_FromClient);
			lock.release();
		
		} catch (Exception e) {
			System.out.println("SERVER_3: sendToClient FAILED");
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

	public  void recieveFromClient() {
		try {
			lock.acquire();
			byte[] buffer = new byte[100]; // aSocket.getReceiveBufferSize()
			request_FromClient = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request_FromClient);
			
			setMessage(new String(request_FromClient.getData()));
			//System.out.println(message);
			String messages[] = message.split(";");
		
			index=9-sem.availablePermits();
			messageQueue[index]=messages;
			Thread slave=new Thread(){
			    @Override
			    public void run() {
			    	switch (messageQueue[index][0]) {
					case "Vote":
						message = vote(messageQueue[index][1], messageQueue[index][2]);
						sendToClient();

						break;
					case "Register":
						message = register(messageQueue[index][1], messageQueue[index][2], messageQueue[index][3],
								messageQueue[index][4],messageQueue[index][5],messageQueue[index][6]);
						System.out.println(message);
						sendToClient();
						break;
					case "Login":
						message = login(messageQueue[index][1], messageQueue[index][2]);
						sendToClient();
						break;
					case "Result":
						Database database = new Database(socket_no);
						int total = database.totalNumberOfVotes(socket_no);
						if (total == 0) message = "false";
						else {
							message = "True"+socket_no;
							
						}
						sendToServer3();
						sendToClient();
						
					default:
						break;
					}
			    }
			
			
		};
		slave.start();
		
		
		}catch (Exception e) {
			System.out.println("SERVER_3: recieveFromClient FAILED");
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
