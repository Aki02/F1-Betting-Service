package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.PostOutcomeRequest;
import org.example.services.BettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final BettingService bettingService;
    public AdminController(BettingService bettingService) { this.bettingService = bettingService; }

    @PostMapping("/events/{eventId}/outcome")
    public ResponseEntity<?> postOutcome(@PathVariable String eventId, @Valid @RequestBody PostOutcomeRequest request) {
        try {
            bettingService.processEventOutcome(eventId, request.getWinnerDriverId());
            return ResponseEntity.ok("Outcome recorded successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
