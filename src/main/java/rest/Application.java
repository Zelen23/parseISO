package rest;


import com.mycompany.iso8583.ConnectMux;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mycompany.iso8583.parse;

@SpringBootApplication
public class Application {

     public static ConnectMux runnableMux;
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);

      //parse.server();

        runnableMux=new ConnectMux(null);
        new Thread(runnableMux).start();


    }

    public void upd(byte[] ss){

        runnableMux.setMyParam(ss);
    }
}

