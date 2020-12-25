package com.demo.phonebook;

import com.demo.phonebook.model.Contact;
import com.demo.phonebook.repository.PhoneBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PhoneBookControllerIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withPassword("inmemory")
            .withUsername("inmemory");

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    public void init()
    {
        phoneBookRepository.save(new Contact("Vladimir New", "+38975444999" ));
        phoneBookRepository.save(new Contact("Vlado", "+38975111229" ));
        phoneBookRepository.save(new Contact("Marija", "+38975881229" ));
    }

    @AfterEach
    public void clean()
    {
        phoneBookRepository.deleteAll();
    }

    @Test
    public void persistValidContactToDB() {

        Contact requestBody = new Contact();
        requestBody.setName("John");
        requestBody.setPhoneNumber("+38970147369");

        assertEquals(3, phoneBookRepository.findAll().size());

        ResponseEntity<Contact> result = testRestTemplate.postForEntity("/phonebook/contacts", requestBody, Contact.class);

        assertNotNull(result);
        assertNotNull(result.getBody().getId());
        assertEquals("John", result.getBody().getName());
        assertEquals("+38970147369", result.getBody().getPhoneNumber());
        assertEquals(4, phoneBookRepository.findAll().size());
    }

    @Test
    public void searchByName() {

        String url = "/phonebook/contacts?name=vlad";

        assertEquals(3, phoneBookRepository.findAll().size());

        ParameterizedTypeReference<List<Contact>> typeRef =
                new ParameterizedTypeReference<List<Contact>>() {};
        ResponseEntity<List<Contact>> response =
                testRestTemplate.exchange(url, HttpMethod.GET, null, typeRef);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().filter(x -> x.getName().equalsIgnoreCase("Vladimir New")).findFirst().isPresent());
        assertTrue(response.getBody().stream().filter(x -> x.getName().equalsIgnoreCase("Vlado")).findFirst().isPresent());
    }

    //TODO: '+' sign issues with encoding
    //@Test
    public void searchByPhone() {

        String phoneNumber = "+38975444999";
        String url = "http://localhost:8080/phonebook/contacts";

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url + "?phoneNumber={phoneNumber}").build(phoneNumber);


        ParameterizedTypeReference<List<Contact>> typeRef = new ParameterizedTypeReference<List<Contact>>() {};
        ResponseEntity<List<Contact>> response =
                testRestTemplate.exchange(uri, HttpMethod.GET, null, typeRef);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Vladimir New", response.getBody().get(0).getName());
        assertEquals("+38975444999", response.getBody().get(0).getPhoneNumber());
    }
}