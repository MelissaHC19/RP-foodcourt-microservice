package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServicePortImpl implements IUserServicePort {
    private final IUserFeign userFeign;

    @Override
    public boolean getUserById(Long id) {
        GetUserRequest userRequest = userFeign.getUserById(id);
        return userRequest.isRoleValidation();
    }
}