package ru.example.service;

import ru.example.domain.Contact;
import ru.example.dto.ContactDTO;

import java.util.List;

public interface ContactService {

    List<ContactDTO> findByPhone(String numberPhone, Integer userId);

    ContactDTO findById(Integer contactId, Integer userId);

    List<ContactDTO> getAll(Integer userId);

    ContactDTO add(Integer userId, Contact contact);

    ContactDTO change(Integer userId, Integer contactId, Contact contact);

    void delete(Integer userId, Integer contactId);
}
