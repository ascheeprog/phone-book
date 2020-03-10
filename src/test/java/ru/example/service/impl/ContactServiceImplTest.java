package ru.example.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.example.domain.Contact;
import ru.example.domain.User;
import ru.example.dto.ContactDTO;
import ru.example.exception.NotFoundException;
import ru.example.repository.ContactRepository;
import ru.example.repository.UserRepository;
import ru.example.service.ContactService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContactRepository contactRepository;

    private Contact contact1;

    private Contact contact2;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Loran");
        user.setLastName("Pain");
        user.setId(1);

        contact1 = new Contact();
        contact1.setId(1);
        contact1.setFirstName("Scott");
        contact1.setLastName("Travis");
        contact1.setPhone("1234567890");
        contact1.setUser(user);

        contact2 = new Contact();
        contact2.setFirstName("Big");
        contact2.setLastName("Daddy");
        contact2.setPhone("9870654321");
        contact2.setUser(user);
    }

    @Test
    public void givenContact_whenSavedContact_thenReturnSaveContact() {
        contact1.setUser(null);

        when(contactRepository.save(contact1)).thenReturn(contact1);
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        User user1 = userRepository.findById(user.getId()).get();

        ContactDTO contactDTO = contactService.add(user1.getId(), contact1);

        assertThat(contactDTO.getId(), is(contact1.getId()));
        assertThat(contactDTO.getFirstName(), is(contact1.getFirstName()));
        assertThat(contactDTO.getLastName(), is(contact1.getLastName()));
        assertThat(contactDTO.getPhone(), is(contact1.getPhone()));
        verify(contactRepository, times(1)).save(contact1);
        verify(userRepository, times(2)).findById(user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void givenContactAndUserWithoutId_whenSaveContact_thenNotFoundException() {
        contact1.setUser(null);
        when(contactRepository.save(contact1)).thenReturn(contact1);
        contactService.add(1, contact1);
    }

    @Test
    public void givenUserId_whenGetAllContactUser_thenSuccess() {

        when(contactRepository.findByUserId(user.getId())).thenReturn(Collections.singletonList(contact1));
        when(userRepository.existsById(1)).thenReturn(true);

        List<ContactDTO> contactDTOList = contactService.getAll(user.getId());

        assertThat(contactDTOList.size(), is(1));
        assertThat(contactDTOList.get(0).getPhone(), is(contact1.getPhone()));
        assertThat(contactDTOList.get(0).getFirstName(), is(contact1.getFirstName()));
        assertThat(contactDTOList.get(0).getId(), is(contact1.getId()));
        assertThat(contactDTOList.get(0).getLastName(), is(contact1.getLastName()));
        verify(contactRepository, times(1)).findByUserId(user.getId());
        verify(userRepository, times(1)).existsById(1);
    }

    @Test(expected = NotFoundException.class)
    public void givenUserIdAndNonExistsContact_whenGetAllContactsUser_thenNotFoundException() {
        when(contactRepository.findByUserId(1)).thenReturn(Collections.emptyList());
        contactService.getAll(1);
    }
    @Test(expected = NotFoundException.class)
    public void givenNonExistingUserIdAndContact_whenGetAllContactsUser_thenNotFoundException() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        contactService.getAll(1);
    }

    @Test
    public void givenPhoneNumberAndUserId_whenFindByPhone_thenGetListContact() {
        when(contactRepository.findByPhoneAndUserId("1234567890", user.getId())).thenReturn(Collections.singletonList(contact1));
        when(userRepository.existsById(user.getId())).thenReturn(true);

        List<ContactDTO> contactDTOS = contactService.findByPhone("1234567890", user.getId());

        assertThat(contactDTOS.size(), is(1));
        assertThat(contactDTOS.get(0).getPhone(), is("1234567890"));
        assertThat(contactDTOS.get(0).getFirstName(), is(contact1.getFirstName()));
        assertThat(contactDTOS.get(0).getLastName(), is(contact1.getLastName()));
        assertThat(contactDTOS.get(0).getId(), is(contact1.getId()));
        verify(contactRepository, times(1)).findByPhoneAndUserId("1234567890", user.getId());
        verify(userRepository, times(1)).existsById(user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void givenPhoneNumber_whenFindByPhone_thenNotFoundException() {
        when(contactRepository.findByPhoneAndUserId("1234567890", user.getId())).thenReturn(Collections.singletonList(contact1));

        List<ContactDTO> contactDTOS = contactService.findByPhone("1234567890", user.getId());

        assertThat(contactDTOS.size(), is(1));
        assertThat(contactDTOS.get(0).getPhone(), is("1234567890"));
        assertThat(contactDTOS.get(0).getFirstName(), is(contact1.getFirstName()));
        assertThat(contactDTOS.get(0).getLastName(), is(contact1.getLastName()));
        assertThat(contactDTOS.get(0).getId(), is(contact1.getId()));
        verify(contactRepository, times(1)).findByPhoneAndUserId("1234567890", user.getId());
    }

    @Test
    public void givenContactIdAndUserId_whenFindById_thenGetCorrectContact() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(true);

        ContactDTO contactDTO = contactService.findById(1, 1);

        assertThat(contactDTO.getId(), is(contact1.getId()));
        assertThat(contactDTO.getFirstName(), is(contact1.getFirstName()));
        assertThat(contactDTO.getLastName(), is(contact1.getLastName()));
        assertThat(contactDTO.getPhone(), is(contact1.getPhone()));
        verify(contactRepository, times(1)).findByIdAndUserId(1, 1);
        verify(userRepository, times(1)).existsById(1);
    }

    @Test(expected = NotFoundException.class)
    public void givenContactIdAndNotCorrectUserId_whenFindById_thenNotFoundException() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(false);

        contactService.findById(1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void givenUserIdAndNotCorrectContactId_whenFindById_thenNotFoundException() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.empty());
        when(userRepository.existsById(1)).thenReturn(true);

        contactService.findById(1, 1);
    }

    @Test
    public void givenUserIdAndContactIdAndChangeContact_whenChangeContact_thenSuccess() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(true);
        when(contactRepository.save(contact1)).thenReturn(contact1);

        ContactDTO contactDTO = contactService.change(1, 1, contact2);

        assertThat(contactDTO.getPhone(), is(contact2.getPhone()));
        assertThat(contactDTO.getFirstName(), is(contact2.getFirstName()));
        assertThat(contactDTO.getLastName(), is(contact2.getLastName()));
        assertThat(contactDTO.getId(), is(contact1.getId()));
        verify(contactRepository, times(1)).findById(1);
        verify(userRepository, times(1)).existsById(1);
        verify(contactRepository, times(1)).save(contact1);
    }

    @Test(expected = NotFoundException.class)
    public void givenUserIdAndNotCorrectContactId_whenChangeContact_thenNotFoundException() {
        when(contactRepository.findById(1)).thenReturn(Optional.empty());
        when(userRepository.existsById(1)).thenReturn(true);
        when(contactRepository.save(contact2)).thenReturn(contact2);

        contactService.change(1, 1, contact2);
    }

    @Test(expected = NotFoundException.class)
    public void givenContactIdAndNotCorrectUserId_whenChangeContact_thenNotFoundException() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(false);
        when(contactRepository.save(contact2)).thenReturn(contact2);

        contactService.change(1, 1, contact2);
    }

    @Test(expected = NotFoundException.class)
    public void givenContactIdAndUserId_whenChangeContact_thenNotFoundException() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(true);
        when(contactRepository.save(contact2)).thenReturn(null);

        contactService.change(1, 1, contact2);
    }

    @Test
    public void givenUserIdAndContactId_whenDeleteContact_thenSuccess() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(true);

        doNothing().when(contactRepository).delete(contact1);

        contactService.delete(1, 1);

        verify(contactRepository, times(1)).findByIdAndUserId(1, 1);
        verify(userRepository, times(1)).existsById(1);
        verify(contactRepository, times(1)).delete(contact1);
    }

    @Test(expected = NotFoundException.class)
    public void givenUserIdAndNotCorrectContactId_whenDeleteContact_thenNotFoundException() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.empty());
        when(userRepository.existsById(1)).thenReturn(true);

        doNothing().when(contactRepository).delete(contact1);
        contactService.delete(1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void givenContactIdAndNotCorrectUserId_whenDeleteContact_thenNotFoundException() {
        when(contactRepository.findByIdAndUserId(1, 1)).thenReturn(Optional.of(contact1));
        when(userRepository.existsById(1)).thenReturn(false);

        doNothing().when(contactRepository).delete(contact1);
        contactService.delete(1, 1);
    }
}
