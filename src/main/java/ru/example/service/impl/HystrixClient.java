package ru.example.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.domain.Contact;
import ru.example.domain.User;
import ru.example.dto.ContactDTO;
import ru.example.dto.UserDTO;
import ru.example.service.ContactService;
import ru.example.service.JpaClient;
import ru.example.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class HystrixClient implements JpaClient {

    private final UserService userService;
    private final ContactService contactService;

    @Override
    @HystrixCommand
    public List<ContactDTO> findByPhone(String numberPhone, Integer userId) {
        return contactService.findByPhone(numberPhone, userId);
    }

    @Override
    @HystrixCommand
    public ContactDTO findById(Integer userId, Integer contactId) {
        return contactService.findById(userId, contactId);
    }

    @Override
    @HystrixCommand
    public List<ContactDTO> getAll(Integer userId) {
        return contactService.getAll(userId);
    }

    @Override
    @HystrixCommand
    public ContactDTO add(Integer userId, Contact contact) {
        return contactService.add(userId, contact);
    }

    @Override
    @HystrixCommand
    public ContactDTO change(Integer userId, Integer contactId, Contact contact) {
        return contactService.change(userId, contactId, contact);
    }

    @Override
    @HystrixCommand
    public void delete(Integer userId, Integer contactId) {
        contactService.delete(userId, contactId);
    }

    @Override
    public long contactsCount() {
        return contactService.contactsCount();
    }

    @Override
    @HystrixCommand
    public List<UserDTO> findByName(String name) {
        return userService.findByName(name);
    }

    @Override
    @HystrixCommand
    public UserDTO findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    @HystrixCommand
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @Override
    @HystrixCommand
    public UserDTO change(Integer id, User user) {
        return userService.change(id, user);
    }

    @Override
    @HystrixCommand
    public UserDTO add(User user) {
        return userService.add(user);
    }

    @Override
    @HystrixCommand
    public void delete(Integer id) {
        userService.delete(id);
    }

    @Override
    public long usersCount() {
        return userService.usersCount();
    }
}
