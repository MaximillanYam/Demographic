import java.util.Scanner;
public class ClientHandler {    public static void main(String[] args) {
    Client client = new Client("127.0.0.1", 5000);
        client.handleServerCommunication();
    }
}
