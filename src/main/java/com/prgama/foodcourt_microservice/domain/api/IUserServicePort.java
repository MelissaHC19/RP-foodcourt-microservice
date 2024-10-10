package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;

public interface IUserServicePort {
    GetUserRequest getUserById(Long id);
}