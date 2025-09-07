package org.example.repositories;

import org.example.domain.EventOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOutcomeRepository extends JpaRepository<EventOutcome, Long> {
    // To check if the outcome already exists for event
    boolean existsByEventId(String eventId);
}
