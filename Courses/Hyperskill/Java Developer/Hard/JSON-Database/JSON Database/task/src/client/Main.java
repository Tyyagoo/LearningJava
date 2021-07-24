package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static final String address = "127.0.0.1";
    public static final int port = 8080;

    @Parameter(names = "-t", description = "Type of the request")
    private String type;
    @Parameter(names = "-k", description = "Key of the value")
    private String key = "";
    @Parameter(names = "-v", description = "Value to save in the database (only on -t set)")
    private String text = "";

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

            Map<String, String> map = new LinkedHashMap<>();
            map.put("type", type);
            if (!"".equals(key)) map.put("key", key);
            if (!"".equals(text)) map.put("value", text);

            Gson gson = new Gson();
            String json = gson.toJson(map);
            output.writeUTF(json);
            System.out.printf("Sent: %s%n", json);
            String received = input.readUTF();
            System.out.printf("Received: %s%n", received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
