package com.example.cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static final String url = "jdbc:mysql://localhost:3306/cafe" +
            "?useSSL=false" +
            "&serverTimezone=UTC" +
            "&useUnicode=true" +
            "&characterEncoding=UTF-8";

    public static Connection connect() {
        try {
            String username = UserData.getUsername();
            String password = UserData.getPassword();
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
