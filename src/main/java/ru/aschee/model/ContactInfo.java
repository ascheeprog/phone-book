package ru.aschee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aschee.domain.Company;
import ru.aschee.domain.Contact;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    private Contact contact;
    private Company company;
}
