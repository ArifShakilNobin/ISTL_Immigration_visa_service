//package istl.immigrationvisa;
//
//import istl.immigrationvisa.domain.audit.AuditLog;
//import istl.immigrationvisa.repositories.audit.AuditLogRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//class ImmigrationVisaApplicationTests {
//
//    @Autowired
//    private AuditLogRepository auditLogRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testActivityLogAspect() throws Exception {
//        mockMvc.perform(post("/api/v1/auth/login")
//                        .content("{\"username\":\"user\",\"password\":\"pass\"}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        List<AuditLog> logs = auditLogRepository.findAll();
//        assertThat(logs).isNotEmpty();
//    }
//
//}
