/**
 *    A class for creating a transactions that extends user

@author Dimeji Faluyi
@version 2/21/2018
@see UserInterface

*
*/
import java.io.Serializable;
import java.util.Date;




public class Transaction implements Serializable{



  public Transaction(
          String aItemID,
          Date aSaleDate,
          int aSaleQuantity,
          int aCustomerID,
          int aEmployeeID
        ){
        itemID = aItemID;
        saleDate = aSaleDate;
        saleQuantity = aQuantity;
        customerID = aCustomerID;
        employeeID = aEmployeeID;
  }

  // might not need setters and getters
  // /**
  //   * Setter
  //   *  @param aItemID the id for the item in the transaction
  //   */
  //
  //  public void setItemID(String aItemID){
  //      itemID = aItemID;
  //  }



  private String itemID;
  private Date saleDate;
  private int saleQuantity;
  private int customerID;
  private int employeeID;


}
