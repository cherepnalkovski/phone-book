package com.demo.phonebook.repository;

import com.demo.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepository extends JpaRepository<Contact, String>, JpaSpecificationExecutor<Contact>  {
}
