import java.io.*;
import java.util.ArrayList;

public class ServerAPI {
    private String fileName;

    // Constructor
    public ServerAPI(String fileName) {
        this.fileName = fileName;
    }

    // Write method to the file
    public String writeRow(Data data) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(fileName, true))) { // true for append mode
            // Writing to the file the new values that the user entered
            write.write(String.join(",", data.getName(), data.getAge(), data.getSSN()));
            write.newLine();
            write.flush(); // flush after writing
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "Wrote row";
    }

    // Convert file content to an array
    public ArrayList<String[]> fileTransform() {
        ArrayList<String[]> fileRow = new ArrayList<>();

        // Reading line by line into an array list
        // Each array is information about the row
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip headers
            read.readLine();
            while ((line = read.readLine()) != null) {
                fileRow.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileRow;
    }

    // Will search for the ssn
    public String[] searchRow(Data data) {
        String target = data.getSSN();

        // Calling the function to get an array list of array
        // Each array contains the information for each row
        ArrayList<String[]> file = fileTransform();
        int ssn = 2; // 

        // If we find that the ssn matches the target value
        // We will create a new Data that will be sent back to the user
        for (String[] row : file) {
            if (row[ssn].equals(target)) {
                return row;
            }
        }

        return null;
    }

    // Will delete a row from the csv file
    public boolean deleteRow(Data data) {
        // Searching for row
        String[] searchedResult = searchRow(data);
        System.out.println("We reached the function deleterow");

        // Checking we found a result
        if (searchedResult != null) {
            String line = String.join(",", searchedResult);
            writeExcept(line);
            return true;
        }

        return false;
    }

    // Write except the included line
    public void writeExcept(String line) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(fileName))) {
            ArrayList<String[]> file = fileTransform();
            for (String[] row : file) {
                String newRow = String.join(",", row);
                if (!newRow.equals(line)) {
                    System.out.println("Printing out this line: " + line); 
                    write.write(newRow);
                    write.newLine();
                }
            }
            write.flush(); // flush after writing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
