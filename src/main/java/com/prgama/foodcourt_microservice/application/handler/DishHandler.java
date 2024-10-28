package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ListDishesResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.application.mapper.ICreateDishRequestMapper;
import com.prgama.foodcourt_microservice.application.mapper.IListDishesResponseMapper;
import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final ICreateDishRequestMapper createDishRequestMapper;
    private final IListDishesResponseMapper listDishesResponseMapper;

    @Override
    public void createDish(Long ownerId, CreateDishRequest createDishRequest) {
        Dish dish = createDishRequestMapper.requestToDish(createDishRequest);
        dishServicePort.createDish(ownerId, dish);
    }

    @Override
    public void modifyDish(Long id, ModifyDishRequest modifyDishRequest, Long ownerId) {
        dishServicePort.modifyDish(id, modifyDishRequest.getDescription(), modifyDishRequest.getPrice(), modifyDishRequest.getActive(), ownerId);
    }

    @Override
    public PaginationResponse<ListDishesResponse> listDishes(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortDirection) {
        Pagination<Dish> pagination = dishServicePort.listDishesByRestaurantAndCategory(restaurantId, categoryId, pageNumber, pageSize, sortDirection);
        return listDishesResponseMapper.paginationToPaginationResponse(pagination);
    }
}