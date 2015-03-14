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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtfUsername = new JTextField(20);
	JTextField jtfPassword = new JTextField(16);
	JTextField jtfCandidate = new JTextField(20);
	JTextField jtfAge = new JTextField(4);
	JButton backButton = new JButton("Back");
	JButton registerButton = new JButton("Register");
	JMenuItem jmiLogin, jmiBack, jmiHelp, jmiAbout;
	JLabel status = new JLabel("Status:Not Registered");
	private final int portNumber = 1111;
	private DatagramSocket aSocket = null;
	private ArrayList<String> Register_info;

	Registration() {

		Register_info = new ArrayList<String>();

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

		// add menu items with mnemonics to menu "options"
		optionsMenu.add(jmiLogin = new JMenuItem("Login", 'L'));
		optionsMenu.addSeparator();
		optionsMenu.add(jmiBack = new JMenuItem("Back", 'B'));

		// panel p1 to holds text fields
		JPanel p1 = new JPanel(new GridLayout(4, 4));
		p1.add(new JLabel("Username"));
		p1.add(jtfUsername = new JTextField(20));
		p1.add(new JLabel("Password"));
		p1.add(jtfPassword = new JPasswordField(16));
		p1.add(new JLabel("Candidate"));
		p1.add(jtfCandidate = new JTextField(20));
		p1.add(new JLabel("Age"));
		p1.add(jtfAge = new JTextField(4));

		// panel p2 to holds buttons
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(backButton = new JButton("Back"));
		p2.add(registerButton = new JButton("Register"));

		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(status = new JLabel("Status:Not Registered"));

		// Panel with image??????

		// add panels to frame
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(p1, BorderLayout.CENTER);
		panel.add(p2, BorderLayout.SOUTH);
		panel.add(p3, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
		setTitle("Main Page");

		// listners for exit menuitem and button
		/*
		 * jmiBack.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) {
		 * 
		 * Login.this.dispose(); Login.this.setVisible(false); } });
		 */

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Registration.this.dispose();
				Registration.this.setVisible(false);
			}
		});

		// listner for about menuitem
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This is the login panel"
						+ "\n Assignment for University", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// action listeners for Login in button and menu item
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					send_to_server("Register"
							+";"+jtfUsername.getText().toString()
							+";"+jtfPassword.getText().toString()
							+";"+jtfCandidate.getText().toString()
							+";"+jtfAge.getText().toString());
					/*send_to_server("Register");
					Register_info.add(jtfUsername.getText().toString());
					Register_info.add(jtfPassword.getText().toString());
					Register_info.add(jtfCandidate.getText().toString());
					Register_info.add(jtfAge.getText().toString());
					for (int i = 0; i < Register_info.size(); i++) {
						send_to_server(Register_info.get(i));
					}*/
					if (!receiveServer_1()) {
						status.setText("Status:Not Registered");
						JOptionPane.showMessageDialog(null,
								"Sorry, wrong credentials");
						return;
					} else {
						status.setText("Status:Registered");
					}
				} catch (Exception se) {
					se.printStackTrace();
					JOptionPane.showMessageDialog(null,
								"Sorry, couldn't check your credentials. Check the logs and report the problem to an administrator.");
					return;
				}

				/*
				 * Database database=new Database(); try { boolean registered =
				 * database.register(jtfUsername.getText(),
				 * jtfPassword.getText()
				 * ,jtfDistrict.getText(),jtfAge.getText()); if (!registered) {
				 * //JOptionPane.showMessageDialog(null,
				 * "Sorry, wrong credentials");
				 * status.setText("Status:Not Registered");
				 * JOptionPane.showMessageDialog
				 * (null,"Sorry, wrong credentials"); return; } else{
				 * status.setText("Status:Registered");
				 * 
				 * } } catch (Exception se) { se.printStackTrace();
				 * JOptionPane.showMessageDialog(null,
				 * "Sorry, couldn't check your credentials. Check the logs and report the problem to an administrator."
				 * ); return; }
				 */
			}
		});

		jmiLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Registration.this.dispose();
				Registration.this.setVisible(false);
			}
		});
	}

	public static void main(String arg[]) {
		Registration frame = new Registration();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void send_to_server(String message) {
		try {
			aSocket = new DatagramSocket();
			byte[] m = message.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost"); // localHost
			DatagramPacket request = new DatagramPacket(m, message.length(),
					aHost, portNumber);
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
