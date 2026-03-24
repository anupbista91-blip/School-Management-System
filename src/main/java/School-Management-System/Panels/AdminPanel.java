package School_Management_System.Panels;

import School_Management_System.Service.*;
import School_Management_System.Forms.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Combined Admin Panel:
 * - Dashboard stats
 * - Pending Requests Management
 * - Real-time notifier + highlighting
 */
public class AdminPanel {

    private final JPanel panel;

    // Services
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    // ===== Pending Request UI =====
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel pendingBadge;

    // ===== Notifier =====
    private final Set<String> displayedLCNumbers = new HashSet<>();
    private final Set<String> newRequests = new HashSet<>();

    public AdminPanel(UserService userService,
                      StudentService studentService,
                      TeacherService teacherService,
                      CourseService courseService,
                      RegistrationService registrationService) {

        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.registrationService = registrationService;

        panel = new JPanel(new BorderLayout(10,10));

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Dashboard", createDashboard());
        tabs.addTab("Pending Requests", createPendingRequestsPanel());

        panel.add(tabs);

        // Start notifier
        startNotifier();
    }

    // ================= DASHBOARD =================
    private JPanel createDashboard() {
        JPanel dash = new JPanel(new BorderLayout(10,10));
        dash.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel welcome = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel stats = new JPanel(new GridLayout(2,2,20,20));

        stats.add(new JLabel("Students: " + studentService.getAllStudents().size()));
        stats.add(new JLabel("Teachers: " + teacherService.getAllTeachers().size()));
        stats.add(new JLabel("Courses: " + courseService.getAllCourses().size()));
        stats.add(new JLabel("Pending: " + registrationService.getPendingStudentRequests().size()));
        stats.add(new JLabel("Pending: " + registrationService.getPendingTeacherRequests().size()));


        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(panel).dispose();
            new LoginForm(userService, studentService, teacherService, courseService, registrationService);
        });

        dash.add(welcome, BorderLayout.NORTH);
        dash.add(stats, BorderLayout.CENTER);
        dash.add(logout, BorderLayout.SOUTH);

        return dash;
    }

    // ================= PENDING PANEL =================
    private JPanel createPendingRequestsPanel() {
        JPanel p = new JPanel(new BorderLayout(10,10));

        // Table
        tableModel = new DefaultTableModel(
                new Object[]{"ID","Name","Email","Phone","Role","Status"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };

        table = new JTable(tableModel);

        // Highlight renderer
        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable t,Object val,boolean sel,boolean foc,int r,int c){
                Component comp = super.getTableCellRendererComponent(t,val,sel,foc,r,c);
                String lc = t.getValueAt(r,0).toString();
                if(newRequests.contains(lc)) comp.setBackground(Color.YELLOW);
                else comp.setBackground(sel? t.getSelectionBackground():Color.WHITE);
                return comp;
            }
        });

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        // Top
        JPanel top = new JPanel();
        pendingBadge = new JLabel();
        top.add(new JLabel("Pending Requests"));
        top.add(pendingBadge);

        p.add(top, BorderLayout.NORTH);

        // Buttons
        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");
        JButton refresh = new JButton("Refresh");

        approve.addActionListener(e -> approveSelected());
        reject.addActionListener(e -> rejectSelected());
        refresh.addActionListener(e -> refreshTable());

        JPanel bottom = new JPanel();
        bottom.add(approve);
        bottom.add(reject);
        bottom.add(refresh);

        p.add(bottom, BorderLayout.SOUTH);

        refreshTable();
        return p;
    }

    // ================= LOGIC =================

    private void refreshTable() {
        tableModel.setRowCount(0);

        List<RegistrationService.StudentRegistrationRequest>
                list = registrationService.getPendingStudentRequests();

        for (RegistrationService.StudentRegistrationRequest r : list) {
            tableModel.addRow(new Object[]{
                    r.id, r.fullName, r.email,
                    r.phone, r.username, r.plainPassword, r.status
            });
        }

        pendingBadge.setText("Count: " + list.size());

        List<RegistrationService.TeacherRegistrationRequest>
        list1 = registrationService.getPendingTeacherRequests();

        for (RegistrationService.TeacherRegistrationRequest r : list1) {
            tableModel.addRow(new Object[]{
                    r.id, r.fullName, r.email,
                    r.phone, r.username, r.plainPassword, r.status
            });
        }

        pendingBadge.setText("Count: " + list.size());
    }

    private void approveSelected() {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){
            JOptionPane.showMessageDialog(panel,"Select row!");
            return;
        }

        for(int r: rows){
            String lc = tableModel.getValueAt(r,0).toString();
            registrationService.updateStudentStatus(lc,"APPROVED");
        }

        refreshTable();
    }

    private void rejectSelected() {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){
            JOptionPane.showMessageDialog(panel,"Select row!");
            return;
        }

        for(int r: rows){
            String lc = tableModel.getValueAt(r,0).toString();
            registrationService.updateStudentStatus(lc,"REJECTED");
        }

        refreshTable();
    }

    // ================= NOTIFIER =================
    private void startNotifier() {

        List<RegistrationService.StudentRegistrationRequest> initial =
                registrationService.getPendingStudentRequests();

        for(var r: initial) displayedLCNumbers.add(r.id);

        new Timer(10000, e -> checkNew()).start();
    }

    private void checkNew() {
        List<RegistrationService.StudentRegistrationRequest> list =
                registrationService.getPendingStudentRequests();

        List<String> newLC = list.stream()
                .map(r -> r.id)
                .filter(lc -> !displayedLCNumbers.contains(lc))
                .collect(Collectors.toList());

        if(!newLC.isEmpty()){
            highlight(newLC);

            JOptionPane.showMessageDialog(panel,
                    "New Request Arrived!",
                    "Notification",
                    JOptionPane.INFORMATION_MESSAGE);

            displayedLCNumbers.addAll(newLC);
        }

        refreshTable();
    }

    private void highlight(List<String> lcs){
        newRequests.clear();
        newRequests.addAll(lcs);
        table.repaint();

        new Timer(10000, e->{
            newRequests.clear();
            table.repaint();
            ((Timer)e.getSource()).stop();
        }).start();
    }

    // ================= GETTER =================
    public JPanel getPanel() {
        return panel;
    }
}
