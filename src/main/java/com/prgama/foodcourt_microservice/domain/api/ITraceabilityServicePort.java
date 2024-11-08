package com.prgama.foodcourt_microservice.domain.api;

import java.time.LocalDateTime;

public interface ITraceabilityServicePort {
    void createTraceability(Long orderId, Long clientId, String clientEmail, LocalDateTime initialTime, String newStatus);
    void updateTraceability(LocalDateTime finalTime, String lastStatus, String newStatus, Long employeeId, String employeeEmail, Long orderId);
}
