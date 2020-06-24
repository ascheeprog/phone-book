package ru.example.service;

import ru.example.domain.User;
import ru.example.dto.UserDTO;

import java.util.List;

public interface UserService {
    /**
     * Search users by first user name
     * @param name first user name
     * @return list users
     * @exception ru.example.exception.NotFoundException if users with name not found
     */
    List<UserDTO> findByName(String name);

    /**
     * Search user by user id
     * @param id user id
     * @return user if exists
     * @exception ru.example.exception.NotFoundException if user not found
     */
    UserDTO findById(Integer id);

    List<UserDTO> getAll();

    UserDTO change(Integer id,User user);

    UserDTO add(User user);

    void delete(Integer id);
}
