/**
 * Filename: Course.java.
 * Purpose: To handle Course related matters.
 */
package School_Management_System.Model;

/**
 * Course class representing a course in the system.
 * Handles course ID, code, name, and credits.
 */
public class Course {
    private int course_id;    // Unique external ID for the course
    private String code;      // Course code (e.g., CS101)
    private String name;      // Course name (e.g., Computer Science)
    private int credits;      // Credit units

    /**
     * Default constructor for frameworks or GUI binding.
     */
    public Course() {}

    /**
     * Constructs a Course with full details
     * @param course_id database ID
     * @param code course code
     * @param name course name
     * @param credits credit units
     */
    public Course(int course_id, String code, String name, int credits) {
        this.course_id = course_id;
        this.code = code;
        this.name = name;
        this.credits = credits;
    }

    // ===========================
    // Getters & Setters
    // ===========================

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {  // Allow updating code
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {  // Allow updating name
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {  // Allow updating credits
        this.credits = credits;
    }

    // ===========================
    // toString for GUI Display
    // ===========================
    @Override
    public String toString() {
        return String.format("%s (%s) - %d credits", name, code, credits);
    }
}