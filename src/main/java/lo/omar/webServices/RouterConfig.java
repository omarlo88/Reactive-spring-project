package lo.omar.webServices;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lo.omar.dao.DepartementRepository;
import lo.omar.entities.Departement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

    //@Autowired
    //private DepartementRepository departementRepository;


    //RouterFunction<ServerResponse> oubien de type ServerResponse
    @Bean
    RouterFunction<?> routerFunctionTest (DepartementRepository departementRepository){
        /*return RouterFunctions.route()
                .GET("/departements", serverRequest ->
                        ServerResponse.ok()
                                .body(departementRepository.findAll(), Departement.class)).build();*/

        /*return RouterFunctions.route()
                .GET("/departements", accept(APPLICATION_JSON), serverRequest ->
                        ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(departementRepository.findAll(), Departement.class))
                .GET("/departements/{id}", RequestPredicates.contentType(APPLICATION_JSON), serverRequest -> {
                    String id = serverRequest.pathVariable("id");
                    return ServerResponse.ok().body(departementRepository.findById(id), Departement.class);
                })
                .build();*/

        /*return RouterFunctions
                .route(RequestPredicates.GET("/departements").and(accept(APPLICATION_JSON)),
                        serverRequest ->
                                ServerResponse.ok()
                                .body(departementRepository.findAll(), Departement.class));*/

        /*return RouterFunctions
                .route(RequestPredicates.GET("/departements"),
                        serverRequest ->
                                ServerResponse.ok()
                                        .contentType(MediaType.TEXT_EVENT_STREAM)
                                        .body(departementRepository.findAll(), Departement.class));*/

        /*return RouterFunctions
                .route(RequestPredicates.GET("/departements"),
                        serverRequest ->
                                ServerResponse.ok()
                                .body(departementRepository.findAll(), Departement.class))
                .andRoute(RequestPredicates.GET("/departements/{id}"),
                        serverRequest -> {
                            String id = serverRequest.pathVariable("id");
                            return ServerResponse.ok().body(departementRepository.findById(id), Departement.class);
                        });*/

        /*return route(RequestPredicates.GET("/departements").and(accept(APPLICATION_JSON)),
                        serverRequest ->
                                ServerResponse.ok()
                                        .body(departementRepository.findAll(), Departement.class))
                .and(route(RequestPredicates.GET("/departements/{id}"),
                        serverRequest -> {
                            String id = serverRequest.pathVariable("id");
                            return ServerResponse.ok().body(departementRepository.findById(id), Departement.class);
                        }));
*/

        /*return RouterFunctions.route()
                .path("/api", builder -> builder
                                .GET("/departements", accept(APPLICATION_JSON), serverRequest ->
                                        ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                                .body(departementRepository.findAll(), Departement.class))
                                .GET("/departements/{id}", serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    return ServerResponse.ok().body(departementRepository.findById(id), Departement.class);
                                }))
                .GET("/departementByName/{name}", serverRequest -> {
                            String name = serverRequest.pathVariable("name");
                            return ServerResponse.ok().body(departementRepository.getByNom(name), Departement.class);
                        })
                .build();*/

        /*return route()
                .path("/api", builder -> builder
                        .nest(accept(APPLICATION_JSON), builder1 -> builder1
                                .GET("/departements", serverRequest ->
                                        ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                                .body(departementRepository.findAll(), Departement.class))
                                .GET("/departements/{id}", serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    return ServerResponse.ok().body(departementRepository.findById(id), Departement.class);
                                })
                                .POST("/departement", serverRequest -> {
                                    Mono<Departement> dept = serverRequest.bodyToMono(Departement.class)
                                            .flatMap(departement -> departementRepository.save(departement));
                                    return ServerResponse.ok().body(dept, Departement.class);
                                })
                        ))
                .GET("/departementByName/{name}", serverRequest -> {
                    String name = serverRequest.pathVariable("name");
                    return ServerResponse.ok().body(departementRepository.getByNom(name), Departement.class);
                })
                .build();*/

        return route()
                .path("/api/departements", builder -> builder
                        .nest(accept(APPLICATION_JSON), builder1 -> builder1
                                .GET("/", contentType(TEXT_EVENT_STREAM), serverRequest ->
                                                /*ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                                        .body(BodyInserters.fromPublisher(departementRepository.findAll(), Departement.class))*/

                                         ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                                .body(departementRepository.findAll(), Departement.class)
                                 )
                                //.GET("/", this::getDepartements)
                                .GET("/stream", serverRequest ->
                                        ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                                .body(departementRepository.findAll(), Departement.class))
                                .GET("/{id}", contentType(MediaType.APPLICATION_JSON), serverRequest -> {
                                    String id = serverRequest.pathVariable("id");

                                    /*Mono<Departement> dept = departementRepository.findById(id);
                                    return dept
                                            .flatMap(dt -> ServerResponse.ok().body(BodyInserters.fromPublisher(dept, Departement.class)))
                                                    .switchIfEmpty(ServerResponse.notFound().build());*/

                                    return departementRepository.findById(id)
                                        .flatMap(dt -> ServerResponse.ok()
                                            .body(BodyInserters.fromValue(dt)))
                                            .switchIfEmpty(ServerResponse.notFound().build());
                                })
                                .POST("/", serverRequest -> {

                                    /*Mono<Departement> dept = serverRequest.bodyToMono(Departement.class);
                                    return ServerResponse.ok()
                                            .body(BodyInserters.fromPublisher(dept.flatMap(departementRepository::save), Departement.class));*/

                                    Mono<Departement> dept = serverRequest.bodyToMono(Departement.class)
                                            .flatMap(departementRepository::save);
                                    return ServerResponse.ok().body(dept, Departement.class);
                                })
                                .PUT("/{id}", serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    Mono<Departement> dtNew = serverRequest.bodyToMono(Departement.class);
                                    return departementRepository.findById(id)
                                            .flatMap(dtOld -> ServerResponse.ok().body(
                                                    BodyInserters.fromPublisher(
                                                            dtNew
                                                                    //.map(dt -> new Departement(id, dt.getNom(), dt.getChef(), dt.getEmployees()))
                                                                    .map(dt -> {
                                                                        dtOld.setNom(dt.getNom());
                                                                        dtOld.setChef(dt.getChef());
                                                                        dtOld.setEmployees(dt.getEmployees());
                                                                        return dtOld;
                                                                    })
                                                                    .flatMap(departementRepository::save),
                                                            Departement.class)
                                            ))
                                            .switchIfEmpty(ServerResponse.notFound().build());
                                })
                                /*
                                .PUT("/{id}", serverRequest -> {
                                    String id = serverRequest.pathVariable("id");
                                    Mono<Departement> dtNew = serverRequest.bodyToMono(Departement.class);
                                    return departementRepository.findById(id)
                                            .flatMap(dtOld -> ServerResponse.ok().body(
                                                    BodyInserters.fromPublisher(
                                                            dtNew.map(dt -> new Departement(id, dt.getNom(), dt.getChef(), dt.getEmployees()))
                                                            .flatMap(departementRepository::save),
                                                            Departement.class)
                                            ))
                                            .switchIfEmpty(ServerResponse.notFound().build());
                                })*/
                        )
                        .DELETE("/{id}", serverRequest -> {
                                String id = serverRequest.pathVariable("id");

                            /*return ServerResponse.ok().body(departementRepository.deleteById(id), Void.class)
                                    .switchIfEmpty(ServerResponse.notFound().build());*/

                                return ServerResponse.noContent().build(departementRepository.deleteById(id))
                                        .switchIfEmpty(ServerResponse.notFound().build());

                            })
                )
                .GET("/departementByName/{nom}", serverRequest -> {
                    String nom = serverRequest.pathVariable("nom");
                    return ServerResponse.ok().body(departementRepository.getByNom(nom), Departement.class);
                })
                .GET("/chercherDepartementByQuery", serverRequest -> {
                    //String nom = serverRequest.queryParam("nom").orElseThrow(RuntimeException::new);
                    String nom = serverRequest.queryParam("nom").orElseThrow(() -> new RuntimeException("Not Found!!"));
                    return ServerResponse.ok().body(departementRepository.findAllByNomContainingIgnoreCaseOrderByNom(nom), Departement.class);
                })
                .build();
    }

    /*private Mono<ServerResponse> getDepartements(ServerRequest serverRequest){
        return ServerResponse.ok()//.contentType(MediaType.TEXT_EVENT_STREAM)
                .body(departementRepository.findAll(), Departement.class);
    }
*/
    @Bean
    RouterFunction<?> routerFunctionHandler(DepartementHandler handler){
        return RouterFunctions.route()
                .path("/api/handler/departements", builder -> builder
                        //.GET("/", contentType(APPLICATION_JSON), handler::getDepartements)
                        //.GET("/", accept(APPLICATION_JSON), handler::getDepartements)
                        .GET("/", handler::getDepartements)
                        .GET("/{id}", handler::getDepartement)
                        .GET("/stream/departements", handler::getDepartementsStream)
                        .GET("/query/byNom", handler::rechercherDepartementByQuery)
                        .DELETE("/{id}", handler::deleteDepartement)
                        .nest(accept(APPLICATION_JSON), builder1 -> builder1
                                .POST("/", handler::saveDepartement)
                                .PUT("/{id}", handler::updateDepartement))
                        .GET("/chercherDepartement/ByNomIgnoreCase", handler::chercherDepartementByNomIgnoreCase)
                        .GET("/chercherDepartementByNom/StartingWithIgnoreCase", handler::chercherDepartementByNomStartingWithIgnoreCase)
                        .GET("/chercherDepartementByNomLike/WithIgnoreCase", handler::chercherDepartementByNomLikeWithIgnoreCase)
                )
                .build();
    }
}
