package rest;

import com.mycompany.iso8583.ConstructorMess;

import java.util.HashMap;
import java.util.List;

public class Response {

    private final String status;
    private final Integer code;
    private final HashMap<String, Object> data;

    public Response(String status, Integer code, HashMap<String, Object>  data) {
        this.status = status;
        this.code = code;
        this.data= data;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public HashMap<String, Object>  getData() {
        return data;
    }

}
