package School_Management_System.DataBase_Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5607/School_Management_System_db"; // use your port if custom
    private static final String USER = "postgres"; // your PostgreSQL username
    private static final String PASSWORD = "Lovemom@123"; // your PostgreSQL password

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver"); // optional but safe
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
