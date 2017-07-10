package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.BaseTests;
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

    @Test
    public void contextLoads() {

    }
}
