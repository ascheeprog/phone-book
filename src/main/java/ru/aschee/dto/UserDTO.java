package ru.aschee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    @NotNull
    @NotBlank
    @Size(max = 50, min = 2)
    private String nickname;

    private String firstName;

    private String lastName;
}
