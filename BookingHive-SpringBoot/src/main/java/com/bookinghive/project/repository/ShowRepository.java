package com.bookinghive.project.repository;

import com.bookinghive.project.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findByTheatre_TheatreIdAndMovie_MovieId(Long theatreId, Long movieId);
    List<Show> findByTheatre_TheatreId(Long theatreId);
    List<Show> findByTheatre_TheatreIdAndMovie_MovieName(Long theatreId, String movieName);
}
