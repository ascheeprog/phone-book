package ru.aschee.mapper;

import org.mapstruct.Mapper;
import ru.aschee.domain.Contact;
import ru.aschee.dto.ContactDTO;
import ru.aschee.dto.ContactInfoDTO;
import ru.aschee.model.ContactInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    List<ContactDTO> listContactsToListDTO(List<Contact> contacts);

    ContactDTO contactToDTO(Contact contact);

    Contact dtoToContact(ContactDTO dto);

    ContactInfo dtoToContactInfo(ContactInfoDTO dto);

    ContactInfoDTO contactInfoToDTO(ContactInfo contactInfo);
}
