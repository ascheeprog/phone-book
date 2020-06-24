package ru.example.mapper;

import org.mapstruct.Mapper;
import ru.example.domain.Contact;
import ru.example.dto.ContactDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    List<ContactDTO> listContactsToListDTO(List<Contact> contacts);
    ContactDTO contactToDTO(Contact contact);
    Contact DtoToContact(ContactDTO dto);
}
