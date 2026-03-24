package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;

import School_Management_System.Panels.*;
import School_Management_System.Service.*;
import School_Management_System.Forms.LoginForm;

/**
 * AdminDashboard is the main window for admin to manage the school system.
 * Contains tabs for Students, Teachers, Courses, Pending Requests.
 * Includes real-time notification for new registration requests with row highlighting.
 */
public class AdminDashboard extends JFrame {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public AdminDashboard(UserService userService,
                          StudentService studentService,
                          TeacherService teacherService,
                          CourseService courseService,
                          RegistrationService registrationService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.registrationService = registrationService;

        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== MAIN PANEL =====
        JTabbedPane tabbedPane = new JTabbedPane();

        // Admin panel (welcome + logout)
        JPanel adminPanel = createAdminPanel();
        tabbedPane.addTab("Admin Panel", adminPanel);

        // Student panel
        StudentPanel studentPanel = new StudentPanel(studentService, courseService, userService);
        tabbedPane.addTab("Students", studentPanel.getPanel());

        // Teacher panel
        TeacherPanel teacherPanel = new TeacherPanel(teacherService);
        tabbedPane.addTab("Teachers", teacherPanel.getPanel());

        // Courses panel
        CoursePanel coursePanel = new CoursePanel();
        tabbedPane.addTab("Courses", coursePanel.getPanel());

        // Pending Requests panel
        AdminPanel requestsPanel = new AdminPanel(
                userService, studentService, teacherService, courseService, registrationService
        );
        tabbedPane.addTab("Pending Requests", requestsPanel.getPanel());

        add(tabbedPane);
        setVisible(true);

        // ===== Real-time Pending Requests Notifier =====
        new AdminPanel(userService, studentService, teacherService, courseService, registrationService); // start notifications with highlighting
    }

    /**
     * Creates the top Admin panel with welcome message and logout.
     */
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // close dashboard
            new LoginForm(userService, studentService, teacherService, courseService, registrationService); // open login form
        });

        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);

        return panel;
    }
}
