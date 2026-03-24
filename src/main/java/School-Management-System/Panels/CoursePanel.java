/**
 * File: CoursePanel.java
 * Purpose: Panel to display and create courses.
 */
package School_Management_System.Panels;

import javax.swing.*;
import java.awt.*;
import School_Management_System.Service.CourseService;
import School_Management_System.Model.Course;
import School_Management_System.Widgets.EntityTableModel;
import java.util.List;

/**
 * CoursePanel manages simple course list and creation UI.
 */
public class CoursePanel {
    private final JPanel panel;
    private final CourseService service = new CourseService();
    private final EntityTableModel<Course> tableModel;
    private final JTable table;

    /** Constructs course panel. */
    public CoursePanel() {
        panel = new JPanel(new BorderLayout());
        tableModel = new EntityTableModel<>(new String[] {"Course_Id","Code","Name","Credits"}, c -> new Object[] {
                c.getCourse_id(), c.getCode(), c.getName(), c.getCredits()
        });
        table = new JTable(tableModel);
        refresh();

        JPanel form = new JPanel(new GridLayout(10,4,6,6));
        JTextField course_id = new JTextField();
        JTextField code = new JTextField();
        JTextField name = new JTextField();
        JTextField credits = new JTextField();
        JButton add = new JButton("Add");

        form.add(code); form.add(name); form.add(credits); form.add(add);

        add.addActionListener(e -> {
            // Parse course_id as integer
            int c_id = 0;
            try {
                c_id = Integer.parseInt(course_id.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Course ID must be a number!");
                return;
            }

            String cd = code.getText().trim();
            String n = name.getText().trim();

            int cr = 3; // default credits
            try { cr = Integer.parseInt(credits.getText().trim()); } catch (ArithmeticException ex) {}

            service.createCourse(c_id, cd, n, cr);
            refresh();

            // Clear input fields
            course_id.setText("");
            code.setText("");
            name.setText("");
            credits.setText("");
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(form, BorderLayout.SOUTH);
    }

    /** Refreshes table data. */
    private void refresh() {
        List<Course> list = service.getAllCourses();
        tableModel.setItems(list);
    }

    /** Returns panel. */
    public JPanel getPanel() { return panel; }
}