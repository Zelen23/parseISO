package rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Simulator")
public class sendMessController {

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    @GetMapping
    public Response showStatus(){
        return new Response(SUCCESS_STATUS, 1);
    }

    @PostMapping("/send")
    public Response send (@RequestParam(value = "mess") String mess,@RequestBody Request request){
        final  Response response;


            String messInResp=request.getSendMessage();
            System.out.println("getMess "+messInResp );
            System.out.println("mess "+mess );

            response = new Response(SUCCESS_STATUS, CODE_SUCCESS);




return response;
    }


}
