package Controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import Database.Database;
import View.ElectionResult;

public class ElectionTask extends TimerTask {
int serverNumber;
JFrame frame;
	public ElectionTask(int number){
	serverNumber=number;
	frame=new JFrame();
	Timer time = new Timer(); // Instantiate Timer Object
	
	time.schedule(this, 0, 15000);
}
	@Override
	public void run() {
		
   	 
    	Database database = new Database(serverNumber);
		ArrayList<String> candidates = database.getCandidates(serverNumber);
		int[] values = new int[candidates.size()];
		for (int i = 0; i < candidates.size(); i++) {
			values[i] = database.getVotesCount(serverNumber, candidates.get(i));
		}
		String[] names = new String[candidates.size()];
		for (int i = 0; i < candidates.size(); i++)
			names[i] = candidates.get(i);
		ElectionResult result = new ElectionResult(values, names);
		frame.setSize(1000, 1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.add(result);
		frame.revalidate();
	}

}
