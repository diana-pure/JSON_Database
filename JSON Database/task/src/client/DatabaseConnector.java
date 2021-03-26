package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class DatabaseConnector {
    private String address;
    private int port;

    public DatabaseConnector(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void perform(String request) {

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            print("Client started!");
            output.writeUTF(request);
            print("Sent: " + request);

            request = input.readUTF();
            print("Received: " + request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }
}
