package Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import Database.Database;
import View.ElectionResult;

public class BackEndServer extends Thread{
	private final int portNumber = 5555;
	private DatagramSocket aSocket = null;
	private int serverNumber=0;
	
	public static void main(String args[]) {
		BackEndServer  backEnd = new BackEndServer();
		while(true)
			backEnd.run();
	}

	public BackEndServer() {
		try {
			aSocket = new DatagramSocket(portNumber);
			System.out.println("BackEndServer, Usage: java UDPServer <" + portNumber+ ">");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
	
		if (receiveServer_1()) {
			ElectionTask thread=new ElectionTask(serverNumber);
	
			
		}
	}

	public boolean receiveServer_1() {
		try {

			byte[] buffer = new byte[20];// aSocket.getReceiveBufferSize()
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			
			if ((new String(reply.getData()).trim().substring(0, 4).equals("True"))){
				serverNumber=Integer.parseInt((new String(reply.getData()).trim()).substring(4, 8));
			
				return true;}
			else
				return false;
		} catch (Exception e) {
			System.out.println("Server couldn't register you!!");
			return false;

		}
	}
}
