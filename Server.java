// Import
import java.net.*;
import java.io.*;

public class Server {
    // Instance variables
    ServerSocket server; 

    // Constructor 
    public Server(int port) {
        try {
            // Creating server
            server = new ServerSocket(port);
        }
        catch(IOException e) {
            System.out.print("Failed to create server");
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = server.accept(); // wait for client to connect
                ServerHandler handler = new ServerHandler(clientSocket);
                new Thread(handler).start();  // start new thread to handle communication with this client
            } catch(IOException e) {
                System.out.println("Failed to accept connection");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Creating Server
        Server server1 = new Server(5000); 
        server1.start();
    }
}
