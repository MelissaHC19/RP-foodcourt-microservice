package com.prgama.foodcourt_microservice.application.dto.request;

import com.prgama.foodcourt_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateRestaurantRequest {
    @NotBlank(message = RequestConstants.NAME_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.NAME_REGULAR_EXPRESSION, message = RequestConstants.INVALID_NAME_MESSAGE)
    private String name;

    @NotBlank(message = RequestConstants.NIT_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.NIT_REGULAR_EXPRESSION, message = RequestConstants.INVALID_NIT_MESSAGE)
    private String nit;

    @NotBlank(message = RequestConstants.ADDRESS_MANDATORY_MESSAGE)
    private String address;

    @NotBlank(message = RequestConstants.PHONE_NUMBER_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.PHONE_NUMBER_REGULAR_EXPRESSION, message = RequestConstants.INVALID_PHONE_NUMBER_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = RequestConstants.URL_LOGO_MANDATORY_MESSAGE)
    private String urlLogo;

    @NotNull(message = RequestConstants.OWNER_ID_MANDATORY_MESSAGE)
    private Long ownerId;
}
