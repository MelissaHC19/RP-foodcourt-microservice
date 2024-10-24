package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.domain.api.IAuthenticationServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationHandler implements IAuthenticationHandler {
    private final IAuthenticationServicePort authenticationServicePort;

    @Override
    public void authentication(String token, String role) {
        authenticationServicePort.authentication(token.substring(7), role);
    }

    @Override
    public Long authenticationForDish(String token, String role) {
        return authenticationServicePort.authenticationForDish(token.substring(7), role);
    }
}
