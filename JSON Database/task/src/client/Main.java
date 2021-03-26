package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

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

        String request = RequestBuilder.build(main.type, main.index, main.value);
        new DatabaseConnector(ADDRESS, PORT).perform(request);
    }
}
