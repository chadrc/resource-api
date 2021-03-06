package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.domain.User;
import com.chadrc.resourceapi.domain.UserRepository;
import com.chadrc.resourceapi.service.ResourcePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerListTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Autowired
    private UserRepository userRepository;

    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        userRepository.delete(userRepository.findAll());

        userList.add(resourceControllerProxy.addUser("Alpha", "Alpha"));
        userList.add(resourceControllerProxy.addUser("Bravo", "Beta"));
        userList.add(resourceControllerProxy.addUser("Charlie", "Gamma"));
        userList.add(resourceControllerProxy.addUser("Delta", "Delta"));
        userList.add(resourceControllerProxy.addUser("Echo", "Epsilon"));

        userList.add(resourceControllerProxy.addUser("Foxtrot", "Zeta"));
        userList.add(resourceControllerProxy.addUser("Golf", "Eta"));
        userList.add(resourceControllerProxy.addUser("Hotel", "Theta"));
        userList.add(resourceControllerProxy.addUser("India", "Iota"));
        userList.add(resourceControllerProxy.addUser("Juliet", "Kappa"));

        userList.add(resourceControllerProxy.addUser("Kilo", "Lambda"));
        userList.add(resourceControllerProxy.addUser("Lima", "Mu"));
        userList.add(resourceControllerProxy.addUser("Mike", "Nu"));
        userList.add(resourceControllerProxy.addUser("November", "Xi"));
        userList.add(resourceControllerProxy.addUser("Oscar", "Omicron"));

        userList.add(resourceControllerProxy.addUser("Papa", "Pi"));
        userList.add(resourceControllerProxy.addUser("Quebec", "Rho"));
        userList.add(resourceControllerProxy.addUser("Romeo", "Sigma"));
        userList.add(resourceControllerProxy.addUser("Sierra", "Tau"));
        userList.add(resourceControllerProxy.addUser("Tango", "Upsilon"));

        userList.add(resourceControllerProxy.addUser("Uniform", "Phi"));
        userList.add(resourceControllerProxy.addUser("Victor", "Chi"));
        userList.add(resourceControllerProxy.addUser("Whiskey", "Psi"));
        userList.add(resourceControllerProxy.addUser("X-Ray", "Omega"));
        userList.add(resourceControllerProxy.addUser("Yankee", "Yankee"));

        userList.add(resourceControllerProxy.addUser("Zulu", "Zulu"));
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(userList);
    }

    @Test
    public void listWithNullResourceNameYields400() {
        ListRequest options = new ListRequest(null, null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void listWithUnknownResourceFails() {
        ListRequest options = new ListRequest("Animal", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void listUsersWithNullPagingInfoSucceeds() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        assert responseEntity.getBody() instanceof ResourcePage;
    }

    @Test
    public void defaultListHas26AsTotal() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();
        assertEquals(26, resourcePage.getTotal());
    }

    @Test
    public void defaultListIsFirst() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();
        assertTrue(resourcePage.isFirst());
    }

    @Test
    public void defaultListIsNotLast() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();
        assertFalse(resourcePage.isLast());
    }

    @Test
    public void defaultListHas10PageSize() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();
        assertEquals(10, resourcePage.getPageSize());
    }

    @Test
    public void listUsersWithNullPagingInfoHas10Resources() {
        ListRequest options = new ListRequest("User", null);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        assertEquals(resourcePage.getResources().size(), 10);
    }

    @Test
    public void listUsersWith5CountHas5Resources() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCount(5);
        ListRequest options = new ListRequest("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        assertEquals(resourcePage.getResources().size(), 5);
    }

    @Test
    public void listUsersWithPage1HasSecondSetOfResources() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPage(1);
        ListRequest options = new ListRequest("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        List resources = resourcePage.getResources();
        User first = (User) resources.get(0);
        assertEquals("Kilo", first.getFirstName());
    }

    @Test
    public void listUsersWithCount3Page4StartsWith15Resource() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPage(4);
        pagingInfo.setCount(3);
        ListRequest options = new ListRequest("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        List resources = resourcePage.getResources();
        User first = (User) resources.get(0);

        assertEquals("Mike", first.getFirstName());
    }

    @Test
    public void listUsersWithSortByLastNameHasChiAsThirdResource() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setSort(new ArrayList<PagingSort>(){{
            add(new PagingSort("lastName", SortDirection.Acending));
        }});
        ListRequest options = new ListRequest("User", pagingInfo);
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().list(options);
        ResourcePage resourcePage = (ResourcePage) responseEntity.getBody();

        List resources = resourcePage.getResources();
        User user = (User) resources.get(2);

        assertEquals("Chi", user.getLastName());
    }
}
