Boilerplate, client and server communication 

Goal: User will access the server through the client to make changes to a csv file, editing information such as name, age, and ssn. 

Client.java: Provides the basic needs, connects to the server, communicates via the terminal to enter information about name, age, ssn. Can query the server to find information about someone via ssn. User can stop communication via input from the keyboard

Instance variables:
Socket
DataOutputStream
DataInputStream 

Methods:
ReadMessage: Takes input via the keyboard 
SendMessage: Sends message to the server


Server.java: Provides the functionality of creating and querying users within the csv file. Server will run until hard stopped.

Instance variables:
ServerSocket 
Socket
DataInputStream
DataOutputStream
file (csv)

Methods: 

writeToFile 
readFromFile
