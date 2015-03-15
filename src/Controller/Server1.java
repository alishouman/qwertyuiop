package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import Database.Database;

public class Server1 {

	private int socket_no = 1111;
	private String name = "localhost", message;
	private DatagramPacket request, request_FromClient;
	private DatagramSocket aSocket = null;
	private Random r;
	private ArrayList <String> candidates;
	 

	public static void main(String args[]) {
		new Server1();
	}

	public void setMessage(String message) {
		this.message = message.trim();
	}

	public String getMessage() {
		return message.trim();
	}

	public Server1() {
		try {
			System.out.println("Server_1, Usage: java UDPServer <" + socket_no
					+ ">");
			aSocket = new DatagramSocket(socket_no);
			
			candidates = new ArrayList<String>();
	    	try{
	    		generateCandidates();
	    	}catch (Exception e)
	    	{
	    		System.out.println("couldnt get the contents from the file");
	    	}
	    	
			while (true) {
				run();
			}
		} catch (Exception e) {
			System.out
					.println("couldnt connect to socket number: " + socket_no);
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

	public String vote(String username,String candidate) {
		Database database=new Database();
	    
     
    	boolean voted = database.vote(username, candidate);
      if(voted)
    	  return "True";
      else return "False";
	}

	public String register(String username, String password,String candidate,String age) {
		Database database=new Database();
    
            if (Integer.parseInt(age)<18)
            	return "False";
        	boolean registered = database.register(username, password,candidate,age);
          if(registered)
        	  return "True";
          else return "False";
	}

	public String login(String username, String password) {
		Database database=new Database();
	    
      
    	boolean loggedin = database.checkLogin(username, password);
      if(loggedin)
    	  return "True";
      else return "False";

	}

	public void recieveFromClient() {
		try {
			byte[] buffer = new byte[100]; // aSocket.getReceiveBufferSize()
			request_FromClient = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request_FromClient);

			setMessage(new String(request_FromClient.getData()));
			System.out.println(message);
			String messages[] = message.split(";");
			
			
			
				
			switch (messages[0]) {
			case "Vote":
				message=vote(messages[1], messages[2]);
				
				break;
			case "Register":
				message=register(messages[1], messages[2],messages[3],messages[4]);
				break;
			case "Login":
				message=login(messages[1], messages[2]);	
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("SERVER_1: recieveFromClient FAILED");
		}
	}
	/*
	 * public void send_Receive_Server_2() { try { r = new Random();
	 * randomGenerator = r.nextInt(3); if (randomGenerator == 0) message = "0";
	 * byte[] m = message.getBytes(); InetAddress aHost =
	 * InetAddress.getByName(name); request = new DatagramPacket(m,
	 * message.length(), aHost, portNumber);
	 * System.out.println("Server_1: sending "+getMessage()+" to Server_2");
	 * aSocket.send(request); byte[] buffer = new
	 * byte[length];//aSocket.getReceiveBufferSize() DatagramPacket reply = new
	 * DatagramPacket(buffer, buffer.length); aSocket.receive(reply);
	 * setMessage(new String(reply.getData())); if (getMessage().equals("ACK"))
	 * counterACK++; else counterNACK++;
	 * System.out.println("Server_1, Reply from Server_2: " + getMessage()); }
	 * catch (Exception e) {
	 * System.out.println("SERVER_1: send_Receive_Server_2 FAILED"); } }
	 */
	public void generateCandidates() throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("candidates.txt"));
		String line;
		while ((line = in.readLine()) != null) {
			candidates.add(line);
		}
		in.close();
	}
}
