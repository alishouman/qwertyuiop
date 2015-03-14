package View;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;








import javax.swing.*;

import Database.Database;

public class Login extends JFrame {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtfUsername = new JTextField(15);
    JTextField jtfPassword = new JTextField(15);
    JComboBox districts;
    JButton backButton = new JButton("Back");
    JButton loginButton = new JButton("Login");
    JMenuItem jmiLogin, jmiBack, jmiHelp, jmiAbout;
    JLabel status = new JLabel("Status:Not logged in");


    Login() {

    	   String[] districtNames = { "District 1", "District 2", "District 3", "District 4", "District 5" };
    		  districts =new JComboBox(districtNames);
        //create menu bar
        JMenuBar jmb = new JMenuBar();

        //set menu bar to the applet
        setJMenuBar(jmb);

        //add menu "operation" to menu bar
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic('O');
        jmb.add(optionsMenu);

        //add menu "help"
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpMenu.add(jmiAbout = new JMenuItem("About", 'A'));
        jmb.add(helpMenu);

        //add menu items with mnemonics to menu "options"
        optionsMenu.add(jmiLogin = new JMenuItem("Login", 'L'));
        optionsMenu.addSeparator();
        optionsMenu.add(jmiBack = new JMenuItem("Back", 'B'));

        //panel p1 to holds text fields
        JPanel p1 = new JPanel(new GridLayout(3, 3));
        p1.add(new JLabel("Username"));
        p1.add(jtfUsername = new JTextField(15));
        p1.add(new JLabel("Password"));
        p1.add(jtfPassword = new JPasswordField(15));
        p1.add(new JLabel("Choose your district "));
        p1.add(districts);

        //panel p2 to holds buttons
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(backButton = new JButton("Back"));
        p2.add(loginButton = new JButton("Login"));

        JPanel p3 = new JPanel(new FlowLayout());
        p3.add(status = new JLabel("Status:Not logged in"));

        //Panel with image??????

        //add panels to frame
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(p1, BorderLayout.CENTER);
        panel.add(p2, BorderLayout.SOUTH);
        panel.add(p3, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        setTitle("Main Page");


        //listners for exit menuitem and button
        jmiBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
                Login.this.dispose();
                Login.this.setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
                Login.this.dispose();
                Login.this.setVisible(false);
            }
        });

        //listner for about menuitem
        jmiAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This is the login panel"
                        + "\n Assignment for University",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //action listeners for Login in button and menu item
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Database database=new Database();
               try {
                   boolean loggedIn = database.checkLogin(jtfUsername.getText(), jtfPassword.getText());
                   if (!loggedIn) {
                        //JOptionPane.showMessageDialog(null, "Sorry, wrong credentials");
                        status.setText("Status:Password or Username is incorrect");
                        JOptionPane.showMessageDialog(null,"Sorry, wrong credentials");
                        return;
                    }
                   else{
                	   status.setText("Status:Logged in");
                	   Login.this.dispose();
                      Login.this.setVisible(false);
                    
                      Vote vote= new Vote();
                      vote.setSize(500, 500);
                      vote.setLocationRelativeTo(null);
                      vote.setVisible(true);
                     
                   }
                } catch (Exception se) {
                    se.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Sorry, couldn't check your credentials. Check the logs and report the problem to an administrator.");
                    return;
                }
                


            }
        });

        jmiLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
       
                Login.this.dispose();
                Login.this.setVisible(false);
            }
        });
    }

    public static void main(String arg[]) {
        Login frame = new Login();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


