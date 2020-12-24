package com.demo.phonebook.service;

import com.demo.phonebook.model.Contact;

public interface PhoneBookService {
    void saveContact(Contact contact);

    void getContact(String name, String phoneNumber);
}
