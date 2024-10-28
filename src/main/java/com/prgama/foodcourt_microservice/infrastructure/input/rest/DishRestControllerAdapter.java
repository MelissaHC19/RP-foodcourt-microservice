package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.dto.response.ListDishesResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.application.handler.IAuthenticationHandler;
import com.prgama.foodcourt_microservice.application.handler.IDishHandler;
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
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishRestControllerAdapter {
    private final IDishHandler dishHandler;
    private final IAuthenticationHandler authenticationHandler;

    @Operation(summary = DocumentationConstants.CREATE_DISH_SUMMARY,
            tags = {DocumentationConstants.DISH_TAG},
            description = DocumentationConstants.CREATE_DISH_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CREATED_STATUS_CODE,
                    description = DocumentationConstants.CREATED_RESPONSE_CODE_DESCRIPTION_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.NOT_FOUND_STATUS_CODE,
                    description = DocumentationConstants.NOT_FOUND_RESPONSE_CODE_DESCRIPTION_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CONFLICT_STATUS_CODE,
                    description = DocumentationConstants.CONFLICT_RESPONSE_CODE_DESCRIPTION_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.FORBIDDEN_STATUS_CODE,
                    description = DocumentationConstants.FORBIDDEN_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.UNAUTHORIZED_STATUS_CODE,
                    description = DocumentationConstants.UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody CreateDishRequest request) {
        Long ownerId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_OWNER);
        dishHandler.createDish(ownerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.DISH_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = DocumentationConstants.UPDATE_DISH_SUMMARY,
            tags = {DocumentationConstants.DISH_TAG},
            description = DocumentationConstants.UPDATE_DISH_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.OK_STATUS_CODE,
                    description = DocumentationConstants.OK_RESPONSE_CODE_DESCRIPTION_UPDATE_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.NOT_FOUND_STATUS_CODE,
                    description = DocumentationConstants.NOT_FOUND_RESPONSE_CODE_DESCRIPTION_UPDATE_DISH,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.FORBIDDEN_STATUS_CODE,
                    description = DocumentationConstants.FORBIDDEN_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.UNAUTHORIZED_STATUS_CODE,
                    description = DocumentationConstants.UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<ControllerResponse> modifyDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id, @Valid @RequestBody ModifyDishRequest request) {
        Long ownerId = authenticationHandler.authenticationForDish(token, ControllerConstants.ROLE_OWNER);
        dishHandler.modifyDish(id, request, ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ControllerResponse(ControllerConstants.DISH_MODIFIED_MESSAGE, HttpStatus.OK.toString(), LocalDateTime.now()));
    }

    @Operation(summary = DocumentationConstants.LIST_DISHES_SUMMARY,
            tags = {DocumentationConstants.DISH_TAG},
            description = DocumentationConstants.LIST_DISHES_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.OK_STATUS_CODE,
                    description = DocumentationConstants.OK_RESPONSE_CODE_DESCRIPTION_PAGINATION_DISHES,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_PAGINATION,
                    content = @Content),
    })
    @GetMapping("/list/{restaurantId}")
    public ResponseEntity<PaginationResponse<ListDishesResponse>> listDishes(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long restaurantId,
            @Parameter(description = DocumentationConstants.CATEGORY_ID_PARAMETER)
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = DocumentationConstants.PAGE_NUMBER_PARAMETER)
            @RequestParam(required = false) Integer pageNumber,
            @Parameter(description = DocumentationConstants.PAGE_SIZE_PARAMETER_DISHES)
            @RequestParam(required = false) Integer pageSize,
            @Parameter(description = DocumentationConstants.SORT_DIRECTION_PARAMETER)
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_SORT_DIRECTION) String sortDirection
    ) {
        authenticationHandler.authentication(token, ControllerConstants.ROLE_CLIENT);
        PaginationResponse<ListDishesResponse> pagination = dishHandler.listDishes(restaurantId, categoryId, pageNumber, pageSize, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(pagination);
    }
}