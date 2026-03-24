package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;
import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Service.UserService;

public class ForgotPasswordWindow {
    private JPanel panel;
    private JTextField txtLC;
    private JTextField txtRegNo;
    private JButton btnVerify;
    private SchoolGUI gui;
    private UserService userService;

    public ForgotPasswordWindow(SchoolGUI gui, UserService userService) {
        this.gui = gui;
        this.userService = userService;
        init();
    }

    private void init() {
        panel = new JPanel(new GridLayout(3,2,10,10));

        txtLC = new JTextField();
        txtRegNo = new JTextField();
        btnVerify = new JButton("Verify");

        panel.add(new JLabel("LC Number:"));
        panel.add(txtLC);
        panel.add(new JLabel("Reg No:"));
        panel.add(txtRegNo);
        panel.add(btnVerify);

        btnVerify.addActionListener(e -> verifyUser());
    }

    private void verifyUser() {
        String lc = txtLC.getText().trim();
        String reg = txtRegNo.getText().trim();

        boolean valid = userService.verifyByLCAndReg(lc, reg);

         if(valid){
            gui.showResetPassword(lc, reg);
        } else {
            JOptionPane.showMessageDialog(panel,"Invalid details!");
        }
    }

    public JPanel getPanel(){ return panel; }
}
