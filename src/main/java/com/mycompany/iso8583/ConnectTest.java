package com.mycompany.iso8583;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.System.*;

public class ConnectTest implements Runnable {

    /*апи посылает сообшение с параметром
     * если пришел параметр от апи то пускаю его и жду ответа
     * если ответа нет то  нет респонса*/

    private volatile byte[] mess;

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
        connect = new Socket();
        try {
            server = new ServerSocket(3111);

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
        //Socket conn=connectToMux();
        try {

            try {

                if (!conn.isClosed()) {
                    connect.setSoTimeout(30000);

                    OutputStream oup = conn.getOutputStream();
                    InputStream inp=conn.getInputStream();

                    int red = -1;
                    byte[] buffer = new byte[1024];
                    oup.write(byteMess);
                    oup.flush();

                    while ((red = inp.read(buffer)) > -1) {

                        if (ISOUtil.hexString(buffer).lastIndexOf("000000859822")==-1) {
                            out.println("echo "+ISOUtil.hexString(buffer).substring(0,red));
                        } else {
                            /*Допустим iso-шка  распрасить посмотреть на de011
                            * спереди может быть куча 00000000
                            * пределить длинну header*/
                            redData = new byte[red];
                            System.arraycopy(buffer, 0, redData, 0, red);
                            String respStr= ISOUtil.hexString(redData);
                            System.out.println("Data From Mux :" +respStr);

                            ISOMsg request= new parse().parsers(ISOUtil.hexString(byteMess).substring(52));

                            Integer sizeHeader=new parse().headerDynamic(ISOUtil.hex2byte(respStr),"000000859822");
                            ISOMsg resp=  new parse().parsers(respStr.substring(sizeHeader));

                            String req_messID=request.getString(7)+request.getString(11);
                            String resp_messID=resp.getString(7)+resp.getString(11);

                            out.println("mess 1 "+req_messID+" mess 2 "+resp_messID);

                            if(req_messID.equals(resp_messID)){

                                out.println("getMess");
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

        return redData;
    }



}
