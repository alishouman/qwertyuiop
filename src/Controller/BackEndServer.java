package Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import Database.Database;
import View.ElectionResult;

public class BackEndServer {
	private final int portNumber = 5555;
	private DatagramSocket aSocket = null;
	
	public static void main(String args[]) {
		new BackEndServer();
	}

	public BackEndServer() {
		try {
			aSocket = new DatagramSocket(portNumber);
			System.out.println("Server_3, Usage: java UDPServer <" + portNumber+ ">");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			run();
		}
	}

	public void run() {
		ElectionResult result;
		if (receiveServer_1()) {
			Database database = new Database();
			ArrayList<String> candidates = database.getCandidates(1111);
			int[] values = new int[candidates.size()];
			for (int i = 0; i < candidates.size(); i++) {
				values[i] = database.getVotesCount(1111, candidates.get(i));
			}
			String[] names = new String[candidates.size()];
			for (int i = 0; i < candidates.size(); i++)
				names[i] = candidates.get(i);
			result = new ElectionResult(values, names);
			result.setSize(1000, 1000);
			result.setLocationRelativeTo(null);
			result.setVisible(true);
		}
	}

	public boolean receiveServer_1() {
		try {

			byte[] buffer = new byte[6];// aSocket.getReceiveBufferSize()
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
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
