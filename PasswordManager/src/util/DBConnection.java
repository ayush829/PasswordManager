package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl21";
    private static final String USER = "system";
    private static final String PASSWORD = "Ayush2501";

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully (Oracle)");
            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
