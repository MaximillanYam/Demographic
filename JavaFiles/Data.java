import java.io.*;

public class Data implements java.io.Serializable {
    // Defining the data that the client will send to the server 
    public String name; 
    public String age;
    public String ssn; 
    public String operation;

    // Constructor
    public Data(String ssn, String operation) {
        this("", "0", ssn, operation);
    }

    public Data(String name, String age, String ssn) {
        this(name, age, ssn, "");
    }
    
    public Data(String name, String age, String ssn, String operation) {
        this.name = name;
        this.age = age;
        this.ssn = ssn; 
        this.operation = operation;
    }

    // Getters 
    public String getName() { 
        return name;
    }
    
    public String getAge() {
        return age;
    }
    
    public String getSSN() {
        return ssn;
    }

    public String getOperation() {
        return operation;
    }
}
