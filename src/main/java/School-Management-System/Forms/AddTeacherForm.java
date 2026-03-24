package School_Management_System.Forms;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Service.TeacherService;
import School_Management_System.Model.Teacher;

/**
 * AddTeacherForm allows admin to manually add a teacher.
 * Supports pre-fill from registration requests.
 */
public class AddTeacherForm extends JFrame {

    private final TeacherService teacherService;

    private JTextField employee_NoField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField coursesNameField;
    private JTextField subjectsNameField;
    private JSpinner yearsExperienceSpinner;
    private JTextField usernameField;
    private JTextField passwordField;

    public AddTeacherForm(TeacherService teacherService) {
        this.teacherService = teacherService;

        setTitle("Add Teacher");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel employee_NoLabel = new JLabel("Employee No:");
        employee_NoField = new JTextField(12);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField(12);

        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField(12);

        JLabel phoneLabel = new JLabel("Phone: ");
        phoneField = new JTextField(12);

        JLabel subjectsNameLabel = new JLabel("Subjects Name: ");
        subjectsNameField = new JTextField(12);

        JLabel coursesNameLabel = new JLabel("Courses Name: ");
        coursesNameField = new JTextField(12);

        JLabel yearsExperienceLabel = new JLabel("Years of Experience: ");
        yearsExperienceSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 9999, 1));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(12);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(12);

        JButton addButton = new JButton("Add Teacher");

        // Layout
        gbc.gridx = 0; gbc.gridy = 1; panel.add(employee_NoLabel, gbc);
        gbc.gridx = 1; panel.add(employee_NoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(fullNameLabel, gbc);
        gbc.gridx = 1; panel.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(emailLabel, gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(phoneLabel, gbc);
        gbc.gridx = 1; panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(subjectsNameLabel, gbc);
        gbc.gridx = 1; panel.add(subjectsNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(coursesNameLabel, gbc);
        gbc.gridx = 1; panel.add(coursesNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(yearsExperienceLabel, gbc);
        gbc.gridx = 1; panel.add(yearsExperienceSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(usernameLabel, gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(passwordLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; panel.add(addButton, gbc);

        add(panel);
        setVisible(true);

        // Add button action
        addButton.addActionListener(e -> {
            try {
                String employee_No = employee_NoField.getText().trim();
                String fullName = fullNameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String subjectNames = subjectsNameField.getText().trim();
                String coursesNames = coursesNameField.getText().trim();
                int yearsExperience = (Integer) yearsExperienceSpinner.getValue();
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (employee_No.isEmpty()|| fullName.isEmpty() || username.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "LC Number, Full Name, and Username are required!");
                    return;
                }

                Teacher t = new Teacher (employee_No, fullName, email, phone, username);
                teacherService.addTeacher(employee_No, fullName, email,phone);

                JOptionPane.showMessageDialog(this, "Teacher added successfully!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    public void prefillData(String employee_No, String fullName, String email, String phone, String subjectsName, String coursesName, int yearsExperience, String username, String password) {
        employee_NoField.setText(employee_No);
        fullNameField.setText(fullName);
        emailField.setText(email);
        phoneField.setText(phone);
        usernameField.setText(username);
        passwordField.setText(password);
    }
}
