package com.mycompany.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class ConnectTest  implements   Runnable{

    /*апи посылает сообшение с параметром
     * если пришел параметр от апи то пускаю его и жду ответа
     * если ответа нет то  нет респонса*/

    private  volatile byte[] mess;
    public void setMess(byte[] mess) {
        this.mess = mess;
    }


    public Socket getConnect() {
        return connect;
    }

    private Socket connect;




    @Override
    public void run() {

        ServerSocket server = null;
         connect=new Socket();
        try {
            server = new ServerSocket(3111);

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] redData = new byte[0];
        out.println("srarted");

        while (true) {

            try {
                connect = server.accept();
                if(connect.getInputStream().read()==-1){
                    out.println("WTF");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

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

    public byte[] checkecho( Socket conn,byte [] byteMess) {

        byte[] redData = new byte[0];
        //Socket conn=connectToMux();
        try {

            try {

                if(conn.isConnected()){

                    OutputStream oup = conn.getOutputStream();
                    InputStream inp=conn.getInputStream();
                    int red = -1;
                    byte[] buffer = new byte[5 * 1024]; // a read buffer of 5KiB
                    oup.write(byteMess);

                    while ((red = inp.read(buffer)) > -1) {

                        redData = new byte[red];
                        System.arraycopy(buffer, 0, redData, 0, red);

                        System.out.println("Data From Client2 :" + ISOUtil.hexString(redData));

                    return redData;
                    }
                }else{

                    System.out.println("disconect");
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

return redData;
    }


    public byte[] sendToMux(Socket conn, byte[] byteMess) throws IOException {

        DataOutputStream out;
        BufferedReader reader;
        DataInputStream in;
        String resp="";

        byte[] inmess = new byte[0];


        if(conn.isConnected()) {
            out = new DataOutputStream(new DataOutputStream(conn.getOutputStream()));
            in= new DataInputStream(new DataInputStream(conn.getInputStream()));
            reader= new BufferedReader( new InputStreamReader(conn.getInputStream()));
            //send mess
            out.write(byteMess);
            resp =reader.readLine();
        }

        // reader.readLine();


        return resp.getBytes();
    }





}
