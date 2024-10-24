package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.handler.IAuthenticationHandler;
import com.prgama.foodcourt_microservice.application.handler.IRestaurantHandler;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import com.prgama.foodcourt_microservice.infrastructure.constants.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantRestControllerAdapter {
    private final IRestaurantHandler restaurantHandler;
    private final IAuthenticationHandler authenticationHandler;

    @Operation(summary = DocumentationConstants.CREATE_RESTAURANT_SUMMARY,
            tags = {DocumentationConstants.RESTAURANT_TAG},
            description = DocumentationConstants.CREATE_RESTAURANT_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CREATED_STATUS_CODE,
                    description = DocumentationConstants.CREATED_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.NOT_FOUND_STATUS_CODE,
                    description = DocumentationConstants.NOT_FOUND_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CONFLICT_STATUS_CODE,
                    description = DocumentationConstants.CONFLICT_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createRestaurant(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateRestaurantRequest request) {
        authenticationHandler.authentication(token, ControllerConstants.ROLE_ADMIN);
        restaurantHandler.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.RESTAURANT_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}