package ru.aschee.service;

import ru.aschee.dto.UserDTO;
import ru.aschee.dto.UserInfoDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    List<UserDTO> listUsers(String name, String order, String userSortField);

    UserDTO updateUser(UserDTO user, String id);

    UserDTO getUserById(String id);

    UserInfoDTO getUserInfoById(String id);

    void deleteUserById(String id);
}
