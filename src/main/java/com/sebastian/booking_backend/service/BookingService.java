package com.sebastian.booking_backend.service;

import com.sebastian.booking_backend.model.Booking;
import com.sebastian.booking_backend.model.User;
import com.sebastian.booking_backend.repository.BookingRepository;
import com.sebastian.booking_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Booking createBooking(Booking booking, String email) {
        if (email != null) {
            userRepository.findByEmail(email).ifPresent(booking::setUser);
        }
        return bookingRepository.save(booking);
    }

    public Booking updateStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
