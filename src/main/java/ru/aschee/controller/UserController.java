package ru.aschee.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aschee.dto.UserDTO;
import ru.aschee.dto.UserInfoDTO;
import ru.aschee.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers(@RequestParam(required = false) String name,
                                                   @RequestParam(defaultValue = "DESC") String order,
                                                   @RequestParam(defaultValue = "nickname") String sortField) {
        return ResponseEntity.ok(userService.listUsers(name, order, sortField));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.updateUser(dto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}/info")
    public ResponseEntity<UserInfoDTO> userInfo(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getUserInfoById(userId), HttpStatus.OK);
    }
}
