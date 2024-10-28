package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.DishEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IDishPageMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishEntityMapper dishEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishPageMapper dishPageMapper;

    @Override
    public void createDish(Dish dish) {
        dishRepository.save(dishEntityMapper.dishToEntity(dish));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return dishRepository.findByName(name).isPresent();
    }

    @Override
    public void modifyDish(Dish dish) {
        dishRepository.save(dishEntityMapper.dishToEntity(dish));
    }

    @Override
    public Dish findById(Long id) {
        return dishEntityMapper.dishEntityToDish(dishRepository.findById(id).orElse(null));
    }

    @Override
    public Pagination<Dish> listDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<DishEntity> page = (categoryId == null)
                ? dishRepository.findAllByRestaurantId(restaurantId, pageRequest)
                : dishRepository.findAllByRestaurantIdAndCategoryId(categoryId, restaurantId, pageRequest);

        return dishPageMapper.pageToPagination(page, dishEntityMapper);
    }
}