import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
/**
 * This class is used to store the default values of parameters, 
 * and also provide methods to overwrite the value of each parameter. 
 *
 */
public class Parameters {
	// default parameters
	
    // The number of total kinds of groups in the model, each color represents one group 
    protected static int colors = 4;

    // The width and height of the grid
    protected static int size = 50;

    // The number of immigrating new agent per tick
    protected static int immigration_Rate = 1;

    // The entire running ticks for simulation
    protected static int totalTicks = 2000;

    // Number of ticks to run between state reports..
    protected static int segTicks = 50;

    // The probability of an agent dies per tick
    protected static double death_Rate = 0.10;

    // The probability of an offspring mutates
    protected static double mutation_Rate = 0.005;

    // The initialized probability of an agent reproducing in each tick 
    // without the effects of interactions
    protected static double basePTR = 0.12;

    // The cost of one agent giving help to another agent
    protected static double cost = 0.01; 
    
    // The benefit of one agent receiving help from another agent 
    protected static double benefit = 0.03; 

    // Indicates the probability of an immigrant being in-group cooperative 
    protected static double coWithSame_Chance = 0.5;
    
    // Indicates the probability of an immigrant being out-group cooperative
    protected static double coWithOther_Chance = 0.5;
    
    // extension2: patch rich rate
    protected static int rich_mode = 0;
    // describing how rich a patch is
    protected static double rich_Rate = 0.1;
    
    // get experiment parameters, to overwrite default parameters.
    public static void getParameters(){
    	
        if (System.getProperty("colors") != null)
        	colors = parseInt(System.getProperty("colors"));

        if (System.getProperty("size") != null)
            size = parseInt(System.getProperty("size"));

        if (System.getProperty("immigration_Rate") != null)
        	immigration_Rate = parseInt(System.getProperty("immigration_Rate"));

        if (System.getProperty("ticks") != null)
        	totalTicks = parseInt(System.getProperty("steps"));

        if (System.getProperty("segTicks") != null)
        	segTicks= parseInt(System.getProperty("segTicks"));

        if (System.getProperty("death_Rate") != null)
            death_Rate = parseInt(System.getProperty("death_Rate"));

        if (System.getProperty("mutation_Rate") != null)
            mutation_Rate = parseDouble(System.getProperty("mutation_Rate"));

        if (System.getProperty("basePTR") != null)
        	basePTR = parseDouble(System.getProperty("basePTR"));

        if (System.getProperty("cost") != null)
        	cost = parseDouble(System.getProperty("cost"));

        if (System.getProperty("benefit") != null)
        	benefit = parseDouble(System.getProperty("benefit"));

        if (System.getProperty("coWithSame_Chance") != null)
        	coWithSame_Chance = parseDouble(System.getProperty("coWithSame_Chance"));

        if (System.getProperty("coWithOther_Chance") != null)
        	coWithOther_Chance = parseDouble(System.getProperty("coWithOther_Chance"));
        
        if (System.getProperty("rich_mode") != null){
        	rich_mode = parseInt(System.getProperty("rich_mode"));
        	if(rich_mode != 0 && rich_mode != 2)
        		System.out.println("Wrong rich_mode!");
        	System.exit(0);
        }
        
        if (System.getProperty("rich_Rate") != null)
        	rich_Rate = parseDouble(System.getProperty("rich_Rate"));
    }
}
