package com.sebastian.booking_backend.config;

import com.sebastian.booking_backend.model.User;
import com.sebastian.booking_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.bootstrap-admin", name = "enabled", havingValue = "true")
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap-admin.email}")
    private String adminEmail;

    @Value("${app.bootstrap-admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            throw new IllegalStateException(
                    "BOOTSTRAP_ADMIN_EMAIL and BOOTSTRAP_ADMIN_PASSWORD are required when admin bootstrapping is enabled");
        }

        // Bootstrap only when explicitly enabled; do not ship a default privileged account.
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin bootstrap account created.");
        } else {
            // Admin exists — make sure role is set to ADMIN
            userRepository.findByEmail(adminEmail).ifPresent(admin -> {
                if (!"ADMIN".equals(admin.getRole())) {
                    admin.setRole("ADMIN");
                    userRepository.save(admin);
                    System.out.println("Admin bootstrap role updated.");
                }
            });
        }
    }
}
