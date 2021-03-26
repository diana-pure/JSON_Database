package server.database;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    private String response;
    private String details;

    public Response(String response, String details) {
        this.response = response;
        this.details = details;
    }

    public String toJsonString() {
        Map<String, String> r = new LinkedHashMap<>();
        if (response.equals("ERROR")) {
            r.put("response", "ERROR");
            r.put("reason", details);
        } else if (response.equals("OK")) {
            r.put("response", "OK");
            if (!details.isEmpty()) {
                r.put("value", details);
            }
        } else {
            r.put("response", "OK");
            r.put("value", details);
        }
        return new Gson().toJson(r);
    }
}
