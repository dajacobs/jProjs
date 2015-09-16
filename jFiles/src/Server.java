import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Server {
    public static final int PORT = 6100;
    public static void main(String[] args) {
        Socket clnt;
        ServerSocket sck;
        BufferedReader rdr;
        
        try {
            sck = new ServerSocket(PORT);
            while(true) {
                clnt = sck.accept();
                rdr = new BufferedReader(new InputStreamReader(clnt.getInputStream()));
                
                Message mssg = new MessageImpl(rdr.readLine());
                mssg.setCounts();
                
                ObjectOutputStream soos = new ObjectOutputStream(clnt.getOutputStream());
                soos.writeObject(mssg);
                System.out.println("Message written to socket");
                
                clnt.close();
                
            } 
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}