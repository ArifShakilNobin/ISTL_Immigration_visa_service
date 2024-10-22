package istl.immigrationvisa.security.audit;

import istl.immigrationvisa.domain.audit.AuditLog;
import istl.immigrationvisa.repositories.audit.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

//@Aspect
//@Component
//public class ActivityLogAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(ActivityLogAspect.class);
//
//    @Autowired
//    private AuditLogRepository auditLogRepository;
//
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    @AfterReturning("execution(* istl.immigrationvisa.*Controller.*(..))")
//    public void logActivity(JoinPoint joinPoint) {
//        logger.debug("ActivityLogAspect triggered for method---------------->: {}", joinPoint.getSignature().toShortString());
//
//        try {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            String entity = joinPoint.getSignature().getDeclaringTypeName();
//            String operation = joinPoint.getSignature().getName();
//            String ipAddress = httpServletRequest.getRemoteAddr();
//
//            AuditLog log = new AuditLog();
//            log.setUsername(username);
//            log.setEntity(entity);
//            log.setOperation(operation);
//            log.setTimestamp(LocalDateTime.now());
//            log.setIpAddress(ipAddress);
//
//            logger.info("Saving audit log-------------------->: {}", log);
//
//            auditLogRepository.save(log);
//        } catch (Exception e) {
//            logger.error("Error saving audit log----------------------->", e);
//        }
//    }
//}

//@Aspect
//@Component
//public class ActivityLogAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(ActivityLogAspect.class);
//    @Autowired
//    private AuditLogRepository auditLogRepository;
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    @AfterReturning("execution(* istl.immigrationvisa..*Controller.*(..))")
//    public void logActivity(JoinPoint joinPoint) {
//        logger.debug("ActivityLogAspect triggered for method: {}", joinPoint.getSignature().toShortString());
//
//        try {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            String entity = joinPoint.getSignature().getDeclaringTypeName();
//            String operation = joinPoint.getSignature().getName();
////            String ipAddress = httpServletRequest.getRemoteAddr();
//            String ipAddress = getClientIp(httpServletRequest);
//
//            logger.info("Logging activity: Username={}, Entity={}, Operation={}, IP={}----------------->", username, entity, operation, ipAddress);
//
//            AuditLog log = new AuditLog();
//            log.setUsername(username);
//            log.setEntity(entity);
//            log.setOperation(operation);
//            log.setTimestamp(LocalDateTime.now());
//            log.setIpAddress(ipAddress);
//
//            logger.info("Saving audit log: {}", log);
//
//            auditLogRepository.save(log);
//
//
//        } catch (Exception e) {
//            logger.error("Error in aspect logging", e);
//        }
//    }
//
//    // Method to retrieve the client's network IP address
//    private String getClientIp(HttpServletRequest request) {
//        String ipAddress = request.getHeader("X-Forwarded-For");
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//        }
//
//        // Handle cases where multiple IP addresses are returned (e.g., "X-Forwarded-For: client, proxy1, proxy2")
//        if (ipAddress != null && ipAddress.contains(",")) {
//            ipAddress = ipAddress.split(",")[0].trim();
//        }
//
//        // If still localhost, try to determine the actual network IP
//        if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "127.0.0.1".equals(ipAddress)) {
//            ipAddress = getNetworkIp();
//        }
//
//        logger.info("Client IP Address: {}", ipAddress);
//        return ipAddress;
//    }
//
//    // Method to get the actual network IP address of the client machine
//    private String getNetworkIp() {
//        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();
//            return inetAddress.getHostAddress();
//        } catch (UnknownHostException e) {
//            logger.error("Unable to get network IP address", e);
//            return "unknown";
//        }
//    }


@Aspect
@Component
public class ActivityLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ActivityLogAspect.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @AfterReturning("execution(* istl.immigrationvisa..*Controller.*(..))")
    public void logActivity(JoinPoint joinPoint) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String entity = joinPoint.getSignature().getDeclaringTypeName();
            String operation = joinPoint.getSignature().getName();

            // Get IP and country from request headers
            String ipAddress = httpServletRequest.getHeader("Client-IP");
            String country = httpServletRequest.getHeader("Client-Country");

            AuditLog log = new AuditLog();
            log.setUsername(username);
            log.setEntity(entity);
            log.setOperation(operation);
            log.setTimestamp(LocalDateTime.now());
            log.setIpAddress(ipAddress);
            log.setCountry(country);

            logger.info("Saving audit log: {}", log);

            auditLogRepository.save(log);
        } catch (Exception e) {
            logger.error("Error saving audit log", e);
        }
    }

}
