package rest;


import com.mycompany.iso8583.ConnectTest;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.Socket;

@SpringBootApplication
public class Application {
    org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    public static ConnectTest getConnect() {
        return connect;
    }

     public  static ConnectTest connect;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
        connect= new ConnectTest();
      Thread thread= new Thread(connect);
      thread.start();

    }

    public byte[] sendMess(byte[]mess){
         Socket conn=connect.getConnect();
         if(conn.isConnected()){
             logger.info("started");
             return connect.checkecho(conn,mess);
         }else{
             logger.info("disconnect");
             return null;

         }

    }



}

