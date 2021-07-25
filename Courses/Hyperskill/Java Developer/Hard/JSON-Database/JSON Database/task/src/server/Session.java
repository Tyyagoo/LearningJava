package server;

import server.commands.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Session implements Runnable {

    private final Socket socket;
    private final Controller controller;

    public Session(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream())){
            String received = input.readUTF();
            String response = controller.invoke(received);
            output.writeUTF(response != null ? response : "{ \"response\": \"OK\" }");
            if (response == null) Main.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}