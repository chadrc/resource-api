package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.*;
import com.chadrc.resourceapi.models.*;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private MailingListRepository mailingListRepository;

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
                    add(new CreateParameter("magazine", magazine.getId()));
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
                    add(new CreateParameter("newspaper", newspaper.getId()));
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
                    add(new CreateParameter("magazine", ""));
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
                    add(new CreateParameter("newspaper", ""));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createIssueWithUnregisteredResourceModelYields400() throws Exception {
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("catalog", "catalogId"));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCustomerWithAddress() throws Exception {
        mockMvc.perform(post("/customer")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("name", "Muffin Man"));
                    add(new CreateParameter("address", new Address("Boston", "MA", "Drury Lane", "12345")));
                }}))))
                .andExpect(status().isOk());

        List<Customer> customers = customerRepository.findAll();
        assertEquals(1, customers.size());

        Customer customer = customers.get(0);
        assertEquals("Muffin Man", customer.getName());
        assertEquals("Boston", customer.getAddress().getCity());
        assertEquals("MA", customer.getAddress().getState());
        assertEquals("Drury Lane", customer.getAddress().getAddress());
        assertEquals("12345", customer.getAddress().getZip());
    }

    @Test
    public void createCustomWithNullAddressIsOk() throws Exception {
        mockMvc.perform(post("/customer")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("name", "Muffin Man"));
                    add(new CreateParameter("address", null));
                }}))))
                .andExpect(status().isOk());
    }

    @Test
    public void createWithObjectOnFromIdYields400() throws Exception {
        mockMvc.perform(post("/issue")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("magazine", new Magazine("My Magazine")));
                }}))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookOrderWithFromIdList() throws Throwable {
        Book book1 = bookRepository.insert(new Book("Book 1", "Author"));
        Book book2 = bookRepository.insert(new Book("Book 2", "Author"));

        mockMvc.perform(post("/bookorder")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("books", new ArrayList<String>() {{
                        add(book1.getId());
                        add(book2.getId());
                    }}));
                }}))))
                .andExpect(status().isOk());

        List<BookOrder> bookOrders = bookOrderRepository.findAll();
        assertEquals(1, bookOrders.size());

        BookOrder bookOrder = bookOrders.get(0);
        assertEquals(2, bookOrder.getBookIds().size());
        assertEquals(book1.objectId(), bookOrder.getBookIds().get(0));
        assertEquals(book2.objectId(), bookOrder.getBookIds().get(1));
    }

    @Test
    public void createMagazineWithCategories() throws Throwable {
        mockMvc.perform(post("/magazine")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("categories", new ArrayList<String>() {{
                        add("Food");
                        add("Home");
                    }}));
                }}))))
                .andExpect(status().isOk());

        List<Magazine> magazines = magazineRepository.findAll();
        assertEquals(1, magazines.size());

        Magazine magazine = magazines.get(0);
        assertEquals(2, magazine.getCategories().size());
    }

    @Test
    public void createMailingList() throws Throwable {
        mockMvc.perform(post("/mailinglist")
                .contentType(contentType)
                .content(json(new CreateRequest(new ArrayList<CreateParameter>() {{
                    add(new CreateParameter("addresses", new ArrayList<Address>() {{
                        add(new Address("Boston", "MA", "123 Main Street", "09876"));
                        add(new Address("New York", "NY", "987 Main Street", "12345"));
                    }}));
                }}))))
                .andExpect(status().isOk());

        List<MailingList> mailingLists = mailingListRepository.findAll();
        assertEquals(1, mailingLists.size());

        MailingList mailingList = mailingLists.get(0);
        assertEquals(2, mailingList.getAddresses().size());
        assertEquals("Boston", mailingList.getAddresses().get(0).getCity());
        assertEquals("New York", mailingList.getAddresses().get(1).getCity());
    }
}
