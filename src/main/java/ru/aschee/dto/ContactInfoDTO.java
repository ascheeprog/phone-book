package ru.aschee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDTO {

    private ContactDTO contact;

    private CompanyDTO company;
}
