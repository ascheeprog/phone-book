package ru.example.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Contact {

    @Id
    @GeneratedValue(generator = "contact_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "contact_id_seq", name = "contact_generator", allocationSize = 1, initialValue = 1)
    private Integer id;

    private String firstName;

    private String lastName;
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{10}")
    private String phone;

    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
}
