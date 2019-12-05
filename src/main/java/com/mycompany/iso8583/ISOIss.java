/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;

import org.jpos.iso.IFA_BITMAP;
import org.jpos.iso.IFA_FLLCHAR;
import org.jpos.iso.IFA_LLBINARY;
import org.jpos.iso.IFA_LLCHAR;
import org.jpos.iso.IFA_LLLBINARY;
import org.jpos.iso.IFA_LLLCHAR;
import org.jpos.iso.IFA_LLLLCHAR;
import org.jpos.iso.IFA_LLNUM;
import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.IFB_AMOUNT;
import org.jpos.iso.IFB_BINARY;
import org.jpos.iso.IFB_BITMAP;
import org.jpos.iso.IFB_HEX;
import org.jpos.iso.IFB_LLBINARY;
import org.jpos.iso.IFB_LLCHAR;
import org.jpos.iso.IFB_LLHBINARY;
import org.jpos.iso.IFB_LLHEX;
import org.jpos.iso.IFB_LLLBINARY;
import org.jpos.iso.IFB_LLLCHAR;
import org.jpos.iso.IFB_LLLLCHAR;
import org.jpos.iso.IFB_LLLNUM;
import org.jpos.iso.IFB_LLNUM;
import org.jpos.iso.IFB_NUMERIC;
import org.jpos.iso.IFE_BITMAP;
import org.jpos.iso.IFE_CHAR;
import org.jpos.iso.IFE_CHAR;
import org.jpos.iso.IFE_LLLBINARY;
import org.jpos.iso.IFE_LLLCHAR;
import org.jpos.iso.IFE_LLLLCHAR;
import org.jpos.iso.IFE_LLLNUM;
import org.jpos.iso.IFE_LLNUM;
import org.jpos.iso.IFE_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.IF_ECHAR;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

/**
 *
 * @author adolf
 */
public class ISOIss  extends ISOBasePackager{
        private static final boolean pad = false;
    protected ISOFieldPackager fld[] = {
            new IFB_NUMERIC (  4, "MESSAGE TYPE INDICATOR",true),
            new IFB_BITMAP  ( 16, "BIT MAP"),
            new IFB_LLNUM   ( 19, "PAN - PRIMARY ACCOUNT NUMBER",true),
            new IFB_NUMERIC (  6, "PROCESSING CODE", true),
            new IFB_NUMERIC ( 12, "AMOUNT, TRANSACTION", true),
            new IFB_NUMERIC ( 12, "AMOUNT, SETTLEMENT", true),
            new IFB_NUMERIC ( 12, "AMOUNT, CARDHOLDER BILLING", true),            
 /*de007*/  new IFB_NUMERIC ( 10, "TRANSMISSION DATE AND TIME", true),           
            new IFB_NUMERIC (  8, "AMOUNT, CARDHOLDER BILLING FEE", true),
            new IFB_NUMERIC (  8, "CONVERSION RATE, SETTLEMENT", true),
/*de010*/   new IFB_NUMERIC (  8, "CONVERSION RATE, CARDHOLDER BILLING", true),
            new IFB_NUMERIC (  6, "SYSTEM TRACE AUDIT NUMBER", true),
            new IFB_NUMERIC (  6, "TIME, LOCAL TRANSACTION", true),
            new IFB_NUMERIC (  4, "DATE, LOCAL TRANSACTION", true),
            new IFB_NUMERIC (  4, "DATE, EXPIRATION", true),
            new IFB_NUMERIC (  4, "DATE, SETTLEMENT", true),
            new IFB_NUMERIC (  4, "DATE, CONVERSION", true),
            new IFB_NUMERIC (  4, "DATE, CAPTURE", true),
/*de018*/   new IFB_NUMERIC (  4, "MERCHANTS TYPE", true),
            new IFB_NUMERIC (  3, "ACQUIRING INSTITUTION COUNTRY CODE", true),
            new IFB_NUMERIC (  3, "PAN EXTENDED COUNTRY CODE", true),
            new IFB_NUMERIC (  3, "FORWARDING INSTITUTION COUNTRY CODE", true),
            new IFB_NUMERIC (  4, "POINT OF SERVICE ENTRY MODE", true),
   /*de023*/new IFB_NUMERIC (  3, "CARD SEQUENCE NUMBER", true),
            new IFB_NUMERIC (  3, "NETWORK INTERNATIONAL IDENTIFIEER", true),
            new IFB_NUMERIC (  2, "POINT OF SERVICE CONDITION CODE", true),
            new IFB_NUMERIC (  2, "POINT OF SERVICE PIN CAPTURE CODE", true),
            new IFB_NUMERIC (  1, "AUTHORIZATION IDENTIFICATION RESP LEN",true),
   /*de28*/ new IFE_CHAR    (  9, "AMOUNT, TRANSACTION FEE"),
            new IFE_CHAR  (  9, "AMOUNT, SETTLEMENT FEE"),
            new IFE_CHAR  (  10, "AMOUNT, TRANSACTION PROCESSING FEE"),
            new IFE_CHAR  ( 11, "AMOUNT, SETTLEMENT PROCESSING FEE"),
  /*de032*/ new IFB_LLNUM   ( 11, "ACQUIRING INSTITUTION IDENT CODE",true),
            new IFE_CHAR   ( 11, "FORWARDING INSTITUTION IDENT CODE"),
            new IFB_LLCHAR  ( 28, "PAN EXTENDED"),
   /*de035*/new IFB_LLHEX   ( 37, "TRACK 2 DATA"),
            new IFB_NUMERIC (104, "TRACK 3 DATA",true),
            new IFE_CHAR     ( 12, "RETRIEVAL REFERENCE NUMBER"),
            new IFE_CHAR     (  6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
            new IFE_CHAR     (  2, "RESPONSE CODE"),
            new IFE_CHAR     (  3, "SERVICE RESTRICTION CODE"),
            new IFE_CHAR     (  8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
            new IFE_CHAR     ( 15, "CARD ACCEPTOR IDENTIFICATION CODE" ),
            new IFE_CHAR     ( 40, "CARD ACCEPTOR NAME/LOCATION"),
/*44ANS V*/ new IFE_CHAR  ( 25, "ADITIONAL RESPONSE DATA"),
            new IFE_CHAR  ( 76, "TRACK 1 DATA"),
            new IFE_CHAR (1, "ADITIONAL DATA - ISO"),
            new IFE_CHAR (1, "ADITIONAL DATA - NATIONAL"),
            new IFE_CHAR (1, "ADITIONAL DATA - PRIVATE"),
            new IFB_NUMERIC     (  3, "CURRENCY CODE, TRANSACTION",true),
            new IFB_NUMERIC     (  3, "CURRENCY CODE, SETTLEMENT",true),
            new IFB_NUMERIC     (  3, "CURRENCY CODE, CARDHOLDER BILLING" ,true  ),
            new IFB_BINARY  (  8, "PIN DATA"   ),
/*53*/      new IFB_NUMERIC ( 16, "SECURITY RELATED CONTROL INFORMATION",true),    
            new IFE_CHAR (120, "ADDITIONAL AMOUNTS"),
/*add55*/   new IFB_LLHBINARY (255, "INTEGRATED CIRCUIT CARD (ICC)-RELATED DATA"),
            new IFE_CHAR (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED NATIONAL"),
  /*59*/    new IFE_CHAR (255, "RESERVED NATIONAL"),
  /*add 60*/new IFB_LLBINARY (12, "NATIONAL POINT-OF-SERVICE GEOGRAPHIC DATA"),
   /*add61*/new IFB_LLNUM (36, "ADDITIONAL POS INFORMATION",true),
            new IFB_LLBINARY (36, "RESERVED NATIONAL"),
     /*63*/ new IFB_LLBINARY (36, "RESERVED PRIVATE"),
    
            

    

            

        };

    public ISOIss() {
        super();
        setFieldPackager(fld);
    }
    
    
}
