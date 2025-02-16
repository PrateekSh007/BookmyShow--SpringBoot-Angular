package com.bookinghive.project.service;

import com.bookinghive.project.entity.Seat;
import com.bookinghive.project.model.SeatModel;

import java.util.List;

public interface SeatService {
    Long addSeat(SeatModel seatModel);
    List<SeatModel> getSeats(long theatreId, long showId);
    Seat getSeatById(Long id);
    void deleteSeatById(Long id);
    void markSeatAsOccupied(Long id);
    boolean isSeatBlockedOrOccupied(String seatNo, long theatreId, long showId);
    List<Seat> findSeatBocked();
}
