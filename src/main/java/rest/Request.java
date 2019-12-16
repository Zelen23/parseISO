package rest;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public String getSendMessage() {
        return sendMessage;
    }

    private  String sendMessage;

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    private  String rawMessage;

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getDe011() {
        return de011;
    }

    public void setDe011(String de011) {
        this.de011 = de011;
    }

    private String de011;


    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    private HashMap<String, Object> data;
}
