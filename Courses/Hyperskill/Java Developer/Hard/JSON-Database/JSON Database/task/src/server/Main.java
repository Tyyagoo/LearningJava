package server;

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
                    String response = controller.invoke(received);
                    output.writeUTF(response != null ? response : "{ \"response\": \"OK\" }");
                    if (response == null) break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
