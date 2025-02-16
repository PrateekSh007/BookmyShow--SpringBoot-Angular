package com.bookinghive.project.service;

import com.bookinghive.project.model.AdminAnalyticsResponse;
import com.bookinghive.project.model.MovieGrossSaleResponse;

import java.util.List;

public interface AdminAnalyticsService {
    AdminAnalyticsResponse getMovieAnalytics(Long cityId, String movieName);
    List<MovieGrossSaleResponse> getAllMoviesGrossSale();
}
