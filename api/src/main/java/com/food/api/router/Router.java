package com.food.api.router;

import com.food.api.handler.MenuDescriptionHandler;
import com.food.api.handler.MenuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> menuRouter(MenuHandler menuHandler) {
        return RouterFunctions.route()
                .GET("/menus", menuHandler::getMenus)
                .GET("/menus/{id}", menuHandler::getMenu)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> menuDescriptionRouter(MenuDescriptionHandler menuDescriptionHandler) {
        return RouterFunctions.route()
                .GET("/menus/descriptions", menuDescriptionHandler::getMenuDescriptions)
                .GET("/menus/{id}/descriptions", menuDescriptionHandler::getMenuDescription)
                .PUT("/menus/{id}/descriptions", menuDescriptionHandler::putMenuDescription)
                .POST("/menus/descriptions", menuDescriptionHandler::postMenuDescription)
                .build();
    }

}
