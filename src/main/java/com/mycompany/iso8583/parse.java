/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO87BPackager;

/**
 *
 * @author adolf
 */
public class parse {
    
   public static void main(String[] args) {
       
       try {
           
           // Create ISO Message
           ISOMsg msg = new ISOMsg();
           GenericPackager packager = new GenericPackager("/home/azelinsky/ISS/parseISO/src/resources/grab.xml");
           
           //msg.setPackager(packager);
           msg.setPackager(new ISOIss());
          // msg.unpack(packmessSmoll());
            msg.unpack(packmess());
          
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
      
               
              
	
    
    }
public static byte[] packmess() {
    

       String message1="0100763C669128E0BA16104785299000235458010000000000000011000000000011120416382300077216382312042005601108400700000100C4F0F0F0F0F0F0F0F00B0123456789012504785299000235458D20052011372500000201F9F3F3F8F1F6F0F0F0F7F7F2C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400840DAE7386BEA0B25162001010100000000660100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A00000000310100128080000000000000000058000000002";
       byte[] data = null;
       
       //String message=ISOUtil.hexString(packmessSmoll());
       byte[] packed=packmessSmoll();
       String string=ISOUtil.hexString(packed);
   
        byte[] c=ISOUtil.hex2byte(string);
        
        //System.err.println(ISOUtil.e));
      
        byte[] b =string.getBytes(StandardCharsets.UTF_8);
      

       System.out.println("*2 " +c.length);
        
  return  c;
      
      }
public static byte[] packmessSmoll() {
          
           String message="";
           byte[] data = null;
    try {
        
        GenericPackager packager = new GenericPackager("/home/azelinsky/ISS/parseISO/src/resources/grab.xml");
        
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
        isoMsg.set(35, "4785299000235458D20052011372500000201");
        isoMsg.set(37, "933816000772");   
        isoMsg.set(41, "ATM01");
        isoMsg.set(42, "CARD ACCEPTOR  ");
        isoMsg.set(43, "ACQUIRER NAME            CITY NAME    US");  
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
    } catch (ISOException ex) {
        Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("*1 "+data.length);
        return  data;
  
      
      }

    
}
