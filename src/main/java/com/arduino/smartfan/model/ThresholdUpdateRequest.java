package com.arduino.smartfan.model;

public class ThresholdUpdateRequest {
    private int temperatureThreshold;
    private int proximityThreshold;

    // Getters and Setters
    public int getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(int temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
    }

    public int getProximityThreshold() {
        return proximityThreshold;
    }

    public void setProximityThreshold(int proximityThreshold) {
        this.proximityThreshold = proximityThreshold;
    }
}
