package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.PlaceBetRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BettingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testPlaceBetEndpoint() throws Exception {
        PlaceBetRequest req = new PlaceBetRequest();
        req.setUserId(1L);
        req.setEventId("sample-2024-monaco-race");
        req.setDriverId(44);
        req.setDriverName("Lewis Hamilton");
        req.setAmount(BigDecimal.valueOf(10));
        req.setOdds(3);

        mockMvc.perform(
                        post("/api/bets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(req))
                )
                .andExpect(status().isOk());
    }

    @Test
    void testDuplicateBetEndpointRejected() throws Exception {
        PlaceBetRequest req = new PlaceBetRequest();
        req.setUserId(1L);
        req.setEventId("sample-2025-monaco-race");
        req.setDriverId(44);
        req.setDriverName("Lewis Hamilton");
        req.setAmount(BigDecimal.valueOf(10));
        req.setOdds(3);

        // Place first bet (OK)
        mockMvc.perform(
                post("/api/bets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req))
        ).andExpect(status().isOk());

        // Place same bet again (should fail)
        mockMvc.perform(
                post("/api/bets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req))
        ).andExpect(status().isBadRequest());
    }
}
