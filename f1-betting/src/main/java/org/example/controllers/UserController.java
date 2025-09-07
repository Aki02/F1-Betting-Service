package org.example.controllers;

import org.example.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository){ this.userRepository = userRepository; }

    @GetMapping("/{id}/balance")
    public Map<String, Object> balance(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("userId", u.getId());
                    result.put("balance", u.getBalance());
                    return result;
                })
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("error", "User not found");
                    return error;
                });
    }
}
