package ru.aschee.storage.data;

import ru.aschee.domain.Contact;
import ru.aschee.model.ContactInfo;

import java.util.List;

public interface ContactData {

    List<Contact> findContactsByPhone(String numberPhone, String userId);

    Contact findContactById(String contactId, String userId);

    ContactInfo findContactInfoById(String contactId, String userId);

    List<Contact> findContactsByName(String userId, String name);

    List<Contact> listContacts(String userId);

    Contact putContact(Contact contact);

    void deleteContact(String contactId, String userId);
}
