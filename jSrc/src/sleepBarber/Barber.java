package sleepBarber;

import static sleepBarber.SleepingBarber.accessSeats;
import static sleepBarber.SleepingBarber.barber;
import static sleepBarber.SleepingBarber.customers;
import static sleepBarber.SleepingBarber.numberOfFreeSeats;

public class Barber extends Thread {
    public Barber() {
        
    }
    @Override
    public void run() {
        while(true) {
            try {
                customers.acquire();
                accessSeats.release();
                numberOfFreeSeats++;
                barber.release();
                accessSeats.release();
                this.cutHair();
            } catch (InterruptedException e) {
                
            }
        }
    }

    private void cutHair() {
        System.out.println("The barber is cutting hair.");
        try {
            sleep(5000);
        } catch(InterruptedException e) {
            
        }
    }
}