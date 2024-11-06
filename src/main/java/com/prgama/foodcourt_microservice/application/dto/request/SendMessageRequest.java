package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMessageRequest {
    private String phoneNumber;
    private String message;
}
