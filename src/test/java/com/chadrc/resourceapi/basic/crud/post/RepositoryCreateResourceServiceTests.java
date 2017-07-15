package com.chadrc.resourceapi.basic.crud.post;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
