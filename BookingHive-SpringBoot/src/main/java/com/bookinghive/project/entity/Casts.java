package com.bookinghive.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "casts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Casts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}
