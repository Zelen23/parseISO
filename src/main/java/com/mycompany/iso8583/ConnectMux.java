package com.mycompany.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.mycompany.iso8583.parse.createEcho;
import static com.mycompany.iso8583.parse.createEcho810;

public class ConnectMux implements Runnable  {

    public byte[] getMyParam() {
        return myParam;
    }

    public void setMyParam(byte[] myParam) {
        this.myParam = myParam;
    }

    private volatile byte[] myParam;

    public ConnectMux(byte[] myParam) {
        this.myParam = myParam;
    }

    @Override
    public void run() {

        DataInputStream in;
        DataOutputStream out;
        String header_echo="0036000016010200361111112222220000000000000000000001";
        byte[] inmess = new byte[0];
        String dataString="";

        ServerSocket server= null;
        try {
            server = new ServerSocket(3111);
            Socket conn=server.accept();
            out=new DataOutputStream(new DataOutputStream(conn.getOutputStream()));
            in=new DataInputStream(new DataInputStream(conn.getInputStream()));
            // get resp cut header

            if(conn.isConnected()){

                while (true){

                    Thread.sleep(5000);

                    System.out.println("connected");

                    if(myParam!=null){
                        out.write(myParam);
                        myParam=null;
                    }

                    in.read(inmess);
                    if(inmess.length>1){
                        dataString=ISOUtil.hexString(inmess);
                        String  noHeadermess=dataString.substring(52);

                        //unpack
                        ISOMsg msg=new ISOMsg();
                        msg.setPackager(new ISOIss());
                        try {
                            msg.unpack(ISOUtil.hex2byte(noHeadermess));
                            //check mti
                            if(msg.getMTI()=="0800"){
                                System.out.println("Echo");

                                String send=header_echo+ISOUtil.hexString(createEcho810(msg));
                                System.out.println(send);
                                byte[] c=ISOUtil.hex2byte(send);
                                out.write(c);
                            }

                        } catch (ISOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        System.out.println("response "+ ISOUtil.hexString(inmess));
                    }

                }




            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }






    }
}


