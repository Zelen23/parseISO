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
            out.println("mess " + this.mess);
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

public byte[] sendTo2(Socket conn, byte[] byteMess){

    DataInputStream in;
    DataOutputStream out;
    byte[] inmess=new byte[100];
    String dataString="";

    while (true){

        try {

            out=new DataOutputStream(new DataOutputStream(conn.getOutputStream()));
            in=new DataInputStream(new DataInputStream(conn.getInputStream()));


            out.write(byteMess);
            out.flush();
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


                } catch (ISOException e) {
                    e.printStackTrace();
                }

            }

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


return inmess;



    }
}


}
