import java.io.*;

public class CsvRead {
    public static void main(String[] args) {
        
        // Creating csv file
        String fileName = "test.csv";
        
        // Writing to file
        try(BufferedWriter bufferedW = new BufferedWriter(new FileWriter(fileName))) {
            // Creating columns in csv file 
            String[] columnNames = {"Name", "Age", "City"};
            bufferedW.write(String.join(",", columnNames));
            bufferedW.newLine();
            
            // Adding a line of data to file
            columnNames = new String[]{"John Doe", "30", "New York"};
            bufferedW.write(String.join(",", columnNames));
            bufferedW.newLine();
            
            System.out.println("Successfully wrote to 'test.csv' file");
        }
        catch(IOException e) {
            System.out.print("An error occured in writing the csv file");
            e.printStackTrace();
        }

        // Reading from created file 
        try(BufferedReader bufferedR = new BufferedReader(new FileReader(fileName))) {
            String line; 

            // Reading the column name first
            line = bufferedR.readLine();
            String[] columnNames = line.split(",");

            // Reading the rest of the data in the file
            while((line = bufferedR.readLine()) != null) {
                // Using the delimiter "," as the cutoff for the data
                String[] data = line.split(",");
            

                for(int i = 0; i < columnNames.length; i++) {
                    System.out.print(columnNames[i] + ":");

                    int j = i;
                    for(; j < data.length; j += 3) {
                        System.out.print(data[j] + " ");
                    }

                    System.out.println();
                }
            }
        }
        catch(IOException e) {
            System.out.print("There was an error reading from the file");
            e.printStackTrace();
        }

    }
}
