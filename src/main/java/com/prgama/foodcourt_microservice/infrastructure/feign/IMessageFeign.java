package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.SendMessageRequest;
import com.prgama.foodcourt_microservice.infrastructure.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = FeignConstants.FEIGN_CLIENT_NAME_MESSAGING, url = FeignConstants.FEIGN_CLIENT_URL_MESSAGING)
public interface IMessageFeign {
    @PostMapping("/send-message")
    ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest sendMessageRequest);
}
