package ru.example.controller;


import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.UserDTO;
import ru.example.mapper.UserMapper;
import ru.example.service.JpaClient;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final JpaClient client;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(client.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(client.findById(userId));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserDTO>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(client.findByName(name));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(client.add(mapper.DtoToUser(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Integer userId, @Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(client.change(userId, mapper.DtoToUser(dto)), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        client.delete(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
