/**
 * File: Teacher.java
 * Purpose: Teacher model extending Person.
 */
package School_Management_System.Model;

/**
 * Teacher class with subject specialization and experience.
 */

public class Teacher {

    private String employeeNo;
    private String fullName;
    private String email;
    private String phone;
    private String username;
    private String password;      // plain for creation only

    public Teacher(String employeeNo, String fullName, String email, String phone,
                   String username) {
        this.employeeNo = employeeNo;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    // ===== Getters & Setters =====
    public String  getEmployeeNo() { return employeeNo; }
    public void setEmployeeNo(String employeeNo) { this.employeeNo = employeeNo; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; } // for initial User creation
    public void setPassword(String password) { this.password = password; }
}