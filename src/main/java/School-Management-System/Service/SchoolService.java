/**
 * File: SchoolService.java
 * Purpose: Facade service combining different service operations and authentication.
 */
package School_Management_System.Service;

import School_Management_System.Model.User;
import School_Management_System.DAO.UserDAO;
import School_Management_System.DAO.impl.UserDAOImpl;

public class SchoolService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    /** Returns student service. */
    public StudentService student() {
        return studentService;
    }

    /** Returns course service. */
    public CourseService course() {
        return courseService;
    }

    /**
     * Authenticates user (LOGIN)
     */
    public User authenticate(String username, String password) {

        if (username == null || password == null) return null;

        username = username.trim();
        password = password.trim();

        User u = userDAO.findByUsername(username);

        if (u != null && u.verifyPassword(password)) {
            return u; // ✅ full user returned (with name/email/phone)
        }

        return null;
    }

    /**
     * Registers new user (UPDATED)
     */
    public User registerUser(String username,
                             String password,
                             String role,
                             String fullName,
                             String email,
                             String phone,
                             String linkedPersonId) {

        // ===== CLEAN INPUT =====
        username = username.trim();
        password = password.trim();
        fullName = fullName.trim();
        email = email.trim();
        phone = phone.trim();

        // ===== VALIDATION =====
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            throw new IllegalArgumentException("Username, Password, and Full Name are required!");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        // ===== CHECK EXISTING USER =====
        if (userDAO.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        // ===== ROLE =====
        User.Role r = User.Role.valueOf(role.toUpperCase());

        // ===== CREATE USER (NEW CONSTRUCTOR) =====
        User user = new User(
                username,
                password,
                r,
                linkedPersonId,
                fullName,
                email,
                phone
        );

        // ===== SAVE =====
        return userDAO.create(user);
    }
}