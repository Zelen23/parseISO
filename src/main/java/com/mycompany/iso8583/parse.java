/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;

//https://neapay.com/online-tools/emv-tlv-decoder.html
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jpos.iso.*;
import org.jpos.tlv.TLVList;
import org.jpos.tlv.TLVMsg;
import org.slf4j.LoggerFactory;


/**
 *
 * @author adolf
 */

public class parse {

    ConfigFile config=new ConfigFile();
    org.slf4j.Logger logger = LoggerFactory.getLogger(parse.class);


    public byte[] updateRawMess(byte[] raw, String de011) {
        // отхерачит 52 символа
        String mess = ISOUtil.hexString(raw).substring(52);
        String header_echo = ISOUtil.hexString(raw).substring(0, 52);
        byte[] updr = new byte[0];

        ISOMsg msg = new ISOMsg();
        byte[] c = ISOUtil.hex2byte(mess);
        msg.setPackager(new ISOIss());
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String de007 = sdf.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
        String de012 = sdf2.format(new Date());

        try {

            msg.unpack(c);
            String de037 = msg.getString(37).substring(0, 6);
            msg.set(7, de007);//1213153223
            msg.set(11, de011);//000927
            msg.set(12, de012); //153223
            msg.set(37, de037 + de011);//934715000927

            byte[] data = msg.pack();
            String message = ISOUtil.hexString(data);
            logger.info("updateRawMess " + header_echo + message);

            updr = ISOUtil.hex2byte(header_echo + message);


        } catch (ISOException e) {
            e.printStackTrace();
        }

        return updr;
    }

    public HashMap<String, Object> parseToArray(String rawMessage,Boolean detalmode) {

        HashMap<String, Object> list = new LinkedHashMap<String, Object>();
        ISOMsg msg = new ISOMsg();

        try {

            byte[] c = ISOUtil.hex2byte(rawMessage);
            msg.setPackager(new ISOIss());
            msg.unpack(c);

            String cat = msg.getMTI();
            list.put("mti", cat);
            for (int i = 1; i <= msg.getMaxField(); i++) {
                if (msg.hasField(i)) {
                    list.put("de" + String.format("%03d", i), msg.getString(i));
                    if (i == 126) {
                        ISOComponent comp = msg.getComponent(126);
                        ISOIss.F126Packager pp = new ISOIss.F126Packager();
                        HashMap<String, String> f126 = new HashMap<>();
                        ISOMsg subIso = new ISOMsg();
                        subIso.setPackager(pp);
                        subIso.unpack(comp.pack());
                        for (int j = 1; j <= subIso.getMaxField(); j++) {

                            if (subIso.hasField(j)) {
                                f126.put("" + j, subIso.getString(j));
                            }
                        }
                        list.put("de" + String.format("%03d", i), f126);
                    }
                    if(i==104){
                        TLVList tlvData = new TLVList();

                        String fff = msg.getString(i);
                        //tlvData.unpack(ISOUtil.hex2byte(fff));
                    }
                    if(i==55){
                        byte[] fld = msg.getBytes(55);
                        byte[] fld2 =Arrays.copyOfRange( fld,  3, fld.length);
                        TLVList tlvData = new TLVList();

                        tlvData.unpack(fld2);
                        HashMap<String,String> f55=new HashMap<>();
                        for (TLVMsg tLVMsg : tlvData.getTags()) {
                            f55.put( Integer.toHexString(tLVMsg.getTag()),ISOUtil.hexString(tLVMsg.getValue()));
                        }
                        if(detalmode){
                            list.put("de" + String.format("%03d", i), f55);
                        }else{
                            list.put("de" + String.format("%03d", i),msg.getString(i));
                        }

                    }
                    if(i==123){
                        if(detalmode) {
                            HashMap<String,String>tlvObj=split(msg.getString(i));
                            HashMap<String,HashMap<String,String>> objTLV=new HashMap<>();

                            for (String obj:tlvObj.keySet()){
                                TLVList tlvData = new TLVList();

                                tlvData.unpack(ISOUtil.hex2byte(tlvObj.get(obj)));
                                HashMap<String,String> f123=new HashMap<>();
                                for (TLVMsg tLVMsg : tlvData.getTags()) {
                                    f123.put( Integer.toHexString(tLVMsg.getTag()),ISOUtil.hexString(tLVMsg.getValue()));
                                }
                                objTLV.put(obj,f123);
                            }
                            list.put("de" + String.format("%03d", i),objTLV);

                        }else{
                            list.put("de" + String.format("%03d", i), msg.getString(i));
                        }

                    }
                }

            }

        } catch (ISOException ex) {
            Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
        }


        return list;
    }
    public Integer headerDynamic(byte[] rawMess) {

        String splitter= config.getParams("header.const");

        String[] x = ISOUtil.hexString(rawMess).split(splitter);
        Integer size=x[0].length()+splitter.length()+22;
        logger.info("HeaderSize "+size);

        return size;

    }
    public  String createMess(HashMap<String, Object> mess) {
        //приходит json
        // считаю поля
        // создаю ISO mess  в цикле значение добавляю в mess  если значение * то поздаю по  наблоизатору
        ISOMsg msg = new ISOMsg();
        msg.setPackager(new ISOIss());
        String hexSize = null;

        byte[] packbody = null;
        for (Map.Entry<String, Object> obj : mess.entrySet()) {

            if (obj.getKey() == "mti") {
                try {
                    msg.setMTI(obj.getValue().toString());
                } catch (ISOException e) {
                    e.printStackTrace();
                }
            } else {
                int key = parseKey(obj.getKey());

                if (obj.getValue().toString().equals("*")) {
                    msg.set(key, autogenerate(key));
                } else {

                    switch (key){
                        case 126:
                            HashMap<String, Object> subf= (HashMap<String, Object>) obj.getValue();
                            try {
                                msg.set(packMSG126(subf));

                            } catch (ISOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 55:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String, Object> tlv= (HashMap<String, Object>) obj.getValue();
                                msg.set(55,packMSG55(tlv));
                                break;
                            }

                        default:
                            msg.set(key, obj.getValue().toString());
                    }

                }

            }

        }

        try {
            packbody = msg.pack();

        } catch (ISOException e) {
            e.printStackTrace();
        }
        int size = packbody.length + 22;
        hexSize = String.format("%04X", size);


        String headConst=config.getParams("header.const");
        String header = hexSize + "0000160102" + hexSize +headConst +"0000000000000000000000";


        return header + ISOUtil.hexString(packbody);
    }
    private String packMSG55(HashMap<String, Object> tlv) {

        ISOMsg msg55=new ISOMsg(55);
        TLVList tlvData = new TLVList();
        for (Map.Entry<String, Object> obj : tlv.entrySet()){
             Integer tag= ISOUtil.byte2int(ISOUtil.hex2byte(obj.getKey()));
            tlvData.append(tag,ISOUtil.hex2byte(obj.getValue().toString()));
        }

/*        tlvData.append(0x9F1A,ISOUtil.hex2byte("0978"));
*/

        byte[] flfpack = tlvData.pack();
        String hex55=ISOUtil.hexString(flfpack);
        String hexSize=String.format("%04X", hex55.length()/2);
        return "01"+hexSize+hex55;
    }
    public ISOMsg packMSG126(Map<String, Object> obj) {

        ISOMsg msg126 = new ISOMsg(126);
        for (Map.Entry<String, Object> obj2 : obj.entrySet()) {

            ISOField subfld = new ISOField(Integer.parseInt(obj2.getKey()), obj2.getValue().toString());
            try {
                msg126.set(subfld);

            } catch (ISOException e) {
                e.printStackTrace();
            }
        }


        return msg126;
    }
    public Integer parseKey(String keyFromHashMap) {

        Integer key = Integer.parseInt(keyFromHashMap.split("de")[1]);

        return key;
    }
    public String autogenerate(Integer fieldNumber) {
        String value = "";
        String val1= config.getParams("val1");
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String de007 = sdf.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
        String de012 = sdf2.format(new Date());
        SimpleDateFormat sdf3 = new SimpleDateFormat("MMdd");
        String de013 = sdf3.format(new Date());
        switch (fieldNumber) {

            case 7:
                value = de007;
                break;
            case 10:
                value = de013.substring(1)+val1;
                break;
            case 11:
                value = "000927";
                break;
            case 12:
                value = de012;
                break;
            case 13:
                value = de013;
                break;
            case 15:
                value = de013;
                break;
            case 37:
                value = "934715000927";
                break;


        }
        return value;
    }
    public ISOMsg parsers(String message) {
        ISOMsg msg = new ISOMsg();
        try {
            byte[] c = ISOUtil.hex2byte(message);
            msg.setPackager(new ISOIss());
            msg.unpack(c);

            String cat = msg.getMTI();
            for (int i = 1; i <= msg.getMaxField(); i++) {
                if (msg.hasField(i)) {
                    cat = cat + "|" + msg.getString(i);
                    logger.info("    Field-" + i + " : " + msg.getString(i));
                }

            }
        } catch (ISOException ex) {
            Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return msg;
    }
    public static byte[] packmessSmoll() {

        String message = "";
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
            isoMsg.set(35, "4785299000235458=20052011372500000201");

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
            message = ISOUtil.hexString(data);
            System.out.println("RESULT1 : " + message);
        } catch (Exception ex) {
            Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("*1 " + data.length);
        return data;


    }
    public static byte[] createEcho() {
        byte[] data = null;
        String header_echo = "0036000016010200361111112222220000000000000000000001";
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String de007 = sdf.format(new Date());

        ISOMsg msg = new ISOMsg();
        msg.setPackager(new ISOIss());
        try {
            msg.setMTI("0800");
            msg.set(2, "02044");
            msg.set(7, de007);
            msg.set(11, "000777");
            msg.set(70, "999");


            // Get and print the output result
            data = msg.pack();
            String message = ISOUtil.hexString(data);
            System.out.println("RESULT_0800 : " + message);


        } catch (ISOException e) {
            e.printStackTrace();
        }

        return data;
    }


    public static HashMap<String, String> split(String tokenData){
        boolean flag=true;
        Integer start=0;
        Integer counter=0;
        HashMap<String,String> tlvObj = new HashMap<String, String>();
        do {
            counter=counter+1;

            String tag=tokenData.substring(start,start+2);
            int increment=convertaSize(tokenData.substring(start+2,start+6));
            String tlvListforTag=tokenData.substring(start+6,start+6+increment*2);

            tlvObj.put(tag,tlvListforTag);
            start=start+4+increment*2+2;

            if(tokenData.length()<=start){
                flag=false;
            }

        } while (flag);

        return tlvObj;
    }

    public static int convertaSize(String hex){

        return Integer.parseInt(hex,16);

    }

    public static byte[] createEcho810(ISOMsg isoMsg) {
        byte[] data = null;
        String header_echo = "0036000016010200361111112222220000000000000000000001";
        //String header_echo="003E0000160102003E0000008598220000000000000000000000";
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String de007 = sdf.format(new Date());
        //de011=de011+1;
        try {
            isoMsg.setMTI("0810");

            // Get and print the output result
            data = isoMsg.pack();
            String message = ISOUtil.hexString(data);
            System.out.println("RESULT_0810 : " + header_echo + message);

        } catch (ISOException e) {
            e.printStackTrace();
        }


        return data;
    }
    public static void server() {
        DataInputStream in;
        DataOutputStream out;
        String header_echo = "0036000016010200361111112222220000000000000000000001";
        byte[] inmess = new byte[66];
        String dataString = "";

        try {
            ServerSocket server = new ServerSocket(3111);
            Socket conn = server.accept();
            out = new DataOutputStream(new DataOutputStream(conn.getOutputStream()));


            while (true) {
                try {
                    String send = header_echo + ISOUtil.hexString(createEcho());
                    System.out.println(send);
                    byte[] c = ISOUtil.hex2byte(send);
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
    public static byte[] packmess() {


        String message1 = "0100763C669128E0BA16104785299000235458010000000000000011000000000011120416382300077216382312042005601108400700000100C4F0F0F0F0F0F0F0F00B0123456789012504785299000235458D20052011372500000201F9F3F3F8F1F6F0F0F0F7F7F2C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400840DAE7386BEA0B25162001010100000000660100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A00000000310100128080000000000000000058000000002";
        String mess110 = "0110722020810AF0820210478529900023545801000000000000001112041638230007720840000B012345678901F9F3F3F8F1F6F0F0F0F7F7F2F5F5C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20940404040F2404040F20840140100119F3602000D910A6B569898F80693613535058000000002";
        String mes = "0100763C669128E1BA16104785298326580456010000000000000111000000025000120617260000077317260012062209601108409010000100C4F0F0F0F0F0F0F0F00B0123456789012504785298326580456D22092011322100000647F9F3F4F0F1F7F0F0F0F7F7F3C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E2045CE9E9E90840064378DBF325BE0E65AA20010101000000006201005F9F3303204000950580000100009F37049BADBCAB9F100706010A03A0B0009F260851CBBEF70FDA108B9F360200FF820200009C01009F1A0209789A031909189F02060000000123005F2A0209789F03060000000000008407A00000000310100122080000000000000000058000000002";
        String pin = "0100763C669128E0BA16104785298326580456702000000000000000000000000000120617302700077417302712062209601108400510000100C4F0F0F0F0F0F0F0F00B0123456789012504785298326580456D22092011322100000647F9F3F4F0F1F7F0F0F0F7F7F4C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20840084078DBF325BE0E65AA20010101000000006C0100699F3303204000950580000100009F37049BADBCAB9F100706010A03A0B0009F26089C9D5C389366C0E89F360200FF820200009C01709F1A0209789A031909199F02060000000123005F2A0209789F0306000000000000C008C290FE4F74552A778407A00000000310100425000010080000000000000000058000000002";
        String mess120 = "0120F63C66910AE0A8160000000000000004104785298326580456000000000000000177000000003000120813390200077913390212082209599906430100000108C4F0F0F0F0F0F0F0F00B012345678901F9F3F4F2F1F3F0F0F0F7F7F9F0F0E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E206430643200101010000000004050008100800000000000000000580000000020E0040000000000000F1F1F0F0F3F1";
        String mess130 = "0130722020810AE0800210478529832658045600000000000000017712081339020007790643080B012345678901F9F3F4F2F1F3F0F0F0F7F7F9F0F0E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20643058000000002";
        String mess430 = "0420F63C649108E0A81E000000400000000010478529832658045600000000000000017700000000300012081339100007791339021208220959990643010008C3F0F0F0F0F0F0F0F00B012345678901F9F3F4F2F1F3F0F0F0F7F7F9E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400643200101010000000004050008101200000000000000000000000000000000008808000000000000000007A0000000022504012000000000000000001234567890100000000000";

       /*

       Received de061:000000000000000000000000000000000088
Received de062:0000000000000000
Received de063:A0000000022504
Received de090:012000000000000000001234567890100000000000*/
        byte[] data = null;

        //String message=ISOUtil.hexString(packmessSmoll());
        byte[] packed = packmessSmoll();
        String string = ISOUtil.hexString(packed);

        byte[] c = ISOUtil.hex2byte(mess430);

        //System.err.println(ISOUtil.e));

        byte[] b = string.getBytes(StandardCharsets.UTF_8);


        System.out.println("*2 " + c.length);

        return c;

    }
    public static byte[] processByPAN(String pin, String pan) {
        try {
            //
            byte[] pinBytes = pin.getBytes();

            pan = ISOUtil.padleft(pan, 16, '0');
            byte[] accountNoBytes = ISOUtil.hex2byte(pan);
            byte[] resultBytes = new byte[8];
            //PIN BLOCK
            for (int i = 0; i < 8; i++) {
                resultBytes[i] = (byte) (pinBytes[i] ^ accountNoBytes[i]);
            }
            return resultBytes;
        } catch (ISOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void pack126() {

        ISOMsg msg2 = new ISOMsg();


        try {
            ISOMsg subf = new ISOMsg();
            subf.set(10, "000842");
            subf.setFieldNumber(126);

            msg2.setMTI("0100");
            msg2.set(2, "4785297631668352");
            msg2.set(subf);

            msg2.setPackager(new ISOIss());
            System.out.println(ISOUtil.hexString(msg2.pack()));
        } catch (ISOException e) {
            e.printStackTrace();
        }

    }
    public ISOMsg pack2() {
        ISOMsg msg = new ISOMsg();
        ISOMsg msg126 = new ISOMsg(126);
        msg.setPackager(new ISOIss());

        ISOField f126_10 = new ISOField(10, "000842");

        try {
            msg126.set(f126_10);

            msg.setMTI("0100");
            msg.set(2, "4785297631668352");
            msg.set(msg126);
            System.out.println(ISOUtil.hexString(msg.pack()));
        } catch (ISOException e) {
            e.printStackTrace();
        }

        return msg126;
    }
}
