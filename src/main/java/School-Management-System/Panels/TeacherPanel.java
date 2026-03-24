package School_Management_System.Panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import School_Management_System.Service.TeacherService;
import School_Management_System.Model.Teacher;
import School_Management_System.Forms.AddTeacherForm;

public class TeacherPanel {

    private final JPanel panel;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final TeacherService teacherService;

    public TeacherPanel(TeacherService teacherService) {
        this.teacherService = teacherService;

        panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        tableModel = new DefaultTableModel(
                new Object[]{"Employee No","Full Name","Phone","Username"},0
        );
        table = new JTable(tableModel);
        refresh();

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JButton addButton = new JButton("Add Teacher");
        JButton refreshButton = new JButton("Refresh Table");

        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> new AddTeacherForm(teacherService));
        refreshButton.addActionListener(e -> refresh());
    }

    public void refresh() {
        tableModel.setRowCount(0);
        List<Teacher> teachers = teacherService.getAllTeachers();
        for(Teacher t : teachers) {
            tableModel.addRow(new Object[]{
                    t.getEmployeeNo(),
                    t.getFullName(),
                    t.getPhone(),
                    t.getUsername()
            });
        }
    }

    public JPanel getPanel() { return panel; }
}