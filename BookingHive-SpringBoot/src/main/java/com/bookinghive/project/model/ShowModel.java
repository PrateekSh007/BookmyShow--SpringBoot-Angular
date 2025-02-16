package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShowModel {
    private long id;
    private double price;
    private Date startTime;
    private long theatreId;
    private long movieId;

}
