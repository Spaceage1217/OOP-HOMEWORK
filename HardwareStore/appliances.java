/**
 *    A class for creating appliances that extends item

@author Dimeji Faluyi
@version 2/20/2018
@see InventoryFunctions
@see UserInterface
@see Item
*
*/



public class Appliances extends Item
{

  /**
   *   Constructs a Item object
       @param aID the the item id.
   *   @param aItemName the item name
       @param aQuantity the item quantity
       @param aPrice the item price
       @param aBrand the item brand for a(n) appliances
       @param atyoe the item type for a(n) appliances
   *
   */
   public Appliances(
         String aID,
         String aItemName,
         int aQuantity,
         float aPrice,
         String aBrand,
         String aType
   ){
     super(aID,aItemName,aQuantity,aPrice);
     brand = aBrand;
     type = aType;
   }



   /**
     * Setter
     *  @param aBrand the name of the brand for the item
     */

    public void setBrand(String aBrand){
         brand = aBrand;
    }

  /**
    * Setter
    *  @param aType the name of the type for the item
    */

   public void setType(String aType){
        type = aType;
   }

   /**
     * Getter
     * @return item brand.
     *
     */

    public String getBrand(){
        return brand;
    }


   /**
     * Getter
     * @return item type.
     *
     */

    public String getType(){
        return type;
    }



      private String brand;
      private String type;

}
