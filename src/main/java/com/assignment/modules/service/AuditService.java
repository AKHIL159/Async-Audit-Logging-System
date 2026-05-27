package com.assignment.modules.service;


import com.assignment.modules.model.AuditLog;
import com.assignment.modules.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Async("taskExecutor")
    public void logAudit(String username, String action, String entityId, String details,
                         HttpServletRequest request, String auditThreadName, String dataThreadName, int statusCode, String errorMsg) {

        String ipAddress = request.getRemoteAddr();
        String requestUri = request.getRequestURI();

        AuditLog log = new AuditLog(
                username,
                action,
                entityId,
                details,
                ipAddress,
                requestUri,
                auditThreadName,
                dataThreadName,
                statusCode,
                errorMsg
        );

        auditLogRepository.save(log);
    }

    @Async("taskExecutor")
    public CompletableFuture<List<AuditLog>> getNAuditLogs(int N) {
        if(N < -1 || N == 0) {
            return null;
        }
        else if(N == -1) {
            return CompletableFuture.completedFuture(
                    auditLogRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
            );
        }
        else{
            return CompletableFuture.completedFuture(
                    auditLogRepository.findAll(PageRequest.of(0, N, Sort.by(Sort.Direction.DESC, "timestamp"))).getContent()
            );
        }
    }







//    --------DUMMY AUDIT SERVICE------
//    public void logToFile(User user) {
//        try {
//            // Simulate I/O delay
//            Thread.sleep(100);
//            System.out.println("[LOG] User created: " + user.getEmail());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    public void saveToAuditTable(User user) {
//        try {
//            // Simulate DB delay
//            Thread.sleep(100);
//            System.out.println("[AUDIT DB] Saved user: " + user.getEmail());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
}
