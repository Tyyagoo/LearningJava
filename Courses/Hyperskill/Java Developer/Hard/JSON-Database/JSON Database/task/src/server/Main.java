package server;

import server.commands.Controller;
import server.database.Database;

import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static final String address = "127.0.0.1";
    public static final int port = 8080;
    private static boolean running = true;
    private static final ExecutorService executors = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {

        Database database = new Database("db.json");

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            System.out.println("Server started!");
            serverSocketChannel.configureBlocking(false);
            while (running) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) continue;
                Controller controller = new Controller(database);
                Session session = new Session(socketChannel.socket(), controller);
                executors.submit(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        executors.shutdown();
        running = false;
    }
}
