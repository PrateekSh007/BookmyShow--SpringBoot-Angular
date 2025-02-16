package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.City;
import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.entity.Show;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.model.ShowModel;
import com.bookinghive.project.model.TheatreModel;
import com.bookinghive.project.repository.ShowRepository;
import com.bookinghive.project.repository.TheatreRepository;
import com.bookinghive.project.service.CityService;
import com.bookinghive.project.service.ShowService;
import com.bookinghive.project.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TheatreServiceImpl implements TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CityService cityService;
    @Autowired
    private ShowService showService;

    @Override
    public List<MovieModel> getMoviesByCityId(Long cityId) {

        List<Theatre> theatres = theatreRepository.findByCity_Id(cityId);


        List<Show> shows = showService.findByTheatreId(cityId,theatres);


        Set<Movie> uniqueMovies = new HashSet<>();
        for (Show show : shows) {
            uniqueMovies.add(show.getMovie());
        }
        List<MovieModel> movieModels = uniqueMovies.stream()
                .map(movie -> {
                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovieId(movie.getMovieId());
                    movieModel.setMovieName(movie.getMovieName());
                    movieModel.setGenre(movie.getGenre());
                    movieModel.setRating(movie.getRating());
                    movieModel.setUrl(movie.getUrl());
                    movieModel.setAbout(movie.getAbout());
                    movieModel.setTrailer(movie.getTrailer());
                    return movieModel;
                })
                .collect(Collectors.toList());

        return movieModels;
    }

    @Override
    public TheatreModel saveTheatre(TheatreModel theatreModel) {

        City city = cityService.getCityById(theatreModel.getCityId());


        Theatre theatre = new Theatre();
        theatre.setThName(theatreModel.getThName());
        theatre.setCity(city);


        theatre = theatreRepository.save(theatre);


        theatreModel.setTheatreId(theatre.getTheatreId());
        return theatreModel;
    }

    @Override
    public List<TheatreModel> getAllTheatres() {
        List<TheatreModel> theatreModels = new ArrayList<>();
        List<Theatre> theatres = theatreRepository.findAll();

        for (Theatre theatre : theatres) {
            TheatreModel theatreModel = new TheatreModel();
            theatreModel.setTheatreId(theatre.getTheatreId());
            theatreModel.setThName(theatre.getThName());
            theatreModel.setCityId(theatre.getCity().getId());
            theatreModels.add(theatreModel);
        }
        return theatreModels;
    }

    @Override
    public TheatreModel getTheatreById(Long theatreId) {
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);

        if (theatreOptional.isPresent()) {
            Theatre theatre = theatreOptional.get();
            TheatreModel theatreModel = new TheatreModel();
            theatreModel.setTheatreId(theatre.getTheatreId());
            theatreModel.setThName(theatre.getThName());
            theatreModel.setCityId(theatre.getCity().getId());
            return theatreModel;
        } else {
            return null;
        }
    }

    @Override
    public TheatreModel updateTheatre(Long theatreId, TheatreModel theatreModel) {
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);

        if (theatreOptional.isPresent()) {
            Theatre theatre = theatreOptional.get();
            theatre.setThName(theatreModel.getThName());


            City city = cityService.getCityById(theatreModel.getCityId());
            theatre.setCity(city);


            theatre = theatreRepository.save(theatre);


            theatreModel.setTheatreId(theatre.getTheatreId());
            return theatreModel;
        } else {
            return null;
        }
    }

    @Override
    public void deleteTheatre(Long theatreId) {
        theatreRepository.deleteById(theatreId);
    }
    @Override
    public Theatre getTheatreByIdForShow(Long theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with ID: " + theatreId));
    }

    @Override
    public List<ShowModel> getShowsByTheatreId(Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with ID: " + theatreId));

        List<Show> shows = theatre.getShows();
        List<ShowModel> showModels = new ArrayList<>();

        for (Show show : shows) {
            ShowModel showModel = new ShowModel();
            showModel.setId(show.getShowId());
            showModel.setPrice(show.getPrice());
            showModel.setStartTime(show.getStartTime());
            showModel.setTheatreId(theatre.getTheatreId());
            showModel.setMovieId(show.getMovie().getMovieId());
            showModels.add(showModel);
        }

        return showModels;
    }
    @Override
    public List<TheatreModel> getTheatresByCityAndMovie(Long cityId, Long movieId) {
        List<Theatre> theatres = theatreRepository.findByCity_Id(cityId);

        List<TheatreModel> theatreModels = new ArrayList<>();
        for (Theatre theatre : theatres) {
            boolean hasMovie = theatre.getShows().stream()
                    .anyMatch(show -> show.getMovie().getMovieId().equals(movieId));
            if (hasMovie) {
                TheatreModel theatreModel = new TheatreModel();
                theatreModel.setTheatreId(theatre.getTheatreId());
                theatreModel.setThName(theatre.getThName());
                theatreModel.setCityId(theatre.getCity().getId());
                theatreModels.add(theatreModel);
            }
        }

        return theatreModels;
    }

    public List<Theatre> getTheatreByCityId(Long cityId){
       return theatreRepository.findByCity_Id(cityId);
    }


}