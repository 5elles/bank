package it.academy.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static DataSource dataSource;
    private Properties properties;
    private DataSource(){
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/db.properties")){
            properties.load(input);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static DataSource getInstance() {
        if (dataSource == null) {
            dataSource = new DataSource();
        }
        return dataSource;
    }
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                            properties.getProperty("db.user"),
                            properties.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
