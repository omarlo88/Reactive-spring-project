package lo.omar.webservicesboutique;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class ProduitRouterConfig {

  private static final String PATH_ID = "/{id}";  // Compliant
    @Bean
    RouterFunction<?> routerFunctionProduit(ProduitHandler handler) {
        return RouterFunctions.route()
                .path("/global/api/produits", builder -> builder
                    .GET("", handler::getProduits)
                    .GET(PATH_ID, handler::getProduit)
                    .GET("/produitsQuery/byNom", handler::getProduitsByNom)
                    .GET("/produitsbycategorie/{idCat}", handler::getProduitsByCategorieId)
                    .GET("/produitsbyprix", handler::getProduitsByPrix)
                    .GET("/produitsbyprixbeteween", handler::getProduitsByPrixBetween)
                    .GET("/produitsbypromotion", handler::getProduitsByNom)
                    .DELETE(PATH_ID, handler::deleteProduit)
                        .nest(accept(MediaType.APPLICATION_JSON), builder1 -> builder1
                            .PUT(PATH_ID, handler::updateProduit)
                            .POST("", handler::saveProduit)
                        )
                )
                .build();
    }
}
