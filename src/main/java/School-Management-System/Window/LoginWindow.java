/**
 * File: LoginWindow.java
 * Purpose: Login window UI and logic.
 */
package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;
import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Service.SchoolService;
import School_Management_System.Model.User;
import School_Management_System.Util.Theme;

/**
 * LoginWindow implements a simple login form with username/password
 * and supports Forgot Password & Registration request.
 */
public class LoginWindow extends JPanel {

    private final SchoolGUI gui;
    private final SchoolService service;

    private JPanel panel;
    private JPanel form;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JButton btnStudentRegister;
    private JButton btnTeacherRegister;
    private JButton btnForgot;

    public LoginWindow(SchoolGUI gui) {
        this.gui = gui;
        this.service = new SchoolService(); // your auth service

        initComponents();
    }

    private void initComponents() {

        // Main container
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.BACKGROUND);

        // Form panel
        form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.PRIMARY, 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Username
        JLabel lblUser = new JLabel("Username:");
        txtUser = new JTextField(18);
        gbc.gridx = 0; gbc.gridy = 0; form.add(lblUser, gbc);
        gbc.gridx = 1; form.add(txtUser, gbc);

        // Password
        JLabel lblPass = new JLabel("Password:");
        txtPass = new JPasswordField(18);
        gbc.gridx = 0; gbc.gridy = 1; form.add(lblPass, gbc);
        gbc.gridx = 1; form.add(txtPass, gbc);

        // Buttons
        btnLogin = new JButton("Login");
        btnStudentRegister = new JButton("Student Request Account");
        btnTeacherRegister = new JButton("Teacher Request Account");
        btnForgot = new JButton("Forgot Password?");

        Theme.styleButton(btnLogin);
        Theme.styleButton(btnStudentRegister);
        Theme.styleButton(btnTeacherRegister);
        Theme.styleButton(btnForgot);

        Theme.addHoverEffect(btnLogin);
        Theme.addHoverEffect(btnStudentRegister);
        Theme.addHoverEffect(btnTeacherRegister);
        Theme.addHoverEffect(btnForgot);

        gbc.gridx = 0; gbc.gridy = 2; form.add(btnLogin, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(btnForgot, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(btnStudentRegister, gbc);
        gbc.gridx = 1; gbc.gridy = 3; form.add(btnTeacherRegister, gbc);

        // Add form to panel
        panel.add(form);

        // Add main panel to this JPanel
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        // ===== Action Listeners =====
        btnLogin.addActionListener(e -> onLogin());
        btnStudentRegister.addActionListener(e -> gui.showStudentRegistrationRequest());
        btnTeacherRegister.addActionListener(e -> gui.showTeacherRegistrationRequest());
        btnForgot.addActionListener(e -> gui.showForgotPassword());
    }

    private void onLogin() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter username and password!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = service.authenticate(username, password);
        if (user != null) {
            gui.showDashboard(user);
        } else {
            JOptionPane.showMessageDialog(panel, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}