package com.mycompany.iso8583;

import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFile {

    FileInputStream fis;
    Properties property = new Properties();
    org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigFile.class);

    public String getParams(String strParam){
        String param = "";
        try {
            fis = new FileInputStream("config.properties");
            property.load(fis);

             param=property.getProperty(strParam);
             logger.info("params "+param);


        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    return param;
    }

    public Integer getIntParams(String strParam){
        Integer param = 10000;
        try {
            fis = new FileInputStream("config.properties");
            property.load(fis);

            param=Integer.parseInt(property.getProperty(strParam));
            logger.info("params "+param);


        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return param;
    }

}
