package com.bookinghive.project.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String password;
    private String number;
    private Integer role;
}