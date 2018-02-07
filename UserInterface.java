import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class UserInterface
{
   public static void main(String[] args)
   {
      String FILENAME = "input.txt";
      InventoryFunctions storage;
      if(FILENAME.equals("")){
        storage = new InventoryFunctions();
      }
      else{
        storage =  new InventoryFunctions(FILENAME);
      }

      Scanner in = new Scanner(System.in);
      int choice = 0;

      // do{
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
          choice = in.nextInt();
        }

          switch (choice){
            case 1:
              storage.displayItems();
              break;
            case 2:
              storage.changeQuantity("add");
              // try {
              //     System.out.println("Saving Inventory...");
              //     storage.writeToFile(FILENAME);
              //   }
              // catch(IOException e){
              //     System.out.println("ERROR Saving, Please check that");
              //     System.out.println("1.File has not been deleted");
              //     System.out.println("2.You have permission ");
              //     System.out.println("to write to " + FILENAME);
              //     System.out.println(e);
              // }
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
      // }while(choice !=6);



      in.close();


   }
}
