package istl.immigrationvisa.domain.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String ip;
    private String clientType;
    private String uri;
    private String method;
    private LocalDateTime timestamp;
    private long executionTime;
    private String outcome;
    private String userAgent;
    private String activity;
}
