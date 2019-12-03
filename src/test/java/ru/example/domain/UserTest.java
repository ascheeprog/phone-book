package ru.example.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.example.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("/application-test.properties")
public class UserTest {

    @Autowired
    private UserRepository repository;

    private User user1;

    private User user2;

    private Validator validate;

    @Before
    public void setUp(){
        user1 = new User();
        user1.setFirstName("Alex");
        user1.setLastName("Litov");

        user2 = new User();
        user2.setFirstName("Tom");
        user2.setLastName("Kasper");

        validate = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenUser_whenCreateUser_thenTwoConstraintViolations(){
        User user = new User();
        Set<ConstraintViolation<User>> violation = validate.validate(user);
        assertThat(violation.size(),is(2));
    }
    @Test
    public void givenUser_whenCreateUser_thenOneConstraintViolations(){
        User user = new User();
        user.setFirstName("");
        Set<ConstraintViolation<User>> violation = validate.validate(user);
        assertThat(violation.size(),is(1));
    }

    @Test
    public void givenUser_whenSaveUser_thenGetCorrectUser(){
        User savedUser1 = repository.save(user1);
        assertThat(savedUser1.getId(),notNullValue());
        assertThat(savedUser1.getFirstName(),is(user1.getFirstName()));
        assertThat(savedUser1.getLastName(),is(user1.getLastName()));
    }

    @Test
    public void givenUser_whenSaveUserInDataBase_thenGetCorrectUserFromDBById(){
        User savedUser = repository.save(user1);
        User userFromDB = repository.findById(savedUser.getId()).get();

        assertThat(savedUser,notNullValue());
        assertThat(savedUser,is(userFromDB));
        assertThat(user1,is(userFromDB));
    }

    @Test
    public void givenUsers_whenSaveUsers_thenGetAllUsersFromDB(){
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        repository.save(user1);
        repository.save(user2);

        List<User> usersFromDB = repository.findAll();

        assertThat(usersFromDB,notNullValue());
        assertThat(usersFromDB.size(),is(2));
        assertThat(usersFromDB,containsInAnyOrder(users.toArray(new User[0])));
    }

    @Test
    public void givenUsers_whenSaveAndRemoveUsers_thenGetEmptyValuesFromDB(){
        User savedUser1 = repository.save(user1);
        User savedUser2 = repository.save(user2);

        assertThat(savedUser1,is(user1));
        assertThat(savedUser2,is(user2));

        repository.deleteById(savedUser1.getId());
        repository.deleteById(savedUser2.getId());

        assertTrue(repository.findById(savedUser1.getId()).isEmpty());
        assertTrue(repository.findById(savedUser2.getId()).isEmpty());
    }

    @Test
    public void givenUsers_whenSaveUsersAndFindAllUsersByFirstNameStartingWith_thenAllUsersWithBeginningOfTheFirstName(){
        User alexandr = new User();
        alexandr.setFirstName("alexandr");
        alexandr.setLastName("Hellowing");

        assertThat(alexandr,is(repository.save(alexandr)));
        assertThat(user1,is(repository.save(user1)));
        assertThat(user2,is(repository.save(user2)));

        List<User> users = new ArrayList<>();
        users.add(alexandr);
        users.add(user1);

        assertFalse(users.isEmpty());
        assertThat(users.size(),is(2));

        List<User> firstNamesBeginWithAlex = repository.findAllByFirstNameIgnoreCaseStartingWith("alex");

        assertThat(firstNamesBeginWithAlex,notNullValue());
        assertThat(firstNamesBeginWithAlex.size(),is(2));
        assertThat(firstNamesBeginWithAlex, containsInAnyOrder(users.toArray(new User[0])));
    }
}
