package com.seuprojeto.projeto_web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.AuditLogEntity;
import com.seuprojeto.projeto_web.repositories.AuditLogRepository;

@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String entidade, String detalhes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anônimo";

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Consulta de dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails(detalhes);
        auditLog.setUsername(username);

        auditLogRepository.save(auditLog); 
    }

    public void logAdd(String entidade, String detalhes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anônimo";

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Salvar dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails(detalhes);
        auditLog.setUsername(username);

        auditLogRepository.save(auditLog); 
    }

    public void logLogin() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anônimo";

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Logar");
        auditLog.setEntity("User");
        auditLog.setDetails("");
        auditLog.setUsername(username);

        auditLogRepository.save(auditLog); 
    }
    public void logDelete(String entidade, String detalhes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anônimo";

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("exclusão de dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails("Dado Excluido: " + detalhes);
        auditLog.setUsername(username);

        auditLogRepository.save(auditLog); 
    }

    public void logUpdate(String entidade, String detalhes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anônimo";

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setAction("Atualização dados");
        auditLog.setEntity(entidade);
        auditLog.setDetails(detalhes);
        auditLog.setUsername(username);

        auditLogRepository.save(auditLog); 
    }

}
