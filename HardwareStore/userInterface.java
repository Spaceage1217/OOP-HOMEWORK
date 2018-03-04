/**
 *    A class for runing database functions
 @author Dimeji Faluyi
 @version 2/7/2018
 @see InventoryFunctions
 @see UserInterface
 *
 *
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

public class UserInterface
{
   public static void main(String[] args)
   {

      String inventory_DB_File = "inventoryDB.txt";
      String user_DB_File = "userDB.txt";
      String transaction_DB_File = "transactionDB.txt";
      InventoryFunctions storage = null;
      UserDatabase userDB = null;
      TransactionDB  transactionDB = null;

      try{
        storage = new InventoryFunctions(inventory_DB_File);
        userDB = new UserDatabase(user_DB_File);
        transactionDB = new TransactionDB(transaction_DB_File,storage,userDB);
      }catch(Exception e){
          System.out.println("whats wrong");
      }


      Scanner in = new Scanner(System.in);
      int choice = 0;
    //  System.out.println("Enter 1 to use database.txt or 2 to supply file name");

      // while(!in.hasNextInt()){
      //   System.out.println("Invalid input");
      //   System.out.println("Please enter 1 or 2");
      //   in.next();
      // }

      //choice = in.nextInt();

      // while(choice > 2 || choice < 1){
      //   System.out.println("Invalid input");
      //   System.out.print("Index does not exist please enter a valid index");
      //   System.out.println("Please enter 1 or 2");
      //   while(!in.hasNextInt()){
      //     System.out.println("Invalid input");
      //     System.out.println("Please enter 1 or 2");
      //     in.next();
      //   }
      //   choice = in.nextInt();
      // }

      // switch(choice){
      //   case 1:
      //     break;
      //   case 2:
      //     System.out.println("Please input the file name");
      //     inventory_DB_File = in.next();
      //     break;
      // }
      // if(inventory_DB_File.equals("")){
      //   storage = new InventoryFunctions();
      // }
      // else{
      //     storage =  new InventoryFunctions(inventory_DB_File);
      // }

      do{
        System.out.println("Welcome to the storage");
        System.out.println("Enter 1 to display all items in the storage.");
        System.out.println("Enter 2 to Add new item (or quantity) to the database.");
        System.out.println("Enter 3 to Delete an item from a database.");
        System.out.println("Enter 4 to Search for an item (given its name or part of its name).");
        System.out.println("Enter 5 to Show a list of users in the database.");
        System.out.println("Enter 6 to Add new user to the database.");
        System.out.println("Enter 7 to Update user info (given their id).");
        System.out.println("Enter 8 to Complete a sale transaction.");
        System.out.println("Enter 9 to Show completed sale transactions.");
        System.out.println("Enter 10 to Exit program.");


        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter a number between 1 - 10");
          in.next();
        }

        choice = in.nextInt();
        while(choice > 10 || choice < 1){
          System.out.println("Invalid input");
          System.out.print("Index does not exist please enter a valid index");
          System.out.println("Please enter a number between 1 - 6");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter a number between 1 - 6");
            in.next();
          }
          choice = in.nextInt();
        }
        switch (choice){
          case 1:
          if(storage.getItems().isEmpty()){
            System.out.println("There are no items at this time Please add some or check to make sure you are using correct user database");
            break;
          }
            storage.displayItems();
            break;
          case 2:
            int optionSelect;
            String itemType;
            System.out.println("Enter 1 to add a new Item ");
            System.out.println("Enter 2 to change the quantity of an exsiting item");
            while(!in.hasNextInt()){
              System.out.println("Invalid input");
              System.out.println("Please enter 1 or 2");
              in.next();
            }
            optionSelect = in.nextInt();
            while(optionSelect > 2 || optionSelect < 1){
              System.out.println("Invalid input");
              System.out.println("Enter 1 to add a new Item ");
              System.out.println("Enter 2 to change the quantity of an exsiting item");
              while(!in.hasNextInt()){
                System.out.println("Invalid input");

                System.out.println("Please enter a number ");
                System.out.println("Enter 1 to add a new Item ");
                System.out.println("Enter 2 to change the quantity of an exsiting item");
                in.next();
              }
              optionSelect = in.nextInt();
            }

            if(optionSelect == 1){
              System.out.println("What type of item do you want to add?");
              System.out.println("Enter 1 to add a small hardware item");
              System.out.println("Enter 2 to add a appliance");
              while(!in.hasNextInt()){
                System.out.println("Invalid input");
                System.out.println("Please enter 1 or 2");
                in.next();
              }
              optionSelect = in.nextInt();
              while(optionSelect > 2 || optionSelect < 1){
                System.out.println("Invalid input");
                System.out.println("Enter 1 to add a small hardware item");
                System.out.println("Enter 2 to add a appliance");
                while(!in.hasNextInt()){
                  System.out.println("Invalid input");
                  System.out.println("Enter 1 to add a small hardware item");
                  System.out.println("Enter 2 to add a appliance");
                  in.next();
                }
                optionSelect = in.nextInt();
              }
              itemType = (optionSelect == 1) ? "smallHardwareItem" : "appliance";
              storage.addItem(itemType);
            }
            else if(optionSelect == 2 ){
              storage.changeQuantity("add");
            }
            break;
          case 3:
          if(storage.getItems().isEmpty()){
            System.out.println("There are no items to delete at this time Please add some or check to make sure you are using correct user database");
            break;
          }
            storage.deletItem();
            break;
          case 4:
            storage.search();
            break;
          case 5:
            if(userDB.getUsers().isEmpty()){
              System.out.println("There are no users at this time Please add some or check to make sure you are using correct user database");
              break;
            }
            userDB.displayUsers();
            break;
          case 6:
            int userSelect;
            String userType;
            System.out.println("What type of user do you want to add?");
            System.out.println("Enter 1 to add a customer");
            System.out.println("Enter 2 to add a employee");
            while(!in.hasNextInt()){
              System.out.println("Invalid input");
              System.out.println("Please enter 1 or 2");
              in.next();
            }
            userSelect = in.nextInt();
            while(userSelect > 2 || userSelect < 1){
              System.out.println("Invalid input");
              System.out.println("Please enter 1 for customer or 2 employee");
              while(!in.hasNextInt()){
                System.out.println("Invalid input");
                System.out.println("Please enter 1 for customer or 2 employee");
                in.next();
              }
              userSelect = in.nextInt();
            }
            userType = (userSelect == 1) ? "customer" : "employee";
            userDB.addNewUser(userType);
            break;
          case 7:
            if(userDB.getUsers().isEmpty()){
              System.out.println("There are no users at this time Please add some or check to make sure you are using correct user database");
              break;
            }
            int ID;
            int IDLength;
            User user;
            System.out.println("Please enter the 5 digit ID of the user that you wish to update");
            userDB.displayUsers();
            while(!in.hasNextInt()){
              System.out.println("Invalid input");
              System.out.println("Please enter a 5 digit ID with no characters");
              in.next();
            }
            ID = in.nextInt();
            IDLength =  String.valueOf(ID).length();
            while(IDLength != 5){
              System.out.println("Invalid input");
              System.out.println("Please enter the 5 digit ID of the user that you wish to update");
              while(!in.hasNextInt()){
                System.out.println("Invalid input");
                System.out.println("Please enter the 5 digit ID of the user that you wish to update");
                in.next();
              }
              ID = in.nextInt();
              IDLength =  String.valueOf(ID).length();
            }

            if(userDB.updateUser(ID) == null){
              break;
            }
            else{
              System.out.println("User updated");
              userDB.displayUser(ID);
              break;
            }
          case 8:
            transactionDB.completeTransaction();
            break;
          case 9:
            transactionDB.displayTransactions();
            break;
          case 10:
            try {
                System.out.println("Quitting Program...");
                System.out.println("Saving Inventory...");

                storage.writeToFile();
                userDB.writeToFile();
                transactionDB.writeToFile();

              }
            catch(IOException e){
                System.out.println("ERROR Saving, Please check that");
                System.out.println("1.File has not been deleted");
                System.out.println("2.You have permission ");
                System.out.println("to write to " + inventory_DB_File);
                System.out.println(e);
            }
            catch(Exception e){
              System.out.println(e);
              choice = 10;
            }
            //Exit the program
            break;
          }

      }while(choice !=10);
   }

}
