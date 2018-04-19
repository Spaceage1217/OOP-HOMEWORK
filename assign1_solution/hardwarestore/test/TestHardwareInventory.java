package hardwarestore;



import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;





/*
        1. Show all existing items in stock and their quantities.
        2. Add a new quantity of a specific item to the stock.
        3. Remove a certain quantity of a specific item type.
        4. Search for an item (given its name or part of its name).
        5. Show a list of all items below a certain quantity.
        6. Exit program.
*/
public class TestHardwareInventory
{

  /**
    This method will test to see if the itemquantiy is changed
    when you call the function addQuantity
  */
  //private final HardwareStore hardwareStore;
  HardwareStore testStore;
  public TestHardwareInventory() throws IOException {
    testStore  = new HardwareStore();
  }

  @Test
  public void checkItemQuantity(){
    int quantity = 5;


    testStore.addNewItem("dvf5","myItem","Door&Window",22,22.00f);
    int itemIndex = testStore.findItem("dvf5");
    testStore.addQuantity(itemIndex, quantity);
    Item testItem = testStore.getItem(itemIndex);
    assertTrue("Quantity is not equal to 5 for testStore item",testItem.getQuantity() == quantity);
  }

}
