/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iso8583;


import org.jpos.iso.*;
import org.jpos.iso.packager.Base1Packager;
import org.jpos.iso.packager.Base1SubFieldPackager;
import org.jpos.iso.packager.Base1_BITMAP126;
import org.jpos.iso.packager.GenericTaggedFieldsPackager;
import org.jpos.tlv.packager.bertlv.BERTLVEbcdicHexPackager;

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
  /*de034*/ new IFB_LLLHBINARY  ( 255, "ELECTRONIC COMMERCE DATA"),
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
            new IFB_LLHECHAR  ( 76, "TRACK 1 DATA"),
            new IFE_CHAR (1, "ADITIONAL DATA - ISO"),
            new IFE_CHAR (1, "ADITIONAL DATA - NATIONAL"),
            new IFB_LLHECHAR (255, "ADITIONAL DATA - PRIVATE"),
            new IFB_HEX     (  3, "CURRENCY CODE, TRANSACTION",true),
            new IFB_HEX     (  3, "CURRENCY CODE, SETTLEMENT",true),
            new IFB_HEX     (  3, "CURRENCY CODE, CARDHOLDER BILLING" ,true  ),
            new IFB_BINARY  (  8, "PIN DATA"   ),
/*53*/      new IFB_NUMERIC ( 16, "SECURITY RELATED CONTROL INFORMATION",true),    
            new IFB_LLHECHAR (120, "ADDITIONAL AMOUNTS"),
/*add55*/   new IFB_LLHBINARY (255, "INTEGRATED CIRCUIT CARD (ICC)-RELATED DATA"),
/*add56*/   new IFB_LLHBINARY (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED ISO"),
            new IFE_CHAR (255, "RESERVED NATIONAL"),
  /*59*/    new IFB_LLHBINARY (255, "RESERVED NATIONAL"),
  /*add 60*/new IFB_LLHBINARY (12, "NATIONAL POINT-OF-SERVICE GEOGRAPHIC DATA"),
   /*add61*/new IFB_LLHBINARY (24, "ADDITIONAL POS INFORMATION"),
            //new IFB_LLHBINARY (36, "RESERVED NATIONAL"),
            new ISOMsgFieldPackager(
                    new IFB_LLHBINARY (255, "Field 62"),
                    new F62Packager()),
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
    /*111*/ new IFB_LLLHBINARY( 999, "RESERVED ISO USE"),
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
    /*123*/ new IFB_LLHBINARY (255, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
     /*126*///new IFB_LLHECHAR(99, "RESERVED PRIVATE USE"),//1b(hex)16b(bitmap)ebcid medd
            new ISOMsgFieldPackager(
                    new IFB_LLHBINARY (255, "Field 126"),
                    new F126Packager()),

            new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            new IFB_BINARY  (  8, "MAC 2")

        };

    public ISOIss() {
        super();
        setFieldPackager(fld);
    }



    protected static class F126Packager extends Base1SubFieldPackager
    {
        protected ISOFieldPackager fld126[] =
                {
                        new Base1_BITMAP126(16, "Bit Map"),
                        new IFE_CHAR     (25, "Customer Name"),
                        new IFE_CHAR     (57, "Customer Address"),
                        new IFE_CHAR     (57, "Biller Address"),
                        new IFE_CHAR     (18, "Biller Telephone Number"),
                        new IFE_CHAR     (6,  "Process By Date"),
                        new IFB_LLNUM    (17, "Cardholder Cert Serial Number", true),
                        new IFB_LLNUM    (17, "Merchant Cert Serial Number", true),
                        new IFB_NUMERIC  (40, "Transaction ID", true),
                        new IFB_NUMERIC  (40, "TransStain", true),
                        new IFE_CHAR     (6,  "CVV2 Request Data"),
                };

        protected F126Packager ()
        {
            super();
            setFieldPackager(fld126);
        }
    }

    protected static class F62Packager extends Base1SubFieldPackager
    {
        protected ISOFieldPackager fld62[] =
                {
                        new Base1_BITMAP126(16, "Bit Map"),
                        new IFE_CHAR     (1, "Authorization Characteristics Indicator"),
                        new IFB_NUMERIC  (15, "Transaction Identifier",true),
                        new IFE_CHAR     (4, "Validation Code"),
                        new IFE_CHAR     (1, "Market-Specific Data Identifier"),
                        new IFB_NUMERIC  (2, "Duration",true),
                        new IFE_CHAR     (1, "Prestigious Property Indicator"),
                        new IFE_CHAR     (26, "Purchase Identifier"),
                        new IFB_NUMERIC     (6, "Check-Out/Check-In Date",true),
                        new IFE_CHAR     (1, "No Show Indicator"),
                        new IFB_NUMERIC     (6, "Extra Charges",true),
                        new IFB_NUMERIC     (2, "Multiple Clearing Sequence Number",true),
                        new IFB_NUMERIC     (2, "Multiple Clearing Sequence Count",true),
                        new IFB_NUMERIC     (1, "Restricted Ticket Indicator",true),
                        new IFE_CHAR     (12, "Total Amount Authorized"),
                        new IFE_CHAR     (1, "Requested Payment Service"),
                        new IFE_CHAR     (2, "Chargeback Rights Indicator"),
                        new IFE_CHAR     (15, "Gateway Transaction Identifier"),
                        new IFE_CHAR     (4, "Validation Code"),
                        new IFE_CHAR     (4, "Validation Code"),
                        new IFB_NUMERIC     (10, "Merchant Verification Value",true),
                        new IFE_CHAR     (4, "Online Risk Assessment Risk Score and Reason Codes"),
                        new IFE_CHAR     (6, "Online Risk Assessment Condition Codes"),
                        new IFE_CHAR     (2, "Product ID"),
                        new IFE_CHAR     (6, "Program Identifier"),
                        new IFE_CHAR     (1, "Spend Qualified Indicator"),
                        new IFE_CHAR     (1, "Account Status"),
                        new IFE_CHAR     (1, "Account Status"),
                        new IFE_CHAR     (7, "ATM Routing Table Unique Identifier")


                };

        protected F62Packager ()
        {
            super();
            setFieldPackager(fld62);
        }
    }
    protected static class F127Packager extends ISOBasePackager
    {
        protected ISOFieldPackager fld127[] =
                {
                        new IFE_CHAR    (1,   "FILE UPDATE COD"),
                        new IFB_LLHNUM  (19,  "ACCOUNT NUMBER", true),
                        new IFB_NUMERIC (4,   "PURGE DATE", true),
                        new IFE_CHAR    (2,   "ACTION CODE"),
                        new IFE_CHAR    (9,   "REGION CODING"),
                        new IFB_NUMERIC (4,   "FILLER", true),
                };
        protected F127Packager ()
        {
            super();
            setFieldPackager(fld127);
        }
    }



}
