package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTraceabilityRequest {
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime initialTime;
    private List<CreateStatusLogRequest> statusLogs;
    private Long restaurantId;
}
