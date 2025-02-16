package com.bookinghive.project.service.impl;

import com.bookinghive.project.entity.*;
import com.bookinghive.project.model.BookingModel;
import com.bookinghive.project.model.BookingResponse;
import com.bookinghive.project.repository.BookingRepository;
import com.bookinghive.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    TheatreService theatreService;
    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;
    @Autowired
    SeatService seatService;
    public BookingResponse saveBooking(BookingModel bookingModel) {
        Booking booking=new Booking();
        Theatre theatre=theatreService.getTheatreByIdForShow(bookingModel.getTheatreId());
        User user=userService.getUserById(bookingModel.getUserId());
        Movie movie=movieService.getMovieByIdForShow(bookingModel.getMovieId());
        booking.setStatus(bookingModel.getStatus());
        booking.setUser(user);
        booking.setMovie(movie);
        booking.setTheatre(theatre);
        Seat seat=seatService.getSeatById(bookingModel.getSeatId());
        if(seat!=null) {
            seat.setOccupied(true);
            seat.setBlocked(true);
            booking.setSeat(seat);
        }
        else{
            return null;
        }



        bookingRepository.save(booking);
        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setStatus(booking.getStatus());
        bookingResponse.setTimestamp(booking.getTimestamp());
        bookingResponse.setUserId(booking.getUser().getId());
        bookingResponse.setMovieName(booking.getMovie().getMovieName());
        bookingResponse.setTheatreName(booking.getTheatre().getThName());
        bookingResponse.setSeatNo(booking.getSeat().getSeatNo());
        bookingResponse.setSeatId(bookingResponse.getSeatId());
        return bookingResponse;




    }

    @Override
    public List<BookingResponse> getAllBookingsByUserId(Long userId) throws Exception {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        // If no bookings found, throw an exception
        if (bookings.isEmpty()) {
            throw new Exception("No bookings found for the user with ID: " + userId);
        }
        List<BookingResponse> bookingResponses=new ArrayList<>();
        for(Booking booking:bookings){
            BookingResponse bookingResponse=new BookingResponse();
            bookingResponse.setTheatreName(booking.getTheatre().getThName());
            bookingResponse.setStatus(booking.getStatus());
            bookingResponse.setMovieName(booking.getMovie().getMovieName());
            bookingResponse.setTimestamp(booking.getTimestamp());
            bookingResponse.setUserId(booking.getUser().getId());
            bookingResponse.setSeatNo(booking.getSeat().getSeatNo());
            bookingResponses.add(bookingResponse);

        }
        return bookingResponses;
    }





}
