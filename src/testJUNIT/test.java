package testJUNIT;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Database.*;
import Controller.*;

public class test {	
	  
	   @Test
	   public void test_fillCandidates () throws Exception
	   {
		   String fileName = "candidates.txt";
		   Database database = new Database(); 
		   ArrayList<String> candidates = new ArrayList<String>();
		   ArrayList<String> candidates_2 = new ArrayList<String>();
		   
		   candidates = readFile(fileName);
		   database.generateCandidates(fileName);
		   candidates_2 = database.getCandidates(1111);
		   assertEquals(candidates,candidates_2);
	   }
	   
	   //@Test 
	   public void test_voteCount () 
	   {
		   int result = 0;
		   int result_2= 0;
		   Database database = new Database();
		   database.register("test_1", "test_1", "Ahmed", "20");
		   result = database.getVotesCount(1111, "Ahmed");
		   database.vote("test_1", "Ahmed");
		   result_2 = database.getVotesCount(1111, "Ahmed");
		   assertEquals (result+1, result_2);
		   
		  /* database.vote("mohamed", "David");
		   result = database.getVotesCount(1111, "David");
		   System.out.println(result);
		   assertEquals (result, 2);
		   */
	   }
	   public ArrayList<String> readFile (String fileName) throws IOException
	   {
		   ArrayList<String> candidates = new ArrayList<String>();
		   BufferedReader in = new BufferedReader(new FileReader("candidates.txt"));
			String line;
			while ((line = in.readLine()) != null) {
				candidates.add(line);
			} 
			in.close();
			return candidates;
	   }
}
