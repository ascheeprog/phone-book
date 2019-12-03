package ru.example.controller;


import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.UserDTO;
import ru.example.dto.mapper.UserMapper;
import ru.example.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserDTO>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.add(mapper.DtoToUser(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Integer userId, @Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.change(userId, mapper.DtoToUser(dto)), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
