package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PlaceBetRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String eventId;

    @NotNull
    private Integer driverId;

    @NotBlank
    private String driverName;

    @NotNull
    @Min(1)
    private BigDecimal amount;

    @NotNull
    private Integer odds;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getOdds() { return odds; }
    public void setOdds(Integer odds) { this.odds = odds; }

    @Override
    public String toString() {
        return "PlaceBetRequest{" +
                "userId=" + userId +
                ", eventId='" + eventId + '\'' +
                ", driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", amount=" + amount +
                ", odds=" + odds +
                '}';
    }
}
