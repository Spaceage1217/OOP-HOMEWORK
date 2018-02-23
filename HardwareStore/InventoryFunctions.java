/**
 *    A class containing database functions for inventory
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
import java.util.UUID;


public class InventoryFunctions implements Serializable
{

  /**
  *
  *   constructor to set inventory.
  *
  */
  private List<Item> items;
  private String inventory_DB_File;
  public InventoryFunctions(){
    items = new ArrayList<Item>();
  }

  public InventoryFunctions(String aInventory_DB_File){
   items = new ArrayList<Item>();
   inventory_DB_File = aInventory_DB_File;
   try{
     loadFromFile(inventory_DB_File);
   }
   catch(FileNotFoundException e)
   {
     System.out.print("Please check to make sure file exist and ");
     System.out.println("is in same directory as program");
     System.out.println(e);
   }
   catch(IOException e)
   {
     System.out.println("Please enter the input the file named " + inventory_DB_File);
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
  *   Search for item in inventory given its name
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
      return -1;
    }else{
      displayItems(filterdItems);
    }

    return 0;
  }


    /**
    *
    *   Search for item in inventory given its ID
        @return item
    *
    */
    public Item search(String itemID){
      Item filterdItems = new ArrayList<Item>();
      filterdItems = filter(item -> item.getId().contains(itemID),items);
      if(filterdItems.isEmpty()){
        return null;
      }else{
        return filterdItems.get(0);
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
  *
  *   A function to add a new item to the database
  *
      @param  type a string that tells the item what type to create "smallHardwareItems" or "appliances"
      @return new item
  *
  */
  public Item addItem(String type){
    Scanner in = new Scanner(System.in);
    String itemName;
    String id ="";
    int quantity;
    float price;
    String category;
    String brand;
    String type;
    String applianceType;
    int choice;
    Item newItem;

    System.out.println("Enter the "+type+"'s Name");
    itemName = in.nextLine();
    System.out.println("Enter the quantity of the item");
    while(!in.hasNextInt()){
      System.out.println("Invalid input");
      System.out.println("Please enter numbers only");
      in.next();
    }
    quantity = in.nextInt();
    System.out.println("Enter the price of the item");
    while(!in.hasNextFloat()){
      System.out.println("Invalid input");
      System.out.println("Please enter numbers only");
      in.next();
    }
    price = in.nextFloat();

    if(type == "smallHardwareItems"){
      System.out.println("Which category does the item belong to");
      System.out.println("Enter 1 for Door&Window");
      System.out.println("Enter 2 for Cabinet&Furniture");
      System.out.println("Enter 3 for Fasteners");
      System.out.println("Enter 4 for Structural");
      System.out.println("Enter 5 for Other");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      choice = in.nextInt();
      while(choice > 5 || choice < 1){
        System.out.println("Invalid input");
        System.out.println("Please pick from one of the following catogries");
        System.out.println("Enter 1 for Door&Window");
        System.out.println("Enter 2 for Cabinet&Furniture");
        System.out.println("Enter 3 for Fasteners");
        System.out.println("Enter 4 for Structural");
        System.out.println("Enter 5 for Other");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
      }

      switch(choice){
        case 1:
          category = "Door&Window";
          break;
        case 2:
          category = "Cabinet&Furniture";
          break;
        case 3:
          category = "Fasteners";
        case 4:
          category = "Structural";
        case 5:
          category = "Other"
      }
      newItem = new SmallHardwareItem(
                    id,
                    itemName,
                    quantity,
                    price,
                    category
                );
    }

    if(type == "appliances"){
      System.out.println("Enter the brand Name");
      brand = in.nextLine();
      System.out.println("Which type does the item belong to");
      System.out.println("Enter 1 for Refrigerators");
      System.out.println("Enter 2 for Washers&Dryers");
      System.out.println("Enter 3 for Ranges&Ovens");
      System.out.println("Enter 4 for Small Appliences");
      System.out.println("Enter 5 for Other");
      while(!in.hasNextInt()){
        System.out.println("Invalid input");
        System.out.println("Please enter numbers only");
        in.next();
      }
      choice = in.nextInt();
      while(choice > 5 || choice < 1){
        System.out.println("Invalid input");
        System.out.println("Please pick from one of the following types");
        System.out.println("Enter 1 for Refrigerators");
        System.out.println("Enter 2 for Washers&Dryers");
        System.out.println("Enter 3 for Ranges&Ovens");
        System.out.println("Enter 4 for Small Appliences");
        System.out.println("Enter 5 for Other");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        choice = in.nextInt();
      }

      switch(choice){
        case 1:
          applianceType = "Refrigerators";
          break;
        case 2:
          applianceType = "Washers&Dryers";
          break;
        case 3:
          applianceType = "Ranges&Ovens";
        case 4:
          applianceType = "Small Appliences";
        case 5:
          applianceType = "Other"
      }

      newItem = new Appliances(
                    id,
                    itemName,
                    quantity,
                    price,
                    brand,
                    applianceType,
                );

    }

    itmes.add(newItem);
    return newItem;

  }

  /**
  *
  *   A function to delete a item from the database
  *
      @param  ID a id for the item in the database
      @return new item
  *
  */
  public int deletItem(int ID){
    for (Item item : subSet){
      if(item.getId == ID){
        items.remove(item);
        return 0;
      }
    }
    return -1;
  }

  /**
	Loads data from database text file
	@param inventory_DB_File is the name of the previous database file
	@throws FileNotFoundException when there is no previous database file matching the inventory_DB_File
  @throws IOException if input file name not entered properly.
	*/
	private void loadFromFile(String inventory_DB_File) throws Exception
	{
    FileInputStream fis = new FileInputStream(db_File);
    ObjectInputStream ois = new ObjectInputStream(fis);
    items = (ArrayList<Item>) ois.readObject();
	}

  /**
  Writes data to database txt file
  @param inventory_DB_File is the name of the previous database file
  @throws IOException if error writing to file
  */
  public void writeToFile(String inventory_DB_File) throws Exception
  {
    try {
        FileOutputStream fos = new FileOutputStream(users_DB_File);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(items);
        oos.close();
    }
    catch (IOException e) {
        System.out.println("Error: Cannot write to " + inventory_DB_File + ".");
    }
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


  private String tableFormat = "|%5s|%10s|%20s|%20s|%10s|%10.2f|\n";
  private String headingFormat = "|%5s|%10s|%20s|%20s|%10s|%10s|\n";
}
