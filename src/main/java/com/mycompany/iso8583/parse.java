/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;

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
        packmess();
        
    
    }
public static void packmess() {
          String mess ="014B0000160102014B0000008598220000000000000000000000"
                  + "0100"
                  + "763C669128E0BA16"
                  + "104785299000235458"
                  + "010000000000000011000000000011120416382300077216382312042005601108400700000100C4F0F0F0F0F0F0F0F00B0123456789012504785299000235458D20052011372500000201F9F3F3F8F1F6F0F0F0F7F7F2C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400840DAE7386BEA0B25162001010100000000660100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A00000000310100128080000000000000000058000000002";
       
    try {
        ISOMsg isoMsg = new ISOMsg();
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
        byte[] data = isoMsg.pack();
        String message=ISOUtil.hexString(data);
        System.out.println("RESULT : " + message);
    } catch (ISOException ex) {
        Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
    }
        
  
      
      }
public static void packmessSmoll() {
          String mess ="014B0000160102014B0000008598220000000000000000000000"
                  + "0100"
                  + "763C669128E0BA16"
                  + "104785299000235458"
                  + "010000000000000011000000000011120416382300077216382312042005601108400700000100C4F0F0F0F0F0F0F0F00B0123456789012504785299000235458D20052011372500000201F9F3F3F8F1F6F0F0F0F7F7F2C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E208400840DAE7386BEA0B25162001010100000000660100639F7C0A000000000000000000009F3303204000950580000100009F37049BADBCAB9F100706011103A0B0009F260892B379D36CA348049F3602000D82023C009C01009F1A0208269A031804279F02060000000000005F2A0208268407A00000000310100128080000000000000000058000000002";
     

    try {
        
        GenericPackager packager = new GenericPackager("C:\\Users\\adolf\\Documents\\NetBeansProjects\\iso8583\\src\\resources\\XmlSchema.xsd");
        
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        isoMsg.setMTI("0100");
        isoMsg.set(2, "4785299000235458");
        isoMsg.set(3, "010000");
        isoMsg.set(4, "000000000011");
        isoMsg.set(6, "000000000011");
        isoMsg.set(7, "1204163823");
        isoMsg.set(28, "D00000000");

        
        
        // Get and print the output result
        byte[] data = isoMsg.pack();
        String message=ISOUtil.hexString(data);
        System.out.println("RESULT : " + message);
    } catch (ISOException ex) {
        Logger.getLogger(parse.class.getName()).log(Level.SEVERE, null, ex);
    }
        
  
      
      }

    
}
