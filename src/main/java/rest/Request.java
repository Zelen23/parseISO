package rest;

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
}
