package ru.dmt100.flight_booking.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        var inputStream = PropertyUtil.class.getClassLoader()
                .getResourceAsStream("application.properties");
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }


}
