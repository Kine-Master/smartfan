package com.arduino.smartfan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private float temperature;
    private float proximity;
    private boolean fanOn;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getProximity() {
        return proximity;
    }

    public void setProximity(float proximity) {
        this.proximity = proximity;
    }

    public boolean isFanOn() {
        return fanOn;
    }

    public void setFanOn(boolean fanOn) {
        this.fanOn = fanOn;
    }
}
