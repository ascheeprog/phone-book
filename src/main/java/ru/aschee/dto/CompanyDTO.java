package ru.aschee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class CompanyDTO {

    private String id;

    private String name;

    @Email
    private String email;

    private String phone;

    private String urlLogo;
}
