package com.arduino.smartfan.repository;

import com.arduino.smartfan.model.FanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanStatusRepository extends JpaRepository<FanStatus, Long> {
}
