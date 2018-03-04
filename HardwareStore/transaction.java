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
        saleQuantity = aSaleQuantity;
        customerID = aCustomerID;
        employeeID = aEmployeeID;
  }

  /**
   * Getter
   * @return item id.
   *
  */
  protected final String getItemId(){
      return itemID;
  }

  /**
   * Getter
   * @return saleDate.
   *
  */
  protected final Date getSaleDate(){
      return saleDate;
  }

  /**
   * Getter
   * @return saleQuantity.
   *
  */
  protected final int getSaleQuantity(){
      return saleQuantity;
  }

  /**
   * Getter
   * @return customer id.
   *
  */
  protected final int getCustomerID(){
      return customerID;
  }

  /**
   * Getter
   * @return employee id.
   *
  */
  protected final int getEmployeeID(){
      return employeeID;
  }


  private String itemID;
  private Date saleDate;
  private int saleQuantity;
  private int customerID;
  private int employeeID;


}
