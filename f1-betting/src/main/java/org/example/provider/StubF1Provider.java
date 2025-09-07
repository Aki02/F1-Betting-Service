package org.example.provider;

import org.example.dto.DriverMarketDTO;
import org.example.dto.EventDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StubF1Provider implements F1Provider {

    @Override
    public List<EventDTO> getSessions(String sessionType, Integer year, String country) {
        EventDTO event = new EventDTO();
        event.setEventId("sample-2024-monaco-race");
        event.setMeetingName("Monaco GP");
        event.setSessionType(sessionType != null ? sessionType : "race");
        event.setYear(year != null ? year : 2024);
        event.setCountry(country != null ? country : "Monaco");
        event.setStartTime(LocalDateTime.now().plusDays(7));

        DriverMarketDTO d1 = new DriverMarketDTO();
        d1.setDriverId(44);
        d1.setDriverFullName("Lewis Hamilton");
        d1.setOdds(2);

        DriverMarketDTO d2 = new DriverMarketDTO();
        d2.setDriverId(33);
        d2.setDriverFullName("Max Verstappen");
        d2.setOdds(3);

        event.setDriverMarket(List.of(d1, d2));
        return List.of(event);
    }
}
