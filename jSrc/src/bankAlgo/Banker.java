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
        System.arraycopy(maxDemand, 0, need[threadNum], 0, maxDemand.length);
    }
    
    /**
     * The thread state is output.
     **/
    public void getState() {
        System.out.print("Available = \t[");
        for(int i = 0; i < resource - 1; i++) { 
            System.out.print(available[i] + " "); 
        }
        System.out.println(available[resource - 1] + "]");
        System.out.print("\nAllocation = \t");
        for(int i = 0; i < CUSTOMER; i++) {
            System.out.print("[");
            for(int j = 0; j < resource - 1; j++) { 
                System.out.print(allocation[i][j] + " "); 
            }
            System.out.print(allocation[i][resource - 1] + "]");
        }
        System.out.print("\nMax = \t\t");
        for(int i = 0; i < CUSTOMER; i++) {
            System.out.print("[");
            for(int j = 0; j < resource - 1; j++) { 
                System.out.print(maximum[i][j] + " "); 
            }
            System.out.print(maximum[i][resource - 1]+"]");
        }
        System.out.print("\nNeed = \t\t");
        for(int i = 0; i < CUSTOMER; i++) {
            System.out.print("[");
            for(int j = 0; j < resource - 1; j++) { 
                System.out.print(need[i][j]+" "); 
            }
            System.out.print(need[i][resource - 1]+"]");
        }
        System.out.println();
    }
    
    /**
     * Determines whether granting a request results in leaving
     * the system in a safe state or not.
     * @return  true - the system is in a safe state
     **/
    private boolean isSafeState (int threadNum, int request[]) {
        System.out.print("\n Customer # " + threadNum + " requesting ");
        for (int i = 0; i < resource; i++) { 
            System.out.print(request[i] + " "); 
        }
        System.out.print("Available = ");
        for(int i = 0; i < resource; i++) { 
            System.out.print(available[i] + "  "); 
        }
        // Are sufficient resources available
        for(int i = 0; i < resource; i++) {
            if(request[i] > available[i]) {
                System.err.println("INSUFFICIENT RESOURCES");
                return false;
            }
        }
        // Ordering of threads to finish
        boolean[] canFinish = new boolean[CUSTOMER];
        for(int i = 0; i < CUSTOMER; i++) { 
            canFinish[i] = false; 
        }
        // Copy the available matrix to available
        int[] avail = new int[resource];
        System.arraycopy(available, 0, avail, 0, available.length);
        // Decrement available by request and adjust need and allocation
        for (int i = 0; i < resource; i++) {
            avail[i] -= request[i];
            need[threadNum][i] -= request[i];
            allocation[threadNum][i] += request[i];
        }
        // Ordering of threads so that each thread can finish
        for(int i = 0; i < CUSTOMER; i++) {
            // Find a thread that can finish
            for(int j = 0; j < CUSTOMER; j++) {
                if(!canFinish[j]) {
                    boolean temp = true;
                    for(int k = 0; k < resource; k++) {
                        if(need[j][k] > avail[k]) { 
                            temp = false; 
                        }
                    }
                    // If this thread can finish
                    if(temp) { 
                        canFinish[j] = true;
                        for (int x = 0; x < resource; x++) { 
                            avail[x] += allocation[j][x]; 
                        }
                    }
                }
            }
        }
        // Restore the value of need and allocation for this thread
        for(int i = 0; i < resource; i++) {
            need[threadNum][i] += request[i];
            allocation[threadNum][i] -= request[i];
        }
        boolean returnValue = true;
        for(int i = 0; i < CUSTOMER; i++) {
            if(! canFinish[i]) {
                returnValue = false;
                break;
            }
        }
        return returnValue;
    }
    
    /**
     * Request for resources.
     * Blocking method returns when the request can safely be satisfied.
     * @param request
     * @param threadNum
     * @return  true - the request is granted.
     **/
    public synchronized boolean requestResources(int threadNum, int[] request) {
        if (!isSafeState(threadNum,request)) { 
            return false; 
        }
        // If it is safe, allocate the resources
        for (int i = 0; i < resource; i++) {
            available[i] -= request[i];
            allocation[threadNum][i] += request[i];
            need[threadNum][i] = maximum[threadNum][i] - allocation[threadNum][i];
        }
        System.out.println("Customer # " + threadNum + " using resources.");
        System.out.print("Available = ");
        for (int i = 0; i < resource; i++) { System.out.print(available[i] + "  "); }
        System.out.print("Allocated = [");
        for (int i = 0; i < resource; i++) { System.out.print(allocation[threadNum][i] + "  "); }
        System.out.print("]");
        return true;
    }
}