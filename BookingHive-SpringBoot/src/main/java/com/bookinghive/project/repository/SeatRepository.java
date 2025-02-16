package com.bookinghive.project.repository;

import com.bookinghive.project.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // Custom query to fetch seats by theatreId and showId
    List<Seat> findByTheatre_TheatreIdAndShow_ShowId(long theatreId, long showId);
    List<Seat> findAllByBlocked(boolean blocked);
}