/**
 *
 A class containing database functions user
 @author Dimeji Faluyi
 @version 2/22/2018
 @see UserInterface
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
// import java.util.Random;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.io.*;
import java.security.SecureRandom;
import java.io.Serializable;
import java.util.Formatter;


public class UserDatabase implements Serializable{

  private static final long serialVersionUID = 1L;

   private List<User> users;
   private String users_DB_File;


   /**
   * creats userDB
     @param takes DB files
   **/

   public UserDatabase(){
     users = new ArrayList<User>();
   }

   public UserDatabase(String aUsers_DB_File) throws Exception{
     users = new ArrayList<User>();
     users_DB_File = aUsers_DB_File;
     try{
       loadFromFile();
     }
     catch(FileNotFoundException e)
     {
       System.out.print("Please check to make sure file exist and ");
       System.out.println("is in same directory as program");
       System.out.println(e);
     }
     catch(IOException e)
     {
       System.out.println("Please enter the input the file named " + users_DB_File);
       System.out.println(e);
     }

   }


    /**
    Loads data from database text file
    @throws FileNotFoundException when there is no previous database file matching the transaction_DB_File
    @throws IOException if input file name not entered properly.
    */

    private void loadFromFile() throws Exception
    {
      try{
       FileInputStream fis = new FileInputStream(users_DB_File);
       ObjectInputStream ois = new ObjectInputStream(fis);
       users = (ArrayList<User>) ois.readObject();
     }
     catch (FileNotFoundException e) {
          FileOutputStream fos = new FileOutputStream(users_DB_File, false);
      }
      catch (IOException e) {
        System.out.println("Error: Problem with file " + users_DB_File + ".");
      }
    }

    /**
    * writes the data to transaction_DB_File
    * @throws Exception (throws Exception)
    */

    public void writeToFile() throws Exception {

       try {
           FileOutputStream fos = new FileOutputStream(users_DB_File);
           ObjectOutputStream oos = new ObjectOutputStream(fos);

           oos.writeObject(users);
           oos.close();
       }
       catch (IOException e) {
           System.out.println("Error: Cannot write to " + users_DB_File + ".");
       }

    }

    /**
    *
    *   Search for User in user database given its ID
        @return user
    *
    */

    public User search(int ID){
      List<User> filterdUser = new ArrayList<User>();
      filterdUser = filter(user -> user.getID() == ID,users);
      if(filterdUser.isEmpty()){
        return null;
      }else{
        return filterdUser.get(0);
      }
    }

    /**
    *  display user
    *  @return -1 if user list empty

    **/

  public int displayUsers(){
    if(users.isEmpty()){
      System.out.println("There are no users at this time Please add some or check to make sure you are using correct user database");
      return -1;
    }
      final int firsNameLength = 10;
      final int lastNameLength = 10;
      final int addressLength = 40;
      String firstName;
      String lastName;
      String SSN;
      int ssnDigits;
      String salary;
      String phoneNumber;
      String address;
      double amount = 0;
      DecimalFormat formatter = new DecimalFormat();

      String tableFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
      String headingFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
      System.out.print("-----------------------------------------------------------");
      System.out.print("-----------------------------------------------------------");
      System.out.println("---------");
      System.out.printf(headingFormat,"ID#",  "First Name","Last Name","Phone Number","Address","SSN","Salary");
      System.out.print("-----------------------------------------------------------");
      System.out.print("-----------------------------------------------------------");
      System.out.println("---------");

      for (User user : users){
        SSN ="N/A";
        salary ="N/A";
        phoneNumber ="N/A";
        address ="N/A";
        firstName = user.getFirstName();
        lastName = user.getLastName();
        if(firstName.length() > firsNameLength){
          firstName = firstName.substring(0,firsNameLength)+"...";
        }
        if(lastName.length() > lastNameLength){
          lastName = lastName.substring(0,lastNameLength)+"...";
        }

        if(user instanceof Employee){
            ssnDigits = ((Employee)user).getSSN();
            SSN = String.valueOf(ssnDigits).replaceFirst("(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3");
            salary = String.valueOf(((Employee)user).getMonthlySalary());
            amount = Double.parseDouble(salary);
            formatter = new DecimalFormat("#,###.00");
        }
        if( user instanceof Customer){
           phoneNumber = ((Customer)user).getPhoneNumber();
           address = ((Customer)user).getAddress();
           if(address.length() > addressLength){
             address = address.substring(0,addressLength)+"...";
           }
        }

        System.out.printf(
                            tableFormat,
                            user.getID(),firstName,
                            lastName,phoneNumber,
                            address,
                            SSN,
                            formatter.format(amount)
                          );
      }
      System.out.print("-----------------------------------------------------------");
      System.out.print("-----------------------------------------------------------");
      System.out.println("---------");
      return 0;
    }

    /**
    *  display user
    *  @return -1 if user list empty
      @param type of user
    **/

    public int displayUsers(String type){
      if(users.isEmpty()){
        System.out.println("There are no users at this time Please add some or check to make sure you are using correct user database");
        return -1;
      }
        final int firsNameLength = 10;
        final int lastNameLength = 10;
        final int addressLength = 40;
        String firstName;
        String lastName;
        String SSN;
        int ssnDigits;
        String salary;
        String phoneNumber;
        String address;
        double amount = 0;
        DecimalFormat formatter = new DecimalFormat();

        String tableFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
        String headingFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
        System.out.print("-----------------------------------------------------------");
        System.out.print("-----------------------------------------------------------");
        System.out.println("---------");
        System.out.printf(headingFormat,"ID#",  "First Name","Last Name","Phone Number","Address","SSN","Salary");
        System.out.print("-----------------------------------------------------------");
        System.out.print("-----------------------------------------------------------");
        System.out.println("---------");

        for (User user : users){
          SSN ="N/A";
          salary ="N/A";
          phoneNumber ="N/A";
          address ="N/A";
          firstName = user.getFirstName();
          lastName = user.getLastName();
          if(firstName.length() > firsNameLength){
            firstName = firstName.substring(0,firsNameLength)+"...";
          }
          if(lastName.length() > lastNameLength){
            lastName = lastName.substring(0,lastNameLength)+"...";
          }

          if(user instanceof Employee){
              ssnDigits = ((Employee)user).getSSN();
              SSN = String.valueOf(ssnDigits).replaceFirst("(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3");
              salary = String.valueOf(((Employee)user).getMonthlySalary());
              amount = Double.parseDouble(salary);
              formatter = new DecimalFormat("#,###.00");
          }
          if( user instanceof Customer){
             phoneNumber = ((Customer)user).getPhoneNumber();
             address = ((Customer)user).getAddress();
             if(address.length() > addressLength){
               address = address.substring(0,addressLength)+"...";
             }
          }

          if(type.equals("employee") && user instanceof Employee){

              System.out.printf(
                                  tableFormat,
                                  user.getID(),firstName,
                                  lastName,phoneNumber,
                                  address,
                                  SSN,
                                  formatter.format(amount)
                                );
            }

        if(type.equals("customer") && user instanceof Customer){

            System.out.printf(
                                tableFormat,
                                user.getID(),firstName,
                                lastName,phoneNumber,
                                address,
                                SSN,
                                formatter.format(amount)
                              );
          }
        }
        System.out.print("-----------------------------------------------------------");
        System.out.print("-----------------------------------------------------------");
        System.out.println("---------");
        return 0;
      }

      /**
      *  display user
      *  @return -1 if user list empty
        @param id of user
      **/
  public int displayUser(int ID){
    if(users.isEmpty()){
      System.out.println("There are no users at this time Please add some or check to make sure you are using correct user database");
      return -1;
    }
    User user = search(ID);
    final int firsNameLength = 10;
    final int lastNameLength = 10;
    final int addressLength = 40;
    String firstName;
    String lastName;
    String SSN;
    int ssnDigits;
    String salary;
    String phoneNumber;
    String address;
    double amount = 0;
    DecimalFormat formatter = new DecimalFormat();


    String tableFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
    String headingFormat = "|%-10s|%-13s|%-13s|%-16s|%-40s|%-12s|%-15s|\n";
    System.out.print("-----------------------------------------------------------");
    System.out.print("-----------------------------------------------------------");
    System.out.println("---------");
    System.out.printf(headingFormat,"ID#",  "First Name","Last Name","Phone Number","Address","SSN","Salary");
    System.out.print("-----------------------------------------------------------");
    System.out.print("-----------------------------------------------------------");
    System.out.println("---------");

      SSN ="N/A";
      salary ="N/A";
      phoneNumber ="N/A";
      address ="N/A";
      firstName = user.getFirstName();
      lastName = user.getLastName();
      if(firstName.length() > firsNameLength){
        firstName = firstName.substring(0,firsNameLength)+"...";
      }
      if(lastName.length() > lastNameLength){
        lastName = lastName.substring(0,lastNameLength)+"...";
      }

      if(user instanceof Employee){
          ssnDigits = ((Employee)user).getSSN();
          SSN = String.valueOf(ssnDigits).replaceFirst("(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3");
          salary = String.valueOf(((Employee)user).getMonthlySalary());
          amount = Double.parseDouble(salary);
          formatter = new DecimalFormat("#,###.00");
      }
      if( user instanceof Customer){
         phoneNumber = ((Customer)user).getPhoneNumber();
         address = ((Customer)user).getAddress();
         if(address.length() > addressLength){
           address = address.substring(0,addressLength)+"...";
         }
      }

      System.out.printf(
                          tableFormat,
                          user.getID(),firstName,
                          lastName,phoneNumber,
                          address,
                          SSN,
                          formatter.format(amount)
                        );

    System.out.print("-----------------------------------------------------------");
    System.out.print("-----------------------------------------------------------");
    System.out.println("------");
    return 0;

  }

  public User addNewUser(String type){
    User newUser = null;
    newUser = createNewUser(type);
    users.add(newUser);
    return newUser;
  }

  public User updateUser(int ID){
    if(users.isEmpty()){
      return null;
    }
   User user = search(ID);
   if(user == null){
    System.out.println("User not found please make sure you enterd the correct ID");
    System.out.println();
     return null;
   }
   Scanner in = new Scanner(System.in);
   int choice;
   String type = "";


    do{

       System.out.println("What field would you like to update for " + user.getFirstName());
       System.out.println("Press 1 to update all fields");
       System.out.println("Press 2 to update first name");
       System.out.println("Press 3 to update last name");
       System.out.println("Press 4 to update phone number");
       System.out.println("Press 5 to update address");
       System.out.println("Press 6 to update SSN");
       System.out.println("Press 7 to update salary");
       System.out.println("Press 8 to quit");

       while(!in.hasNextInt()){
         System.out.println("Invalid input");
         System.out.println("Please enter a index number");
         in.next();
       }
       choice = in.nextInt();
       while(choice > 8 || choice < 1){
         System.out.println("Invalid input");
         System.out.print("Index does not exist please enter a valid index");
         System.out.println("Please enter a number between 1 - 6");
         while(!in.hasNextInt()){
           System.out.println("Invalid input");
           System.out.println("Please enter a number between 1 - 6");
           in.next();
         }
         choice = in.nextInt();
       }
       if(user instanceof Employee){
         type = "employee";
       }
       else if (user instanceof Customer){
         type = "customer";
       }

     switch(choice){
       case 1:
        user = createNewUser(type);
        break;
       case 2:
         user.setFirstName(createFirstName(type));
         break;
       case 3:
         user.setLastName(createLastName(type));
         break;
       case 4:
          if(type != "customer"){
            System.out.println("User is not a customer therefor phone number cannot be updated");
          }
          else{
            ((Customer)user).setPhoneNumber(createPhoneNumber());
          }
          break;
       case 5:
           if(type != "customer"){
             System.out.println("User is not a customer therefor address cannot be updated");
           }
           else{
             ((Customer)user).setAddress(createAddress());
           }
           break;
        case 6:
            if(type != "employee"){
              System.out.println("User is not a employee therefor SSN cannot be updated");
            }
            else{
              ((Employee)user).setSSN(createSSN());
            }
            break;
        case 7:
            if(type != "employee"){
              System.out.println("User is not a employee therefor salary cannot be updated");
            }
            else{
              ((Employee)user).setSalary(createSalary());
            }
            break;
        case 8:
          break;
     }
    }while(choice != 8);

    return user;
  }
  /**
  *  creates first name
  *  @return string first name
  **/

  private String createFirstName(String type){
   Scanner in = new Scanner(System.in);
   String firstName;
   System.out.println("Enter "+type+"'s First Name");
   firstName = in.nextLine();
   return firstName;
  }
  /**
  *  creates last name
  *  @return string last name
  **/

  private String createLastName(String type){
   Scanner in = new Scanner(System.in);
   String lastName;
   System.out.println("Enter "+type+"'s Last Name");
   lastName = in.nextLine();
   return lastName;
  }
  /**
  *  creates phone number
  *  @return phone number
  **/
  private String createPhoneNumber(){
   Scanner in = new Scanner(System.in);
   long phoneDigits;
   int digitLength;
   String phoneNumber;

   System.out.println("Please enter a 10 digit phone number (XXXXXXXXXX no dashes just digits)");
   while(!in.hasNextLong()){
     System.out.println("Invalid input");
     System.out.println("Please enter numbers only");
     in.next();
   }
   phoneDigits = in.nextLong();
   digitLength =  String.valueOf(phoneDigits).length();

   while(digitLength != 10){
     System.out.println("Invalid input");
     System.out.println("Please enter a 10 digit phone number");
     while(!in.hasNextLong()){
       System.out.println("Invalid input");
       System.out.println("Please enter numbers only");
       in.next();
     }
     phoneDigits = in.nextLong();
     digitLength =  String.valueOf(phoneDigits).length();
   }
   phoneNumber = String.valueOf(phoneDigits);
   long phoneNum = Long.parseLong(phoneNumber);
   phoneNumber = String.valueOf(phoneNum).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
   return phoneNumber;
  }
/**
*  creates address
*  @return address string
**/
  private String createAddress(){
   Scanner in = new Scanner(System.in);
   String address;
   System.out.println("Please enter a address");
   address = in.nextLine();
   return address;
  }
  /**
  *  creates ssn
  *  @return ssn int
  **/
  private int createSSN(){
   Scanner in = new Scanner(System.in);
   int digitLength;
   int SSN;
   System.out.println("Please enter a 9 digit social security number (XXXXXXXXX no dashes just digits)");
   while(!in.hasNextInt()){
     System.out.println("Invalid input");
     System.out.println("Please enter numbers only");
     in.next();
   }
   SSN = in.nextInt();
   digitLength =  String.valueOf(SSN).length();

   while(digitLength != 9){
     System.out.println("Invalid input");
     System.out.println("Please enter a 9 digit SSN");
     while(!in.hasNextInt()){
       System.out.println("Invalid input");
       System.out.println("Please enter numbers only");
       in.next();
     }
     SSN = in.nextInt();
     digitLength =  String.valueOf(SSN).length();
   }
   return SSN;
  }
  /**
  *  creates salary
  *  @return salary
  **/
  private float createSalary(){
   float monthlySalary;
   Scanner in = new Scanner(System.in);
   System.out.println("Please enter a monthly salary in the form (xxxx.xx)");
   while(!in.hasNextFloat()){
     System.out.println("Invalid input");
     System.out.println("Please enter numbers only");
     in.next();
   }
   monthlySalary = in.nextFloat();
   return monthlySalary;
  }
  /**
  *  creates user
  @param type a string of user type
  *  @return new user
  **/
  public User createNewUser(String type){
    User newUser = null;
    Scanner in = new Scanner(System.in);
    int userID;
    int choice;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    SecureRandom random = new SecureRandom();
    int SSN;
    float monthlySalary;
    userID = random.nextInt(100000);
    while(search(userID) !=null){
      userID = random.nextInt(100000);
    }

    firstName = createFirstName(type);
    lastName = createLastName(type);
    if(type.equals("customer")){
      phoneNumber = createPhoneNumber();
      address = createAddress();
      newUser = new Customer(
                        userID,
                        firstName,
                        lastName,
                        phoneNumber,
                        address
                      );
    }
    else if(type.equals("employee")){
      SSN = createSSN();
      monthlySalary = createSalary();
      newUser = new Employee(
                        userID,
                        firstName,
                        lastName,
                        SSN,
                        monthlySalary
                      );
    }
      return newUser;
  }


/**
* Getter
* @return users .
*
*/
protected final List<User> getUsers(){
      return users;
}


    /**
    *
    *   A simple utility function that filters a list of items
        @param takes a predicate and an list of items
        @return filtered list of items
    *
    */

    private List<User> filter(Predicate<User> criteria, List<User> list) {
          return list.stream().filter(criteria).collect(Collectors.<User>toList());
    }

}
