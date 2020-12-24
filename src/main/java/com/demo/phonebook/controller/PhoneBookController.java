package com.demo.phonebook.controller;

import com.demo.phonebook.model.Contact;
import com.demo.phonebook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/phonebook/contacts")
public class PhoneBookController
{
    private final PhoneBookService phoneBookService;

    public PhoneBookController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @PostMapping
    public void saveContact(@RequestBody Contact contact)
    {
        phoneBookService.saveContact(contact);
    }

    @GetMapping
    public void getContact(@PathParam("name") String name, @PathParam("phoneNumber") String phoneNumber)
    {
        phoneBookService.getContact(name, phoneNumber);
    }
}
