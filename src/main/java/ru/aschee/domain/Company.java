package ru.aschee.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Data
@Entity
@Builder(toBuilder = true)
@QueryEntity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode
public class Company {

    @Id
    private String id;

    private String name;

    @Email
    private String email;

    private String phone;

    private String urlLogo;

    private String userId;

    private final String contactId;
}
