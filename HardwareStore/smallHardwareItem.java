/**
 *    A class for creating smallHardwareItem that extends item

@author Dimeji Faluyi
@version 2/20/2018
@see InventoryFunctions
@see UserInterface
@see Item
*
*/



public class SmallHardwareItem extends Item
{
  /**
   *   Constructs a Item object
       @param aID the the item id.
   *   @param aItemName the item name
       @param aQuantity the item quantity
       @param aPrice the item price
       @param aCategory the item category for a(n) SmallHardwareItem
   *
   */

   public SmallHardwareItem(
         String aID,
         String aItemName,
         int aQuantity,
         float aPrice,
         String aCategory
   ){
     super(aID,aItemName,aQuantity,aPrice);
     category = aCategory;
   }


   /**
     * Getter
     * @return item category.
     *
     */

    public String getCategory(){
        return category;
    }

    /**
      * Setter
      *  @param aCategory the name of the category for the item
      */

     public void setCategory(String aCategory){
          category = aCategory;
     }

      private String category;

}
