package org.example.util;

import org.example.domain.User;
import org.example.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository users;
    public DataInitializer(UserRepository users) { this.users = users; }

    @Override
    public void run(String... args) {
        if (users.count() == 0) {
            User u = new User();
            u.setBalance(BigDecimal.valueOf(100));
            users.save(u);
            System.out.println("Created sample user id=" + u.getId() + " with balance €100");
        }
    }
}
