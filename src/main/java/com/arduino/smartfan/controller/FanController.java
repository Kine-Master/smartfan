package com.arduino.smartfan.controller;

import com.arduino.smartfan.model.FanStatus;
import com.arduino.smartfan.model.ThresholdUpdateRequest;
import com.arduino.smartfan.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/fan")
@CrossOrigin(origins = "*")
public class FanController {

    @Autowired
    private FanService fanService;

    // Fetch all fan status records
    @GetMapping("/status")
    public List<FanStatus> getFanStatus() {
        return fanService.getAllStatuses();
    }

    // Update thresholds
    @PostMapping("/thresholds")
    public String updateThresholds(@RequestBody ThresholdUpdateRequest request) {
        fanService.updateThresholds(request);
        return "Thresholds updated successfully!";
    }

    // Get the current fan status
    @GetMapping("/current")
    public FanStatus getCurrentStatus() {
        return fanService.getCurrentStatus();
    }

    // Check if Arduino device is connected
    @GetMapping("/device-status")
    public ResponseEntity<String> checkDeviceStatus() {
        String deviceUrl = "http://your-arduino-ip/status"; // Replace with your Arduino IP

        try {
            URL url = new URL(deviceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds timeout
            connection.setReadTimeout(3000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return ResponseEntity.ok("Connected");
            } else {
                return ResponseEntity.ok("Disconnected");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("Disconnected");
        }
    }
}
