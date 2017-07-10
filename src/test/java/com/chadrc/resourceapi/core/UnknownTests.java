package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.core.mocks.DeleteRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnknownTestsConfig.class)
public class UnknownTests extends BaseTests {

    @Test
    public void unsupportedMethodYields405() throws Exception {
        mockMvc.perform(delete("/book")
                .param("data", json(new DeleteRequest())))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void headCallWithNoGetOrHeadServiceYields405() throws Exception {
        mockMvc.perform(head("/book"))
                .andExpect(status().isMethodNotAllowed());
    }
}
