package lo.omar.webservicesboutique;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;


@Configuration
public class SousCategorieRouterConfig {

    /*@Bean
    RouterFunction<?> routerFunctionSousCategorie1(SousCategorieHandler handler){
        return RouterFunctions.route(
                GET("/api/categories"), handler::getSousCategories)
                .andRoute(GET("/api/categories/{id}"), handler::getSousCategorie)
                .andRoute(DELETE("/api/categories/{id}"), handler::deleteSousCategorie)
                .andRoute(POST("/api/categories").and(accept(MediaType.APPLICATION_JSON)), handler::saveSousCategorie)
                .andRoute(PUT("/api/categories/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::updateSousCategorie);
    }*/

    @Bean
    RouterFunction<?> routerFunctionSousCategorie(SousCategorieHandler handler) {
        return RouterFunctions.route()
                .path("/api/sousCategories", builder -> builder
                        .GET("/", handler::getSousCategories)
                        .GET("/{id}", handler::getSousCategorie)
                        .GET("/byIdCat/{idCat}", handler::getSousCategoriesByCategoriesIdsContains)
                        .DELETE("/{id}", handler::deleteSousCategorie)
                        .POST("/", accept(MediaType.APPLICATION_JSON), handler::saveSousCategorie)
                        .PUT("/{id}",accept(MediaType.APPLICATION_JSON), handler::updateSousCategorie)
                )
                .build();
    }

}


