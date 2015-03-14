package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





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
    JButton registerButton = new JButton("Register");
    JMenuItem jmiLogin, jmiBack, jmiHelp, jmiAbout;
    JLabel status = new JLabel("Status:Not Registered");


   Vote(String username) {
	   this.username=username;
	   String[] candidateNames = { "Candidate 1", "Candidate 2", "Candidate 3", "Candidate 4", "Candidate 5" };
	   this.candidates =new JComboBox(candidateNames);
	  
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
        JPanel p1 = new JPanel(new GridLayout(1, 1));
        p1.add(new JLabel("Choose your candidate "));
        p1.add(candidates);

        //panel p2 to holds buttons
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(backButton = new JButton("Back"));
        p2.add(registerButton = new JButton("Register"));

        JPanel p3 = new JPanel(new FlowLayout());
        p3.add(status = new JLabel("Status:Not Registered"));

        //Panel with image??????

        //add panels to frame
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(p1, BorderLayout.CENTER);
        panel.add(p2, BorderLayout.SOUTH);
        panel.add(p3, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        setTitle("Main Page");


        //listners for exit menuitem and button
      /*  jmiBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
                Login.this.dispose();
                Login.this.setVisible(false);
            }
        });*/

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
                Vote.this.dispose();
                Vote.this.setVisible(false);
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
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Database database=new Database();
               try {
                  boolean voted = database.vote(username,candidates.getSelectedItem().toString());
                   if (!voted) {
                        //JOptionPane.showMessageDialog(null, "Sorry, wrong credentials");
                        status.setText("Status:Not Registered");
                        JOptionPane.showMessageDialog(null,"Sorry, wrong credentials");
                        return;
                    }
                   else{
                	   status.setText("Status:Registered");
                     
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
       
                Vote.this.dispose();
                Vote.this.setVisible(false);
            }
        });
    }

   
}


