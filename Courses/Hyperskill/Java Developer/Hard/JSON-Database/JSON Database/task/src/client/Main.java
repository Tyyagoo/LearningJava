package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static final String address = "127.0.0.1";
    public static final int port = 8080;

    @Parameter(names = "-t", description = "Type of the request")
    private String type;
    @Parameter(names = "-i", description = "Index of the cell")
    private Integer index;
    @Parameter(names = "-m", description = "Value to save in the database (only on -t set)")
    private String text;

    public static void main(String[] argv) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.run();
    }

    public void run() {
        System.out.println("Client started!");
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            String content;
            switch (type) {
                case "get":
                case "delete":
                    content = String.format("%s %d", type, index);
                    break;
                case "set":
                    content = String.format("%s %d %s", type, index, text);
                    break;
                case "exit":
                    content = String.format("%s", type);
                    break;
                default:
                    content = "";
                    System.exit(-1);
            }
            output.writeUTF(content);
            System.out.printf("Sent: %s%n", content);
            String received = input.readUTF();
            System.out.printf("Received: %s%n", received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
