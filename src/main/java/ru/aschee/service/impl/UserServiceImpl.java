package ru.aschee.service.impl;

import com.github.dockerjava.api.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.aschee.domain.User;
import ru.aschee.dto.UserDTO;
import ru.aschee.dto.UserInfoDTO;
import ru.aschee.exception.NotFoundException;
import ru.aschee.mapper.UserMapper;
import ru.aschee.model.UserSortField;
import ru.aschee.model.request.ListUserRequest;
import ru.aschee.service.UserService;
import ru.aschee.service.util.ServiceUtils;
import ru.aschee.storage.JpaClient;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JpaClient client;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Override
    public UserDTO createUser(UserDTO user) {
        User userFromDTO = mapper.dtoToUser(user);
        checkExistsNickname(null, userFromDTO.getNickname());
        checkIdSpecified(userFromDTO.getId());
        return mapper.userToDto(client.putUser(userFromDTO.toBuilder().id(ServiceUtils.generateId()).build()));
    }

    @Override
    public List<UserDTO> listUsers(String name, String order, String sortField) {
        ListUserRequest request = ListUserRequest.builder()
                .name(name)
                .sortOrder(ServiceUtils.sortOrder(order))
                .sortField(UserSortField.valueOf(sortField))
                .build();
        return mapper.listUsersToListDTO(client.findUsersByRequest(request));
    }

    @Override
    public UserDTO updateUser(UserDTO user, String id) {
        User userFromDTO = mapper.dtoToUser(user);
        User userFromDB = checkValidUser(id);
        checkIdSpecified(userFromDTO.getId());
        userFromDTO.setId(userFromDB.getId());
        checkExistsNickname(userFromDB.getNickname(), userFromDTO.getNickname());
        if (!Objects.equals(userFromDTO, userFromDB)) {
            return mapper.userToDto(client.putUser(userFromDTO));
        } else {
            throw new BadRequestException("Users are equal");
        }
    }

    @Override
    public UserDTO getUserById(String id) {
        return mapper.userToDto(client.findUserById(id));
    }

    @Override
    public UserInfoDTO getUserInfoById(String id) {
        return mapper.userInfoToDTO(client.findUserInfoById(id));
    }

    @Override
    public void deleteUserById(String id) {
        if (client.findUserById(id) == null) {
            throw new NotFoundException("User not found, id: " + id);
        }
        client.deleteUser(id);
    }

    private User checkValidUser(String id) {
        User userFromDB = client.findUserById(id);
        if (userFromDB == null) {
            throw new NotFoundException("User not found, id: " + id);
        }
        return userFromDB;
    }

    private void checkExistsNickname(String oldNickname, String newNickname) {
        if (client.nicknameIsExists(newNickname) && !Objects.equals(newNickname, oldNickname)) {
            throw new BadRequestException("Nickname is exists, nickname: " + newNickname);
        }
    }

    private void checkIdSpecified(String id) {
        if (id != null) {
            throw new BadRequestException("Id must not be specified");
        }
    }
}