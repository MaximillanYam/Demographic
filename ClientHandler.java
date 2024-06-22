import java.util.Scanner;
public class ClientHandler {
    public Client client;

    // Constructor
    public ClientHandler(Client client) {
        this.client = client;
    }

    // Method to communicate with the server 
    public void handleClientConnection() {
        Scanner input = new Scanner(System.in);
        String userInput; 

        while(true) {
            System.out.print("Send a message to the server, 'stop' to stop: ");
            userInput = input.nextLine();

            if(userInput.equals("stop")) {
                break;
            }
            
            client.sendMessage(userInput);

            // Reading server confirmation
            String serverResponse = client.readMessage();
            System.out.println("Server: " + serverResponse);

        }
    }

    public static void main(String[] args) {
        
    }


}
