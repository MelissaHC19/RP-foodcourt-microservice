package com.prgama.foodcourt_microservice.application.handler;

public interface IAuthenticationHandler {
    void authentication(String token, String role);
    Long authenticationForDish(String token, String role);
}