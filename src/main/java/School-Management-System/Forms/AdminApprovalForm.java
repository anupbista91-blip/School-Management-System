package School_Management_System.Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import School_Management_System.Model.Teacher;
import School_Management_System.Service.RegistrationService;
import School_Management_System.Service.StudentService;
import School_Management_System.Service.TeacherService;
import School_Management_System.Service.UserService;

public class AdminApprovalForm extends JFrame {

    private final RegistrationService registrationService;
    private  final StudentService studentService;
    private final TeacherService teacherService;
    private final UserService userService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public AdminApprovalForm(RegistrationService registrationService,
                             StudentService studentService,
                             TeacherService teacherService,
                             UserService userService) {

        this.registrationService = registrationService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;

        setTitle("Pending Requests");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Full Name", "Email", "Phone", "Role"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        JPanel panel = new JPanel();
        panel.add(approveBtn);
        panel.add(rejectBtn);

        add(panel, BorderLayout.SOUTH);

        loadData();

        approveBtn.addActionListener(this::approve);
        rejectBtn.addActionListener(this::reject);

        setVisible(true);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        // STUDENTS
        for (RegistrationService.StudentRegistrationRequest r :
                registrationService.getAllStudentRequests()) {

            tableModel.addRow(new Object[]{
                    r.id, r.fullName, r.email, r.phone, "STUDENT"
            });
        }

        // TEACHERS
        for (RegistrationService.TeacherRegistrationRequest r :
                registrationService.getAllTeacherRequests()) {

            tableModel.addRow(new Object[]{
                    r.id, r.fullName, r.email, r.phone, "TEACHER"
            });
        }
    }

    private void approve(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        String fullName = tableModel.getValueAt(row, 0).toString();
        String email = tableModel.getValueAt(row, 1).toString();
        String phone = tableModel.getValueAt(row, 2).toString();
        String role = tableModel.getValueAt(row, 3).toString();
        String username = tableModel.getValueAt(row, 4).toString();
        String plainPassword = tableModel.getValueAt(row, 5).toString();
        String id = tableModel.getValueAt(row, 6).toString();

        boolean success;

        if ("STUDENT".equals(role)) {
            // Build student request object from table row
            RegistrationService.StudentRegistrationRequest req =
                    new RegistrationService.StudentRegistrationRequest(id, fullName, email, phone, username, plainPassword);

            success = registrationService.approveStudentRequest(req, studentService, userService);
        } else {
            // Build teacher request object from table row
            RegistrationService.TeacherRegistrationRequest req1 =
                    new RegistrationService.TeacherRegistrationRequest(id, fullName, email, phone, username, plainPassword);

            success = registrationService.approveTeacherRequest(req1, teacherService, userService);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Approved!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Approval failed!");
        }
    }

    private void reject(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        String id = tableModel.getValueAt(row, 0).toString();
        String role = tableModel.getValueAt(row, 4).toString();

        boolean success;

        if ("STUDENT".equals(role)) {
            success = registrationService.deleteStudentRequest(id);
        } else {
            success = registrationService.deleteTeacherRequest(id);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Rejected!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Reject failed!");
        }
    }
}