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
                bookRepository.insert(new Book("Book 1", "Alpha")),
                bookRepository.insert(new Book("Book 2", "Alpha")),
                bookRepository.insert(new Book("Book 3", "Alpha")),
                bookRepository.insert(new Book("Book 4", "Alpha")),
                bookRepository.insert(new Book("Book 5", "Alpha")),
                bookRepository.insert(new Book("Book 6", "Bravo")),
                bookRepository.insert(new Book("Book 7", "Bravo")),
                bookRepository.insert(new Book("Book 8", "Bravo")),
                bookRepository.insert(new Book("Book 9", "Bravo")),
                bookRepository.insert(new Book("Book 10", "Bravo")),
                bookRepository.insert(new Book("Book 11", "Charlie")),
                bookRepository.insert(new Book("Book 12", "Charlie")),
                bookRepository.insert(new Book("Book 13", "Charlie")),
                bookRepository.insert(new Book("Book 14", "Charlie")),
                bookRepository.insert(new Book("Book 15", "Charlie"))
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
                .andExpect(jsonPath("$.data.records[0].title", is("Book 1")))
                .andExpect(jsonPath("$.data.records[0].author", is("Alpha")));
    }

    @Test
    public void getDefaultBookPage() throws Exception {
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].title", is("Book 1")))
                .andExpect(jsonPath("$.data.records[1].title", is("Book 2")))
                .andExpect(jsonPath("$.data.records[2].title", is("Book 3")))
                .andExpect(jsonPath("$.data.records[3].title", is("Book 4")))
                .andExpect(jsonPath("$.data.records[4].title", is("Book 5")))
                .andExpect(jsonPath("$.data.records[5].title", is("Book 6")))
                .andExpect(jsonPath("$.data.records[6].title", is("Book 7")))
                .andExpect(jsonPath("$.data.records[7].title", is("Book 8")))
                .andExpect(jsonPath("$.data.records[8].title", is("Book 9")))
                .andExpect(jsonPath("$.data.records[9].title", is("Book 10")));
    }

    @Test
    public void getBookPage() throws Exception {
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].title", is("Book 1")))
                .andExpect(jsonPath("$.data.records[1].title", is("Book 2")))
                .andExpect(jsonPath("$.data.records[2].title", is("Book 3")))
                .andExpect(jsonPath("$.data.records[3].title", is("Book 4")))
                .andExpect(jsonPath("$.data.records[4].title", is("Book 5")));
    }

    @Test
    public void getSecondBookPage() throws Exception {
        mockMvc.perform(get("/book")
                .param("page", "1")
                .param("count", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].title", is("Book 6")))
                .andExpect(jsonPath("$.data.records[1].title", is("Book 7")))
                .andExpect(jsonPath("$.data.records[2].title", is("Book 8")))
                .andExpect(jsonPath("$.data.records[3].title", is("Book 9")))
                .andExpect(jsonPath("$.data.records[4].title", is("Book 10")));
    }

    @Test
    public void getBookPageSorted() throws Exception {
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("count", "5")
                .param("sort[0].field", "title")
                .param("sort[0].direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].title", is("Book 9")))
                .andExpect(jsonPath("$.data.records[1].title", is("Book 8")))
                .andExpect(jsonPath("$.data.records[2].title", is("Book 7")))
                .andExpect(jsonPath("$.data.records[3].title", is("Book 6")))
                .andExpect(jsonPath("$.data.records[4].title", is("Book 5")));
    }

    @Test
    public void getBookPageMultiSorted() throws Exception {
        mockMvc.perform(get("/book")
                .param("page", "0")
                .param("count", "5")
                .param("sort[0].field", "author")
                .param("sort[0].direction", "DESC")
                .param("sort[1].field", "title")
                .param("sort[1].direction", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].title", is("Book 11")))
                .andExpect(jsonPath("$.data.records[1].title", is("Book 12")))
                .andExpect(jsonPath("$.data.records[2].title", is("Book 13")))
                .andExpect(jsonPath("$.data.records[3].title", is("Book 14")))
                .andExpect(jsonPath("$.data.records[4].title", is("Book 15")));
    }

    @Test
    public void getSagaRecord() throws Exception {
        mockMvc.perform(get("/saga")
                .param("id", sagas.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.records[0].name", is("Saga 1")));
    }

    @Test
    public void unknownIdYields404() throws Exception {
        mockMvc.perform(get("/book")
                .param("id", "593d7bd9f3383a00015a0506"))
                .andExpect(status().isNotFound());
    }
}
