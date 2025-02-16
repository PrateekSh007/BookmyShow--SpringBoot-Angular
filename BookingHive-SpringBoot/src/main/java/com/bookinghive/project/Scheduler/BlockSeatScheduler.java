package com.bookinghive.project.Scheduler;

import com.bookinghive.project.entity.Seat;
import com.bookinghive.project.repository.SeatRepository;
import com.bookinghive.project.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@EnableScheduling
@Component("BlockSeatScheduler")
public class BlockSeatScheduler {
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    SeatService seatService;
    @Scheduled(fixedRate = 60000)
    public void unblockExpiredSeats() {
        List<Seat> blockedSeats = seatService.findSeatBocked();
        LocalDateTime now = LocalDateTime.now();
        for (Seat seat : blockedSeats) {
            if (seat.getBlockedTime() != null && ChronoUnit.MINUTES.between(seat.getBlockedTime(), now) >= 1&&!seat.isOccupied()) {
                seatService.deleteSeatById(seat.getSeatId());
                log.info("unblocked due to timeout");
            }
        }
    }
}
