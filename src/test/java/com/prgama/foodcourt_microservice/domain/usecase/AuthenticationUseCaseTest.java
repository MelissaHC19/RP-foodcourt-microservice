package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.InvalidRoleException;
import com.prgama.foodcourt_microservice.domain.exception.InvalidTokenException;
import com.prgama.foodcourt_microservice.domain.spi.ITokenProviderPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseTest {
    @Mock
    private ITokenProviderPort tokenProviderPort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    @Test
    @DisplayName("Authenticates user successfully when token is valid and has the role with permissions")
    void authentication() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Owner";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(true);
        Mockito.when(tokenProviderPort.getRoleFromToken(token)).thenReturn(role);
        authenticationUseCase.authentication(token, role);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).getRoleFromToken(token);
    }

    @Test
    @DisplayName("Validation exception when token is invalid")
    void authenticationShouldThrowValidationExceptionWhenInvalidToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDSCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Owner";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(false);
        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            authenticationUseCase.authentication(token, role);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_TOKEN_MESSAGE);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.never()).getRoleFromToken(token);
    }

    @Test
    @DisplayName("Validation exception when user has a role without permissions")
    void authenticationShouldThrowValidationExceptionWhenInvalidRole() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Admin";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(true);
        Mockito.when(tokenProviderPort.getRoleFromToken(token)).thenReturn("Owner");
        InvalidRoleException exception = assertThrows(InvalidRoleException.class, () -> {
            authenticationUseCase.authentication(token, role);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_ROLE_MESSAGE);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).getRoleFromToken(token);
    }

    @Test
    @DisplayName("Authenticates user successfully and returns user id when token is valid and has role with permissions")
    void authenticationForDish() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Owner";
        String userId = "10";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(true);
        Mockito.when(tokenProviderPort.getRoleFromToken(token)).thenReturn(role);
        Mockito.when(tokenProviderPort.getUserIdFromToken(token)).thenReturn(userId);
        Long result = authenticationUseCase.authenticationForDish(token, role);
        assertThat(result).isEqualTo(Long.valueOf(userId));
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).getRoleFromToken(token);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).getUserIdFromToken(token);
    }

    @Test
    @DisplayName("Validation exception when token is invalid")
    void authenticationForDishShouldThrowValidationExceptionWhenInvalidToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Owner";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(false);
        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            authenticationUseCase.authenticationForDish(token, role);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_TOKEN_MESSAGE);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.never()).getRoleFromToken(token);
        Mockito.verify(tokenProviderPort, Mockito.never()).getUserIdFromToken(token);
    }

    @Test
    @DisplayName("Validation exception when user has a role without permissions")
    void authenticationForDishShouldThrowValidationExceptionWhenInvalidRole() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        String role = "Admin";
        Mockito.when(tokenProviderPort.validateToken(token)).thenReturn(true);
        Mockito.when(tokenProviderPort.getRoleFromToken(token)).thenReturn("Owner");
        InvalidRoleException exception = assertThrows(InvalidRoleException.class, () -> {
            authenticationUseCase.authenticationForDish(token, role);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_ROLE_MESSAGE);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).validateToken(token);
        Mockito.verify(tokenProviderPort, Mockito.times(1)).getRoleFromToken(token);
        Mockito.verify(tokenProviderPort, Mockito.never()).getUserIdFromToken(token);
    }
}