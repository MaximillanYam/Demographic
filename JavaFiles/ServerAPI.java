import java.io.*;
import java.util.ArrayList;

public class ServerAPI {
    private String fileName;

    // Constructor
    public ServerAPI(String fileName) {
        this.fileName = fileName;
    }

    // Method to check if their are duplicate ssn rows in the csv file
    synchronized public String writeRowIfNotExists(Data data) {
        // Refresh the file content before checking
        ArrayList<String[]> fileContent = fileTransform();
        
        // Check if the SSN exists
        for (String[] row : fileContent) {
            if (row[2].equals(data.getSSN())) {
                return "SSN exists";
            }
        }
    
        // If the SSN doesn't exist, write the new row
        try (BufferedWriter write = new BufferedWriter(new FileWriter(fileName, true))) {
            write.write(String.join(",", data.getName(), data.getAge(), data.getSSN()));
            write.newLine();
            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    
        return "Wrote row";
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

        // Immediately read the updated file content
        fileTransform();

        return "Wrote row";
    }

    public ArrayList<String[]> fileTransform() {
        ArrayList<String[]> fileRow = new ArrayList<>();
    
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] row = line.split(",");
                fileRow.add(row);
                System.out.println("Read row: " + String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Total rows read: " + fileRow.size());
        return fileRow;
    }

    public String[] searchRow(Data data) {
        String target = data.getSSN();
        ArrayList<String[]> file = fileTransform();
    
        for (String[] row : file) {
            if (row[2].equals(target)) {
                System.out.println("Found matching SSN: " + String.join(",", row));
                return row;
            }
        }
    
        System.out.println("No matching SSN found for: " + target);
        return null;
    }

    // Will delete a row from the csv file
    public boolean deleteRow(Data data) {
        // Searching for row
        String[] searchedResult = searchRow(data);
        System.out.println("We reached the function delete row");

        // Checking we found a result
        if (searchedResult != null) {
            String line = String.join(",", searchedResult);
            writeExcept(line);
            return true;
        }

        return false;
    }

    public void writeExcept(String line) {
        ArrayList<String[]> file = fileTransform();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] row : file) {
                String newRow = String.join(",", row);
                if (!newRow.equals(line)) {
                    write.write(newRow);
                    write.newLine();
                }
            }
            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
