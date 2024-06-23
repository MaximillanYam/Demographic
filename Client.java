// Import
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {
    // Instance variables
    private Socket clientSocket; 
    private DataOutputStream output;
    private DataInputStream input;
    
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

        // Method to communicate with the server 
        public void handleServerCommunication() {
            Scanner input = new Scanner(System.in);
            String userInput; 
    
            while(true) {
                System.out.print("Send a message to the server, 'stop' to stop: ");
                userInput = input.nextLine();
    
                if(userInput.equals("stop")) {
                    break;
                }
                
                this.sendMessage(userInput);
    
                // Reading server confirmation
                String serverResponse = this.readMessage();
                System.out.println("Server: " + serverResponse);
            }
        }
}
