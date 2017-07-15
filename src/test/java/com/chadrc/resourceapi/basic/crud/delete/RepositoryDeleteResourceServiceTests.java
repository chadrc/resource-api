package com.chadrc.resourceapi.basic.crud.delete;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryDeleteResourceServiceTestsConfig.class)
public class RepositoryDeleteResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    @Before
    @Override
    public void setup() throws Throwable {
        super.setup();
        bookRepository.delete(bookRepository.findAll());
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void deleteResourceSuccess() throws Throwable {
        Book book = bookRepository.insert(new Book("Title", "Author"));
        mockMvc.perform(delete("/book")
                .param("id", book.getId()))
                .andExpect(status().isOk());

        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }
}
