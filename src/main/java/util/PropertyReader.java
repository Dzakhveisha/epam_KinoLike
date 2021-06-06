package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static PropertyReader instance = null;

    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE_NAME = "./src/main/resources/db.properties";

    private PropertyReader() {
    }

    public static PropertyReader getInstance() {
        if (instance == null) {
            synchronized (PropertyReader.class) {
                if (instance == null) {
                    instance = new PropertyReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String propertyName) {
        loadProperties();
        return properties.getProperty(propertyName);
    }

    private static void loadProperties() {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(PROPERTY_FILE_NAME);
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
