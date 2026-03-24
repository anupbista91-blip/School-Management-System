/**
 * File: AuditLog.java
 * Purpose: Simple model representing an audit log entry.
 */
package School_Management_System.Model;

import java.util.Date;

/**
 * AuditLog stores metadata for an administrative action.
 */
public class AuditLog {
    private long id;
    private String actorUsername;
    private String action;
    private String details;
    private Date createdAt;
    private String ipAddress;

    /** Getters and setters */
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getActorUsername() { return actorUsername; }
    public void setActorUsername(String actorUsername) { this.actorUsername = actorUsername; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
