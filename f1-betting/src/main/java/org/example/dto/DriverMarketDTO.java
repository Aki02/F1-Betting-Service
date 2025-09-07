package org.example.dto;

public class DriverMarketDTO {
    private Integer driverId;
    private String driverFullName;
    private int odds;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverFullName() {
        return driverFullName;
    }

    public void setDriverFullName(String driverFullName) {
        this.driverFullName = driverFullName;
    }

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    @Override
    public String toString() {
        return "DriverMarketDTO{" +
                "driverId=" + driverId +
                ", driverFullName='" + driverFullName + '\'' +
                ", odds=" + odds +
                '}';
    }
}
