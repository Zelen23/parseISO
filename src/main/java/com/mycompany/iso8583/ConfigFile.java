package com.mycompany.iso8583;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFile {

    FileInputStream fis;
    Properties property = new Properties();


    public String getParams(String strParam){
        String param = "";
        try {
            fis = new FileInputStream("config.properties");
            property.load(fis);

             param=property.getProperty(strParam);

             System.out.println("params "+param);


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
            System.out.println("params "+param);


        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return param;
    }

}
