package com.chadrc.resourceapi.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreTestConfig.class)
public class CoreTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void contextLoads() {
    }

    /*
     * Ping Tests
     */

    @Test
    public void callWithGetMethodSucceeds() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPostMethodSucceeds() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPatchMethodSucceeds() throws Exception {
        mockMvc.perform(patch("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPutMethodSucceeds() throws Exception {
        mockMvc.perform(put("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithDeleteMethodSucceeds() throws Exception {
        mockMvc.perform(delete("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithOptionsMethodSucceeds() throws Exception {
        mockMvc.perform(options("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithHeadMethodSucceeds() throws Exception {
        mockMvc.perform(head("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithTraceMethodSucceeds() throws Exception {
        mockMvc.perform(request(HttpMethod.TRACE, "/"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithUnknownMethodYields405() throws Exception {
        mockMvc.perform(request("NOTSUPPORTED", URI.create("/")))
                .andExpect(status().isMethodNotAllowed());
    }

    /*
     * Data Tests
     */

    @Test
    public void getBookRecord() throws Exception {
        mockMvc.perform(get("/")
                .contentType(contentType)
                .param("resourceName", "Book")
                .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is("Harry Potter and the Philosopher's Stone")))
                .andExpect(jsonPath("$.data.author", is("J.K. Rowling")));
    }

    @Test
    public void callToPostMockServiceReturnsValue() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is("Post Result")));
    }

    @SuppressWarnings("unchecked")
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}