package com.prgama.foodcourt_microservice.application.dto.request;

import com.prgama.foodcourt_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModifyDishRequest {
    private String description;

    @Positive(message = RequestConstants.POSITIVE_PRICE_MESSAGE)
    private Integer price;
}
