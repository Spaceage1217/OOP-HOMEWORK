/**
 *    A class for creating a customer that extends user

@author Dimeji Faluyi
@version 2/20/2018
@see InventoryFunctions
@see UserInterface
@see User
*
*/


public class Customer extends User{

  public Customer(int aID, String aFirstName, String aLastName, String aPhoneNumber, String aAddress){
    super(aID,aFirstName,aLastName);
    address = aAddress;
    monthlySalary = aMonthlySalary;
  }


  /**
    * Setter
    *  @param aPhoneNumber the name of the phoneNumber for the customer
    */

   public void setPhoneNumber(String aPhoneNumber){
     phoneNumber = aPhoneNumber;
   }

 /**
   * Setter
   *  @param aAddress the address for the customer
   */

  public void setAddress(String aAddress){
    address = aAddress;
  }

 /**
   * Getter
   * @return customer phoneNumber .
   *
   */

  public String getPhoneNumber(){
      return phoneNumber;
  }

  /**
    * Getter
    * @return customer address .
    *
    */

   public String getAddress(){
       return address;
   }

   private String phoneNumber;
   private String address;

}
