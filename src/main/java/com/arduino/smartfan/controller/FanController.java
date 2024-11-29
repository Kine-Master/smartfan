package com.arduino.smartfan.controller;

import com.arduino.smartfan.model.FanStatus;
import com.arduino.smartfan.model.ThresholdUpdateRequest;
import com.arduino.smartfan.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fan")
@CrossOrigin(origins = "*")
public class FanController {

    @Autowired
    private FanService fanService;

    @GetMapping("/status")
    public List<FanStatus> getFanStatus() {
        return fanService.getAllStatuses();
    }

    @PostMapping("/thresholds")
    public String updateThresholds(@RequestBody ThresholdUpdateRequest request) {
        fanService.updateThresholds(request);
        return "Thresholds updated successfully!";
    }

    @GetMapping("/current")
    public FanStatus getCurrentStatus() {
        return fanService.getCurrentStatus();
    }
}
