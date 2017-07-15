package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.BaseTests;
import com.chadrc.resourceapi.basic.BookRepository;
import com.chadrc.resourceapi.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryPatchResourceServiceTestsConfig.class)
public class RepositoryPatchResourceServiceTests extends BaseTests {
    @Autowired
    private BookRepository bookRepository;

    private Book bookToUpdate;

    @Before
    @Override
    public void setup() throws Throwable {
        super.setup();
        bookRepository.delete(bookRepository.findAll());

        bookToUpdate = bookRepository.insert(new Book("Book to Update", "Author"));
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void updateBookSuccess() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Updated Title");
        updates.put("author", "Updated Author");

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
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Value");

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(null, updates))))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void nonExistentResourceYields400() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Value");

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest("UnknownId", updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUnknownFieldYields400() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("notAField", "Value");

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void errorInUpdateYields400() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "");

        mockMvc.perform(patch("/book")
                .contentType(contentType)
                .content(json(new PatchRequest(bookToUpdate.getId(), updates))))
                .andExpect(status().isBadRequest());
    }
}
