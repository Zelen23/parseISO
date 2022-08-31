package com.mycompany.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.slf4j.Logger;
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
    Logger logger = LoggerFactory.getLogger(ConnectTest.class);

    @Override
    public void run() {

        ServerSocket server = null;
        connect = new Socket();
        try {
            server = new ServerSocket(config.getIntParams("connect.port"));

        } catch (IOException e) {
            e.printStackTrace();
        }

         while (true) {
        try {

            connect = server.accept();
            logger.info("conn ");
            //getEco();


        } catch (IOException e) {
            e.printStackTrace();
        }
         }

    }

    public byte[] forMuxMess(Socket conn, byte[] byteMess) {

        byte[] redData = new byte[0];
        try {
            try {
                if (!conn.isClosed()) {
                    connect.setSoTimeout(config.getIntParams("connect.timeout"));

                    OutputStream oup = conn.getOutputStream();
                    InputStream inp= conn.getInputStream();
                    inp.read();
                    int red = -1;
                    byte[] buffer = new byte[1024];
                    oup.write(byteMess);
                    oup.flush();
                    int counter=0;

                    while ((red = inp.read(buffer)) > -1) {
// если в буффере  не нашел параметры заголовка то значит это эхо
                        if (ISOUtil.hexString(buffer).lastIndexOf(
                            config.getParams("header.const"))==-1) {

                            logger.info("echo "+ISOUtil.hexString(buffer).substring(0,red));
                            counter++;
                            logger.info("counter "+counter);
                            if (counter>=config.getIntParams("echo.counter")){
                                logger.info("CMS no responsed ");
                                inp.reset();


                                break;
                            }
                        } else {
                            counter=0;
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
    public void getEco() throws IOException, ISOException {

        InputStream inp = null;
        try {
            inp = connect.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream oup = connect.getOutputStream();
        int red = -1;
        byte[] buffer = new byte[1024];
        while ((red = inp.read(buffer)) > -1) {

            String resiveMess=ISOUtil.hexString(buffer,0,red);
            logger.info("resiveMess "+resiveMess);

            if (resiveMess.equals("00000000")){
                oup.write(ISOUtil.hex2byte("00000000"));


            }else{
                ISOMsg resp = new parse().parsers(resiveMess.substring(52));

                if(resp.getMTI().equals("0800")){


                    resp.setMTI("0810");
                    byte[] packbody = resp.pack();
                    int size = packbody.length + 22;
                    String hexSize = String.format("%04X", size);

                    String headConst = config.getParams("header.const");
                    String header = hexSize + "0000160102" + hexSize + headConst + "0000000000000000000000";

                    String r0810 = header + ISOUtil.hexString(packbody);
                    logger.info("echo " + r0810);
                    try {
                        oup.write(ISOUtil.hex2byte(r0810));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        }
    }
    private String createMessID(ISOMsg isoMsg){
        return isoMsg.getString(7)+isoMsg.getString(11);
    }


}
