package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;
import com.prgama.foodcourt_microservice.infrastructure.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = FeignConstants.FEIGN_CLIENT_NAME, url = FeignConstants.FEIGN_CLIENT_URL)
public interface IUserFeign {
    @GetMapping("/{id}")
    GetUserRequest getUserById(@PathVariable Long id);
}