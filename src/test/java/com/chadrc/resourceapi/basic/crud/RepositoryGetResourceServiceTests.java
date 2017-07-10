package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RespositoryGetResourceServiceTestsConfig.class)
public class RepositoryGetResourceServiceTests extends BaseTests {

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setup() {
        bookRepository.delete(bookRepository.findAll());

        bookRepository.insert(new Book("Book 1", "Test Author"));
        bookRepository.insert(new Book("Book 2", "Test Author"));
        bookRepository.insert(new Book("Book 3", "Test Author"));
        bookRepository.insert(new Book("Book 4", "Fake Author"));
        bookRepository.insert(new Book("Book 5", "Fake Author"));
    }

    @Test
    public void contextLoads() {

    }
}
