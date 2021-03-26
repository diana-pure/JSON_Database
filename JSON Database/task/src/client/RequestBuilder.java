package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RequestBuilder {
    private static final String requestURI = "JSON Database\\task\\src\\client\\data\\";
    @Parameter(names = {"-in"})
    String file;
    @Parameter(names = {"-t"})
    String type;
    @Parameter(names = {"-k"})
    String index;
    @Parameter(names = {"-v"})
    String value;

    public static String build(String[] args) throws FileNotFoundException {
        RequestBuilder builder = new RequestBuilder();
        JCommander.newBuilder().addObject(builder).build().parse(args);

        return builder.file == null ? build(builder.type, builder.index, builder.value) : build(builder.file);
    }

    private static String build(String file) throws FileNotFoundException {
        return new Scanner(new FileInputStream(requestURI + file)).nextLine();
    }

    private static String build(String type, String index, String value) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("type", type);
        switch (type) {
            case "get":
                requestData.put("key", index);
                break;
            case "set":
                requestData.put("key", index);
                requestData.put("value", value);
                break;
            case "delete":
                requestData.put("key", index);
                break;
            case "exit":
                break;
        }

        return new Gson().toJson(requestData);
    }
}
