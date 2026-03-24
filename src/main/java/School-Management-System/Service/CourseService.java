/**
 * File: CourseService.java
 * Purpose: Service to manage Course-related operations.
 */
package School_Management_System.Service;

import School_Management_System.DataBase_Connection.DBConnection;
import School_Management_System.Model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseService handles course CRUD in PostgreSQL.
 */
public class CourseService {

    // =========================
    // GET ALL COURSES
    // =========================
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getInt("credits")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }

    // =========================
    // CREATE COURSE
    // =========================
    public boolean createCourse(int course_id, String code, String name, int credits) {
        String sql = "INSERT INTO courses (course_id, code, name, credits) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, course_id);
            ps.setString(2, code);
            ps.setString(3, name);
            ps.setInt(4, credits);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error creating course: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // GET COURSE BY ID
    // =========================
    public Course getCourseById(int course_id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, course_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("course_id"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getInt("credits")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // UPDATE COURSE
    // =========================
    public boolean updateCourse(Course updatedCourse) {
        String sql = "UPDATE courses SET code = ?, name = ?, credits = ? WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, updatedCourse.getCode());
            ps.setString(2, updatedCourse.getName());
            ps.setInt(3, updatedCourse.getCredits());
            ps.setInt(4, updatedCourse.getCourse_id());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating course: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // DELETE COURSE
    // =========================
    public boolean deleteCourse(int course_id) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, course_id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting course: " + e.getMessage());
            return false;
        }
    }
}
