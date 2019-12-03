package ru.example.dto.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.example.domain.User;
import ru.example.dto.UserDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private Validator validator;

    @Before
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenUserDTO_whenCreateContact_thenTwoConstraintViolationNullAndEmptyFirstName(){
        UserDTO userDTO = new UserDTO();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations.size(),is(2));
    }

    @Test
    public void givenUserDTO_whenCreateContact_thenTwoConstraintViolationEmptyAndSizeFirstName(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations.size(),is(2));
    }

    @Test
    public void givenUserDTO_whenCreateContact_thenOneConstraintViolationSizeFirstName(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("L");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations.size(),is(1));
    }

    @Test
    public void givenUserWithFirstAndLastName_whenMaps_thenGetCorrectUserDto() {
        User user = new User();
        user.setFirstName("Jordan");
        user.setLastName("Milos");

        UserDTO dto = userMapper.userToDto(user);

        assertThat(dto.getFirstName(), is(user.getFirstName()));
        assertThat(dto.getLastName(), is(user.getLastName()));
    }

    @Test
    public void givenUserDtoWithFirstAndLastName_whenMaps_thenGetCorrectUser() {
        UserDTO dto = new UserDTO();
        dto.setFirstName("Sylvana");
        dto.setLastName("Praudmur");

        User user = userMapper.DtoToUser(dto);

        assertThat(user.getLastName(), is(dto.getLastName()));
        assertThat(user.getFirstName(), is(dto.getFirstName()));
    }

    @Test
    public void givenListUsers_whenMaps_thenGetListUsersDTO(){
        User user1 = new User();
        user1.setFirstName("Jordan");

        User user2 = new User();
        user2.setFirstName("Myke");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        List<UserDTO> userDTOS = userMapper.listUsersToListDTO(users);

        assertThat(userDTOS.size(),is(users.size()));
        assertThat(userDTOS.get(0).getFirstName(),is(users.get(0).getFirstName()));
        assertThat(userDTOS.get(1).getFirstName(),is(users.get(1).getFirstName()));

    }
}