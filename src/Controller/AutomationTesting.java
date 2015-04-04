package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import View.MainMenu;
import View.Registration;
import Database.Database_2;

public class AutomationTesting extends JFrame implements Runnable{
	private ArrayList <String> candidates;
	private ArrayList <String> names;
	private Random r;
	private int randomGenerator = 0;
	private Database_2 database;
	private JLabel jtfAge = new JLabel();
	private JLabel jtffiles = new JLabel();
	private JLabel InputFile = new JLabel();
	private JButton testButton = new JButton("Test");
	private JMenuItem jmiHelp, jmiAbout;
	private JComboBox districts;
	private JComboBox JCfile;
	private String FileName = "Simulation/UsersSimulation.txt";
	public static void main(String args[]) {
		new AutomationTesting();
	}
	public AutomationTesting ()
	{
		try {
			
			run ();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("run failed!");
		}
	}
	
	public void run () 
	{
		SearchInTextFiles("Input");
		createGUI ();
		testButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			names = new ArrayList <String>();
			database  = new Database_2();
			database.cleanDatabase();
			try {
				database.generateCandidates("input/"+JCfile.getSelectedItem().toString());
				readFile(FileName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for (int i=0; i<names.size();i++)
			{
				r = new Random();
				randomGenerator = r.nextInt(candidates.size());
				database.vote(names.get(i),candidates.get(randomGenerator));
			}
			outputToFile("output/"+districts.getSelectedItem().toString());
			JOptionPane.showMessageDialog(null,
					"You have successufly ran the automation testing, "
							+ "please check the output file for results",
					"Automation testing", JOptionPane.INFORMATION_MESSAGE);

			}
		
		});
		
	}
		
	private void createGUI ()
	{
		JFrame frame = new JFrame();
		String[] districtNames = { "District 1","District 2", "District 3"};
		districts = new JComboBox(districtNames);
		JMenuBar jmb = new JMenuBar();
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
		jtfAge = new JLabel("Districts:");
		jtffiles = new JLabel ("Input files");
		p2.add(jtfAge);
		p2.add(districts);
		p2.add(jtffiles);
		p2.add (JCfile);
		p2.add(testButton = new JButton("Test"));
		
		// Panel with image??????

		// add panels to frame
		JPanel panel = new JPanel(new GridLayout(1, 1));

		panel.add(p2, BorderLayout.CENTER);
		setTitle ("Automation testing");
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void readFile(String fileName) throws Exception
	{
		String array[];
		candidates = new ArrayList <String>();
		candidates = database.getCandidates(1111);
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line = in.readLine()) != null) {
			array = line.split(";");
			names.add(array[0]);
			if (Integer.parseInt(array[5]) >=18)
			database.register(array[0], array[1], array[2], array[3], array[4], array[5]);
		}
		in.close();
	}

	public void outputToFile(String fileName) {
		// create an print writer for writing to a file
		PrintWriter out;
		int vote=0;
		int allVotes = 0;
		double average=0.0;
		String winner = "";
		double max =0.0;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		Database_2 database = new Database_2();
		try {
			out = new PrintWriter(new FileWriter(fileName));
			out.println("Votes for "+districts.getSelectedItem().toString()+"\n");
			for (String candidate : candidates) {
				vote = database.getVotesCount(1111, candidate);
				allVotes = database.totalNumberOfVotes(1111) ;
				average =( (vote*1.0) / (allVotes*1.0) ) * 100;
				out.println("candidate name: " + candidate + ", and has "
						+ database.getVotesCount(1111, candidate) + " "
								+ "Votes with a percentage of "
						+ numberFormat.format(average));
				if (max <average)
				{
					max = average;
					winner = candidate;
				}		
			}
			out.println("\n\nCongratulations to "+winner+
					" You have won this District with a percentage of "
					+numberFormat.format(max));
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void SearchInTextFiles(String directory) {
		
		JCfile = new JComboBox();
		File dir = new File(directory);
		  for (File file : dir.listFiles()) {
          if (file.getName().endsWith(".txt")) {
        	  	JCfile.addItem(file.getName().toString());
            }
        }
	}
}
