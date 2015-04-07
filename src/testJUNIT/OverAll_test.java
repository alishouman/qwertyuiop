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

public class OverAll_test {
	Database database1;
	Database database2;
	Database database3;
	private int D1_portNumber = 1111;
	private int D2_portNumber = 2222;
	private int D3_portNumber = 3333;
	@Before
	public void setUp() throws Exception {
		database1 = new Database(D1_portNumber);
		database1.cleanDatabase();
		String fileName1 = "Input/File_1.txt";
		database1.generateCandidates(fileName1);
		
		database2 = new Database(D2_portNumber);
		database2.cleanDatabase();
		String fileName2 = "Input/File_2.txt";
		database2.generateCandidates(fileName2);
		
		database3 = new Database(D3_portNumber);
		database3.cleanDatabase();
		String fileName3 = "Input/File_3.txt";
		database3.generateCandidates(fileName3);
	}

	@After
	public void tearDown() throws Exception {

		database1.cleanDatabase();
		database2.cleanDatabase();
		database3.cleanDatabase();
	}

	@Test
	public void Regsiter_different_districts()
	{
		database1.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database2.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}

	@Test
	public void Regsiter_different_districts_2()
	{
		database1.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database3.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}

	@Test
	public void Regsiter_different_districts_3()
	{
		database2.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database1.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}
	@Test
	public void Regsiter_different_districts_4()
	{
		database2.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database3.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}
	
	@Test
	public void Regsiter_different_districts_5()
	{
		database3.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database1.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}
	@Test
	public void Regsiter_different_districts_6()
	{
		database3.register("test_1", "test_1", "Ahmed", "Omar", "Canada", "20");
		Boolean result = database2.checkLogin("test_1", "test_1");
		assertEquals(result, false);
	}
	
	public ArrayList<String> readFile(String fileName) throws IOException {
		ArrayList<String> candidates = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader("Input/File_3.txt"));
		String line;
		while ((line = in.readLine()) != null) {
			candidates.add(line);
		}
		in.close();
		return candidates;
	}
/***********************************************************************************************/	
	
	
	
	
	
}