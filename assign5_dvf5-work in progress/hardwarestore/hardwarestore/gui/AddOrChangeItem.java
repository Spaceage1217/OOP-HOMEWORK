package hardwarestore.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

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

	
	private static final String successNewItem = "Success: Item added to the database";
	private static final String errorNewItem = "Error: (Empty fields not allowed) Please check the form and try again.";
	private static final String successChangeQuantity = "Success: Quantity changed";
	private static final String errorChangeQuantity = "Error: Quantity not changed";
	
	
	
	
	
	ArrayList<Item> items;
		
	private JComboBox cmbItemTypes;
	private JComboBox cmbCategories;
	
	private JTextField itemNameInput;
	private JTextField itemQuantityInput;
	private JTextField itemPriceInput;
	private JTextField itemBrandNameInput;
	
	
	private JPanel submitPanel;
	private JPanel additionalInputs;
	
	private JButton submit;
    private JButton reset;
    private JButton displayItems;
	
    private String itemName;
    private String itemQuantity;
    private String itemPrice;
    private String itemBrandName;
    private String itemType;
    private String catogery;
    
	private HardwareStore store;
	
	


	AddOrChangeItem(){

		setTitle("Add or Change Item Quantity");
		setLayout( new FormLayout());
		
		
		try {
			store = new HardwareStore();
			items = (ArrayList<Item>)store.getItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//first name
		//quantity
		//price
		// type select combo box
		// brand name if appliance
		//category select based on type combo box
		
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
        cmbItemTypes.addActionListener(this);
        add(cmbItemTypes);
        
        // submit panel
        add(new JLabel(""));
        submitPanel = new JPanel();
        add(submitPanel);
		
        
        // buttons
        submit = new JButton("Add Item");
        submit.addActionListener(this);
        submitPanel.add(submit);

        reset = new JButton("Reset");
        reset.addActionListener(this);
        submitPanel.add(reset);

        displayItems = new JButton("Show All Items");
        displayItems.addActionListener(this);
        submitPanel.add(displayItems);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setAlwaysOnTop(true);
        setVisible(true);
      


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if(e.getSource() == submit) {
			itemName = itemNameInput.getText();
			itemPrice = itemPriceInput.getText();
			itemQuantity = itemQuantityInput.getText();
			// itemBrandName = itemBrandNameInput.getText();
			itemType = (String) cmbItemTypes.getItemAt(cmbItemTypes.getSelectedIndex());
			//catogery  = (String) cmbCategories.getItemAt(cmbCategories.getSelectedIndex());
			
			float actualPrice;
			int actualQuantity;
			
			if(itemName.length() < 1) {
				JOptionPane.showMessageDialog(new JFrame(),"Invalid Name. "
                        +  "Name cannot be left blank");
			}
			
			try {
					
				actualPrice = Float.parseFloat(itemPrice.trim());
				if (actualPrice < 0 ) {
					JOptionPane.showMessageDialog(new JFrame(),"Invalid price. "
	                        + "Please check that price is not less than 0.");
					}
				
			}
			catch(NumberFormatException exp) {
					JOptionPane.showMessageDialog(new JFrame(),"Invalid price. "
	                        +  "Please enter numerical values only");
			}
			
			try {
				
				actualQuantity = Integer.parseInt(itemQuantity.trim());
				if (actualQuantity < 0 ) {
					JOptionPane.showMessageDialog(new JFrame(),"Invalid Quantity. "
	                        + "Please check that quantity is not less than 0.");
					}
				
			}
			catch(NumberFormatException exp) {
					JOptionPane.showMessageDialog(new JFrame(),"Invalid Quantity. "
	                        +  "Please enter numerical values only");
			}
			
			
	
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
				itemBrandNameInput.addActionListener(this);
				additionalInputs.add(itemBrandNameInput);
				
				additionalInputs.add(new JLabel("Select Appliance Category"));
		        cmbCategories = new JComboBox(applianceOptions);
		        break;
			case 1:
				additionalInputs.add(new JLabel("Select Small Hardware Category"));
		        cmbCategories = new JComboBox(smallHardWareOptions);
		        break;
			}
			
			cmbCategories.setSelectedIndex(-1);
	        cmbCategories.addActionListener(this);
	        additionalInputs.add(cmbCategories);
	        
	        cmbItemTypes.setEnabled(false);

            submitPanel.add(submit);
            submitPanel.add(reset);
            repaint();
            setVisible(true);
		}
		
	}

}
