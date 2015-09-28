package bankAlgo;

public class Banker {
    public static final int CUSTOMER = 5;
    
    // Number of resources
    private final int resource;     
    // Number of each resource
    private final int[] available;
    // Demand of each thread
    private final int[][] maximum;
    // Number allowed to each thread
    private final int[][] allocation;
    // Remainder of each thread
    private final int[][] need;
    
    
    /**
     *  New bank constructor with resources.
     * @param resources
     **/
    public Banker(int resources[]) {
        resource = resources.length;
        available = new int[resource];
        System.arraycopy(resources, 0, available, 0, resource);
        maximum = new int[CUSTOMER][];
        allocation = new int[CUSTOMER][];
        need = new int[CUSTOMER][];
    }
    
    /**
     * Invoked by a thread when it enters the system.
     * Max demand with the bank is also recorded.
     * @param threadNum
     * @param maxDemand
     **/
    public void addCustomer(int threadNum, int maxDemand[]) {
        maximum[threadNum] = new int[resource];
        allocation[threadNum] = new int[resource];
        need[threadNum] = new int[resource];
        System.arraycopy(maxDemand, 0, maximum[threadNum], 0, maxDemand.length);
        System.arraycopy(maxDemand, 0, need[theadNum], 0, maxDemand.length);
    }
}