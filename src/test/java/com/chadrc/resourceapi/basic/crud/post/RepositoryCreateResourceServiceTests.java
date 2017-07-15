package com.chadrc.resourceapi.basic.crud.post;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.models.Book;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryCreateResourceServiceTestsConfig.class)
public class RepositoryCreateResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    @Before
    @Override
    public void setup() {
        super.setup();
        bookRepository.delete(bookRepository.findAll());
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void createDefaultBook() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest())))
                .andExpect(status().isOk());

        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void createBookWithTitleAndAuthor() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<Object>() {{
                    add("Created Book");
                    add("Author");
                }}))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created.title", Matchers.is("Created Book")))
                .andExpect(jsonPath("$.created.author", Matchers.is("Author")));
    }

    @Test
    public void nonExistentConstructorYields400() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<Object>() {{
                    add(100);
                }}))))
                .andExpect(status().isBadRequest());
    }
}
