package hardwarestore.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import hardwarestore.HardwareStore;
import hardwarestore.users.Customer;
import hardwarestore.users.Employee;
import hardwarestore.users.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;



public class DisplayUsers extends JFrame implements ActionListener{
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private JButton editButton;
	private JPanel editPanel;
	
	HardwareStore store;
    JTable itemDisplay;
    String[] columnNames = {"User Type", "User ID", "First Name", "Last Name", "Special"};
    ArrayList<User> users;
    
    /**
     * The DisplayUsers constructor. creats a Jtable that will help display the items.
     * Based on mode the display table will be editable
     */
  
    
    DisplayUsers(String mode) {
    		
    		    setLayout(new FormLayout());
    		    
    		if(mode.equals("edit")) {
    			LOGGER.info("User is now edit user mode");
    			editPanel = new JPanel();
    			editPanel.setBorder(
    	                BorderFactory.createCompoundBorder(
    	                        BorderFactory.createCompoundBorder(
    	                                BorderFactory.createTitledBorder("Edit a specifc user"),
    	                                BorderFactory.createEmptyBorder(5,5,5,5)), null));
    		  	add(new JLabel("Select Row of user in which you wish to change "));
    			editButton = new JButton ("EDIT User");
    			editButton.setToolTipText("Select Row then Click to change User info");
    			editButton.addActionListener(this);
    			editPanel.add(editButton);
    			add(editPanel);
    		}
    		
    	    setSize(1075, 500);
    	    setTitle("Item Inventory");
    	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	    try {
				store = new HardwareStore(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	 
    	    users = (ArrayList<User>)store.getAllUsers();
    	  	add(new JLabel(""));
    	    itemDisplay = new JTable();
    	    itemDisplay.setModel(new DefaultTableModel(new Object[][] {}, columnNames) {
    	    	   @Override
    	    	    public boolean isCellEditable(int row, int column) {
    	    	       //all cells false
    	    	       return false;
    	    	    }
    	    });  	
    	    addRowsToTable();
    	    
    	    add(new JScrollPane(itemDisplay));
    	    setVisible(true);
    	}
    	
    	/**
    	 * adds an Item into the table row.
    	 */
    	public void addRowsToTable() {
    		
    	    LOGGER.info("Creating user table");

    	    DefaultTableModel tableModel = (DefaultTableModel) itemDisplay.getModel();
    	    Object columns[] = new Object[4];
    	    
    	        for (User user: users) {
    	            if(user instanceof Customer) {
    	            	columns[0] = "Customer";
    	            	columns[5] = "Phone#:"+ ((Customer)user).getPhoneNumber() + " Addr:"+((Customer)user).getAddress();
    	            }
    	            else {
    	            	columns[0] = "Employee";
    	            	columns[5] = "SSN#:"+ ((Employee)user).getSocialSecurityNumber() + " Salary:"+((Employee)user).getMonthlySalary();
    	            }
    	            columns[1] = user.getId();
    	            columns[2] = user.getFirstName();
    	            columns[3] = user.getLastName();


    	            tableModel.addRow(columns);
    	        }
    	}
    	

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == editButton) {
			}
			
		}





}



