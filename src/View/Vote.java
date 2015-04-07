package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.*;

import Database.Database;

public class Vote extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JComboBox candidates;
	String username;
	JButton backButton = new JButton("Back");
	JButton voteButton = new JButton("Vote");
	JButton resultButton = new JButton("View Results");
	JMenuItem jmiHelp, jmiAbout;
	JLabel status = new JLabel("Status:Not Registered");
	private  int serverNumber = 0;
	private DatagramSocket aSocket = null;
	private ArrayList<String> Register_info;

	Vote(String username1, ArrayList<String> candidates2,int portNumber) {
		serverNumber=portNumber;
		this.username = username1;
		String[] candidateNames = new String[candidates2.size()];
		for (int i = 0; i < candidates2.size(); i++)
			candidateNames[i] = candidates2.get(i);

		this.candidates = new JComboBox(candidateNames);

		// create menu bar
		JMenuBar jmb = new JMenuBar();

		// set menu bar to the applet
		setJMenuBar(jmb);

		// add menu "operation" to menu bar
		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic('O');
		jmb.add(optionsMenu);

		// add menu "help"
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		helpMenu.add(jmiAbout = new JMenuItem("About", 'A'));
		jmb.add(helpMenu);

	

		// panel p1 to holds text fields
		JPanel p1 = new JPanel(new GridLayout(1, 1));
		p1.add(new JLabel("Choose your candidate "));
		p1.add(candidates);

		// panel p2 to holds buttons
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(backButton = new JButton("Back"));
		p2.add(voteButton = new JButton("Vote"));
		p2.add(resultButton = new JButton("View Results"));

		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(status = new JLabel("Status:Havent Voted"));

		// Panel with image??????

		// add panels to frame
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(p1, BorderLayout.CENTER);
		panel.add(p2, BorderLayout.SOUTH);
		panel.add(p3, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
		setTitle("Vote Page");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Vote.this.dispose();
				Vote.this.setVisible(false);
				Login login = new Login();
				login.pack();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

		// listner for about menuitem
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This is the voting panel"
						+ "\n Assignment for University", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		resultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					send_to_server("Result");
					if (!receiveServer_1()) {
						JOptionPane.showMessageDialog(null,
								"Sorry, No body placed a vote yet!\n"+"Be the first one to vote...");
						return;
					}

				} catch (Exception se) {
					se.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Sorry, couldn't check your credentials. "
							+ "Check the logs and report the problem to an administrator.");
					return;
				}
			}
		});
		// action listeners for Login in button and menu item
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					send_to_server("Vote" + ";" + username + ";"
							+ candidates.getSelectedItem().toString());
					if (!receiveServer_1()) {
						status.setText("Status:Havent Voted");
						JOptionPane.showMessageDialog(null,
								"Sorry, You have already placed you vote...");
						return;
					} else {
						status.setText("Status:Voted");
					}
				} catch (Exception se) {
					se.printStackTrace();
					JOptionPane.showMessageDialog(null,
					"Sorry, couldn't check your credentials. "
					+ "Check the logs and report the problem to an administrator.");
					return;
				}
			}
		});
	}
	public void send_to_server(String message) {
		try {
			aSocket = new DatagramSocket();
			byte[] m = message.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost"); // localHost
			DatagramPacket request = new DatagramPacket(m, message.length(),
					aHost, serverNumber);
			aSocket.send(request);
		} catch (Exception e) {
			System.out.println("Send to server Failed!!");
		}
	}

	public boolean receiveServer_1() {
		try {
			byte[] buffer = new byte[6];// aSocket.getReceiveBufferSize()
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			if ((new String(reply.getData()).trim().substring(0,4).equals("True")))
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("Server couldn't register you!!");
			return false;
		}
	}
}
