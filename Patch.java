
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class is used to represent a single space unit in the modeled world.
 * 
 */
public class Patch{
    private List<Patch> neighbours = new ArrayList<>();
    private int color = 0;
    private boolean rich = false;
    private Agent owner = null;
    private Grid grid = null;

    public Patch(Grid grid) {
        this.grid = grid;
    }

    public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isRich() {
		return rich;
	}

	public void setRich(boolean rich) {
		this.rich = rich;
	}

	public Agent getOwner() {
		return owner;
	}

	public void setOwner(Agent owner) {
		this.owner = owner;
	}

	//blob distribute advantage of rich_Rate
    public void setToRichB(){
    	this.setRich(true);
    }
    
    //random coloration
    public void setToColor(){
    	Random random = new Random();
    	this.color = random.nextInt(Parameters.colors - 1);
    }
    
    // for quater coloration
    public void setToColorB(int c){
    	this.color = c;
    }
    
    // used for reproduction and interaction
    public void setNeighbours(Collection<Patch> neighbours) {
        this.neighbours.addAll(neighbours);
    }

    public boolean isEmpty(){
        if(owner == null)
        	return true;
        return false;
    }
    public void setEmpty() {
        owner = null;
        grid.setEmpty(this);
    }

    public void setAgent(Agent agent) {
        owner = agent;
        grid.setOccupied(this);
    }
    
    /**
     * choose all non-empty neighbours(for interation)
     * @return all non-empty neighbour patches
     */
    public Collection<Agent> getOccupiedNeighbours() {
        Set<Agent> agents = new HashSet<>();
        for (Patch neighbour : neighbours)
            if (!neighbour.isEmpty())
                agents.add(neighbour.owner);
        return agents;
    }
    
    /**
     * Randomly choose an empty patch from empty neighbours(for reproducing)
     * @return an empty patch
     */
    public Patch getEmptyNeighbour() {
        List<Patch> emptyNeighbours = new ArrayList<>(neighbours.size());
        for (Patch neighbour : neighbours)
            if (neighbour.isEmpty())
                emptyNeighbours.add(neighbour);
        if (emptyNeighbours.isEmpty())
            return null;
        Random random = new Random();
        int index = random.nextInt(emptyNeighbours.size());
        return emptyNeighbours.get(index);
    }

    
}
