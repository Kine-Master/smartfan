package com.arduino.smartfan.service;

import com.arduino.smartfan.model.FanStatus;
import com.arduino.smartfan.model.ThresholdUpdateRequest;
import com.arduino.smartfan.repository.FanStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FanService {

    @Autowired
    private FanStatusRepository repository;

    private int temperatureThreshold = 30;
    private int proximityThreshold = 300;

    public List<FanStatus> getAllStatuses() {
        return repository.findAll();
    }

    public void updateThresholds(ThresholdUpdateRequest request) {
        this.temperatureThreshold = request.getTemperatureThreshold();
        this.proximityThreshold = request.getProximityThreshold();
    }

    public FanStatus getCurrentStatus() {
        List<FanStatus> statuses = repository.findAll();
        return statuses.isEmpty() ? null : statuses.get(statuses.size() - 1);
    }

    public void saveFanStatus(float temperature, float proximity, boolean fanOn) {
        FanStatus status = new FanStatus();
        status.setTimestamp(LocalDateTime.now());
        status.setTemperature(temperature);
        status.setProximity(proximity);
        status.setFanOn(fanOn);

        repository.save(status);

        // Enforce FIFO limit (150-300 entries)
        if (repository.count() > 300) {
            List<FanStatus> statuses = repository.findAll();
            repository.delete(statuses.get(0)); // Remove the oldest entry
        }
    }
}
