/**
 *    A class containing database functions
 @author Dimeji Faluyi
 @version 2/7/2018
 @see UserInterface
 @see Item
 @see user
 @see employee
 *
 *
 */

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

public class InventoryFunctions
{

  /**
  *
  *   constructor to set inventory.
  *
  */
  private List<Item> items;
  public InventoryFunctions(){
    items = new ArrayList<Item>();
  }

  public InventoryFunctions(String FILENAME){
   items = new ArrayList<Item>();
   try{
     loadFromFile(FILENAME);
   }
   catch(FileNotFoundException e)
   {
     System.out.print("Please check to make sure file exist and ");
     System.out.println("is in same directory as program");
     System.out.println(e);
   }
  }

  /**
  *
  *   Show all existing items in stock and their quantities.
      @param items
  *
  */
  public  void displayItems(){
    int index = 0;
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
    System.out.printf(headingFormat,"#","ID#",  "Name","Category","Quantity","Price");
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");

		for (Item item : items){
			System.out.printf(
                          tableFormat,
                          index+1,
                          item.getId(), item.getName(),
                          item.getCategory(), item.getQuantity(),
                          item.getPrice()
                        );
      index++;
		}
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
  }

  /**
  *
  *   Shows all exsisting items from the list passed
      @param items
  *
  */

  private  void displayItems(List<Item> subSet){
    tableFormat = "|%10s|%20s|%20s|%10s|%10.2f|\n";
    headingFormat = "|%10s|%20s|%20s|%10s|%10s|\n";
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
    System.out.printf(headingFormat,"ID#",  "Name","Category","Quantity","Price");
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");

		for (Item item : subSet){
			System.out.printf(
                          tableFormat,
                          item.getId(), item.getName(),
                          item.getCategory(), item.getQuantity(),
                          item.getPrice()
                        );
		}
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
  }

  /**
  *
  *   Display Item by index
      @param @param takes an index and a list of items
  *
  */

  private  void displayItem(int index, List<Item> items){
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
    System.out.printf(headingFormat,"#","ID#",  "Name","Category","Quantity","Price");
    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");

      System.out.printf(
                          tableFormat,
                          index+1,
                          items.get(index).getId(), items.get(index).getName(),
                          items.get(index).getCategory(), items.get(index).getQuantity(),
                          items.get(index).getPrice()
                        );

    System.out.print("---------------------------------------------------");
    System.out.println("--------------------------------------");
  }

  /**
  *
  *   Add a new quantity of a specific item to the stock
      @param type which is a string that determines weather you add or remove
  *
  */

  public  void changeQuantity(String type){

    displayItems();
    Scanner in = new Scanner(System.in);
    System.out.println("Please enter a index number you wish to change");
    int choice;
    while(!in.hasNextInt()){
      System.out.println("Invalid input");
      System.out.println("Please enter a index number");
      in.next();
    }
    choice = in.nextInt();
    while(choice > items.size() || choice < 0){
      System.out.println("Index does not exist please enter a valid index");
      choice = in.nextInt();
    }
    int index = choice - 1;
    if(type.equals("add")){
      addOrRemoveQuantity(index,"add");
    }
    else{
      addOrRemoveQuantity(index,"remove");
    }

  }

  /**
  *
  *   Search for item in inventory
      @return index of item
  *
  */
  public int search(){
    Scanner in = new Scanner(System.in);
    String query;
    System.out.println("Type in item name that you wish to search for");
    query = in.nextLine();
    List<Item> filterdItems = new ArrayList<Item>();
    filterdItems = filter(item -> item.getName().contains(query),items);
    if(filterdItems.isEmpty()){
      System.out.println("Item not found");
    }else{
      displayItems(filterdItems);
    }

    return 0;
  }

  /**
  *
  *   A simple helper function for changeQuantity that adds the new quantity to
  *   old one.
      @param takes an index and a list of items
      @return filtered list of items
  *
  */

  private void addOrRemoveQuantity(int index , String type){
    Scanner in = new Scanner(System.in);
    int choice;
    int sign;
    if(type.equals("add")){
      System.out.println("How much do you wish to add to the quantity");
      sign = 1;
    }else{
      System.out.println("How much do you wish to remove from the quantity");
      sign = -1;
    }
    while(!in.hasNextInt()){
      System.out.println("Please enter a number");
      in.next();
    }
    choice = in.nextInt();
    int amount = items.get(index).getQuantity() + (choice*sign);
    amount = (amount > 0 )? amount :  0;
    items.get(index).setQuantity(amount);
    System.out.println("The updated item");
    displayItem(index,items);
  }


  /**
  *
  *   Shows a all items below a certain quantity.
  *
  */

  public void showBelowQuantity(){
    Scanner in = new Scanner(System.in);
    int quantity;
    System.out.print("At what quantity do you want to show all items below ");
    System.out.println("that amount?");
    quantity = in.nextInt();
    List<Item> filterdItems = new ArrayList<Item>();
    filterdItems = filter(item -> item.getQuantity() < quantity,items);
    displayItems(filterdItems);
  }




  /**
	Loads data from database text file
	@param FILENAME is the name of the previous database file
	@throws FileNotFoundException when there is no previous database file matching the FILENAME
	*/
	private void loadFromFile(String FILENAME) throws FileNotFoundException
	{
		try{
			Item item;
			FileReader fr = new FileReader(FILENAME);
			Scanner inFile = new Scanner(fr);
			String line;
			String[] fields = new String[5];

			while(inFile.hasNextLine()){
        line = inFile.nextLine();
				fields = line.split("~");
        item = new Item(
                        fields[0],
                        fields[1],
                        fields[2],
                        Integer.valueOf(fields[3]),
                        Float.valueOf(fields[4])
                        );
				items.add(item);
			}

      inFile.close();
		}
		catch(FileNotFoundException fe){
			System.out.println(fe);

		}
	}

  /**
  Writes data to database txt file
  @param FILENAME is the name of the previous database file
  @throws IOException if error writing to file
  */
  public void writeToFile(String FILENAME) throws IOException
  {
      FileWriter fw;
  		if(FILENAME.equals("")){ //if no previous database on file, automatically creates one named "packages.txt"
  			fw = new FileWriter("input.txt", false);
  		}
  		else {
  			fw = new FileWriter(FILENAME, false); //overwrites data presently in file, if any
  		}

  		PrintWriter outFile = new PrintWriter(fw);

  		for (Item item : items){
  			outFile.printf(
                 outputFormat,
                 item.getId(),
                 item.getName(),
                 item.getCategory(),
                 item.getQuantity(),
                 item.getPrice()
  						   );
  		}
  		outFile.close();
  }





  /**
  *
  *   A simple utility function that filters a list of items
      @param takes a predicate and an list of items
      @return filtered list of items
  *
  */

  private List<Item> filter(Predicate<Item> criteria, List<Item> list) {
        return list.stream().filter(criteria).collect(Collectors.<Item>toList());
  }


  private String outputFormat = "%s~%s~%s~%s~%.2f\n";
  private String tableFormat = "|%5s|%10s|%20s|%20s|%10s|%10.2f|\n";
  private String headingFormat = "|%5s|%10s|%20s|%20s|%10s|%10s|\n";
}
