package client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static final String address = "127.0.0.1";
    public static final int port = 8080;

    public static void main(String[] args) {
        System.out.println("Client started!");
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String content = "Give me a record # 12";
            output.writeUTF(content);
            System.out.printf("Sent: %s%n", content);
            String received = input.readUTF();
            System.out.printf("Received: %s%n", received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
