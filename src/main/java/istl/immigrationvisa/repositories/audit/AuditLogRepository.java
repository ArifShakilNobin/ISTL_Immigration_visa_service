package istl.immigrationvisa.repositories.audit;

import istl.immigrationvisa.domain.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}