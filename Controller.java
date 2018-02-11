import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This class is used to control the simulation process
 * and track the numbers of each kind of agents through whole running periods.
 *  
 */

public class Controller {

	private Collection<Agent> agents = new HashSet<>();
    private AgentProducer agentProducer = null;
    private Grid grid = null;
    
    // The number of altruist strategy agents
    private int countCC = 0;
    // The number of ethnocentric strategy agents
    private int countCD = 0;
    // The number of cosmopolitan strategy agents
    private int countDC = 0;
    // The number of egoist strategy agents 
    private int countDD = 0;
    // The number of cooperation behaviors
    private int countCo = 0;
    // The number of reproduce behaviors
    private int countRe = 0;
    // The number of die behavious
    private int countDie = 0;

    public Controller(Grid grid) {
        this.grid = grid;
    }
    
    public int getCountCC() {
        return countCC;
    }

    public int getCountCD() {
        return countCD;
    }

    public int getCountDC() {
        return countDC;
    }

    public int getCountDD() {
        return countDD;
    }
    
    public int getCountCo() {
		return countCo;
	}

	public int getCountDie() {
		return countDie;
	}

	public void setCountDie(int countDie) {
		this.countDie = countDie;
	}

	public int getCountRe() {
		return countRe;
	}

	public void setAgentProducer(AgentProducer agentProducer) {
		this.agentProducer = agentProducer;
	}

	public void addAgent(Agent agent) {
        updateAgentCount(agent, 1);
        agents.add(agent);
    }

    /**
     * all possible behaviors per tick
     */
    public void tick() {
        immigrator();
        reset();
        int signalInter = interact();
        int signalRepro = reproduce();
        countCo += signalInter;
        countRe += signalRepro;
        int signalDie = die();
        countDie += signalDie;
    }
    
    /**
     * produce new immigrants with a random colour and a strategy 
     * according to their cooperate chance with same/others if empty patches exist in the grid.
     */
    public void immigrator() {
        Collection<Patch> emptyPatches = grid.getNEmptyPatch(Parameters.immigration_Rate);
        for (Patch newPatch : emptyPatches) {
        	Random random = new Random();
            int newColour = random.nextInt(Parameters.colors);
            Strategy newStrategy = Strategy.CC;
            if (random.nextDouble() > Parameters.coWithSame_Chance)
                newStrategy = Strategy.notCoWithSelf(newStrategy);
            if (random.nextDouble() > Parameters.coWithOther_Chance)
                newStrategy = Strategy.notCoWithOther(newStrategy);
            agentProducer.immigrate(newPatch, newStrategy, newColour);
        }
    }
    
    private void reset() {
        for(Agent agent : agents)
            agent.reset();
    }
    
    /**
     * each patch tries to interact with neighbours
     * @return signal: times that interact behavior occurs
     */
    private int interact() {
    	int signal = 0;
        for (Agent agent : agents)
            signal += agent.interact();
        return signal;
    }

    /**
     * (randomly iterate) each patch tries to reproduce 
     * @return signal: times that reproduce behavior occurs
     */
    private int reproduce() {
    	int signal = 0;
        List<Agent> agentList = new ArrayList<>(agents.size());
        agentList.addAll(agents);
        Collections.shuffle(agentList);//
        for (Agent agent : agentList)
            signal += agent.reproduce();
        return signal;
    }
    
    /**
     * agents die based on death_Rate. If one dies, 
     * the patch occupied would be set to vacancy
     * @return signal: times that die behavior occurs
     */
    private int die() {
    	Random random = new Random();
    	int signal = 0;
        Collection<Agent> deadAgents = new HashSet<>(agents.size());
        for (Agent agent : agents){
            if (random.nextDouble() < Parameters.death_Rate){
            	signal++;
                updateAgentCount(agent, -1);
                agent.die();
                deadAgents.add(agent);
            }
        }
        agents.removeAll(deadAgents);
        return signal;
    }
    
    /**
     * 
     * @return the percent of cooperative behaviors in all behaviors
     */
    public double getCoBehavior() {
    	double coBehavior = (double)countCo/(double)(countRe+countCo+countDie);
    	return coBehavior;
    }
    
    /**
     * 
     * @return the percent of ethnocentric strategy in all strategies
     */
    public double getEthStrategy() {
    	double ethStrategy = (double)countCD/(double)(countCC+countCD+countDC+countDD);
    	return ethStrategy;
    }
    
    private void updateAgentCount(Agent agent, int tmp) {
    	
        switch (agent.getStrategy()) {
            case CC:
                countCC += tmp;
                break;
            case CD:
                countCD += tmp;
                break;
            case DC:
                countDC += tmp;
                break;
            case DD:
                countDD += tmp;
                break;
        }
    }
}
