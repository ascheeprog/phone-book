package ru.example.service.impl;

import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.example.domain.User;
import ru.example.dto.UserDTO;
import ru.example.dto.mapper.UserMapper;
import ru.example.exception.NotFoundException;
import ru.example.repository.UserRepository;
import ru.example.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<UserDTO> findByName(String name) {
        List<User> users = repository.findAllByFirstNameIgnoreCaseStartingWith(name);
        if (users.isEmpty()) {
            throw new NotFoundException("Users with this name: " + name + " is not present");
        }
        return mapper.listUsersToListDTO(users);
    }

    @Override
    @Transactional
    public UserDTO findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.map(value -> mapper.userToDto(value)).orElseThrow(() -> new NotFoundException("User with this id: " + id + " not found"));
    }

    @Override
    @Transactional
    public List<UserDTO> getAll() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("Users not founds");
        }
        return mapper.listUsersToListDTO(users);
    }

    @Override
    @Transactional
    public UserDTO change(Integer id, User user) {
        return mapper.userToDto(repository.findById(id).map(user1 -> {
            if (user1.equals(user)) return user1;
            else {
                user1.setFirstName(user.getFirstName());
                user1.setLastName(user.getLastName());
                log.info("User with this id:{} is changed", id);
                return repository.save(user1);
            }
        }).orElseThrow(() -> new NotFoundException("User with this id: " + id + " not found")));
    }

    @Override
    @Transactional
    public UserDTO add(User user) {
        log.info("User with this firstName:{} and lastName:{} is created",user.getFirstName(),user.getLastName());
        return mapper.userToDto(repository.save(user));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            log.info("User with this id:{} is deleted", id);
            repository.delete(user.get());
        } else throw new NotFoundException("User with this: " + id + " not found");
    }
}
