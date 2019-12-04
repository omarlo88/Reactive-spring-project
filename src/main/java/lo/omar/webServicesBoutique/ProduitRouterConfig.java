package lo.omar.webServicesBoutique;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ProduitRouterConfig {

    @Bean
    RouterFunction<?> routerFunctionProduit(ProduitHandler handler) {
        return RouterFunctions.route()
                .path("/global/api/produits", builder -> builder
                        .GET("/", handler::getProduits)
                        .GET("/{id}", handler::getProduit)
                        .GET("/produitsQuery/byNom", handler::getProduitsByNom)
                        .GET("/produitsbycategorie/{idCat}", handler::getProduitsByCategorieId)
                        .GET("/produitsbyprix", handler::getProduitsByPrix)
                        .GET("/produitsbyprixbeteween", handler::getProduitsByPrixBetween)
                        .GET("/produitsbypromotion", handler::getProduitsByNom)
                        .DELETE("/{id}", handler::deleteProduit)
                        .nest(accept(MediaType.APPLICATION_JSON), builder1 -> builder1
                                .PUT("/{id}", handler::updateProduit)
                                .POST("/", handler::saveProduit)
                        )
                )
                .build();
    }
}
