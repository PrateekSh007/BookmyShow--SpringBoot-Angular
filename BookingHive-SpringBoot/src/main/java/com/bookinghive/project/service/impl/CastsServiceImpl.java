package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.Casts;
import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.model.AddCastsRequest;
import com.bookinghive.project.model.CastsModel;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.repository.CastsRepository;
import com.bookinghive.project.repository.MovieRepository;
import com.bookinghive.project.service.CastsService;
import com.bookinghive.project.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CastsServiceImpl implements CastsService {

    @Autowired
   CastsRepository  castsRepository;
    @Autowired
   MovieService movieService;

    @Override
    public MovieModel addCastsToMovie(AddCastsRequest request) {



        Movie movie = movieService.findMovieByName(request.getMovieName());


        List<Casts> castList = request.getCasts().stream().map(dto -> {
            Casts cast = new Casts();
            cast.setName(dto.getName());
            cast.setUrl(dto.getUrl());
            cast.setMovie(movie);
            return cast;
        }).collect(Collectors.toList());

        castsRepository.saveAll(castList);


        movie.getCasts().addAll(castList);


        return new MovieModel(
                movie.getMovieId(),
                movie.getMovieName(),
                movie.getGenre(),
                movie.getRating(),
                movie.getUrl(),
                movie.getTrailer(),
                movie.getAbout(),
                movie.getCasts().stream()
                        .map(cast -> new CastsModel(cast.getName(), cast.getUrl()))
                        .collect(Collectors.toList())
        );
    }
    @Override
    public MovieModel getCastsByMovieId(Long movieId) {
        Movie movie=movieService.findByMovieId(movieId);

        return new MovieModel(
                movie.getMovieId(),
                movie.getMovieName(),
                movie.getGenre(),
                movie.getRating(),
                movie.getUrl(),
                movie.getTrailer(),
                movie.getAbout(),
                movie.getCasts().stream()
                        .map(cast -> new CastsModel(cast.getName(), cast.getUrl()))
                        .collect(Collectors.toList())
        );
    }
}
