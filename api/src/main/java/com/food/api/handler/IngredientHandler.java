package com.food.api.handler;

import com.food.api.dto.IngredientDto;
import com.food.api.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class IngredientHandler {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientHandler(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Mono<ServerResponse> getIngredients(ServerRequest request) {
        return ingredientRepository.findAll()
                .collectList()
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> putIngredient(ServerRequest request) {
        return request.bodyToMono(IngredientDto.class)
                .flatMap(ingredientRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> postIngredient(ServerRequest request) {
        return request.bodyToMono(IngredientDto.class)
                .flatMap(ingredientRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> deleteIngredient(ServerRequest request) {
        String id = request.pathVariable("id");
        return ingredientRepository.findById(id)
                .flatMap(ingredientRepository::delete)
                .flatMap((none) -> noContent().build());
    }

}
