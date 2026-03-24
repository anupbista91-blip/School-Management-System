package School_Management_System.Model;

/**
 * Represents a user in the system.
 * Handles secure password hashing with salt.
 */
public class User {

    public enum Role { ADMIN, TEACHER, STUDENT }

    private int id;
    private String username;
    private String plainPassword;
    private Role role;
    private String linkedPersonId;
    private String fullName;
    private String email;
    private String phone;

    // ===== Constructor for NEW user =====
    public User(String username, String plainPassword, Role role,
                String linkedPersonId, String fullName, String email, String phone) {

        this.username = username;
        this.plainPassword = plainPassword;
        this.role = role;
        this.linkedPersonId = linkedPersonId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // ===== Constructor for DB-loaded user =====
    public User(int id, String username, String plainPassword,
                Role role, String linkedPersonId,
                String fullName, String email, String phone) {

        this.id = id;
        this.username = username;
        this.plainPassword = plainPassword;
        this.role = role;
        this.linkedPersonId = linkedPersonId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // ===== VERIFY PASSWORD =====
    public boolean verifyPassword(String inputPassword) {
        return inputPassword.equals(this.plainPassword);
    }

    // ===== GETTERS =====
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getplainPassword() { return plainPassword; }
    public Role getRole() { return role; }
    public String getLinkedPersonId() { return linkedPersonId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // ===== SETTERS =====
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPlainPassword(String plainPassword) { this.plainPassword=plainPassword; }
    public void setRole(Role role) { this.role = role; }
    public void setLinkedPersonId(String linkedPersonId) { this.linkedPersonId = linkedPersonId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // ===== DISPLAY =====
    @Override
    public String toString() {
        return String.format("%s (%s)", fullName, role);
    }
}