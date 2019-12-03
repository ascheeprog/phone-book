package ru.example.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "users_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_generator", allocationSize = 1, initialValue = 1)
    private Integer id;

    @NotNull
    @NotBlank
    private String firstName;

    private String lastName;
}
