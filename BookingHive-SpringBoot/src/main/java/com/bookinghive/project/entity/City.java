package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cityName;


    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Theatre> theatres;


    @ManyToMany
    @JoinTable(
            name = "city_movie", // Join table name
            joinColumns = @JoinColumn(name = "city_id"), // Foreign key for City
            inverseJoinColumns = @JoinColumn(name = "movie_id") // Foreign key for Movie
    )
    private List<Movie> movies; // Movies shown in the City
}
