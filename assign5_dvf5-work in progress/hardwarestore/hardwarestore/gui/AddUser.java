package hardwarestore.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hardwarestore.HardwareStore;
import hardwarestore.items.Item;

public class AddUser  extends JFrame implements ItemListener, ActionListener {
	
	private static final String[] userTypeOptions = {"Employee", "Customer"};
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	
	
	
	
	ArrayList<Item> items;
		
	private JComboBox cmbUserTypes;
	
	
	private JTextField userFirstNameInput;
	private JTextField userLastNameInput;
	
	
	private JTextField userSSNInput;
	private JTextField userSalaryInput;
	
	private JTextField userPhoneNumberInput;
	private JTextField userAddressInput;

	
	private JPanel submitPanel;
	private JPanel additionalInputs;
	
	private JButton submit;
    private JButton reset;
	
    private String userFirstName;
    private String userLastName;
    private String userSSN;
    private String userSalary;
    private String userPhoneNumber;
    private String userAddress;
    private String userType;
    
	private HardwareStore store;
	
	


	AddUser(){
		setTitle("Add New User");    
		setLayout( new FormLayout());
		
		try {
			store = new HardwareStore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		//field for first name
	  	add(new JLabel("First Name"));
	  	userFirstNameInput = new JTextField(15);
	  	userFirstNameInput.addActionListener(this);
	    add(userFirstNameInput);
	        
		//field for last name
	  	add(new JLabel("Last Name"));
	  	userLastNameInput = new JTextField(15);
	  	userLastNameInput.addActionListener(this);
        add(userLastNameInput);
           
	    //field for User type
	  	add(new JLabel("Select Type of User"));
        cmbUserTypes = new JComboBox(userTypeOptions);
        cmbUserTypes.setSelectedIndex(-1);
        cmbUserTypes.addItemListener(this);
        add(cmbUserTypes);
        
        // submit panel
        add(new JLabel(""));
        submitPanel = new JPanel();
        add(submitPanel);
        
        //aditional inputs based on item type
        add(new JLabel("")); 
		additionalInputs = new JPanel();
		add(additionalInputs);
		
        
        // buttons
        submit = new JButton("Add User");
        submit.addActionListener(this);
        submitPanel.add(submit);

        reset = new JButton("Reset");
        reset.addActionListener(this);
        submitPanel.add(reset);

   
        
        setSize(950, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);

	}
	
	
	
	/**
	 * Resets the form inputs when called
	 */
	
	void resetForm() {
		userFirstNameInput.setText("");
		userLastNameInput.setText("");
		cmbUserTypes.setSelectedIndex(-1);
		cmbUserTypes.setEnabled(true);
		additionalInputs.removeAll();
		repaint();
        setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * checks to see if submit or reset buttons are clicked.
	 * Verifies inputs on form and submits it if submit is clicked.
	 * resets form if reset is clicked.
	 * @param e
	 * @param actulalSalary 
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if(e.getSource() == submit) {
			int actualSSN = 0;
			float actualSalary = 0;
			int actualPhoneNumber = 0;
			boolean canSubmit = true;
			
			
			try {
				userFirstName = userFirstNameInput.getText();
				userLastName = userLastNameInput.getText();
				userType = (String) cmbUserTypes.getItemAt(cmbUserTypes.getSelectedIndex());
			}
			catch(NullPointerException exp) {
				canSubmit = false;				
			}
			
			
		if(userType.equals("Employee")) {
			try {
				userSSN = userSSNInput.getText();
				actualSSN = Integer.parseInt(userSSN);
				userSalary = userSalaryInput.getText();
				actualSalary = Float.parseFloat(userSalary);
				
				   if (actualSSN <= 100000000 || actualSSN > 999999999) {
					   JFrame message = new JFrame();
						message.setAlwaysOnTop(true);
					   JOptionPane.showMessageDialog(message,"Invalid social security number. "
	                           + "SSN is a 9-digit integer.");                 
	               }
				   if (actualSalary < 0) {
					   	JFrame message = new JFrame();
						message.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(message,"Invalid salary."
		                         + "It must be (at least) 0."); 
		            }
					
				
			}
			catch(NullPointerException exp) {
				JFrame message = new JFrame();
				message.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(message,"Invalid SSN/Salary fields. "
                        +  "Please check SSN/Salary fields and make sure they are in the proper format");
			}
	
		}
		else if (userType.equals("Customer")) {
				try {
					userPhoneNumber = userPhoneNumberInput.getText();
					userAddress = userAddressInput.getText();
				}
				catch(NullPointerException exp) {
					JFrame message = new JFrame();
					message.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(message,"Invalid Phone Number/Address fields. "
	                        +  "Please check Phone Number/Address fields and make sure they not empty");
				}
			}
		
			

			if(userType == null) {
				canSubmit = false;
				JFrame message = new JFrame();
				message.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(message,"Invalid User Type. "
                        +  "User Type cannot be left blank");
			}
			

			if(userFirstName.length() < 1 || userFirstName == null){
				canSubmit = false;
				JFrame message = new JFrame();
				message.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(message,"Invalid First Name. "
                        +  "First Name cannot be left blank");
			}
			
			if(userLastName.length() < 1 || userLastName == null){
				canSubmit = false;
				JFrame message = new JFrame();
				message.setAlwaysOnTop(true);
				JOptionPane.showMessageDialog(message,"Invalid Last Name. "
                        +  "Last Name cannot be left blank");
			}
			

			

			
			if(canSubmit) {
				if(userType.equals("Employee")) {
					LOGGER.info("adding Employee " +userFirstName + " " + userLastName + " " + actualSSN + " " + actualSalary);
                    store.addEmployee(userFirstName,userLastName, actualSSN, actualSalary);
				}
				else if (userType.equals("Customer")) {
					LOGGER.info("adding Customer " +userFirstName + " " + userLastName + " " + userPhoneNumber + " " + userAddress);
                    store.addCustomer(userFirstName, userLastName, userPhoneNumber, userAddress);
				}
				try {
					store.writeDatabase();
					resetForm();
					LOGGER.info("Changes to the database saved");
				} catch (IOException e1) {
					LOGGER.severe("Error when writing to databse.");
					e1.printStackTrace();
				}
               
			}
	}
		
	
	if(e.getSource() == reset) {
		resetForm();
	}
}

	@Override
	public void itemStateChanged(ItemEvent e) {
		int choice = cmbUserTypes.getSelectedIndex();
		if(choice != -1) {
			
			switch(choice) {
			case 0:
				additionalInputs.add(new JLabel("Employee Salary"));
				userSalaryInput = new JTextField(15);
				additionalInputs.add(userSalaryInput);
				
				additionalInputs.add(new JLabel("Employee SSN"));
				userSSNInput = new JTextField(15);
				additionalInputs.add(userSSNInput);
		        break;
			case 1:
				additionalInputs.add(new JLabel("Customer Phone Number"));
				userPhoneNumberInput = new JTextField(15);
				additionalInputs.add(userPhoneNumberInput);
				
				additionalInputs.add(new JLabel("Customer Address"));
				userAddressInput = new JTextField(15);
				additionalInputs.add(userAddressInput);
				
			}

			        
	        cmbUserTypes.setEnabled(false);

	        additionalInputs.add(submit);
	        additionalInputs.add(reset);
            repaint();
            setVisible(true);
		}
		
	}

}
