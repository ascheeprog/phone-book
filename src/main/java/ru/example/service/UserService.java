package ru.example.service;

import ru.example.domain.User;
import ru.example.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findByName(String name);

    UserDTO findById(Integer id);

    List<UserDTO> getAll();

    UserDTO change(Integer id,User user);

    UserDTO add(User user);

    void delete(Integer id);
}
