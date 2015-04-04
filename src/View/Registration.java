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
import javax.swing.JComboBox;
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
	JTextField jtfFirstName = new JTextField(20);
	JTextField jtfLastName = new JTextField(20);
	JTextField jtfAddress = new JTextField(20);
	JTextField jtfAge = new JTextField(4);
	JComboBox districts;
	JButton backButton = new JButton("Back");
	JButton registerButton = new JButton("Register");
	JMenuItem jmiHelp, jmiAbout;
	JLabel status = new JLabel("Status:Not Registered");

	private DatagramSocket aSocket = null;
	private ArrayList<String> Register_info;

	Registration() {

		Register_info = new ArrayList<String>();

		// create menu bar
		JMenuBar jmb = new JMenuBar();
		String[] districtNames = { "District 1","District 2", "District 3"};
		districts = new JComboBox(districtNames);

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
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.add(new JLabel("FirstName"));
		p1.add(jtfFirstName = new JTextField(20));
		p1.add(new JLabel("LastName"));
		p1.add(jtfLastName = new JTextField(20));
		p1.add(new JLabel("Username"));
		p1.add(jtfUsername = new JTextField(20));
		p1.add(new JLabel("Password"));
		p1.add(jtfPassword = new JPasswordField(16));
		p1.add(new JLabel("Address"));
		p1.add(jtfAddress = new JTextField(20));
		p1.add(new JLabel("Districts"));
		p1.add(districts);
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
		setTitle("Registration Page");
		pack();

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Registration.this.dispose();
				Registration.this.setVisible(false);
				MainMenu main = new MainMenu();
				main.pack();
				main.setLocationRelativeTo(null);
				main.setVisible(true);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
					send_to_server("Register" + ";"
							+ jtfUsername.getText().toString() + ";"
							+ jtfPassword.getText().toString() + ";"
							+ jtfFirstName.getText().toString() + ";"
							+ jtfLastName.getText().toString() + ";"
							+ jtfAddress.getText().toString() + ";"
							+ jtfAge.getText().toString());
					if (!receiveServer_1()) {
						status.setText("Status:Not Registered");
						JOptionPane
								.showMessageDialog(null,
										"Registration failed ! Please try again.");
						return;
					} else {
						status.setText("Status:Registered");
						MainMenu menu = new MainMenu();
						dispose();
						setVisible(false);
						JOptionPane
						.showMessageDialog(null,
								"You have successufly registered into our system, thanks...");
						menu.pack();
						menu.setLocationRelativeTo(null);
						menu.setVisible(true);
						menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}
				} catch (Exception se) {
					se.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"Sorry, couldn't check your credentials. Check the logs and report the problem to an administrator.");
					return;
				}
			}
		});

	}

	public static void main(String arg[]) {
		Registration frame = new Registration();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void send_to_server(String message) {
		try {
			aSocket = new DatagramSocket();
			byte[] m = message.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost"); // localHost
			DatagramPacket request = new DatagramPacket(m, message.length(),
					aHost, returnPortNumber());
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
			
			if ((new String(reply.getData()).trim().equals("True")))
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("Server couldn't register you!!");
			return false;

		}
	}
	public int returnPortNumber ()
	{
		String choice = districts.getSelectedItem().toString();
		switch (choice){
		case "District 1":
			return 1111;
		case "District 2":
			return 2222;
		case "District 3":
			return 3333;
		default:
			return 0;
		}
	}
}
