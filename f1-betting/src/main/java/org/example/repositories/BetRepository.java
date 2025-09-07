package org.example.repositories;

import org.example.domain.Bet;
import org.example.domain.BetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByEventIdAndStatus(String eventId, BetStatus status);
    List<Bet> findByUserId(Long userId);
    // To prevent duplicates
    List<Bet> findByUserIdAndEventIdAndDriverId(Long userId, String eventId, Integer driverId);
}
