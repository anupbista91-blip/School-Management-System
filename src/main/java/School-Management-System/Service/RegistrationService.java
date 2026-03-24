package School_Management_System.Service;

import School_Management_System.DataBase_Connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles student and teacher registration requests separately.
 * Supports sending requests, fetching pending requests, approving/rejecting, and cleaning up.
 */
public class RegistrationService {

    // =========================
    // STUDENT REGISTRATION REQUEST
    // =========================
    public static class StudentRegistrationRequest {
        public String id;
        public String fullName;
        public String email;
        public String phone;
        public String username;
        public String plainPassword;
        public String status = "PENDING";

        public StudentRegistrationRequest(String id, String fullName, String email, String phone,
                                          String username, String plainPassword) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.username = username;
            this.plainPassword = plainPassword;
        }
    }

    // =========================
    // TEACHER REGISTRATION REQUEST
    // =========================
    public static class TeacherRegistrationRequest {
        public String id;
        public String fullName;
        public String email;
        public String phone;
        public String username;
        public String plainPassword;
        public String status = "PENDING";

        public TeacherRegistrationRequest(String id, String fullName, String email, String phone,
                                          String username, String plainPassword) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.username = username;
            this.plainPassword = plainPassword;
        }
    }

    // =========================
    // SEND REQUESTS
    // =========================
    public boolean sendStudentRequest(StudentRegistrationRequest req) {
        String sql = "INSERT INTO student_registration_requests " +
                "(full_name, email, phone, username, password, status, id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, req.id);
            ps.setString(2, req.fullName);
            ps.setString(3, req.email);
            ps.setString(4, req.phone);
            ps.setString(5, req.username);
            ps.setString(6, req.plainPassword);
            ps.setString(7, req.status);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendTeacherRequest(TeacherRegistrationRequest req) {
        String sql = "INSERT INTO teacher_registration_requests " +
                " (full_name, email, phone, username, password, status, id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, req.id);
            ps.setString(2, req.fullName);
            ps.setString(3, req.email);
            ps.setString(4, req.phone);
            ps.setString(5, req.username);
            ps.setString(6, req.plainPassword);
            ps.setString(7, req.status);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // GET PENDING REQUESTS
    // =========================
    public List<StudentRegistrationRequest> getPendingStudentRequests() {
        List<StudentRegistrationRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM student_registration_requests WHERE status = 'PENDING' ORDER BY id ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StudentRegistrationRequest r = new StudentRegistrationRequest(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                r.status = rs.getString("status");
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherRegistrationRequest> getPendingTeacherRequests() {
        List<TeacherRegistrationRequest> list1 = new ArrayList<>();
        String sql = "SELECT * FROM teacher_registration_requests WHERE status = 'PENDING' ORDER BY id ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TeacherRegistrationRequest r = new TeacherRegistrationRequest(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                r.status = rs.getString("status");
                list1.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }

    // =========================
    // APPROVE / REJECT
    // =========================
    // =========================
// APPROVE
// =========================
    public boolean approveStudentRequest(StudentRegistrationRequest req,
                                         StudentService studentService,
                                         UserService userService) {
        try {
            // 1. Create user account
            userService.createUserForStudent(req.username, req.plainPassword, req.id);

            // 2. Add to main student table
            studentService.addStudent(req.id, req.fullName, req.email, req.phone);

            // 3. Remove from student registration requests table
            deleteStudentRequest(req.id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveTeacherRequest(TeacherRegistrationRequest req,
                                         TeacherService teacherService,
                                         UserService userService) {
        try {
            // 1. Create user account
            userService.createUserForTeacher(req.username, req.plainPassword, req.id);

            // 2. Add to main teacher table
            teacherService.addTeacher(req.id, req.fullName, req.email, req.phone);

            // 3. Remove from teacher registration requests table
            deleteTeacherRequest(req.id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // REJECT
    // =========================
    public boolean rejectStudentRequest(String lcNumber) {
        try {
            // Simply delete the request from requests table
            return deleteStudentRequest(lcNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectTeacherRequest(String employeeNo) {
        try {
            // Simply delete the request from requests table
            return deleteTeacherRequest(employeeNo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // DELETE REQUESTS
    // =========================
    public boolean deleteStudentRequest(String lcNumber) {
        String sql = "DELETE FROM student_registration_requests WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lcNumber);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTeacherRequest(String employeeNo) {
        String sql = "DELETE FROM teacher_registration_requests WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employeeNo);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // UPDATE STATUS
    // =========================
    public boolean updateStudentStatus(String lcNumber, String status) {
        String sql = "UPDATE student_registration_requests SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toUpperCase());
            ps.setString(2, lcNumber);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // return true only if at least one row updated

        } catch (Exception e) {
            e.printStackTrace();
            return false; // return false if exception occurs
        }
    }

    private boolean updateTeacherStatus(String employeeNo, String status) {
        String sql = "UPDATE teacher_registration_requests SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toUpperCase());
            ps.setString(2, employeeNo);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // Get All Student Requests
    // =========================

    public List<StudentRegistrationRequest> getAllStudentRequests() {
        List<StudentRegistrationRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM student_registration_requests";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new StudentRegistrationRequest(
                        rs.getString("lc_number"),
                        rs.getString("full_name"),
                        rs.getString("email"),
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

    // =========================
    // Get All Teachers
    // =========================

    public List<TeacherRegistrationRequest> getAllTeacherRequests() {
        List<TeacherRegistrationRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM teacher_registration_requests";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TeacherRegistrationRequest(
                        rs.getString("employee_no"),
                        rs.getString("full_name"),
                        rs.getString("email"),
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
}