package hardwarestore;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
  public static void main( String[] args) throws Exception
  {
	/*
	 * NOTE:  For some reason the TestInput class does not allow the test runner
	 *  to finish even though all test are completed. It seems to be related to the
	 *  		 System.setOut(new PrintStream(os))<--- function
	 *  When I comment it out the test seems to complete. but without it my test cases will fail
	 *  I have left the input test *TestInputs.class* but to see the runner finish you can just run
	 *  the *TestHardwareInventory*
	 * */
	  
	//Result result = JUnitCore.runClasses(TestHardwareInventory.class);
    Result result = JUnitCore.runClasses(TestHardwareInventory.class,TestInputs.class);
    int numOfFailures = result.getFailureCount();
    if( result.wasSuccessful())
    {
      System.out.println("All tests successful !!!");
      System.out.println("# of Test ran : "+result.getRunCount());
      System.out.println("Completed in : "+result.getRunTime()+"ms");
    

    }
    else{
      System.out.println("No. of failed test cases =" + numOfFailures);
      for( Failure failure : result.getFailures()){
          System.out.println(failure.toString());
      }
    }
  }
}
