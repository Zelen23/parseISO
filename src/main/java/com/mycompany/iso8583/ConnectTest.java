package com.mycompany.iso8583;

import org.jpos.iso.ISOUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ConnectTest  implements   Runnable{

    /*апи посылает сообшение с параметром
     * если пришел параметр от апи то пускаю его и жду ответа
     * если ответа нет то  нет респонса*/


    public void setResult(String result) {
        this.result = result;
    }

    private  volatile String mess;
    private volatile String result;

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("mess " + this.mess);
            setResult("R: " + this.mess);
            if (this.mess != null) {
                this.mess = null;

            }else{
                result=null;
            }


        }}

    public Socket connectToMux() {


        ServerSocket server = null;
        Socket connect=new Socket();
        try {
            server = new ServerSocket(3111);
            connect=server.accept();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return connect;

            // get resp cut header

        }

    public byte[] sendToMux(Socket conn, byte[] byteMess) throws IOException {

            DataOutputStream out;

            byte[] inmess = new byte[0];


            if(conn.isConnected()) {

                out = new DataOutputStream(new DataOutputStream(conn.getOutputStream()));
                out.write(byteMess);

            }
            return inmess;
    }




}
