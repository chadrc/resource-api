package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.mocks.*;
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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AllMethodsMvcTestsConfig.class)
public class AllMethodsMvcTests {

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
        mockMvc.perform(get("/book")
                .param("data", json(new GetRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPostMethodSucceeds() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new PostRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPatchMethodSucceeds() throws Exception {
        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithPutMethodSucceeds() throws Exception {
        mockMvc.perform(put("/book")
                .contentType(contentType)
                .content(json(new PutRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithDeleteMethodSucceeds() throws Exception {
        mockMvc.perform(delete("/book")
                .param("data", json(new DeleteRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithOptionsMethodSucceeds() throws Exception {
        mockMvc.perform(options("/book"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithHeadMethodSucceeds() throws Exception {
        mockMvc.perform(head("/book")
                .param("data", json(new GetRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithTraceMethodSucceeds() throws Exception {
        mockMvc.perform(request(HttpMethod.TRACE, "/book"))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithUnknownMethodYields405() throws Exception {
        mockMvc.perform(request("NOTSUPPORTED", URI.create("/book")))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void callWithGetAndCustomEndpointSucceeds() throws Exception {
        mockMvc.perform(get("/book/multi")
                .param("data", json(new GetMultiRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void callWithUnknownCustomEndpointYields405() throws Exception {
        mockMvc.perform(get("/book/unknown")
                .param("data", json(new GetRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void callWithInvalidJsonYields400() throws Exception {
        mockMvc.perform(get("/book")
                .param("data", "{not valid JSON}"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Data Tests
     */

    @Test
    public void getBookRecord() throws Exception {
        mockMvc.perform(get("/book")
                .contentType(contentType)
                .param("data", json(new GetRequest("0"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Harry Potter and the Philosopher's Stone")))
                .andExpect(jsonPath("$.author", is("J.K. Rowling")));
    }

    @Test
    public void getSagaRecord() throws Exception {
        mockMvc.perform(get("/saga")
                .contentType(contentType)
                .param("data", json(new GetRequest("2"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("A Song of Ice and Fire")))
                .andExpect(jsonPath("$.books[0]", is(11)))
                .andExpect(jsonPath("$.books[1]", is(12)))
                .andExpect(jsonPath("$.books[2]", is(13)))
                .andExpect(jsonPath("$.books[3]", is(14)))
                .andExpect(jsonPath("$.books[4]", is(15)))
                .andExpect(jsonPath("$.books[5]", is(16)))
                .andExpect(jsonPath("$.books[6]", is(17)));
    }

    @Test
    public void callToPostMockServiceReturnsValue() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "The Bad Beginning");
        values.put("author", "Lemony Snicket");
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new PostRequest(values))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)));
    }

    @Test
    public void callToPutMockServiceReturnsValue() throws Exception {
        mockMvc.perform(put("/book")
                .contentType(contentType)
                .content(json(new PutRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is("Put Result")));
    }

    @Test
    public void callToPatchMockServiceReturnsValue() throws Exception {
        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is("Patch Result")));
    }

    @Test
    public void callToDeleteMockServiceReturnsValue() throws Exception {
        mockMvc.perform(delete("/book")
                .contentType(contentType)
                .param("data", json(new DeleteRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is("Delete Result")));
    }

    @SuppressWarnings("unchecked")
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}