package lo.omar.webServices;

import lo.omar.entities.Coffee;
import lo.omar.entities.CoffeeOrder;
import lo.omar.services.CoffeeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CoffeeHandler {

    /*@Autowired
    private CoffeService coffeService;*/

    private final CoffeeService coffeeService;

    public CoffeeHandler(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

//    @PreAuthorize("hasAnyRole('user', 'admin')")
public Mono<ServerResponse> getCoffees(ServerRequest request){
    return ServerResponse.ok().body(coffeeService.getCoffees(),Coffee.class);
}

    //    @PreAuthorize("hasAnyRole('user', 'admin')")
    public Mono<ServerResponse> getCoffee(ServerRequest request){
        /*Mono<Coffee> coffeeMono = coffeeService.getCoffee(request.pathVariable("id"));
        return coffeeMono
                .flatMap(coffee -> ServerResponse.ok().body(BodyInserters.fromPublisher(coffeeMono, Coffee.class)))
                .switchIfEmpty(ServerResponse.notFound().build());*/

        /*return ServerResponse.ok().body(coffeeService.getCoffee(request.pathVariable("id")), Coffee.class)
                .switchIfEmpty(ServerResponse.notFound().build());*/

        return coffeeService.getCoffee(request.pathVariable("id"))
            .flatMap(coffee -> ServerResponse.ok().body(BodyInserters.fromValue(coffee)))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    //    @PreAuthorize("hasRole('admin')")
    public Mono<ServerResponse> deleteCoffe(ServerRequest request){

        /*return ServerResponse.noContent().build(coffeeService.deleteCoffee(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());*/

        return coffeeService.getCoffee(request.pathVariable("id"))
            .flatMap(c -> ServerResponse.ok().build(coffeeService.deleteCoffee(c.getId())))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    //    @PreAuthorize("hasRole('admin')")
    public Mono<ServerResponse> saveCoffee2(ServerRequest request){

        /*return coffeeService.saveCoffe(request.bodyToMono(Coffee.class))
                .flatMap(coffee -> ServerResponse.ok().body(BodyInserters.fromObject(coffee)));*/

        return ServerResponse.ok().body(
                request.bodyToMono(Coffee.class)
                .flatMap(coffeeService::saveCoffee), Coffee.class
        );
    }

    //    @PreAuthorize("hasRole('admin')")
    public Mono<ServerResponse> saveCoffee(ServerRequest request){

        /*return coffeeService.saveCoffe(request.bodyToMono(Coffee.class))
                .flatMap(coffee -> ServerResponse.ok().body(BodyInserters.fromObject(coffee)));*/

        return ServerResponse.ok().body(
            coffeeService.saveCoffee(request.bodyToMono(Coffee.class)), Coffee.class);
    }

    //    @PreAuthorize("hasRole('admin')")
    public Mono<ServerResponse> updateCoffee(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Coffee> coffeeNew = request.bodyToMono(Coffee.class);
        return coffeeService.getCoffee(id)
            .flatMap(coffeeOld -> ServerResponse.ok().body(
                BodyInserters.fromPublisher(
                    coffeeNew
                        //.map(coffee -> new Coffee(id, coffee.getCoffeeName()))
                        .map(coffee -> {
                            coffeeOld.setCoffeeName(coffee.getCoffeeName());
                            return coffeeOld;
                        })
                        .flatMap(coffeeService::updateCoffee),
                    Coffee.class)
            ))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    //    @PreAuthorize("hasAnyRole('user', 'admin')")
    public Mono<ServerResponse> getCoffeeOrdres(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
            .body(coffeeService.getCoffeeOrders(request.pathVariable("id")), CoffeeOrder.class)
            .switchIfEmpty(ServerResponse.notFound().build());
    }
}
