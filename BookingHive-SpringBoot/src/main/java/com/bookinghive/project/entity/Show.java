package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movie_show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long showId;

    @Column(nullable = false)
    private double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startTime;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @OneToMany(mappedBy = "show")
    private List<Seat> seats;

}