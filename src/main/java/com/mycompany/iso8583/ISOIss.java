/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;


import org.jpos.iso.*;

/**
 *
 * @author adolf
 */
public class ISOIss  extends ISOBasePackager{
        private static final boolean pad = false;
    protected ISOFieldPackager fld[] = {
            new IFB_NUMERIC (  4, "MESSAGE TYPE INDICATOR",true),
            new IFB_BITMAP  ( 32, "BIT MAP"),
            new IFB_LLHNUM   ( 19, "PAN - PRIMARY ACCOUNT NUMBER",pad),
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
  /*de032*/ new IFB_LLHNUM   ( 11, "ACQUIRING INSTITUTION IDENT CODE",true),
            new IFE_CHAR   ( 11, "FORWARDING INSTITUTION IDENT CODE"),
            new IFB_LLCHAR  ( 28, "PAN EXTENDED"),
   /*de035*/new IFB_LLHNUM (37, "TRACK 2 DATA",true),
            new IFB_NUMERIC (104, "TRACK 3 DATA",true),
            new IFE_CHAR     ( 12, "RETRIEVAL REFERENCE NUMBER"),
            new IFE_CHAR     (  6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
            new IFE_CHAR     (  2, "RESPONSE CODE"),
            new IFE_CHAR     (  3, "SERVICE RESTRICTION CODE"),
            new IFE_CHAR     (  8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
            new IFE_CHAR     ( 15, "CARD ACCEPTOR IDENTIFICATION CODE" ),
            new IFE_CHAR     ( 40, "CARD ACCEPTOR NAME/LOCATION"),
/*44ANS V*/ new IFB_LLHECHAR  ( 25, "ADITIONAL RESPONSE DATA"),
            new IFE_CHAR  ( 76, "TRACK 1 DATA"),
            new IFE_CHAR (1, "ADITIONAL DATA - ISO"),
            new IFE_CHAR (1, "ADITIONAL DATA - NATIONAL"),
            new IFB_LLHECHAR (255, "ADITIONAL DATA - PRIVATE"),
            new IFB_HEX     (  3, "CURRENCY CODE, TRANSACTION",true),
            new IFB_HEX     (  3, "CURRENCY CODE, SETTLEMENT",true),
            new IFB_HEX     (  3, "CURRENCY CODE, CARDHOLDER BILLING" ,true  ),
            new IFB_BINARY  (  8, "PIN DATA"   ),
/*53*/      new IFB_NUMERIC ( 16, "SECURITY RELATED CONTROL INFORMATION",true),    
            new IFE_CHAR (120, "ADDITIONAL AMOUNTS"),
/*add55*/   new IFB_LLHBINARY (255, "INTEGRATED CIRCUIT CARD (ICC)-RELATED DATA"),
            new IFE_CHAR (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED NATIONAL"),
  /*59*/    new IFE_CHAR (255, "RESERVED NATIONAL"),
  /*add 60*/new IFB_LLHBINARY (12, "NATIONAL POINT-OF-SERVICE GEOGRAPHIC DATA"),
   /*add61*/new IFB_LLHBINARY (24, "ADDITIONAL POS INFORMATION"),
            new IFB_LLHBINARY (36, "RESERVED NATIONAL"),
     /*63*/ new IFB_LLHBINARY (36, "RESERVED PRIVATE"),
            new IFB_BINARY  (  8, "MESSAGE AUTHENTICATION CODE FIELD"),
            new IFB_BINARY  (  1, "BITMAP, EXTENDED"),
            new IFB_NUMERIC (  1, "SETTLEMENT CODE", true),
            new IFB_NUMERIC (  2, "EXTENDED PAYMENT CODE", true),
            new IFB_NUMERIC (  3, "RECEIVING INSTITUTION COUNTRY CODE", true),
            new IFB_NUMERIC (  3, "SETTLEMENT INSTITUTION COUNTRY CODE", true),
            new IFB_NUMERIC (  3, "NETWORK MANAGEMENT INFORMATION CODE", true),
            new IFB_NUMERIC (  4, "MESSAGE NUMBER", true),
            new IFB_NUMERIC (  4, "MESSAGE NUMBER LAST", true),
            new IFB_NUMERIC (  6, "DATE ACTION", true),
            new IFB_NUMERIC ( 10, "CREDITS NUMBER", true),
            new IFB_NUMERIC ( 10, "CREDITS REVERSAL NUMBER", true),
            new IFB_NUMERIC ( 10, "DEBITS NUMBER", true),
            new IFB_NUMERIC ( 10, "DEBITS REVERSAL NUMBER", true),
            new IFB_NUMERIC ( 10, "TRANSFER NUMBER", true),
            new IFB_NUMERIC ( 10, "TRANSFER REVERSAL NUMBER", true),
            new IFB_NUMERIC ( 10, "INQUIRIES NUMBER", true),
            new IFB_NUMERIC ( 10, "AUTHORIZATION NUMBER", true),
            new IFB_NUMERIC ( 12, "CREDITS, PROCESSING FEE AMOUNT", true),
            new IFB_NUMERIC ( 12, "CREDITS, TRANSACTION FEE AMOUNT", true),
            new IFB_NUMERIC ( 12, "DEBITS, PROCESSING FEE AMOUNT", true),
            new IFB_NUMERIC ( 12, "DEBITS, TRANSACTION FEE AMOUNT", true),
            new IFB_NUMERIC ( 16, "CREDITS, AMOUNT", true),
            new IFB_NUMERIC ( 16, "CREDITS, REVERSAL AMOUNT", true),
            new IFB_NUMERIC ( 16, "DEBITS, AMOUNT", true),
            new IFB_NUMERIC ( 16, "DEBITS, REVERSAL AMOUNT", true),
     /*90*/ new IFB_NUMERIC ( 42, "ORIGINAL DATA ELEMENTS", true),
            new IF_CHAR     (  1, "FILE UPDATE CODE"),
            new IF_CHAR     (  2, "FILE SECURITY CODE"),
            new IF_CHAR     (  6, "RESPONSE INDICATOR"),
            new IF_CHAR     (  7, "SERVICE INDICATOR"),
     /*95*/ new IFE_CHAR     ( 42, "REPLACEMENT AMOUNTS"),
            new IFB_BINARY  ( 16, "MESSAGE SECURITY CODE"),
            new IFB_AMOUNT  ( 17, "AMOUNT, NET SETTLEMENT", pad),
            new IF_CHAR     ( 25, "PAYEE"),
            new IFB_LLNUM   ( 11, "SETTLEMENT INSTITUTION IDENT CODE", pad),
            new IFB_LLNUM   ( 11, "RECEIVING INSTITUTION IDENT CODE", pad),
            new IFB_LLCHAR  ( 17, "FILE NAME"),
            new IFB_LLCHAR  ( 28, "ACCOUNT IDENTIFICATION 1"),
            new IFB_LLCHAR  ( 28, "ACCOUNT IDENTIFICATION 2"),
   /*104*/  new IFB_LLHBINARY (255, "TRANSACTION DESCRIPTION"),
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED ISO USE"), 
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"   ),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"  ),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
     /*126*/new IFB_LLHECHAR(99, "RESERVED PRIVATE USE"),//1b(hex)16b(bitmap)ebcid medd
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_BINARY  (  8, "MAC 2")
    
            

    

            

        };

    public ISOIss() {
        super();
        setFieldPackager(fld);
    }
    
    
}
