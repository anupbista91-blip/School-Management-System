package School_Management_System.Forms;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Service.RegistrationService;

public class RegistrationRequestForm extends JFrame {

    private final RegistrationService registrationService;

    public RegistrationRequestForm(RegistrationService registrationService) {
        this.registrationService = registrationService;

        setTitle("Request Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== FIELDS =====
        JTextField idField = new JTextField(12);
        JTextField nameField = new JTextField(12);
        JTextField emailField = new JTextField(12);
        JTextField phoneField = new JTextField(12);
        JTextField usernameField = new JTextField(12);
        JTextField passwordField = new JTextField(12);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"STUDENT", "TEACHER"});

        JButton submitButton = new JButton("Send Request");

        // ===== LAYOUT =====
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("LC Number / Employee No:"), gbc);
        gbc.gridx = 1; panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1; panel.add(roleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        add(panel);
        setVisible(true);

        // ===== ACTION =====
        submitButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String fullName = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();

            if (id.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all required fields!");
                return;
            }

            boolean success;

            if ("STUDENT".equals(role)) {
                RegistrationService.StudentRegistrationRequest req =
                        new RegistrationService.StudentRegistrationRequest(
                                id, fullName, email, phone, username, password
                        );
                success = registrationService.sendStudentRequest(req);

            } else {
                RegistrationService.TeacherRegistrationRequest req =
                        new RegistrationService.TeacherRegistrationRequest(
                                id, fullName, email, phone, username, password
                        );
                success = registrationService.sendTeacherRequest(req);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Request sent!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed or duplicate request.");
            }
        });
    }
}