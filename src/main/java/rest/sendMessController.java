package rest;


import com.mycompany.iso8583.ConfigFile;
import org.jpos.iso.ISOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.mycompany.iso8583.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/Simulator")
public class sendMessController {

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    org.slf4j.Logger logger = LoggerFactory.getLogger(sendMessController.class);


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

                logger.info("getResp "+ISOUtil.hexString(respMux));

            }
        HashMap<String, Object> list = new LinkedHashMap<String, Object>();

        if(respMux.length>1){

            Integer sizeHeader=new parse().headerDynamic(respMux);
            String body=ISOUtil.hexString(respMux).substring(sizeHeader);
            list=new parse().parseToArray(body,false);
            }else{
            list.put("1", respMux);
        }

        response = new Response(SUCCESS_STATUS, CODE_SUCCESS, list);
        return response;
    }

    @PostMapping("/rawdecode")
    public  Response send_r(@RequestParam(value = "mess") String mess,@RequestParam  (defaultValue = "raw") String mode,@RequestBody Request request){
        Response response;

        String raw=request.getRawMessage();
        if(raw!=""){
            byte[] c = ISOUtil.hex2byte(raw);
            String pars=ISOUtil.hexString(c);
            Integer size=new parse().headerDynamic(c);
            Boolean detalmode=false;
            if("detal".equals(mode)){
                detalmode=true;
            }

            HashMap<String, Object> list =new parse().parseToArray(pars.substring(size),detalmode);
            response = new Response(SUCCESS_STATUS, CODE_SUCCESS,list);
        }else{
            response = new Response(ERROR_STATUS, AUTH_FAILURE, null);
        }

        return response;

    }

    @PostMapping("/sendJSON")
    public Response sendJSON (@RequestParam(value = "mess") String mess,@RequestBody Request request){
        final  Response response;
        String rawRequest = null;
        byte []respMux = new byte[0];
        HashMap<String,Object> jsondata=request.getData();
        if(!jsondata.isEmpty()) {

            rawRequest= new parse().createMess(jsondata);
            byte[] c = ISOUtil.hex2byte(rawRequest);
            respMux= new Application().sendMess(c);
            logger.info(rawRequest);
        }
        if(respMux!=null&&respMux.length>1){
            HashMap<String, Object> list =new HashMap<String, Object>();
            Integer sizeHeader=new parse().headerDynamic(respMux);
            HashMap<String, Object> resp = new parse()
                    .parseToArray(ISOUtil.hexString(respMux).substring(sizeHeader),false);
            list.put("rawRequest",rawRequest);
            list.put("data",resp);

            response = new Response(SUCCESS_STATUS, CODE_SUCCESS, list);
        }else{
            HashMap<String,Object> body=new HashMap<>();
            body.put("send: ",rawRequest);
            response = new Response(ERROR_STATUS, AUTH_FAILURE,body );

        }
        return response;
    }

}
