package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.core.AllMethodsMvcTestsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AllMethodsMvcTestsConfig.class, properties = {"spring.mvc.dispatch-options-request=true"})
public class RepositoryGetResourceServiceTests extends BaseTests {
    @Test
    public void contextLoads() {

    }
}
