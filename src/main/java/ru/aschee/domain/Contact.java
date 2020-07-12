package ru.aschee.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder(toBuilder = true)
@QueryEntity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Contact {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    @NotBlank
    private String phone;

    @Email
    private String email;

    private String userId;

    private String url;
}
