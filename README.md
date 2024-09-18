# Java Swing Chat Application

This project is a Java-based chat application using sockets for communication between a server and multiple clients. The graphical user interface (GUI) is built using Java Swing.

## Features
- Real-time client-server communication using Java sockets.
- Multiple clients can connect to the server simultaneously.
- A clean and simple GUI for sending and receiving messages.
- Messages from clients and the server are displayed in real time.
- The server broadcasts all messages to connected clients.

## Instructions to Run
1. Clone the repository to your local machine.
2. Ensure you have Java installed. You can download the latest version of Java from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) if necessary.
3. Compile the server and client Java files:
    ```bash
    javac ChatServerSwing.java
    javac ChatClientSwing.java
    ```
4. Run the server first:
    ```bash
    java ChatServerSwing
    ```
5. Then run the client in a separate terminal (you can open multiple clients):
    ```bash
    java ChatClientSwing
    ```

## Project Structure
- `ChatServerSwing.java`: The server-side Java file handling client connections and broadcasting messages.
- `ChatClientSwing.java`: The client-side Java file with the Swing interface for sending/receiving messages.
- `README.md`: Instructions and details about the project.

## Usage
- Run the server first, followed by one or more clients.
- Each client can send messages to the server, which broadcasts them to all other connected clients.
- Messages are displayed in the chat window for each client in real-time.

## License
This project is open-source and free to use.
