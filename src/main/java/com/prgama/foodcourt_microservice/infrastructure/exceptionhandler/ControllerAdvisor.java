package com.prgama.foodcourt_microservice.infrastructure.exceptionhandler;

import com.prgama.foodcourt_microservice.application.dto.response.ExceptionResponse;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.infrastructure.constants.ControllerConstants;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<ExceptionResponse> handleNotOwnerException(NotOwnerException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AlreadyExistsByNameException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsByNameException(AlreadyExistsByNameException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(RestaurantNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestFeignClient(FeignException.NotFound exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage().split(ControllerConstants.EXTRACT_ERROR_MESSAGE_START)[1].split(ControllerConstants.EXTRACT_ERROR_MESSAGE_END)[0], HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptionsDTO(MethodArgumentNotValidException exception) {
        ArrayList<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getDefaultMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put(ControllerConstants.TIMESTAMP, LocalDateTime.now().toString());
        response.put(ControllerConstants.ERRORS, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}