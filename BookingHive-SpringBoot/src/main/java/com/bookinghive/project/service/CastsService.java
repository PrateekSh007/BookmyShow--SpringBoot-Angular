package com.bookinghive.project.service;

import com.bookinghive.project.model.AddCastsRequest;

import com.bookinghive.project.model.MovieModel;

public interface CastsService {
    MovieModel addCastsToMovie(AddCastsRequest request);
    MovieModel getCastsByMovieId(Long movieId);
}
