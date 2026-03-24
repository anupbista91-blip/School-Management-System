package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;
import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Service.UserService;

public class ResetPasswordWindow {
    private JPanel panel;
    private JPasswordField txtNew;
    private JPasswordField txtConfirm;
    private JButton btnUpdate;

    private String lc;
    private String reg;
    private UserService userService;
    private SchoolGUI gui;

    public ResetPasswordWindow(SchoolGUI gui, UserService userService, String lc, String reg){
        this.gui = gui;
        this.userService = userService;
        this.lc = lc;
        this.reg = reg;
        init();
    }

    private void init(){
        panel = new JPanel(new GridLayout(3,2,10,10));

        txtNew = new JPasswordField();
        txtConfirm = new JPasswordField();
        btnUpdate = new JButton("Update Password");

        panel.add(new JLabel("New Password:"));
        panel.add(txtNew);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(txtConfirm);
        panel.add(btnUpdate);

        btnUpdate.addActionListener(e -> updatePassword());
    }

    private void updatePassword(){
        String p1 = new String(txtNew.getPassword());
        String p2 = new String(txtConfirm.getPassword());

        if(!p1.equals(p2)){
            JOptionPane.showMessageDialog(panel,"Passwords do not match!");
            return;
        }

        userService.resetPassword(lc, reg, p1);

        JOptionPane.showMessageDialog(panel,"Password updated!");
        gui.showLogin();
    }

    public JPanel getPanel(){ return panel; }
}
