package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TheatreModel {
    private Long theatreId;
    private String thName;
    private Long cityId;
    private List<Long> movieIds;
}
