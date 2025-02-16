package com.bookinghive.project.controllers;

import com.bookinghive.project.model.ShowModel;
import com.bookinghive.project.service.ShowService;
import com.bookinghive.project.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    @Autowired
    private TheatreService theatreService;
    @Autowired
    private ShowService showService;
    @PostMapping()
    public ResponseEntity<?> addShow(@RequestBody ShowModel showModel) {
        try {
            ShowModel response = showService.addShow(showModel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the show: " + e.getMessage());
        }
    }

    @GetMapping("/{theatreId}")
    public ResponseEntity<List<ShowModel>> getShowByTheatreId(@PathVariable Long theatreId){
        List<ShowModel> showModels=theatreService.getShowsByTheatreId(theatreId);
        return ResponseEntity.status(HttpStatus.OK).body(showModels);

    }
    @GetMapping("/shows")
    public ResponseEntity<List<ShowModel>> getShowsByTheatreAndMovie(
            @RequestParam Long theatreId,
            @RequestParam Long movieId) {

        List<ShowModel> shows = showService.getShowsByTheatreAndMovie(theatreId, movieId);
        return ResponseEntity.ok(shows);
    }



}
