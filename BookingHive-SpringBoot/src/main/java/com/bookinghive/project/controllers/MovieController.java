package com.bookinghive.project.controllers;

import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @PostMapping
    public ResponseEntity<MovieModel> createMovie(@RequestBody MovieModel movieModel) {
        MovieModel savedMovie = movieService.saveMovie(movieModel);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        List<MovieModel> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Get a movie by ID
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable Long movieId) {
        MovieModel movie = movieService.getMovieById(movieId);

        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}