package com.mycompany.iso8583;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectTest implements Runnable {

    ConfigFile config=new ConfigFile();


    public Socket getConnect() {
        return connect;
    }

    private Socket connect;
    org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectTest.class.getName());

    @Override
    public void run() {

        ServerSocket server = null;
        connect = new Socket();
        try {
            server = new ServerSocket(config.getIntParams("connect.port"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // while (true) {
        try {
            connect = server.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //  }

    }

    public byte[] checkecho(Socket conn, byte[] byteMess) {

        byte[] redData = new byte[0];

        try {

            try {

                if (!conn.isClosed()) {
                    connect.setSoTimeout(config.getIntParams("connect.timeout"));

                    OutputStream oup = conn.getOutputStream();
                    InputStream inp=conn.getInputStream();

                    int red = -1;
                    byte[] buffer = new byte[1024];
                    oup.write(byteMess);
                    oup.flush();

                    while ((red = inp.read(buffer)) > -1) {

                        if (ISOUtil.hexString(buffer).lastIndexOf(
                            config.getParams("header.const"))==-1) {
                            logger.info("echo "+ISOUtil.hexString(buffer).substring(0,red));
                        } else {
                            redData = new byte[red];
                            System.arraycopy(buffer, 0, redData, 0, red);
                            String respStr  = ISOUtil.hexString(redData);
                            logger.info("Data From Mux :" +respStr);

                            ISOMsg request= new parse().parsers(ISOUtil.hexString(byteMess).substring(52));

                            Integer sizeHeader=new parse().headerDynamic(ISOUtil.hex2byte(respStr));
                            ISOMsg resp=  new parse().parsers(respStr.substring(sizeHeader));

                            String req_messID=createMessID(request);
                            String resp_messID=createMessID(resp);

                            logger.info("mess req "+req_messID+" mess resp "+resp_messID);

                            if(req_messID.equals(resp_messID)){
                            logger.info("getMess");
                             return redData;
                            }

                        }
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String createMessID(ISOMsg isoMsg){
        return isoMsg.getString(7)+isoMsg.getString(11);
    }


}
