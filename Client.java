// Import
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {
    // Instance variables
    private Socket clientSocket; 
    private DataOutputStream output;
    private DataInputStream input;
    private ObjectOutputStream objectOutput;
    Scanner in = new Scanner(System.in);
    
    // Constructor
    public Client(String address, int port) {
        try{
            // Creating socket, output/input to read messages and send messages
            clientSocket = new Socket(address, port);
            output = new DataOutputStream(clientSocket.getOutputStream());
            input = new DataInputStream(clientSocket.getInputStream());
            objectOutput = new ObjectOutputStream(output);

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

    // Read module 
    public boolean readFromServer() {
        String readStatus = readMessage();
        if(readStatus == null) {
            return true;
        } else {
            System.out.println("Server: " + readStatus);
            return false;
        }
    }

    // User input module 
    public String getUserInput() {
        String userInput;
        System.out.println("Enter your message: ");
        userInput = in.nextLine();

        return userInput;
    }

    // Send module
    // **Have to modify the messge to sent the data object** 
    public void sendToServer(Data object) {
        try {
            objectOutput.writeObject(object);
            objectOutput.flush();
            //return true;
        } catch(IOException e) {
            e.printStackTrace();
            //return false;
        }
    }

    // Create data object 
    public Data createRow(String operation) {
        if(operation.equals("1")) {  // Adding a person
            System.out.println("Enter the persons name, age, ssn"); 
            String name = in.nextLine();
            int age = in.nextInt();
            in.nextLine();
            int ssn = in.nextInt();
            String op = operation;
            return new Data(name, age, ssn, op);
        } else if(operation.equals("2")) {   // Delete a person
            System.out.println("Enter the ssn");
            int ssn = in.nextInt();
            String op = operation;
            return new Data(ssn, op);
        } else {
            System.out.println("Enter the ssn");
            int ssn = in.nextInt();
            String op = operation;
            return new Data(ssn, op);
        }
    }

    // Method to communicate with the server 
    public void handleServerCommunication() {
        while(true) {
            // Reads prompt from server 
            if(readFromServer()) {
                close();
                System.out.print("Client: lost connection when reading from server");
                break;
            }
            
            // User enters choice to the prompt
            String input = getUserInput(); // Contains the number choice
            int completed = 0;
            Data info;

            while(completed != 1) { 
                if(input.equals("1") || input.equals("2") || input.equals("3")) {
                    info = createRow(input);
                    sendToServer(info);
                    completed = 1;
                    /* 
                    if(sendToServer(info)) {
                        System.out.println("Client: Failed to send data");
                    } else {
                        completed = 1;
                    }
                    */
                } else {
                    input = getUserInput();
                }
            }
        }           
    }

}
