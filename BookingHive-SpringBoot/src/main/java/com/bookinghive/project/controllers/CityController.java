package com.bookinghive.project.controllers;



import com.bookinghive.project.model.CityModel;
import com.bookinghive.project.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity<CityModel> saveCity(@RequestBody CityModel cityModel) {
        CityModel savedCity = cityService.saveCity(cityModel);
        return ResponseEntity.ok(savedCity);
    }

    @GetMapping
    public ResponseEntity<List<CityModel>> getAllCities() {
        List<CityModel> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }
}
