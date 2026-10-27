package cloud.oceanlaunch.starter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void addsATaskAndShowsIt() throws Exception {
        mvc.perform(post("/tasks").param("title", "Write the README")).andExpect(redirectedUrl("/"));
        mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("Write the README")));
    }

    @Test
    void anEmptyTaskShowsWhatIsWrong() throws Exception {
        mvc.perform(post("/tasks").param("title", " "))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Write what needs doing.")));
    }

    @Test
    void isHealthy() throws Exception {
        mvc.perform(get("/actuator/health")).andExpect(status().isOk());
    }
}
