/**
 * File: UserDAO.java
 * Purpose: DAO interface for user-related database operations, including
 * support for auto-generated database IDs.
 */
package School_Management_System.DAO;

import java.util.List;
import School_Management_System.Model.User;

/**
 * UserDAO defines operations to manage users in persistent storage.
 * Implementations should handle CRUD and ID management.
 */
public interface UserDAO {

    /**
     * Find a user by username.
     *
     * @param username username to search
     * @return User object if found, null otherwise
     */
    User findByUsername(String username);

    /**
     * Create a new user in the database.
     * Implementations should populate the generated database ID into the User object.
     *
     * @param user user to create (must contain passwordHash and salt)
     * @return created User object with ID populated from the database
     */
    User create(User user);

    /**
     * Update a user's role by username.
     *
     * @param username the username of the user
     * @param role     new role to assign
     * @return true if update succeeded, false otherwise
     */
    boolean updateRole(String username, User.Role role);

    /**
     * Delete a user by username.
     *
     * @param username username to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteByUsername(String username);

    /**
     * List all users in the database.
     *
     * @return list of User objects
     */
    List<User> listAll();
}