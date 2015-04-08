package Controller;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import Database.Database;
import View.ElectionResult;

public class ElectionTask extends TimerTask {
int serverNumber;
ElectionResult result ;
JFrame frame=new JFrame();
	public ElectionTask(int number){
	serverNumber=number;
	frame=new JFrame();
	 result = new ElectionResult(null,null);
	Timer time = new Timer(); // Instantiate Timer Object
	
	time.schedule(this, 0, 15000);
}
	@Override
	public void run() {
		//frame.dispose();
	frame.remove(result);
		frame.setSize(1000,1000);
		frame.setLayout(new BorderLayout());
    	Database database = new Database(serverNumber);
		ArrayList<String> candidates = database.getCandidates(serverNumber);
		int[] values = new int[candidates.size()];
		for (int i = 0; i < candidates.size(); i++) {
			values[i] = database.getVotesCount(serverNumber, candidates.get(i));
		}
		String[] names = new String[candidates.size()];
		for (int i = 0; i < candidates.size(); i++)
			names[i] = candidates.get(i);
	 result = new ElectionResult(values, names);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//frame.removeAll();
		frame.add(result,BorderLayout.CENTER);
	
		frame.revalidate();
	}

}
