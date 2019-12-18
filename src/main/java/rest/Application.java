package rest;


import com.mycompany.iso8583.ConnectTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Socket;

@SpringBootApplication
public class Application {

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
             System.out.println("started");
             return connect.checkecho(conn,mess);
         }else{
             System.out.println("disconnect");
             return null;

         }

    }



}

