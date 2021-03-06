package com.food.api.repository;

import com.food.api.dto.MenuDescriptionDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MenuDescriptionRepository extends ReactiveCrudRepository<MenuDescriptionDto, String> {
}
