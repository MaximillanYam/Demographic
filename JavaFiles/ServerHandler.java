import java.net.Socket;
import java.io.*;

public class ServerHandler implements Runnable{
    private Socket serverSocket; 
    private DataInputStream input;
    private DataOutputStream output;
    private ObjectInputStream objectInput;
    private static String fileName = "data.csv";

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
        ServerAPI API = new ServerAPI(fileName);

        while (true) {
            // Reading result to the previous message
            Data clientInput = readFromClient();

            if(clientInput != null) {    
                sendMessage("Recieved data");
            } else {
                sendMessage("Did not recieve data");
            }
            
            if(clientInput == null) {
                // Logic to execute operation based on the operation sent with the file 
                String clientChoice = clientInput.getOperation();

                // Swtich statement to direct the the operation 
                switch(clientChoice) {
                    case "1":
                        sendMessage(API.writeRow(clientInput));
                        break;
                    case "2":
                        API.deleteRow(clientInput);
                        sendMessage("Deleted row");
                        break;
                    case "3":
                        String[] query = API.searchRow(clientInput);
                        sendMessage(String.join(",", query));
                        break;
                }
            }
        }
    }
}
