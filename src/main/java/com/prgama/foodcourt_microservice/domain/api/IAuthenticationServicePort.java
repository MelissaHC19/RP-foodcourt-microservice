package com.prgama.foodcourt_microservice.domain.api;

public interface IAuthenticationServicePort {
    void authentication(String token, String role);
    Long authenticationForDish(String token, String role);
}
