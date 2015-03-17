package testJUNIT;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class test {	
	   @Test
	    public void testConcatenate() {
		    ArrayList <String> omar = new ArrayList<String>() ;
		    ArrayList <String> omar2  = new ArrayList<String>();
	        practice myUnit = new practice();
	        int result = myUnit.concatenate(1, 2);
	        assertEquals(omar, omar2);
	   }
}
