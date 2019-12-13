package rest;

import com.mycompany.iso8583.ConnectTest;
import com.mycompany.iso8583.ConstructorMess;
import org.jpos.iso.ISOUtil;
import org.springframework.web.bind.annotation.*;
import com.mycompany.iso8583.parse;
import com.mycompany.iso8583.ConnectMux;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/Simulator")
public class sendMessController {

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    @GetMapping
    public Response showStatus(){
        return new Response(SUCCESS_STATUS, 1, null);
    }

    @PostMapping("/send")
    public Response send (@RequestParam(value = "mess") String mess,@RequestBody Request request){
        final  Response response;
        byte []respMux = new byte[0];
        String resss="";

            String messInResp=request.getSendMessage();
            String de011=request.getDe011();
            if(messInResp!="") {

                byte[] c = ISOUtil.hex2byte(messInResp);
                byte[] d=new parse().updateRawMess(c,de011);
                respMux= new Application().sendMess(d);

                System.out.println("getResp "+ISOUtil.hexString(respMux));

            }
        HashMap<String, Object> list = new LinkedHashMap<String, Object>();
           // list=new parse().parseToArray(ISOUtil.hexString(respMux).substring(52));
        list.put("1", ISOUtil.hexString(respMux));
       //  давать респонс когда получен результат
        response = new Response(SUCCESS_STATUS, CODE_SUCCESS, list);
        return response;
    }


    @PostMapping("/rawdecode")
    public  Response send_r(@RequestParam(value = "mess") String mess,@RequestBody Request request){
        Response response;



        String raw=request.getRawMessage();
        String de011=request.getDe011();

        if(raw!=""){

            byte[] c = ISOUtil.hex2byte(raw);
            byte[] d=new parse().updateRawMess(c,de011);
            String pars=ISOUtil.hexString(d);

            HashMap<String, Object> list =new parse().parseToArray(pars.substring(52));
            response = new Response(SUCCESS_STATUS, CODE_SUCCESS,list);
        }else{
            response = new Response(ERROR_STATUS, AUTH_FAILURE, null);
        }






        return response;

    }


}
