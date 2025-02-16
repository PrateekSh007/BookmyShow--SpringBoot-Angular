package com.bookinghive.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Age is mandatory")
    private Integer age;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private Integer role;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    private String number;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
