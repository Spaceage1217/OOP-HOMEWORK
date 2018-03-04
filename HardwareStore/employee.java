/**
 *    A class for creating a employee that extends user

@author Dimeji Faluyi
@version 2/20/2018
@see InventoryFunctions
@see UserInterface
@see User
*
*/


public class Employee extends User{

  public Employee(int aID, String aFirstName, String aLastName, int aSSN, float aMonthlySalary){
    super(aID,aFirstName,aLastName);
    SSN = aSSN;
    monthlySalary = aMonthlySalary;
  }


  /**
    * Setter
    *  @param aSSN the name of the SSN for the Emoloyee
    */

   public void setSSN(int aSSN){
     SSN = aSSN;
   }

 /**
   * Setter
   *  @param aMonthlySalary the monthly salary for the Emoloyee
   */

  public void setSalary(float aMonthlySalary){
    monthlySalary = aMonthlySalary;
  }

 /**
   * Getter
   * @return employee SSN .
   *
   */

  public int getSSN(){
      return SSN;
  }

  /**
    * Getter
    * @return employee monthlySalary .
    *
    */

   public float getMonthlySalary(){
       return monthlySalary;
   }

   private int SSN;
   private float monthlySalary;

}
