package org.example.controllers;

import org.example.dto.EventDTO;
import org.example.provider.F1Provider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {
    private final F1Provider provider;
    public EventsController(F1Provider provider) { this.provider = provider; }

    @GetMapping
    public List<EventDTO> listEvents(@RequestParam(required = false) String sessionType,
                                     @RequestParam(required = false) Integer year,
                                     @RequestParam(required = false) String country) {
        return provider.getSessions(sessionType, year, country);
    }
}
