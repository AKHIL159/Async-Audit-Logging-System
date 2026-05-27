package com.assignment.modules.model;



import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLogThread")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String action;
    private String entityId;
    private String details;
    private String ipAddress;
    private String requestUri;
    private String auditThreadName;
    private String dataThreadName;
    private int statusCode;
    private String errorMsg;
    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(String username, String action, String entityId, String details,
                    String ipAddress, String requestUri, String auditThreadName, String dataThreadName, int statusCode, String errorMsg) {
        this.username = username;
        this.action = action;
        this.entityId = entityId;
        this.details = details;
        this.ipAddress = ipAddress;
        this.requestUri = requestUri;
        this.statusCode = statusCode;
        this.auditThreadName = auditThreadName;
        this.dataThreadName = dataThreadName;
        this.errorMsg = errorMsg;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getAuditThreadName() {
        return auditThreadName;
    }

    public void setAuditThreadName(String auditThreadName) {
        this.auditThreadName = auditThreadName;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getDataThreadName() {
        return dataThreadName;
    }
    public void setDataThreadName(String dataThreadName) {
        this.dataThreadName = dataThreadName;
    }
}