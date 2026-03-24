/**
 * File: SQLDataStore.java
 * Purpose: Adapter that provides simple entry points similar to the in-memory DataStore,
 *          delegating to DAO implementations.
 */
package School_Management_System.Data;

import School_Management_System.DAO.UserDAO;
import School_Management_System.DAO.impl.UserDAOImpl;
import School_Management_System.DAO.AuditLogDAO;
import School_Management_System.DAO.impl.AuditLogDAOImpl;
import School_Management_System.Model.User;

import java.util.List;

/**
 * SQLDataStore is a singleton facade that provides methods used by services.
 * Extend this to expose student/course/enrollment methods delegating to respective DAOs.
 */
public class SQLDataStore {
    private static SQLDataStore instance;

    private final UserDAO userDAO = new UserDAOImpl();
    private final AuditLogDAO auditLogDAO = new AuditLogDAOImpl();

    private SQLDataStore() {}

    /**
     * Get singleton instance.
     * @return SQLDataStore
     */
    public static synchronized SQLDataStore getInstance() {
        if (instance == null) instance = new SQLDataStore();
        return instance;
    }

    /** Find user by username. */
    public User getUser(String username) {
        return userDAO.findByUsername(username);
    }

    /** Create user. */
    public void addUser(User u) {
        userDAO.create(u);
    }

    /** Update role. */
    public boolean updateUserRole(String username, User.Role role) {
        boolean ok = userDAO.updateRole(username, role);
        if (ok) {
            // write an audit entry
            // minimal audit: actor unknown for now — admin UI should pass actor
            // Here we just insert a simple log
            // auditLogDAO.insert(new AuditLog(...));
        }
        return ok;
    }

    /** Delete user. */
    public boolean deleteUser(String username) {
        return userDAO.deleteByUsername(username);
    }

    /** List users. */
    public List<User> listUsers() {
        return userDAO.listAll();
    }
}
