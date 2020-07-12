package ru.aschee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class ContactDTO {
    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{10}")
    private String phone;

    @Email
    private String email;

    private String url;
}
