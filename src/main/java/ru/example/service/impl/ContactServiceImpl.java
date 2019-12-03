package ru.example.service.impl;

import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.example.domain.Contact;
import ru.example.dto.ContactDTO;
import ru.example.dto.mapper.ContactMapper;
import ru.example.exception.NotFoundException;
import ru.example.repository.ContactRepository;
import ru.example.repository.UserRepository;
import ru.example.service.ContactService;

import java.util.List;

@Log4j2
@Service
@EnableTransactionManagement
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private ContactMapper mapper = Mappers.getMapper(ContactMapper.class);

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<ContactDTO> findByPhone(String numberPhone, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id:" + userId + " not found");
        }
        return mapper.listContactsToListDTO(contactRepository.findByPhoneAndUserId(numberPhone, userId));
    }

    @Override
    @Transactional
    public ContactDTO findById(Integer contactId, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id:" + userId + " not found");
        }
        return mapper.contactToDTO(contactRepository.findByIdAndUserId(contactId, userId).orElseThrow(() -> new NotFoundException("Contact with id:" + contactId + " not found")));
    }

    @Override
    @Transactional
    public List<ContactDTO> getAll(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id:" + userId + " not found");
        }
        List<Contact> contacts = contactRepository.findByUserId(userId);
        if (contacts.isEmpty()) {
            throw new NotFoundException("Contacts not founds");
        }
        return mapper.listContactsToListDTO(contacts);
    }

    @Override
    @Transactional
    public ContactDTO add(Integer userId, Contact contact) {
        return mapper.contactToDTO(userRepository.findById(userId).map(user -> {
            contact.setUser(user);
            log.info("Contact with this phone number:{} is created", contact.getPhone());
            return contactRepository.save(contact);
        }).orElseThrow(() ->
            new NotFoundException("User with id:" + userId + " not found")));
    }

    @Override
    @Transactional
    public ContactDTO change(Integer userId, Integer contactId, Contact contact) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id:" + userId + " not found");
        }
        return mapper.contactToDTO(contactRepository.findById(contactId).map(value -> {
            if (value.equals(contact)) {
                return value;
            } else {
                value.setEmail(contact.getEmail());
                value.setPhone(contact.getPhone());
                value.setFirstName(contact.getFirstName());
                value.setLastName(contact.getLastName());
                log.info("Contact with this phone number:{} is changed", contact.getPhone());
                return contactRepository.save(value);
            }
        }).orElseThrow(() -> new NotFoundException("Contact with id:" + contactId + " not found")));
    }

    @Override
    @Transactional
    public void delete(Integer userId, Integer contactId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id:" + userId + " not found");
        }
        Contact contact = contactRepository.findByIdAndUserId(userId, contactId).orElseThrow(() -> new NotFoundException("Contact with id:" + contactId + " not found"));
        log.info("Contact with this id:{} deleted",contactId);
        contactRepository.delete(contact);
    }
}