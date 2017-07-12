package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryGetResourceServiceTestsConfig.class)
public class RepositoryGetResourceServiceTests extends BaseTests {

    @Autowired
    private BookRepository bookRepository;

    @Before
    @Override
    public void setup() {
        super.setup();
        bookRepository.delete(bookRepository.findAll());

        bookRepository.insert(new Book("Book 1", "Test Author"));
        bookRepository.insert(new Book("Book 2", "Test Author"));
        bookRepository.insert(new Book("Book 3", "Test Author"));
        bookRepository.insert(new Book("Book 4", "Fake Author"));
        bookRepository.insert(new Book("Book 5", "Fake Author"));
        bookRepository.insert(new Book("Book 6", "Fake Author"));
        bookRepository.insert(new Book("Book 7", "Fake Author"));
        bookRepository.insert(new Book("Book 8", "Fake Author"));
        bookRepository.insert(new Book("Book 9", "Fake Author"));
        bookRepository.insert(new Book("Book 10", "Fake Author"));
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void getBookRecord() throws Exception {
        Book book = bookRepository.findByTitle("Book 1");
        mockMvc.perform(get("/book")
                .param("data", json(new GetRequest(book.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.author", is("Test Author")));
    }
}
