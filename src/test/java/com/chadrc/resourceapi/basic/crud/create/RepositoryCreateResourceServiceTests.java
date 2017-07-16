package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.basic.IssueRepository;
import com.chadrc.resourceapi.basic.MagazineRepository;
import com.chadrc.resourceapi.basic.NewspaperRepository;
import com.chadrc.resourceapi.models.Book;
import com.chadrc.resourceapi.models.Issue;
import com.chadrc.resourceapi.models.Magazine;
import com.chadrc.resourceapi.models.Newspaper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryCreateResourceServiceTestsConfig.class)
public class RepositoryCreateResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private NewspaperRepository newspaperRepository;

    @Before
    @Override
    public void setup() throws Throwable {
        super.setup();
        bookRepository.deleteAll();
        magazineRepository.deleteAll();
        issueRepository.deleteAll();
        newspaperRepository.deleteAll();
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void createDefaultBook() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest())))
                .andExpect(status().isOk());

        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void createBookWithTitleAndAuthor() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("title", "Created Book"));
                    add(new CreateParameter("author", "Author"));
                }}))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", Matchers.is("Created Book")))
                .andExpect(jsonPath("$.data.author", Matchers.is("Author")));
    }

    @Test
    public void nonExistentConstructorYields400() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("notAParam", 100));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createOnConstructorWithNoCreateYields400() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("title", "Title"));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void errorDuringConstructorYields400() throws Exception {
        mockMvc.perform(post("/book")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("title", ""));
                    add(new CreateParameter("author", ""));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createIssueWithMagazine() throws Exception {
        Magazine magazine = magazineRepository.insert(new Magazine("My Magazine"));
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("magazineId", magazine.getId()));
                }}))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());

        Issue issue = issues.get(0);
        assertEquals(magazine.getId(), issue.getIssuableId());
    }

    @Test
    public void createIssueWithNewspaper() throws Exception {
        Newspaper newspaper = newspaperRepository.insert(new Newspaper("My Newspaper"));
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("newspaperId", newspaper.getId()));
                }}))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());

        Issue issue = issues.get(0);
        assertEquals(newspaper.getId(), issue.getIssuableId());
    }

    @Test
    public void createIssueWithEmptyMagazineId() throws Exception {
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("magazineId", ""));
                }}))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());
    }

    @Test
    public void createIssueWithEmptyNewspaperIdYields400() throws Exception {
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("newspaperId", ""));
                }}))))
                .andExpect(status().isBadRequest());
    }
}