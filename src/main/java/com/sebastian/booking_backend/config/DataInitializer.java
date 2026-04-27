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
        // Create admin user if not present
        if (!userRepository.existsByEmail("admin@booking.at")) {
            User admin = new User();
            admin.setEmail("admin@booking.at");
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin created: admin@booking.at / admin123");
        } else {
            // Admin exists — make sure role is set to ADMIN
            userRepository.findByEmail("admin@booking.at").ifPresent(admin -> {
                if (!"ADMIN".equals(admin.getRole())) {
                    admin.setRole("ADMIN");
                    userRepository.save(admin);
                    System.out.println("✅ Admin role fixed!");
                } else {
                    System.out.println("✅ Admin already exists: admin@booking.at / admin123");
                }
            });
        }
    }
}
