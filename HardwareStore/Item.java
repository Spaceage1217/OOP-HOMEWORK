/**
 *    A class for creating item

@author Dimeji Faluyi
@version 2/7/2018
@see InventoryFunctions
@see UserInterface
*
*/
 import java.util.UUID;


public class Item
{
	  /**
	   *   Constructs a Item object
         @param aID the the item id.
     *   @param aItemName the item name
         @param aQuantity the item quantity
         @param aPrice the item price
	   *
     */
	   public Item(String aID, String aItemName, int aQuantity, float aPrice){
             if(aID.isEmpty()){
               id = aItemName+"-"+UUID.randomUUID().toString().substring(0,5);
             }else{
               id = aID;
             }
		         itemName = aItemName;
             quantity = aQuantity;
             price = aPrice;
			    }

	      /**
	       *       Setter sets name
	       *             @param  aItemName the name of an item
	       *                */
	      protected final void setName(String aItemName){
		             itemName = aItemName;
			        }
        /**
         *       Setter sets quantity
         *             @param  aQuantity the quantity amount
         *                */
        protected final void setQuantity(int aQuantity){
                  quantity = aQuantity;
               }

	      /**
		     *       Getter
		     *             @return class name.
		     *                */
	      protected final String getName(){
			      return itemName;
			          }
        /**
         *       Getter
         *             @return item quantity.
         *                */
        protected final int getQuantity(){
            return quantity;
                }
  			/**
  			 *       Getter
  			 *             @return item id.
  			 *                */
  			protected final String getId(){
  					return id;
  							}
        /**
					 *       Getter
					 *             @return item price.
					 *                */
					protected final float getPrice(){
							return price;
									}

		     private String itemName;
				 private String id;
         private int quantity;
         private float price;


}
