package com.sebastian.booking_backend.controller;

import com.sebastian.booking_backend.model.Booking;
import com.sebastian.booking_backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody Booking booking, Authentication auth) {
        String email = auth != null ? auth.getName() : null;
        return bookingService.createBooking(booking, email);
    }

    @GetMapping("/my-bookings")
    public List<Booking> myBookings(Authentication auth) {
        return bookingService.getBookingsByUser(auth.getName());
    }

    @GetMapping("/admin/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PatchMapping("/admin/bookings/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return bookingService.updateStatus(id, body.get("status"));
    }

    @DeleteMapping("/admin/bookings/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
