package server;

import java.io.IOException;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors();
        try {
            Server server = new Server(PORT, ADDRESS, poolSize);
            new Thread(server).start();
            System.out.println("Server started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
