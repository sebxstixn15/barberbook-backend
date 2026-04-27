package com.sebastian.booking_backend.config;

import com.sebastian.booking_backend.model.User;
import com.sebastian.booking_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Admin erstellen falls nicht vorhanden
        if (!userRepository.existsByEmail("admin@booking.at")) {
            User admin = new User();
            admin.setEmail("admin@booking.at");
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin erstellt: admin@booking.at / admin123");
        } else {
            // Admin existiert schon — stelle sicher dass role ADMIN ist
            userRepository.findByEmail("admin@booking.at").ifPresent(admin -> {
                if (!"ADMIN".equals(admin.getRole())) {
                    admin.setRole("ADMIN");
                    userRepository.save(admin);
                    System.out.println("✅ Admin-Rolle korrigiert!");
                } else {
                    System.out.println("✅ Admin bereits vorhanden: admin@booking.at / admin123");
                }
            });
        }
    }
}
