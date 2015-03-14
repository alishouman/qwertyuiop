package Controller;

import java.net.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JOptionPane;

import Database.Database;

public class Server1 {

	private int socket_no = 1111;
	private String name = "localhost", message;
	private DatagramPacket request, request_FromClient;
	private DatagramSocket aSocket = null;
	private Random r;

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

	public void vote(String candidate) {

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

	public void login(String username, String password) {

	}

	public void recieveFromClient() {
		try {
			byte[] buffer = new byte[100]; // aSocket.getReceiveBufferSize()
			request_FromClient = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request_FromClient);

			setMessage(new String(request_FromClient.getData()));
	
			switch (message) {
			case "vote":
				aSocket.receive(request_FromClient);
				String candidate = new String(request_FromClient.getData());
				vote(candidate);
				break;
			case "Register":
				aSocket.receive(request_FromClient);
				String username = new String(request_FromClient.getData());
				aSocket.receive(request_FromClient);
				String password = new String(request_FromClient.getData());
				aSocket.receive(request_FromClient);
				 candidate = new String(request_FromClient.getData());
				aSocket.receive(request_FromClient);
				String age = new String(request_FromClient.getData());
				message=register(username, password,candidate,age);
				sendToClient();
				break;
			case "login":
				aSocket.receive(request_FromClient);
				username = new String(request_FromClient.getData());
				aSocket.receive(request_FromClient);
				password = new String(request_FromClient.getData());
				login(username, password);
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
}
