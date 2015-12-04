package sleepBarber;

import static sleepBarber.SleepingBarber.accessSeats;
import static sleepBarber.SleepingBarber.barber;
import static sleepBarber.SleepingBarber.customers;
import static sleepBarber.SleepingBarber.numberOfFreeSeats;

public class Customer extends Thread {
    int id;
    boolean notCut = true;
    
    // Customer constructor
    public Customer(int i) {
        id = i;
    }
    
    @Override
    public void run() {
        while(notCut) {
            try {
                accessSeats.acquire();
                if(numberOfFreeSeats > 0) {
                    System.out.println("Customer " + this.id + " just sat down.");
                    numberOfFreeSeats--;
                    customers.release();
                    accessSeats.release();
                    try {
                        barber.acquire();
                        notCut = false;
                        this.getHaircut();
                    } catch(InterruptedException e) {
                        
                    }
                } else {
                    System.out.println("There are no free seats. Customer " + this.id + " has left the barbershop.");
                    accessSeats.release();
                    notCut = false;
                }
            } catch(InterruptedException e) {
                
            }
        }
    }
    public void getHaircut() {
        System.out.println("Customer " + this.id + " is getting his hair cut.");
        try {
            sleep(5050);
        } catch(InterruptedException e) {
                
        }
    }    
}