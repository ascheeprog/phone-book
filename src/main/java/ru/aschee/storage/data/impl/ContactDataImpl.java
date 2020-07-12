package ru.aschee.storage.data.impl;

import com.querydsl.core.types.Projections;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aschee.domain.Contact;
import ru.aschee.model.ContactInfo;
import ru.aschee.storage.Storage;
import ru.aschee.storage.data.ContactData;
import ru.aschee.storage.repository.ContactRepository;

import java.util.List;

import static ru.aschee.domain.QCompany.company;
import static ru.aschee.domain.QContact.contact;

@Primary
@Component
public class ContactDataImpl extends Storage implements ContactData {
    private final ContactRepository contactRepository;

    public ContactDataImpl(ContactRepository contactRepository) {
        super(Contact.class);
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findContactsByPhone(String numberPhone, String userId) {
        return from(contact)
                .where(safe(contact.phone::contains, numberPhone),
                        contact.userId.eq(userId))
                .orderBy(contact.firstName.asc(), contact.lastName.asc())
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findContactsByName(String userId, String name) {
        return from(contact).distinct()
                .where(contact.userId.eq(userId),
                        safe(contact.firstName::eq, name),
                        safe(contact.lastName::eq, name))
                .orderBy(contact.firstName.asc(), contact.lastName.asc())
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact findContactById(String contactId, String userId) {
        return from(contact)
                .where(contact.userId.eq(userId), contact.id.eq(contactId))
                .fetchOne();
    }

    @Override
    @Transactional(readOnly = true)
    public ContactInfo findContactInfoById(String contactId, String userId) {
        return from(contact)
                .select(Projections.constructor(ContactInfo.class, contact, company))
                .innerJoin(company).on(contact.id.eq(company.contactId))
                .where(contact.id.eq(contactId), contact.userId.eq(userId))
                .fetchOne();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> listContacts(String userId) {
        return from(contact).where(safe(contact.userId::eq, userId))
                .orderBy(contact.firstName.asc(), contact.lastName.asc())
                .fetch();
    }

    @Override
    @Transactional
    public Contact putContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void deleteContact(String contactId, String userId) {
        delete(contact)
                .where(contact.userId.eq(userId))
                .execute();
    }
}