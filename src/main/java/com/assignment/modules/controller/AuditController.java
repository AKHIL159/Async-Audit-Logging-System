package com.assignment.modules.controller;

import com.assignment.modules.model.AuditLog;
import com.assignment.modules.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;


    @GetMapping
    public CompletableFuture<List<AuditLog>> getAllAuditLogs() {
        return auditService.getNAuditLogs(-1);
    }

    @GetMapping("/{N}")
    public CompletableFuture<List<AuditLog>> getNAuditLogs(@PathVariable int N) {
        return auditService.getNAuditLogs(N);
    }
}
