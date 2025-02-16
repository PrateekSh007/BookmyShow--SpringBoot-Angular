package com.bookinghive.project.service;

import com.bookinghive.project.model.BookingModel;
import com.bookinghive.project.model.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse saveBooking(BookingModel bookingModel);
    List<BookingResponse> getAllBookingsByUserId(Long userId) throws Exception;
}
