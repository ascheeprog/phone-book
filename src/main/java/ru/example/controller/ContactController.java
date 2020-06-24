package ru.example.controller;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.ContactDTO;
import ru.example.mapper.ContactMapper;
import ru.example.service.JpaClient;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ContactController {
    private final JpaClient client;
    private final ContactMapper mapper = Mappers.getMapper(ContactMapper.class);

    @GetMapping("/users/{userId}/contacts")
    public ResponseEntity<List<ContactDTO>> getAllContact(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(client.getAll(userId));
    }

    @GetMapping("/users/{userId}/contacts/{contactId}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable("userId") Integer userId, @PathVariable("contactId") Integer contactId) {
        return ResponseEntity.ok(client.findById(userId, contactId));
    }

    @GetMapping("/users/{userId}/contacts/search")
    public ResponseEntity<List<ContactDTO>> getContactsByNumberPhone(@PathVariable("userId") Integer userId, @RequestParam String number) {
        return ResponseEntity.ok(client.findByPhone(number, userId));
    }

    @PostMapping("/users/{userId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContactDTO> createContact(@PathVariable("userId") Integer userId, @Valid @RequestBody ContactDTO dto) {
        return new ResponseEntity<>(client.add(userId, mapper.DtoToContact(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/contacts/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContactDTO> editContact(@PathVariable("userId") Integer userId, @PathVariable("contactId") Integer contactId, @Valid @RequestBody ContactDTO dto) {
        return ResponseEntity.ok(client.change(userId, contactId, mapper.DtoToContact(dto)));
    }

    @DeleteMapping("/users/{userId}/contacts/{contactId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("userId") Integer userId, @PathVariable("contactId") Integer contactId) {
        client.delete(userId, contactId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
