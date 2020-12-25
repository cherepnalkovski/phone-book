package com.demo.phonebook.service;

import com.demo.phonebook.model.Contact;

import java.util.List;
import java.util.Optional;

public interface PhoneBookService {
    /**
     * Persist contact
     * @param contact
     *      name - required
     *      phoneNumber - required, person number with format:
     *                            - should start with +389
     *                            - next two numbers can be 70, 71, 75, 76
     *                            - next we have 6 digits number
     * @return Contact object with id from DB
     */
    Contact saveContact(Contact contact);

    /**
     * Retrieve contact
     * @param name - person name
     * @param phoneNumber - person number
     * @return Contact object with id from DB
     */
    List<Contact> getContact(Optional<String> name, Optional<String> phoneNumber);
}
