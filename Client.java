// Import
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {
    // Instance variables
    Socket clientSocket; 
    DataOutputStream output;
    DataInputStream input;
    
    // Constructor
    public Client(String address, int port) {
        try{
            // Creating socket, output/input to read messages and send messages
            clientSocket = new Socket(address, port);
            output = new DataOutputStream(clientSocket.getOutputStream());
            input = new DataInputStream(clientSocket.getInputStream());

            System.out.println("You have connected to the server\n");

        }
        catch(IOException e) {
            System.out.print("The client failed to connect");
            e.printStackTrace();
        }
    }

  
    // Creating methods to write and read, close
    public String readMessage() { 
        try {
            return input.readUTF();
        }
        catch(IOException e) {
            System.out.print("Failed to recieve message");
            e.printStackTrace();
            return null;
        }
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        }
        catch(IOException e) {
            System.out.print("The message failed to send");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            clientSocket.close();
        }
        catch(IOException e) {
            System.out.print("The client socket failed to close");
            e.printStackTrace();
        }
    }
}
