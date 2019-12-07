package ru.example.domain;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.example.repository.ContactRepository;
import ru.example.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactTest {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;

    private Validator validator;

    private User savedUser1;
    private User savedUser2;

    private Contact contact1;
    private Contact contact2;
    @Before
    public void setUp() {
        User user = new User();
        user.setFirstName("Dio-JoJo");
        user.setLastName("Brando");
        savedUser1 = userRepository.save(user);

        contact1 = new Contact();
        contact1.setPhone("1234567890");
        contact1.setFirstName("Nicolas");
        contact1.setUser(savedUser1);

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Brando");
        savedUser2 = userRepository.save(user1);

        contact2 = new Contact();
        contact2.setPhone("9999999999");
        contact2.setFirstName("Junius");
        contact2.setLastName("Brustus");
        contact2.setUser(savedUser2);
        contact2.setEmail("BrutusCaesar@gmail.com");

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenContact_whenSaveContact_thenSuccess() {
        Contact savedContact = contactRepository.save(contact2);

        assertThat(savedContact.getId(),notNullValue());
        assertThat(savedContact.getUser(),is(savedUser2));
        assertThat(savedContact.getPhone(),is(contact2.getPhone()));
        assertThat(savedContact.getFirstName(),is(contact2.getFirstName()));
        assertThat(savedContact.getLastName(),is(contact2.getLastName()));
        assertThat(savedContact.getEmail(),is(contact2.getEmail()));
    }

    @Test
    public void givenContact_whenSaveContactAndGetContactFromDB_thenCorrect(){
        Contact contact = contactRepository.save(contact2);

        Contact contactFromDB = contactRepository.findByIdAndUserId(contact.getId(),savedUser2.getId()).get();

        assertThat(contactFromDB.getId(),notNullValue());
        assertThat(contactFromDB.getUser(),is(contact2.getUser()));
        assertThat(contactFromDB.getEmail(),is(contact2.getEmail()));
        assertThat(contactFromDB.getPhone(),is(contact2.getPhone()));
        assertThat(contactFromDB.getFirstName(),is(contact2.getFirstName()));
        assertThat(contactFromDB.getLastName(),is(contact2.getLastName()));
        assertThat(contactFromDB,is(contact));
    }

    @Test
    public void givenContact_whenCreateContact_thenTwoConstraintViolationsNullAndNotCorrectPhoneNumber(){
        Contact contact = new Contact();
        Set<ConstraintViolation<Contact>> violation = validator.validate(contact);
        assertThat(violation.size(),is(2));
    }

    @Test
    public void givenContact_whenCreateContact_thenTwoConstraintViolationNullAndEmptyPhoneNumber(){
        Contact contact = new Contact();
        contact.setPhone("");
        Set<ConstraintViolation<Contact>> violation = validator.validate(contact);
        assertThat(violation.size(),is(2));
    }

    @Test
    public void givenContact_whenCreateContact_thenOneConstraintViolationsNotCorrectPhoneNumber(){
        Contact contact = new Contact();
        contact.setPhone("123");
        Set<ConstraintViolation<Contact>> violation = validator.validate(contact);
        assertThat(violation.size(),is(1));
    }

    @Test
    public void givenContact_whenCreateContact_thenOneConstraintViolationsNotCorrectEmail(){
        Contact contact = new Contact();
        contact.setPhone("1234567890");
        contact.setEmail("asd");
        Set<ConstraintViolation<Contact>> violation = validator.validate(contact);
        assertThat(violation.size(),is(1));
    }

    @Test
    public void givenContact_whenFindContactByPhoneNumber_thenGetListContacts(){
        Contact contact = new Contact();
        contact.setPhone("1234567890");
        contact.setUser(savedUser1);

        contactRepository.save(contact);
        contactRepository.save(contact1);

        List<Contact> contacts = new ArrayList<>();

        contacts.add(contact);
        contacts.add(contact1);

        List<Contact> contactsFromDB = contactRepository.findByPhoneAndUserId("1234567890",savedUser1.getId());

        assertThat(contactsFromDB,notNullValue());
        assertThat(contactsFromDB.size(),is(contacts.size()));
        assertThat(contactsFromDB,containsInAnyOrder(contacts.toArray(new Contact[0])));
    }

    @Test
    public void givenContact_whenRemoveContactFromDB_thenSuccess(){
        Contact savedContact = contactRepository.save(contact1);
        contactRepository.deleteById(savedContact.getId());

        assertTrue(contactRepository.findByIdAndUserId(savedContact.getId(),savedUser1.getId()).isEmpty());
    }

    @Test
    public void givenContacts_whenSavedContactsAndGetAllContacts_thenGetAllContactsFromDBCurrentUser(){
        Contact contact = new Contact();
        contact.setUser(savedUser1);
        contact.setPhone("1234569999");

        contactRepository.save(contact);
        contactRepository.save(contact1);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        contacts.add(contact1);

        List<Contact> contactsFromDB = contactRepository.findByUserId(savedUser1.getId());

        assertThat(contactsFromDB,notNullValue());
        assertThat(contactsFromDB.size(),is(contacts.size()));
        assertThat(contactsFromDB,containsInAnyOrder(contacts.toArray(new Contact[0])));
    }
}
