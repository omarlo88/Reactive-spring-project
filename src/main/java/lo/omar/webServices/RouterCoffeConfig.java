package lo.omar.webServices;

import lo.omar.entities.CoffeeOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class RouterCoffeConfig {

    @Bean
    RouterFunction<ServerResponse> routerFunctionCoffee(CoffeeHandler handler){
        return RouterFunctions.route()
                .path("/api/coffees", builder -> builder
                        //.GET("/", contentType(APPLICATION_JSON), handler::getCoffees)
                        .GET("/", handler::getCoffees)
                        .GET("/{id}", handler::getCoffee)
                        .GET("/coffee/{id}", handler::getCoffeeOrdres /*serverRequest -> {
                            String id = serverRequest.pathVariable("id");
                            Flux<CoffeeOrder> coffeeOrderFlux = Flux.<CoffeeOrder>generate(sink -> sink.next(new CoffeeOrder(id, Instant.now())))
                                    .delayElements(Duration.ofSeconds(1));
                            coffeeOrderFlux.subscribe(System.out::println);
                            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                    .body(coffeeOrderFlux, CoffeeOrder.class);
                        }*/)
                        .DELETE("/{id}", handler::deleteCoffe)
                        .nest(accept(APPLICATION_JSON), builder1 -> builder1
                                .POST("/", handler::saveCoffee2)
                                .PUT("/{id}", handler::updateCoffee))
                )
                .build();
    }
}
