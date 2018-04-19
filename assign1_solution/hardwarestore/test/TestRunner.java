package hardwarestore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
  public static void main( String[] args)
  {
    Result result = JUnitCore.runClasses(TestHardwareInventory.class);
    int numOfFailures = result.getFailures().size();
    if(numOfFailures==0)
    {
      System.out.println("All tests successful !!!");
    }
    else{
      System.out.println("No. of failed test cases =" + numOfFailures);
      for( Failure failure : result.getFailures()){
          System.out.println(failure.toString());
      }
    }
  }
}
