package School_Management_System.Service;

import School_Management_System.DataBase_Connection.DBConnection;
import School_Management_System.Model.User;
import School_Management_System.Util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserService handles all user-related database operations.
 */
public class UserService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public UserService(StudentService studentService, TeacherService teacherService,
                       CourseService courseService, RegistrationService registrationService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.registrationService = registrationService;
    }

    /**
     * UserService handles user authentication and management in PostgreSQL.
     */

    // ===== AUTHENTICATE USER =====
    public User authenticate(String username, String password) {
        User user = getUserByUsername(username);

        if (user != null) {
            // ✅ Direct comparison (no hashing)
            if (password.equals(user.getplainPassword())) {
                return user;
            }
        }
        return null;
    }


    // ===== GET USER BY USERNAME =====
    public User getUserByUsername(String username) {
        String sql = "SELECT id, username, role, linked_person_id FROM users WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("plainPassword"),
                            User.Role.valueOf(rs.getString("role")),
                            rs.getString("linked_person_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===== VERIFY USER (GENERIC) =====
    public boolean verifyUser(String username, int linkedPersonId) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND linked_person_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, linkedPersonId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===== VERIFY BY LC + REG NO (FORGOT PASSWORD) =====
    public boolean verifyByLCAndReg(String lc, String regNo) {

        String sql = "SELECT reg_no FROM students WHERE lc_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lc);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int dbRegNo = rs.getInt("reg_no");
                    return String.valueOf(dbRegNo).equals(regNo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===== RESET PASSWORD (FORGOT PASSWORD FLOW) =====
    public boolean resetPassword(String lc, String regNo, String newPassword) {

        String getUserSql = "SELECT u.username FROM users u " +
                "JOIN students s ON u.linked_person_id = s.lc_number " +
                "WHERE s.lc_number = ? AND s.reg_no = ?";

        String updateSql = "UPDATE users SET plain_password = ? WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps1 = con.prepareStatement(getUserSql)) {

            ps1.setString(1, lc);
            ps1.setInt(2, Integer.parseInt(regNo));

            try (ResultSet rs = ps1.executeQuery()) {

                if (rs.next()) {
                    String username = rs.getString("username");

                    try (PreparedStatement ps2 = con.prepareStatement(updateSql)) {
                        ps2.setString(1, newPassword); // ✅ no hashing
                        ps2.setString(2, username);

                        return ps2.executeUpdate() > 0;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ===== UPDATE PASSWORD DIRECTLY =====
    public boolean updatePassword(String username, String newPassword) {

        String sql = "UPDATE users SET plain_password = ? WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ===== CREATE USER FOR STUDENT =====
    public boolean createUserForStudent(String username, String plainPassword, String lcNumber) {

        String sql = "INSERT INTO users (username, plain_password, role, linked_person_id) " +
                "VALUES (?, ?, ?::user_role, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, plainPassword);
            ps.setString(3, "STUDENT");
            ps.setString(4, lcNumber);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ===== CREATE USER FOR TEACHER =====
    public boolean createUserForTeacher(String username, String plainPassword, String employeeNo) {

        String sql = "INSERT INTO users (username, plain_password, role, linked_person_id) " +
                "VALUES (?, ?, ?::user_role, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, plainPassword);
            ps.setString(3, "TEACHER");
            ps.setString(4, employeeNo);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createStudentRequest(String Id, String fullName, String email, String phone,
                                        String username, String password) {
        String sql = "INSERT INTO student_registration_requests (id, full_name, email, phone, username, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Id);                 // ID column
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);                 // Consider hashing in real app
            ps.setString(7, "PENDING");                // Default status

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTeacherRequest(String Id, String fullName, String email, String phone,
                                        String username, String password) {
        String sql = "INSERT INTO teacher_registration_requests (id, full_name, email, phone, username, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Id);               // ID column
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);                 // Consider hashing in real app
            ps.setString(7, "PENDING");                // Default status

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}