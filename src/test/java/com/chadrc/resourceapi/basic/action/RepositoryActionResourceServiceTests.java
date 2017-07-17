package com.chadrc.resourceapi.basic.action;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.models.Book;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryActionResourceServiceTestsConfig.class)
public class RepositoryActionResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void contextLoads() {

    }

    @Test
    public void capitalizeTitleAction() throws Throwable {
        Book book = bookRepository.insert(new Book("My Title"));

        mockMvc.perform(post("/book/action")
                .contentType(contentType)
                .content(json(new ActionRequest(book.getId(), "capitalizeTitle"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", Matchers.is("MY TITLE")));
    }
}
