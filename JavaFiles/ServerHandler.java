import java.net.Socket;
import java.io.*;

public class ServerHandler implements Runnable {
    private Socket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private ObjectInputStream objectInput;
    private static String fileName = "data.csv";
    ServerAPI API;

    // Constructor
    public ServerHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;
        API = new ServerAPI(fileName);

        try {
            input = new DataInputStream(serverSocket.getInputStream());
            output = new DataOutputStream(serverSocket.getOutputStream());
            objectInput = new ObjectInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to close socket
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("The server socket failed to close");
            e.printStackTrace();
        }
    }

    // Method to read message from socket
    public String readMessage() {
        try {
            return input.readUTF();
        } catch (IOException e) {
            return null;
        }
    }

    // Method to send message through socket
    public String sendMessage(String message) {
        try {
            output.writeUTF(message);
            return "Sent message";
        } catch (IOException e) {
            return null;
        }
    }

    // Read module
    public Data readFromClient() {
        try {
            return (Data) objectInput.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

    // Send module
    public boolean sendToClient(String input) {
        String sendStatus = sendMessage(input);
        return sendStatus == null;
    }
/* 
    // Function to check if the ssn already exists in the csv file 
    public String ssnExist(Data clientInput) {
        String[] query = API.searchRow(clientInput);
        if(query == null) {
            return "0";
        }
        return "1";
    }
*/
    // Define how the thread runs when a client connects to the server
    @Override
    public void run() {
        while (true) {
            // Reading result to the previous message
            Data clientInput = readFromClient();

            if (clientInput != null) {
                sendMessage("Received data");

                // Logic to execute operation based on the operation sent with the file
                String clientChoice = clientInput.getOperation();

                // Switch statement to direct the operation
                switch (clientChoice) {
                    case "1":
                        String result = API.writeRowIfNotExists(clientInput);
                        if (result.equals("SSN exists")) {
                            sendMessage("1");
                        } else {
                            sendMessage("0");
                        }
                        break;
                    case "2":
                        if(API.deleteRow(clientInput)) {
                            sendMessage("Deleted row");
                        } else {
                            sendMessage("Did not delete row");
                        }
                        
                        break;
                    case "3":
                        String[] query = API.searchRow(clientInput);
                        if(query != null) {                 
                            sendMessage(String.join(",", query));
                        } else {
                            sendMessage("The ssn was invalid");
                        }
                        break;
                    default:
                        sendMessage("Invalid operation");
                        break;
                }
            } else {
                sendMessage("Did not receive data");
            }
        }
    }
}
