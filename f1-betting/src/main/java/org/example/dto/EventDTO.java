package org.example.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventDTO {
    private String eventId;
    private String meetingName;
    private String sessionType;
    private Integer year;
    private String country;
    private LocalDateTime startTime;
    private List<DriverMarketDTO> driverMarket;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<DriverMarketDTO> getDriverMarket() {
        return driverMarket;
    }

    public void setDriverMarket(List<DriverMarketDTO> driverMarket) {
        this.driverMarket = driverMarket;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventId='" + eventId + '\'' +
                ", meetingName='" + meetingName + '\'' +
                ", sessionType='" + sessionType + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", startTime=" + startTime +
                ", driverMarket=" + driverMarket +
                '}';
    }
}
