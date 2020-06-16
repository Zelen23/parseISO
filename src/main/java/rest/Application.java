package rest;


import com.mycompany.iso8583.ConnectTest;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;


@SpringBootApplication
public class Application {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);


    public static ConnectTest getConnect() {
        return connect;
    }

    public  static ConnectTest connect;

    public static void main(String[] args) throws InterruptedException {

       // SpringApplication.run(Application.class, args);
        SpringApplication application = new SpringApplication(Application.class);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        application.setDefaultProperties(properties);
        application.run(args);

      connect= new ConnectTest();
      Thread thread= new Thread(connect);
      thread.start();

      logger.info("*********run fakeSimulator***********");
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

