package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.*;
import com.chadrc.resourceapi.models.Book;
import com.chadrc.resourceapi.models.Issue;
import com.chadrc.resourceapi.models.Magazine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryPatchResourceServiceTestsConfig.class)
public class RepositoryPatchResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private NewspaperRepository newspaperRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private MailingListRepository mailingListRepository;

    private Book bookToUpdate;

    @Before
    @Override
    public void setup() throws Throwable {
        super.setup();
        bookRepository.deleteAll();
        magazineRepository.deleteAll();
        issueRepository.deleteAll();
        newspaperRepository.deleteAll();
        customerRepository.deleteAll();
        bookOrderRepository.deleteAll();
        mailingListRepository.deleteAll();

        bookToUpdate = bookRepository.insert(new Book("Book to Update", "Author"));
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void updateBookSuccess() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter("title", "Updated Title"));
        updates.put("author", new RequestParameter("Author", "Updated Author"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isOk());

        Book book = bookRepository.findOne(bookToUpdate.objectId());

        assertEquals("Updated Title", book.getTitle());
        assertEquals("Updated Author", book.getAuthor());
    }

    @Test
    public void nullIdYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter("title", "Value"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(null, updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFieldMarkedNoUpdateYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("id", new RequestParameter("id", "myId"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void nonExistentResourceYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter("title", "Title"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest("UnknownId", updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUnknownFieldYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("notAField", new RequestParameter("notAField", "Value"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void errorInUpdateYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter("title", ""));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createIssueWithMagazine() throws Exception {
        Magazine magazine = magazineRepository.insert(new Magazine("My Magazine"));
        Magazine magazine2 = magazineRepository.insert(new Magazine("Second Magazine"));
        Issue newIssue = issueRepository.insert(new Issue(magazine));

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("issuable", new RequestParameter("magazine", magazine2.getId()));

        mockMvc.perform(patch("/issue")
                .contentType(contentType)
                .content(json(new PatchRequest(newIssue.getId(), updates))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());

        Issue issue = issues.get(0);
        assertEquals(magazine2.getId(), issue.getIssuableId());
    }
}
