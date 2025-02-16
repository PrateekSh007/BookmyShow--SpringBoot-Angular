package com.bookinghive.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class ResponseModel {
    private Long user_id;
    private String token;
    private Integer role;

}