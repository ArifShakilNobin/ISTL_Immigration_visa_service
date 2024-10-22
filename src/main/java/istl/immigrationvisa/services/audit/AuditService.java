package istl.immigrationvisa.services.audit;

import istl.immigrationvisa.domain.audit.AuditLog;
import istl.immigrationvisa.repositories.audit.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional
    public void createAuditLog(AuditLog log) {
        auditLogRepository.save(log);
    }
}
