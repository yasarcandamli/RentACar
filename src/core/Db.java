package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    //Singleton Design Pattern
    private static Db instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/rentacar.sql";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "yasarcan";

    private Db() {
        try {
            this.connection = DriverManager.getConnection(
                    this.DB_URL,
                    this.DB_USER,
                    this.DB_PASSWORD
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}
