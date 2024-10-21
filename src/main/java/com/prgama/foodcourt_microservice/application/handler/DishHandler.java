package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.mapper.ICreateDishRequestMapper;
import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final ICreateDishRequestMapper createDishRequestMapper;

    @Override
    public void createDish(CreateDishRequest createDishRequest) {
        Dish dish = createDishRequestMapper.requestToDish(createDishRequest);
        dishServicePort.createDish(dish);
    }

    @Override
    public void modifyDish(Long id, ModifyDishRequest modifyDishRequest) {
        dishServicePort.modifyDish(id, modifyDishRequest.getDescription(), modifyDishRequest.getPrice());
    }
}