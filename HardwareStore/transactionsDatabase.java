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


public class TransactionDB implements Serializable{

  private List<Transaction> transactions;
  public TransactionDB(){
    transactions = new ArrayList<Transactions>();
  }

  public TransactionDB(String FILENAME){
    transactions = new ArrayList<Transactions>();
    try{
      loadFromFile(FILENAME);
    }
    catch(FileNotFoundException e)
    {
      System.out.print("Please check to make sure file exist and ");
      System.out.println("is in same directory as program");
      System.out.println(e);
    }
    catch(IOException e)
    {
      System.out.println("Please the inputfile named " +FILENAME);
      System.out.println(e);
    }
  }



  public int completeTransaction(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    int itemID;
    Date saleDate = new Date();
    int quantity;
    int choice;
    Scanner in = new Scanner(System.in);
    do{
      System.out.println("Please enter the ID of the item that is being");
      System.out.println("purchased");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      itemID = in.nextInt();
      if(findID(itemID) == -1){
        System.out.println("ItemID does not exist");
        System.out.println("Press 1 to try again or 2 quit");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
        while(choice > 2 || choice < 1){
          System.out.println("Invalid input");
          System.out.println("Press 1 to try again or 2 quit");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter 1 or 2");
            in.next();
          }
          choice = in.nextInt();
        }
        if(choice == 2){
          return -1;
        }
      }else{
        choice = 2;
      }
    }while(choice != 2);
///dates
    do{
      boolean goodDate = false;
      String format = "MM-dd-yyyy"
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      System.out.println("Please enter the Date of when the item was");
      System.out.println("purchased with the format (MM-dd-yyyy)");
      while (true){
          String str = in.nextLine();
          if(str == "1"){
            return -1;
          }
          try {
              saleDate = sdf.parse(str);
          } catch (ParseException e) {
              System.out.println("Invalid date please enter a date with the format (MM-dd-yyyy)");
              System.out.println("Or press 1 to quit");
              continue;
          }
          break;
      }
      if(findSaleDate(saleDate) == -1){
        System.out.println("Press 1 to try again or 2 quit");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
        while(choice > 2 || choice < 1){
          System.out.println("Invalid input");
          System.out.println("Press 1 to try again or 2 quit");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter 1 or 2");
            in.next();
          }
          choice = in.nextInt();
        }
        if(choice == 2){
          return -1;
        }
      }
      else{
        choice = 2;
      }
    }while(choice != 2);

    //quantity

    do{
      System.out.println("Please enter the quantity of the item that is being");
      System.out.println("purchased");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      quantity = in.nextInt();
      private int currentQuantityAvalabile = getQuantity(itemID);
      while(currentQuantityAvalabile<quantity){
        System.out.print("The current quantity of the item you want is lower than ");
        System.out.println("the current quantity of that item ");
        System.out.println("Press 1 to try again or 2 quit");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
        while(choice > 2 || choice < 1){
          System.out.println("Invalid input");
          System.out.println("Press 1 to try again or 2 quit");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Press 1 to try again or 2 quit");
            in.next();
          }
          choice = in.nextInt();
        }
        if( choice == 1 ){
          System.out.println("please enter a quantity less than" + currentQuantityAvalabile);
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter numbers only");
            in.next();
          }
          quantity = in.nextInt();
        }
        else if(choice == 2){
          return -1;
        }
      }
      else{
        choice = 2;
      }
    }while(choice != 2);


    
  }



  /**
  Loads data from database text file
  @param FILENAME is the name of the previous database file
  @throws FileNotFoundException when there is no previous database file matching the FILENAME
  */
  private void loadFromFile(String FILENAME) throws Exception
  {
    FileInputStream fis = new FileInputStream(FILENAME);
    ObjectInputStream ois = new ObjectInputStream(fis);
    transactions = (ArrayList<Transactions>) ois.readObject();
  }


}
