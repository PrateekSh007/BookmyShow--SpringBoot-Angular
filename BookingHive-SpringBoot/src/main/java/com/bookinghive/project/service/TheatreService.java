package com.bookinghive.project.service;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.model.ShowModel;
import com.bookinghive.project.model.TheatreModel;

import java.util.List;

public interface TheatreService {
    TheatreModel saveTheatre(TheatreModel theatreModel);
    List<TheatreModel> getAllTheatres();
    TheatreModel getTheatreById(Long theatreId);
    TheatreModel updateTheatre(Long theatreId, TheatreModel theatreModel);
    void deleteTheatre(Long theatreId);
    Theatre getTheatreByIdForShow(Long theatreId);
    List<ShowModel> getShowsByTheatreId(Long theatreId);
    List<TheatreModel> getTheatresByCityAndMovie(Long cityId, Long movieId);

    List<MovieModel> getMoviesByCityId(Long cityId);
    List<Theatre> getTheatreByCityId(Long cityId);
}
