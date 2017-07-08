package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.mocks.GetRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
