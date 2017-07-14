package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.models.Book;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryGetResourceServiceTestsConfig.class)
public class RepositoryGetResourceServiceTests extends BaseTests {

    @Autowired
    private BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();

    @Before
    @Override
    public void setup() {
        super.setup();
        books.clear();
        bookRepository.delete(bookRepository.findAll());

        books = Arrays.asList(
                bookRepository.insert(new Book("Book 1", "Test Author")),
                bookRepository.insert(new Book("Book 2", "Test Author")),
                bookRepository.insert(new Book("Book 3", "Test Author")),
                bookRepository.insert(new Book("Book 4", "Fake Author")),
                bookRepository.insert(new Book("Book 5", "Fake Author")),
                bookRepository.insert(new Book("Book 6", "Fake Author")),
                bookRepository.insert(new Book("Book 7", "Fake Author")),
                bookRepository.insert(new Book("Book 8", "Fake Author")),
                bookRepository.insert(new Book("Book 9", "Fake Author")),
                bookRepository.insert(new Book("Book 10", "Fake Author")));
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void getBookRecord() throws Exception {
        mockMvc.perform(get("/book")
                .param("ids", books.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.author", is("Test Author")));
    }
}
