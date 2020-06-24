package ru.example.mapper;

import org.mapstruct.Mapper;
import ru.example.domain.User;
import ru.example.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToDto(User user);
    List<UserDTO> listUsersToListDTO(List<User> users);
    User DtoToUser(UserDTO dto);
}
