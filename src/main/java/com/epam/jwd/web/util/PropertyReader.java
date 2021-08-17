package com.epam.jwd.web.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PROPERTY_LOADING_FAIL_MSG = "Loading settings failed.";
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
        try (InputStream propertiesStream = getClass().getClassLoader()
                .getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties = new Properties();
            properties.load(propertiesStream);
        } catch (IOException e) {
            LOGGER.error( PROPERTY_LOADING_FAIL_MSG + e.getMessage());
        }
    }

}
