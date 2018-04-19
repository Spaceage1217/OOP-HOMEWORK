/*
 * Hardware Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */
package hardwarestore;

import hardwarestore.items.Item;

import hardwarestore.users.User;

import java.io.IOException;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * This is the main class of the Hardware Store database manager. It provides a
 * console for a user to use the 5 main commands.
 *
 * @author Junye Wen
 */
public class MainApp extends JFrame {
	
    // This object will allow us to interact with the methods of the class HardwareStore
    private final HardwareStore hardwareStore;
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in); // Used to read from System's standard input

    /**
     * Default constructor. Initializes a new object of type HardwareStore
     *
     * @throws IOException
     */
    public MainApp() throws IOException {
        hardwareStore = new HardwareStore();
    }

    //Function 1
    /**
     * This method shows all items in the inventory.
     */
    public void showAllItems() {
        // We need to sort the item list first
        HardwareStore.sortItemList();
        System.out.print(hardwareStore.getAllItemsFormatted());
    }

    //Function 2
    /**
     * This method will add items quantity with given number. If the item does
     * not exist, it will call another method to add it.
     *
     */
    public void addItemQuantity() {
        String idNumber = "";
        while (true) {
            System.out.println("Please input the ID of item (String, 5 alphanumeric characters).");
            System.out.println("If the item does not exist, it will be added as a new entry.");
            idNumber = CONSOLE_INPUT.nextLine();
            if (!idNumber.matches("[A-Za-z0-9]{5}")) {
                System.out.println("Invalid ID Number: not proper format. "
                        + "ID Number must be 5 alphanumeric characters.");
                continue;
            }
            break;
        }


        int itemIndex = hardwareStore.findItemIndex(idNumber);
        if (itemIndex != -1) { // If item exists in the database
            System.out.println("Item found in database.");
            int quantity = 0;
            while (true) {
                System.out.println("Current quantity: " + hardwareStore.findItem(idNumber).getQuantity());
                System.out.println("Please enter quantity you want to add.");
                quantity = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (quantity <= 0) {
                    System.out.println("Invalid quantity. "
                            + "The addition amount must be larger than 0.");
                    continue;
                }
                break;
            }


            hardwareStore.addQuantity(itemIndex, quantity);
        } else {
            // If it reaches here, the item does not exist. We need to add new one.
            System.out.println("Item with given number does not exist.");

            // Enter name
            System.out.println("Please type the name of item.");
            String name = CONSOLE_INPUT.nextLine();

            // Enter quantity
            int quantity = 0;
            while(true) {
                System.out.println("Please type the quantity of the item (integer).");
                try {
                    quantity = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (quantity < 0) {
                        System.out.println("Invalid price. "
                                + "The quantity cannot be smaller than 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;
            }


            // Enter price
            float price = 0;
            while (true) {
                System.out.println("Please type the price of the item (float).");
                try {
                    price = CONSOLE_INPUT.nextFloat();
                    CONSOLE_INPUT.nextLine();
                    if (price < 0) {
                        System.out.println("Invalid price. "
                                + "The price cannot be smaller than 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input a float.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;

            }

            // Select item type
            while (true) {
                System.out.println("Please select the type of item.");
                System.out.println("1: Small Hardware Items\n2: Appliances");
                int selection = 0;
                try {
                    selection = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    switch (selection) {
                        case 1:
                            // Adding small hardware items
                            // Select category
                            String category = null;
                            System.out.println("Please select the category of item.");
                            System.out.println("1: Door&Window\n2: Cabinet&Furniture\n3: Fasteners\n4: Structural\n5: Other");
                            try {
                                selection = CONSOLE_INPUT.nextInt();
                                CONSOLE_INPUT.nextLine();
                                switch (selection) {
                                    case 1:
                                        category = "Door&Window";
                                        break;
                                    case 2:
                                        category = "Cabinet&Furniture";
                                        break;
                                    case 3:
                                        category = "Fasteners";
                                        break;
                                    case 4:
                                        category = "Structural";
                                        break;
                                    case 5:
                                        category = "Other";
                                        break;
                                    default:
                                        System.out.println("Invalid input.");
                                        continue;
                                }
                            } catch (Exception e) {
                                System.out.println("Illegal input: Must input an integer.");
                                CONSOLE_INPUT.nextLine();
                                continue;
                            }
                            hardwareStore.addNewSmallHardwareItem(idNumber, name, quantity, price, category);
                            return;

                        case 2:
                            // Adding appliances
                            // Input brand
                            System.out.println("Please input the brand of appliance. (String)");
                            String brand = CONSOLE_INPUT.nextLine();
                            // Select type
                            String type = null;
                            System.out.println("Please select the type of appliance.");
                            System.out.println("1: Refrigerators\n2: Washers&Dryers\n3: Ranges&Ovens\n4: Small Appliance");
                            try {
                                selection = CONSOLE_INPUT.nextInt();
                                CONSOLE_INPUT.nextLine();
                                switch (selection) {
                                    case 1:
                                        type = "Door&Window";
                                        break;
                                    case 2:
                                        type = "Washers&Dryers";
                                        break;
                                    case 3:
                                        type = "Ranges&Ovens";
                                        break;
                                    case 4:
                                        type = "Small Appliance";
                                        break;
                                    default:
                                        System.out.println("Invalid input.");
                                        continue;
                                }
                            } catch (Exception e) {
                                System.out.println("Illegal input: Must input an integer.");
                                CONSOLE_INPUT.nextLine();
                                continue;
                            }

                            hardwareStore.addNewAppliance(idNumber, name, quantity, price, brand, type);
                            return;
                        default:
                            System.out.println("Invalid input.");
                            continue;
                    }

                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
            }

        }

    }

    //Function 3
    /**
     * This method will remove the item with given ID.
     * If the item does not exist, it will show an appropriate message.
     */
    public void removeItem() {
        System.out.println("\033[31m" + "WARNING: This function will remove the whole item entry. Please use with caution!");
        System.out.println("\033[31m" + "Will return to main menu if you make any mistake inputting information!");
        System.out.println("\033[0m" + "Please input the ID of item.");
        String idNumber = CONSOLE_INPUT.nextLine();
        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be at least 5 alphanumeric characters.");
            System.out.println("Will return to main menu.");
            return;
        }

        int itemIndex = hardwareStore.findItemIndex(idNumber);
        if (itemIndex == -1) {
            System.out.println("Item does not exist.");
            System.out.println("Will return to main menu.");
            return;
        } else {
            System.out.println("\033[31m" + "Item found. Are you sure you want to remove the whole entry?");
            System.out.println("\033[31m" + "(Data cannot be recovered!)");
            System.out.println("\033[31m" + "Please type YES (all capitalized) to confirm deletion.");
            String input = CONSOLE_INPUT.nextLine();
            if (input.equals("YES")) {
                System.out.println("\033[0m" + "User typed " + input + ". Confirm: Item will be removed.");
                hardwareStore.removeItem(itemIndex);
                System.out.println("\033[0m" + "Item removed from inventory.");
            } else {
                System.out.println("\033[0m" + "User typed " + input + ". Abort: Item will not be removed.");
            }

        }
    }

    //Function 4
    /**
     * This method can search item by a given name (part of name.
     * Case-insensitive.) Will display all items with the given name.
     */
    public void searchItemByName() {

        System.out.println("Please input the name of item.");
        String name = CONSOLE_INPUT.nextLine();

        String output = hardwareStore.getMatchingItemsByName(name);
        if (output == null) {
            System.out.println("Item not found with: " + name + ".");
        } else {
            System.out.println(output);
        }
    }

    //Function 5
    /**
     * This method shows all users in the system.
     */
    public void showAllUsers() {
        System.out.print(hardwareStore.getAllUsersFormatted());
    }
    //Function 6
    /**
     * This method will add a user (customer or employee) to the system.
     *
     */
    public void addUser() {
        int selection = 0;

        String firstName = "";
        String lastName = "";
        // First select if you want to add customer or employee
        while (true) {
            System.out.println("Please select the type of user.");
            System.out.println("1: Employee\n2: Customer");
            try {
                selection = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();

                switch (selection) {
                    case 1:
                        // Add Employee
                        System.out.println("Please input the first name (String):");
                        firstName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the last name (String):");
                        lastName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the SSN (9-digit integer, no other characters):");
                        int socialSecurityNumber = 0;
                        try {
                            socialSecurityNumber = CONSOLE_INPUT.nextInt();
                            CONSOLE_INPUT.nextLine();
                            if (socialSecurityNumber <= 100000000 || socialSecurityNumber > 999999999) {
                                System.out.println("Invalid social security number. "
                                        + "SSN is a 9-digit integer.");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Illegal input: Must input an integer.");
                            CONSOLE_INPUT.nextLine();
                            continue;
                        }

                        System.out.println("Please input the monthly salary (float):");
                        float monthlySalary = 0;
                        try {
                            monthlySalary = CONSOLE_INPUT.nextFloat();
                            CONSOLE_INPUT.nextLine();
                            if (monthlySalary < 0) {
                                System.out.println("Invalid salary."
                                        + "It must be (at least) 0.");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Illegal input: Must input a float.");
                            CONSOLE_INPUT.nextLine();
                            continue;
                        }

                        hardwareStore.addEmployee(firstName,lastName, socialSecurityNumber, monthlySalary);
                        return;

                    case 2:
                        // Add Customer
                        System.out.println("Please input the first name (String):");
                        firstName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the last name (String):");
                        lastName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the phone number (String):");
                        String phoneNumber = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the address (String):");
                        String address = CONSOLE_INPUT.nextLine();
                        hardwareStore.addCustomer(firstName, lastName, phoneNumber, address);
                        return;
                    default:
                        System.out.println("Invalid input.");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }


    }

    //Function 7
    /**
     * This method will edit a user (customer or employee).
     *
     */
    public void editUser() {
        int idInput = 0;
        while (true) {
            System.out.println("Please input the ID of user.");
            try {
                idInput = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
            break;
        }


        User editUser = hardwareStore.findUser(idInput);
        if (editUser == null) {
            System.out.println("User not found.");
            System.out.println("Will return to main menu.");
            return;
        }
        String text = " -------------------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-9s| %-12s| %-12s| %-45s|%n", "User Type", "User ID", "First Name", "Last Name", "Special") +
                " -------------------------------------------------------------------------------------------------\n";
        text += editUser.getFormattedText();
        text += " -------------------------------------------------------------------------------------------------\n";

        System.out.println("Current user information:");
        System.out.println(text);
        String firstName = "";
        String lastName = "";
        if (editUser.isEmployee) {
            //User is employee
            int socialSecurityNumber = 0;
            float monthlySalary = 0;
            while (true) {
                System.out.println("Please input the first name (String):");
                firstName = CONSOLE_INPUT.nextLine();
                System.out.println("Please input the last name (String):");
                lastName = CONSOLE_INPUT.nextLine();
                System.out.println("Please input the SSN (9-digit integer, no other characters):");

                try {
                    socialSecurityNumber = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (socialSecurityNumber <= 100000000 || socialSecurityNumber > 999999999) {
                        System.out.println("Invalid social security number. "
                                + "SSN is a 9-digit integer.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }

                System.out.println("Please input the monthly salary (float):");
                try {
                    monthlySalary = CONSOLE_INPUT.nextFloat();
                    CONSOLE_INPUT.nextLine();
                    if (monthlySalary < 0) {
                        System.out.println("Invalid salary."
                                + "It must be (at least) 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input a float.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;
            }

            hardwareStore.editEmployeeInformation(idInput, firstName,lastName, socialSecurityNumber, monthlySalary);
            return;

        } else {
            //User is customer
            System.out.println("Please input the first name (String):");
            firstName = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the last name (String):");
            lastName = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the phone number (String):");
            String phoneNumber = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the address (String):");
            String address = CONSOLE_INPUT.nextLine();
            hardwareStore.editCustomerInformation(idInput, firstName, lastName, phoneNumber, address);
            return;
        }
    }

    //Function 8
    /**
     * This method will lead user to complete a transaction.
     */
    public void finishTransaction(){
        String itemID = "";
        int itemIndex = 0;
        int saleQuantity = 0;
        //Get the item ID. Will not break unless got a valid input.
        while (true) {
            System.out.println("Please input the item ID:");
            itemID=CONSOLE_INPUT.nextLine();
            itemIndex = hardwareStore.findItemIndex(itemID);
            if (itemIndex == -1) {
                System.out.println("Item not found. Will return to main menu.");
                return;
            } else {
                Item tempItem = hardwareStore.findItem(itemID);
                System.out.println("Please input the amount of items sold in this transaction (int)");
                System.out.println("Maximum number: " + tempItem.getQuantity());
                try {
                    saleQuantity = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (saleQuantity <= 0) {
                        System.out.println("Invalid input: must be greater than 0.");
                        continue;
                    } else if (saleQuantity > tempItem.getQuantity()) {
                        System.out.println("Invalid input: Number too big. Transaction cannot progress.");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("Amount of items sold input invalid: not an integer");
                    continue;
                }
            }

        }

        //Get employee ID. Will check the input: must be a user, and employee.
        int employeeID = 0;
        while (true) {
            System.out.println("Please input the id of the employee.");
            try {
                employeeID = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (hardwareStore.findUserIndex(employeeID) == -1) {
                    System.out.println("User not found.");
                    continue;
                } else if (!hardwareStore.findUser(employeeID).isEmployee) {
                    System.out.println("This user is not an employee.");
                }
                break;

            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }

        //Get customer ID. Will check the input: must be a user, and customer.
        int customerID = 0;
        while (true) {
            System.out.println("Please input the id of the customer.");
            try {
                customerID = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (hardwareStore.findUserIndex(customerID) == -1) {
                    System.out.println("User not found.");
                    continue;
                } else if (hardwareStore.findUser(customerID).isEmployee) {
                    System.out.println("This user is not a customer.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }

        hardwareStore.progressTransaction(itemID, saleQuantity, customerID, employeeID, itemIndex);
        System.out.println("Transaction complete.");

    }

    //Function 9
    public void showAllTransactions(){
        System.out.print(hardwareStore.getAllTransactionsFormatted());
    }

    //Function 10
    /**
     * These method is called to save the database before exit the system.
     * @throws IOException
     */
    public void saveDatabase() throws IOException {
        hardwareStore.writeDatabase();
    }

    /**
     * This method will begin the user interface console. Main uses a loop to
     * continue executing commands until the user types '6'.
     *
     * @param args this program expects no command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MainApp app = new MainApp();

        String welcomeMessage = "\nWelcome to the Hardware Store database. Choose one of the following functions:\n\n"
                + "\t 1. Show all existing items records in the database (sorted by ID number).\n"
                + "\t 2. Add new item (or quantity) to the database.\n"
                + "\t 3. Delete an item from a database.\n"
                + "\t 4. Search for an item given its name.\n"
                + "\t 5. Show a list of users in the database.\n"
                + "\t 6. Add new user to the database.\n"
                + "\t 7. Update user info (given their id).\n"
                + "\t 8. Complete a sale transaction.\n"
                + "\t 9. Show completed sale transactions.\n"
                + "\t10. Exit program.\n";


        System.out.println(welcomeMessage);
        String selection = CONSOLE_INPUT.nextLine();

        while (!selection.equals("10")) {

            switch (selection) {
                case "1":
                    // 1. Show all existing items records in the database (sorted by ID number).
                    app.showAllItems();
                    break;
                case "2":
                    // 2. Add new item (or quantity) to the database.
                    app.addItemQuantity();
                    break;
                case "3":
                    // 3. Delete an item from a database.
                    app.removeItem();
                    break;
                case "4":
                    // 4. Search for an item given its name.
                    app.searchItemByName();
                    break;
                case "5":
                    // 5. Show a list of users in the database.
                    app.showAllUsers();
                    break;
                case "6":
                    // 6. Add new user to the database.
                    app.addUser();
                    break;
                case "7":
                    // 7. Update user info (given their id).
                    app.editUser();
                    break;
                case "8":
                    // 8. Complete a sale transaction.
                    app.finishTransaction();
                    break;
                case "9":
                    // 9. Show completed sale transactions.
                    app.showAllTransactions();
                    break;
                case "h":
                    System.out.println(welcomeMessage);
                    break;
                default:
                    System.out.println("That is not a recognized command. Please enter another command or 'h' to list the commands.");
                    break;

            }

            System.out.println("Please enter another command or 'h' to list the commands.\n");
            selection = CONSOLE_INPUT.nextLine();
        }

        CONSOLE_INPUT.close();
        
        
        System.out.print("Saving database...");
        app.saveDatabase();

        System.out.println("Have a nice day!");

    }
}
