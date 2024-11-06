package com.prgama.foodcourt_microservice.domain.api;

public interface IMessageServicePort {
    void sendMessage(String phoneNumber, String message);
}
