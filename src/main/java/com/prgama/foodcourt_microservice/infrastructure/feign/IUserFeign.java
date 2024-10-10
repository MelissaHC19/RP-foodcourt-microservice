package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-microservice", url = "localhost:8080/user")
public interface IUserFeign {
    @GetMapping("/{id}")
    GetUserRequest getUserById(@PathVariable Long id);
}