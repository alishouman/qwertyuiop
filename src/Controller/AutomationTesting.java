package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import Database.Database_2;

public class AutomationTesting {
	private ArrayList <String> candidates;
	private ArrayList <String> names;
	private Random r;
	private int randomGenerator = 0;

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
	
	public void run () throws Exception
	{
		names = new ArrayList <String>();
		String FileName = "UsersSimulation.txt";
		Database_2 database  = new Database_2();
		database.generateCandidates("candidates_2.txt");
		readFile(FileName);
		for (int i=0; i<names.size();i++)
		{
			r = new Random();
			randomGenerator = r.nextInt(candidates.size());
			database.vote(names.get(i),candidates.get(randomGenerator));
		}
		outputToFile("outputResults.txt");
	}

	public void readFile(String fileName) throws Exception
	{
		String array[];
		candidates = new ArrayList <String>();
		Database_2 database  = new Database_2();
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
			out.println("Votes for District 1");
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
}
