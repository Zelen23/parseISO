package rest;


import com.mycompany.iso8583.ConnectMux;
import com.mycompany.iso8583.ConnectTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class Application {


    public static Socket getMainSocket() {
        return mainSocket;
    }

    public static Socket mainSocket;


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
         mainSocket= new ConnectTest().connectToMux();



    }
}

