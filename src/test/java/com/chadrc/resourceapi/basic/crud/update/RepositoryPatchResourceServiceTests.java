package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.*;
import com.chadrc.resourceapi.models.*;
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
        updates.put("title", new RequestParameter("Updated Title"));
        updates.put("author", new RequestParameter("Updated Author"));

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
        updates.put("title", new RequestParameter("Value"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(null, updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFieldMarkedNoUpdateYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("id", new RequestParameter("myId"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void nonExistentResourceYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter("Title"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest("UnknownId", updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUnknownFieldYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("notAField", new RequestParameter("Value"));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void errorInUpdateYields400() throws Exception {
        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("title", new RequestParameter(""));

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateIssueWithMagazine() throws Exception {
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

    @Test
    public void updateIssueWithNewspaper() throws Exception {
        Newspaper newspaper = newspaperRepository.insert(new Newspaper("My Newspaper"));
        Newspaper newspaper2 = newspaperRepository.insert(new Newspaper("My Second Newspaper"));
        Issue newIssue = issueRepository.insert(new Issue(newspaper));

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("issuable", new RequestParameter("newspaper", newspaper2.getId()));

        mockMvc.perform(patch("/issue")
                .contentType(contentType)
                .content(json(new PatchRequest(newIssue.getId(), updates))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());

        Issue issue = issues.get(0);
        assertEquals(newspaper2.getId(), issue.getIssuableId());
    }

    @Test
    public void updateIssueWithEmptyMagazineId() throws Exception {
        Issue newIssue = issueRepository.insert(new Issue());

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("issuable", new RequestParameter("magazine", ""));

        mockMvc.perform(patch("/issue")
                .contentType(contentType)
                .content(json(new PatchRequest(newIssue.getId(), updates))))
                .andExpect(status().isOk());

        List<Issue> issues = issueRepository.findAll();
        assertEquals(1, issues.size());
    }

    @Test
    public void createIssueWithEmptyNewspaperIdYields400() throws Exception {
        Issue newIssue = issueRepository.insert(new Issue());

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("issuable", new RequestParameter("newspaper", ""));

        mockMvc.perform(patch("/issue")
                .contentType(contentType)
                .content(json(new PatchRequest(newIssue.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createIssueWithUnregisteredResourceModelYields400() throws Exception {
        Issue newIssue = issueRepository.insert(new Issue());

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("issuable", new RequestParameter("catalog", "catalogId"));

        mockMvc.perform(patch("/issue")
                .contentType(contentType)
                .content(json(new PatchRequest(newIssue.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerAddress() throws Exception {
        Customer newCustomer = customerRepository.insert(new Customer("Muffin Man", new Address("Boston", "MA", "Drury Lane", "12345")));

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("address", new RequestParameter(new Address("Boston", "MA", "Far Away Drive", "12345")));

        mockMvc.perform(patch("/customer")
                .contentType(contentType)
                .content(json(new PatchRequest(newCustomer.getId(), updates))))
                .andExpect(status().isOk());

        List<Customer> customers = customerRepository.findAll();
        assertEquals(1, customers.size());

        Customer customer = customers.get(0);
        assertEquals("Muffin Man", customer.getName());
        assertEquals("Boston", customer.getAddress().getCity());
        assertEquals("MA", customer.getAddress().getState());
        assertEquals("Far Away Drive", customer.getAddress().getAddress());
        assertEquals("12345", customer.getAddress().getZip());
    }

    @Test
    public void createCustomWithNullAddressIsOk() throws Exception {
        Customer newCustomer = customerRepository.insert(new Customer("Muffin Man", new Address("Boston", "MA", "Drury Lane", "12345")));

        Map<String, RequestParameter> updates = new HashMap<>();
        updates.put("address", new RequestParameter(null));

        mockMvc.perform(patch("/customer")
                .contentType(contentType)
                .content(json(new PatchRequest(newCustomer.getId(), updates))))
                .andExpect(status().isOk());
    }

//    @Test
//    public void createWithObjectOnFromIdYields400() throws Exception {
//        mockMvc.perform(patch("/issue")
//                .contentType(contentType)
//                .content(json(new CreateRequest(new ArrayList<RequestParameter>() {{
//                    add(new RequestParameter("magazine", new Magazine("My Magazine")));
//                }}))))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void createBookOrderWithFromIdList() throws Throwable {
//        Book book1 = bookRepository.insert(new Book("Book 1", "Author"));
//        Book book2 = bookRepository.insert(new Book("Book 2", "Author"));
//
//        mockMvc.perform(patch("/bookorder")
//                .contentType(contentType)
//                .content(json(new CreateRequest(new ArrayList<RequestParameter>() {{
//                    add(new RequestParameter("books", new ArrayList<String>() {{
//                        add(book1.getId());
//                        add(book2.getId());
//                    }}));
//                }}))))
//                .andExpect(status().isOk());
//
//        List<BookOrder> bookOrders = bookOrderRepository.findAll();
//        assertEquals(1, bookOrders.size());
//
//        BookOrder bookOrder = bookOrders.get(0);
//        assertEquals(2, bookOrder.getBookIds().size());
//        assertEquals(book1.objectId(), bookOrder.getBookIds().get(0));
//        assertEquals(book2.objectId(), bookOrder.getBookIds().get(1));
//    }
//
//    @Test
//    public void createMagazineWithCategories() throws Throwable {
//        mockMvc.perform(patch("/magazine")
//                .contentType(contentType)
//                .content(json(new CreateRequest(new ArrayList<RequestParameter>() {{
//                    add(new RequestParameter("categories", new ArrayList<String>() {{
//                        add("Food");
//                        add("Home");
//                    }}));
//                }}))))
//                .andExpect(status().isOk());
//
//        List<Magazine> magazines = magazineRepository.findAll();
//        assertEquals(1, magazines.size());
//
//        Magazine magazine = magazines.get(0);
//        assertEquals(2, magazine.getCategories().size());
//    }
//
//    @Test
//    public void createMailingList() throws Throwable {
//        mockMvc.perform(patch("/mailinglist")
//                .contentType(contentType)
//                .content(json(new CreateRequest(new ArrayList<RequestParameter>() {{
//                    add(new RequestParameter("addresses", new ArrayList<Address>() {{
//                        add(new Address("Boston", "MA", "123 Main Street", "09876"));
//                        add(new Address("New York", "NY", "987 Main Street", "12345"));
//                    }}));
//                }}))))
//                .andExpect(status().isOk());
//
//        List<MailingList> mailingLists = mailingListRepository.findAll();
//        assertEquals(1, mailingLists.size());
//
//        MailingList mailingList = mailingLists.get(0);
//        assertEquals(2, mailingList.getAddresses().size());
//        assertEquals("Boston", mailingList.getAddresses().get(0).getCity());
//        assertEquals("New York", mailingList.getAddresses().get(1).getCity());
//    }
}
