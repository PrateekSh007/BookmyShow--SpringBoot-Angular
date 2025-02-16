package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
    private String email;
    private String password;
    private Integer role;
}
