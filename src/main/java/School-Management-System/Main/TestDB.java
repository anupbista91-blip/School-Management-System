package School_Management_System.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB{
    public static void main(String[] args) throws Exception {

        String sql = "Select full_name from students where semester=1";
        String url = "jdbc:postgresql://localhost:5607/School_Management_System_db";
        String username = "postgres";
        String password = "Lovemom@123";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        st.executeQuery(sql);
    }
}
