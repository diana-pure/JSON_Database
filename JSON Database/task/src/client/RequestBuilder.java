package client;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    public static String build(String type, String index, String value) {
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
