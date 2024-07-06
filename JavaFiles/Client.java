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

    // Takes input from the server and checks  
    public boolean readFromServer() {
        String readStatus = readMessage();
        if(readStatus == null) {
            System.out.print("Client: lost connection when reading from server");
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

    // Sends data object to the server
    public boolean sendToServer(Data object) {
        try {
            objectOutput.writeObject(object);
            objectOutput.flush();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create data object to send to the server to perform operation of database
    public Data createRow(String operation) {
        if(operation.equals("1")) {  // Adding a person
            System.out.println("Enter the persons name, age, ssn"); 
            String name = in.nextLine();
            String age = in.nextLine();  
            // Checking to see if the user entered a correct age 
            int ag = Integer.parseInt(age);
            while(ag <= 0 || ag >= 100) {
                System.out.println("Enter a range between (1-100) for age:");
                age = in.nextLine();
                ag = Integer.parseInt(age);
            }
            String ssn = in.nextLine();
            // Checking to see if the user entered a correct ssn
            while(ssn.length() != 11) {
                System.out.println("Enter a correct ssn ");
                ssn = in.nextLine();
            }
            String op = operation;
            return new Data(name, age, ssn, op);
        } else if(operation.equals("2")) {   // Delete a person
            System.out.println("Enter the ssn");
            String ssn = in.nextLine();
            // Checking to see if the user entered a correct ssn
            while(ssn.length() != 11) {
                System.out.println("Enter a correct ssn ");
                ssn = in.nextLine();
            }
            String op = operation;
            return new Data(ssn, op);
        } else {
            System.out.println("Enter the ssn");
            String ssn = in.nextLine();
            // Checking to see if the user entered a correct ssn
            while(ssn.length() != 11) {
                System.out.println("Enter a correct ssn ");
                ssn = in.nextLine();
            }
            String op = operation;
            return new Data(ssn, op);
        }
    }

    // Reasking information for user because the ssn already existed for another user
    public Data reRow() {
        System.out.println("Enter the persons name, age, ssn"); 
        String name = in.nextLine();
        String age = in.nextLine();  
        // Checking to see if the user entered a correct age 
        int ag = Integer.parseInt(age);
        while(ag <= 0 || ag >= 100) {
            System.out.println("Enter a range between (1-100) for age:");
            age = in.nextLine();
            ag = Integer.parseInt(age);
        }
        String ssn = in.nextLine();
        // Checking to see if the user entered a correct ssn
        while(ssn.length() != 11) {
            System.out.println("Enter a correct ssn ");
            ssn = in.nextLine();
        }
        return new Data(name, age, ssn, "1");
    }

    // Method to communicate with the server 
    public void handleServerCommunication() {
        boolean swi = false;

        while(true) {
            // Ask the user a prompt 
            System.out.println("Enter a choice, 1 to Add, 2 to Delete, 3 to query");
            
            // User enters choice to the prompt
            String input = getUserInput(); // Contains the number choice

            if(input.equals("stop")) {
                break;
            }

            int completed = 0;
            Data info;

            // Loop to ensure that the user sends a correct choice
            while(completed != 1) { 
                if(input.equals("1") || input.equals("2") || input.equals("3")) {
                    info = createRow(input);
                    
                    if(info.getOperation().equals("1")) {
                        sendToServer(info);
                        System.out.println(readMessage());

                        String sLogic = readMessage();
                        System.out.println("outside while statement, logic: " + sLogic);                        
                        while(sLogic.equals("1")) {
                            info = reRow();
                            sendToServer(info);
                            sLogic = readMessage();
                        }
                    } else {
                        if(sendToServer(info)) {
                            System.out.println("Server:" + readMessage());

                        } 
                    }
                    completed = 1;                    
                } else { 
                    input = getUserInput();

                    if(input.equals("stop")) {
                        swi = true;
                        break;
                    }
                }
            }

            if(swi) {
                break;
            }

            System.out.println("We getoutside and print");
            // Reading response for the getting the message
            System.out.println("Server:" + readMessage());

            // Reading response for operation done
            //System.out.println("Server:" + readMessage());
        }           
    }
}
