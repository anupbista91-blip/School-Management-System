package School_Management_System.Forms;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Service.UserService;

/**
 * ForgotPasswordForm allows a user to reset their password securely.
 * User must verify LC_Number and RegNo before updating the password.
 */
public class ForgotPasswordForm extends JFrame {

    private final UserService userService;

    public ForgotPasswordForm(UserService userService) {
        this.userService = userService;

        setTitle("Forgot Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== INPUT FIELDS =====
        JLabel lcLabel = new JLabel("LC Number:");
        JTextField lcField = new JTextField(12);

        JLabel regLabel = new JLabel("Reg No:");
        JSpinner regSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 9999, 1));

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField(12);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        JPasswordField confirmField = new JPasswordField(12);

        JButton submitButton = new JButton("Reset Password");

        // ===== LAYOUT =====
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lcLabel, gbc);
        gbc.gridx = 1; panel.add(lcField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(regLabel, gbc);
        gbc.gridx = 1; panel.add(regSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(passwordLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(confirmLabel, gbc);
        gbc.gridx = 1; panel.add(confirmField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(submitButton, gbc);

        add(panel);
        setVisible(true);

        // ===== BUTTON ACTION =====
        submitButton.addActionListener(e -> {
            try {
                String lcNumber = lcField.getText().trim();
                int regNo = (Integer) regSpinner.getValue();
                String newPassword = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmField.getPassword());

                if (lcNumber.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match!");
                    return;
                }

                // Verify LC_Number + RegNo
                boolean verified = userService.verifyUser(lcNumber, regNo);
                if (!verified) {
                    JOptionPane.showMessageDialog(this, "LC Number or Reg No is incorrect!");
                    return;
                }

                // Update password securely
                userService.updatePassword(lcNumber, newPassword);

                JOptionPane.showMessageDialog(this, "Password updated successfully! Please login.");
                dispose(); // Close the forgot password window

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}

