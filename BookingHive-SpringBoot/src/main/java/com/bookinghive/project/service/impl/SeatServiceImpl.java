package com.bookinghive.project.service.impl;


import com.bookinghive.project.entity.Seat;
import com.bookinghive.project.entity.Theatre;
import com.bookinghive.project.entity.Show;
import com.bookinghive.project.model.SeatModel;
import com.bookinghive.project.repository.SeatRepository;
import com.bookinghive.project.repository.TheatreRepository;
import com.bookinghive.project.repository.ShowRepository;
import com.bookinghive.project.service.SeatService;
import com.bookinghive.project.service.ShowService;
import com.bookinghive.project.service.TheatreService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;



    @Autowired
    ShowService showService;
    @Autowired
    TheatreService theatreService;

    @Override
    public Long addSeat(SeatModel seatModel) {
        // Check if the seat is already blocked or occupied
        if (isSeatBlockedOrOccupied(seatModel.getSeatNo(), seatModel.getTheatreId(), seatModel.getShowId())) {
            throw new RuntimeException("Seat is already blocked or occupied.");
        }

        // Fetch Theatre and Show entities using the given IDs
        Theatre theatre = theatreService.getTheatreByIdForShow(seatModel.getTheatreId());
        Show show = showService.getShowByIdForSeat(seatModel.getShowId());

        // Create a new Seat entity and set its fields
        Seat seat = new Seat();
        seat.setSeatNo(seatModel.getSeatNo());
        seat.setOccupied(seatModel.isOccupied());
        seat.setBlocked(seatModel.isBlocked());
        //a


        seat.setBlockedTime(LocalDateTime.now());




        seat.setTheatre(theatre);
        seat.setShow(show);
        seat = seatRepository.save(seat);
        return seat.getSeatId();
    }

    @Override
    public List<SeatModel> getSeats(long theatreId, long showId) {
        // Fetch the seats based on theatreId and showId
        List<Seat> seats= seatRepository.findByTheatre_TheatreIdAndShow_ShowId(theatreId, showId);
        List<SeatModel> seatModels=new ArrayList<>();
        for (Seat seat : seats) {
            SeatModel seatModel = new SeatModel();
            seatModel.setSeatNo(seat.getSeatNo());
            seatModel.setOccupied(seat.isOccupied());
            seatModel.setBlocked(seat.isBlocked());
            seatModel.setTheatreId(theatreId);
            seatModel.setShowId(showId);

            seatModels.add(seatModel);
        }

        return seatModels;


    }
    @Override
    public Seat getSeatById(Long id){
        Optional<Seat> seat= seatRepository.findById(id);
        return seat.orElse(null);
    }
    @Override
    @Transactional
    public void markSeatAsOccupied(Long id) {
        Seat seat = getSeatById(id);
        seat.setOccupied(true);

        seatRepository.save(seat);
        log.info("Marked seat with ID {} as occupied", id);
    }

    @Override
    @Transactional
    public void deleteSeatById(Long id) {
        Seat seat =getSeatById(id);
        if(seat!=null&&seat.isOccupied()){
            return;
        }
        else {
            if(seat!=null){

            seatRepository.deleteById(id);
            log.info("Deleted seat with ID {}", id);
                }
        }
    }
    @Override
    public boolean isSeatBlockedOrOccupied(String seatNo, long theatreId, long showId) {
        List<Seat> seats = seatRepository.findByTheatre_TheatreIdAndShow_ShowId(theatreId, showId);
        for (Seat seat : seats) {
            if (seat.getSeatNo().equals(seatNo) && (seat.isBlocked() || seat.isOccupied())) {
                return true;
            }
        }
        return false;
    }

    public List<Seat> findSeatBocked(){
        return seatRepository.findAllByBlocked(true);
    }
}