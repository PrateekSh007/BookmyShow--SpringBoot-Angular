package com.bookinghive.project.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCastsRequest {
    private String movieName;
    private List<CastsModel> casts;
}
