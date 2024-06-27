import java.net.Socket;
import java.io.*;

public class ServerHandler implements Runnable{
    private Socket serverSocket; 
    private DataInputStream input;
    private DataOutputStream output;
    private ObjectInputStream objectInput;

    // Constructor
    public ServerHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;

        try {
            input = new DataInputStream(serverSocket.getInputStream());
            output = new DataOutputStream(serverSocket.getOutputStream());
            objectInput = new ObjectInputStream(input);


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Method to close socket
    public void close() {
        try {
            serverSocket.close();
        } catch(IOException e) {
            System.out.println("The server socket failed to close");
            e.printStackTrace();
        }
    }

    // Method to read message from socket 
    public String readMessage() {
        try {
            return input.readUTF();
        } catch(IOException e) {
            return null;
        }
    }

    // Method to send message through socket 
    public String sendMessage(String message) {
        try { 
            output.writeUTF(message);
            return "Sent message";
        } catch(IOException e) {
            return null; 
        }
    } 

    // Read module 
    // **Have to implement the reading of data object**
    public Data readFromClient() {
        try {
            Data read = (Data) objectInput.readObject();
            return read;
        } catch(ClassNotFoundException | IOException e) {
            return null;
        }
    }

    // Send module
    public boolean sendToClient(String input) {
        String sendStatus = sendMessage(input);
        if(sendStatus == null) {
            return true;
        } else {
            return false;
        }
    }

    // Define how the thread runs when a client connects to the server
    @Override
    public void run() {
        String serverMessage = "Choose an option\n1:Enter a person\n2:Delete a person\n3:Query a person\nEnter numbers(1-3)";
        
        while (true) {
            // Sending prompt to client 
            if(sendToClient(serverMessage)) {
                close();
            }
                        
            // Reading result to the previous message
            Data clientInput = readFromClient();
            System.out.println(clientInput.getName());
            System.out.println(clientInput.getAge());
            System.out.println(clientInput.getSSN());
            System.out.println(clientInput.getOperation());
        }
    }

}
