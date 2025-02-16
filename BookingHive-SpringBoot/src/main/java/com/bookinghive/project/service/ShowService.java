package com.bookinghive.project.service;

import com.bookinghive.project.entity.Show;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.model.ShowModel;

import java.util.List;

public interface ShowService {
    ShowModel addShow(ShowModel showModel);
    Show getShowByIdForSeat(long id);
    List<ShowModel> getShowsByTheatreAndMovie(Long theatreId, Long movieId);
    List<Show> findByTheatreId(Long id, List<Theatre> theatres);

}
