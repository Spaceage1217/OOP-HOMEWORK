/**
 *    A class for creating item
 *    */
 import java.util.UUID;


public class Item
{
	  /**
	   *   Constructs a Item object that saves the name of a item.
	   *   @param aItem the name of the person.
	   *
     */
	   public Item(String aID, String aItemName, String aCategory, int aQuantity, float aPrice){
             if(aID.isEmpty()){
               id = aItemName+"-"+UUID.randomUUID().toString().substring(0,5);
             }else{
               id = aID;
             }
		         itemName = aItemName;
             category = aCategory;
             quantity = aQuantity;
             price = aPrice;
			    }

	      /**
	       *       Setter sets name
	       *             @return assigns aItemName to field itemName.
	       *                */
	      public void setName(String aItemName){
		             itemName = aItemName;
			        }
        /**
         *       Setter sets quantity
         *             @return assigns aQuantity to field quantity.
         *                */
        public void setQuantity(int aQuantity){
                  quantity = aQuantity;
               }

	      /**
		     *       Getter
		     *             @return class name.
		     *                */
	      public String getName(){
			      return itemName;
			          }
        /**
         *       Getter
         *             @return item quantity.
         *                */
        public int getQuantity(){
            return quantity;
                }
  			/**
  			 *       Getter
  			 *             @return item id.
  			 *                */
  			public String getId(){
  					return id;
  							}
        /**
					 *       Getter
					 *             @return item price.
					 *                */
					public float getPrice(){
							return price;
									}
        /**
					 *       Getter
					 *             @return item category.
					 *                */
					public String getCategory(){
							return category;
									}
		     private String itemName;
         private String category;
				 private String id;
         private int quantity;
         private float price;


}
