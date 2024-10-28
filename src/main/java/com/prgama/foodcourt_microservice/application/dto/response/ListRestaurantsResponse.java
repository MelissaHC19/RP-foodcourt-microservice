package com.prgama.foodcourt_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ListRestaurantsResponse {
    private final String name;
    private final String urlLogo;
}
