package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.domain.User;
import com.chadrc.resourceapi.service.ResourcePage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerListTests {

    @Autowired
    ResourceControllerProxy resourceControllerProxy;

    @Before
    public void setUp() throws Exception {
        resourceControllerProxy.addUser("Alpha", "Alpha");
        resourceControllerProxy.addUser("Bravo", "Beta");
        resourceControllerProxy.addUser("Charlie", "Gamma");
        resourceControllerProxy.addUser("Delta", "Delta");
        resourceControllerProxy.addUser("Echo", "Epsilon");

        resourceControllerProxy.addUser("Foxtrot", "Zeta");
        resourceControllerProxy.addUser("Golf", "Eta");
        resourceControllerProxy.addUser("Hotel", "Theta");
        resourceControllerProxy.addUser("India", "Iota");
        resourceControllerProxy.addUser("Juliet", "Kappa");

        resourceControllerProxy.addUser("Kilo", "Lambda");
        resourceControllerProxy.addUser("Lima", "Mu");
        resourceControllerProxy.addUser("Mike", "Nu");
        resourceControllerProxy.addUser("November", "Xi");
        resourceControllerProxy.addUser("Oscar", "Omicron");

        resourceControllerProxy.addUser("Papa", "Pi");
        resourceControllerProxy.addUser("Quebec", "Rho");
        resourceControllerProxy.addUser("Romeo", "Sigma");
        resourceControllerProxy.addUser("Sierra", "Tau");
        resourceControllerProxy.addUser("Tango", "Upsilon");

        resourceControllerProxy.addUser("Uniform", "Phi");
        resourceControllerProxy.addUser("Victor", "Chi");
        resourceControllerProxy.addUser("Whiskey", "Psi");
        resourceControllerProxy.addUser("X-Ray", "Omega");
        resourceControllerProxy.addUser("Yankee", "Yankee");

        resourceControllerProxy.addUser("Zulu", "Zulu");
    }

    @Test
    public void listUsersWithNullPagingInfoSucceeds() {
        ListOptions options = new ListOptions("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        assert responseEntity.getBody() instanceof ResourcePage;
    }

    @Test
    public void listUsersWithNullPagingInfoHas10Resources() {
        ListOptions options = new ListOptions("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        assertEquals(resourcePage.getResources().size(), 10);
    }

    @Test
    public void listUsersWith5PageSizeHas5Resources() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCount(5);
        ListOptions options = new ListOptions("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        assertEquals(resourcePage.getResources().size(), 5);
    }

    @Test
    public void listUsersWithPage2HasSecondSetOfResources() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPage(1);
        ListOptions options = new ListOptions("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        List resources = resourcePage.getResources();
        User first = (User) resources.get(0);
        assertEquals(first.getFirstName(), "Kilo");
    }
}
