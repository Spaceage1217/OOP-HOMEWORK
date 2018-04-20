package hardwarestore.gui;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import hardwarestore.HardwareStore;
import hardwarestore.items.Appliances;
import hardwarestore.items.Item;
import hardwarestore.items.SmallHardwareItems;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;



public class DisplayItems  extends JFrame implements ActionListener{
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private JButton editButton;
	private JPanel editPanel;
	private JButton deleteButton;
	private JButton searchButton;
	private JButton resetButton;
	private JPanel inventoryPanel;
	private JTextField searchBox;


    HardwareStore store;
    JTable itemDisplay;
    String[] columnNames = {"Item ID", "Name", "Quantity", "Price", "Item Type","Category / Brand and type"};
    ArrayList<Item> items;
    
    /**
     * The showItem constructor. creats a Jtable that will help display the items.
     */
  
    
    	DisplayItems(String mode) {
    		
    		    setLayout(new FormLayout());
    		    
    		if(mode.equals("edit")) {
    			LOGGER.info("User is now edit quantity mode");
    			editPanel = new JPanel();
    			editPanel.setBorder(
    	                BorderFactory.createCompoundBorder(
    	                        BorderFactory.createCompoundBorder(
    	                                BorderFactory.createTitledBorder("Edit a specifc item quantity"),
    	                                BorderFactory.createEmptyBorder(5,5,5,5)), null));
    		  	add(new JLabel("Select Row you wish to change "));
    			editButton = new JButton ("EDIT Quantity");
    			editButton.setToolTipText("Select Row then Click to change item Quantity");
    			editButton.addActionListener(this);
    			editPanel.add(editButton);
    			add(editPanel);
    		}
    		
    		if(mode.equals("delete")) {
    			LOGGER.info("User is now in delete mode");
    			editPanel = new JPanel();
    			editPanel.setBorder(
    	                BorderFactory.createCompoundBorder(
    	                        BorderFactory.createCompoundBorder(
    	                                BorderFactory.createTitledBorder("Remove an Item from inventory"),
    	                                BorderFactory.createEmptyBorder(5,5,5,5)), null));
    		  	add(new JLabel("Select Row you wish to remove"));
    		  	deleteButton = new JButton ("Remove Item");
    		  	deleteButton.setToolTipText("Select Row then Click to remove from inventory");
    			
    		  	deleteButton.addActionListener(this);
    			editPanel.add(deleteButton);
    			add(editPanel);
    		}
    		
    		if(mode.equals("search")) {
    			
    			LOGGER.info("User is now in search mode");
    			editPanel = new JPanel();
    			
    			editPanel.setBorder(
    	                BorderFactory.createCompoundBorder(
    	                        BorderFactory.createCompoundBorder(
    	                                BorderFactory.createTitledBorder("Search for an Item"),
    	                                BorderFactory.createEmptyBorder(5,5,5,5)), null));
    			
    		  	add(new JLabel("Enter Item you wish to search for"));
    		  	searchBox = new JTextField();
    		  	searchBox.setPreferredSize(new Dimension(100,30));
    			editPanel.add(searchBox);
    		
    			searchButton = new JButton ("Search");
    			searchButton.setToolTipText("Press to search");
    			searchButton.addActionListener(this);
    			editPanel.add(searchButton);


    			
    			resetButton = new JButton ("show all");
    			resetButton.setToolTipText("Press to show all");
    			resetButton.addActionListener(this);
    			editPanel.add(resetButton);

    			
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
    	    
    	    HardwareStore.sortItemList();
    	    items = (ArrayList<Item>)store.getItems();
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
    	
    	public void addRowsToTable(ArrayList<Item> items) {
    		
    	    LOGGER.info("Creating search table");

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

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == editButton) {
				String newQuantity =JOptionPane.showInputDialog("Enter new quantity to be added to exsisting amount");
				try {
					int actualNewQuantity = Integer.parseInt(newQuantity.trim());
					if(actualNewQuantity < 0) {
						JOptionPane.showMessageDialog(new JFrame(),"Invalid Quantity. "
		                        + "Please check that quantity is not less than 0.");
					}
					else {
						String itemID = (String) itemDisplay.getModel().getValueAt(itemDisplay.getSelectedRow(), 0);
						String oldQuantity = (String )itemDisplay.getModel().getValueAt(itemDisplay.getSelectedRow(), 2);
						int actualOldQuantity = Integer.parseInt(oldQuantity.trim());
						
						
						int itemIndex = store.findItemIndex(itemID);
						store.addQuantity(itemIndex, actualNewQuantity);
						itemDisplay.getModel().setValueAt(actualOldQuantity+actualNewQuantity, itemDisplay.getSelectedRow(), 2);
						store.writeDatabase();
						LOGGER.info("Saving new Item quantity to database");
					}

				}
				catch(NullPointerException exp) {
					// user canceled or left string empty =(
				} catch (IOException e1) {
					LOGGER.info("Could not save new Item quantity to database");
					e1.printStackTrace();
				}

			}
			
			if(e.getSource() == deleteButton){
				try {
					String itemID = (String) itemDisplay.getModel().getValueAt(itemDisplay.getSelectedRow(), 0);
					int itemIndex = store.findItemIndex(itemID);
				    int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete, this is permenant","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
		                store.removeItem(itemIndex);
		            	store.writeDatabase();
		        	    itemDisplay.setModel(new DefaultTableModel(new Object[][] {}, columnNames));  	
		        	    addRowsToTable();
						LOGGER.info("Deleting Item from database");
					}
				}catch(NullPointerException exp) {
					
				} catch (IOException e1) {
					LOGGER.info("something went wrong when trying to delete item");
					e1.printStackTrace();
				}
			}
			
			if(e.getSource() == searchButton) {
				try {
					String query = searchBox.getText();
	        	    itemDisplay.setModel(new DefaultTableModel(new Object[][] {}, columnNames));  	
					addRowsToTable((ArrayList<Item>) store.getMatchingItemsList(query));
				}catch(NullPointerException exp) {
					JFrame message = new JFrame();
					message.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(message,"Invalid Form. "
	                        +  "Search box cannot be empty");
				}
				
			}
			if(e.getSource() == resetButton) {
				itemDisplay.setModel(new DefaultTableModel(new Object[][] {}, columnNames));  	
        	    addRowsToTable();
			}
			
		}





}



