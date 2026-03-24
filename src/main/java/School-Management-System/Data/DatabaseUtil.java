/**
 * File: DatabaseUtil.java
 * Purpose: Lightweight JDBC helper to obtain connections using db.properties.
 */
package School_Management_System.Data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * DatabaseUtil centralizes obtaining JDBC connections.
 * NOTE: For production use a connection pool (HikariCP).
 */
public class DatabaseUtil {
    private static final String PROPS = "/db.properties";
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try (InputStream in = DatabaseUtil.class.getResourceAsStream(PROPS)) {
            Properties p = new Properties();
            if (in != null) {
                p.load(in);
                url = p.getProperty("db.url");
                user = p.getProperty("db.user");
                password = p.getProperty("db.password");
                driver = p.getProperty("db.driver");
            }
            if (driver != null && !driver.isEmpty()) {
                Class.forName(driver);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load database configuration: " + e.getMessage());
        }
    }

    /**
     * Obtain a new JDBC connection.
     * Caller should close the connection.
     *
     * @return Connection instance
     * @throws java.sql.SQLException if connection fails
     */
    public static Connection getConnection() throws java.sql.SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}