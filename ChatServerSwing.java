import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class ChatServerSwing {
    private static final int PORT = 1234;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static JTextArea messageArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Server");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        frame.setVisible(true);

        // Start the server in a separate thread
        new Thread(ChatServerSwing::startServer).start();
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            SwingUtilities.invokeLater(() -> messageArea.append("Server started on port " + PORT + "\n"));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                SwingUtilities.invokeLater(() -> messageArea.append("New client connected: " + clientSocket.getInetAddress() + "\n"));

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer);

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                new Thread(() -> {
                    try {
                        String message;
                        while ((message = reader.readLine()) != null) {
                            final String msg = message; // Effectively final
                            SwingUtilities.invokeLater(() -> {
                                messageArea.append("Client: " + msg + "\n");
                                broadcastMessage(msg);
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        clientWriters.remove(writer);
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }
}
