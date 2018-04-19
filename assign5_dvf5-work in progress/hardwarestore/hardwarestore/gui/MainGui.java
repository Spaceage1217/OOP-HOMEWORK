package hardwarestore.gui;


import javax.swing.*;

import hardwarestore.HardwareStore;
import hardwarestore.items.LoggerDatabase;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;


public class MainGui extends JFrame implements MouseListener, WindowListener {

	private static String[] menuOptions = {
			"1. Show all existing items records in the database (sorted by ID number).",
			"2. Add new item (or quantity) to the database.",
			"3. Delete an item from a database.",
			"4. Search for an item given its name.",
			"5. Show a list of users in the database.",
			"6. Add new user to the database.",
			"7. Update user info (given their id).",
			"8. Complete a sale transaction.",
			"9. Show completed sale transactions.",
			"10. Exit program.",	
	};
	
	HardwareStore store;
	
	private JPanel menuPanel;
	private JList<String> menuList;
	private final DefaultListModel<String> menuModel;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              try {
            	  LoggerDatabase.setup();
              } catch (IOException e) {
                  System.out.println("An error occured while creating the logger databse");
              }
                MainGui hardwareStore = new MainGui();
                hardwareStore.setVisible(true);
            }
        });
    }
    
    
    private MainGui() {
        LOGGER.info("App is now running");

        // setting up main window
        setTitle("Shipping Store");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0,0));

        try {
			store = new HardwareStore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // setting up a panel for the main menu
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);


        // setting up menu list model
        menuModel = new DefaultListModel<String>();
        for (String mn: menuOptions) {
            menuModel.addElement(mn);
        }

        // setting up list for sidebar menu
        menuList = new JList<String>();
        menuList.setModel(menuModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setLayoutOrientation(JList.VERTICAL);
        menuList.addMouseListener(this);
        menuList.setSelectedIndex(-1);

        // adding list of menu options to scrollable sidebar
        JScrollPane listMenuScroller = new JScrollPane(menuList);
        menuList.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Welcome to the Hardware Store database. Choose one of the following functions"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),

                        listMenuScroller.getBorder()));
        menuPanel.add(listMenuScroller);
     
        add(menuPanel, BorderLayout.CENTER);

    }
    
    
    
    
    
    
    
    
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			store.readDatabase();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			store.writeDatabase();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
    /**
     * This method responds to mouse click events. Based on when the user clicks the screen if a particular part of the
     * menu was clicked that even generates a new frame pertaining to the menu option.
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (menuList.getSelectedIndex()) {
            case 0:
                //user selects 'show all packages'
                LOGGER.info("User selects: Show All Items");
                DisplayItems displayItems = new DisplayItems();
                displayItems.setLocation(this.getX(), this.getY());
                break;
            case 1:
                //user selects 'add a new package'
                LOGGER.info("User selects: Add a New Item or Change Quantity of an exisiting item");
                AddOrChangeItem addOrChangeItem = new AddOrChangeItem();
                addOrChangeItem.setLocation(this.getX(), this.getY());
                break;
//            case 2:
//                //user selects 'delete a package'
//                LOGGER.info("User selects: Delete a Package");
//                DeletePackage dp = new DeletePackage();
//                dp.setLocation(this.getX(), this.getY());
//                break;
//            case 3:
//                //user selects 'search for a package'
//                LOGGER.info("User selects: Search for a Package");
//                SearchPackage searchPackage = new SearchPackage();
//                searchPackage.setLocation(this.getX(), this.getY());
//                break;
//            case 4:
//                //user selects 'show all users
//                LOGGER.info("User selects: Show All Users");
//                ShowUsers su = new ShowUsers();
//                su.setLocation(this.getX(), this.getY());
//                break;
//            case 5:
//                //user selects 'add a new user
//                LOGGER.info("User selects: Add a New User");
//                AddUser au = new AddUser();
//                au.setLocation(this.getX(), this.getY());
//                break;
//            case 6:
//                //user selects 'update an existing user
//                LOGGER.info("User selects: Update an Existing User");
//                UpdateUser uu = new UpdateUser();
//                uu.setLocation(this.getX(), this.getY());
//                break;
//            case 7:
//                //user selects 'deliver a package
//                LOGGER.info("User selects: Deliver a Package");
//                DeliverPackage deliverP = new DeliverPackage();
//                deliverP.setLocation(this.getX(), this.getY());
//                break;
//            case 8:
//                //user selects 'show all transactions
//                LOGGER.info("User selects: Show All Transactions");
//                ShowTransactions st = new ShowTransactions();
//                st.setLocation(this.getX(), this.getY());
//                break;
//            case 9:
//                LOGGER.info("User selects: Save & Exit Program");
//                LOGGER.info("Exiting Program");
//                Runtime.getRuntime().exit(0);
//                break;
        }
    }
	
	
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
