package hardwarestore;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.io.PrintWriter;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;



/*
 * 
 * 		Functions to test
        1. Show all existing items in stock and their quantities.
        2. Add a new quantity of a specific item to the stock.
        3. Remove a certain quantity of a specific item type.
        4. Search for an item (given its name or part of its name).
        5. Show a list of all items below a certain quantity.
        6. Exit program.
*/
public class TestHardwareInventory
{
	
  HardwareStore testStore;
  private static final String DATA_FILE_NAME = "database.txt";
  String expectedOutput;
  String returnedOutput;
  String separator = System.getProperty("line.separator");

  @Before
  public void setStore() throws IOException{
	testStore  = new HardwareStore();
  }
  @After
  public void resetStore() throws IOException{
	  PrintWriter pw = new PrintWriter("database.txt");
	  pw.close();
	  testStore = null;
  }
  
  
  /**
  This method will test to see if the new quantity has been added
  when you call the function addQuantity
   */

  
  @Test
  public void checkAddToItemQuantityTest(){
	
	final int startQuantity = 22;
    final int addQuantity = 5;
    final int newQuantity = startQuantity + addQuantity;
    testStore.addNewItem("dvf5","myItem","Door&Window",startQuantity,22.00f);
    final int itemIndex = testStore.findItem("dvf5");
    testStore.addQuantity(itemIndex, addQuantity);
    Item testItem = testStore.getItem(itemIndex);
    assertTrue("Quantity is not equal to "+newQuantity+ "for testStore item",testItem.getQuantity() == newQuantity);
  }
  
  /**
  This method will test to see if the new quantity has been removed
  when you call the function removeQuantity
   */
  @Test
  public void checkRemoveItemQuantityTest(){
	  
		final int startQuantity = 22;
	    final int subtractQuantity = 5;
	    final int newQuantity = startQuantity - subtractQuantity;

	    testStore.addNewItem("dvf5","myItem","Door&Window",startQuantity,22.00f);
	    final int itemIndex = testStore.findItem("dvf5");
	    testStore.removeQuantity(itemIndex, subtractQuantity);
	    Item testItem = testStore.getItem(itemIndex);
	    //System.out.println(testItem.getQuantity());
	    assertTrue("Quantity is not equal to "+newQuantity+ "for testStore item",testItem.getQuantity() == newQuantity);
	  }
  
  /**
  This method will test to see if you can search the database using the complete name of the item
   */
  @Test
  public void searchForItemInDatabaseUsingCompleteNameTest() {
	    testStore.addNewItem("dvf5","myItem","Door&Window",22,22.00f);
	    final String itemName = "myItem";
	    final String searchResults = testStore.getMatchingItemsByName(itemName);
	    assertTrue("search for item in database is not working properly",searchResults.contains(itemName));
  }
  
  /**
  This method will test to see if you can search the database using the partial name of the item
   */
  @Test
  public void searchForItemInDatabaseUsingPartialNameTest() {
	    testStore.addNewItem("dvf5","myItem","Door&Window",22,22.00f);
	    final String partialItemName = "yIte";
	    final String searchResults = testStore.getMatchingItemsByName(partialItemName);
	    assertTrue("search for item in database is not working properly",searchResults.contains(partialItemName));
  }
  
  /**
  This method will test to see if you can search the database for all items below a certain quantity
   */
  @Test
  public void showAListOfAllItemsBelowCertainQuantityTest() {
	  final int threshHold = 23;
	  final String itemName1 = "myItem";
	  final String itemName2 = "anotherItem";

	  testStore.addNewItem("dvf5",itemName1,"Door&Window",22,22.00f);
	  testStore.addNewItem("abc1","yourItem","Door&Window",32,22.00f);
	  testStore.addNewItem("cba2",itemName2,"Door&Window",10,22.00f);
	  final String searchResults = testStore.getMatchingItemsByQuantity(threshHold);
	  assertTrue("search for item below certain quantity is not working properly",searchResults.contains(itemName1)&&searchResults.contains(itemName2));
	  
  }
  
  /**
  This method will test to see if you can display all items currently in the DB
   */
  @Test
  public void showAListOfAllExistingItemsAndQuantitesTest() {
	  final String itemName1 = "myItem";
	  final String itemName2 = "anotherItem";
	  final int quantity1 = 22;
	  final int quantity2 = 10;

	  testStore.addNewItem("dvf5",itemName1,"Door&Window",quantity1,22.00f);
	  testStore.addNewItem("cba2",itemName2,"Door&Window",quantity2,22.00f);
	  
	  final String searchResults = testStore.getAllItemsFormatted();
	  assertTrue(
				  "search for item below certain quantity is not working properly",
				  searchResults.contains(itemName1)&&searchResults.contains(quantity1+"")&&searchResults.contains(itemName2)&&searchResults.contains(quantity2+"")
				  
			  );
	  
  }
  
  /**
  This method will test to see if you can save all items to the database
   */
  @Test
  public void saveToDatabaseTest() throws IOException {
      testStore.addNewItem("dvf5","myItem","Door&Window",22,22.00f);
      testStore.writeDatabase();
      File dataFile = new File(DATA_FILE_NAME);
      Scanner itemScanner = new Scanner(new FileReader(dataFile));
      String fromDB = itemScanner.nextLine();
      String expectedDB = "dvf5~myItem~Door&Window~22~22.00";
      assertTrue("databse not written to properly, got "+ fromDB + " expected " + expectedDB,fromDB.equals(expectedDB));
      itemScanner.close();
  }
  
 
  

}
