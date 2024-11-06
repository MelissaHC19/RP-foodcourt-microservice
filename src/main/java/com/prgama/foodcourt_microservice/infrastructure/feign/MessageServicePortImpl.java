package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.SendMessageRequest;
import com.prgama.foodcourt_microservice.domain.api.IMessageServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MessageServicePortImpl implements IMessageServicePort {
    private final IMessageFeign messageFeign;

    @Override
    public void sendMessage(String phoneNumber, String message) {
        SendMessageRequest request = new SendMessageRequest(phoneNumber, message);
        messageFeign.sendMessage(request);
    }
}
