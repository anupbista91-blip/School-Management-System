/**
 * File: AuditLogDAOImpl.java
 * Purpose: JDBC implementation for AuditLogDAO.
 */
package School_Management_System.DAO.impl;

import School_Management_System.DAO.AuditLogDAO;
import School_Management_System.Data.DatabaseUtil;
import School_Management_System.Model.AuditLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AuditLogDAOImpl persists audit events to the audit_logs table.
 */
public class AuditLogDAOImpl implements AuditLogDAO {

    private static final String INSERT_LOG = "INSERT INTO audit_logs (actor_username, action, details, ip_address) VALUES (?,?,?,?)";
    private static final String SELECT_RECENT = "SELECT id, actor_username, action, details, created_at, ip_address FROM audit_logs ORDER BY created_at DESC LIMIT ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(AuditLog entry) {
        try (Connection c = DatabaseUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_LOG)) {
            ps.setString(1, entry.getActorUsername());
            ps.setString(2, entry.getAction());
            ps.setString(3, entry.getDetails());
            ps.setString(4, entry.getIpAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting audit log: " + e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditLog> listRecent(int limit) {
        List<AuditLog> out = new ArrayList<>();
        try (Connection c = DatabaseUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_RECENT)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuditLog a = new AuditLog();
                    a.setId(rs.getLong("id"));
                    a.setActorUsername(rs.getString("actor_username"));
                    a.setAction(rs.getString("action"));
                    a.setDetails(rs.getString("details"));
                    a.setCreatedAt(rs.getTimestamp("created_at"));
                    a.setIpAddress(rs.getString("ip_address"));
                    out.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading audit logs: " + e.getMessage(), e);
        }
        return out;
    }
}