package School_Management_System.Forms;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Panels.StudentPanel;
import School_Management_System.Service.StudentService;
import School_Management_System.Service.CourseService;
import School_Management_System.Service.UserService;
import School_Management_System.Model.Student;

/**
 * AddStudentForm allows admin to manually add a student.
 * Includes secure password creation and validation.
 */
public class AddStudentForm extends JFrame {

    private final StudentService studentService;
    private final CourseService courseService;
    private final UserService userService;
    private final StudentPanel studentPanel;

    // ===== Form Fields =====
    private JTextField lcField;
    private JSpinner regSpinner;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JSpinner semesterSpinner;
    private JTextField courseField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JSpinner userIdSpinner;

    public AddStudentForm(StudentService studentService,
                          CourseService courseService,
                          UserService userService,
                          StudentPanel studentPanel) {

        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
        this.studentPanel = studentPanel;

        setTitle("Add Student");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== TOP FORM =====
        JPanel topForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lcField = new JTextField(12);
        regSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 9999, 1));
        nameField = new JTextField(12);
        emailField = new JTextField(12);
        phoneField = new JTextField(12);
        userIdSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 9999,1));

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; topForm.add(new JLabel("LC Number:"), gbc);
        gbc.gridx = 1; topForm.add(lcField, gbc);
        gbc.gridx = 2; topForm.add(new JLabel("Reg No:"), gbc);
        gbc.gridx = 3; topForm.add(regSpinner, gbc);
        gbc.gridx = 4; topForm.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 5; topForm.add(nameField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; topForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; topForm.add(emailField, gbc);
        gbc.gridx = 2; topForm.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 3; topForm.add(phoneField, gbc);

        // ===== BOTTOM FORM =====
        JPanel bottomForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(6, 6, 6, 6);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        semesterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        courseField = new JTextField(12);
        usernameField = new JTextField(12);
        passwordField = new JPasswordField(12);
        confirmField = new JPasswordField(12);
        userIdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));

        gbc2.gridx = 0; gbc2.gridy = 0; bottomForm.add(new JLabel("Semester:"), gbc2);
        gbc2.gridx = 1; bottomForm.add(semesterSpinner, gbc2);

        gbc2.gridx = 2; bottomForm.add(new JLabel("Course:"), gbc2);
        gbc2.gridx = 3; bottomForm.add(courseField, gbc2);

        gbc2.gridx = 4; bottomForm.add(new JLabel("Username:"), gbc2);
        gbc2.gridx = 5; bottomForm.add(usernameField, gbc2);

        gbc2.gridx = 6; bottomForm.add(new JLabel("Password:"), gbc2);
        gbc2.gridx = 7; bottomForm.add(passwordField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 1; bottomForm.add(new JLabel("Confirm Password:"), gbc2);
        gbc2.gridx = 1; bottomForm.add(confirmField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 0; bottomForm.add(new JLabel("UserId:"), gbc2);
        gbc2.gridx = 1; bottomForm.add(userIdSpinner, gbc2);

        JButton addButton = new JButton("Add Student");
        gbc2.gridx = 6; gbc2.gridy = 1; gbc2.gridwidth = 2;
        bottomForm.add(addButton, gbc2);

        // ===== ADD PANELS TO MAIN =====
        mainPanel.add(topForm, BorderLayout.NORTH);
        mainPanel.add(bottomForm, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);

        // ===== BUTTON ACTION =====
        addButton.addActionListener(e -> addStudent());
    }

    // ===== HANDLE ADD STUDENT =====
    private void addStudent() {
        try {
            String lcNumber = lcField.getText().trim();
            int regNo = (Integer) regSpinner.getValue();
            String fullName = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            int semester = (Integer) semesterSpinner.getValue();
            String course = courseField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());
            int userId = (Integer) userIdSpinner.getValue();

            // ===== VALIDATION =====
            if (lcNumber.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Required fields missing!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }

            // ===== CREATE STUDENT OBJECT =====
            Student student = new Student(lcNumber, regNo, fullName, email, phone,
                    semester, course, username, password);

            // Add to database
            boolean added = studentService.addStudent(lcNumber, fullName, email, phone);
            if (!added) {
                JOptionPane.showMessageDialog(this, "Failed to add student to database!");
                return;
            }

            // 🔥 Create login account in 'users' table
            boolean created = userService.createUserForStudent(username, password, lcNumber);
            if (!created) {
                JOptionPane.showMessageDialog(this, "Failed to create user account!");
                return;
            }

            if (studentPanel != null) studentPanel.refresh();

            JOptionPane.showMessageDialog(this, "Student added successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /** Prefill form for approving registration requests */
    public void prefillData(String lcNumber, String fullName, String email, String phone, String username, String password) {
        lcField.setText(lcNumber);
        nameField.setText(fullName);
        emailField.setText(email);
        phoneField.setText(phone);
        usernameField.setText(username);
        passwordField.setText(password);
    }

}
