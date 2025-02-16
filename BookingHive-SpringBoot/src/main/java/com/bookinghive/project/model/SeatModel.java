package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatModel {
    private String seatNo;
    private boolean occupied;
    private boolean blocked;
    private long theatreId;
    private long showId;


}
