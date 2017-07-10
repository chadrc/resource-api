package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.core.mocks.GetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomBasePathTestConfig.class, properties = {"baseResourceUri=resource"})
public class CustomBasePathTest extends BaseTests {

    @Value("${baseResourceUri}")
    public String baseResourcePath;

    @Test
    public void contextLoads() {
    }

    @Test
    public void basePathLoaded() {
        assertEquals("resource", baseResourcePath);
    }

    @Test
    public void callWithGetMethodSucceeds() throws Exception {
        mockMvc.perform(get("/resource/book")
                .param("data", json(new GetRequest())))
                .andExpect(status().isOk());
    }
}
