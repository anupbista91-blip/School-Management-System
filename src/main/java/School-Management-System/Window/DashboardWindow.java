/**
 * File: DashboardWindow.java
 * Purpose: Dashboard UI that adapts controls and visible sections based on user role.
 */
package School_Management_System.Window;

import javax.swing.*;
import java.awt.*;

import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Model.User;
import School_Management_System.Panels.*;
import School_Management_System.Service.*;
import School_Management_System.Util.Theme;

/**
 * Unified Dashboard Window (All roles + Admin features)
 */
public class DashboardWindow {

    private JPanel panel;
    private JLabel lblWelcome;
    private JPanel navPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final SchoolGUI gui;
    private final StudentService studentService;
    private final CourseService courseService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final RegistrationService registrationService;

    private User currentUser;

    // Admin special panel
    private AdminPanel pendingPanel;

    public DashboardWindow(SchoolGUI gui,
                           StudentService studentService,
                           CourseService courseService,
                           UserService userService,
                           TeacherService teacherService,
                           RegistrationService registrationService) {

        this.gui = gui;
        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
        this.teacherService = teacherService;
        this.registrationService = registrationService;

        initComponents();
    }

    private void initComponents() {

        panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BACKGROUND);

        // ===== TOP =====
        lblWelcome = new JLabel("Welcome");
        lblWelcome.setFont(Theme.TITLE_FONT);
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10));
        panel.add(lblWelcome, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(Theme.SIDEBAR);
        panel.add(navPanel, BorderLayout.WEST);

        // ===== CONTENT =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Default panels
        contentPanel.add(createHomePanel(), "home");

        StudentPanel studentPanel = new StudentPanel(studentService, courseService, userService);
        contentPanel.add(studentPanel.getPanel(), "students");

        TeacherPanel teacherPanel = new TeacherPanel(teacherService);
        contentPanel.add(teacherPanel.getPanel(), "teachers");

        CoursePanel coursePanel = new CoursePanel();
        contentPanel.add(coursePanel.getPanel(), "courses");

        // ✅ Pending Requests Panel
        pendingPanel = new AdminPanel(
                userService, studentService, teacherService,  courseService, registrationService
        );
        contentPanel.add(pendingPanel.getPanel(), "pending");

        // ✅ Start notifier
        new AdminPanel(userService, studentService, teacherService, courseService, registrationService);
    }

    // ===== USER SET =====
    public void setWelcomeUser(User user) {
        this.currentUser = user;

        if (user != null) {
            lblWelcome.setText("Welcome, " + user.getFullName());
            buildNavForRole(user.getRole());
        }

        cardLayout.show(contentPanel, "home");
    }

    // ===== NAVIGATION =====
    private void buildNavForRole(User.Role role) {

        navPanel.removeAll();

        navPanel.add(createNavButton("Home", () -> show("home")));

        // ===== PROFILE =====
        if (role == User.Role.STUDENT || role == User.Role.TEACHER) {
            navPanel.add(createNavButton("Profile", () -> {
                contentPanel.add(createProfilePanel(), "profile");
                show("profile");
            }));
        }

        // ===== ADMIN FEATURES =====
        if (role == User.Role.ADMIN) {

            navPanel.add(createNavButton("Admin Panel", () -> {
                AdminPanel adminPanel = new AdminPanel(
                        userService, studentService, teacherService, courseService, registrationService
                );
                contentPanel.add(adminPanel.getPanel(), "admin");
                show("admin");
            }));

            navPanel.add(createNavButton("Students", () -> show("students")));
            navPanel.add(createNavButton("Teachers", () -> show("teachers")));
            navPanel.add(createNavButton("Pending Requests", () -> show("pending")));
        }

        // ===== COURSES =====
        navPanel.add(createNavButton("Courses", () -> show("courses")));

        // ===== LOGOUT =====
        navPanel.add(Box.createVerticalGlue());
        navPanel.add(createNavButton("Logout", () -> gui.showLogin()));

        navPanel.revalidate();
        navPanel.repaint();
    }

    private JButton createNavButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        Theme.styleButton(btn);
        Theme.addHoverEffect(btn);

        btn.addActionListener(e -> action.run());

        navPanel.add(btn);
        navPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        return btn;
    }

    private void show(String name) {
        cardLayout.show(contentPanel, name);
    }

    // ===== HOME =====
    private JPanel createHomePanel() {
        JPanel home = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Dashboard Home", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        home.add(label, BorderLayout.CENTER);
        return home;
    }

    // ===== PROFILE =====
    private JPanel createProfilePanel() {

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(new JLabel("Full Name: " + currentUser.getFullName()));
        panel.add(new JLabel("Username: " + currentUser.getUsername()));
        panel.add(new JLabel("Email: " + currentUser.getEmail()));
        panel.add(new JLabel("Phone: " + currentUser.getPhone()));
        panel.add(new JLabel("Role: " + currentUser.getRole()));

        return panel;
    }

    public JPanel getPanel() {
        return panel;
    }
}