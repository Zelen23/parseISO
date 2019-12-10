/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jpos.iso.*;


/**
 *
 * @author adolf
 */
public class parse {

    static Integer de011=100001;

    /*
public static void main(String[] args) {

    //String message =args[0].toString();
    //parse(message);
    //String message= "00360000160102003611111122222200000000000000000000010800C22000000000000004000000000000000502044012091509451000010999";
    //checkREsp(message);
    server();



    }

    *
     */

public static ISOMsg parse(String message){
    ISOMsg msg = new ISOMsg();

    try {


        byte[] c=ISOUtil.hex2byte(message);

        // Create ISO Message

        //GenericPackager packager = new GenericPackager("src/resources/grab.xml");

        //msg.setPackager(packager);
        msg.setPackager(new ISOIss());
        // msg.unpack(packmessSmoll());
        msg.unpack(c);

        String cat = msg.getMTI();
        for (int i=1;i<=msg.getMaxField();i++) {
            if (msg.hasField(i)) {
                cat = cat +"|"+msg.getString(i);
                System.out.println("    Field-"+i+" : "+msg.getString(i));
            }

        }
        System.out.print(cat);
    } catch (ISOException ex) {
        Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
    }

    return msg;
}
public static byte[] packmess() {
    

       String message1="0100763C669128E0BA16104785299000235458010000000000000011000000000011120416382300077216382312042005601108400700000100C4F0F0F0F0F0F0F0F00B0123456789012504785299000235458D20052011372500000201F9F3F3F8F1F6F0F0F0F7F7F2C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400840DAE7386BEA0B25162001010100000000660100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A00000000310100128080000000000000000058000000002";
       String mess110= "0110722020810AF0820210478529900023545801000000000000001112041638230007720840000B012345678901F9F3F3F8F1F6F0F0F0F7F7F2F5F5C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20940404040F2404040F20840140100119F3602000D910A6B569898F80693613535058000000002";                                       
       String mes=     "0100763C669128E1BA16104785298326580456010000000000000111000000025000120617260000077317260012062209601108409010000100C4F0F0F0F0F0F0F0F00B0123456789012504785298326580456D22092011322100000647F9F3F4F0F1F7F0F0F0F7F7F3C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E2045CE9E9E90840064378DBF325BE0E65AA20010101000000006201005F9F3303204000950580000100009F37049BADBCAB9F100706010A03A0B0009F260851CBBEF70FDA108B9F360200FF820200009C01009F1A0209789A031909189F02060000000123005F2A0209789F03060000000000008407A00000000310100122080000000000000000058000000002";
       String pin=     "0100763C669128E0BA16104785298326580456702000000000000000000000000000120617302700077417302712062209601108400510000100C4F0F0F0F0F0F0F0F00B0123456789012504785298326580456D22092011322100000647F9F3F4F0F1F7F0F0F0F7F7F4C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20840084078DBF325BE0E65AA20010101000000006C0100699F3303204000950580000100009F37049BADBCAB9F100706010A03A0B0009F26089C9D5C389366C0E89F360200FF820200009C01709F1A0209789A031909199F02060000000123005F2A0209789F0306000000000000C008C290FE4F74552A778407A00000000310100425000010080000000000000000058000000002";
       String mess120="0120F63C66910AE0A8160000000000000004104785298326580456000000000000000177000000003000120813390200077913390212082209599906430100000108C4F0F0F0F0F0F0F0F00B012345678901F9F3F4F2F1F3F0F0F0F7F7F9F0F0E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E206430643200101010000000004050008100800000000000000000580000000020E0040000000000000F1F1F0F0F3F1";
       String mess130 ="0130722020810AE0800210478529832658045600000000000000017712081339020007790643080B012345678901F9F3F4F2F1F3F0F0F0F7F7F9F0F0E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20643058000000002";
       String mess430 ="0420F63C649108E0A81E000000400000000010478529832658045600000000000000017700000000300012081339100007791339021208220959990643010008C3F0F0F0F0F0F0F0F00B012345678901F9F3F4F2F1F3F0F0F0F7F7F9E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400643200101010000000004050008101200000000000000000000000000000000008808000000000000000007A0000000022504012000000000000000001234567890100000000000";
      
       /*
       
       Received de061:000000000000000000000000000000000088
Received de062:0000000000000000
Received de063:A0000000022504
Received de090:012000000000000000001234567890100000000000*/
       byte[] data = null;
       
       //String message=ISOUtil.hexString(packmessSmoll());
       byte[] packed=packmessSmoll();
       String string=ISOUtil.hexString(packed);
   
        byte[] c=ISOUtil.hex2byte(mess430);
        
        //System.err.println(ISOUtil.e));
      
        byte[] b =string.getBytes(StandardCharsets.UTF_8);
      

       System.out.println("*2 " +c.length);
        
  return  c;
      
      }
public static byte[] packmessSmoll() {
          
           String message="";
           byte[] data = null;
    try {
        
       // GenericPackager packager = new GenericPackager("src/resources/grab.xml");
        
        ISOMsg isoMsg = new ISOMsg();
        //isoMsg.setPackager(packager);
        isoMsg.setPackager(new ISOIss());
        isoMsg.setMTI("0100");     
        isoMsg.set(2, "4785299000235458");
        isoMsg.set(3, "010000");
        isoMsg.set(4, "000000000011");
        isoMsg.set(6, "000000000011");
        isoMsg.set(7, "1204163823");        
        isoMsg.set(11, "000772");
        isoMsg.set(12, "163823"); 
        isoMsg.set(13, "1204");
        isoMsg.set(14, "2005");
        isoMsg.set(18, "6011");
        isoMsg.set(19, "840");
        isoMsg.set(22, "700");
        isoMsg.set(23, "001");
        isoMsg.set(25, "00");
        isoMsg.set(28, "D00000000");
        isoMsg.set(32, "12345678901");
        //isoMsg.set(35, "4785299000235458D20052011372500000201");
        isoMsg.set(35,"4785299000235458=20052011372500000201" );
      
        isoMsg.set(37, "933816000772");   
        isoMsg.set(41, "ATM01");
        isoMsg.set(42, "CARD ACCEPTOR  ");
        isoMsg.set(43, "ACQUIRER NAME            CITY NAME   US");  
        isoMsg.set(49, "840");
        isoMsg.set(51, "840");
        isoMsg.set(52, "DAE7386BEA0B2516");
        isoMsg.set(53, "2001010100000000");
        isoMsg.set(55, "0100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A0000000031010");
        isoMsg.set(60, "28");
        isoMsg.set(62, "0000000000000000");
        isoMsg.set(63, "8000000002");


        // Get and print the output result
        data = isoMsg.pack();
         message=ISOUtil.hexString(data);
        System.out.println("RESULT1 : " + message);
    } catch (Exception ex) {
        Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("*1 "+data.length);
        return  data;
  
      
      }

public static byte[] createEcho(){
    byte[] data=null;
    String header_echo="0036000016010200361111112222220000000000000000000001";
    SimpleDateFormat sdf= new SimpleDateFormat("MMddHHmmss");
    String de007=sdf.format(new Date());
    de011=de011+1;
    ISOMsg msg=new ISOMsg();
    msg.setPackager(new ISOIss());
          try {
              msg.setMTI("0800");
              msg.set(2,"02044");
              msg.set(7,de007);
              msg.set(11, String.valueOf(de011));
              msg.set(70,"999");


              // Get and print the output result
             data = msg.pack();
              String message = ISOUtil.hexString(data);
             // System.out.println("RESULT_0800 : " +message);


          } catch (ISOException e) {
              e.printStackTrace();
          }

return data;
      }
public static byte[] createEcho810(ISOMsg isoMsg){
        byte[] data=null;
        String header_echo="0036000016010200361111112222220000000000000000000001";
    //String header_echo="003E0000160102003E0000008598220000000000000000000000";
        SimpleDateFormat sdf= new SimpleDateFormat("MMddHHmmss");
        String de007=sdf.format(new Date());
        //de011=de011+1;
    try {
        isoMsg.setMTI("0810");

        // Get and print the output result
        data = isoMsg.pack();
        String message = ISOUtil.hexString(data);
        System.out.println("RESULT_0810 : " + header_echo+message);

    } catch (ISOException e) {
        e.printStackTrace();
    }


        return data;
    }

public static void checkREsp(String resp){

    String mes=resp.substring(52);
    parse(mes);


}

static void server(){
        DataInputStream in;
        DataOutputStream out;
        String header_echo="0036000016010200361111112222220000000000000000000001";
        byte[] inmess=new byte[66];
        String dataString="";

                try {
                    ServerSocket server=new ServerSocket(3111);
                    Socket conn=server.accept();
                    out=new DataOutputStream(new DataOutputStream(conn.getOutputStream()));


                    while (true){
                        try {
                            String send=header_echo+ISOUtil.hexString(createEcho());
                            System.out.println(send);
                            byte[] c=ISOUtil.hex2byte(send);
                            Thread.sleep(5000);
                            out.write(c);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }




                } catch (IOException e) {
                    e.printStackTrace();
                }



    }






    
}
