Boilerplate, client and server communication 

Goal: User will access the server through the client to make changes to a csv file, editing information such as name, age, and ssn. 

The main four code files:
client 
clienthandler 
server
serverhandler 

The client class file contains the logic for the creating a client socket that is then implemented in clienthandler to keep the client class file clean. The server is then defined and started in server class file. The server handler class is to help multi threading where we define a socket much like the client but instead will intialized by the server accept() method. The run method will then intialize the means of communication for the client and server