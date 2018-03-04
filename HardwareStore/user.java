/**
 *    A class for creating user

@author Dimeji Faluyi
@version 2/20/2018
@see InventoryFunctions
@see UserInterface
*
*/
import java.io.Serializable;


public class User implements Serializable {


  private static final long serialVersionUID = 1L;

  /**
   *   Constructs a Item object
       @param aID the the user ID.
   *   @param aFirstName the user's first name
   *   @param aLastName the user's last name
   *
   */
   public User(int aID, String aFirstName, String aLastName){
        ID = aID;
        firstName = aFirstName;
        lastName = aLastName;
   }


   /**
    *  Setter sets user first name
    *  @param aFirstName the user's first name
    *
    */
    protected final void setFirstName(String aFirstName){
        firstName = aFirstName;
    }

    /**
     *  Setter sets user last name
        @param aLastName the user's last name
     *
     */
     protected final void setLastName( String aLastName){
          lastName = aLastName;
     }

    /**
     *  Getter
     *  @return User first name.
     *
     */
    protected final String getFirstName(){
        return firstName;
    }
    /**
     *  Getter
     *  @return User last name.
     *
     */
    protected final String getLastName(){
        return lastName;
    }

    /**
     *  Getter
     *  @return User last name.
     *
     */
    protected final int getID(){
        return ID;
    }


    private int ID;
    private String firstName;
    private String lastName;


}
