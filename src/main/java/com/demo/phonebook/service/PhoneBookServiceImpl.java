package com.demo.phonebook.service;

import com.demo.phonebook.model.Contact;
import com.demo.phonebook.repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class PhoneBookServiceImpl implements PhoneBookService
{
    private final PhoneBookRepository phoneBookRepository;

    public PhoneBookServiceImpl(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }

    @Override
    public void saveContact(Contact contact) {
        // TODO: Validation
        phoneBookRepository.save(contact);
    }

    @Override
    public void getContact(String name, String phoneNumber) {
        // TODO : validation
        if(ObjectUtils.isEmpty(name) || !phoneValidation(phoneNumber))
        {
            // throw exception
        }

        phoneBookRepository.findByNameAndNumber(name, phoneNumber);
    }

    private boolean phoneValidation(String phoneNumber)
    {
        return true;
    }

}
