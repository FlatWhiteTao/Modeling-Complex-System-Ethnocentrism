
import java.util.Random;

/**
 *
 * This class is used to handle the creation of new agents for the ethnocentrism model.
 */
public class AgentProducer {

    private Controller controller;

    //initialise
    public AgentProducer(Controller controller) {
        this.controller = controller;
    }

    /**
     * Create a new agent due to natural immigration 
     * @param patch    The patch where the created agent locate.
     * @param strategy The cooperation strategy of the created agent.
     * @param color   The color of the created agent.
     */
    public void immigrate(Patch patch, Strategy strategy, int color) {
        Agent immigrant = new Agent(strategy, color, patch, this);
        patch.setAgent(immigrant);
        controller.addAgent(immigrant);
    }

    /**
     * Creates a new agent due to reproducing.
     * @param patch  The patch where the created child locates.
     * @param parent The agent who creates this child.
     */
    public void makeChild(Patch patch, Agent parent) {
        Strategy newStrategy = parent.getStrategy();
        Random random = new Random();
        int newColor = parent.getColor();
        // if mutation occurs, the child would get new color and new strategy
        if (random.nextDouble() < Parameters.mutation_Rate) {
            newColor = random.nextInt(Parameters.colors);
            while(newColor == parent.getColor())
            	newColor = random.nextInt(Parameters.colors);
        }
        if (random.nextDouble() < Parameters.mutation_Rate)
            newStrategy = Strategy.notCoWithSelf(newStrategy);
        if (random.nextDouble() < Parameters.mutation_Rate)
            newStrategy = Strategy.notCoWithOther(newStrategy);
        Agent child = new Agent(newStrategy, newColor, patch, this);
        patch.setAgent(child);
        controller.addAgent(child);
    }
}
