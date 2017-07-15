package com.chadrc.resourceapi.basic.crud.read;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.basic.SagaRepository;
import com.chadrc.resourceapi.models.Book;
import com.chadrc.resourceapi.models.Saga;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private SagaRepository sagaRepository;

    private List<Book> books = new ArrayList<>();
    private List<Saga> sagas = new ArrayList<>();

    @Before
    @Override
    public void setup() throws Throwable {
        super.setup();
        books.clear();
        bookRepository.delete(bookRepository.findAll());
        sagaRepository.delete(sagaRepository.findAll());

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
                bookRepository.insert(new Book("Book 10", "Fake Author"))
        );

        sagas = Arrays.asList(
                sagaRepository.insert(new Saga("Saga 1", Arrays.asList(0, 1, 2))),
                sagaRepository.insert(new Saga("Saga 2", Arrays.asList(3, 4, 5)))
        );
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void getBookRecord() throws Exception {
        mockMvc.perform(get("/book")
                .param("id", books.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Book 1")))
                .andExpect(jsonPath("$.author", is("Test Author")));
    }

    @Test
    public void getBookPage() throws Exception {
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].title", is("Book 1")))
                .andExpect(jsonPath("$.data.[0].author", is("Test Author")))
                .andExpect(jsonPath("$.data.[1].title", is("Book 2")))
                .andExpect(jsonPath("$.data.[1].author", is("Test Author")))
                .andExpect(jsonPath("$.data.[2].title", is("Book 3")))
                .andExpect(jsonPath("$.data.[2].author", is("Test Author")))
                .andExpect(jsonPath("$.data.[3].title", is("Book 4")))
                .andExpect(jsonPath("$.data.[3].author", is("Fake Author")))
                .andExpect(jsonPath("$.data.[4].title", is("Book 5")))
                .andExpect(jsonPath("$.data.[4].author", is("Fake Author")));
    }

    @Test
    public void getSagaRecord() throws Exception {
        mockMvc.perform(get("/saga")
                .param("id", sagas.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Saga 1")));
    }

    @Test
    public void noParamsYields400() throws Exception {
        mockMvc.perform(get("/book"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void unknownIdYields404() throws Exception {
        mockMvc.perform(get("/book")
                .param("id", "593d7bd9f3383a00015a0506"))
                .andExpect(status().isNotFound());
    }
}
