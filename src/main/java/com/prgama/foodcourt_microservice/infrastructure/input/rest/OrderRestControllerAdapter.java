package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.handler.IAuthenticationHandler;
import com.prgama.foodcourt_microservice.application.handler.IOrderHandler;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestControllerAdapter {
    private final IOrderHandler orderHandler;
    private final IAuthenticationHandler authenticationHandler;

    @Operation(summary = DocumentationConstants.CREATE_ORDER_SUMMARY,
            tags = {DocumentationConstants.ORDER_TAG},
            description = DocumentationConstants.CREATE_ORDER_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CREATED_STATUS_CODE,
                    description = DocumentationConstants.CREATED_RESPONSE_CODE_DESCRIPTION_ORDER,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_ORDER,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.NOT_FOUND_STATUS_CODE,
                    description = DocumentationConstants.NOT_FOUND_RESPONSE_CODE_DESCRIPTION_ORDER,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CONFLICT_STATUS_CODE,
                    description = DocumentationConstants.CONFLICT_RESPONSE_CODE_DESCRIPTION_ORDER,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.FORBIDDEN_STATUS_CODE,
                    description = DocumentationConstants.FORBIDDEN_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.UNAUTHORIZED_STATUS_CODE,
                    description = DocumentationConstants.UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @PostMapping
    private ResponseEntity<ControllerResponse> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateOrderRequest request) {
        Long clientId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_CLIENT);
        orderHandler.createOrder(request, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.ORDER_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }
}
