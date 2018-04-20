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

public class AddOrChangeItem  extends JFrame implements ItemListener, ActionListener {
	
	private static final String[] itemTypeOptions = {"Appliances", "SmallHardWareItems"};
	private static final String[] smallHardWareOptions = {"Door&Window","Cabinet&Furniture","Fasteners","Structural","Other"};
	private static final String[] applianceOptions = {"Refrigerators","Washers&Dryers","Ranges&Ovens","Small Appliance"};
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	
	
	
	
	ArrayList<Item> items;
		
	private JComboBox cmbItemTypes;
	private JComboBox cmbItemCategories;
	
	private JTextField itemNameInput;
	private JTextField itemQuantityInput;
	private JTextField itemPriceInput;
	private JTextField itemBrandNameInput;
	
	
	private JPanel submitPanel;
	private JPanel additionalInputs;
	private JPanel editPanel;
	
	private JButton submit;
    private JButton reset;
    private JButton editItemQuantities;
	
    private String itemName;
    private String itemQuantity;
    private String itemPrice;
    private String itemBrandName;
    private String itemType;
    private String itemCatogery;
    
	private HardwareStore store;
	
	


	AddOrChangeItem(){
		setTitle("Add or Change Item Quantity");
		
		editPanel = new JPanel();
		editPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Edit a specifc item quantity"),
                                BorderFactory.createEmptyBorder(5,5,5,5)), null));
	  	add(new JLabel(""));
	    editItemQuantities = new JButton("Edit Item Quantity");
        editItemQuantities.addActionListener(this);
        editPanel.add(editItemQuantities);
        add(editPanel);
        
        
		setLayout( new FormLayout());
		try {
			store = new HardwareStore();
			items = (ArrayList<Item>)store.getItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//field for name
	  	add(new JLabel("Name"));
        itemNameInput = new JTextField(15);
        itemNameInput.addActionListener(this);
	    add(itemNameInput);
	        
	    //field for quantity
	  	add(new JLabel("Quantity"));
        itemQuantityInput = new JTextField(15);
        itemQuantityInput.addActionListener(this);
        add(itemQuantityInput);
        
        //field for price
	  	add(new JLabel("Price"));
        itemPriceInput = new JTextField(15);
        itemPriceInput.addActionListener(this);
        add(itemPriceInput);
        
	    //field for item type
	  	add(new JLabel("Select Type"));
        cmbItemTypes = new JComboBox(itemTypeOptions);
        cmbItemTypes.setSelectedIndex(-1);
        cmbItemTypes.addItemListener(this);
        add(cmbItemTypes);
        
        // submit panel
        add(new JLabel(""));
        submitPanel = new JPanel();
        add(submitPanel);
        
        //aditional inputs based on item type
        add(new JLabel("")); 
		additionalInputs = new JPanel();
		add(additionalInputs);
		
        
        // buttons
        submit = new JButton("Add Item");
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
	 * Creates a random ID string based off item name
	 * @parms item name
	 * @return random ID string
	 */
	String randomID(String itemName) {
        return (itemName).substring(0,2)+ UUID.randomUUID().toString().substring(0,3);
	}
	
	
	/**
	 * Resets the form inputs when called
	 */
	
	void resetForm() {
		itemNameInput.setText("");
		itemPriceInput.setText("");
		itemQuantityInput.setText("");
		try {
			itemBrandNameInput.setText("");
		}catch(NullPointerException e) {
			//has not been created yet
			itemBrandNameInput = null;
		}
		cmbItemTypes.setSelectedIndex(-1);
		cmbItemTypes.setEnabled(true);
		try {
		cmbItemCategories.setSelectedIndex(-1);
		}catch(NullPointerException e){
			//has not been created yet
			cmbItemCategories = null;
		}
		additionalInputs.removeAll();
		repaint();
        setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * checks to see if submit or reset buttons are clicked.
	 * Verifies inputs on form and submits it if submit is clicked.
	 * resets form if reset is clicked.
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if(e.getSource() == submit) {
			float actualPrice = 0;
			int actualQuantity = 0;
			boolean canSubmit = true;
			
			try {
				itemName = itemNameInput.getText();
				itemPrice = itemPriceInput.getText();
				itemQuantity = itemQuantityInput.getText();
				itemType = (String) cmbItemTypes.getItemAt(cmbItemTypes.getSelectedIndex());
				itemCatogery  = (String) cmbItemCategories.getItemAt(cmbItemCategories.getSelectedIndex());

			}
			catch(NullPointerException exp) {
				canSubmit = false;
				JOptionPane.showMessageDialog(new JFrame(),"Invalid Form. "
                        +  "Form contains multiple empty fields");
			}
			
			try {
				itemBrandName = itemBrandNameInput.getText();
				if(itemBrandName.length() < 1)
				{
					canSubmit = false;
					JOptionPane.showMessageDialog(new JFrame(),"Invalid Brand Name. "
	                        +  "Brand Name cannot be left blank");
				}
			}
			catch(NullPointerException exp) {
				itemBrandName = null;
			}
			
			if(itemCatogery == null) {
				canSubmit = false;
				JOptionPane.showMessageDialog(new JFrame(),"Invalid Category Name. "
                        +  "Category/Type cannot be left blank");
			}
			

			if(itemName.length() < 1){
				canSubmit = false;
				JOptionPane.showMessageDialog(new JFrame(),"Invalid Name. "
                        +  "Name cannot be left blank");
			}
			
			try {
					
				actualPrice = Float.parseFloat(itemPrice.trim());
				if (actualPrice < 0 )
					{
						canSubmit = false;
						JOptionPane.showMessageDialog(new JFrame(),"Invalid price. "
		                        + "Please check that price is not less than 0.");
					}
				
			}
			catch(NumberFormatException exp) {
					canSubmit = false;
					JOptionPane.showMessageDialog(new JFrame(),"Invalid price. "
	                        +  "Please enter numerical values only");
			}
			
			try {
				
				actualQuantity = Integer.parseInt(itemQuantity.trim());
				if (actualQuantity < 0 ) {
					canSubmit = false;
					JOptionPane.showMessageDialog(new JFrame(),"Invalid Quantity. "
	                        + "Please check that quantity is not less than 0.");
					}
				
			} 
			catch(NumberFormatException exp) {
					canSubmit = false;
					JOptionPane.showMessageDialog(new JFrame(),"Invalid Quantity. "
	                        +  "Please enter numerical values only");
			}
			
			if(canSubmit) {
				String itemID = randomID(itemName);
				if(itemBrandName != null) {
					LOGGER.info("submitting appliance " +itemName + " " + actualQuantity + " " + actualPrice + " " + itemBrandName + " " + itemCatogery);
					store.addNewAppliance(itemID, itemName, actualQuantity, actualPrice, itemBrandName, itemType);
				}
				else {
					LOGGER.info("submitting hardware " +itemName + " " + actualQuantity + " " + actualPrice + " " + itemCatogery);
                    store.addNewSmallHardwareItem(itemID, itemName, actualQuantity, actualPrice, itemCatogery);

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
	
	if(e.getSource() == editItemQuantities) {
		 DisplayItems displayItems = new DisplayItems("edit");
         displayItems.setLocation(this.getX(), this.getY());
	}
}

	@Override
	public void itemStateChanged(ItemEvent e) {
		int choice = cmbItemTypes.getSelectedIndex();
		if(choice != -1) {
			
			switch(choice) {
			case 0:
				additionalInputs.add(new JLabel("Brand Name"));
				itemBrandNameInput = new JTextField(15);
				additionalInputs.add(itemBrandNameInput);
				
				additionalInputs.add(new JLabel("Select Appliance Category"));
		        cmbItemCategories = new JComboBox(applianceOptions);
		        break;
			case 1:
				additionalInputs.add(new JLabel("Select Small Hardware Category"));
		        cmbItemCategories = new JComboBox(smallHardWareOptions);
		        break;
			}
			
	        cmbItemCategories.setSelectedIndex(-1);
	        cmbItemCategories.addActionListener(this);
	        additionalInputs.add(cmbItemCategories);
			
			
	        
	        cmbItemTypes.setEnabled(false);

	        additionalInputs.add(submit);
	        additionalInputs.add(reset);
            repaint();
            setVisible(true);
		}
		
	}

}
