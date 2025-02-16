package com.bookinghive.project.model;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookingResponse {




    private LocalDateTime timestamp ;


    private Integer status;


    private Long userId;

    private String movieName;

    private String theatreName;
    private String seatNo;
    private Long seatId;
}
