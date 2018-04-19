package hardwarestore.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import hardwarestore.HardwareStore;
import hardwarestore.items.Appliances;
import hardwarestore.items.Item;
import hardwarestore.items.SmallHardwareItems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;



public class DisplayItems  extends JFrame{
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    HardwareStore store;
    JTable itemDisplay;
    String[] columnNames = {"Item ID", "Name", "Quantity", "Price", "Item Type","Category / Brand and type"};
    ArrayList<Item> items;
    
    /**
     * The showItem constructor. creats a Jtable that will help display the items.
     */
    	DisplayItems() {
    	    setSize(1075, 500);
    	    setTitle("Package List");
    	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	    try {
				store = new HardwareStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	    HardwareStore.sortItemList();
    	    items = (ArrayList<Item>)store.getItems();
    	
    	    itemDisplay = new JTable();
    	    itemDisplay.setModel(new DefaultTableModel(new Object[][] {}, columnNames));  	
    	    addRowsToTable();
    	
    	    add(new JScrollPane(itemDisplay));
    	    setVisible(true);
    	}
    	
    	/**
    	 * adds an Item into the table row.
    	 */
    	public void addRowsToTable() {
    	    LOGGER.info("Creating Items table");

    	    DefaultTableModel tableModel = (DefaultTableModel) itemDisplay.getModel();
    	    //create array to speficiy what goes in the columns
    	    Object columns[] = new Object[6];
    	    
    	        for (Item item: items) {
    	            columns[0] = item.getIdNumber();
    	            columns[1] = item.getName();
    	            columns[2] = Integer.toString(item.getQuantity());
    	            columns[3] = item.getPrice();
    	            if(item instanceof Appliances) {
    	            	columns[4] = "Appliances";
    	            	columns[5] = ((Appliances)item).getBrand()+" "+ ((Appliances)item).getType();
    	            }
    	            else {
    	            	columns[4] = "Small HArdware Items";
    	            	columns[5] = ((SmallHardwareItems)item).getCategory();
    	            }

    	            tableModel.addRow(columns);
    	        }
    	}





}



