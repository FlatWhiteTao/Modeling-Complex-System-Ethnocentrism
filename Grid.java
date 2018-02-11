
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *  This class is used to simulate the real world and provide spaces for agents 
 *  to conduct basic actions by storing a collection of patches.
 */
public class Grid
{
    
    private static int size = Parameters.size;
    private static Patch[][] patchGrid = new Patch[size][];
    private Set<Patch> emptyPatches = new HashSet<>();
    // the number of agents on rich patch
	private int richCountCC = 0;
	private int richCountCD = 0;
	private int richCountDD = 0;
	private int richCountDC = 0;

	public Grid(){
        for (int i = 0; i < size; i += 1){
            Patch[] row = new Patch[size];
            patchGrid[i] = row;
            for (int j = 0; j < size; j += 1){
                row[j] = new Patch(this);
                emptyPatches.add(row[j]);
            }
        }
        initialiseGrid();
    }

    public int getRichCountCC() {
		return richCountCC;
	}

	public int getRichCountCD() {
		return richCountCD;
	}

	public int getRichCountDD() {
		return richCountDD;
	}

	public int getRichCountDC() {
		return richCountDC;
	}

	public void setEmpty(Patch patch) {
	    emptyPatches.add(patch);
	}

	public void setOccupied(Patch patch) {
	    emptyPatches.remove(patch);
	}
	    
	/**
	 * initialise the grid. the most left column is treated as neighbour to the most right column
	 * same as the most top and bottom row
	 * @param size
	 */
    private static void initialiseGrid(){
    	for(int m = 0;m < size;m++)
    		for(int n = 0;n < size;n++){
    			int up = m - 1;
    			int down = m + 1;
    			int left = n - 1;
    			int right = n + 1;
    			if(m == 0)
    				up = size -1;
    			if(m == size-1)
    				down = 0;
    			if(n == 0)
    				left = size - 1;
    			if(n == size-1)
    				right = 0;
    			initialisePatch(m, n, up, down, left, right);
    		}
    	extensionPatch();
    }
    
    private static void initialisePatch(int m, int n, int up, int down, int left, int right){
        // Initialise the patch with its neighbours.
    	Patch patch = patchGrid[m][n];
        Collection<Patch> neighbours = Arrays.asList(patchGrid[up][n], patchGrid[m][left],
                        patchGrid[m][right], patchGrid[down][n]);
        patch.setNeighbours(neighbours);
    }
    /**
     * 
     * extension work
     */
    private static void extensionPatch(){
    	if(Parameters.rich_mode == 2)
        	for(int m = 0; m < Parameters.size/2; m++)
        		for (int n = 0;n < Parameters.size; n++)
        			patchGrid[m][n].setToRichB();
    }

    /**
     * 
     * @return select at most n empty patches (for immigration)
     */
    public Collection<Patch> getNEmptyPatch(int n) {
    	Random random = new Random();
        List<Patch> emptyList = new ArrayList<>(emptyPatches.size());
        emptyList.addAll(emptyPatches);
        Collection<Patch> randomCollection = new HashSet<>(n);
        for(int i = 0; i < n && emptyList.size() > 0; i++){
            int index = random.nextInt(emptyList.size());
            randomCollection.add(emptyList.remove(index));
        }
        return randomCollection;
    }
    
    /**
     * calculating the number of agents on rich patches
     */
    public void getRichNumber() {
    	richCountCC = 0;
    	richCountCD = 0;
    	richCountDD = 0;
    	richCountDC = 0;
    	for(int m = 0;m < Parameters.size;m++)
    		for(int n = 0;n < Parameters.size;n++)
    			if(patchGrid[m][n].isRich()&&!patchGrid[m][n].isEmpty())
    				switch (patchGrid[m][n].getOwner().getStrategy()) {
    		            case CC:
    		                richCountCC += 1;
    		                break;
    		            case CD:
    		                richCountCD += 1;
    		                break;
    		            case DC:
    		                richCountDC += 1;
    		                break;
    		            case DD:
    		                richCountDD += 1;
    		                break;
    		        }
    }
    
}
