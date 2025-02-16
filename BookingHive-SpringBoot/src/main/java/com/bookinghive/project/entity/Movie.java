package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String rating;
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String trailer ;

    @Column(nullable = false)
    private String about ;

    // Many Movies can be shown in many Cities (Many-to-Many relationship)
    @ManyToMany(mappedBy = "movies")
    private List<City> cities; // List of Cities where the Movie is shown

    // One Movie can have many Theatres (One-to-Many relationship)
//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
//    private List<Seat> seats;
    @ManyToMany(mappedBy = "movies")
    private List<Theatre> theatres;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Show> shows;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Casts> casts;
}
