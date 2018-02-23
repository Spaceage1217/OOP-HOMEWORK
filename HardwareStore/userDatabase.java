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
//to do 5 and 7
// show a list of users in the DB
// update user info given id



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
import java.security.SecureRandom;


public class UserDatabase implements Serializable{
   private List<User> users;
   private String users_DB_File;
   public UserDatabase(){
     users = new Array<User>();
   }
   public UserDatabase(String aUsers_DB_File){
     users = new Array<User>();
     users_DB_File = aUsers_DB_File;
     try{
       loadFromFile(users_DB_File);
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
    @param db_File is the name of the previous database file
    @throws FileNotFoundException when there is no previous database file matching the transaction_DB_File
    @throws IOException if input file name not entered properly.
    */

    private void loadFromFile(String db_File) throws Exception
    {
     FileInputStream fis = new FileInputStream(db_File);
     ObjectInputStream ois = new ObjectInputStream(fis);
     users = (ArrayList<User>) ois.readObject();
    }

    /**
    * writes the data to transaction_DB_File
    * @throws Exception (throws Exception)
    */

    public void writeToFile(String users_DB_File) throws Exception {

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
      User filterdUser = new ArrayList<User>();
      filterdUser = filter(user -> user.getID() == ID,users);
      if(filterdUser.isEmpty()){
        return null;
      }else{
        return filterdUser.get(0);
      }
    }

    public User addNewUser(String type){
      User newUser = null;
      Scanner in = new Scanner(System.in);
      int userID;
      int choice;
      String firstName;
      String lastName;
      Integer phoneDigits;
      int digitLength;
      String phoneNumber;
      String address;
      SecureRandom random = new SecureRandom();
      int SSN;
      float monthlySalary;
      userID = random.nextInt(100000);
      while(search(userID) !=null){
        userID = random.nextInt(100000);
      }


      System.out.println("Enter "+type+"'s First Name");
      firstName = in.nextLine();
      System.out.println("Enter "+type+"'s Last Name");
      lastName = in.nextLine();

      if(type == "customer"){
        System.out.println("Please enter a 10 digit phone number (XXXXXXXXXX no dashes just digits)");
        while(!in.hasNextInt()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        phoneDigits = in.nextInt();
        digitLength =  String.valueOf(phoneDigits).length();

        while(digitLength != 10){
          System.out.println("Invalid input");
          System.out.println("Please enter a 10 digit phone number");
          while(!in.hasNextInt()){
            System.out.println("Invalid input");
            System.out.println("Please enter numbers only");
            in.next();
          }
          phoneDigits = in.nextInt();
          digitLength =  String.valueOf(phoneDigits).length();
        }
        phoneNumber = phoneDigits.toString();
        long phoneNum = Long.parseLong(phoneNumber);
        phoneNumber = String.valueOf(phoneNum).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
        System.out.println("Please enter a address");
        address = in.nextLine();

        newUser = new Customer(
                          userID,
                          firstName,
                          lastName,
                          phoneNumber,
                          address
                        );
      }

      if(type == "employee"){
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
        System.out.println("Please enter a monthly salary in the form (xxxx.xx)");
        while(!in.hasNextFloat()){
          System.out.println("Invalid input");
          System.out.println("Please enter numbers only");
          in.next();
        }
        monthlySalary = in.nextFloat();


        newUser = new Employee(
                          userID,
                          firstName,
                          lastName,
                          SSN,
                          monthlySalary
                        );
      }

      users.add(newUser);
      return newUser;
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


//
// do{
// System.out.println("Please enter 5 digit ID of the New Customer");
// while(!in.hasNextInt()){
//   System.out.println("Invalid input");
//   System.out.println("Please enter numbers only");
//   in.next();
// }
// customerID = in.nextInt();
// int digitLength =  String.valueOf(customerID).length();
// if(digitLength < 5 || digitLength > 5){
//   System.out.println("Press 1 to try again, 2 to quit");
//   while(!in.hasNextInt()){
//     System.out.println("Invalid input");
//     System.out.println("Please enter numbers only");
//     in.next();
//   }
//   choice = in.nextInt();
//   while(choice > 2 || choice < 1){
//     System.out.println("Invalid input");
//     System.out.println("Press 1 to try again, 2 to quit");
//     while(!in.hasNextInt()){
//       System.out.println("Invalid input");
//       System.out.println("Please enter 1 to try again, 2 quit");
//       in.next();
//     }
//     choice = in.nextInt();
//   }
//   if(choice == 2){
//     return null;
//   }
// }
// while(digitLength < 5 || digitLength > 5){
//   System.out.println("Invalid input");
//   System.out.println("Please enter a 5 digit customer ID");
//   while(!in.hasNextInt()){
//     System.out.println("Invalid input");
//     System.out.println("Please enter numbers only");
//     in.next();
//   }
//   customerID = in.nextInt();
// }
// }while ( choice != 2);
