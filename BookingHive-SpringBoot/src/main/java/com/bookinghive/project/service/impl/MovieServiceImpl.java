package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.repository.MovieRepository;
import com.bookinghive.project.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieModel saveMovie(MovieModel movieModel) {
        Movie movie = new Movie();
        movie.setMovieName(movieModel.getMovieName());
        movie.setGenre(movieModel.getGenre());
        movie.setRating(movieModel.getRating());
        movie.setUrl(movieModel.getUrl());
        movie.setAbout(movieModel.getAbout());
        movie.setTrailer(movieModel.getTrailer());


        // Save the movie to the repository
        movie = movieRepository.save(movie);

        // Set the movieId in the MovieModel (mapping response)
        movieModel.setMovieId(movie.getMovieId());
        return movieModel;
    }

    @Override
    public List<MovieModel> getAllMovies() {
        List<MovieModel> movieModels = new ArrayList<>();
        List<Movie> movies = movieRepository.findAll();

        for (Movie movie : movies) {
            MovieModel movieModel = new MovieModel();
            movieModel.setMovieId(movie.getMovieId());
            movieModel.setMovieName(movie.getMovieName());
            movieModel.setGenre(movie.getGenre());
            movieModel.setRating(movie.getRating());
            movieModel.setAbout(movie.getAbout());
            movieModel.setTrailer(movie.getTrailer());

            // Optionally, you could map additional relationships here if needed.
            // For example, if movies are associated with theatres, you can add them.

            movieModels.add(movieModel);
        }

        return movieModels;
    }

    @Override
    public MovieModel getMovieById(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            MovieModel movieModel = new MovieModel();
            movieModel.setMovieId(movie.getMovieId());
            movieModel.setMovieName(movie.getMovieName());
            movieModel.setGenre(movie.getGenre());
            movieModel.setRating(movie.getRating());
            movieModel.setUrl(movie.getUrl());
            movieModel.setAbout(movie.getAbout());
            movieModel.setTrailer(movie.getTrailer());

            // Additional logic can be applied here if you want to map the movie's relation to other entities.

            return movieModel;
        } else {
            return null; // Or throw an exception based on the business logic.
        }
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
    @Override
    public Movie getMovieByIdForShow(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
    }
    public Movie findMovieByName(String movieName){
        Optional<Movie> movie=movieRepository.findByMovieName(movieName);
        if (movie.isEmpty()) {
            throw new RuntimeException("Movie not found with name: " + movieName);
        }
        return movie.get();
    }
    public Movie findByMovieId(Long id){
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

    }

}
