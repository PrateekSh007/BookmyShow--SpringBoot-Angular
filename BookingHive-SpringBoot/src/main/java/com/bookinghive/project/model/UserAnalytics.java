package com.bookinghive.project.model;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserAnalytics {

    private String name;
    private String email;
    private String number;
    // You can use your BookingResponse DTO, or any other booking DTO you have
    private List<BookingResponse> bookings;

}
