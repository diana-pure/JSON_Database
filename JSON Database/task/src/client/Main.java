package client;

import java.io.FileNotFoundException;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        try {
            new DatabaseConnector(ADDRESS, PORT).perform(RequestBuilder.build(args));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
