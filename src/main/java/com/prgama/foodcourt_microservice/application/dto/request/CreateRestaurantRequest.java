package com.prgama.foodcourt_microservice.application.dto.request;

import com.prgama.foodcourt_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateRestaurantRequest {
    @NotBlank(message = RequestConstants.NAME_MANDATORY_MESSAGE)
    private String name;

    @NotBlank(message = RequestConstants.NIT_MANDATORY_MESSAGE)
    private String nit;

    @NotBlank(message = RequestConstants.ADDRESS_MANDATORY_MESSAGE)
    private String address;

    @NotBlank(message = RequestConstants.PHONE_NUMBER_MANDATORY_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = RequestConstants.URL_LOGO_MANDATORY_MESSAGE)
    private String urlLogo;

    @NotNull(message = RequestConstants.OWNER_ID_MANDATORY_MESSAGE)
    private Long ownerId;
}
