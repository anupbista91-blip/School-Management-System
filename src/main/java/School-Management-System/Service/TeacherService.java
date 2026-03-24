package School_Management_System.Service;

import School_Management_System.DataBase_Connection.DBConnection;
import School_Management_System.Model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * TeacherService handles teacher CRUD operations in PostgreSQL.
 */
public class TeacherService {

    // =========================
    // ADD TEACHER
    // =========================
    public boolean addTeacher(String employee_no,
                              String fullName,
                              String email,
                              String phone) {
        String sql = "INSERT INTO teachers (employee_no, full_name, phone, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee_no);
            ps.setString(2, fullName);
            ps.setString(6, email);
            ps.setString(4, phone);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error adding teacher: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // GET TEACHER BY EMPLOYEE NUMBER
    // =========================
    public Teacher getTeacherByEmployeeNo(int empNo) {
        String sql = "SELECT * FROM teachers WHERE employee_no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getString("employee_no"),
                            rs.getString("full_name"),
                            rs.getString("phone"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // =========================
    // GET ALL TEACHERS
    // =========================
    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM teachers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Teacher(
                        rs.getString("employee_no"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean createTeacherRequest(String employeeNo, String fullName, String email,
                                        String phone, String username, String password) {
        String sql = "INSERT INTO teacher_registration_requests " +
                "(id, full_name, email, phone, username, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employeeNo);         // id = Employee No
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);           // Consider hashing
            ps.setString(7, "PENDING");          // Default status

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}