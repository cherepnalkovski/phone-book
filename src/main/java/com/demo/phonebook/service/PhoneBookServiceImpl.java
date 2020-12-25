package com.demo.phonebook.service;

import com.demo.phonebook.model.Contact;
import com.demo.phonebook.repository.PhoneBookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    private final PhoneBookRepository phoneBookRepository;

    public PhoneBookServiceImpl(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }

    @Override
    public Contact saveContact(@Valid Contact contact) {
        return phoneBookRepository.save(contact);
    }

    @Override
    public List<Contact> getContact(Optional<String> name, Optional<String> phoneNumber) {
        return phoneBookRepository.findAll(filterContacts(name, phoneNumber));
    }

    private Specification<Contact> filterContacts(Optional<String> name, Optional<String> phoneNumber) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name.isPresent() && (name.get().length() > 0)) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.get().toLowerCase() + "%")));
            }
            if (phoneNumber.isPresent() &&(phoneNumber.get().length() > 0)) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), phoneNumber.get().toLowerCase())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
