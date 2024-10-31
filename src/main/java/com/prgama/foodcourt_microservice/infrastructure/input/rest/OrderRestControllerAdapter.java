package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.dto.response.ListOrdersResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.application.handler.IAuthenticationHandler;
import com.prgama.foodcourt_microservice.application.handler.IOrderHandler;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import com.prgama.foodcourt_microservice.infrastructure.constants.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<ControllerResponse> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateOrderRequest request) {
        Long clientId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_CLIENT);
        orderHandler.createOrder(request, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.ORDER_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = DocumentationConstants.LIST_ORDERS_SUMMARY,
            tags = {DocumentationConstants.ORDER_TAG, DocumentationConstants.RESTAURANT_TAG},
            description = DocumentationConstants.LIST_ORDERS_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.OK_STATUS_CODE,
                    description = DocumentationConstants.OK_RESPONSE_CODE_DESCRIPTION_PAGINATION_ORDERS,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_PAGINATION,
                    content = @Content),
    })
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<ListOrdersResponse>> listOrders(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(description = DocumentationConstants.STATUS_PARAMETER)
            @RequestParam(required = false) String status,
            @Parameter(description = DocumentationConstants.PAGE_NUMBER_PARAMETER)
            @RequestParam(required = false) Integer pageNumber,
            @Parameter(description = DocumentationConstants.PAGE_SIZE_PARAMETER_ORDERS)
            @RequestParam(required = false) Integer pageSize,
            @Parameter(description = DocumentationConstants.SORT_DIRECTION_PARAMETER)
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_SORT_DIRECTION) String sortDirection
    ) {
        Long employeeId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_EMPLOYEE);
        PaginationResponse<ListOrdersResponse> pagination = orderHandler.listOrders(employeeId, status, pageNumber, pageSize, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(pagination);
    }
}
