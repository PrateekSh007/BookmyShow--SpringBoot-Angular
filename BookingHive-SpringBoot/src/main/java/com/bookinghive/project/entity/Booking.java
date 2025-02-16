package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Auto-set timestamp on creation


    @Column(nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)  // Mapping the Booking to a Seat
    private Seat seat;
}
