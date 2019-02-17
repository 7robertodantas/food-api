package com.food.api.router;

import com.food.api.handler.IngredientHandler;
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
                .PUT("/menus/{id}", menuHandler::putMenu)
                .POST("/menus", menuHandler::postMenu)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ingredientRouter(IngredientHandler ingredientHandler) {
        return RouterFunctions.route()
                .GET("/ingredients", ingredientHandler::getIngredients)
                .PUT("/ingredients/{name}", ingredientHandler::putIngredient)
                .DELETE("/ingredients/{name}", ingredientHandler::deleteIngredient)
                .POST("/ingredients", ingredientHandler::postIngredient)
                .build();
    }

}
