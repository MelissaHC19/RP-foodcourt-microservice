package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetUserRequest {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String role;
}
