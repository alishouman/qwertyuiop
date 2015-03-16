package Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import Database.Database;
import View.ElectionResult;

public class Server3 {
	private final int portNumber =3333;
	private DatagramSocket aSocket = null;
	public static void main(String args[]) {
		new Server3();
	}
	public Server3() {
		try {
			aSocket = new DatagramSocket(portNumber);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
			while (true) {
				run();
			}
		
	}
	public void run(){
		ElectionResult result;
		if(receiveServer_1()){
			Database database=new Database();
			ArrayList<String>candidates=database.getCandidates(1111);
			int [] values=new int [candidates.size()];
			for(int i=0;i<candidates.size();i++){
				values[i]=database.getVotesCount(1111, candidates.get(i));
				System.out.println("Value is "+values[i]);
			}
			
	 result=new ElectionResult(values);
		result.setSize(500, 500);
        result.setLocationRelativeTo(null);
        result.setVisible(true);}
	}
	public boolean receiveServer_1() {
		try {
			
			byte[] buffer = new byte[6];// aSocket.getReceiveBufferSize()
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			System.out.println("UDPClient1, Reply: "
					+ new String(reply.getData()).trim());
			if ((new String(reply.getData()).trim().equals("True")))
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("Server couldn't register you!!");
			return false;

		}
	}
}
