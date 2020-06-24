package ru.example.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.example.domain.Contact;
import ru.example.domain.User;
import ru.example.dto.ContactDTO;
import ru.example.dto.UserDTO;
import ru.example.mapper.ContactMapper;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContactMapperTest {
    private final ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);

    private Validator validate;

    @Before
    public void setUp(){
        validate = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenContactWithPhoneAndFirstNameAndUser_whenMaps_thenGetCorrectContactDTO() {
        User user = new User();
        user.setFirstName("John");

        Contact contact = new Contact();
        contact.setPhone("1234567098");
        contact.setFirstName("Alex");
        contact.setUser(user);

        ContactDTO dto = contactMapper.contactToDTO(contact);

        assertThat(dto.getPhone(), is(contact.getPhone()));
        assertThat(dto.getFirstName(), is(contact.getFirstName()));
    }

    @Test
    public void givenContactDto_whenCreateContactDto_thenThreeConstraintViolationsPatternNumberPhoneAndNullUserAndNullNumber(){
        ContactDTO dto = new ContactDTO();

        Set<ConstraintViolation<ContactDTO>> violation = validate.validate(dto);
        assertThat(violation.size(),is(2));
    }

    @Test
    public void givenContactDto_whenCreateContactDto_thenTwoConstraintViolationsPatternNumberPhoneAndNullUser(){
        ContactDTO dto = new ContactDTO();
        dto.setPhone("1");

        Set<ConstraintViolation<ContactDTO>> violation = validate.validate(dto);
        assertThat(violation.size(),is(1));
    }

    @Test
    public void givenContactDto_whenCreateContactDto_thenTwoConstraintViolationsNullPhoneNumberAndEmptyPhoneNumber(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Bob");
        ContactDTO dto = new ContactDTO();

        Set<ConstraintViolation<ContactDTO>> violation = validate.validate(dto);
        assertThat(violation.size(),is(2));
    }

    @Test
    public void givenContactDtoWithPhoneAndUserDto_whenMaps_thenCorrect(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Buba");

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPhone("1234567890");

        Contact contact = contactMapper.DtoToContact(contactDTO);

        assertThat(contactDTO.getPhone(),is(contact.getPhone()));
        assertThat(contactDTO.getFirstName(),is(contact.getFirstName()));
    }

    @Test
    public void givenListContacts_whenMaps_thenGetCorrectListContactDTO(){
        User user = new User();
        user.setFirstName("Rocky");
        Contact contact1= new Contact();
        contact1.setUser(user);
        contact1.setPhone("987654321");
        Contact contact2 = new Contact();
        contact2.setUser(user);
        contact2.setPhone("8889993311");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);

        List<ContactDTO> contactDTOS = contactMapper.listContactsToListDTO(contacts);

        assertThat(contactDTOS.size(),is(contacts.size()));
        assertThat(contactDTOS.get(0).getPhone(),is(contacts.get(0).getPhone()));
        assertThat(contactDTOS.get(1).getPhone(),is(contacts.get(1).getPhone()));
    }
}
