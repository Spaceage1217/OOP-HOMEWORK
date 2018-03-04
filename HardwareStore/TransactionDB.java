/**
 *    A class for creating and reading a transactions databse

@author Dimeji Faluyi
@version 2/21/2018
@see UserInterface
@see transaction
@see Item
@see user
@see employee
*
*/
import java.io.Serializable;
import java.util.Date;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import java.text.ParseException;
import java.io.*;
import java.text.SimpleDateFormat;


public class TransactionDB implements Serializable{

  private static final long serialVersionUID = 1L;

  private List<Transaction> transactions;
  private InventoryFunctions itemStorage;
  private UserDatabase userDB;
  String transaction_DB_File;

  public TransactionDB(InventoryFunctions aItemStorage,UserDatabase aUserDB){
    itemStorage = aItemStorage;
    userDB = aUserDB;
    transactions = new ArrayList<Transaction>();
  }

  public TransactionDB(String aTransaction_DB_File, InventoryFunctions aItemStorage,UserDatabase aUserDB) throws Exception{
    itemStorage = aItemStorage;
    userDB = aUserDB;
    transactions = new ArrayList<Transaction>();
    transaction_DB_File = aTransaction_DB_File;
    try{
      loadFromFile();
    }
    catch(FileNotFoundException e)
    {
      System.out.print("Please check to make sure file exist and ");
      System.out.println("is in same directory as program");
      System.out.println(e);
    }
    catch(IOException e)
    {
      System.out.println("Please enter the input the file named " + transaction_DB_File);
      System.out.println(e);
    }
  }



  public int completeTransaction(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    String itemID;
    Item item;
    Date saleDate = new Date();
    User user;
    User newUser;
    int customerID;
    int employeeID;
    int quantity;
    int choice;
    Scanner in = new Scanner(System.in);

    //ID

      System.out.print("Please enter the ID of the item that is being ");
      System.out.println("purchased");
      itemStorage.displayItems();
      itemID = in.nextLine();
      item =  findID(itemID);
      if(item == null){
        System.out.println("ItemID does not exist");
        return -1;
      }

    //DATE
    saleDate = getDate();
    if(saleDate == null){
      System.out.println("aborting transaction....");
      return -1;
    }

    //quantity
    do{
      System.out.print("Please enter the quantity of the item that is being ");
      System.out.println("purchased");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      quantity = in.nextInt();
      int currentQuantityAvalabile = item.getQuantity();
      choice = 2;
      while(currentQuantityAvalabile<quantity){
        System.out.print("The current quantity of the item you want is lower than ");
        System.out.println("the current quantity of that item ");

          System.out.println("please enter a quantity less than " + currentQuantityAvalabile);
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter numbers only");
            in.next();
          }
          quantity = in.nextInt();
        }
    }while(choice != 2);

    //customerID
    do{
      System.out.print("Please enter the ID of the customer making the ");
      System.out.println("purchase");
      userDB.displayUsers("customer");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      customerID = in.nextInt();
      user = findID(customerID);
      if(user == null){
        System.out.println("customer ID does not exist");
        System.out.println("Press 1 to try again, 2 to add new customer or 3 to quit");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
        while(choice > 3 || choice < 1){
          System.out.println("Invalid input");
          System.out.println("Press 1 to try again, 2 to add new customer or 3 to quit");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter 1 to try again, 2 to add new customer or 3 to quit");
            in.next();
          }
          choice = in.nextInt();
        }
        if(choice == 3){
          return -1;
        }
        else if (choice == 2){
          newUser = userDB.addNewUser("customer");
          if(newUser == null){
            return -1;
          }
          else{
              customerID = newUser.getID();
              choice = 3;
          }
        }
      }
      else{
        choice = 3;
      }
    }while(choice != 3);
    //employeeID
    do{
      System.out.print("Please enter the ID of the employee completing the");
      System.out.println("purchase");
      userDB.displayUsers("employee");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      employeeID = in.nextInt();
      user = findID(employeeID);
      if(user == null){
        System.out.println("employee ID does not exist");
        System.out.println("Press 1 to try again or 2 to add a new employee or 3 to quit");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
        while(choice > 3 || choice < 1){
          System.out.println("Invalid input");
          System.out.println("Press 1 to try again or 2 to add a new employee or 3 to quit");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Press 1 to try again or 2 to add a new employee or 3 to quit");
            in.next();
          }
          choice = in.nextInt();
        }
        if(choice == 3){
          return -1;
        }
        else if (choice == 2){
          newUser = userDB.addNewUser("employee");
          if(newUser == null){
            return -1;
          }
          else{
              employeeID = newUser.getID();
              choice = 3;
          }
        }
      }
      else{
        choice = 3;
      }
    }while(choice != 3);
    System.out.println("adding transaction to the database...");
    Transaction newTransaction = new Transaction(itemID,saleDate,quantity,customerID,employeeID);
    transactions.add(newTransaction);
    quantity = item.getQuantity()-quantity;
    item.setQuantity(quantity);
    return 0;
  }

  /**
  @return item if id is found otherwise returns null
  @param itemID id of item you wish to find
  */
  private Item findID(String itemID){
    return itemStorage.search(itemID);
  }


  private User findID(int userID){
    return userDB.search(userID);
  }

  /**
  @return date that the user sets
  */
  private Date getDate(){
    Scanner in = new Scanner(System.in);
    Date date = new Date();
    String format = "MM-dd-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    boolean hasFailed = false;
    System.out.println("Please enter todays date");
    System.out.println("with the format (MM-dd-yyyy)");
    while (true){
        String str = in.nextLine();
        if(str.equals("1") && hasFai){
          date = null;
          return date ;
        }
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            System.out.println("Invalid date please enter a date with the format (MM-dd-yyyy)");
            System.out.println("Or press 1 to quit");
            hasFailed = true;
            continue;
        }
        break;
    }
    return date;
  }





  /**
  Loads data from database text file
  @throws FileNotFoundException when there is no previous database file matching the transaction_DB_File
  */
  private void loadFromFile() throws Exception
  {
     try{
      FileInputStream fis = new FileInputStream(transaction_DB_File);
      ObjectInputStream ois = new ObjectInputStream(fis);
      transactions = (ArrayList<Transaction>) ois.readObject();
     }
     catch (FileNotFoundException e) {
          FileOutputStream fos = new FileOutputStream(transaction_DB_File, false);
      }
      catch (IOException e) {
        System.out.println("Error: Problem with file " + transaction_DB_File + ".");
      }
  }

  /**
   * writes the data to transaction_DB_File
   * @throws Exception (throws Exception)
   */
  public void writeToFile() throws Exception {
      try {
          FileOutputStream fos = new FileOutputStream(transaction_DB_File);
          ObjectOutputStream oos = new ObjectOutputStream(fos);

          oos.writeObject(transactions);
          oos.close();
      }
      catch (IOException e) {
          System.out.println("Error: Cannot write to " + transaction_DB_File + ".");
      }

  }

  /**
   * Displays list of all completed transactions <br><br>
   *
   */
  public int displayTransactions() {
    if(transactions.isEmpty()){
      System.out.println("There are no transactions at this time Please add some or check to make sure you are using correct user database");
      return -1;
    }

      SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
      String format = "|%-15s|%-16s|%-15s|%-15s|%-15s|";
      System.out.print("-----------------------------------------------------------");
      System.out.println("-----------------------");
      System.out.printf(
                    format,
                    "Item ID","Sale Date",
                    "Quantity",
                    "Customer ID ",
                    "Employee ID"
                  );
      System.out.println();
      System.out.print("-----------------------------------------------------------");
      System.out.println("-----------------------");
      for (Transaction transaction : transactions) {

          System.out.printf(
                      format,
                      transaction.getItemId(),
                      sdf.format(transaction.getSaleDate()),
                      transaction.getSaleQuantity(),
                      transaction.getEmployeeID(),
                      transaction.getCustomerID()
                  );
          System.out.println();
          System.out.print("-----------------------------------------------------------");
          System.out.println("-----------------------");
      }
      System.out.println();
      System.out.print("-----------------------------------------------------------");
      System.out.println("-----------------------");
      return 0;
  }





}
