/**
 * File: SchoolGUI.java
 * Purpose: Coordinates windows and acts as a simple GUI controller.
 */
package School_Management_System.GUI;

import School_Management_System.Model.User;
import School_Management_System.Service.*;
import School_Management_System.Window.LoginWindow;
import School_Management_System.Window.RegisterWindow;
import School_Management_System.Window.DashboardWindow;
import School_Management_System.Util.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * SchoolGUI orchestrates all windows and panels.
 */
public class SchoolGUI {

    private final JFrame mainFrame;
    private final LoginWindow loginWindow;
    private final RegisterWindow registerWindow;
    private final DashboardWindow dashboardWindow;

    private final StudentService studentService;
    private final CourseService courseService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final RegistrationService registrationService;

    public SchoolGUI(StudentService studentService,
                     CourseService courseService,
                     UserService userService,
                     TeacherService teacherService,
                     RegistrationService registrationService) {

        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
        this.teacherService = teacherService;
        this.registrationService = registrationService;

        mainFrame = new JFrame("School Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setMinimumSize(new Dimension(850, 550));
        mainFrame.setLocationRelativeTo(null);

        // Windows
        loginWindow = new LoginWindow(this);
        registerWindow = new RegisterWindow(this);
        dashboardWindow = new DashboardWindow(this, studentService, courseService, userService, teacherService, registrationService);

        showLogin();
    }

    /** Show Login */
    public void showLogin() {
        mainFrame.setContentPane(loginWindow.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    /** Show Dashboard with FULL USER */
    public void showDashboard(User user) {
        dashboardWindow.setWelcomeUser(user); // ✅ FIX
        mainFrame.setContentPane(dashboardWindow.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /** Student Registration Request (unchanged logic) */
    public void showStudentRegistrationRequest() {
        JPanel requestPanel = new JPanel(new GridBagLayout());
        requestPanel.setBackground(Theme.BACKGROUND);

        JTextField txtId = new JTextField(15);
        JTextField txtFullName = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JTextField txtPhone = new JTextField(15);
        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPasswordField = new JPasswordField(15);
        JButton btnSendRequest = new JButton("Send Request");

        Theme.styleButton(btnSendRequest);
        Theme.addHoverEffect(btnSendRequest);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("LC Number:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtId, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtFullName, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtEmail, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtPhone, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtUsername, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtPasswordField, gbc); y++;

        gbc.gridx = 1; gbc.gridy = y;
        requestPanel.add(btnSendRequest, gbc);

        btnSendRequest.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPasswordField.getPassword()).trim();

            if (id.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Required fields missing!",
                        "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = studentService.createStudentRequest(
                    id, name, email, phone, username, password
            );

            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Request submitted!");
                showLogin();
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Failed to submit request.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(requestPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /** Teacher Registration Request (unchanged logic) */
    public void showTeacherRegistrationRequest() {
        JPanel requestPanel = new JPanel(new GridBagLayout());
        requestPanel.setBackground(Theme.BACKGROUND);

        JTextField txtId = new JTextField(15);
        JTextField txtFullName = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JTextField txtPhone = new JTextField(15);
        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPasswordField = new JPasswordField(15);
        JButton btnSendRequest = new JButton("Send Request");

        Theme.styleButton(btnSendRequest);
        Theme.addHoverEffect(btnSendRequest);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Employee No:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtId, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtFullName, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtEmail, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtPhone, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtUsername, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; requestPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; requestPanel.add(txtPasswordField, gbc); y++;

        gbc.gridx = 1; gbc.gridy = y;
        requestPanel.add(btnSendRequest, gbc);

        btnSendRequest.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPasswordField.getPassword()).trim();

            if (id.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Required fields missing!",
                        "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = teacherService.createTeacherRequest(
                    id, name, email, phone, username, password
            );

            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Request submitted!");
                showLogin();
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Failed to submit request.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(requestPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /** Show Forgot Password Window (initial verification by LC + Reg) */
    public void showForgotPassword() {
        JPanel forgotPanel = new JPanel(new GridBagLayout());
        forgotPanel.setBackground(Theme.BACKGROUND);

        JTextField txtID = new JTextField(15);
        JTextField txtReg = new JTextField(15);
        JButton btnVerify = new JButton("Verify");

        Theme.styleButton(btnVerify);
        Theme.addHoverEffect(btnVerify);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0; gbc.gridy = 0; forgotPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; forgotPanel.add(txtID, gbc);
        gbc.gridx = 0; gbc.gridy = 1; forgotPanel.add(new JLabel("Reg Number:"), gbc);
        gbc.gridx = 1; forgotPanel.add(txtReg, gbc);
        gbc.gridx = 1; gbc.gridy = 2; forgotPanel.add(btnVerify, gbc);

        btnVerify.addActionListener(e -> {
            String id = txtID.getText().trim();
            String reg = txtReg.getText().trim();

            if(id.isEmpty() || reg.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Please fill all fields!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean valid = userService.verifyByLCAndReg(id, reg);
            if(valid){
                showResetPassword(id, reg); // move to reset password panel
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid ID or Reg Number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(forgotPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /** Show Reset Password Window (actual password change) */
    public void showResetPassword(String id, String reg) {
        JPanel resetPanel = new JPanel(new GridBagLayout());
        resetPanel.setBackground(Theme.BACKGROUND);

        JPasswordField txtNewPass = new JPasswordField(15);
        JButton btnReset = new JButton("Reset Password");

        Theme.styleButton(btnReset);
        Theme.addHoverEffect(btnReset);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0; gbc.gridy = 0; resetPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1; resetPanel.add(txtNewPass, gbc);
        gbc.gridx = 1; gbc.gridy = 1; resetPanel.add(btnReset, gbc);

        btnReset.addActionListener(e -> {
            String newPass = new String(txtNewPass.getPassword()).trim();

            if(newPass.isEmpty()){
                JOptionPane.showMessageDialog(mainFrame, "Please enter a new password!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = userService.resetPassword(id, reg, newPass);
            if(success){
                JOptionPane.showMessageDialog(mainFrame, "Password updated successfully!");
                showLogin();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(resetPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /** Launch GUI */
    public void launchGUI(StudentService studentService,
                          CourseService courseService,
                          UserService userService) {

        SwingUtilities.invokeLater(() ->
                new SchoolGUI(studentService, courseService, userService, teacherService, registrationService));
    }
}
