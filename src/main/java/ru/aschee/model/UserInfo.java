package ru.aschee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aschee.domain.Company;
import ru.aschee.domain.Contact;
import ru.aschee.domain.User;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private User user;

    private Company company;

    private List<Contact> contacts;
}
