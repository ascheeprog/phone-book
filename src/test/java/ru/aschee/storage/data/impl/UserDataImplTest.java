//package ru.aschee.storage.data.impl;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.aschee.domain.User;
//import ru.aschee.dto.UserDTO;
//import ru.aschee.exception.NotFoundException;
//import ru.aschee.storage.repository.UserRepository;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserDataImplTest {
//
//    @MockBean
//    private UserRepository repository;
//
//    @Autowired
//    private UserDataImpl userService;
//
//    private User user;
//
//    @Before
//    public void setUp() {
//        user = new User();
//        user.setId(1);
//        user.setFirstName("Roland");
//        user.setLastName("Bultimor");
//
//    }
//
//    @Test
//    public void givenUser_whenSavedUser_thenSuccess() {
//        when(repository.save(user)).thenReturn(user);
//
//        UserDTO userDTO = userService.putUser(user);
//
//        assertThat(userDTO.getId(), is(user.getId()));
//        assertThat(userDTO.getFirstName(), is(user.getFirstName()));
//        assertThat(userDTO.getLastName(), is(user.getLastName()));
//        verify(repository, times(1)).save(user);
//    }
//
//    @Test
//    public void givenUserId_whenFindById_thenGetCorrectUser() {
//        when(repository.findById(1)).thenReturn(Optional.of(user));
//
//        UserDTO userDTO = userService.findUserById(1);
//
//        assertThat(userDTO.getId(), is(1));
//        assertThat(userDTO.getFirstName(), is(user.getFirstName()));
//        assertThat(userDTO.getLastName(), is(user.getLastName()));
//        verify(repository, times(1)).findById(1);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void givenNonExistingUserId_whenFindById_thenNotFoundException() {
//        when(repository.findById(1)).thenReturn(Optional.empty());
//
//        userService.findUserById(1);
//    }
//
//    @Test
//    public void givenUser_whenFindByName_thenGetAllCorrectUser() {
//        when(repository.findAllByFirstNameIgnoreCaseStartingWith("Roland")).thenReturn(Collections.singletonList(user));
//
//        List<UserDTO> userDTOS = userService.findUserByName("Roland");
//
//        assertThat(userDTOS.get(0).getFirstName(), is(user.getFirstName()));
//        assertThat(userDTOS.get(0).getLastName(), is(user.getLastName()));
//        assertThat(userDTOS.get(0).getId(), is(user.getId()));
//
//        verify(repository, times(1)).findAllByFirstNameIgnoreCaseStartingWith("Roland");
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void givenNonExistsUsersWithName_whenFindByName_thenNotFoundException() {
//        when(repository.findAllByFirstNameIgnoreCaseStartingWith("Roland")).thenReturn(Collections.emptyList());
//
//        userService.findUserByName("Roland");
//    }
//
//    @Test
//    public void givenUsers_whenGetAllUsers_thenCorrect() {
//        when(repository.findAll()).thenReturn(Collections.singletonList(user));
//
//        List<UserDTO> userDTOS = userService.listUsers();
//
//        assertThat(userDTOS.get(0).getFirstName(), is(user.getFirstName()));
//        assertThat(userDTOS.get(0).getLastName(), is(user.getLastName()));
//        assertThat(userDTOS.get(0).getId(), is(user.getId()));
//
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void givenUsersIsNonExists_whenGetAllUsers_thenNotFoundException(){
//        when(repository.findAll()).thenReturn(Collections.emptyList());
//        userService.listUsers();
//    }
//
//    @Test
//    public void givenUserIdAndChangedUser_whenChange_thenGetChangedUser() {
//        when(repository.findById(1)).thenReturn(Optional.of(user));
//
//        User user1 = new User();
//        user1.setFirstName("Josef");
//        user1.setLastName("Motato");
//        user1.setId(user.getId());
//        when(repository.save(user)).thenReturn(user);
//
//        UserDTO userDTO = userService.changeUser(1, user1);
//
//        assertThat(userDTO.getId(), is(user.getId()));
//        assertThat(userDTO.getFirstName(), is(user1.getFirstName()));
//        assertThat(userDTO.getLastName(), is(user1.getLastName()));
//
//        verify(repository, times(1)).save(user);
//        verify(repository, times(1)).findById(1);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void givenChangedUserAndNonExistingUserId_whenChange_thenNotFoundException() {
//        when(repository.findById(1)).thenReturn(Optional.empty());
//
//        User user1 = new User();
//        user1.setFirstName("Josef");
//        user1.setLastName("Motato");
//        user1.setId(user.getId());
//        when(repository.save(user)).thenReturn(user);
//        userService.changeUser(1, user1);
//    }
//
//    @Test
//    public void givenUserId_whenRemoveUser_thenSuccess() {
//        when(repository.findById(1)).thenReturn(Optional.of(user));
//        doNothing().when(repository).delete(user);
//        userService.deleteUser(1);
//
//        verify(repository, times(1)).delete(user);
//        verify(repository, times(1)).findById(1);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void givenNonExistingUserId_whenRemoveUser_thenNotFoundException(){
//        when(repository.findById(1)).thenReturn(Optional.empty());
//        userService.deleteUser(1);
//    }
//}