package School_Management_System.Forms;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Service.*;
import School_Management_System.Model.Student;
import School_Management_System.Model.Teacher;
import School_Management_System.Model.User;
import School_Management_System.Window.AdminDashboard;

/**
 * LoginForm for users (students, teachers, admin)
 * Supports forgot password functionality
 */
public class LoginForm extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public LoginForm(UserService userService,
                     StudentService studentService,
                     TeacherService teacherService,
                     CourseService courseService,
                     RegistrationService registrationService) {

        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.registrationService = registrationService;

        setTitle("School Management System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton forgotButton = new JButton("Forgot Password");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(forgotButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);

        // ===== Button Actions =====
        loginButton.addActionListener(e -> handleLogin());
        forgotButton.addActionListener(e -> handleForgotPassword());
    }

    /**
     * Handle login action
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Please enter username and password!");
            return;
        }

        // Authenticate user
        User user = userService.authenticate(username, password);
        if(user != null) {
            JOptionPane.showMessageDialog(this,"Login successful!");
            dispose(); // close login form

            // Open dashboard
            new AdminDashboard(
                    userService,
                    studentService,
                    teacherService,
                    courseService,
                    registrationService
            );
        } else {
            JOptionPane.showMessageDialog(this,"Invalid credentials!");
        }
    }

    /**
     * Forgot password flow
     */
    private void handleForgotPassword() {
        String[] options = {"Student","Teacher"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select your role:",
                "Forgot Password",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if(choice == 0) { // Student
            String lcNumber = JOptionPane.showInputDialog(this,"Enter your LC Number:");
            if(lcNumber == null || lcNumber.trim().isEmpty()) return;

            String regNoStr = JOptionPane.showInputDialog(this,"Enter your Registration Number:");
            if(regNoStr == null || regNoStr.trim().isEmpty()) return;

            int regNo;
            try { regNo = Integer.parseInt(regNoStr.trim()); }
            catch(Exception ex) { JOptionPane.showMessageDialog(this,"Invalid registration number!"); return; }

            Student s = studentService.searchStudentByLC(lcNumber);
            if(s == null || s.getRegNo() != regNo) {
                JOptionPane.showMessageDialog(this,"Student not found or invalid registration number!");
                return;
            }

            String newPassword = JOptionPane.showInputDialog(this,"Enter new password:");
            if(newPassword == null || newPassword.trim().isEmpty()) return;

            boolean updated = userService.resetPassword(lcNumber, String.valueOf(regNo), newPassword);
            if(updated) {
                JOptionPane.showMessageDialog(this,"Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this,"Failed to update password!");
            }

        } else if(choice == 1) { // Teacher
            String empStr = JOptionPane.showInputDialog(this,"Enter your Employee Number:");
            if(empStr == null || empStr.trim().isEmpty()) return;

            int empNo;
            try { empNo = Integer.parseInt(empStr.trim()); }
            catch(Exception ex) { JOptionPane.showMessageDialog(this,"Invalid employee number!"); return; }

            Teacher t = teacherService.getTeacherByEmployeeNo(empNo);
            if(t == null) {
                JOptionPane.showMessageDialog(this,"Teacher not found!");
                return;
            }

            String newPassword = JOptionPane.showInputDialog(this,"Enter new password:");
            if(newPassword == null || newPassword.trim().isEmpty()) return;

            boolean updated = userService.updatePassword(t.getUsername(), newPassword);
            if(updated) {
                JOptionPane.showMessageDialog(this,"Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this,"Failed to update password!");
            }
        }
    }
}