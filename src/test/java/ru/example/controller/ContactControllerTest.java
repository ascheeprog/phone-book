package ru.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.domain.Contact;
import ru.example.domain.User;
import ru.example.dto.ContactDTO;
import ru.example.dto.mapper.ContactMapper;
import ru.example.exception.NotFoundException;
import ru.example.service.ContactService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContactControllerTest {

    private ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    private User user;
    private Contact contact;
    private ContactDTO contactDTO;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("Ludvig");
        user.setLastName("Tudor");

        contact = new Contact();
        contact.setId(1);
        contact.setFirstName("Saitama");
        contact.setLastName("OnePunchMan");
        contact.setPhone("1234567890");
        contact.setUser(user);

        contactDTO = contactMapper.contactToDTO(contact);
    }

    @Test
    public void givenUserId_whenGetContacts_thenReturnJsonArray() throws Exception {
        List<ContactDTO> contacts = Collections.singletonList(contactDTO);

        given(contactService.getAll(1)).willReturn(contacts);

        mockMvc.perform(get("/users/{userId}/contacts", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(contactDTO.getFirstName())))
                .andExpect(jsonPath("$[0].id", is(contactDTO.getId())))
                .andExpect(jsonPath("$[0].lastName", is(contactDTO.getLastName())))
                .andExpect(jsonPath("$[0].phone", is(contactDTO.getPhone())));
    }

    @Test
    public void givenNonExistsContactsOrUserId_whenGetContacts_thenGetNotFound() throws Exception {
        given(contactService.getAll(1)).willThrow(new NotFoundException("Contacts not found or user with this id:" + 1 + " not found"));

        mockMvc.perform(get("/users/{userId}/contacts", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Contacts not found or user with this id:" + 1 + " not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void givenUserIdAndContactId_whenGetContact_thenThenReturnJson() throws Exception {
        given(contactService.findById(1, 1)).willReturn(contactDTO);

        mockMvc.perform(get("/users/{userId}/contacts/{contactId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(contactDTO.getFirstName())))
                .andExpect(jsonPath("$.id", is(contactDTO.getId())))
                .andExpect(jsonPath("$.lastName", is(contactDTO.getLastName())))
                .andExpect(jsonPath("$.phone", is(contactDTO.getPhone())));
    }

    @Test
    public void givenNonExistingContactIdOrUserId_whenGetContact_thenGetNotFound() throws Exception {
        given(contactService.findById(1, 1)).willThrow(new NotFoundException("User with id:" + 1 + " not found" + " or " + "Contact with id:" + 1 + " not found"));

        mockMvc.perform(get("/users/{userId}/contacts/{contactId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("User with id:" + 1 + " not found" + " or " + "Contact with id:" + 1 + " not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void givenUserIdAndContactName_whenGetContacts_thenReturnJsonArray() throws Exception {
        List<ContactDTO> contacts = Collections.singletonList(contactDTO);

        given(contactService.findByPhone("1234567890", 1)).willReturn(contacts);

        mockMvc.perform(get("/users/{userId}/contacts/search", 1).param("number", "1234567890")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(contactDTO.getFirstName())))
                .andExpect(jsonPath("$[0].id", is(contactDTO.getId())))
                .andExpect(jsonPath("$[0].lastName", is(contactDTO.getLastName())))
                .andExpect(jsonPath("$[0].phone", is(contactDTO.getPhone())));
    }

    @Test
    public void givenNonExistingContactNameOrUserId_whenGetContact_thenGetNotFound() throws Exception {
        given(contactService.findByPhone("9876543210", 1))
                .willThrow(new NotFoundException("User with id:" + 1
                        + " not found"
                        + " or "
                        + "Contact with phone: "
                        + "9876543210"
                        + " not found"));

        mockMvc.perform(get("/users/{userId}/contacts/search", 1).param("number", "9876543210")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("User with id:" + 1 + " not found" + " or " + "Contact with phone: " + "9876543210" + " not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void givenUserIdAndContact_whenPostContact_thenReturnJson() throws Exception {
        given(contactService.add(1, contact)).willReturn(contactDTO);

        mockMvc.perform(post("/users/{userId}/contacts", 1)
                .content(new ObjectMapper().writeValueAsString(contactDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenUserIdAndNotCorrectContact_whenPostContact_thenGetBadRequest() throws Exception {
        ContactDTO notCorrectContact = new ContactDTO();

        mockMvc.perform(post("/users/{userId}/contacts", 1)
                .content(new ObjectMapper().writeValueAsString(notCorrectContact))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUserIdAndContactIdAndChangedContact_whenPutContact_thenReturnJson() throws Exception {
        Contact changedContact = new Contact();
        changedContact.setId(1);
        changedContact.setFirstName("OverPowerMan");
        changedContact.setLastName("Glinomesovich");
        changedContact.setPhone("1234567890");
        changedContact.setUser(user);

        ContactDTO changedContactDto = contactMapper.contactToDTO(changedContact);

        given(contactService.change(1, 1, changedContact)).willReturn(changedContactDto);

        mockMvc.perform(put("/users/{userId}/contacts/{contactId}", 1, 1)
                .content(new ObjectMapper().writeValueAsString(changedContact))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUserIdAndContactIdAndNotCorrectContact_whenPutContact_thenGetBadRequest() throws Exception {
        ContactDTO notCorrectContact = new ContactDTO();

        mockMvc.perform(put("/users/{userId}/contacts/{contactId}", 1, 1)
                .content(new ObjectMapper().writeValueAsString(notCorrectContact))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUserIdAndContactId_whenRemoveContact_thenSuccess() throws Exception {
        mockMvc.perform(delete("/users/{userId}/contacts/{contactId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void givenNonExistingUserIdOrContactId_whenRemoveContact_thenNotFound() throws Exception {
        doThrow(new NotFoundException("User with id:" + 1 + " not found" + " or " + "Contact with id:" + 1 + " not found")).when(contactService).delete(1, 1);

        mockMvc.perform(delete("/users/{userId}/contacts/{contactId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("User with id:" + 1 + " not found" + " or " + "Contact with id:" + 1 + " not found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}
