package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static final String address = "127.0.0.1";
    public static final int port = 8080;
    private static final String FILEPATH_TEST_ENVIRONMENT = System.getProperty("user.dir") + "/src/client/data/";
    private static final String FILEPATH_LOCAL_ENVIRONMENT = System.getProperty("user.dir") + "/JSON Database/task/src/client/data/";
    private static final String filepath = FILEPATH_TEST_ENVIRONMENT;

    @Parameter(names = "-t", description = "Type of the request")
    private String type;
    @Parameter(names = "-k", description = "Key of the value")
    private String key = "";
    @Parameter(names = "-v", description = "Value to save in the database (only on -t set)")
    private String text = "";
    @Parameter(names = "-in", description = "File with processable request")
    private String inputFileName = "";

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
            Gson gson = new Gson();
            String json = "";
            if ("".equals(inputFileName)) {
                map.put("type", type);
                if (!"".equals(key)) map.put("key", key);
                if (!"".equals(text)) map.put("value", text);

                json = gson.toJson(map);
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(filepath + inputFileName))) {
                    JsonElement data = JsonParser.parseReader(reader);
                    json = gson.toJson(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if ("".equals(json)) return;
            output.writeUTF(json);
            System.out.printf("Sent: %s%n", json);
            String received = input.readUTF();
            System.out.printf("Received: %s%n", received);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
