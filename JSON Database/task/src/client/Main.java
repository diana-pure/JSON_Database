package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    @Parameter(names = {"-t"})
    String type;
    @Parameter(names = {"-i"})
    int index;
    @Parameter(names = {"-m"})
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
            String messageToSend = "";

            switch (main.type) {
                case "get":
                    messageToSend = main.type + " " + main.index;
                    break;
                case "set":
                    messageToSend = main.type + " " + main.index + " " + main.value;
                    break;
                case "delete":
                    messageToSend = main.type + " " + main.index;
                    break;
                case "exit":
                    messageToSend = main.type;
                    break;
            }

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
