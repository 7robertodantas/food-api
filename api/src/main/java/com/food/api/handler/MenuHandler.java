package com.food.api.handler;

import com.food.api.dto.MenuDto;
import com.food.api.repository.MenuDescriptionRepository;
import com.food.core.facade.ItemsValues;
import com.food.core.processor.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class MenuHandler {

    private final MenuDescriptionRepository menuDescriptionRepository;

    private final ItemProcessor itemProcessor;

    @Autowired
    public MenuHandler(MenuDescriptionRepository menuDescriptionRepository,
                       ItemProcessor itemProcessor) {
        this.menuDescriptionRepository = menuDescriptionRepository;
        this.itemProcessor = itemProcessor;
    }

    public Mono<ServerResponse> getMenus(ServerRequest request) {
        return menuDescriptionRepository.findAll()
                .map(menuDescription -> {
                    ItemsValues itemValues = itemProcessor.process(menuDescription);
                    return new MenuDto(menuDescription, itemValues);
                })
                .collectList()
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> getMenu(ServerRequest request) {
        String id = request.pathVariable("id");
        return menuDescriptionRepository.findById(id)
                .map(menuDescription -> {
                    ItemsValues itemValues = itemProcessor.process(menuDescription);
                    return new MenuDto(menuDescription, itemValues);
                })
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

}
