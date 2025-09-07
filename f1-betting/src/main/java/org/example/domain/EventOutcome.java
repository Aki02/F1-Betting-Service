package org.example.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "event_outcomes",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"eventId"}
        )
)
public class EventOutcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventId;
    private Integer winnerDriverId;
    private LocalDateTime recordedAt;

    public EventOutcome() {}
    public EventOutcome(String eventId, Integer winnerDriverId) {
        this.eventId = eventId;
        this.winnerDriverId = winnerDriverId;
        this.recordedAt = LocalDateTime.now();
    }
}
