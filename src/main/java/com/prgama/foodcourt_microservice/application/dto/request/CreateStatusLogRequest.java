package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateStatusLogRequest {
    private String lastStatus;
    private String newStatus;
    private LocalDateTime issuedAt;
}
