/**
 * File: Student.java
 * Purpose: Student model to manage Student works.
 */

package School_Management_System.Model;

public class Student {

    private String lcNumber;
    private int regNo;
    private String fullName;
    private String email;
    private String phone;
    private int semester;
    private String course; // String course names
    private String username;
    private String plainPassword;

    public Student() {}

    public Student(String lcNumber, int regNo, String fullName, String email, String phone, int semester,
                   String course, String username, String plainPassword) {
        this.lcNumber = lcNumber;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.semester = semester;
        this.course = course;
        this.username = username;
        this.plainPassword = plainPassword;
    }

    // ===== Getters & Setters =====
    public String getLcNumber() { return lcNumber; }
    public void setLcNumber(String lcNumber) { this.lcNumber = lcNumber; }

    public int getRegNo() { return regNo; }
    public void setRegNo(int regNo) { this.regNo = regNo; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getplainPassword() { return plainPassword; } // used for initial User creation
    public void setplainPassword(String password) { this.plainPassword = password; }
}