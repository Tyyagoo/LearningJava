package server;

import server.console.Console;
import server.console.UserInterface;
import server.database.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final Pattern integerPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");
            try (Socket socket = serverSocket.accept();
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output  = new DataOutputStream(socket.getOutputStream())) {

                String received = input.readUTF();
                Console.print("Received: %s", received);
                Matcher matcher = integerPattern.matcher(received);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group());
                    String response = String.format("A record # %d was sent!", number);
                    output.writeUTF(response);
                    Console.print("Sent: %s", response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        .
            Database database = new Database();
            UserInterface.setDatabase(database);

            while (UserInterface.isRunning()) {
                try {
                    UserInterface.invoke();
                } catch (Exception e) {
                    Console.println(e.getMessage());
                }

            }
        */
    }
}
