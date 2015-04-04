package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.AutomationTesting;

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField jtfAge = new JTextField(4);
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JButton testButton = new JButton("Test");
	JMenuItem jmiHelp, jmiAbout;

	MainMenu() {

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

		// panel p2 to holds buttons
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(loginButton = new JButton("Login"));
		p2.add(registerButton = new JButton("Register"));
		p2.add(testButton = new JButton("Test"));

		// Panel with image??????

		// add panels to frame
		JPanel panel = new JPanel(new GridLayout(1, 1));

		panel.add(p2, BorderLayout.CENTER);

		add(panel, BorderLayout.CENTER);
		setTitle("Main Page");

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("before LOGIN");
				MainMenu.this.dispose();
				MainMenu.this.setVisible(false);
				Login login = new Login();
				login.pack();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.out.println("after LOGIN");
			}
		});

		// listner for about menuitem
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"This is the main menu panel"
								+ "\n Assignment for University", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// action listeners for Login in button and menu item
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MainMenu.this.dispose();
				MainMenu.this.setVisible(false);
				Registration register = new Registration();
				register.pack();
				register.setLocationRelativeTo(null);
				register.setVisible(true);
				register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AutomationTesting test = new AutomationTesting();
			}
		});
	
	}

	public static void main(String arg[]) {
		MainMenu frame = new MainMenu();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
