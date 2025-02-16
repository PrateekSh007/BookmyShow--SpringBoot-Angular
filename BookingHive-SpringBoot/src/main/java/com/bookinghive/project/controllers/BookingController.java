package com.bookinghive.project.controllers;

import com.bookinghive.project.Utility.JwtUtil;
import com.bookinghive.project.model.BookingModel;
import com.bookinghive.project.model.BookingResponse;
import com.bookinghive.project.service.BookingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private JwtUtil jwtUtil;
    // Save Booking
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingModel bookingModel, HttpServletRequest request) {
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

                bookingModel.setUserId(userId);

                BookingResponse booking = bookingService.saveBooking(bookingModel);
                return ResponseEntity.ok(booking);
            } else {
                return ResponseEntity.status(401).body("Unauthorized: Invalid Token");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Bad Request: " + e.getMessage());
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(bookingService.getAllBookingsByUserId(userId));
    }

}
