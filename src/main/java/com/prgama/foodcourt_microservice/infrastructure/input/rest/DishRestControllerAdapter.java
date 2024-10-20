package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.handler.IDishHandler;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishRestControllerAdapter {
    private final IDishHandler dishHandler;

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createDish(@Valid @RequestBody CreateDishRequest request) {
        dishHandler.createDish(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.DISH_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}