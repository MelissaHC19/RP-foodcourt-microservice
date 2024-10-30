package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.handler.IAuthenticationHandler;
import com.prgama.foodcourt_microservice.application.handler.IOrderHandler;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestControllerAdapter {
    private final IOrderHandler orderHandler;
    private final IAuthenticationHandler authenticationHandler;

    @PostMapping
    private ResponseEntity<ControllerResponse> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateOrderRequest request) {
        Long clientId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_CLIENT);
        orderHandler.createOrder(request, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.ORDER_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}
