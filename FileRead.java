// Import 
import java.io.*;

public class FileRead {
    public static void main(String[] args) {

        // Creating text file
        String fileName = "test.txt";

        // Writing to file 
        try(BufferedWriter bufferedW = new BufferedWriter(new FileWriter(fileName))) {
            bufferedW.write("Hello world, this is test file");
            System.out.println("Successfully wrote to the file");
        }
        catch(IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        // Reading from created file 
        try(BufferedReader bufferedR = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = bufferedR.readLine()) != null)  {
                System.out.print(line + " ");
            }
        }
        catch(IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        } 

    }
}
