package clientServer;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.ObjectInputStream;

public class Client {
    public static final int PORT = 6100;
    public static final String host = "127.0.0.1";
    
    public static void main(String[] args) throws IOException {
        Socket sck = null;
        if(args.length != 1) {
            System.err.println("There is no input, 0 or more than 1 argument");
        } else {
            try {
                sck = new Socket(host, PORT);
                PrintWriter pw = new PrintWriter(sck.getOutputStream(), true);
                pw.println(args[0]);
                
                ObjectInputStream ois = new ObjectInputStream(sck.getInputStream());
                
                Message mssg = (Message) ois.readObject();
                System.out.println(mssg.getCharacterCount());
                System.out.println(mssg.getDigitCount());
                sck.close();
                
            } catch (IOException | ClassNotFoundException ioe) {
                System.err.println(ioe);
            } 
            sck.close();
        }
    }
}