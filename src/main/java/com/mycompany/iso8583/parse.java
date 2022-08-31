/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;

//https://neapay.com/online-tools/emv-tlv-decoder.html

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.*;
import org.jpos.tlv.TLVList;
import org.jpos.tlv.TLVMsg;
import org.slf4j.LoggerFactory;


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
                    if(i==62) {
                        ISOComponent comp = msg.getComponent(62);
                        ISOIss.F62Packager pp = new ISOIss.F62Packager();
                        HashMap<String, String> f62 = new HashMap<>();


                        if(detalmode) {
                            ISOMsg subIso = new ISOMsg();
                            subIso.setPackager(pp);
                            subIso.unpack(comp.pack());
                            for (int j = 1; j <= subIso.getMaxField(); j++) {

                                if (subIso.hasField(j)) {
                                    f62.put("" + j, subIso.getString(j));
                                }
                            }
                            list.put("de" + String.format("%03d", i), f62);
                        }else{
                            list.put("de" + String.format("%03d", i), ISOUtil.hexString(comp.pack()));
                        }
                    }
                    if(i==104|i==123|i==56|i==111|i==34|i==125){
                        if(detalmode) {
                            list.put("de" + String.format("%03d", i),
                                    parsingObjectWithTLV(msg.getString(i)));

                        }else{
                            list.put("de" + String.format("%03d", i), msg.getString(i));
                        }
                    }
                    if(i==126) {
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
                }

            }

        } catch (ISOException ex) {
            Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    public HashMap<String, HashMap<String, String>> parsingObjectWithTLV( String fieldData){
       
        HashMap<String,String>tlvObj= splitbyTag(fieldData);
        HashMap<String,HashMap<String,String>> objTLV=new HashMap<>();

        for (String obj:tlvObj.keySet()){
            TLVList tlvData = new TLVList();

            tlvData.unpack(ISOUtil.hex2byte(tlvObj.get(obj)));
            HashMap<String,String> f104=new HashMap<>();
            for (TLVMsg tLVMsg : tlvData.getTags()) {
                f104.put( Integer.toHexString(tLVMsg.getTag()),ISOUtil.hexString(tLVMsg.getValue()));
            }
            objTLV.put(obj,f104);
        }
        return objTLV;
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
                        case 62:
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String, Object> subf2= (HashMap<String, Object>) obj.getValue();
                                try {
                                    msg.set(packMSG62(subf2));

                                } catch (ISOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                ISOIss.F62Packager pp = new ISOIss.F62Packager();
                                ISOMsg subIso = new ISOMsg();
                                subIso.setPackager(pp);
                                HashMap<String, Object> f62 = new HashMap<>();
                                try {
                                    subIso.unpack(ISOUtil.hex2byte(obj.getValue().toString()));
                                    for (int j = 1; j <= subIso.getMaxField(); j++) {

                                        if (subIso.hasField(j)) {
                                            f62.put("" + j, subIso.getString(j));
                                        }
                                    }if(f62.size()!=0){
                                        msg.set(packMSG62(f62));
                                    }else{
                                        logger.info("Field 62 can't parse this "+obj.getValue().toString());
                                    }


                                } catch (ISOException e) {
                                    e.printStackTrace();
                                }
                            }

                            break;

                        case 55:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String, Object> tlv= (HashMap<String, Object>) obj.getValue();
                                msg.set(55,packMSG55(tlv));
                                break;
                            }
                        case 56:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(56,packMSG123(tlv));
                                break;
                            }
                        case 111:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(111,packMSG123(tlv));
                                break;
                            }
                        case 34:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(34,packMSG123(tlv));
                                break;
                            }
                        case 123:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(123,packMSG123(tlv));
                                break;
                            }
                        case 125:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(125,packMSG123(tlv));
                                break;
                            }
                        case 104:
                            //проверить строка или обьект
                            if(!obj.getValue().getClass().equals(String.class)){
                                HashMap<String,HashMap<String, Object>> tlv= (HashMap<String, HashMap<String, Object>>) obj.getValue();
                                msg.set(104,packMSG123(tlv));
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

    private String packTLVFld(HashMap<String, Object> tlv){

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
        return hexSize+hex55;
    }
    private String packMSG55(HashMap<String, Object> tlv) {

        return "01"+packTLVFld(tlv);
    }
    private String packMSG123(HashMap<String, HashMap<String, Object>> tlv) {
        String message="";

        for (Map.Entry<String, HashMap<String, Object>> obj1 : tlv.entrySet()){
            HashMap<String, Object> item = obj1.getValue();
            message+=obj1.getKey()+packTLVFld(item);
        }
    return message;
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
        System.out.println("msg126");
        System.out.println(msg126);
        System.out.println("**********");
        return msg126;
    }

    public ISOMsg packMSG62(Map<String, Object> obj) {

        ISOMsg msg62 = new ISOMsg(62);
        for (Map.Entry<String, Object> obj2 : obj.entrySet()) {
            ISOField subfld = new ISOField(Integer.parseInt(obj2.getKey()), obj2.getValue().toString());
            try {
                msg62.set(subfld);

            } catch (ISOException e) {
                e.printStackTrace();
            }
        }


        return msg62;
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

    public static HashMap<String, String> splitbyTag(String tokenData){
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

}
