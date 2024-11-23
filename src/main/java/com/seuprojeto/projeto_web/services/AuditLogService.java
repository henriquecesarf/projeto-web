package com.seuprojeto.projeto_web.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.AuditLogEntity;
import com.seuprojeto.projeto_web.repositories.AuditLogRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    HttpSession session;

    public void log(String entidade, String detalhes) {

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("INFO");
        auditLog.setEntity(entidade);
        auditLog.setDetails(detalhes);
        auditLog.setCurrentValues("");
        auditLog.setPreviousValues("");

        String username = Optional.ofNullable((String) session.getAttribute("username"))
        .orElse("anonimo");
        auditLog.setUsername(username);      

        auditLog.setUserId(String.valueOf(session.getAttribute("userId")));
        
        auditLogRepository.save(auditLog); 
    }

    public void logAdd(String entidade, String currentValues) {


        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Persistencia de dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails("");
        auditLog.setCurrentValues(currentValues);
        auditLog.setPreviousValues("");
        String username = Optional.ofNullable((String) session.getAttribute("username"))
        .orElse("anonimo");
        auditLog.setUsername(username);    
        auditLog.setUserId(String.valueOf(session.getAttribute("userId")));

        auditLogRepository.save(auditLog); 
    }

    public void logDelete(String entidade, String previousValues) {

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("exclusão de dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails("");
        auditLog.setCurrentValues("");
        auditLog.setPreviousValues(previousValues);
        auditLog.setUsername((String) session.getAttribute("username"));
        auditLog.setUserId(String.valueOf(session.getAttribute("userId")));

        auditLogRepository.save(auditLog); 
    }

    public void logUpdate(String entidade, String currentValues, String previousValues) {

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Atualização dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails("");
        auditLog.setCurrentValues(currentValues);
        auditLog.setPreviousValues(previousValues);
        auditLog.setUsername((String) session.getAttribute("username"));
        auditLog.setUserId(String.valueOf(session.getAttribute("userId")));

        auditLogRepository.save(auditLog); 
    }

}
