package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Version
    private Integer version;

    @Column(nullable = false)
    private String seatNo;
    private boolean occupied;
    private boolean blocked;
    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
    @OneToOne(mappedBy = "seat")
    private Booking booking;
    private LocalDateTime blockedTime;
}
