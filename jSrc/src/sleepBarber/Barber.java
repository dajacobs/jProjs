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
    public static void main(String[] args) {
        Barber newBarb = new Barber();
        newBarb.start();
        
        for(int i = 1; i < 16; i++) {
            Customer newCust = new Customer(i);
            newCust.start();
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                
            }
        }
    }
}