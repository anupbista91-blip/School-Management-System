/**
 * File: RegisterWindow.java
 * Purpose: User registration form UI.
 */
package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;

import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Service.SchoolService;
import School_Management_System.Util.Theme;

/**
 * RegisterWindow - Updated with full user profile support
 */
public class RegisterWindow {

    private JPanel panel;

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtLinkedId;

    private JComboBox<String> cbRole;
    private JButton btnCreate;

    private final SchoolService service = new SchoolService();
    private final SchoolGUI gui;

    public RegisterWindow(SchoolGUI gui) {
        this.gui = gui;
        init();
    }

    private void init() {

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.BACKGROUND);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBackground(Theme.BACKGROUND);
        form.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        txtUser = new JTextField();
        txtPass = new JPasswordField();
        txtFullName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtLinkedId = new JTextField();

        cbRole = new JComboBox<>(new String[]{"STUDENT", "TEACHER", "ADMIN"});
        btnCreate = new JButton("Create Account");

        Theme.styleButton(btnCreate);
        Theme.addHoverEffect(btnCreate);

        // ===== FORM =====
        form.add(new JLabel("Full Name:"));
        form.add(txtFullName);

        form.add(new JLabel("Username:"));
        form.add(txtUser);

        form.add(new JLabel("Password:"));
        form.add(txtPass);

        form.add(new JLabel("Email:"));
        form.add(txtEmail);

        form.add(new JLabel("Phone:"));
        form.add(txtPhone);

        form.add(new JLabel("Role:"));
        form.add(cbRole);

        form.add(new JLabel("LC / ID:"));
        form.add(txtLinkedId);

        form.add(new JLabel());
        form.add(btnCreate);

        panel.add(form);

        btnCreate.addActionListener(e -> onCreate());
    }

    /**
     * Handle registration
     */
    private void onCreate() {

        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String role = (String) cbRole.getSelectedItem();
        String linkedId = txtLinkedId.getText().trim();

        // ===== VALIDATION =====
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                    "Username, Password, and Full Name are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(panel,
                    "Invalid email address!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            service.registerUser(
                    username,
                    password,
                    role,
                    fullName,
                    email,
                    phone,
                    linkedId
            );

            JOptionPane.showMessageDialog(panel,
                    "Account created successfully!");

            gui.showLogin();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(panel,
                    ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel,
                    "Unexpected error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            ex.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}