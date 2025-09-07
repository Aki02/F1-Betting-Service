package org.example.services;

import org.example.domain.Bet;
import org.example.domain.BetStatus;
import org.example.domain.EventOutcome;
import org.example.domain.User;
import org.example.repositories.BetRepository;
import org.example.repositories.EventOutcomeRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BettingService {
    private final UserRepository userRepository;
    private final BetRepository betRepository;
    private final EventOutcomeRepository outcomeRepository;

    public BettingService(UserRepository userRepository, BetRepository betRepository, EventOutcomeRepository outcomeRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.outcomeRepository = outcomeRepository;
    }

    @Transactional
    public Bet placeBet(Long userId, String eventId, Integer driverId, String driverName, BigDecimal amount, int odds) {
        // Check if event already has outcome
        if (outcomeRepository.existsByEventId(eventId)) {
            throw new IllegalArgumentException("Betting closed: outcome already recorded for event " + eventId);
        }

        // Prevent duplicate bet from same user on same driver/event
        if (!betRepository.findByUserIdAndEventIdAndDriverId(userId, eventId, driverId).isEmpty()) {
            throw new IllegalArgumentException("Duplicate bet not allowed for same driver in this event");
        }

        User user = userRepository.findById(userId).orElseThrow();
        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        Bet bet = new Bet();
        bet.setUserId(userId);
        bet.setEventId(eventId);
        bet.setDriverId(driverId);
        bet.setDriverName(driverName);
        bet.setAmount(amount);
        bet.setOdds(odds);
        bet.setStatus(BetStatus.PENDING);
        bet.setPlacedAt(LocalDateTime.now());
        return betRepository.save(bet);
    }

    @Transactional
    public void processEventOutcome(String eventId, Integer winnerDriverId) {
        EventOutcome outcome = new EventOutcome(eventId, winnerDriverId);
        outcomeRepository.save(outcome);

        List<Bet> bets = betRepository.findByEventIdAndStatus(eventId, BetStatus.PENDING);
        for (Bet bet : bets) {
            if (bet.getDriverId().equals(winnerDriverId)) {
                bet.setStatus(BetStatus.WON);
                BigDecimal payout = bet.getAmount().multiply(BigDecimal.valueOf(bet.getOdds()));
                bet.setPayout(payout);
                User user = userRepository.findById(bet.getUserId()).orElseThrow();
                user.setBalance(user.getBalance().add(payout));
                userRepository.save(user);
            } else {
                bet.setStatus(BetStatus.LOST);
                bet.setPayout(BigDecimal.ZERO);
            }
            betRepository.save(bet);
        }
    }
}
