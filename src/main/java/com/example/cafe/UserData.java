package com.example.cafe;

import java.util.Properties;

public class UserData {
    private static final Properties properties = new Properties();

    static {
        properties.setProperty("username", "");
        properties.setProperty("password", "");
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static void setCredentials(String username, String password) {
        properties.setProperty("username", username);
        properties.setProperty("password", password);
    }
}