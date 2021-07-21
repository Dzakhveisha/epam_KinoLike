package com.epam.jwd.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static PropertyReader instance = null;

    private static Properties properties = new Properties();
    private static final String PROPERTY_FILE_NAME = "db.properties";

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
        LoadAllProperties();
        return properties.getProperty(propertyName);
    }

    private void LoadAllProperties() {
        try (InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties = new Properties();
            properties.load(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
