package lo.omar.webServicesBoutique;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
public class CommandeProduitRouterConfig {

    @Bean
    RouterFunction<ServerResponse> routerFunctionCommandeProduit(CommandeProduitHandler handler){
        return RouterFunctions.route()
                .path("/api/commandesProduits", builder -> builder
                        .GET("/", handler::getCommandesProduits)
                        .GET("/{id}", handler::getCommandesProduit)
                        .GET("/bystatus/{stutat}", handler::getCommandesProduitByStatut)
                        .GET("/bydate/{date}", handler::getCommandesProduitByDate)
                        .GET("/bydates", handler::getCommandesProduitByDateBetween)
                        .DELETE("/", handler::deleteCommandeProduit)
                        .nest(accept(MediaType.APPLICATION_JSON), builder1 -> builder1
                                .POST("/", handler::saveCommande)
                                .PUT("/{id}", handler::updateCommandeProduit)
                        )
                )
                .build();
    }
}
