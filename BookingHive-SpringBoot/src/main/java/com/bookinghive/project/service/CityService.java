package com.bookinghive.project.service;

import com.bookinghive.project.entity.City;
import com.bookinghive.project.model.CityModel;

import java.util.List;

public interface CityService {
    CityModel saveCity(CityModel cityModel);
    List<CityModel> getAllCities();
    City getCityById(Long cityId);
}
