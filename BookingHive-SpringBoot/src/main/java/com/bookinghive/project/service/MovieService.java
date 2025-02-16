package com.bookinghive.project.service;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.model.MovieModel;

import java.util.List;

public interface MovieService {
    MovieModel saveMovie(MovieModel movieModel);
    List<MovieModel> getAllMovies();
    MovieModel getMovieById(Long movieId);
    void deleteMovie(Long movieId);
    Movie getMovieByIdForShow(Long movieId);
    Movie findMovieByName(String movieName);
    Movie findByMovieId(Long id);
}
