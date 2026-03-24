package School_Management_System.Panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import School_Management_System.Service.CourseService;
import School_Management_System.Service.StudentService;
import School_Management_System.Model.Student;
import School_Management_System.Forms.AddStudentForm;
import School_Management_System.Service.UserService;

/**
 * StudentPanel displays a table of students.
 * The form to add a new student is opened via AddStudentForm window.
 */
public class StudentPanel {

    private final JPanel panel;
    private final JTable table;
    private final DefaultTableModel tableModel;

    private final UserService userService;
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentPanel(StudentService studentService,
                        CourseService courseService,
                        UserService userService) {

        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;

        // ===== MAIN PANEL =====
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== TABLE SETUP =====
        tableModel = new DefaultTableModel(
                new Object[]{"LcNumber","Reg No","Full Name","Email","Phone","Semester","Course","Username", "plainPassword"}, 0
        );
        table = new JTable(tableModel);
        refresh(); // Populate table initially

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addButton = new JButton("Add Student");
        JButton refreshButton = new JButton("Refresh Table");

        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // ===== BUTTON ACTIONS =====
        addButton.addActionListener(e -> {
            // Open professional AddStudentForm window
            new AddStudentForm(studentService, courseService, userService, this);
        });

        refreshButton.addActionListener(e -> refresh());
    }

    /**
     * Refreshes the table with current student data.
     */
    public void refresh() {
        tableModel.setRowCount(0); // clear existing rows
        List<Student> list = studentService.getAllStudents();
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                    s.getLcNumber(),
                    s.getRegNo(),
                    s.getFullName(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getSemester(),
                    s.getCourse(),
                    s.getUsername(),
                    s.getplainPassword()
            });
        }
    }

    /**
     * Returns the panel to embed in GUI.
     */
    public JPanel getPanel() {
        return panel;
    }
}