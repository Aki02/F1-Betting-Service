package org.example.controllers;

import jakarta.validation.Valid;
import org.example.domain.Bet;
import org.example.dto.PlaceBetRequest;
import org.example.services.BettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bets")
public class BetsController {
    private final BettingService bettingService;
    public BetsController(BettingService bettingService) { this.bettingService = bettingService; }

    @PostMapping
    public ResponseEntity<?> placeBet(@Valid @RequestBody PlaceBetRequest request) {
        try {
            Bet bet = bettingService.placeBet(
                    request.getUserId(),
                    request.getEventId(),
                    request.getDriverId(),
                    request.getDriverName(),
                    request.getAmount(),
                    request.getOdds()
            );
            return ResponseEntity.ok(bet);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
