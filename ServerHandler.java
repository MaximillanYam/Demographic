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

    public String sendMessage(String message) {
        try { 
            output.writeUTF(message);
            return "Sent message";
        } catch(IOException e) {
            return null; 
        }
    } 

    @Override
    public void run() {
        String serverMessage = "Send another message";
    
        while (true) {
            String readStatus = readMessage();
            if (readStatus == null) {
                System.out.println("Server: Connection lost while reading message");
                break;
            }

            String sendStatus = sendMessage(serverMessage);
            if(sendStatus == null) {
                System.out.println("Server: Connection lost while sending message");
                break;
            }
        }
    }


}
