package com.prgama.foodcourt_microservice.domain.api;

public interface IUserServicePort {
    boolean getUserById(Long id);
    Long getEmployeesRestaurant(Long employeeId);
}