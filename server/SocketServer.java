package os;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket server = new ServerSocket(8000);
        System.out.println("running");

        while (true) {
            Socket client = server.accept();
            System.out.println("connected");
            Thread t = new Thread(new Connect(client));
            t.start();
        }

    }

}

class Connect implements Runnable {

    DataOutputStream out;

    public Connect(Socket client) throws IOException {
        out = new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                out.writeUTF(new java.util.Date().toString());
            } catch (IOException e) {
                System.out.println("disconnected.");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}