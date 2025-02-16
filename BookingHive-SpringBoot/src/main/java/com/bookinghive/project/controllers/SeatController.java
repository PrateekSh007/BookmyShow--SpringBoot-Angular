package com.bookinghive.project.controllers;

import com.bookinghive.project.Utility.JwtUtil;
import com.bookinghive.project.entity.Seat;
import com.bookinghive.project.model.SeatModel;
import com.bookinghive.project.service.SeatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    JwtUtil jwtUtil;
    // POST method to add a seat
    @PostMapping
    public ResponseEntity<?> addSeat(@RequestBody SeatModel seatModel, HttpServletRequest request) {
        try {
            String token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null) {
                return ResponseEntity.status(401).body("Unauthorized: Missing Token");
            }

            String email = jwtUtil.extractEmail(token);
            Long userId = jwtUtil.extractUserId(token);

            if (jwtUtil.validateToken(token, email)) {
                if (seatService.isSeatBlockedOrOccupied(seatModel.getSeatNo(), seatModel.getTheatreId(), seatModel.getShowId())){
                    return ResponseEntity.status(409).build();

                }
                Long id = seatService.addSeat(seatModel);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(401).body("Unauthorized: Invalid Token");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Bad Request: " + e.getMessage());
        }
    }


    // GET method to fetch seats based on theatreId and showId
    @GetMapping("{theatreId}/{showId}")
    public ResponseEntity<List<?>> getSeats(@PathVariable long theatreId, @PathVariable long showId) {
        List<SeatModel> seats = seatService.getSeats(theatreId, showId);
        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 No Content if no seats found
        }
        return ResponseEntity.ok(seats);

    }
    @PutMapping("/{id}/block")
    public ResponseEntity<String> blockSeat(@PathVariable Long id) {
        try {
            Seat seat = seatService.getSeatById(id);

            if (seat.isBlocked()) {
                return new ResponseEntity<>("Sorry, the seat is already blocked. Please try another seat.", HttpStatus.CONFLICT);
            }

            seat.setBlocked(true);
            seatService.addSeat(convertToModel(seat));
            return new ResponseEntity<>("Seat blocked successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error in blocking seat.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSeat(@PathVariable Long id) {
        seatService.deleteSeatById(id);
    }
    private SeatModel convertToModel(Seat seat) {
        SeatModel seatModel = new SeatModel();
        seatModel.setSeatNo(seat.getSeatNo());
        seatModel.setOccupied(seat.isOccupied());
        seatModel.setBlocked(seat.isBlocked());
        seatModel.setTheatreId(seat.getTheatre().getTheatreId());
        seatModel.setShowId(seat.getShow().getShowId());
        return seatModel;
    }
    @PutMapping("/{id}/occupy")
    public ResponseEntity<String> markSeatAsOccupied(@PathVariable Long id) {
        try {
            seatService.markSeatAsOccupied(id);
            return new ResponseEntity<>("Seat marked as occupied", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error in booking seat", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
