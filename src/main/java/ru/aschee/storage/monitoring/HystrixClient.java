package ru.aschee.storage.monitoring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aschee.domain.Company;
import ru.aschee.domain.Contact;
import ru.aschee.domain.User;
import ru.aschee.model.ContactInfo;
import ru.aschee.model.UserInfo;
import ru.aschee.model.request.ListUserRequest;
import ru.aschee.storage.JpaClient;
import ru.aschee.storage.data.CompanyData;
import ru.aschee.storage.data.ContactData;
import ru.aschee.storage.data.UserData;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@AllArgsConstructor
public class HystrixClient implements JpaClient {

    private final UserData userData;
    private final ContactData contactData;
    private final CompanyData companyData;

    @Override
    @HystrixCommand
    public List<Contact> findContactsByPhone(@Nonnull String numberPhone, @Nonnull String userId) {
        return contactData.findContactsByPhone(numberPhone, userId);
    }

    @Override
    @HystrixCommand
    public Contact findContactById(@Nonnull String userId, @Nonnull String contactId) {
        return contactData.findContactById(userId, contactId);
    }

    @Override
    @HystrixCommand
    public ContactInfo findContactInfoById(@Nonnull String contactId, @Nonnull String userId) {
        return contactData.findContactInfoById(contactId, userId);
    }

    @Override
    @HystrixCommand
    public List<Contact> findContactsByName(@Nonnull String userId, @Nonnull String name) {
        return contactData.findContactsByName(userId, name);
    }

    @Override
    @HystrixCommand
    public List<Contact> listContacts(@Nonnull String userId) {
        return contactData.listContacts(userId);
    }

    @Override
    @HystrixCommand
    public Contact putContact(@Nonnull Contact contact) {
        return contactData.putContact(contact);
    }

    @Override
    @HystrixCommand
    public void deleteContact(@Nonnull String userId, @Nonnull String contactId) {
        contactData.deleteContact(userId, contactId);
    }

    @Override
    @HystrixCommand
    public List<User> findUsersByRequest(@Nonnull ListUserRequest request) {
        return userData.findUsersByRequest(request);
    }

    @Override
    @HystrixCommand
    public User findUserById(@Nonnull String id) {
        return userData.findUserById(id);
    }

    @Override
    @HystrixCommand
    public UserInfo findUserInfoById(@Nonnull String id) {
        return userData.findUserInfoById(id);
    }

    @Override
    @HystrixCommand
    public User putUser(@Nonnull User user) {
        return userData.putUser(user);
    }

    @Override
    @HystrixCommand
    public boolean nicknameIsExists(String nickname) {
        return userData.nicknameIsExists(nickname);
    }

    @Override
    @HystrixCommand
    public void deleteUser(@Nonnull String id) {
        userData.deleteUser(id);
    }

    @Override
    @HystrixCommand
    public Company putCompany(@Nonnull Company company) {
        return companyData.putCompany(company);
    }

    @Override
    @HystrixCommand
    public Company findCompanyById(@Nonnull String companyId) {
        return companyData.findCompanyById(companyId);
    }
}
