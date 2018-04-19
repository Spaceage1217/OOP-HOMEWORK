package hardwarestore;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;


public class TestInputs {
	  
	  MainApp testApp;

	  String expectedOutput;
	  String returnedOutput;
	  String separator = System.getProperty("line.separator");
	  private final ByteArrayOutputStream os = new ByteArrayOutputStream();
	  


	  @Before
	  public void setEnvironment(){
		  System.setOut(new PrintStream(os));
	  }
	  @After
	  public void resetEnvironment() throws IOException{
		  System.setOut(System.out);
		  expectedOutput = null;
		  returnedOutput = null;
	  }
	  
	
	  
	  /**
	  This method will test to see if the regex expression guards against invalid input and prints correct output to screen
   */
	  @Test
	  public void invalidIDTest() throws IOException {
		
		String idNumber ="!!!!";
		  
		  if (!idNumber.matches("[A-Za-z0-9]{5}")) {
	             System.out.println("Invalid ID Number: not proper format. "
	                     + "ID Number must be 5 alphanumeric characters.\n");
	         }
		  expectedOutput = "Invalid ID Number: not proper format. "
            +"ID Number must be 5 alphanumeric characters.\n" + separator;
		  returnedOutput = os.toString();
		  assertTrue(separator+ "Expected Output: "+expectedOutput+"Returned Output: "+ returnedOutput,expectedOutput.equals(returnedOutput));
		 
		   
	  }
	  /**
		  This method will test to see if the expected output is delivered when you give a bad input
	   */

	  
	  @Test
	  public void invalidSearchByQuantityTest() throws IOException{
		  ByteArrayInputStream in = new ByteArrayInputStream("-12".getBytes());
		  System.setIn(in);
		  testApp = new MainApp();
		  
		  
		  testApp.searchItemByQuantity();
		  //grabbing returned strings based on the way Junye Wen formated the text.
		  returnedOutput = os.toString().split(separator)[1] +'\n'+ os.toString().split(separator)[2];
		  
		  expectedOutput = "Quantity should be at least 0.\n\n";
		  expectedOutput+= "No items found below given quantity.";
		  assertTrue(separator+ "Expected Output: "+expectedOutput+separator+ "Returned Output: "+ returnedOutput,expectedOutput.equals(returnedOutput));
		  
		  System.setIn(System.in);
	  }
 	
	
}

