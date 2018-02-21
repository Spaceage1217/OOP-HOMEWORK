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
      String FILENAME = "database.txt";
      InventoryFunctions storage;




      Scanner in = new Scanner(System.in);
      int choice = 0;
      System.out.println("Press 1 to use database.txt or 2 to supply file name");

      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter 1 or 2");
        in.next();
      }

      choice = in.nextInt();

      while(choice > 2 || choice < 1){
        System.out.println("Invalid input");
        System.out.print("Index does not exist please enter a valid index");
        System.out.println("Please enter 1 or 2");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter 1 or 2");
          in.next();
        }
        choice = in.nextInt();
      }

      switch(choice){
        case 1:
          break;
        case 2:
          System.out.println("Please input the file name");
          FILENAME = in.next();
          break;
      }

      if(FILENAME.equals("")){
        storage = new InventoryFunctions();
      }
      else{
          storage =  new InventoryFunctions(FILENAME);
      }

      do{
        System.out.println("Welcome to the storage");
        System.out.println("Press 1 to display all items in the storage");
        System.out.println("Press 2 to Add a new quantity of a specific item to the stock.");
        System.out.println("Press 3 to Remove a certain quantity of a specific item type ");
        System.out.println("Press 4 to Search for an item (given its name or part of its name).");
        System.out.println("Press 5 to Show a list of all items below a certain quantity.");
        System.out.println("Press 6 to Exit program.");

        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter a number between 1 - 6");
          in.next();
        }

        choice = in.nextInt();
        while(choice > 6 || choice < 0){
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
            storage.displayItems();
            break;
          case 2:
            storage.changeQuantity("add");
            break;
          case 3:
            storage.changeQuantity("remove");
            break;
          case 4:
            storage.search();
            break;
          case 5:
            storage.showBelowQuantity();
            break;
          case 6:
            try {
                System.out.println("Saving Inventory...");
                storage.writeToFile(FILENAME);
              }
            catch(IOException e){
                System.out.println("ERROR Saving, Please check that");
                System.out.println("1.File has not been deleted");
                System.out.println("2.You have permission ");
                System.out.println("to write to " + FILENAME);
                System.out.println(e);
            }
            //Exit the program
            break;
          }

      }while(choice !=6);
   }

}
