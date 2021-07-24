package server;

import exceptions.InvalidDatabaseAccessException;
import server.commands.Controller;
import server.database.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {

        Database database = new Database();
        Controller controller = new Controller(database);

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output  = new DataOutputStream(socket.getOutputStream())) {

                    String received = input.readUTF();
                    String response = "404";
                    try {
                        response = controller.invoke(received);
                        if (response == null) break;
                    } catch (InvalidDatabaseAccessException e) {
                        response = e.getMessage();
                    } finally {
                        output.writeUTF(response != null ? response : "OK");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
