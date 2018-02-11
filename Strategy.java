
/**
 * This class is used to represent each cooperation strategy and 
 * provide methods to set a strategy for a new immigrant.
 * 
 * CC is cooperate with everyone. 
 * CD is cooperate with same color only. 
 * DC is cooperate with different color only. 
 * DD is cooperate with no one.
 */
public enum Strategy{
    CC, CD, DC, DD;
    /**
     * 
     * @param strategy
     * @return co-with same color strategy reversal
     */
    public static Strategy notCoWithSelf(Strategy strategy) {
        switch (strategy) {
            case CC:
                return DC;
            case CD:
                return DD;
            case DC:
                return CC;
            case DD:
                return CD;
            default:
                throw new RuntimeException("No strategy");
        }
    }
    
    /**
     * 
     * @param strategy
     * @return co-with other color strategy reversal
     */
    public static Strategy notCoWithOther(Strategy strategy) {
        switch (strategy) {
            case CC:
                return CD;
            case CD:
                return CC;
            case DC:
                return DD;
            case DD:
                return DC;
            default:
                throw new RuntimeException("No strategy");
        }
    }
}
