import java.net.Socket;
import java.io.*;

public class ServerHandler implements Runnable{
    private Socket serverSocket; 
    private DataInputStream input;
    private DataOutputStream output;

    // Constructor
    public ServerHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;

        try {
            this.input = new DataInputStream(serverSocket.getInputStream());
            this.output = new DataOutputStream(serverSocket.getOutputStream());


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
        try {
            return input.readUTF();
        } catch(IOException e) {
            return null;
        }
    }

    public void sendMessage(String message) {
        try { 
            output.writeUTF(message);
        } catch(IOException e) {
            
        }
    } 

    @Override
    public void run() {
        while (true) {
            String clientMessage = readMessage();
            if (clientMessage == null) {
                // The client has disconnected.
                break;
            }

            // Process the client's message and send a response.
            String response = "Received message: " + clientMessage;
            sendMessage(response);
        }
    }


}
