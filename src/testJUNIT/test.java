package testJUNIT;



import Controller.Server1;
import Database.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class test {	
	   @Test
	    public void testVote() {
		    Server1 myUnit = new Server1();
	        String result = myUnit.vote("peter1234", "David");
	        assertEquals("True", result);
	   }
	   
	   @Test
	    public void testVote2() {
		    Server1 myUnit = new Server1();
	        String result = myUnit.vote("peter", "David");
	        assertEquals("True", result);
	   }
	   
	   @Test
	   public void testLogin() {
	        Server1 myUnit = new Server1();
	        String result = myUnit.vote("peter1234", "12345");
	        assertEquals("True", result);
	   }
	   
	   @Test
	   public void testLogin2() {
	        Server1 myUnit = new Server1();
	        String result = myUnit.vote("peter", "12345");
	        assertEquals("False", result);
	   }
}
