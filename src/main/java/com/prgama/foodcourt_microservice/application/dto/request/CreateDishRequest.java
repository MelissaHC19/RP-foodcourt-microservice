package com.prgama.foodcourt_microservice.application.dto.request;

import com.prgama.foodcourt_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDishRequest {
    @NotBlank(message = RequestConstants.NAME_MANDATORY_MESSAGE)
    private String name;

    @NotBlank(message = RequestConstants.DESCRIPTION_MANDATORY_MESSAGE)
    private String description;

    @NotNull(message = RequestConstants.PRICE_MANDATORY_MESSAGE)
    @Positive(message = RequestConstants.POSITIVE_PRICE_MESSAGE)
    private Integer price;

    @NotBlank(message = RequestConstants.URL_IMAGE_MANDATORY_MESSAGE)
    private String urlImage;

    @NotNull(message = RequestConstants.RESTAURANT_ID_MANDATORY_MESSAGE)
    private Long restaurantId;

    @NotNull(message = RequestConstants.CATEGORY_ID_MANDATORY_MESSAGE)
    private Long categoryId;
}