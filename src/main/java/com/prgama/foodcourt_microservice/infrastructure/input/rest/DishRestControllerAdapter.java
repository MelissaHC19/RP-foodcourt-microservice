package com.prgama.foodcourt_microservice.infrastructure.input.rest;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ControllerResponse;
import com.prgama.foodcourt_microservice.application.handler.IDishHandler;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import com.prgama.foodcourt_microservice.infrastructure.constants.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishRestControllerAdapter {
    private final IDishHandler dishHandler;

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
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createDish(@Valid @RequestBody CreateDishRequest request) {
        dishHandler.createDish(request);
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
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<ControllerResponse> modifyDish(@PathVariable Long id, @Valid @RequestBody ModifyDishRequest request) {
        dishHandler.modifyDish(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ControllerResponse(ControllerConstants.DISH_MODIFIED_MESSAGE, HttpStatus.OK.toString(), LocalDateTime.now()));
    }
}