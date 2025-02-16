package com.bookinghive.project.controllers;

import com.bookinghive.project.entity.Movie;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.model.TheatreModel;
import com.bookinghive.project.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/movies/{cityId}")
    public ResponseEntity<List<MovieModel>> getMoviesByCityId(@PathVariable Long cityId) {
        // Fetch movies by cityId
        List<MovieModel> movies = theatreService.getMoviesByCityId(cityId);





        return ResponseEntity.ok(movies);
    }


    @PostMapping
    public ResponseEntity<TheatreModel> createTheatre(@RequestBody TheatreModel theatreModel) {
        TheatreModel savedTheatre = theatreService.saveTheatre(theatreModel);
        return new ResponseEntity<>(savedTheatre, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<TheatreModel>> getAllTheatres() {
        List<TheatreModel> theatres = theatreService.getAllTheatres();
        return new ResponseEntity<>(theatres, HttpStatus.OK);
    }


    @GetMapping("/{theatreId}")
    public ResponseEntity<TheatreModel> getTheatreById(@PathVariable Long theatreId) {
        TheatreModel theatre = theatreService.getTheatreById(theatreId);
        if (theatre != null) {
            return new ResponseEntity<>(theatre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{theatreId}")
    public ResponseEntity<TheatreModel> updateTheatre(
            @PathVariable Long theatreId,
            @RequestBody TheatreModel theatreModel) {
        TheatreModel updatedTheatre = theatreService.updateTheatre(theatreId, theatreModel);
        if (updatedTheatre != null) {
            return new ResponseEntity<>(updatedTheatre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{theatreId}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long theatreId) {
        theatreService.deleteTheatre(theatreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/theatres")
    public ResponseEntity<List<TheatreModel>> getTheatresByCityAndMovie(
            @RequestParam Long cityId,
            @RequestParam Long movieId) {

        List<TheatreModel> theatres = theatreService.getTheatresByCityAndMovie(cityId, movieId);
        return ResponseEntity.ok(theatres);
    }
}

