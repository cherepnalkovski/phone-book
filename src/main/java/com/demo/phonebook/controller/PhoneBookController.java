package com.demo.phonebook.controller;

import com.demo.phonebook.model.Contact;
import com.demo.phonebook.service.PhoneBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phonebook/contacts")
public class PhoneBookController
{
    private final PhoneBookService phoneBookService;

    public PhoneBookController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @PostMapping
    public Contact saveContact(@Valid @RequestBody Contact contact) {
        return phoneBookService.saveContact(contact);
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Contact> getContact(@RequestParam Optional<String> name, @RequestParam Optional<String> phoneNumber)
    {
        return phoneBookService.getContact(name, phoneNumber);
    }
}
