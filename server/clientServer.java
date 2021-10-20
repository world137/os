package os;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class clientServer {

    public static void main(String[] args) throws IOException {

        InetAddress ip = InetAddress.getByAddress(new byte[] {127, 0, 0, 1});
        System.out.println("Client is running.");

        try {
            Socket socket = new Socket(ip, 8000);
            System.out.println("Connected");
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            while (true) {
                String line = in.readUTF();
                System.out.println(line);
            }
        } catch (SocketException e) {
            System.out.println("Couldn't connect to server.");
            System.out.println("terminated");
        }

    }

}
