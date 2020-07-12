//package ru.aschee.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mapstruct.factory.Mappers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.aschee.domain.User;
//import ru.aschee.dto.UserDTO;
//import ru.aschee.mapper.UserMapper;
//import ru.aschee.exception.NotFoundException;
//import ru.aschee.storage.JpaClient;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserControllerTest {
//
//    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JpaClient client;
//
//    private User user;
//
//    private UserDTO userDTO;
//
//    @Before
//    public void setUp() {
//        user = new User();
//        user.setId(1);
//        user.setFirstName("LeBlank");
//        userDTO = userMapper.userToDto(user);
//    }
//
//    @Test
//    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
//        List<UserDTO> userDTOS = Collections.singletonList(userDTO);
//
//        given(client.listUsers()).willReturn(userDTOS);
//
//        mockMvc.perform(get("/users")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$[0].id", is(user.getId())));
//    }
//
//    @Test
//    public void givenNonExistsUsers_whenGetUsers_thenGetNotFound() throws Exception {
//        given(client.listUsers()).willThrow(new NotFoundException("Users not found"));
//
//        mockMvc.perform(get("/users")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("Users not found")))
//                .andExpect(jsonPath("$.timestamp", notNullValue()));
//    }
//
//    @Test
//    public void givenUserId_whenGetUser_thenReturnJsonUser() throws Exception {
//        given(client.findUserById(1)).willReturn(userDTO);
//
//        mockMvc.perform(get("/users/{usersId}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(userDTO.getId())))
//                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())));
//    }
//
//    @Test
//    public void givenNonExistingUserId_whenGetUser_thenGetNotFound() throws Exception {
//        given(client.findUserById(1)).willThrow(new NotFoundException("User with this id: " + 1 + " not found"));
//
//        mockMvc.perform(get("/users/{usersId}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("User with this id: " + 1 + " not found")))
//                .andExpect(jsonPath("$.timestamp", notNullValue()));
//    }
//
//    @Test
//    public void givenUserName_whenGetUser_thenReturnJsonArray() throws Exception {
//        List<UserDTO> userDTOS = Collections.singletonList(userDTO);
//
//        given(client.findUserByName("LeBlank")).willReturn(userDTOS);
//        mockMvc.perform(get("/users/search").param("name", "LeBlank")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(userDTOS.size())))
//                .andExpect(jsonPath("$[0].id", is(userDTO.getId())))
//                .andExpect(jsonPath("$[0].firstName", is(userDTO.getFirstName())));
//    }
//
//    @Test
//    public void givenNonExistingUserName_whenGetUser_thenGetNotFound() throws Exception {
//        given(client.findUserByName("Loran")).willThrow(new NotFoundException("User with this name: " + "Loran" + " not found"));
//
//        mockMvc.perform(get("/users/search", 1).param("name", "Loran")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("User with this name: " + "Loran" + " not found")))
//                .andExpect(jsonPath("$.timestamp", notNullValue()));
//    }
//
//    @Test
//    public void givenUser_whenPostUser_thenReturnJsonUser() throws Exception {
//        given(client.putUser(user)).willReturn(userDTO);
//
//        mockMvc.perform(post("/users")
//                .content(new ObjectMapper().writeValueAsString(userDTO))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())));
//    }
//
//    @Test
//    public void givenNotCorrectUser_whenPostUser_thenGetBadRequest() throws Exception {
//        UserDTO userDTO = new UserDTO();
//
//        mockMvc.perform(post("/users")
//                .content(new ObjectMapper().writeValueAsString(userDTO))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void givenChangeUserAndUserId_whenPutUser_thenSuccess() throws Exception {
//        User user1 = new User();
//        user1.setId(1);
//        user1.setFirstName("Rick");
//        user1.setLastName("Romanov");
//        UserDTO userDTO1 = userMapper.userToDto(user1);
//        given(client.changeUser(1, user1)).willReturn(userDTO1);
//
//        mockMvc.perform(put("/users/{userId}", 1)
//                .content(new ObjectMapper().writeValueAsString(user1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.firstName", is(userDTO1.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(userDTO1.getLastName())));
//    }
//
//    @Test
//    public void givenNotCorrectChangedUserAndUserId_whenPutUser_thenBadRequest() throws Exception {
//        UserDTO userDTO = new UserDTO();
//
//        mockMvc.perform(put("/users/{userId}", 1)
//                .content(new ObjectMapper().writeValueAsString(userDTO))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void givenChangedUserAndNonExistingUserId_whenPutUser_thenNotFound() throws Exception {
//        User user1 = new User();
//        user1.setId(1);
//        user1.setFirstName("Rick");
//        user1.setLastName("Romanov");
//        UserDTO userDTO1 = userMapper.userToDto(user1);
//        given(client.changeUser(1, user1)).willThrow(new NotFoundException("User with this id: " + 1 + " not found"));
//
//        mockMvc.perform(put("/users/{userId}", 1)
//                .content(new ObjectMapper().writeValueAsString(user1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("User with this id: " + 1 + " not found")))
//                .andExpect(jsonPath("$.timestamp", notNullValue()));
//    }
//
//
//    @Test
//    public void givenUserId_whenDeleteUser_thenStatusAccepted() throws Exception {
//        mockMvc.perform(delete("/users/{userId}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isAccepted());
//    }
//
//    @Test
//    public void givenNonExistingUserId_whenDeleteUser_thenGetNotFound() throws Exception {
//        doThrow(new NotFoundException("User with this: " + 1 + "not found")).when(client).deleteUser(1);
//
//        mockMvc.perform(delete("/users/{userId}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("User with this: " + 1 + "not found")))
//                .andExpect(jsonPath("$.timestamp", notNullValue()));
//    }
//}
