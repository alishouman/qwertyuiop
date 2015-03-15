package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainMenu extends JFrame {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    JTextField jtfAge = new JTextField(4);
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JMenuItem jmiLogin, jmiBack, jmiHelp, jmiAbout;
   


    MainMenu() {
    	
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
   

        //panel p2 to holds buttons
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(loginButton = new JButton("Login"));
        p2.add(registerButton = new JButton("Register"));

     

        //Panel with image??????

        //add panels to frame
        JPanel panel = new JPanel(new GridLayout(1, 1));
       
        panel.add(p2, BorderLayout.CENTER);
    
        add(panel, BorderLayout.CENTER);
        setTitle("Main Page");


        //listners for exit menuitem and button
      /*  jmiBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
                Login.this.dispose();
                Login.this.setVisible(false);
            }
        });*/

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
               MainMenu.this.dispose();
               MainMenu.this.setVisible(false);
               Login login=new Login();
               login.setSize(500, 500);
               login.setLocationRelativeTo(null);
               login.setVisible(true);
            }
        });

        //listner for about menuitem
        jmiAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This is the main menu panel"
                        + "\n Assignment for University",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //action listeners for Login in button and menu item
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            

                MainMenu.this.dispose();
                MainMenu.this.setVisible(false);
                Registration register=new Registration();
                register.setSize(500, 500);
                register.setLocationRelativeTo(null);
                register.setVisible(true);

            }
        });

        jmiLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
       
                MainMenu.this.dispose();
                MainMenu.this.setVisible(false);
            }
        });
    }

    public static void main(String arg[]) {
        MainMenu frame = new MainMenu();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
  
}


