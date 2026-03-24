/**
 * File: AuditLogDAO.java
 * Purpose: DAO interface for persisting audit logs.
 */
package School_Management_System.DAO;

import java.util.List;
import School_Management_System.Model.AuditLog;

/**
 * AuditLogDAO defines methods to persist and query audit logs.
 */
public interface AuditLogDAO {
    /**
     * Insert a new audit log entry.
     * @param entry entry to insert
     * @return true if inserted
     */
    boolean insert(AuditLog entry);

    /**
     * List recent audit logs (limit).
     * @param limit maximum rows
     * @return list of logs
     */
    List<AuditLog> listRecent(int limit);
}