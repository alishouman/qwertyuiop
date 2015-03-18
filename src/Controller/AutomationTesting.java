package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import Database.Database_2;

public class AutomationTesting {
	private ArrayList <String> candidates;
	private ArrayList <String> names;
	private Random r;
	private int randomGenerator = 0;

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
		database.generateCandidates("candidates.txt");
		readFile(FileName);
		System.out.println("names.size(): "+candidates.size());
		for (int i=0; i<names.size();i++)
		{
			r = new Random();
			System.out.println("hello");
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
			database.register(array[0], array[1], array[2], array[3], array[4], array[5]);
		}
		in.close();
	}

	public void outputToFile(String fileName) {
		// create an print writer for writing to a file
		PrintWriter out;
		Database_2 database = new Database_2();
		try {
			out = new PrintWriter(new FileWriter(fileName));
			out.println("Votes for District 1");
			for (String candidate : candidates) {
				out.println("candidate name: " + candidate + ", and has "
						+ database.getVotesCount(1111, candidate) + " Votes.");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
