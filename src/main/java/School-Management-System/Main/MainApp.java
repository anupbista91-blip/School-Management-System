/**
 * MainApp.java
 *
 * Entry point of the School Management System application.
 * This file initializes all the core services (Student, Teacher, Course, Registration, User)
 * and launches the GUI with the login screen.
 * It also sets the system look and feel to make the GUI look native.
 */

package School_Management_System.Main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import School_Management_System.GUI.SchoolGUI;
import School_Management_System.Service.*;

public class MainApp {

    public static void main(String[] args) {

        // Set the GUI to match the system's look and feel
        // This makes the app look native on Windows, macOS, Linux, etc.
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print the error if setting look and feel fails
            e.printStackTrace();
        }

        // Initialize all services classes
        // These services handle business logic for students, courses, teachers, registrations, and users
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        TeacherService teacherService = new TeacherService();
        RegistrationService registrationService = new RegistrationService();
        UserService userService = new UserService(studentService, teacherService, courseService, registrationService);

        // Launch the GUI on the Event Dispatch Thread (EDT)
        // Swing is single-threaded, so GUI operations must run on the EDT to avoid issues
        SwingUtilities.invokeLater(() -> {
            // Create the main GUI window and show the login screen
            new SchoolGUI(studentService, courseService, userService, teacherService, registrationService).showLogin();
        });
    }
}
