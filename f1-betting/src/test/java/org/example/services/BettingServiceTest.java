package org.example.services;

import org.example.domain.Bet;
import org.example.domain.BetStatus;
import org.example.domain.User;
import org.example.repositories.BetRepository;
import org.example.repositories.EventOutcomeRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BettingServiceTest {

    private UserRepository userRepo;
    private BetRepository betRepo;
    private EventOutcomeRepository outcomeRepo;
    private BettingService service;

    @BeforeEach
    void setup() {
        userRepo = mock(UserRepository.class);
        betRepo = mock(BetRepository.class);
        outcomeRepo = mock(EventOutcomeRepository.class);
        service = new BettingService(userRepo, betRepo, outcomeRepo);
    }

    @Test
    void testPlaceBetSuccess() {
        User u = new User(BigDecimal.valueOf(100));
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(betRepo.save(any(Bet.class))).thenAnswer(i -> {
            Bet b = (Bet) i.getArguments()[0];
            b.setId(1L);
            return b;
        });

        Bet bet = service.placeBet(1L, "ev1", 44, "Hamilton", BigDecimal.valueOf(10), 3);
        assertEquals(BigDecimal.valueOf(90), u.getBalance());
        assertEquals(BetStatus.PENDING, bet.getStatus());
    }

    @Test
    void testPlaceBetInsufficientBalance() {
        User u = new User(BigDecimal.valueOf(5));
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        assertThrows(IllegalArgumentException.class, () -> {
            service.placeBet(1L, "ev1", 44, "Hamilton", BigDecimal.valueOf(10), 2);
        });
    }

    @Test
    void testProcessOutcomeWinAndLose() {
        Bet winBet = new Bet();
        winBet.setUserId(1L);
        winBet.setEventId("ev1");
        winBet.setDriverId(44);
        winBet.setAmount(BigDecimal.valueOf(10));
        winBet.setOdds(3);
        winBet.setStatus(BetStatus.PENDING);

        Bet loseBet = new Bet();
        loseBet.setUserId(1L);
        loseBet.setEventId("ev1");
        loseBet.setDriverId(55);
        loseBet.setAmount(BigDecimal.valueOf(5));
        loseBet.setOdds(2);
        loseBet.setStatus(BetStatus.PENDING);

        User u = new User(BigDecimal.valueOf(50));
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(betRepo.findByEventIdAndStatus("ev1", BetStatus.PENDING))
                .thenReturn(List.of(winBet, loseBet));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(betRepo.save(any(Bet.class))).thenAnswer(i -> i.getArguments()[0]);

        service.processEventOutcome("ev1", 44);

        assertEquals(BetStatus.WON, winBet.getStatus());
        assertEquals(BigDecimal.valueOf(30), winBet.getPayout());
        assertEquals(BetStatus.LOST, loseBet.getStatus());
        assertEquals(BigDecimal.ZERO, loseBet.getPayout());
        assertEquals(BigDecimal.valueOf(80), u.getBalance());
    }

    @Test
    void testDuplicateBetRejected() {
        User u = new User(BigDecimal.valueOf(100));
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(betRepo.findByUserIdAndEventIdAndDriverId(1L, "ev1", 44))
                .thenReturn(List.of(new Bet())); // simulate existing bet

        assertThrows(IllegalArgumentException.class, () -> {
            service.placeBet(1L, "ev1", 44, "Hamilton", BigDecimal.valueOf(10), 3);
        });
    }

    @Test
    void testBetRejectedAfterOutcome() {
        User u = new User(BigDecimal.valueOf(100));
        when(userRepo.findById(1L)).thenReturn(Optional.of(u));
        when(outcomeRepo.existsByEventId("ev1")).thenReturn(true); // outcome already exists

        assertThrows(IllegalArgumentException.class, () -> {
            service.placeBet(1L, "ev1", 44, "Hamilton", BigDecimal.valueOf(10), 3);
        });
    }
}
