package com.demo.phonebook;

import com.demo.phonebook.controller.PhoneBookController;
import com.demo.phonebook.model.Contact;
import com.demo.phonebook.repository.PhoneBookRepository;
import com.demo.phonebook.service.PhoneBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PhoneBookController.class)
class PhoneBookApplicationTests
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PhoneBookService phoneBookService;

    @MockBean
    private PhoneBookRepository phoneBookRepository;

    @Test
    void validPayload() throws Exception {
        Contact contact = new Contact();
        contact.setName("Vladimir");
        contact.setPhoneNumber("+38975123456");

        String requestBody = objectMapper.writeValueAsString(contact);

        mvc.perform(post("/phonebook/contacts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void nameNotProvided_invalidPhoneNumber() throws Exception {
        Contact contact = new Contact();
        contact.setName("");
        contact.setPhoneNumber("+3893456");

        String requestBody = objectMapper.writeValueAsString(contact);

        mvc.perform(post("/phonebook/contacts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.violations[?(@.errorMessage == 'name')]").exists())
                .andExpect(jsonPath("$.violations[?(@.errorMessage == 'phoneNumber')]").exists());
    }

    @Test
    void nameNotProvided() throws Exception {
        Contact contact = new Contact();
        contact.setPhoneNumber("+38975123456");

        String requestBody = objectMapper.writeValueAsString(contact);

        mvc.perform(post("/phonebook/contacts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].errorCode").value("INVALID_PAYLOAD"))
                .andExpect(jsonPath("$.violations[0].errorMessage").value("name"));
    }

    @Test
    void phoneNotProvided() throws Exception {
        Contact contact = new Contact();
        contact.setName("Vladimir");

        String requestBody = objectMapper.writeValueAsString(contact);

        mvc.perform(post("/phonebook/contacts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].errorCode").value("INVALID_PAYLOAD"))
                .andExpect(jsonPath("$.violations[0].errorMessage").value("phoneNumber"));
    }

    @Test
    void invalidPhoneFormat() throws Exception {
        Contact contact = new Contact();
        contact.setName("Vladimir");
        contact.setPhoneNumber("+1234");


        String requestBody = objectMapper.writeValueAsString(contact);

        mvc.perform(post("/phonebook/contacts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].errorCode").value("INVALID_PAYLOAD"))
                .andExpect(jsonPath("$.violations[0].errorMessage").value("phoneNumber"));
    }
}
