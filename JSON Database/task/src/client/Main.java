package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    @Parameter(names = {"-t"})
    String type;
    @Parameter(names = {"-k"})
    String index;
    @Parameter(names = {"-v"})
    String value;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        try (Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT)) {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            print("Client started!");
            Map<String, String> requestData = new HashMap<>();
            requestData.put("type", main.type );
            switch (main.type) {
                case "get":
                    requestData.put("key",  main.index);
                    break;
                case "set":
                    requestData.put("key",  main.index);
                    requestData.put("value", main.value);
                    break;
                case "delete":
                    requestData.put("key",  main.index);
                    break;
                case "exit":
                    break;
            }

            String messageToSend = new Gson().toJson(requestData);
            output.writeUTF(messageToSend);
            print("Sent: " + messageToSend);

            messageToSend = input.readUTF();
            print("Received: " + messageToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }
}
