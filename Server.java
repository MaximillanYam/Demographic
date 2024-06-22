// Import
import java.net.*;
import java.io.*;

public class Server {
    // Instance variables
    ServerSocket server; 
    Socket serverSocket;
    DataInputStream input;
    DataOutputStream output;

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

    // Method to register the socket input output 
    public void registerServer(Server server) {
        try {
            server.input = new DataInputStream(server.serverSocket.getInputStream());
            server.output = new DataOutputStream(server.serverSocket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Creating methods to write and read, close
    public String readMessage() { 
        try {
            return input.readUTF();
        }
        catch(IOException e) {
            System.out.println("Failed to recieve message");
            return null;
        }
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        }
        catch(IOException e) {
            System.out.println("The message failed to send");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            input.close();
            output.close();
        }
        catch(IOException e) {
            System.out.println("The client socket failed to close");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        
        // Creating Server
        Server server1 = new Server(5000); 
        String serverResponse = "Recieved message";

        while(true) {
            try {
                // Awaiting client connection 
                server1.serverSocket = server1.server.accept();
                server1.registerServer(server1);
                System.out.println("Client has connected");

                // After connection, we can communicate
                while(true) {
                    //Read the message from the client 
                    String clientMessage = server1.readMessage();
                    if(clientMessage == null) {
                        System.out.println("Client has disconnected\nAwaiting new client...");
                        break;
                    } else {
                        System.out.println("Client: " + clientMessage);
                        server1.sendMessage(serverResponse);
                    }
                }

            } catch(IOException e) {
                // Server failed to connect with the client 
                System.out.print("Server failed to connect with the client");
            }
        }


    }
}
