package bankAlgo;

import java.util.Scanner;

public class Banker {
    private int min[][];
    private int max[][];
    private int allocate[][];
    private int available[][];
    private int numbProc;
    private int numbReso;
    
    private void input() {
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter number of processes and resources: ");
        numbProc = scan.nextInt();
        numbReso = scan.nextInt();
        
        min = new int[numbProc][numbReso];
        max = new int[numbProc][numbReso];
        allocate = new int[numbProc][numbReso];
        available = new int[1][numbReso];
        
        System.out.println("Enter allocate values: ");
        
    }
}