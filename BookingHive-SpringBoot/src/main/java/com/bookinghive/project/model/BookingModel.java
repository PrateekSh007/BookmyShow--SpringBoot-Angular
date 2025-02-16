package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingModel {

    private Long id;


    private LocalDateTime timestamp ;


    private Integer status;

//
    private Long userId;
//
    private Long movieId;
//
    private Long theatreId;

    private Long seatId;


}
