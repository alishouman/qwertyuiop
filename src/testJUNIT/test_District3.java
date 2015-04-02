package testJUNIT;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.*;

public class test_District3 {
	Database database;
	private int portNumber = 3333;
	@Before
	public void setUp() throws Exception {
		database = new Database(portNumber);
		database.cleanDatabase();
		String fileName = "candidates.txt";
		database.generateCandidates(fileName);
	}

	@After
	public void tearDown() throws Exception {

		database.cleanDatabase();

	}

	@Test
	public void testVote() {
		// Server1 myUnit = new Server1();
		database.register("peter1234", "12345", "Peter", "Hani", "Canada", "30");
		Boolean result = database.vote("peter1234", "Ahmed");
		assertEquals(true, result);
	}

	@Test
	public void testLogin() {

		database.register("peter1234", "12345", "Peter", "Hani", "Canada", "30");
		database = new Database(portNumber);
		Boolean result = database.checkLogin("peter1234", "12345");
		assertEquals(true, result);
	}

	@Test
	public void testLogin2() {

		Boolean result = database.checkLogin("peter12", "12345");
		assertEquals(false, result);
	}

	@Test
	public void testRegister() {

		Boolean result = database.register("ali", "12345", "Peter", "Hani",
				"Canada", "30");
		assertEquals(true, result);
	}

	@Test
	public void test_fillCandidates() throws Exception {

		String fileName = "candidates.txt";
		ArrayList<String> candidates = new ArrayList<String>();
		ArrayList<String> candidates_2 = new ArrayList<String>();

		candidates = readFile(fileName);
		database.generateCandidates(fileName);
		candidates_2 = database.getCandidates(portNumber);
		Collections.sort(candidates);
		Collections.sort(candidates_2);
		assertEquals(candidates, candidates_2);
	}

	@Test
	public void test_voteCount() {
		int result = 0;
		int result_2 = 0;

		database.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		result = database.getVotesCount(portNumber, "Ahmed");
		database.vote("test_1", "Ahmed");
		result_2 = database.getVotesCount(portNumber, "Ahmed");
		assertEquals(result + 1, result_2);
	}

	public ArrayList<String> readFile(String fileName) throws IOException {
		ArrayList<String> candidates = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader("candidates.txt"));
		String line;
		while ((line = in.readLine()) != null) {
			candidates.add(line);
		}
		in.close();
		return candidates;
	}
/***********************************************************************************************/	
	
	
	
	
	
}