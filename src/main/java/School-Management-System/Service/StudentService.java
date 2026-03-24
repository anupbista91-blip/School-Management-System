/**
 * File: StudentService.java
 * Purpose: Service to manage Student-related operations using MySQL.
 */

package School_Management_System.Service;

import School_Management_System.DataBase_Connection.DBConnection;
import School_Management_System.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentService handles all student-related database operations.
 * Course is stored as a String (course name) in the database.
 */
public class StudentService {

    // =========================
    // ADD STUDENT
    // =========================
    public boolean addStudent(String lcNumber,
                              String fullName,
                              String email,
                              String phone
                              ) {

        String sql = "INSERT INTO students (lc_number, reg_no, full_name, email, phone, semester, course_id, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lcNumber);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setString(5, phone);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // UPDATE STUDENT
    // =========================
    public boolean updateStudent(String lc_Number,
                                 int regNo,
                                 String fullName,
                                 String email,
                                 String phone,
                                 int semester,
                                 String course) {

        String sql = "UPDATE students SET reg_no = ?, full_name = ?, email = ?, phone = ?, semester = ?, course_id = ? " +
                "WHERE lc_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, regNo);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setInt(5, semester);
            ps.setString(6, course);
            ps.setString(7, lc_Number);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // DELETE STUDENT
    // =========================
    public boolean deleteStudent(String lc_Number) {

        String sql = "DELETE FROM students WHERE lc_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lc_Number);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // GET ALL STUDENTS
    // =========================
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT s.lc_Number, s.reg_no, s.full_name, s.email, s.phone, s.semester, s.course_id, " +
                "u.username, u.plain_password " +
                "FROM students s " +
                "JOIN users u ON u.linked_person_id::TEXT = s.lc_Number::TEXT AND u.role = 'STUDENT'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("Lc_Number"),
                        rs.getInt("reg_no"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("semester"),
                        rs.getString("course_id"),
                        rs.getString("username"),
                        rs.getString("plain_password")
                );

                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // =========================
    // SEARCH STUDENT BY LC NUMBER
    // =========================
    public Student searchStudentByLC(String lcNumber) {

        String sql = "SELECT s.lc_Number, s.reg_no, s.full_name, s.email, s.phone, s.semester, s.course_id, " +
                "u.username, u.plain_password " +
                "FROM students s " +
                "JOIN users u ON u.linked_person_id::TEXT = s.lc_Number::TEXT AND u.role = 'STUDENT'" +
                "WHERE s.lc_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lcNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("Lc_Number"),
                            rs.getInt("reg_no"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getInt("semester"),
                            rs.getString("course_id"),
                            rs.getString("username"),
                            rs.getString("plain_password")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error searching student: " + e.getMessage());
        }

        return null; // not found
    }

    public boolean createStudentRequest(String lcNumber, String fullName, String email,
                                        String phone, String username, String password) {
        String sql = "INSERT INTO student_registration_requests " +
                "(id, full_name, email, phone, username, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lcNumber);           // id = LC Number
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);           // Consider hashing for security
            ps.setString(7, "PENDING");          // Default status

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}