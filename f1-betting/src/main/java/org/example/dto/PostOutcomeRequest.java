package org.example.dto;

import jakarta.validation.constraints.NotNull;

public class PostOutcomeRequest {
    @NotNull
    private Integer winnerDriverId;

    public Integer getWinnerDriverId() { return winnerDriverId; }
    public void setWinnerDriverId(Integer winnerDriverId) { this.winnerDriverId = winnerDriverId; }

    @Override
    public String toString() {
        return "PostOutcomeRequest{" +
                "winnerDriverId=" + winnerDriverId +
                '}';
    }
}
