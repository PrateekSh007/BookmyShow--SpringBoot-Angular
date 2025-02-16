package com.bookinghive.project.controllers;

import com.bookinghive.project.model.AddCastsRequest;
import com.bookinghive.project.model.MovieModel;
import com.bookinghive.project.service.CastsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casts")
public class CastsController {

    private final CastsService castsService;

    public CastsController(CastsService castsService) {
        this.castsService = castsService;
    }

    @PostMapping("")
    public ResponseEntity<MovieModel> addCastsToMovie(@RequestBody AddCastsRequest request) {
        MovieModel updatedMovie = castsService.addCastsToMovie(request);
        return ResponseEntity.ok(updatedMovie);
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieModel> getCastsByMovieId(@PathVariable Long movieId) {
        MovieModel movie = castsService.getCastsByMovieId(movieId);
        return ResponseEntity.ok(movie);
    }
}
