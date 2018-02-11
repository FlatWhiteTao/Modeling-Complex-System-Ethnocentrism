
import java.util.Collection;
import java.util.Random;

/**
 * This class is used to represent an individual of 
 * a group that with a particular cooperation strategy. 
 * Also, the class describes basic actions of an agent. 
 */
public class Agent {
	
    private Strategy strategy = null;
    private int color = -1;
    private Patch patch = null;
    private AgentProducer agentProducer = null;
    private double reproduceChance = Parameters.basePTR;
    

    public Agent(Strategy strategy, int color, Patch patch, AgentProducer agentProducer){
        this.strategy = strategy;
        this.color = color;
        this.patch = patch;
        this.agentProducer = agentProducer;
    }
    
    public int getColor() {
        return color;
    }

    public Strategy getStrategy() {
        return strategy;
    }

	public Patch getPatch() {
		return patch;
	}

	public void setPatch(Patch patch) {
		this.patch = patch;
	}
	
    public void reset() {
    	 reproduceChance = Parameters.basePTR;
    }

    /**
     * agent can gain benefit from cooperative behavior
     */
    public void cooperate() {
    	 reproduceChance += Parameters.benefit;
    }

    /**
     * each patch tries to interact with each neighbour
     * @return signal: the number of cooperative behaviors
     */
    public int interact() {
    	int signal = 0;
        Collection<Agent> neighbours = patch.getOccupiedNeighbours();
        switch (strategy) {
            case CC: // Cooperate with anyone
                for (Agent neighbour : neighbours) {
                	//providing helps
                	reproduceChance -= Parameters.cost;
                    //gain helps from neighbours
                    neighbour.cooperate();
                }
                break;
            case CD: // Cooperate with same color only.
                for (Agent neighbour : neighbours) {
                	signal = 1;
                	if (neighbour.getColor() == color){
                		reproduceChance -= Parameters.cost;
                		neighbour.cooperate();
                	}
                }
                break;
            case DC: // Cooperate with different color only.
                for (Agent neighbour : neighbours) {
                	if (neighbour.getColor() != color){
                		reproduceChance -= Parameters.cost;
                		neighbour.cooperate();
                	}
                }
                break;
		default:
			break;
        }
        return signal;
    }

    /**
     * (randomly iterate) each patch tries to reproduce if a neighbour patch is empty
     * @return signal: times that reproduce behavior occurs
     */
    public int reproduce()
    {
    	int signal = 0;
    	Random random = new Random();
    	if (this.patch.isRich()){// in rich_patch_mode
    		if (random.nextDouble() < reproduceChance + Parameters.rich_Rate){
    			signal = 1;
    			Patch childPatch = patch.getEmptyNeighbour();
    			if (childPatch != null)
    				agentProducer.makeChild(childPatch, this);
    		}
    	}
    	else{
    		if (random.nextDouble() < reproduceChance) {
    			signal = 1;
    			Patch childPatch = patch.getEmptyNeighbour();
    			if (childPatch != null)
    				agentProducer.makeChild(childPatch, this);
    		}
    	}
    	return signal;
    }
    
    public void die() {
        patch.setEmpty();
    }

    
    
}
