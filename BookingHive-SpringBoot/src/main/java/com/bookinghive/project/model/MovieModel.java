package com.bookinghive.project.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {
    private Long movieId;
    private String movieName;
    private String genre;
    private String rating;
    private String url;
    private String trailer ;
    private String about ;
    private List<CastsModel> casts;
}
