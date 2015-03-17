package testJUNIT;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class test {	
	   @Test
	    public void testConcatenate() {
		    ArrayList <String> omar = new ArrayList<String>() ;
		    ArrayList <String> omar2  = new ArrayList<String>();
	        vote myUnit = new vote();
	        int result = myUnit.vote(1, 2);
	        assertEquals(omar, omar2);
	   }
}
