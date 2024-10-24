package com.prgama.foodcourt_microservice.domain.spi;

public interface ITokenProviderPort {
    boolean validateToken(String token);
    String getRoleFromToken(String token);
    String getUserIdFromToken(String token);
}
