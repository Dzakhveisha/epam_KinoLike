package com.epam.jwd.web.util;

import org.junit.Assert;
import org.junit.Test;

public class PropertyReaderTest {

    @Test
    public void getProperty() {
        String expected = "Hello";
        String key = "key";
        String actual = PropertyReader.getInstance().getProperty(key);

        Assert.assertEquals(expected, actual);
    }
}