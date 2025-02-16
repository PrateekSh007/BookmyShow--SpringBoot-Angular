package com.bookinghive.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAnalyticsResponse {
    private String movieName;
    private String cityName;
    private double grossCollection;
    private int seatsBooked;
}
