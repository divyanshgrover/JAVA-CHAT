import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientSwing {
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static JTextArea messageArea;
    private static JTextField inputField;
    private static String lastMessageSent = ""; // Track last sent message

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Client");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        inputField = new JTextField();
        frame.add(inputField, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Send");
        frame.add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });

        // Set up the connection in a separate thread
        new Thread(() -> connectToServer("localhost", 1234)).start();

        frame.setVisible(true);
    }

    private static void connectToServer(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        final String msg = message; // Effectively final
                        SwingUtilities.invokeLater(() -> {
                            if (!msg.equals(lastMessageSent)) { // Only append if it's not the last sent message
                                messageArea.append("Server: " + msg + "\n");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            lastMessageSent = message; // Store the message sent
            writer.println(message);
            SwingUtilities.invokeLater(() -> {
                messageArea.append("You: " + message + "\n");
            });
        }
    }
}
