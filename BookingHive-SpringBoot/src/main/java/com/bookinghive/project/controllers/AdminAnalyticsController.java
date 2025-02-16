package com.bookinghive.project.controllers;

import com.bookinghive.project.model.AdminAnalyticsResponse;
import com.bookinghive.project.model.MovieGrossSaleResponse;
import com.bookinghive.project.service.AdminAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService adminAnalyticsService;

    @GetMapping("/analytics")
    public ResponseEntity<AdminAnalyticsResponse> getMovieAnalytics(
            @RequestParam Long cityId,
            @RequestParam String movieName) {
        AdminAnalyticsResponse response = adminAnalyticsService.getMovieAnalytics(cityId, movieName);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/allanalytics")
    public ResponseEntity<List<MovieGrossSaleResponse>> getAllMoviesGrossSale() {
        List<MovieGrossSaleResponse> response = adminAnalyticsService.getAllMoviesGrossSale();
        return ResponseEntity.ok(response);
    }
}
