package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Properties;

class PropertyReader {
    private  static Properties properties;
    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath()));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error: config.properties is absent!");
        }
    }
    static String getProperty(String prop) {
        return properties.getProperty(prop);
    }
 }
