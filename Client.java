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
  
    // Method to read message 
    public String readMessage() { 
        try {
            return input.readUTF(); // Takes socket input stream to read message 
        }
        catch(IOException e) {
            return null;
        }
    }

    // Method to send message 
    public String sendMessage(String message) {
        try {
            output.writeUTF(message); // Takes socket output stream to send message
            return "Sent message";
        }
        catch(IOException e) {
            return null;
        }
    }

    // Method to close the socket
    public void close() {
        try {
            clientSocket.close(); // Closing the socket
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

                // Checking if the user entered stop 
                if(userInput.equals("stop")) {
                    close();
                    break;
                }
                
                // Sending message to the server
                String sendStatus = sendMessage(userInput);
                if(sendStatus == null) {
                    System.out.println("Client: Connection lost when sending message");
                    break;
                }
        
                // Reading message from the server
                String readStatus = this.readMessage();
                if(readStatus == null) {
                    System.out.println("Client: Connection lost when reading message");
                    break;
                }
                System.out.println("Server: " + readStatus);
            } 
            
        }
}
