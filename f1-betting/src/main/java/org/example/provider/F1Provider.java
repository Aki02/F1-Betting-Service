package org.example.provider;

import org.example.dto.EventDTO;

import java.util.List;

public interface F1Provider {
    List<EventDTO> getSessions(String sessionType, Integer year, String country);
}
