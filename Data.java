import java.io.*;

public class Data implements java.io.Serializable {
    // Defining the data that the client will send to the server 
    public String name; 
    public int age;
    public int ssn; 
    public String operation;

    // Constructor
    public Data(int ssn, String operation) {
        this("", 0, ssn, operation);
    }
    
    public Data(String name, int age, int ssn, String operation) {
        this.name = name;
        this.age = age;
        this.ssn = ssn; 
        this.operation = operation;
    }

    // Getters 
    public String getName() { 
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public int getSSN() {
        return ssn;
    }

    public String getOperation() {
        return operation;
    }
}
