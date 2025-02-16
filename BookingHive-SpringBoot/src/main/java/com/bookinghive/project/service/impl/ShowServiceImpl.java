package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.entity.Show;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.model.ShowModel;
import com.bookinghive.project.repository.ShowRepository;
import com.bookinghive.project.service.MovieService;
import com.bookinghive.project.service.ShowService;
import com.bookinghive.project.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private MovieService movieService;

    @Override
    public ShowModel addShow(ShowModel showModel) {
        try {
            // Fetch Theatre using TheatreService
            Theatre theatre = theatreService.getTheatreByIdForShow(showModel.getTheatreId());
            if (theatre == null) {
                throw new RuntimeException("Theatre not found with id: " + showModel.getTheatreId());
            }

            // Fetch Movie using MovieService
            Movie movie = movieService.getMovieByIdForShow(showModel.getMovieId());
            if (movie == null) {
                throw new RuntimeException("Movie not found with id: " + showModel.getMovieId());
            }

            // Create Show entity
            Show show = new Show();
            show.setPrice(showModel.getPrice());
            show.setStartTime(showModel.getStartTime());
            show.setTheatre(theatre);
            show.setMovie(movie);

            // Handle shows for the theatre
//            List<Show> shows = theatre.getShows();
//            if (shows == null) {
//                shows = new ArrayList<>();
//            }
//            shows.add(show);
//            theatre.setShows(shows);




            // Save Show and return as ShowModel
            Show savedShow = showRepository.save(show);

            ShowModel responseModel = new ShowModel();
            responseModel.setId(savedShow.getShowId());
            responseModel.setPrice(savedShow.getPrice());
            responseModel.setStartTime(savedShow.getStartTime());
            responseModel.setTheatreId(savedShow.getTheatre().getTheatreId());
            responseModel.setMovieId(savedShow.getMovie().getMovieId());

            return responseModel;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding the show: " + e.getMessage());
        }
    }
    public Show getShowByIdForSeat(long id){
        return showRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Show not found with ID: " + id));
    }
    @Override
    public List<ShowModel> getShowsByTheatreAndMovie(Long theatreId, Long movieId) {
        List<Show> shows = showRepository.findByTheatre_TheatreIdAndMovie_MovieId(theatreId, movieId);

        return shows.stream().map(show -> {
            ShowModel showModel = new ShowModel();
            showModel.setId(show.getShowId());
            showModel.setPrice(show.getPrice());
            showModel.setStartTime(show.getStartTime());
            showModel.setTheatreId(show.getTheatre().getTheatreId());
            showModel.setMovieId(show.getMovie().getMovieId());
            return showModel;
        }).collect(Collectors.toList());
    }
    public List<Show> findByTheatreId(Long id,List<Theatre> theatres){

        List<Show> shows = new ArrayList<>();
        for (Theatre theatre : theatres) {
            shows.addAll(showRepository.findByTheatre_TheatreId(theatre.getTheatreId()));
        }
        return shows;


    }


}
