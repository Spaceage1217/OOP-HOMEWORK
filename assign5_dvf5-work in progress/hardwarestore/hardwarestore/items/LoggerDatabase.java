package hardwarestore.items;



import java.io.IOException;
import java.util.logging.*;
/**
 * 
 * Helps store logger to text file
 */
 
public class LoggerDatabase {
	    static private FileHandler fileTxt;
	    static private SimpleFormatter formatterTxt;



	    /**
	     * Gets and sets the  global logger and creates the file that stores the logged info
	     * @throws IOException
	     */
	    static public void setup() throws IOException {

	     
	        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	        //logger.setUseParentHandlers(false); 
	        logger.setUseParentHandlers(true);

	        logger.setLevel(Level.INFO);
	        fileTxt = new FileHandler("HardwareStoreLogs.txt");

	        formatterTxt = new SimpleFormatter();
	        fileTxt.setFormatter(formatterTxt);
	        logger.addHandler(fileTxt);
	    }
}
