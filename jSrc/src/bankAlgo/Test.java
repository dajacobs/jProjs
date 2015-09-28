package bankAlgo;

public class Test {
    public static void main(String args[]) throws java.io.IOException {
        if (args.length < 1) {
            System.err.println("eg: java test <input file> <R1> <R2> <R3>");
            System.exit(-1);
        }
        // Get the name of the input file
        String inputFile = args[0];
        // int numOfResources = 3;
        // Now get the resources
        // The initial number of resources
        int initialResources[] = new int[3];
        for (int i = 0; i < 3; i++) {
            initialResources[i] = Integer.parseInt(args[i + 1].trim());
        }
        // Create the bank
        Banker theBank = new Banker(initialResources);
        int maxDemand[] = new int[3];
        // read initial values for maximum array
        String line;
        try {    
            java.io.BufferedReader inFile = new java.io.BufferedReader(new java.io.FileReader(inputFile));
            int threadNum = 0, resourceNum = 0;
            for(int i = 0; i < Banker.CUSTOMER; i++) {
                line = inFile.readLine();
                java.util.StringTokenizer tokens = new java.util.StringTokenizer(line,",");
                while(tokens.hasMoreTokens()) {
                    int amt = Integer.parseInt(tokens.nextToken().trim());
                    maxDemand[resourceNum++] = amt;
                }
                theBank.addCustomer(threadNum, maxDemand);
                ++threadNum;
                resourceNum = 0;
           }
        } catch(java.io.FileNotFoundException fnfe) { 
            throw new Error("Unable to find file " + inputFile); 
        } catch(java.io.IOException ioe) { 
            throw new Error("Error processing " + inputFile); 
        }
        // Loop reading requests
        java.io.BufferedReader cl = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        // int requests[] = new int[numOfResources];
        String requestLine;
        // The resources involved in the transaction
        int resources[]= new int[3];
        while((requestLine = cl.readLine()) != null) {
            if(requestLine.equals("")) { continue; }
            // Output the state
            if(requestLine.equals("*")) { 
                theBank.getState(); 
            // On the command line [RQ || RL] <customer number> <resource #1> <#2> <#3>
            } else {
                java.util.StringTokenizer tokens = new java.util.StringTokenizer(requestLine);
                // Request (RQ) or release (RL)
                String trans = tokens.nextToken().trim();
                // Customer number making the tranaction
                int custNum = Integer.parseInt(tokens.nextToken().trim());          
                // Get the resources involved in the transaction
                for (int i = 0; i < 3; i++) {
                    resources[i] = Integer.parseInt(tokens.nextToken().trim());
                    System.out.println("*" + resources[i] + "*");
                }
                // Now check the transaction type
                if(trans.equals("RQ")) {
                    if(theBank.requestResources(custNum,resources)) {
                        System.out.println("Approved");
                    } else { 
                        System.out.println("Denied"); 
                    }   
                // Release
                } else if(trans.equals("RL")) { 
                    theBank.releaseResources(custNum, resources); 
                // Illegal request
                } else { 
                    System.err.println("Must be either 'RQ' or 'RL'"); 
                }
            }
        }
    }
}
