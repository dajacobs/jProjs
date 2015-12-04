package sleepBarber;

public class BarberTest implements Runnable {   
    // Main function
    public static void main(String[] args) {
        new Thread(new BarberTest()).start();
    }
}