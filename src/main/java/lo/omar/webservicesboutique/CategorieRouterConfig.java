package lo.omar.webservicesboutique;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;


@Configuration
public class CategorieRouterConfig {

    @Bean
    RouterFunction<?> routerFunctionCategorie(CategorieHandler handler) {
        return RouterFunctions.route()
                .path("/api/categories", builder -> builder
                        .GET("/", handler::getCategories)
                        .GET("/{id}", handler::getCategorie)
                        .DELETE("/{id}", handler::deleteCategorie)
                        .nest(accept(MediaType.APPLICATION_JSON), builder1 -> builder1
                                .POST("/", handler::saveCategorie)
                                .PUT("/{id}", handler::updateCategorie)
                        ))
                .build();
    }
}


