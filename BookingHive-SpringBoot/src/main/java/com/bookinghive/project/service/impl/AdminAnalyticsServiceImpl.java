package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.*;
import com.bookinghive.project.model.AdminAnalyticsResponse;
import com.bookinghive.project.model.MovieGrossSaleResponse;
import com.bookinghive.project.repository.ShowRepository;
//import com.bookinghive.project.repository.TheatreRepository;
import com.bookinghive.project.service.AdminAnalyticsService;
import com.bookinghive.project.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminAnalyticsServiceImpl implements AdminAnalyticsService {

//    @Autowired
//    private TheatreRepository theatreRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheatreService theatreService;

    @Override
    public AdminAnalyticsResponse getMovieAnalytics(Long cityId, String movieName) {

        List<Theatre> theatres = theatreService.getTheatreByCityId(cityId);


        List<Show> shows = new ArrayList<>();
        for (Theatre theatre : theatres) {
            shows.addAll(showRepository.findByTheatre_TheatreIdAndMovie_MovieName(theatre.getTheatreId(), movieName));
        }


        double grossCollection = 0.0;
        int seatsBooked = 0;

        for (Show show : shows) {

            grossCollection += show.getPrice() * show.getSeats().stream()
                    .filter(Seat::isOccupied) // Only count occupied seats
                    .count();


            seatsBooked += (int) show.getSeats().stream()
                    .filter(Seat::isOccupied)
                    .count();
        }


        AdminAnalyticsResponse response = new AdminAnalyticsResponse();
        response.setMovieName(movieName);
        response.setCityName(theatres.get(0).getCity().getCityName());
        response.setGrossCollection(grossCollection);
        response.setSeatsBooked(seatsBooked);

        return response;
    }
    @Override
    public List<MovieGrossSaleResponse> getAllMoviesGrossSale() {
        List<Show> shows = showRepository.findAll();
        Map<String, Double> movieGrossSales = new HashMap<>();


        for (Show show : shows) {
            String movieName = show.getMovie().getMovieName();
            double grossSale = show.getPrice() * show.getSeats().stream()
                    .filter(seat -> seat.isOccupied())
                    .count();

            movieGrossSales.put(movieName, movieGrossSales.getOrDefault(movieName, 0.0) + grossSale);
        }


        List<MovieGrossSaleResponse> responseList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : movieGrossSales.entrySet()) {
            MovieGrossSaleResponse response = new MovieGrossSaleResponse();
            response.setMovieName(entry.getKey());
            response.setGrossSale(entry.getValue());
            responseList.add(response);
        }

        return responseList;
    }
}