package rest;

public class Response {

    private final String status;
    private final Integer code;

    public Response(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}
