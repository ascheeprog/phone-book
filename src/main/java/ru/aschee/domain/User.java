package ru.aschee.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder(toBuilder = true)
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nickname;

    private String firstName;

    private String lastName;
}
