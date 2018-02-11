import java.io.FileWriter;
import java.io.IOException;
/**
 * 
 * The entry point of the replicated model.
 *
 */

public class Sim {

	public static void main(String[] args){
		
		Parameters.getParameters();
		Grid grid = new Grid();
		Controller controller = new Controller(grid);
		AgentProducer agentProducer = new AgentProducer(controller);
		controller.setAgentProducer(agentProducer);
        try
        {
            FileWriter csv = new FileWriter(System.getProperty("user.dir") + "/result.csv");
            labelCsv(csv);
            outputResult(controller, grid, 0, 0, csv);
            int tmpTicks = 0;
            for (int i = 0; i < Parameters.totalTicks / Parameters.segTicks; i++){
            	tmpTicks += Parameters.segTicks;
            	outputResult(controller, grid, Parameters.segTicks, tmpTicks, csv);
            }
            if (tmpTicks < Parameters.totalTicks)
            	outputResult(controller, grid, Parameters.totalTicks-tmpTicks, 
            			Parameters.totalTicks, csv);
            csv.flush();
            csv.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        
	}
	
	private static void outputResult(Controller controller, Grid grid, int ticks, 
			int tmpTicks, FileWriter csv) throws IOException
    {
        for (int i = 0; i < ticks; i++){
            controller.tick();
            updateCsv(controller,grid, csv);
        }
        System.out.println();
        System.out.println("******" + tmpTicks + " ticks******");
        System.out.println("CC count: " + controller.getCountCC());
        System.out.println("CD count: " + controller.getCountCD());
        System.out.println("DC count: " + controller.getCountDC());
        System.out.println("DD count: " + controller.getCountDD());
        if(Parameters.rich_mode == 2){
        	grid.getRichNumber();
        	System.out.println("richCC count: " + grid.getRichCountCC());
        	System.out.println("richCD count: " + grid.getRichCountCD());
        	System.out.println("richDC count: " + grid.getRichCountDC());
        	System.out.println("richDD count: " + grid.getRichCountDD());
        }
        //System.out.println("percent cooperative behavior: " + controller.getCoBehavior());
        System.out.println("percent ethnocentric strategy: " + controller.getEthStrategy());
    }

    private static void labelCsv(FileWriter csv) throws java.io.IOException {
        csv.append("CC");
        csv.append(',');
        csv.append("CD");
        csv.append(',');
        csv.append("DC");
        csv.append(',');
        csv.append("DD");
        if(Parameters.rich_mode == 2){
        	csv.append(',');
        	csv.append("richCC");
            csv.append(',');
            csv.append("richCD");
            csv.append(',');
            csv.append("richDC");
            csv.append(',');
            csv.append("richDD");
        }
        csv.append('\n');
    }

    private static void updateCsv(Controller controller, Grid grid, FileWriter csv) throws IOException {
        csv.append(Integer.toString(controller.getCountCC()));
        csv.append(',');
        csv.append(Integer.toString(controller.getCountCD()));
        csv.append(',');
        csv.append(Integer.toString(controller.getCountDC()));
        csv.append(',');
        csv.append(Integer.toString(controller.getCountDD()));
        if(Parameters.rich_mode != 0){
        	grid.getRichNumber();
        	csv.append(',');
        	csv.append(Integer.toString(grid.getRichCountCC()));
        	csv.append(',');
        	csv.append(Integer.toString(grid.getRichCountCD()));
        	csv.append(',');
        	csv.append(Integer.toString(grid.getRichCountDC()));
        	csv.append(',');
        	csv.append(Integer.toString(grid.getRichCountDD()));
        }
        csv.append('\n');
    }
}
