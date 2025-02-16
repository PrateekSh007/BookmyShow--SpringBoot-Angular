package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;

    @Column(nullable = false)
    private String thName;

    // Many Theatres can belong to one City
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // One Theatre can show many Movies
    @ManyToMany
    @JoinTable(
            name = "theatre_movie", // Join table name
            joinColumns = @JoinColumn(name = "theatre_id"), // Foreign key for Theatre
            inverseJoinColumns = @JoinColumn(name = "movie_id") // Foreign key for Movie
    )
    private List<Movie> movies; // List of Movies played in this Theatre
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Show> shows;
}
