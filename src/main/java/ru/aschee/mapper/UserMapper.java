package ru.aschee.mapper;

import org.mapstruct.Mapper;
import ru.aschee.domain.User;
import ru.aschee.dto.UserDTO;
import ru.aschee.dto.UserInfoDTO;
import ru.aschee.model.UserInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToDto(User user);

    List<UserDTO> listUsersToListDTO(List<User> users);

    User dtoToUser(UserDTO dto);

    UserInfoDTO userInfoToDTO(UserInfo userInfo);

    UserInfo dtoToUserInfo(UserInfoDTO dto);
}
